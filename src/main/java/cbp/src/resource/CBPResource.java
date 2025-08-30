package cbp.src.resource;

import cbp.src.event.ChangeEvent;
import cbp.src.event.ChangeEventAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.*;
import cbp.src.util.AppendFileURIHandlerImpl;
import cbp.src.util.AppendingURIHandler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
        ignoreSet.clear();
        ignoreList.clear();
        while(dis.available() > 0) {
            int value = dis.readInt();
            if(ignoreSet.add(value)) {
                ignoreList.add(value);
            }
        }
        this.persistedIgnoredEvents = ignoreList.size();
    }








}
