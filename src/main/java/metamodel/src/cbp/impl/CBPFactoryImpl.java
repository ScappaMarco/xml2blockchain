package metamodel.src.cbp.impl;

import metamodel.src.cbp.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

public class CBPFactoryImpl extends EFactoryImpl implements CBPFactory {

	public static CBPFactory init() {
		try {
			CBPFactory cbpFactory = (CBPFactory) EPackage.Registry.INSTANCE.getEFactory(CBPPackage.eNS_URI);
			if(cbpFactory != null) {
				return  cbpFactory;
			}
		} catch (Exception e) {
			EcorePlugin.INSTANCE.log(e);
		}
		return new CBPFactoryImpl();
	}

	public CBPFactoryImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CBPPackage.SESSION: return createSession();
			case CBPPackage.REGISTER: return createRegister();
			case CBPPackage.CREATE: return createCreate();
			case CBPPackage.DELETE: return createDelete();
			case CBPPackage.ADD_TO_RESOURCE: return createAddToResource();
			case CBPPackage.REMOVE_FROM_RESOURCE: return createRemoveFromResource();
			case CBPPackage.VALUE: return createValue();
			case CBPPackage.SET_EATTRIBUTE: return createSetEAttribute();
			case CBPPackage.UNSET_EATTRIBUTE: return createUnsetEAttribute();
			case CBPPackage.SET_EREFERENCE: return createSetEReference();
			case CBPPackage.UNSET_EREFERENCE: return createUnsetEReference();
			case CBPPackage.ADD_TO_EATTRIBUTE: return createAddToEAttribute();
			case CBPPackage.REMOVE_FROM_EATTRIBUTE: return createRemoveFromEAttribute();
			case CBPPackage.MOVE_IN_EATTRIBUTE: return createMoveInEAttribute();
			case CBPPackage.ADD_TO_EREFERENCE: return createAddToEReference();
			case CBPPackage.REMOVE_FROM_EREFERENCE: return createRemoveFromEReference();
			case CBPPackage.MOVE_IN_EREFERENCE: return createMoveInEReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	@Override
	public Session createSession() {
		SessionImpl s = new SessionImpl();
		return s;
	}

	@Override
	public Register createRegister() {
		return null;
	}

	@Override
	public Create createCreate() {
		return null;
	}

	@Override
	public Delete createDelete() {
		return null;
	}

	@Override
	public AddToResource createAddToResource() {
		return null;
	}

	@Override
	public RemoveFromResource createRemoveFromResource() {
		return null;
	}

	@Override
	public Value createValue() {
		return null;
	}

	@Override
	public SetEAttribute createSetEAttribute() {
		return null;
	}

	@Override
	public UnsetEAttribute createUnsetEAttribute() {
		return null;
	}

	@Override
	public SetEReference createSetEReference() {
		return null;
	}

	@Override
	public UnsetEReference createUnsetEReference() {
		return null;
	}

	@Override
	public AddToEAttribute createAddToEAttribute() {
		return null;
	}

	@Override
	public RemoveFromEAttribute createRemoveFromEAttribute() {
		return null;
	}

	@Override
	public MoveInEAttribute createMoveInEAttribute() {
		return null;
	}

	@Override
	public AddToEReference createAddToEReference() {
		return null;
	}

	@Override
	public RemoveFromEReference createRemoveFromEReference() {
		return null;
	}

	@Override
	public MoveInEReference createMoveInEReference() {
		return null;
	}

	@Override
	public CBPPackage getCBPPackage() {
		return null;
	}
}