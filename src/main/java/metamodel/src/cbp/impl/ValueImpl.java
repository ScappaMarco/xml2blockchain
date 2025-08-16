package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Value;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class ValueImpl extends EObjectImpl implements Value {

    protected static final String LITERAL_EDEFAULT = null;
    protected String literal = LITERAL_EDEFAULT;

    protected static final String EOBJECT_EDEFAULT = null;
    protected String eobject = EOBJECT_EDEFAULT;

    protected ValueImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.VALUE;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public void setLiteral(String newLiteral) {
        String oldLiteral = literal;
        literal = newLiteral;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.VALUE__LITERAL, oldLiteral, literal));
    }

    @Override
    public String getEobject() {
        return eobject;
    }

    @Override
    public void setEobject(String newEobject) {
        String oldEobject = eobject;
        eobject = newEobject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.VALUE__EOBJECT, oldEobject, eobject));
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.VALUE__LITERAL:
                return getLiteral();
            case CBPPackage.VALUE__EOBJECT:
                return getEobject();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.VALUE__LITERAL:
                setLiteral((String)newValue);
                return;
            case CBPPackage.VALUE__EOBJECT:
                setEobject((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.VALUE__LITERAL:
                setLiteral(LITERAL_EDEFAULT);
                return;
            case CBPPackage.VALUE__EOBJECT:
                setEobject(EOBJECT_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.VALUE__LITERAL:
                return LITERAL_EDEFAULT == null ? literal != null : !LITERAL_EDEFAULT.equals(literal);
            case CBPPackage.VALUE__EOBJECT:
                return EOBJECT_EDEFAULT == null ? eobject != null : !EOBJECT_EDEFAULT.equals(eobject);
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (literal: ");
        result.append(literal);
        result.append(", eobject: ");
        result.append(eobject);
        result.append(')');
        return result.toString();
    }
}