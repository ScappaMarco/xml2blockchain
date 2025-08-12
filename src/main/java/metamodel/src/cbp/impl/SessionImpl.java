package metamodel.src.cbp.impl;

import metamodel.src.cbp.CBPPackage;
import metamodel.src.cbp.Session;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class SessionImpl extends EObjectImpl implements Session {

	protected static final String DEFAULT_ID = null;
	protected String id = DEFAULT_ID;

	protected static final String DEFAULT_TIME = null;
	protected String time = DEFAULT_TIME;

	protected SessionImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return CBPPackage.Literals.SESSION;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String newId) {
		String oldId = this.id;
		this.id = newId;
		if(eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.SESSION__ID, oldId, id));
		}
	}

	public String getTime() {
		return time;
	}

	public void setTime(String newTime) {
		String oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CBPPackage.SESSION__TIME, oldTime, time));
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CBPPackage.SESSION__ID:
				return getId();
			case CBPPackage.SESSION__TIME:
				return getTime();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CBPPackage.SESSION__ID:
				setId((String)newValue);
				return;
			case CBPPackage.SESSION__TIME:
				setTime((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CBPPackage.SESSION__ID:
				setId(DEFAULT_ID);
				return;
			case CBPPackage.SESSION__TIME:
				setTime(DEFAULT_TIME);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CBPPackage.SESSION__ID:
				return DEFAULT_ID == null ? id != null : !DEFAULT_ID.equals(id);
			case CBPPackage.SESSION__TIME:
				return DEFAULT_TIME == null ? time != null : !DEFAULT_TIME.equals(time);
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", time: ");
		result.append(time);
		result.append(')');
		return result.toString();
	}
}