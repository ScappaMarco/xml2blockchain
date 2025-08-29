package cbp.src.event;

import cbp.src.resource.CBPResource;
import metamodel.src.cbp.AddToEReference;
import metamodel.src.cbp.RemoveFromEReference;
import metamodel.src.cbp.RemoveFromResource;
import metamodel.src.cbp.impl.RemoveFromEReferenceImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.DanglingHREFException;

import java.util.*;

public class ChangeEventAdapter extends EContentAdapter {

    protected Notification previusNotification;
    protected List<ChangeEvent<?>> changeEvents = new ArrayList<ChangeEvent<?>>();
    protected boolean enabled = true;
    protected HashSet<EPackage> ePackages = new HashSet<EPackage>();
    protected CBPResource resource = null;

    protected String compositeId = null;
    Set<EObject> tempDeletedObjects = new HashSet<>();

    public boolean isEnabled() {
        return enabled;
    }

    public HashSet<EPackage> getePackages() {
        return ePackages;
    }

    public CBPResource getResource() {
        return resource;
    }

    public List<ChangeEvent<?>> getChangeEvents() {
        return changeEvents;
    }

    public ChangeEventAdapter(CBPResource resource) {
        this.resource = resource;
    }

    @Override
    public void notifyChanged(Notification n) {
        super.notifyChanged(n);

        if(n.isTouch() || !enabled) {
            return;
        }

        if(n.getFeature() != null) {
            EStructuralFeature feature = (EStructuralFeature) n.getFeature();
            if(!feature.isChangeable() || feature.isDerived()) {
                return;
            }
        }

        if(n.getNewValue() != null && n.getNewValue() instanceof DanglingHREFException) {
            return;
        }
        if(n.getOldValue() != null || n.getOldValue() instanceof DanglingHREFException) {
            return;
        }

        ChangeEvent<?> event = null;

        switch (n.getEventType()) {

            case Notification.ADD: {
                if(n.getNotifier() instanceof Resource) {
                    if(n.getNewValue() != null && n.getNewValue() instanceof EObject) {
                        event = new AddToResourceEvent();
                        event.setComposite(compositeId);
                    }
                } else if(n.getNotifier() instanceof EObject) {
                    if(n.getFeature() != null) {
                        EStructuralFeature feature = (EStructuralFeature) n.getFeature();
                        if(feature instanceof EAttribute) {
                            event = new AddToEAttributeEvent();
                            event.setComposite(compositeId);
                        } else if(n.getFeature() instanceof EReference) {
                            event = new SetEReferenceEvent();
                            event.setComposite(compositeId);
                        }
                    } else {
                        event = new AddToResourceEvent();
                        event.setComposite(compositeId);
                    }
                }
                event.setValues(n.getNewValue());
                if(n.getOldValue() != null) {
                    event.setOldValues(n.getOldValue());
                }
                break;
            }

            case Notification.UNSET: {
                if(n.getNotifier() instanceof EObject) {
                    EStructuralFeature feature = (EStructuralFeature) n.getFeature();
                    if(feature instanceof EAttribute) {
                        event = new UnsetEAttributeEvent();
                        event.setComposite(compositeId);
                        event.setOldValues(n.getOldValue());
                    } else if(feature instanceof EReference) {
                        event = new UnsetEReferenceEvent();
                        event.setComposite(compositeId);
                        event.setOldValues(n.getOldValue());
                    }
                }
                break;
            }

            case Notification.SET: {
                if(n.getNotifier() instanceof EObject) {
                    EStructuralFeature feature = (EStructuralFeature) n.getFeature();
                    if(n.getNewValue() != null) {
                        if(feature instanceof EAttribute) {
                            event = new SetEAttributeEvent();
                            event.setComposite(compositeId);
                        } else if(feature instanceof EReference) {
                            EReference opposite = ((EReference) feature).getEOpposite();
                            if(opposite == null || !opposite.isMany() || !opposite.isChangeable()) {
                                event = new SetEReferenceEvent();
                                event.setComposite(compositeId);
                            }
                        }
                        if(event != null) {
                            event.setValues(n.getNewValue());
                            event.setOldValues(n.getOldValue());
                        }
                    } else {
                        if(feature instanceof EAttribute) {
                            event = new UnsetEAttributeEvent();
                            event.setComposite(compositeId);
                            event.setOldValues(n.getOldValue());
                        } else if(feature instanceof  EReference) {
                            event = new UnsetEReferenceEvent();
                            event.setComposite(compositeId);
                            event.setOldValues(n.getOldValue());
                        }
                    }
                }
                break;
            }

            case Notification.ADD_MANY: {
                Collection<Object> values = (Collection<Object>) n.getNewValue();
                int position = n.getPosition();
                for(Object value : values) {
                    ChangeEvent<?> evt = null;
                    if(n.getNotifier() instanceof Resource) {
                        evt = new AddToResourceEvent();
                        evt.setComposite(compositeId);
                    } else if(n.getNotifier() instanceof EObject) {
                        if(n.getFeature() instanceof EAttribute) {
                            evt = new AddToEAttributeEvent();
                            evt.setComposite(compositeId);
                        } else if(n.getFeature() instanceof EReference) {
                            evt = new AddToEReferenceEvent();
                            evt.setComposite(compositeId);
                        }
                    }
                    if(evt != null) {
                        evt.setValues(value);
                        addEventToList(evt, n, position++);
                    }
                }
                break;
            }

            case Notification.REMOVE: {
                if(n.getOldValue() instanceof EObject) {
                    if(n.getNotifier() instanceof Resource) {
                        event = new RemoveFromResourceEvent();
                        event.setComposite(compositeId);
                    } else if(n.getNotifier() instanceof EObject) {
                        event = new RemoveFromEReferenceEvent();
                        event.setComposite(compositeId);
                    }
                } else if(n.getFeature() instanceof EAttribute) {
                    event = new RemoveFromEAttributeEvent();
                    event.setComposite(compositeId);
                }
                event.setValues(n.getOldValue());
                break;
            }

            case Notification.REMOVE_MANY: {
                Collection<Object> values = (Collection<Object>) n.getOldValue();
                int position = n.getPosition();
                for(Object value : values) {
                    if(value instanceof DanglingHREFException) {
                        continue;
                    }
                    ChangeEvent<?> evt = null;
                    if(n.getNotifier() instanceof Resource) {
                        evt = new RemoveFromResourceEvent();
                        evt.setComposite(compositeId);
                    } else if(n.getNotifier() instanceof EObject) {
                        if(n.getFeature() instanceof EAttribute) {
                            evt = new RemoveFromEAttributeEvent();
                            evt.setComposite(compositeId);
                        } else if(n.getFeature() instanceof EReference) {
                            evt = new RemoveFromEReferenceEvent();
                            evt.setComposite(compositeId);
                        }
                    }
                    evt.setValues(value);
                    addEventToList(evt, n, position++);
                }
                break;
            }

            case Notification.MOVE: {
                if(n.getNotifier() instanceof EObject) {
                    FromPositionEvent fromPositionEvent = null;
                    if(n.getFeature() instanceof EAttribute) {
                        MoveWithinEAttributeEvent moveWithinEAttributeEvent = new MoveWithinEAttributeEvent();
                        moveWithinEAttributeEvent.setComposite(compositeId);
                        fromPositionEvent = moveWithinEAttributeEvent;
                        event = moveWithinEAttributeEvent;
                        event.setValues(n.getNewValue());
                    } else if(n.getFeature() instanceof EReference) {
                        MoveWithinEReferenceEvent moveWithinEReferenceEvent = new MoveWithinEReferenceEvent();
                        moveWithinEReferenceEvent.setComposite(compositeId);
                        fromPositionEvent = moveWithinEReferenceEvent;
                        event = moveWithinEReferenceEvent;
                        event.setValues(n.getNewValue());
                    }
                    if(fromPositionEvent != null) {
                        fromPositionEvent.setFromPosition(((Number) n.getOldValue()).intValue());
                    }
                }
                break;
            }

            default: {
                System.err.println("EventAdapter: Unhandled notification!" + n.toString());
                break;
            }
        }
        this.addEventToList(event, n);

        previusNotification = n;
    }

