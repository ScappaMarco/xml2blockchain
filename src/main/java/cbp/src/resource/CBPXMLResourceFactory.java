package cbp.src.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.eclipse.emf.common.util.URI;

public class CBPXMLResourceFactory extends ResourceFactoryImpl {

    @Override
    public Resource createResource(URI uri) {
        if(uri.toString().endsWith(".cbpxml")) {
            return new NewCBPXMLResourceImpl(uri);
        } else {
            throw new RuntimeException("Unknown extension. Could not create resource for URI " + uri);
        }
    }
}