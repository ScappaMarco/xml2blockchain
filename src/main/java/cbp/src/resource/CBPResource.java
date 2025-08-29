package cbp.src.resource;

import cbp.src.event.ChangeEvent;
import cbp.src.event.ChangeEventAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;

public abstract class CBPResource extends ResourceImpl {

    protected ChangeEventAdapter changeEventAdapter;
    protected BiMap<EObject, String> eObjectToIdMap;
    protected Set<Integer> ignoreSet;
    protected List<Integer> ignoreList;
    protected IdType idType = IdType.NUMERIC;

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

    @Override
    protected URIConverter getURIConverter() {
        return new ExtensibleURIConverterImpl(Arrays.asList(new URIHandler[] { new AppendFileURIHandlerImpl(), new AppendingURIHandler(new PlatformResourceURIHandlerImpl()),
                new AppendingURIHandler(new EFSURIHandlerImpl()), new AppendingURIHandler(new ArchiveURIHandlerImpl()), new AppendingURIHandler(new URIHandlerImpl()) }),
                ContentHandler.Registry.INSTANCE.contentHandlers());
    }






}
