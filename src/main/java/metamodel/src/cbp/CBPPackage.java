package metamodel.src.cbp;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * The Package interface for the CBP metamodel (generated-style).
 */
public interface CBPPackage extends EPackage {

	// Basic package info
	String eNAME = "CBP";
	String eNS_URI = "https://github.com/epsilonlabs/emf-cbp/1.0";
	String eNS_PREFIX = "cbp";

	// The singleton instance (implementation class expected in CBP.impl)
	CBPPackage eINSTANCE = metamodel.src.cbp.impl.CBPPackageImpl.init();

	// --- Meta object ids (EClasses) ---
	int SESSION = 0;
	int REGISTER = 1;
	int CREATE = 2;
	int DELETE = 3;
	int ADD_TO_RESOURCE = 4;
	int REMOVE_FROM_RESOURCE = 5;
	int VALUE = 6;
	int SET_EATTRIBUTE = 7;
	int UNSET_EATTRIBUTE = 8;
	int SET_EREFERENCE = 9;
	int UNSET_EREFERENCE = 10;
	int ADD_TO_EATTRIBUTE = 11;
	int REMOVE_FROM_EATTRIBUTE = 12;
	int MOVE_IN_EATTRIBUTE = 13;
	int ADD_TO_EREFERENCE = 14;
	int REMOVE_FROM_EREFERENCE = 15;
	int MOVE_IN_EREFERENCE = 16;

	// --- Feature ids and feature counts per class ---

	/* Session */
	int SESSION__ID = 0;
	int SESSION__TIME = 1;
	int SESSION_FEATURE_COUNT = 2;

	/* Register */
	int REGISTER__EPACKAGE = 0;
	int REGISTER_FEATURE_COUNT = 1;

	/* Create */
	int CREATE__ECLASS = 0;
	int CREATE__EPACKAGE = 1;
	int CREATE__ID = 2;
	int CREATE_FEATURE_COUNT = 3;

	/* Delete */
	int DELETE__ECLASS = 0;
	int DELETE__EPACKAGE = 1;
	int DELETE__ID = 2;
	int DELETE_FEATURE_COUNT = 3;

	/* AddToResource */
	int ADD_TO_RESOURCE__POSITION = 0;
	int ADD_TO_RESOURCE__VALUE = 1;
	int ADD_TO_RESOURCE_FEATURE_COUNT = 2;

	/* RemoveFromResource */
	int REMOVE_FROM_RESOURCE__VALUE = 0;
	int REMOVE_FROM_RESOURCE_FEATURE_COUNT = 1;

	/* Value */
	int VALUE__LITERAL = 0;
	int VALUE__EOBJECT = 1;
	int VALUE_FEATURE_COUNT = 2;

	/* SetEAttribute */
	int SET_EATTRIBUTE__NAME = 0;
	int SET_EATTRIBUTE__TARGET = 1;
	int SET_EATTRIBUTE__VALUE = 2;
	int SET_EATTRIBUTE_FEATURE_COUNT = 3;

	/* UnsetEAttribute */
	int UNSET_EATTRIBUTE__NAME = 0;
	int UNSET_EATTRIBUTE__TARGET = 1;
	int UNSET_EATTRIBUTE_FEATURE_COUNT = 2;

	/* SetEReference */
	int SET_EREFERENCE__NAME = 0;
	int SET_EREFERENCE__TARGET = 1;
	int SET_EREFERENCE__VALUE = 2;
	int SET_EREFERENCE_FEATURE_COUNT = 3;

	/* UnsetEReference */
	int UNSET_EREFERENCE__NAME = 0;
	int UNSET_EREFERENCE__TARGET = 1;
	int UNSET_EREFERENCE_FEATURE_COUNT = 2;

	/* AddToEAttribute */
	int ADD_TO_EATTRIBUTE__NAME = 0;
	int ADD_TO_EATTRIBUTE__POSITION = 1;
	int ADD_TO_EATTRIBUTE__TARGET = 2;
	int ADD_TO_EATTRIBUTE__VALUE = 3;
	int ADD_TO_EATTRIBUTE_FEATURE_COUNT = 4;

