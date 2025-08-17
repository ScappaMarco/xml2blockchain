package cbp.src.event;

import java.util.Collection;

public interface PrimitiveValuesEvent {

    public Collection<Object> getValues();
    public Collection<Object> getOldValues();
}