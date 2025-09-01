package cbp.src.dto;

import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import org.eclipse.emf.ecore.EClass;

import java.util.List;
import java.util.Map;

public class ChangeEventsMap {

    private Map<StartNewSessionEvent, List<ChangeEvent>> changeEvents;
    private Map<String, EClass> eObjectMap;

    public ChangeEventsMap(Map<StartNewSessionEvent, List<ChangeEvent>> changeEvents, Map<String, EClass> eObjectMap) {
        super();
        this.changeEvents = changeEvents;
        this.eObjectMap = eObjectMap;
    }

    public Map<StartNewSessionEvent, List<ChangeEvent>> getChangeEvents() {
        return this.changeEvents;
    }

    public Map<String, EClass> geteObjectMap() {
        return this.eObjectMap;
    }

    public void setChangeEvents(Map<StartNewSessionEvent, List<ChangeEvent>> changeEvents) {
        this.changeEvents = changeEvents;
    }

    public void seteObjectMap(Map<String, EClass> eObjectMap) {
        this.eObjectMap = eObjectMap;
    }
}