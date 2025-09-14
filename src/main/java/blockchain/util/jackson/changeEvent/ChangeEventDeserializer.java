package blockchain.util.jackson.changeEvent;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.*;
import cbp.src.resource.NewCBPXMLResourceImpl;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeEventDeserializer extends JsonDeserializer<ChangeEventsMap> {

    @Override
    public ChangeEventsMap deserialize(JsonParser jasonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        Map<StartNewSessionEvent, List<ChangeEvent>> map = new HashMap<>();
        Map<String, EClass> eObjectMap = new HashMap<>();

        JsonNode rootNode = jasonParser.getCodec().readTree(jasonParser);

        StartNewSessionEvent session = null;

        if(rootNode.get("type").asText().equals("session")) {
            String sessionId = rootNode.get("session-id").asText();
            String sessionTimestamp = rootNode.get("timestamp").asText();

            session = new StartNewSessionEvent(sessionId, sessionTimestamp);

            map.put(session, new ArrayList<>());
        } else {
            throw new RuntimeException("ERROR: session not present in the JSON node");
        }

        JsonNode eventsListNode = rootNode.get("events");
        List<ChangeEvent> eventsList = new ArrayList<>();

        if(eventsListNode != null && eventsListNode.isArray()) {
            ChangeEvent event = null;
            for (JsonNode eNode : eventsListNode) {
                String eventType = eNode.get("type").asText();

                switch (eventType) {
                    case "register": {
                        String packageName = eNode.get("name").asText();
                        event.setName(packageName);
                        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
                        ePackage.setName(packageName);
                        event = new RegisterEPackageEvent(ePackage, new ChangeEventAdapter(new NewCBPXMLResourceImpl()));
                    }
                    break;

                    case "create": {
                        String ePackageName = eNode.get("epackage").asText();
                        String className = eNode.get("eclass-name").asText();
                        String id = eNode.get("id").asText();
                        EClass eclass = EcoreFactory.eINSTANCE.createEClass();
                        eObjectMap.put(id, eclass);
                        eclass.setName(className);
                        event = new CreateEObjectEvent(eclass, id);
                        ((CreateEObjectEvent) event).setePackage(ePackageName);
                    }
                    break;

                    case "cancel": {
                        int offset = eNode.get("offset").asInt();
                        event = new CancelEvent(offset);
                    }
                    break;

                    case "add-to-resource": {
                        AddToResourceEvent ev1 = new AddToResourceEvent();
                        ev1.setPosition(eNode.get("position").asInt());
                        ev1.setValueId(eNode.get("value").asText());
                        ev1.setValue(eObjectMap.get(eNode.get("value").asText()));
                        event = ev1;
                    }
                    break;

                    case "remove-from-resource": {
                        RemoveFromResourceEvent ev2 = new RemoveFromResourceEvent();
                        ev2.setValueId(eNode.get("value").asText());
                        ev2.setValue(eObjectMap.get(eNode.get("value").asText()));
                        event = ev2;

                    }
                    break;

                    case "add-to-ereference": {
                        AddToEReferenceEvent ev3 = new AddToEReferenceEvent();
                        ev3.setName(eNode.get("name").asText());
                        ev3.setPosition(eNode.get("position").asInt());
                        ev3.setTargetId(eNode.get("target-id").asText());
                        ev3.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev3.setValueId(eNode.get("value").asText());
                        ev3.setValue(eObjectMap.get(eNode.get("value").asText()));
                        event = ev3;
                    }
                    break;

                    case "remove-from-ereference": {
                        RemoveFromEReferenceEvent ev4 = new RemoveFromEReferenceEvent();
                        ev4.setName(eNode.get("name").asText());
                        ev4.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev4.setTargetId(eNode.get("target-id").asText());
                        ev4.setValueId(eNode.get("value").asText());
                        ev4.setValue(eObjectMap.get(eNode.get("value").asText()));
                        event = ev4;
                    }
                    break;

                    case "set-eattribute": {
                        SetEAttributeEvent ev5 = new SetEAttributeEvent();
                        ev5.setName(eNode.get("name").asText());
                        ev5.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev5.setTargetId(eNode.get("target-id").asText());
                        ev5.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev5.setValueId(eNode.get("value").asText());
                        event = ev5;
                    }
                    break;

                    case "set-ereference": {
                        SetEReferenceEvent ev6 = new SetEReferenceEvent();
                        ev6.setName(eNode.get("name").asText());
                        ev6.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev6.setTargetId(eNode.get("target-id").asText());
                        ev6.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev6.setValueId(eNode.get("value").asText());
                        event = ev6;
                    }
                    break;

                    case "unset-eattribute": {
                        UnsetEAttributeEvent ev7 = new UnsetEAttributeEvent();
                        ev7.setName(eNode.get("name").asText());
                        ev7.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev7.setTargetId(eNode.get("target-id").asText());
                        event = ev7;
                    }
                    break;

                    case "unset-ereference": {
                        UnsetEReferenceEvent ev8 = new UnsetEReferenceEvent();
                        ev8.setName(eNode.get("name").asText());
                        ev8.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev8.setTargetId(eNode.get("target-id").asText());
                        event = ev8;
                    }
                    break;

                    case "add-to-eattribute": {
                        AddToEAttributeEvent ev9 = new AddToEAttributeEvent();
                        ev9.setName(eNode.get("name").asText());
                        ev9.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev9.setTargetId(eNode.get("target-id").asText());
                        ev9.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev9.setValueId(eNode.get("value").asText());
                        event = ev9;
                    }
                    break;

                    case "remove-from-eattribute": {
                        RemoveFromEAttributeEvent ev10 = new RemoveFromEAttributeEvent();
                        ev10.setName(eNode.get("name").asText());
                        ev10.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev10.setTargetId(eNode.get("target-id").asText());
                        ev10.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev10.setValueId(eNode.get("value").asText());
                        event = ev10;
                    }
                    break;

                    case "move-in-ereference": {
                        MoveWithinEReferenceEvent ev11 = new MoveWithinEReferenceEvent();
                        ev11.setName(eNode.get("name").asText());
                        ev11.setFromPosition(eNode.get("from").asInt());
                        ev11.setToPosition(eNode.get("to").asInt());
                        ev11.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev11.setTargetId(eNode.get("target-id").asText());
                        ev11.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev11.setValueId(eNode.get("value").asText());
                        event = ev11;
                    }
                    break;

                    case "move-in-eattribute": {
                        MoveWithinEAttributeEvent ev12 = new MoveWithinEAttributeEvent();
                        ev12.setName(eNode.get("name").asText());
                        ev12.setFromPosition(eNode.get("from").asInt());
                        ev12.setToPosition(eNode.get("to").asInt());
                        ev12.setTarget(eObjectMap.get(eNode.get("target-id").asText()));
                        ev12.setTargetId(eNode.get("target-id").asText());
                        ev12.setValue(eObjectMap.get(eNode.get("value").asText()));
                        ev12.setValueId(eNode.get("value").asText());
                        event = ev12;
                    }
                    break;

                    case "delete": {
                        String id = eNode.get("id").asText();
                        EClass eClass = eObjectMap.get(id);
                        event = new DeleteEObjectEvent(eClass, new NewCBPXMLResourceImpl(), id);
                    }
                    break;
                }
            }
            if(event != null && session != null) {
                map.get(session).add(event);
            } else {
                throw new RuntimeException("ERROR: event deserialization error occurred");
            }
        }
        return new ChangeEventsMap(map, eObjectMap);
    }
}