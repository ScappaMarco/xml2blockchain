package metamodel.src.cbp.impl;

import metamodel.src.cbp.AddToResource;
import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Value;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class AddToResourceImpl extends EObjectImpl implements AddToResource {

    protected static final String DEFAULT_POSITION = null;
    protected String position = DEFAULT_POSITION;

    protected Value value;

    protected AddToResourceImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.ADD_TO_RESOURCE;
    }

    @Override
    public String getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(String newPosition) {
        String oldPosition = this.getPosition();
        position = newPosition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_RESOURCE__POSITION, oldPosition, position));
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
        Value oldValue = this.getValue();
        value = newValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_RESOURCE__VALUE, oldValue, newValue);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    @Override
    public void setValue(Value newValue) {
        if (newValue != this.getValue()) {
            NotificationChain msgs = null;
            if (value != null)
                msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CBPPackage.ADD_TO_RESOURCE__VALUE, null, msgs);
            if (newValue != null)
                msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CBPPackage.ADD_TO_RESOURCE__VALUE, null, msgs);
            msgs = basicSetValue(newValue, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_RESOURCE__VALUE, newValue, newValue));
        }
    }

    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CBPPackage.ADD_TO_RESOURCE__VALUE:
                return basicSetValue(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.ADD_TO_RESOURCE__POSITION:
                return this.getPosition();
            case CBPPackage.ADD_TO_RESOURCE__VALUE:
                return this.getValue();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.ADD_TO_RESOURCE__POSITION:
                this.setPosition((String)newValue);
                return;
            case CBPPackage.ADD_TO_RESOURCE__VALUE:
                this.setValue((Value)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.ADD_TO_RESOURCE__POSITION:
                this.setPosition(DEFAULT_POSITION);
                return;
            case CBPPackage.ADD_TO_RESOURCE__VALUE:
                this.setValue((Value)null);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.ADD_TO_RESOURCE__POSITION:
                return DEFAULT_POSITION == null ? position != null : !DEFAULT_POSITION.equals(position);
            case CBPPackage.ADD_TO_RESOURCE__VALUE:
                return value != null;
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (position: ");
        result.append(position);
        result.append(')');
        return result.toString();
    }
}