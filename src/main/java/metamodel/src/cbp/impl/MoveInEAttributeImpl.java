package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.MoveInEAttribute;
import metamodel.src.cbp.Value;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class MoveInEAttributeImpl extends EObjectImpl implements MoveInEAttribute {

    protected static final String DEFAULT_FROM = null;
    protected String from = DEFAULT_FROM;

    protected static final String DEFAULT_TO = null;
    protected String to = DEFAULT_TO;

    protected static final String DEFAULT_NAME = null;
    protected String name = DEFAULT_NAME;

    protected static final String DEFAULT_TARGET = null;
    protected String target = DEFAULT_TARGET;

    protected Value value;

    protected MoveInEAttributeImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.MOVE_IN_EATTRIBUTE;
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public void setFrom(String newFrom) {
        String oldFrom = this.getFrom();
        from = newFrom;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__FROM, oldFrom, from));
        }
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public void setTo(String newTo) {
        String oldTo = this.getTo();
        to = newTo;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__TO, oldTo, to));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        String oldName = this.getName();
        name = newName;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__NAME, oldName, name));
        }
    }

    @Override
    public String getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(String newTarget) {
        String oldTarget = this.getTarget();
        target = newTarget;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__TARGET, oldTarget, target));
        }
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
        Value oldValue = value;
        value = newValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__VALUE, oldValue, newValue);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    @Override
    public void setValue(Value newValue) {
        if (newValue != value) {
            NotificationChain msgs = null;
            if (value != null)
                msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CBPPackage.MOVE_IN_EATTRIBUTE__VALUE, null, msgs);
            if (newValue != null)
                msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CBPPackage.MOVE_IN_EATTRIBUTE__VALUE, null, msgs);
            msgs = basicSetValue(newValue, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.MOVE_IN_EATTRIBUTE__VALUE, newValue, newValue));
        }
    }

    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CBPPackage.MOVE_IN_EATTRIBUTE__VALUE:
                return basicSetValue(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.MOVE_IN_EATTRIBUTE__FROM:
                return getFrom();
            case CBPPackage.MOVE_IN_EATTRIBUTE__TO:
                return getTo();
            case CBPPackage.MOVE_IN_EATTRIBUTE__NAME:
                return getName();
            case CBPPackage.MOVE_IN_EATTRIBUTE__TARGET:
                return getTarget();
            case CBPPackage.MOVE_IN_EATTRIBUTE__VALUE:
                return getValue();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.MOVE_IN_EATTRIBUTE__FROM:
                setFrom((String)newValue);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__TO:
                setTo((String)newValue);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__NAME:
                setName((String)newValue);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__TARGET:
                setTarget((String)newValue);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__VALUE:
                setValue((Value)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.MOVE_IN_EATTRIBUTE__FROM:
                setFrom(DEFAULT_FROM);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__TO:
                setTo(DEFAULT_TO);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__NAME:
                setName(DEFAULT_NAME);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__TARGET:
                setTarget(DEFAULT_TARGET);
                return;
            case CBPPackage.MOVE_IN_EATTRIBUTE__VALUE:
                setValue((Value)null);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.MOVE_IN_EATTRIBUTE__FROM:
                return DEFAULT_FROM == null ? from != null : !DEFAULT_FROM.equals(from);
            case CBPPackage.MOVE_IN_EATTRIBUTE__TO:
                return DEFAULT_TO == null ? to != null : !DEFAULT_TO.equals(to);
            case CBPPackage.MOVE_IN_EATTRIBUTE__NAME:
                return DEFAULT_NAME == null ? name != null : !DEFAULT_NAME.equals(name);
            case CBPPackage.MOVE_IN_EATTRIBUTE__TARGET:
                return DEFAULT_TARGET == null ? target != null : !DEFAULT_TARGET.equals(target);
            case CBPPackage.MOVE_IN_EATTRIBUTE__VALUE:
                return value != null;
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (from: ");
        result.append(from);
        result.append(", to: ");
        result.append(to);
        result.append(", name: ");
        result.append(name);
        result.append(", target: ");
        result.append(target);
        result.append(')');
        return result.toString();
    }
}