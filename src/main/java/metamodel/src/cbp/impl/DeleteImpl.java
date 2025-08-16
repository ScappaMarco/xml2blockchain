package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Delete;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class DeleteImpl extends EObjectImpl implements Delete {

    protected static final String DEFAULT_ECLASS = null;
    protected String eclass = DEFAULT_ECLASS;

    protected static final String DEFAULT_EPACKAGE = null;
    protected String epackage = DEFAULT_EPACKAGE;

    protected static final String DEFAULT_ID = null;
    protected String id = DEFAULT_ID;

    protected DeleteImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return CBPPackage.Literals.DELETE;
    }

    @Override
    public String getEclass() {
        return this.eclass;
    }

    @Override
    public void setEclass(String newEclass) {
        String oldEclass = this.getEclass();
        eclass = newEclass;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.DELETE__ECLASS, oldEclass, eclass));
        }
    }

    @Override
    public String getEpackage() {
        return this.epackage;
    }

    @Override
    public void setEpackage(String newEpackage) {
        String oldEpackage = this.getEpackage();
        epackage = newEpackage;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.DELETE__EPACKAGE, oldEpackage, epackage));
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String newId) {
        String oldId = this.getId();
        id = newId;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.DELETE__ID, oldId, id));
        }
    }

    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CBPPackage.DELETE__ECLASS:
                return getEclass();
            case CBPPackage.DELETE__EPACKAGE:
                return getEpackage();
            case CBPPackage.DELETE__ID:
                return getId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CBPPackage.DELETE__ECLASS:
                setEclass((String)newValue);
                return;
            case CBPPackage.DELETE__EPACKAGE:
                setEpackage((String)newValue);
                return;
            case CBPPackage.DELETE__ID:
                setId((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CBPPackage.DELETE__ECLASS:
                setEclass(DEFAULT_ECLASS);
                return;
            case CBPPackage.DELETE__EPACKAGE:
                setEpackage(DEFAULT_EPACKAGE);
                return;
            case CBPPackage.DELETE__ID:
                setId(DEFAULT_ID);
                return;
        }
        super.eUnset(featureID);
    }

    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CBPPackage.DELETE__ECLASS:
                return DEFAULT_ECLASS == null ? eclass != null : !DEFAULT_ECLASS.equals(eclass);
            case CBPPackage.DELETE__EPACKAGE:
                return DEFAULT_EPACKAGE == null ? epackage != null : !DEFAULT_EPACKAGE.equals(epackage);
            case CBPPackage.DELETE__ID:
                return DEFAULT_ID == null ? id != null : !DEFAULT_ID.equals(id);
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (eclass: ");
        result.append(eclass);
        result.append(", epackage: ");
        result.append(epackage);
        result.append(", id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }
}