	/* RemoveFromEAttribute */
	int REMOVE_FROM_EATTRIBUTE__NAME = 0;
	int REMOVE_FROM_EATTRIBUTE__TARGET = 1;
	int REMOVE_FROM_EATTRIBUTE__VALUE = 2;
	int REMOVE_FROM_EATTRIBUTE_FEATURE_COUNT = 3;

	/* MoveInEAttribute */
	int MOVE_IN_EATTRIBUTE__FROM = 0;
	int MOVE_IN_EATTRIBUTE__TO = 1;
	int MOVE_IN_EATTRIBUTE__NAME = 2;
	int MOVE_IN_EATTRIBUTE__TARGET = 3;
	int MOVE_IN_EATTRIBUTE__VALUE = 4;
	int MOVE_IN_EATTRIBUTE_FEATURE_COUNT = 5;

	/* AddToEReference */
	int ADD_TO_EREFERENCE__NAME = 0;
	int ADD_TO_EREFERENCE__POSITION = 1;
	int ADD_TO_EREFERENCE__TARGET = 2;
	int ADD_TO_EREFERENCE__VALUE = 3;
	int ADD_TO_EREFERENCE_FEATURE_COUNT = 4;

	/* RemoveFromEReference */
	int REMOVE_FROM_EREFERENCE__NAME = 0;
	int REMOVE_FROM_EREFERENCE__TARGET = 1;
	int REMOVE_FROM_EREFERENCE__VALUE = 2;
	int REMOVE_FROM_EREFERENCE_FEATURE_COUNT = 3;

	/* MoveInEReference */
	int MOVE_IN_EREFERENCE__FROM = 0;
	int MOVE_IN_EREFERENCE__TO = 1;
	int MOVE_IN_EREFERENCE__NAME = 2;
	int MOVE_IN_EREFERENCE__TARGET = 3;
	int MOVE_IN_EREFERENCE__VALUE = 4;
	int MOVE_IN_EREFERENCE_FEATURE_COUNT = 5;

	// --- Meta object accessors (metodi richiesti) ---

	/* Session */
	EClass getSession();
	EAttribute getSession_Id();
	EAttribute getSession_Time();

	/* Register */
	EClass getRegister();
	EAttribute getRegister_Epackage();

	/* Create */
	EClass getCreate();
	EAttribute getCreate_Eclass();
	EAttribute getCreate_Epackage();
	EAttribute getCreate_Id();

	/* Delete */
	EClass getDelete();
	EAttribute getDelete_Eclass();
	EAttribute getDelete_Epackage();
	EAttribute getDelete_Id();

	/* AddToResource */
	EClass getAddToResource();
	EAttribute getAddToResource_Position();
	EReference getAddToResource_Value();

	/* RemoveFromResource */
	EClass getRemoveFromResource();
	EReference getRemoveFromResource_Value();

	/* Value */
	EClass getValue();
	EAttribute getValue_Literal();
	EAttribute getValue_Eobject();

	/* SetEAttribute */
	EClass getSetEAttribute();
	EAttribute getSetEAttribute_Name();
	EAttribute getSetEAttribute_Target();
	EReference getSetEAttribute_Value();

	/* UnsetEAttribute */
	EClass getUnsetEAttribute();
	EAttribute getUnsetEAttribute_Name();
	EAttribute getUnsetEAttribute_Target();

	/* SetEReference */
	EClass getSetEReference();
	EAttribute getSetEReference_Name();
	EAttribute getSetEReference_Target();
	EReference getSetEReference_Value();

	/* UnsetEReference */
	EClass getUnsetEReference();
	EAttribute getUnsetEReference_Name();
	EAttribute getUnsetEReference_Target();

	/* AddToEAttribute */
	EClass getAddToEAttribute();
	EAttribute getAddToEAttribute_Name();
	EAttribute getAddToEAttribute_Position();
	EAttribute getAddToEAttribute_Target();
	EReference getAddToEAttribute_Value();

