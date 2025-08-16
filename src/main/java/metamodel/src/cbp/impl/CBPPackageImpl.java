package metamodel.src.cbp.impl;

import metamodel.src.cbp.*;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

public class CBPPackageImpl extends EPackageImpl implements CBPPackage {

    private EClass sessionEClass = null;

    private EClass registerEClass = null;

    private EClass createEClass = null;

    private EClass deleteEClass = null;

    private EClass addToResourceEClass = null;

    private EClass removeFromResourceEClass = null;

    private EClass valueEClass = null;

    private EClass setEAttributeEClass = null;

    private EClass unsetEAttributeEClass = null;

    private EClass setEReferenceEClass = null;

    private EClass unsetEReferenceEClass = null;

    private EClass addToEAttributeEClass = null;

    private EClass removeFromEAttributeEClass = null;

    private EClass moveInEAttributeEClass = null;

    private EClass addToEReferenceEClass = null;

    private EClass removeFromEReferenceEClass = null;

    private EClass moveInEReferenceEClass = null;

    CBPPackageImpl() {
        super(eNS_URI, CBPFactory.eINSTANCE);
    }

    private static boolean isInited = false;

    public static CBPPackage init() {
        if (isInited) return (CBPPackage)EPackage.Registry.INSTANCE.getEPackage(CBPPackage.eNS_URI);

        // Obtain or create and register package
        CBPPackageImpl theCBPPackage = (CBPPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CBPPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CBPPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theCBPPackage.createPackageContents();

        // Initialize created meta-data
        theCBPPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCBPPackage.freeze();


        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CBPPackage.eNS_URI, theCBPPackage);
        return theCBPPackage;
    }

    public EClass getSession() {
        return sessionEClass;
    }

