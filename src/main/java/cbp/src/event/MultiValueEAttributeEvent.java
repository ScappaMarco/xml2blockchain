package cbp.src.event;

public class MultiValueEAttributeEvent extends EAttributeEvent {

    @Override
    public void replay() {
    }

    @Override
    public <U> U accept(IChangeEventVisitor<U> visitor) {
        return null;
    }
}