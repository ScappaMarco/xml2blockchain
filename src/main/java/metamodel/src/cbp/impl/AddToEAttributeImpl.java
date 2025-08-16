package metamodel.src.cbp.impl;

import metamodel.src.cbp.AddToEAttribute;
import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Value;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class AddToEAttributeImpl extends EObjectImpl implements AddToEAttribute {

    protected static final String DEFAULT_NAME = null;
    protected String name = DEFAULT_NAME;

    protected static final String DEFAULT_POSITION = null;
    protected String position = DEFAULT_POSITION;

    protected static final String DEFAULT_TARGET = null;
    protected String target = DEFAULT_TARGET;

    protected Value value;

    protected AddToEAttributeImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.ADD_TO_EATTRIBUTE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        String oldName = this.getName();
        this.name = newName;
        if(eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_EATTRIBUTE__NAME, oldName, this.name));
        }
    }

    @Override
    public String getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(String newPosition) {
        String oldPosition = this.getPosition();
        this.position = newPosition;
        if(eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_EATTRIBUTE__POSITION, oldPosition, this.position));
        }
    }

    @Override
    public String getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(String newTarget) {
        String oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_EATTRIBUTE__TARGET, oldTarget, target));
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
        Value oldValue = value;
        value = newValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_EATTRIBUTE__VALUE, oldValue, newValue);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    @Override
    public void setValue(Value newValue) {
        if (newValue != value) {
            NotificationChain msgs = null;
            if (value != null)
                msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CBPPackage.ADD_TO_EATTRIBUTE__VALUE, null, msgs);
            if (newValue != null)
                msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CBPPackage.ADD_TO_EATTRIBUTE__VALUE, null, msgs);
            msgs = basicSetValue(newValue, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.ADD_TO_EATTRIBUTE__VALUE, newValue, newValue));
        }
    }

    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CBPPackage.ADD_TO_EATTRIBUTE__VALUE:
                return basicSetValue(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.ADD_TO_EATTRIBUTE__NAME:
                return getName();
            case CBPPackage.ADD_TO_EATTRIBUTE__POSITION:
                return getPosition();
            case CBPPackage.ADD_TO_EATTRIBUTE__TARGET:
                return getTarget();
            case CBPPackage.ADD_TO_EATTRIBUTE__VALUE:
                return getValue();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.ADD_TO_EATTRIBUTE__NAME:
                setName((String)newValue);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__POSITION:
                setPosition((String)newValue);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__TARGET:
                setTarget((String)newValue);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__VALUE:
                setValue((Value)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.ADD_TO_EATTRIBUTE__NAME:
                setName(DEFAULT_NAME);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__POSITION:
                setPosition(DEFAULT_POSITION);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__TARGET:
                setTarget(DEFAULT_TARGET);
                return;
            case CBPPackage.ADD_TO_EATTRIBUTE__VALUE:
                setValue((Value)null);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.ADD_TO_EATTRIBUTE__NAME:
                return DEFAULT_NAME == null ? name != null : !DEFAULT_NAME.equals(name);
            case CBPPackage.ADD_TO_EATTRIBUTE__POSITION:
                return DEFAULT_POSITION == null ? position != null : !DEFAULT_POSITION.equals(position);
            case CBPPackage.ADD_TO_EATTRIBUTE__TARGET:
                return DEFAULT_TARGET == null ? target != null : !DEFAULT_TARGET.equals(target);
            case CBPPackage.ADD_TO_EATTRIBUTE__VALUE:
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
        result.append(", position: ");
        result.append(position);
        result.append(", target: ");
        result.append(target);
        result.append(')');
        return result.toString();
    }
}