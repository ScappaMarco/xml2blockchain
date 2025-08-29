package cbp.src.event;

import cbp.src.resource.CBPResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public abstract class ResourceEvent extends ChangeEvent<EObject> implements EObjectValuesEvent {

    protected Resource resource;

    //resource CBP
    public void setResource(CBPResource resource) {
        this.resource = resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}