    public void addEventToList(ChangeEvent<?> event, Notification n) {
        this.addEventToList(event, n, -1);
    }

    public void addEventToList(ChangeEvent<?> event, Notification n, int position) {
        if(event instanceof  SetEReferenceEvent || event instanceof AddToEReference) {
            EStructuralFeature feature = (EStructuralFeature) n.getFeature();
            if(!((EObject) n.getNotifier()).eIsSet(feature)) {
                return;
            }
        }
        if(event instanceof EObjectValuesEvent) {
            if(((EObjectValuesEvent) event).getValues().isEmpty()) {
            } else {
                for(EObject obj : ((EObjectValuesEvent) event).getValues()) {
                    this.handleEPackageOf(obj);
                    if(n.getNotifier() instanceof Resource && (Resource) n.getNotifier() == obj.eResource()) {
                        handleCreateEObject(obj);
                    } else if(n.getNotifier() instanceof EObject && ((EObject) n.getNotifier()).eResource() == obj.eResource()) {
                        handleCreateEObject(obj);
                    }
                }
            }
        }
        if(event instanceof EStructuralFeatureEvent<?>) {
            ((EStructuralFeatureEvent<?>) event).setEStructuralFeature((EStructuralFeature) n.getFeature());
            ((EStructuralFeatureEvent<?>) event).setTarget(n.getNotifier());
        }
        if(event != null) {
            if(position > 0) {
                event.setPosition(position);
            } else {
                event.setPosition(n.getPosition());
            }

            if(event instanceof SetEReferenceEvent) {
                if(n.getOldValue() instanceof EObject || n.getOldValue() instanceof EList) {
                    if(n.getOldValue() instanceof EObject) {
                        this.handleDeletedEObject(event, (EObject) n.getOldValue());
                    } else if(n.getOldValue() instanceof EList) {
                        this.handleDeletedEObject(event, (EObject) event.getValue());
                    }
                } else {
                    changeEvents.add(event);
                }
            } else if(!(event instanceof UnsetEReferenceEvent) && !(event instanceof RemoveFromResource) && !(event instanceof RemoveFromEReferenceEvent)) {
                changeEvents.add(event);
            }
        }
        if(event instanceof SetEReferenceEvent || event instanceof  AddToEReferenceEvent || event instanceof AddToEReferenceEvent) {
            if(n.getNewValue() instanceof ArrayList<?>) {
                ArrayList<EObject> list = (ArrayList<EObject>) n.getNewValue();
                for(EObject oj : list) {
                    if(tempDeletedObjects.contains(oj)) {
                        tempDeletedObjects.remove(oj);
                    }
                }
            } else if(n.getNewValue() instanceof EObject && tempDeletedObjects.contains((EObject) n.getNewValue())) {
                tempDeletedObjects.remove((EObject) n.getNewValue());
            }
        }
        if(n.getOldValue() instanceof EObject || n.getOldValue() instanceof EList) {
            if(event instanceof UnsetEReferenceEvent || event instanceof RemoveFromResourceEvent || event instanceof RemoveFromEReferenceEvent) {
                if(n.getOldValue() instanceof EObject) {
                    EObject oldValue = (EObject) n.getOldValue();
                    if(!(tempDeletedObjects.contains(oldValue))) {
                        handleDeletedEObject(event, oldValue);
                    } else if(event instanceof RemoveFromEReferenceEvent) {
                    }
                } else if(n.getOldValue() instanceof EList) {
                    if(!(tempDeletedObjects.contains((EObject) event.getValue()))) {
                        this.handleDeletedEObject(event, (EObject) event.getValue());
                    }
                }
            }
        }
    }