	/* RemoveFromEAttribute */
	EClass getRemoveFromEAttribute();
	EAttribute getRemoveFromEAttribute_Name();
	EAttribute getRemoveFromEAttribute_Target();
	EReference getRemoveFromEAttribute_Value();

	/* MoveInEAttribute */
	EClass getMoveInEAttribute();
	EAttribute getMoveInEAttribute_From();
	EAttribute getMoveInEAttribute_To();
	EAttribute getMoveInEAttribute_Name();
	EAttribute getMoveInEAttribute_Target();
	EReference getMoveInEAttribute_Value();

	/* AddToEReference */
	EClass getAddToEReference();
	EAttribute getAddToEReference_Name();
	EAttribute getAddToEReference_Position();
	EAttribute getAddToEReference_Target();
	EReference getAddToEReference_Value();

	/* RemoveFromEReference */
	EClass getRemoveFromEReference();
	EAttribute getRemoveFromEReference_Name();
	EAttribute getRemoveFromEReference_Target();
	EReference getRemoveFromEReference_Value();

	/* MoveInEReference */
	EClass getMoveInEReference();
	EAttribute getMoveInEReference_From();
	EAttribute getMoveInEReference_To();
	EAttribute getMoveInEReference_Name();
	EAttribute getMoveInEReference_Target();
	EReference getMoveInEReference_Value();

	interface Literals {
		EClass SESSION = eINSTANCE.getSession();
		EAttribute SESSION__ID = eINSTANCE.getSession_Id();
		EAttribute SESSION__TIME = eINSTANCE.getSession_Time();

		EClass REGISTER = eINSTANCE.getRegister();
		EAttribute REGISTER__EPACKAGE = eINSTANCE.getRegister_Epackage();

		EClass CREATE = eINSTANCE.getCreate();
		EAttribute CREATE__ECLASS = eINSTANCE.getCreate_Eclass();
		EAttribute CREATE__EPACKAGE = eINSTANCE.getCreate_Epackage();
		EAttribute CREATE__ID = eINSTANCE.getCreate_Id();

		EClass DELETE = eINSTANCE.getDelete();
		EAttribute DELETE__ECLASS = eINSTANCE.getDelete_Eclass();
		EAttribute DELETE__EPACKAGE = eINSTANCE.getDelete_Epackage();
		EAttribute DELETE__ID = eINSTANCE.getDelete_Id();

		EClass ADD_TO_RESOURCE = eINSTANCE.getAddToResource();
		EAttribute ADD_TO_RESOURCE__POSITION = eINSTANCE.getAddToResource_Position();
		EReference ADD_TO_RESOURCE__VALUE = eINSTANCE.getAddToResource_Value();

		EClass REMOVE_FROM_RESOURCE = eINSTANCE.getRemoveFromResource();
		EReference REMOVE_FROM_RESOURCE__VALUE = eINSTANCE.getRemoveFromResource_Value();

		EClass VALUE = eINSTANCE.getValue();
		EAttribute VALUE__LITERAL = eINSTANCE.getValue_Literal();
		EAttribute VALUE__EOBJECT = eINSTANCE.getValue_Eobject();

		EClass SET_EATTRIBUTE = eINSTANCE.getSetEAttribute();
		EAttribute SET_EATTRIBUTE__NAME = eINSTANCE.getSetEAttribute_Name();
		EAttribute SET_EATTRIBUTE__TARGET = eINSTANCE.getSetEAttribute_Target();
		EReference SET_EATTRIBUTE__VALUE = eINSTANCE.getSetEAttribute_Value();

		EClass UNSET_EATTRIBUTE = eINSTANCE.getUnsetEAttribute();
		EAttribute UNSET_EATTRIBUTE__NAME = eINSTANCE.getUnsetEAttribute_Name();
		EAttribute UNSET_EATTRIBUTE__TARGET = eINSTANCE.getUnsetEAttribute_Target();

		EClass SET_EREFERENCE = eINSTANCE.getSetEReference();
		EAttribute SET_EREFERENCE__NAME = eINSTANCE.getSetEReference_Name();
		EAttribute SET_EREFERENCE__TARGET = eINSTANCE.getSetEReference_Target();
		EReference SET_EREFERENCE__VALUE = eINSTANCE.getSetEReference_Value();