    public EAttribute getSession_Id() {
        return (EAttribute)sessionEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getSession_Time() {
        return (EAttribute)sessionEClass.getEStructuralFeatures().get(1);
    }

    public EClass getRegister() {
        return registerEClass;
    }

    public EAttribute getRegister_Epackage() {
        return (EAttribute)registerEClass.getEStructuralFeatures().get(0);
    }

    public EClass getCreate() {
        return createEClass;
    }

    public EAttribute getCreate_Eclass() {
        return (EAttribute)createEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getCreate_Epackage() {
        return (EAttribute)createEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getCreate_Id() {
        return (EAttribute)createEClass.getEStructuralFeatures().get(2);
    }

    public EClass getDelete() {
        return deleteEClass;
    }

    public EAttribute getDelete_Eclass() {
        return (EAttribute)deleteEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getDelete_Epackage() {
        return (EAttribute)deleteEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getDelete_Id() {
        return (EAttribute)deleteEClass.getEStructuralFeatures().get(2);
    }

    public EClass getAddToResource() {
        return addToResourceEClass;
    }

    public EAttribute getAddToResource_Position() {
        return (EAttribute)addToResourceEClass.getEStructuralFeatures().get(0);
    }

    public EReference getAddToResource_Value() {
        return (EReference)addToResourceEClass.getEStructuralFeatures().get(1);
    }

    public EClass getRemoveFromResource() {
        return removeFromResourceEClass;
    }

    public EReference getRemoveFromResource_Value() {
        return (EReference)removeFromResourceEClass.getEStructuralFeatures().get(0);
    }

    public EClass getValue() {
        return valueEClass;
    }

    public EAttribute getValue_Literal() {
        return (EAttribute)valueEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getValue_Eobject() {
        return (EAttribute)valueEClass.getEStructuralFeatures().get(1);
    }

    public EClass getSetEAttribute() {
        return setEAttributeEClass;
    }

    public EAttribute getSetEAttribute_Name() {
        return (EAttribute)setEAttributeEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getSetEAttribute_Target() {
        return (EAttribute)setEAttributeEClass.getEStructuralFeatures().get(1);
    }

    public EReference getSetEAttribute_Value() {
        return (EReference)setEAttributeEClass.getEStructuralFeatures().get(2);
    }

    public EClass getUnsetEAttribute() {
        return unsetEAttributeEClass;
    }

    public EAttribute getUnsetEAttribute_Name() {
        return (EAttribute)unsetEAttributeEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getUnsetEAttribute_Target() {
        return (EAttribute)unsetEAttributeEClass.getEStructuralFeatures().get(1);
    }

    public EClass getSetEReference() {
        return setEReferenceEClass;
    }

    public EAttribute getSetEReference_Name() {
        return (EAttribute)setEReferenceEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getSetEReference_Target() {
        return (EAttribute)setEReferenceEClass.getEStructuralFeatures().get(1);
    }

    public EReference getSetEReference_Value() {
        return (EReference)setEReferenceEClass.getEStructuralFeatures().get(2);
    }

    public EClass getUnsetEReference() {
        return unsetEReferenceEClass;
    }

    public EAttribute getUnsetEReference_Name() {
        return (EAttribute)unsetEReferenceEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getUnsetEReference_Target() {
        return (EAttribute)unsetEReferenceEClass.getEStructuralFeatures().get(1);
    }

    public EClass getAddToEAttribute() {
        return addToEAttributeEClass;
    }

    public EAttribute getAddToEAttribute_Name() {
        return (EAttribute)addToEAttributeEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getAddToEAttribute_Position() {
        return (EAttribute)addToEAttributeEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getAddToEAttribute_Target() {
        return (EAttribute)addToEAttributeEClass.getEStructuralFeatures().get(2);
    }

    public EReference getAddToEAttribute_Value() {
        return (EReference)addToEAttributeEClass.getEStructuralFeatures().get(3);
    }

    public EClass getRemoveFromEAttribute() {
        return removeFromEAttributeEClass;
    }

    public EAttribute getRemoveFromEAttribute_Name() {
        return (EAttribute)removeFromEAttributeEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getRemoveFromEAttribute_Target() {
        return (EAttribute)removeFromEAttributeEClass.getEStructuralFeatures().get(1);
    }

    public EReference getRemoveFromEAttribute_Value() {
        return (EReference)removeFromEAttributeEClass.getEStructuralFeatures().get(2);
    }

    public EClass getMoveInEAttribute() {
        return moveInEAttributeEClass;
    }

    public EAttribute getMoveInEAttribute_From() {
        return (EAttribute)moveInEAttributeEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getMoveInEAttribute_To() {
        return (EAttribute)moveInEAttributeEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getMoveInEAttribute_Name() {
        return (EAttribute)moveInEAttributeEClass.getEStructuralFeatures().get(2);
    }

    public EAttribute getMoveInEAttribute_Target() {
        return (EAttribute)moveInEAttributeEClass.getEStructuralFeatures().get(3);
    }

    public EReference getMoveInEAttribute_Value() {
        return (EReference)moveInEAttributeEClass.getEStructuralFeatures().get(4);
    }

    public EClass getAddToEReference() {
        return addToEReferenceEClass;
    }

    public EAttribute getAddToEReference_Name() {
        return (EAttribute)addToEReferenceEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getAddToEReference_Position() {
        return (EAttribute)addToEReferenceEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getAddToEReference_Target() {
        return (EAttribute)addToEReferenceEClass.getEStructuralFeatures().get(2);
    }

    public EReference getAddToEReference_Value() {
        return (EReference)addToEReferenceEClass.getEStructuralFeatures().get(3);
    }

    public EClass getRemoveFromEReference() {
        return removeFromEReferenceEClass;
    }

    public EAttribute getRemoveFromEReference_Name() {
        return (EAttribute)removeFromEReferenceEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getRemoveFromEReference_Target() {
        return (EAttribute)removeFromEReferenceEClass.getEStructuralFeatures().get(1);
    }

    public EReference getRemoveFromEReference_Value() {
        return (EReference)removeFromEReferenceEClass.getEStructuralFeatures().get(2);
    }

    public EClass getMoveInEReference() {
        return moveInEReferenceEClass;
    }

    public EAttribute getMoveInEReference_From() {
        return (EAttribute)moveInEReferenceEClass.getEStructuralFeatures().get(0);
    }

    public EAttribute getMoveInEReference_To() {
        return (EAttribute)moveInEReferenceEClass.getEStructuralFeatures().get(1);
    }

    public EAttribute getMoveInEReference_Name() {
        return (EAttribute)moveInEReferenceEClass.getEStructuralFeatures().get(2);
    }

    public EAttribute getMoveInEReference_Target() {
        return (EAttribute)moveInEReferenceEClass.getEStructuralFeatures().get(3);
    }

    public EReference getMoveInEReference_Value() {
        return (EReference)moveInEReferenceEClass.getEStructuralFeatures().get(4);
    }

    public CBPFactory getCBPFactory() {
        return (CBPFactory)getEFactoryInstance();
    }

    private boolean isCreated = false;

    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        sessionEClass = createEClass(SESSION);
        createEAttribute(sessionEClass, SESSION__ID);
        createEAttribute(sessionEClass, SESSION__TIME);

        registerEClass = createEClass(REGISTER);
        createEAttribute(registerEClass, REGISTER__EPACKAGE);

        createEClass = createEClass(CREATE);
        createEAttribute(createEClass, CREATE__ECLASS);
        createEAttribute(createEClass, CREATE__EPACKAGE);
        createEAttribute(createEClass, CREATE__ID);

        deleteEClass = createEClass(DELETE);
        createEAttribute(deleteEClass, DELETE__ECLASS);
        createEAttribute(deleteEClass, DELETE__EPACKAGE);
        createEAttribute(deleteEClass, DELETE__ID);

        addToResourceEClass = createEClass(ADD_TO_RESOURCE);
        createEAttribute(addToResourceEClass, ADD_TO_RESOURCE__POSITION);
        createEReference(addToResourceEClass, ADD_TO_RESOURCE__VALUE);

        removeFromResourceEClass = createEClass(REMOVE_FROM_RESOURCE);
        createEReference(removeFromResourceEClass, REMOVE_FROM_RESOURCE__VALUE);

        valueEClass = createEClass(VALUE);
        createEAttribute(valueEClass, VALUE__LITERAL);
        createEAttribute(valueEClass, VALUE__EOBJECT);

        setEAttributeEClass = createEClass(SET_EATTRIBUTE);
        createEAttribute(setEAttributeEClass, SET_EATTRIBUTE__NAME);
        createEAttribute(setEAttributeEClass, SET_EATTRIBUTE__TARGET);
        createEReference(setEAttributeEClass, SET_EATTRIBUTE__VALUE);

        unsetEAttributeEClass = createEClass(UNSET_EATTRIBUTE);
        createEAttribute(unsetEAttributeEClass, UNSET_EATTRIBUTE__NAME);
        createEAttribute(unsetEAttributeEClass, UNSET_EATTRIBUTE__TARGET);

        setEReferenceEClass = createEClass(SET_EREFERENCE);
        createEAttribute(setEReferenceEClass, SET_EREFERENCE__NAME);
        createEAttribute(setEReferenceEClass, SET_EREFERENCE__TARGET);
        createEReference(setEReferenceEClass, SET_EREFERENCE__VALUE);

        unsetEReferenceEClass = createEClass(UNSET_EREFERENCE);
        createEAttribute(unsetEReferenceEClass, UNSET_EREFERENCE__NAME);
        createEAttribute(unsetEReferenceEClass, UNSET_EREFERENCE__TARGET);

        addToEAttributeEClass = createEClass(ADD_TO_EATTRIBUTE);
        createEAttribute(addToEAttributeEClass, ADD_TO_EATTRIBUTE__NAME);
        createEAttribute(addToEAttributeEClass, ADD_TO_EATTRIBUTE__POSITION);
        createEAttribute(addToEAttributeEClass, ADD_TO_EATTRIBUTE__TARGET);
        createEReference(addToEAttributeEClass, ADD_TO_EATTRIBUTE__VALUE);

        removeFromEAttributeEClass = createEClass(REMOVE_FROM_EATTRIBUTE);
        createEAttribute(removeFromEAttributeEClass, REMOVE_FROM_EATTRIBUTE__NAME);
        createEAttribute(removeFromEAttributeEClass, REMOVE_FROM_EATTRIBUTE__TARGET);
        createEReference(removeFromEAttributeEClass, REMOVE_FROM_EATTRIBUTE__VALUE);

        moveInEAttributeEClass = createEClass(MOVE_IN_EATTRIBUTE);
        createEAttribute(moveInEAttributeEClass, MOVE_IN_EATTRIBUTE__FROM);
        createEAttribute(moveInEAttributeEClass, MOVE_IN_EATTRIBUTE__TO);
        createEAttribute(moveInEAttributeEClass, MOVE_IN_EATTRIBUTE__NAME);
        createEAttribute(moveInEAttributeEClass, MOVE_IN_EATTRIBUTE__TARGET);
        createEReference(moveInEAttributeEClass, MOVE_IN_EATTRIBUTE__VALUE);

        addToEReferenceEClass = createEClass(ADD_TO_EREFERENCE);
        createEAttribute(addToEReferenceEClass, ADD_TO_EREFERENCE__NAME);
        createEAttribute(addToEReferenceEClass, ADD_TO_EREFERENCE__POSITION);
        createEAttribute(addToEReferenceEClass, ADD_TO_EREFERENCE__TARGET);
        createEReference(addToEReferenceEClass, ADD_TO_EREFERENCE__VALUE);

        removeFromEReferenceEClass = createEClass(REMOVE_FROM_EREFERENCE);
        createEAttribute(removeFromEReferenceEClass, REMOVE_FROM_EREFERENCE__NAME);
        createEAttribute(removeFromEReferenceEClass, REMOVE_FROM_EREFERENCE__TARGET);
        createEReference(removeFromEReferenceEClass, REMOVE_FROM_EREFERENCE__VALUE);

        moveInEReferenceEClass = createEClass(MOVE_IN_EREFERENCE);
        createEAttribute(moveInEReferenceEClass, MOVE_IN_EREFERENCE__FROM);
        createEAttribute(moveInEReferenceEClass, MOVE_IN_EREFERENCE__TO);
        createEAttribute(moveInEReferenceEClass, MOVE_IN_EREFERENCE__NAME);
        createEAttribute(moveInEReferenceEClass, MOVE_IN_EREFERENCE__TARGET);
        createEReference(moveInEReferenceEClass, MOVE_IN_EREFERENCE__VALUE);
    }

    private boolean isInitialized = false;

    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        initEClass(sessionEClass, Session.class, "Session", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSession_Id(), ecorePackage.getEString(), "id", null, 0, 1, Session.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSession_Time(), ecorePackage.getEString(), "time", null, 0, 1, Session.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(registerEClass, Register.class, "Register", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRegister_Epackage(), ecorePackage.getEString(), "epackage", null, 0, 1, Register.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(createEClass, Create.class, "Create", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCreate_Eclass(), ecorePackage.getEString(), "eclass", null, 0, 1, Create.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCreate_Epackage(), ecorePackage.getEString(), "epackage", null, 0, 1, Create.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCreate_Id(), ecorePackage.getEString(), "id", null, 0, 1, Create.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteEClass, Delete.class, "Delete", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDelete_Eclass(), ecorePackage.getEString(), "eclass", null, 0, 1, Delete.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDelete_Epackage(), ecorePackage.getEString(), "epackage", null, 0, 1, Delete.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDelete_Id(), ecorePackage.getEString(), "id", null, 0, 1, Delete.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(addToResourceEClass, AddToResource.class, "AddToResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddToResource_Position(), ecorePackage.getEString(), "position", null, 0, 1, AddToResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAddToResource_Value(), this.getValue(), null, "value", null, 0, 1, AddToResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(removeFromResourceEClass, RemoveFromResource.class, "RemoveFromResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRemoveFromResource_Value(), this.getValue(), null, "value", null, 0, 1, RemoveFromResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(valueEClass, Value.class, "Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getValue_Literal(), ecorePackage.getEString(), "literal", null, 0, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getValue_Eobject(), ecorePackage.getEString(), "eobject", null, 0, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setEAttributeEClass, SetEAttribute.class, "SetEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetEAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, SetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSetEAttribute_Target(), ecorePackage.getEString(), "target", null, 0, 1, SetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSetEAttribute_Value(), this.getValue(), null, "value", null, 0, 1, SetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unsetEAttributeEClass, UnsetEAttribute.class, "UnsetEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnsetEAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, UnsetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getUnsetEAttribute_Target(), ecorePackage.getEString(), "target", null, 0, 1, UnsetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setEReferenceEClass, SetEReference.class, "SetEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetEReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, SetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSetEReference_Target(), ecorePackage.getEString(), "target", null, 0, 1, SetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSetEReference_Value(), this.getValue(), null, "value", null, 0, 1, SetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unsetEReferenceEClass, UnsetEReference.class, "UnsetEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnsetEReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, UnsetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getUnsetEReference_Target(), ecorePackage.getEString(), "target", null, 0, 1, UnsetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(addToEAttributeEClass, AddToEAttribute.class, "AddToEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddToEAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, AddToEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAddToEAttribute_Position(), ecorePackage.getEString(), "position", null, 0, 1, AddToEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAddToEAttribute_Target(), ecorePackage.getEString(), "target", null, 0, 1, AddToEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAddToEAttribute_Value(), this.getValue(), null, "value", null, 0, 1, AddToEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(removeFromEAttributeEClass, RemoveFromEAttribute.class, "RemoveFromEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRemoveFromEAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, RemoveFromEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRemoveFromEAttribute_Target(), ecorePackage.getEString(), "target", null, 0, 1, RemoveFromEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRemoveFromEAttribute_Value(), this.getValue(), null, "value", null, 0, 1, RemoveFromEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(moveInEAttributeEClass, MoveInEAttribute.class, "MoveInEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMoveInEAttribute_From(), ecorePackage.getEString(), "from", null, 0, 1, MoveInEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEAttribute_To(), ecorePackage.getEString(), "to", null, 0, 1, MoveInEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, MoveInEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEAttribute_Target(), ecorePackage.getEString(), "target", null, 0, 1, MoveInEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMoveInEAttribute_Value(), this.getValue(), null, "value", null, 0, 1, MoveInEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(addToEReferenceEClass, AddToEReference.class, "AddToEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddToEReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, AddToEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAddToEReference_Position(), ecorePackage.getEString(), "position", null, 0, 1, AddToEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAddToEReference_Target(), ecorePackage.getEString(), "target", null, 0, 1, AddToEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAddToEReference_Value(), this.getValue(), null, "value", null, 0, 1, AddToEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(removeFromEReferenceEClass, RemoveFromEReference.class, "RemoveFromEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRemoveFromEReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, RemoveFromEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRemoveFromEReference_Target(), ecorePackage.getEString(), "target", null, 0, 1, RemoveFromEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRemoveFromEReference_Value(), this.getValue(), null, "value", null, 0, 1, RemoveFromEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(moveInEReferenceEClass, MoveInEReference.class, "MoveInEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMoveInEReference_From(), ecorePackage.getEString(), "from", null, 0, 1, MoveInEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEReference_To(), ecorePackage.getEString(), "to", null, 0, 1, MoveInEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, MoveInEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMoveInEReference_Target(), ecorePackage.getEString(), "target", null, 0, 1, MoveInEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMoveInEReference_Value(), this.getValue(), null, "value", null, 0, 1, MoveInEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        createResource(eNS_URI);
    }
}