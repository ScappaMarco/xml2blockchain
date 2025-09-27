package cbp.src.resource;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.ChangeEventAdapter;
import cbp.src.event.StartNewSessionEvent;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.*;
import cbp.src.util.AppendFileURIHandlerImpl;
import cbp.src.util.AppendingURIHandler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.eclipse.emf.ecore.util.EcoreUtil;

import javax.xml.stream.FactoryConfigurationError;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public abstract class CBPResource extends ResourceImpl {

    protected ChangeEventAdapter changeEventAdapter;
    protected BiMap<EObject, String> eObjectToIdMap;
    protected Set<Integer> ignoreSet;
    protected List<Integer> ignoreList;
    protected IdType idType = IdType.NUMERIC;
    protected int idCounter = 0;
    protected int persistedIgnoredEvents = 0;
    protected String idPrefix = "";

    public enum IdType {
        NUMERIC, UUID
    }

    public CBPResource() {
        this.ignoreSet = new HashSet<>();
        this.ignoreList = new ArrayList<Integer>();
        this.eObjectToIdMap = HashBiMap.create();
        this.changeEventAdapter = new ChangeEventAdapter(this);
    }

    public CBPResource(URI uri) {
        this();
        this.uri = uri;
    }

    public BiMap<EObject, String> getEObjectToIdMap() {
        return this.eObjectToIdMap;
    }

    public ChangeEventAdapter getChangeEventAdapter() {
        return this.changeEventAdapter;
    }

    public List<ChangeEvent<?>> getChangeEvents() {
        return this.changeEventAdapter.getChangeEvents();
    }

    public Set<Integer> getIgnoreSet() {
        return this.ignoreSet;
    }

    public void setIgnoreSet(Set<Integer> set) {
        this.ignoreSet = set;
    }

    public IdType getIdType() {
        return this.idType;
    }

    public void setIdType(IdType idType) {
        this.setIdType(idType, "");
    }

    public void setIdType(IdType idType, String idPrefix) {
        this.idType = idType;
        this.idPrefix = idPrefix;
    }

    @Override
    protected URIConverter getURIConverter() {
        return new ExtensibleURIConverterImpl(Arrays.asList(new URIHandler[] { new AppendFileURIHandlerImpl(), new AppendingURIHandler(new PlatformResourceURIHandlerImpl()),
                new AppendingURIHandler(new EFSURIHandlerImpl()), new AppendingURIHandler(new ArchiveURIHandlerImpl()), new AppendingURIHandler(new URIHandlerImpl()) }),
                ContentHandler.Registry.INSTANCE.contentHandlers());
    }

    @Override
    public String getURIFragment(EObject eObject) {
        String uriFragment = null;
        if(this.eObjectToIdMap == null) {
            uriFragment = null;
        } else {
            uriFragment = this.eObjectToIdMap.get(eObject);
        }
        return uriFragment;
    }

    @Override
    public EObject getEObject(String uriFragment) {
        return this.eObjectToIdMap.inverse().get(uriFragment);
    }

    public void unregister(EObject eObject) {
        this.eObjectToIdMap.remove(eObject);
    }

    public String register(EObject eObject, String id) {
        this.adopt(eObject, id);
        return id;
    }

    public String register(EObject eObject) {
        String id = null;
        if(this.idType == IdType.NUMERIC) {
            while(this.eObjectToIdMap.containsValue(String.valueOf(idCounter))) {
                this.idCounter++;
            }
            id = idPrefix + String.valueOf(idCounter);
            this.idCounter++;
        } else if(this.idType == IdType.UUID) {
            id = EcoreUtil.generateUUID();
        }
        adopt(eObject, id);
        return id;
    }

    public void adopt(EObject eObject, String id) {
        if(!(this.eObjectToIdMap.containsKey(eObject)) && !(this.eObjectToIdMap.containsValue(id))) {
            this.eObjectToIdMap.put(eObject, id);
        } else {
            this.eObjectToIdMap.replace(eObject, id);
        }
    }

    public boolean isRegistered(EObject eObject) {
        return this.eObjectToIdMap.containsKey(eObject);
    }

    public void startNewSession(String id) {
        this.changeEventAdapter.handleStartNewSession(id);
    }

    public void startNewSession() {
        this.changeEventAdapter.handleStartNewSession();
    }

    public void loadIgnoreSet(BufferedInputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        this.ignoreSet.clear();
        this.ignoreList.clear();
        while(dis.available() > 0) {
            int value = dis.readInt();
            if(this.ignoreSet.add(value)) {
                this.ignoreList.add(value);
            }
        }
        this.persistedIgnoredEvents = ignoreList.size();
    }

    public void loadIgnoreSet(ByteArrayInputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        ignoreSet.clear();
        ignoreList.clear();
        while(dis.available() > 0) {
            int value = dis.readInt();
            if(this.ignoreSet.add(value)) {
                this.ignoreList.add(value);
            }
        }
        this.persistedIgnoredEvents = this.ignoreList.size();
    }

    public void loadIgnoreSet(FileInputStream inputStream) throws IOException {
        if(inputStream.getChannel().size() <= Integer.MAX_VALUE) {
            FileChannel inChannel = inputStream.getChannel();
            ByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            int[] result = new int[(int) (inChannel.size() / 4)];
            buffer.order(ByteOrder.BIG_ENDIAN);
            IntBuffer intBuffer = buffer.asIntBuffer();
            intBuffer.get(result);
            this.ignoreSet.clear();
            this.ignoreList.clear();
            for(int i : result) {
                this.ignoreSet.add(i);
            }
            this.ignoreList = new ArrayList<>(this.ignoreSet);
        } else {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
            int count = (int) (inputStream.getChannel().size() / 4);
            Integer[] values = new Integer[count];
            for (int i = 0; i < count; i++) {
                values[i] = dis.readInt();
            }
            this.ignoreSet.clear();
            this.ignoreList.clear();
            this.ignoreSet = new HashSet<Integer>(Arrays.asList(values));
            this.ignoreList = new ArrayList<>(this.ignoreSet);
        }
        this.persistedIgnoredEvents = this.ignoreList.size();
    }

    public void saveIgnoreSet(ByteArrayOutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        for (int item : this.ignoreList.subList(this.persistedIgnoredEvents, this.ignoreList.size())) {
            dos.writeInt(item);
        }
        dos.flush();
        dos.close();
        this.clearIgnoreSet();
        this.persistedIgnoredEvents = this.ignoreList.size();
    }

    public void clearIgnoreSet() {
        this.ignoreSet.clear();
        this.ignoreList.clear();
        this.persistedIgnoredEvents = 0;
    }

    public void deleteElement(EObject target) {
        this.startCompositeEvent();

        this.recursiveDeleteEvent(target);
        this.removeFromExternalReferences(target);
        this.unsetAllReferences(target);
        this.unsetAllAttributes(target);

        EcoreUtil.remove(target);
        this.endCompositeEvent();
    }

    private void recursiveDeleteEvent(EObject target) {
        for (EReference ref : target.eClass().getEAllReferences()) {
            if(ref.isChangeable() && target.eIsSet(ref) && !(ref.isDerived()) && ref.isContainment()) {
                if(ref.isMany()) {
                    List<EObject> values = (List<EObject>) target.eGet(ref);
                    while(values.size() > 0) {
                        EObject value = values.get(values.size() - 1);
                        this.recursiveDeleteEvent(value);
                        this.removeFromExternalReferences(value);
                        this.unsetAllReferences(value);
                        this.unsetAllAttributes(value);
                        values.remove(value);
                    }
                } else {
                    EObject value = (EObject) target.eGet(ref);
                    if(value != null) {
                        this.recursiveDeleteEvent(value);
                        this.removeFromExternalReferences(value);
                        this.unsetAllReferences(value);
                        this.unsetAllAttributes(value);
                        target.eUnset(ref);
                    }
                }
            }
        }
    }

    private void unsetAllReferences(EObject target) {
        for (EReference ref : target.eClass().getEAllReferences()) {
            if(ref.isChangeable() && target.eIsSet(ref) && !(ref.isDerived())) {
                if(ref.isMany()) {
                    List<EObject> values = (List<EObject>) target.eGet(ref);
                    while(values.size() > 0) {
                        EObject value = values.get(values.size() - 1);
                        values.remove(value);
                    }
                } else {
                    EObject value = (EObject) target.eGet(ref);
                    if(value != null) {
                        target.eUnset(ref);
                    }
                }
            }
        }
    }

    private void unsetAllAttributes(EObject target) {
        for (EAttribute att : target.eClass().getEAllAttributes()) {
            if(att.isChangeable() && target.eIsSet(att) && !(att.isDerived())) {
                if(att.isMany()) {
                    EList<?> values = (EList<?>) target.eGet(att);
                    while(values.size() > 0) {
                        values.remove(values.size() - 1);
                    }
                } else {
                    Object value = target.eGet(att);
                    target.eUnset(att);
                }
            }
        }
    }

    private void removeFromExternalReferences(EObject target) {
        Iterator<EObject> iterator = this.getAllContents();
        while (iterator.hasNext()) {
            EObject refferingEObject = iterator.next();
            for (EReference ref : refferingEObject.eClass().getEAllReferences()) {
                if(!ref.isContainment()) {
                    if(ref.isMany()) {
                        List<EObject> values = (List<EObject>) refferingEObject.eGet(ref);
                        values.remove(refferingEObject);
                    } else {
                        EObject value = (EObject) refferingEObject.eGet(ref);
                        if(value != null && value.equals(refferingEObject)) {
                            refferingEObject.eUnset(ref);
                        }
                    }
                }
            }
        }
    }

    public void startCompositeEvent() {
        this.getChangeEventAdapter().startCompositeOperation();
    }

    public void endCompositeEvent() {
        this.getChangeEventAdapter().endCompositeOperation();
    }

    public abstract ChangeEventsMap replayEvents(InputStream inputStream) throws FactoryConfigurationError, IOException;

    public abstract void writeCBPXML(Map<StartNewSessionEvent, List<ChangeEvent>> changeEventMap);
}