		EClass UNSET_EREFERENCE = eINSTANCE.getUnsetEReference();
		EAttribute UNSET_EREFERENCE__NAME = eINSTANCE.getUnsetEReference_Name();
		EAttribute UNSET_EREFERENCE__TARGET = eINSTANCE.getUnsetEReference_Target();

		EClass ADD_TO_EATTRIBUTE = eINSTANCE.getAddToEAttribute();
		EAttribute ADD_TO_EATTRIBUTE__NAME = eINSTANCE.getAddToEAttribute_Name();
		EAttribute ADD_TO_EATTRIBUTE__POSITION = eINSTANCE.getAddToEAttribute_Position();
		EAttribute ADD_TO_EATTRIBUTE__TARGET = eINSTANCE.getAddToEAttribute_Target();
		EReference ADD_TO_EATTRIBUTE__VALUE = eINSTANCE.getAddToEAttribute_Value();

		EClass REMOVE_FROM_EATTRIBUTE = eINSTANCE.getRemoveFromEAttribute();
		EAttribute REMOVE_FROM_EATTRIBUTE__NAME = eINSTANCE.getRemoveFromEAttribute_Name();
		EAttribute REMOVE_FROM_EATTRIBUTE__TARGET = eINSTANCE.getRemoveFromEAttribute_Target();
		EReference REMOVE_FROM_EATTRIBUTE__VALUE = eINSTANCE.getRemoveFromEAttribute_Value();

		EClass MOVE_IN_EATTRIBUTE = eINSTANCE.getMoveInEAttribute();
		EAttribute MOVE_IN_EATTRIBUTE__FROM = eINSTANCE.getMoveInEAttribute_From();
		EAttribute MOVE_IN_EATTRIBUTE__TO = eINSTANCE.getMoveInEAttribute_To();
		EAttribute MOVE_IN_EATTRIBUTE__NAME = eINSTANCE.getMoveInEAttribute_Name();
		EAttribute MOVE_IN_EATTRIBUTE__TARGET = eINSTANCE.getMoveInEAttribute_Target();
		EReference MOVE_IN_EATTRIBUTE__VALUE = eINSTANCE.getMoveInEAttribute_Value();

		EClass ADD_TO_EREFERENCE = eINSTANCE.getAddToEReference();
		EAttribute ADD_TO_EREFERENCE__NAME = eINSTANCE.getAddToEReference_Name();
		EAttribute ADD_TO_EREFERENCE__POSITION = eINSTANCE.getAddToEReference_Position();
		EAttribute ADD_TO_EREFERENCE__TARGET = eINSTANCE.getAddToEReference_Target();
		EReference ADD_TO_EREFERENCE__VALUE = eINSTANCE.getAddToEReference_Value();

		EClass REMOVE_FROM_EREFERENCE = eINSTANCE.getRemoveFromEReference();
		EAttribute REMOVE_FROM_EREFERENCE__NAME = eINSTANCE.getRemoveFromEReference_Name();
		EAttribute REMOVE_FROM_EREFERENCE__TARGET = eINSTANCE.getRemoveFromEReference_Target();
		EReference REMOVE_FROM_EREFERENCE__VALUE = eINSTANCE.getRemoveFromEReference_Value();

		EClass MOVE_IN_EREFERENCE = eINSTANCE.getMoveInEReference();
		EAttribute MOVE_IN_EREFERENCE__FROM = eINSTANCE.getMoveInEReference_From();
		EAttribute MOVE_IN_EREFERENCE__TO = eINSTANCE.getMoveInEReference_To();
		EAttribute MOVE_IN_EREFERENCE__NAME = eINSTANCE.getMoveInEReference_Name();
		EAttribute MOVE_IN_EREFERENCE__TARGET = eINSTANCE.getMoveInEReference_Target();
		EReference MOVE_IN_EREFERENCE__VALUE = eINSTANCE.getMoveInEReference_Value();
	}
}