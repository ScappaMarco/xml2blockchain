package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.RemoveFromEReference;
import metamodel.src.cbp.Value;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class RemoveFromEReferenceImpl extends EObjectImpl implements RemoveFromEReference {

    protected static final String NAME_EDEFAULT = null;
    protected String name = NAME_EDEFAULT;

    protected static final String TARGET_EDEFAULT = null;
    protected String target = TARGET_EDEFAULT;

    protected Value value;

    protected RemoveFromEReferenceImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.REMOVE_FROM_EREFERENCE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.REMOVE_FROM_EREFERENCE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.REMOVE_FROM_EREFERENCE__TARGET, oldTarget, target));
    }

    @Override
    public Value getValue() {
        return value;
    }

    public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
        Value oldValue = value;
        value = newValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CBPPackage.REMOVE_FROM_EREFERENCE__VALUE, oldValue, newValue);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    @Override
    public void setValue(Value newValue) {
        if (newValue != value) {
            NotificationChain msgs = null;
            if (value != null)
                msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CBPPackage.REMOVE_FROM_EREFERENCE__VALUE, null, msgs);
            if (newValue != null)
                msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CBPPackage.REMOVE_FROM_EREFERENCE__VALUE, null, msgs);
            msgs = basicSetValue(newValue, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.REMOVE_FROM_EREFERENCE__VALUE, newValue, newValue));
    }

    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CBPPackage.REMOVE_FROM_EREFERENCE__VALUE:
                return basicSetValue(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.REMOVE_FROM_EREFERENCE__NAME:
                return getName();
            case CBPPackage.REMOVE_FROM_EREFERENCE__TARGET:
                return getTarget();
            case CBPPackage.REMOVE_FROM_EREFERENCE__VALUE:
                return getValue();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.REMOVE_FROM_EREFERENCE__NAME:
                setName((String)newValue);
                return;
            case CBPPackage.REMOVE_FROM_EREFERENCE__TARGET:
                setTarget((String)newValue);
                return;
            case CBPPackage.REMOVE_FROM_EREFERENCE__VALUE:
                setValue((Value)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.REMOVE_FROM_EREFERENCE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case CBPPackage.REMOVE_FROM_EREFERENCE__TARGET:
                setTarget(TARGET_EDEFAULT);
                return;
            case CBPPackage.REMOVE_FROM_EREFERENCE__VALUE:
                setValue((Value)null);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.REMOVE_FROM_EREFERENCE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case CBPPackage.REMOVE_FROM_EREFERENCE__TARGET:
                return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT.equals(target);
            case CBPPackage.REMOVE_FROM_EREFERENCE__VALUE:
                return value != null;
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