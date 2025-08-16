package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.UnsetEReference;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class UnsetEReferenceImpl extends EObjectImpl implements UnsetEReference {

    protected static final String NAME_EDEFAULT = null;
    protected String name = NAME_EDEFAULT;

    protected static final String TARGET_EDEFAULT = null;
    protected String target = TARGET_EDEFAULT;

    protected UnsetEReferenceImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.UNSET_EREFERENCE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.UNSET_EREFERENCE__NAME, oldName, name));
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public void setTarget(String newTarget) {
        String oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.UNSET_EREFERENCE__TARGET, oldTarget, target));
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.UNSET_EREFERENCE__NAME:
                return getName();
            case CBPPackage.UNSET_EREFERENCE__TARGET:
                return getTarget();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.UNSET_EREFERENCE__NAME:
                setName((String)newValue);
                return;
            case CBPPackage.UNSET_EREFERENCE__TARGET:
                setTarget((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.UNSET_EREFERENCE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case CBPPackage.UNSET_EREFERENCE__TARGET:
                setTarget(TARGET_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.UNSET_EREFERENCE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case CBPPackage.UNSET_EREFERENCE__TARGET:
                return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT.equals(target);
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", target: ");
        result.append(target);
        result.append(')');
        return result.toString();
    }
}