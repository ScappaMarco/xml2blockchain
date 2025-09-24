package cbp.src.event;

import cbp.src.resource.CBPResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class DeleteEObjectEvent extends EObjectEvent {

    protected EClass eClass;
    protected CBPResource resource;
    protected String id;
    protected EObject eObject;
    protected String ePackage;

    public DeleteEObjectEvent() {

    }

    public DeleteEObjectEvent(EClass eClass, String id) {
        super();
        this.eClass = eClass;
        this.id = id;
    }

    public DeleteEObjectEvent(EObject eObject, String id) {
        super();
        this.eObject = eObject;
        this.eClass = eObject.eClass();
        this.id = id;
        this.setValue(eObject);
    }

    public DeleteEObjectEvent(EClass eClass, CBPResource resource, String id) {
        super();
        this.eClass = eClass;
        this.id = id;
        this.resource = resource;
        if (resource != null) {
            this.eObject = resource.getEObject(id);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public EClass getEClass() {
        return eClass;
    }

    public String getId() {
        return id;
    }

    public void setEClass(EClass eClass) {
        this.eClass = eClass;
    }

    public CBPResource getResource() {
        return resource;
    }

    public void setResource(CBPResource resource) {
        this.resource = resource;
    }

    public EObject getEObject() {
        return eObject;
    }

    public void setEObject(EObject eObject) {
        this.eObject = eObject;
    }

    @Override
    public void replay() {
        this.eObject = resource.getEObject(this.id);
        if(eObject != null) {
            this.setValue(eObject);
            EcoreUtil.delete(eObject);
        }
    }

    @Override
    public <U> U accept(IChangeEventVisitor<U> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ChangeEvent<?> reverse() {
        CreateEObjectEvent event = new CreateEObjectEvent(eClass, resource, id);
        return event;
    }

    public String getePackage() {
        return this.ePackage;
    }

    public void setePackage(String ePackage) {
        this.ePackage = ePackage;
    }
}