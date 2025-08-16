package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Register;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class RegisterImpl extends EObjectImpl implements Register {

    protected static final String EPACKAGE_EDEFAULT = null;
    protected String epackage = EPACKAGE_EDEFAULT;

    protected RegisterImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.REGISTER;
    }

    @Override
    public String getEpackage() {
        return epackage;
    }

    @Override
    public void setEpackage(String newEpackage) {
        String oldEpackage = epackage;
        epackage = newEpackage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.REGISTER__EPACKAGE, oldEpackage, epackage));
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.REGISTER__EPACKAGE:
                return getEpackage();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.REGISTER__EPACKAGE:
                setEpackage((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.REGISTER__EPACKAGE:
                setEpackage(EPACKAGE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.REGISTER__EPACKAGE:
                return EPACKAGE_EDEFAULT == null ? epackage != null : !EPACKAGE_EDEFAULT.equals(epackage);
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (epackage: ");
        result.append(epackage);
        result.append(')');
        return result.toString();
    }
}