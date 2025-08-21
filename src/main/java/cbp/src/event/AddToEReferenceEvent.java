package cbp.src.event;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class AddToEReferenceEvent extends MultiValueEReferenceEvent implements EObjectValuesEvent {

    @Override
    @SuppressWarnings("unchecked")
    public void replay() {
        List<EObject> list = (List<EObject>) target.eGet(getEStructuralFeature());
        if (list instanceof BasicEList.UnmodifiableEList) {
            return;
        }
        if (position > list.size()) {
            position = list.size();
        }
        list.addAll(position, this.getValues());
    }

    @Override
    public <U> U accept(IChangeEventVisitor<U> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ChangeEvent<?> reverse() {
        RemoveFromEReferenceEvent event = new RemoveFromEReferenceEvent();
        event.setEStructuralFeature(this.getEStructuralFeature());
        event.setValues(this.getValues());
        event.setOldValues(this.getOldValue());
        event.setPosition(this.getPosition());
        event.setTarget(this.getTarget());
        event.setComposite(this.getComposite());
        return event;
    }
}