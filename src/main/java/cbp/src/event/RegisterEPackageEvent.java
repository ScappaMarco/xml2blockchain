package cbp.src.event;

import org.eclipse.emf.ecore.EPackage;

import javax.lang.model.type.NullType;

public class RegisterEPackageEvent extends ChangeEvent<NullType> {

    protected EPackage ePackage;
    protected ChangeEventAdapter eventAdapter;

    public RegisterEPackageEvent(EPackage ePackage, ChangeEventAdapter eventAdapter) {
        this.ePackage = ePackage;
        this.eventAdapter = eventAdapter;
    }

    public EPackage getEPackage() {
        return ePackage;
    }

    @Override
    public void replay() {

    }

    @Override
    public <U> U accept(IChangeEventVisitor<U> visitor) {
        return null;
    }
}