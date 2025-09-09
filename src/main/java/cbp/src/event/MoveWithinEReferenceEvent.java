package cbp.src.event;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public class MoveWithinEReferenceEvent extends MultiValueEReferenceEvent implements FromPositionEvent, ToPosition, EObjectValuesEvent{

    private int fromPosition;
    private int toPosition;

    @Override
    public int getFromPosition() {
        return this.fromPosition;
    }

    @Override
    public void setFromPosition(int position) {
        this.fromPosition = position;
    }

    @Override
    public int getToPosition() {
        return this.toPosition;
    }

    @Override
    public void setToPosition(int toPosition) {
        this.toPosition = toPosition;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void replay() {
        EList<EObject> list = (EList<EObject>) target.eGet(this.getEStructuralFeature());
        if (list.size() > 0) {
            if (fromPosition >= list.size()) {
                fromPosition = list.size() - 1;
            }
            if (position >= list.size()) {
                position = list.size() - 1;
            }
            EObject a = this.getValue();
            EObject b = list.get(fromPosition);
            if (b.equals(a)) {
                list.move(position, fromPosition);
            }
        }
    }

    @Override
    public <U> U accept(IChangeEventVisitor<U> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ChangeEvent<?> reverse(){
        MoveWithinEReferenceEvent event = new MoveWithinEReferenceEvent();
        event.setEStructuralFeature(this.getEStructuralFeature());
        event.setValues(this.getValues());
        event.setOldValues(this.getOldValue());
        int temp = this.getPosition();
        event.setPosition(this.getFromPosition());
        event.setFromPosition(temp);
        event.setTarget(this.getTarget());
        event.setComposite(this.getComposite());
        return event;
    }
}