    private void handleOppositeReference(ChangeEvent<?> event) {
        if(event instanceof SetEReferenceEvent || event instanceof AddToEReferenceEvent) {
            EReference eOpposite = ((EReferenceEvent) event).getEReference().getEOpposite();
            if(eOpposite != null) {
                EObject eTarget = ((EReferenceEvent) event).getTarget();
                EObject eValue = null;
                if(event instanceof AddToEReferenceEvent) {
                    eValue = (EObject) event.getValue();
                } else if(event instanceof SetEReferenceEvent) {
                    eValue = (EObject) event.getValue();
                }
                if(eOpposite.isMany()) {
                    EList<EObject> list = ((EList<EObject>) eValue.eGet(eOpposite));
                    AddToEReferenceEvent e = new AddToEReferenceEvent();
                    e.setComposite(compositeId);
                    e.setEStructuralFeature(eOpposite);
                    e.setValue(eTarget);
                    e.setTarget(eValue);
                    e.setPosition(list.size());
                    changeEvents.add(e);
                } else {
                    SetEReferenceEvent e = new SetEReferenceEvent();
                    e.setComposite(compositeId);
                    e.setEStructuralFeature(eOpposite);
                    e.setValue(eTarget);
                    e.setTarget(eValue);
                    changeEvents.add(e);
                }
            }
        }
    }

