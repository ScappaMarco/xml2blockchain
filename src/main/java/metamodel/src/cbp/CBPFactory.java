package metamodel.src.cbp;

import org.eclipse.emf.ecore.EFactory;

public interface CBPFactory extends EFactory {

	CBPFactory eINSTANCE = metamodel.src.cbp.impl.CBPFactoryImpl.init();

	Session createSession();
	Register createRegister();
	Create createCreate();
	Delete createDelete();
	AddToResource createAddToResource();
	RemoveFromResource createRemoveFromResource();
	Value createValue();
	SetEAttribute createSetEAttribute();
	UnsetEAttribute createUnsetEAttribute();
	SetEReference createSetEReference();
	UnsetEReference createUnsetEReference();
	AddToEAttribute createAddToEAttribute();
	RemoveFromEAttribute createRemoveFromEAttribute();
	MoveInEAttribute createMoveInEAttribute();
	AddToEReference createAddToEReference();
	RemoveFromEReference createRemoveFromEReference();
	MoveInEReference createMoveInEReference();
	CBPPackage getCBPPackage();
}