    protected void unsetOppositeEReference(ChangeEvent<?> event, EObject removedObject) {
        EObject eTarget = null;
        EReference eReference = ((EReferenceEvent) event).getEReference();
        EReference eOpposite = eReference.getEOpposite();
        if (eOpposite != null) {
            if (event instanceof RemoveFromEReferenceEvent) {
                eTarget = ((RemoveFromEReferenceEvent) event).getTarget();
            } else if (event instanceof UnsetEReferenceEvent) {
                eTarget = ((UnsetEReferenceEvent) event).getTarget();
            }
            if (eOpposite.isMany()) {
                int position = ((EList<EObject>) removedObject.eGet(eOpposite)).indexOf(eTarget);
                ((EList<EObject>) removedObject.eGet(eOpposite)).remove(eTarget);

                RemoveFromEReferenceEvent e = new RemoveFromEReferenceEvent();
                e.setComposite(compositeId);
                e.setEStructuralFeature(eOpposite);
                e.setValue(eTarget);
                e.setTarget(removedObject);
                e.setPosition(position);
                changeEvents.add(e);
            } else {
                removedObject.eUnset(eOpposite);

                UnsetEReferenceEvent e = new UnsetEReferenceEvent();
                e.setComposite(compositeId);
                e.setEStructuralFeature(eOpposite);
                e.setOldValue(eTarget);
                e.setTarget(removedObject);
                changeEvents.add(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void handleCreateEObject(EObject obj) {
        if (!resource.isRegistered(obj)) {
            ChangeEvent<?> event = new CreateEObjectEvent(obj, resource.register(obj));
            event.setComposite(compositeId);
            changeEvents.add(event);

            for (EAttribute eAttr : obj.eClass().getEAllAttributes()) {
                if (eAttr.isChangeable() && obj.eIsSet(eAttr) && !eAttr.isDerived()) {
                    if (eAttr.isMany()) {
                        Collection<?> values = (Collection<?>) obj.eGet(eAttr);
                        int i = 0;
                        for (Object value : values) {
                            AddToEAttributeEvent e = new AddToEAttributeEvent();
                            event.setComposite(compositeId);
                            e.setEStructuralFeature(eAttr);
                            e.setValue(value);
                            e.setTarget(obj);
                            e.setPosition(i++);
                            changeEvents.add(e);
                        }
                    } else {
                        Object value = obj.eGet(eAttr);
                        SetEAttributeEvent e = new SetEAttributeEvent();
                        event.setComposite(compositeId);
                        e.setEStructuralFeature(eAttr);
                        e.setValue(value);
                        e.setTarget(obj);
                        changeEvents.add(e);
                    }
                }
            }
            for (EReference eRef : obj.eClass().getEAllReferences()) {

                if (eRef.isChangeable() && obj.eIsSet(eRef) && !eRef.isDerived()) {
                    if (eRef.getEOpposite() != null && eRef.getEOpposite().isMany() && eRef.getEOpposite().isChangeable()) {
                        continue;
                    }

                    if (eRef.isMany()) {
                        Collection<EObject> values = (Collection<EObject>) obj.eGet(eRef);
                        int i = 0;
                        for (EObject value : values) {
                            if (value.eResource() == obj.eResource()) {
                                handleCreateEObject(value);
                            }

                            AddToEReferenceEvent e = new AddToEReferenceEvent();
                            e.setComposite(compositeId);
                            e.setEStructuralFeature(eRef);
                            e.setValue(value);
                            e.setTarget(obj);
                            e.setPosition(i++);
                            changeEvents.add(e);
                            // this.addToModelHistory(e, -1);
                        }
                    } else {
                        EObject value = (EObject) obj.eGet(eRef);
                        if (value.eResource() == obj.eResource()) {
                            handleCreateEObject(value);
                        }
                        SetEReferenceEvent e = new SetEReferenceEvent();
                        e.setComposite(compositeId);
                        e.setEStructuralFeature(eRef);
                        e.setValue(value);
                        e.setTarget(obj);
                        changeEvents.add(e);
                    }
                }
            }
        }
    }

    public void handleEPackageOf(EObject eObject) {
        EPackage ePackage = eObject.eClass().getEPackage();
        if(!ePackages.contains(ePackage)) {
            ePackages.add(ePackage);
            ChangeEvent<?> event = new RegisterEPackageEvent(ePackage, this);
            event.setComposite(compositeId);
            changeEvents.add(event);
        }
    }

    public void handleDeletedEObject(ChangeEvent<?> event, EObject removedObject) {
        this.handleDeletedEObject(event, removedObject, null, null);
    }

    public void handleDeletedEObject(ChangeEvent<?> event, EObject removedObject, Object parent, Object feature) {
        if(removedObject.eResource() == null) {
            Boolean localComposite = null;
            if(compositeId == null) {
                localComposite = true;
                this.startCompositeOperation();
            }
            event.setComposite(compositeId);
            Set<EObject> visitedObjects = new HashSet<>();
            this.removedContainedObjects(removedObject, visitedObjects, true);
            visitedObjects.clear();
            this.unsetAllEFeatures(removedObject, visitedObjects, true);

            changeEvents.add(event);

            String id = resource.getURIFragment(removedObject);
            ChangeEvent<?> deletedEvent = new DeleteEObjectEvent(removedObject, id);
            deletedEvent.setComposite(compositeId);
            changeEvents.add(deletedEvent);

            tempDeletedObjects.add((EObject) removedObject);

            if (localComposite != null && localComposite == true) {
                endCompositeOperation();
            }

        } else {
            changeEvents.add(event);
        }
    }

    public void setEnabled(boolean bool) {
        this.enabled = bool;
    }

    public void handleStartNewSession(String id) {
        changeEvents.add(new StartNewSessionEvent(id));
    }

    public void handleStartNewSession() {
        changeEvents.add(new StartNewSessionEvent());
    }

    protected void removedContainedObjects(EObject target, Set<EObject> visitedObjects, boolean isRoot) {
        visitedObjects.add(target);
        for(EReference ref : target.eClass().getEAllContainments()) {
            if(target.eIsSet(ref) && ref.isChangeable() && !(ref.isDerived())) {
                if(ref.isMany()) {
                    EList<EObject> values = (EList<EObject>) target.eGet(ref);
                    while(values.size() > 0) {
                        int position = values.size() - 1;
                        EObject value = values.get(position);

                        if(visitedObjects.contains(value)) {
                            continue;
                        }
                        this.removedContainedObjects(value, visitedObjects, false);
                        this.unsetAllEFeatures(value, visitedObjects, false);

                        enabled = false;
                        if(values.size() > 0) {
                            values.remove(position);
                        }
                        enabled = true;

                        RemoveFromEReferenceEvent e = new RemoveFromEReferenceEvent();
                        e.setComposite(compositeId);
                        e.setEStructuralFeature(ref);
                        e.setValue(value);
                        e.setTarget(target);
                        e.setPosition(position);
                        changeEvents.add(e);

                        if(value.eContainer() == null) {
                            String id = resource.getURIFragment(value);
                            ChangeEvent<?> deletedEvent = new DeleteEObjectEvent(value, id);
                            deletedEvent.setComposite(compositeId);
                            changeEvents.add(deletedEvent);
                        }
                    }
                } else {
                    if(ref.isUnsettable()) {
                        EObject value = (EObject) target.eGet(ref);
                        if(value != null) {
                            if(visitedObjects.contains(value)) {
                                continue;
                            }
                            this.removedContainedObjects(value, visitedObjects, false);
                            this.unsetAllEFeatures(value, visitedObjects, false);

                            EReference opposite = ((EReference) ref).getEOpposite();
                            if(opposite == null || !(opposite.isMany()) || !(opposite.isChangeable())) {
                                enabled = false;
                                target.eUnset(ref);
                                enabled = true;

                                UnsetEReferenceEvent e = new UnsetEReferenceEvent();
                                e.setComposite(compositeId);
                                e.setEStructuralFeature(ref);
                                e.setValue(value);
                                e.setTarget(target);
                                changeEvents.add(e);

                                if(value.eContainer() == null) {
                                    String id = resource.getURIFragment(value);
                                    ChangeEvent<?> deletedEvent = new DeleteEObjectEvent(value, id);
                                    deletedEvent.setComposite(compositeId);
                                    changeEvents.add(deletedEvent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void unsetAllEFeatures(EObject target, Set<EObject> visitedObjects, boolean isRoot) {
        for(EAttribute att : target.eClass().getEAllAttributes()) {
            if(target.eIsSet(att) && att.isChangeable() && !(att.isDerived())) {
                if(att.isMany()) {
                    EList<Object> values = (EList<Object>) target.eGet(att);
                    while(values.size() > 0) {
                        int position = values.size() - 1;
                        enabled = false;
                        Object value = values.remove(position);
                        enabled = true;

                        RemoveFromEAttributeEvent e = new RemoveFromEAttributeEvent();
                        e.setComposite(compositeId);
                        e.setEStructuralFeature(att);
                        e.setValue(value);
                        e.setTarget(target);
                        e.setPosition(position);
                        changeEvents.add(e);
                    }
                } else {
                    if(!(att.isUnsettable())) {
                        Object value = target.eGet(att);
                        if(value != null) {
                            enabled = false;
                            target.eUnset(att);
                            enabled = true;

                            UnsetEAttributeEvent e = new UnsetEAttributeEvent();
                            e.setComposite(compositeId);
                            e.setEStructuralFeature(att);
                            e.setOldValue(value);
                            e.setTarget(target);
                            changeEvents.add(e);
                        }
                    }
                }
            }
        }
        for(EReference ref : target.eClass().getEAllReferences()) {
            if(!(ref.isContainment()) && target.eIsSet(ref) && ref.isChangeable() && !(ref.isDerived())) {
                if(ref.isMany()) {
                    EList<EObject> values = (EList<EObject>) target.eGet(ref);
                    while(values.size() > 0) {
                        int position = values.size() - 1;
                        enabled = false;
                        EObject value = values.remove(position);
                        enabled = false;

                        RemoveFromEReferenceEvent e = new RemoveFromEReferenceEvent();
                        e.setComposite(compositeId);
                        e.setEStructuralFeature(ref);
                        e.setValue(value);
                        e.setTarget(target);
                        e.setPosition(position);
                        changeEvents.add(e);

                        if(value.eResource() == null) {
                            removedContainedObjects(value, visitedObjects, false);
                        }
                    }
                } else {
                    if(!(ref.isUnsettable())) {
                        EObject value = (EObject) target.eGet(ref);
                        if(value != null) {
                            EReference opposite = ((EReference) ref).getEOpposite();
                            if(opposite == null || opposite.isMany() || !(opposite.isChangeable())) {
                                enabled = false;
                                target.eUnset(ref);
                                enabled = true;

                                UnsetEReferenceEvent e = new UnsetEReferenceEvent();
                                e.setComposite(compositeId);
                                e.setEStructuralFeature(ref);
                                e.setOldValue(value);
                                e.setTarget(target);
                                changeEvents.add(e);

                                if(value.eResource() == null) {
                                    removedContainedObjects(value, visitedObjects, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void unsetAllAttributes(EObject target) {
        for(EAttribute att : target.eClass().getEAllAttributes()) {
            if(att.isChangeable() && target.eIsSet(att) && !(att.isDerived())) {
                if(att.isMany()) {
                    EList<?> values = (EList<?>) target.eGet(att);
                    while(values.size() > 0) {
                        values.remove(values.size() - 1);
                    }
                } else {
                    Object value = target.eGet(att);
                    target.eUnset(att);
                }
            }
        }
    }

    public void startCompositeOperation() {
        compositeId = EcoreUtil.generateUUID();
    }

    public void endCompositeOperation() {
        compositeId = null;
    }

    public void deleteElement(EObject target, Set<EObject> visitedObjects) {
        recursiveDeleteEvent(target, visitedObjects);
        removeFromExternalRefferences(target);
        unsetAllEFeatures(target, visitedObjects, false);
        unsetAllAttributes(target);
    }

    private void recursiveDeleteEvent(EObject target, Set<EObject> visitedObjects) {
        for (EReference eRef : target.eClass().getEAllReferences()) {
            if (eRef.isChangeable() && target.eIsSet(eRef) && !eRef.isDerived() && eRef.isContainment()) {
                if (eRef.isMany()) {
                    List<EObject> values = (List<EObject>) target.eGet(eRef);
                    while (values.size() > 0) {
                        EObject value = values.get(values.size() - 1);
                        recursiveDeleteEvent(value, visitedObjects);
                        removeFromExternalRefferences(value);
                        unsetAllEFeatures(value, visitedObjects, false);
                        unsetAllAttributes(value);
                        values.remove(value);
                    }
                } else {
                    EObject value = (EObject) target.eGet(eRef);
                    if (value != null) {
                        recursiveDeleteEvent(value, visitedObjects);
                        removeFromExternalRefferences(value);
                        unsetAllEFeatures(value, visitedObjects, false);
                        unsetAllAttributes(value);
                        target.eUnset(eRef);
                    }
                }
            }
        }
    }

    private void removeFromExternalRefferences(EObject refferedEObject) {
        Iterator<EObject> iterator = resource.getAllContents();
        while (iterator.hasNext()) {
            EObject refferingEObject = iterator.next();
            for (EReference eRef : refferingEObject.eClass().getEAllReferences()) {
                if (eRef.isContainment() == false && eRef.isChangeable() == true) {
                    if (eRef.isMany()) {
                        List<EObject> valueList = (List<EObject>) refferingEObject.eGet(eRef);
                        if (!(valueList instanceof BasicEList.UnmodifiableEList<?>)) {
                            valueList.remove(refferedEObject);
                        }
                    } else {
                        EObject value = (EObject) refferingEObject.eGet(eRef);
                        if (value != null && value.equals(refferedEObject)) {
                            refferingEObject.eUnset(eRef);
                        }
                    }
                }
            }
        }
    }
}