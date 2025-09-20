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
        StartNewSessionEvent session = null;

        JsonNode rootNode = jasonParser.getCodec().readTree(jasonParser);

        JsonNode sessionNode = null;
        if(rootNode.get("session") == null) {
            /*
            System.out.println("ROOTNODE: " + rootNode.toPrettyString());
            System.out.println("\t - DEBUG: sessionNode null");
            //session only event
            System.out.println("\t - DEBUG: deserializing a session only JSON aaaaaaaaaaaaaaaaaa");
             */
            String sessionID = rootNode.get("session-id").asText();
            String sessionTimeStamp = rootNode.get("timestamp").asText();

            //System.out.println("sessionID: " + sessionID);
            //System.out.println("timestamp: " + sessionTimeStamp);

            session = new StartNewSessionEvent(sessionID, sessionTimeStamp);

            map.put(session, null);

        } else {
            sessionNode = rootNode.get("session");

            if(sessionNode.get("type").asText().equals("session")) {
                String sessionId = sessionNode.get("session-id").asText();
                String sessionTimestamp = sessionNode.get("timestamp").asText();

                session = new StartNewSessionEvent(sessionId, sessionTimestamp);

                map.put(session, new ArrayList<>());
                System.out.println("\t - DESERIALIZING: deserialing a session only branch");
            } else {
                throw new RuntimeException("ERROR: session not present in the JSON node");
            }

            JsonNode eventsListNode = rootNode.get("events");
            List<ChangeEvent> eventsList = new ArrayList<>();

            if(eventsListNode != null && eventsListNode.isArray()) {
                ChangeEvent<?> event = null;
                for (JsonNode eNode : eventsListNode) {
                    String eventType = eNode.get("type").asText();

                    switch (eventType) {
                        case "register": {
                            String packageName = eNode.get("epackage-name").asText();
                            EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
                            ePackage.setName(packageName);
                            event = new RegisterEPackageEvent(ePackage, new ChangeEventAdapter(new NewCBPXMLResourceImpl()));
                            event.setName(packageName);
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
                            JsonNode valueNode = eNode.get("value");
                            if(valueNode != null) {
                                ev1.setValueId(valueNode.asText());
                                ev1.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev1;
                        }
                        break;

                        case "remove-from-resource": {
                            RemoveFromResourceEvent ev2 = new RemoveFromResourceEvent();
                            JsonNode valueNode = eNode.get("value");
                            if(valueNode != null) {
                                ev2.setValueId(valueNode.asText());
                                ev2.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev2;
                        }
                        break;

                        case "add-to-ereference": {
                            AddToEReferenceEvent ev3 = new AddToEReferenceEvent();
                            ev3.setName(eNode.get("name").asText());
                            ev3.setPosition(eNode.get("position").asInt());
                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev3.setTargetId(targetNode.asText());
                                ev3.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev3.setValueId(valueNode.asText());
                                ev3.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev3;
                        }
                        break;

                        case "remove-from-ereference": {
                            RemoveFromEReferenceEvent ev4 = new RemoveFromEReferenceEvent();
                            ev4.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev4.setTargetId(targetNode.asText());
                                ev4.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev4.setValueId(valueNode.asText());
                                ev4.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev4;
                        }
                        break;

                        case "set-eattribute": {
                            SetEAttributeEvent ev5 = new SetEAttributeEvent();
                            ev5.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev5.setTargetId(targetNode.asText());
                                ev5.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev5.setValueId(valueNode.asText());
                                ev5.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev5;
                        }
                        break;

                        case "set-ereference": {
                            SetEReferenceEvent ev6 = new SetEReferenceEvent();
                            ev6.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev6.setTargetId(targetNode.asText());
                                ev6.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev6.setValueId(valueNode.asText());
                                ev6.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev6;
                        }
                        break;

                        case "unset-eattribute": {
                            UnsetEAttributeEvent ev7 = new UnsetEAttributeEvent();
                            ev7.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            if(targetNode != null) {
                                ev7.setTargetId(targetNode.asText());
                                ev7.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            event = ev7;
                        }
                        break;

                        case "unset-ereference": {
                            UnsetEReferenceEvent ev8 = new UnsetEReferenceEvent();
                            ev8.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            if(targetNode != null) {
                                ev8.setTargetId(targetNode.asText());
                                ev8.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            event = ev8;
                        }
                        break;

                        case "add-to-eattribute": {
                            AddToEAttributeEvent ev9 = new AddToEAttributeEvent();
                            ev9.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev9.setTargetId(targetNode.asText());
                                ev9.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev9.setValueId(valueNode.asText());
                                ev9.setValue(eObjectMap.get(valueNode.asText()));
                            };
                            event = ev9;
                        }
                        break;

                        case "remove-from-eattribute": {
                            RemoveFromEAttributeEvent ev10 = new RemoveFromEAttributeEvent();
                            ev10.setName(eNode.get("name").asText());

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev10.setTargetId(targetNode.asText());
                                ev10.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev10.setValueId(valueNode.asText());
                                ev10.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev10;
                        }
                        break;

                        case "move-in-ereference": {
                            MoveWithinEReferenceEvent ev11 = new MoveWithinEReferenceEvent();
                            ev11.setName(eNode.get("name").asText());

                            JsonNode fromNode = eNode.get("from");
                            if(fromNode != null) {
                                ev11.setFromPosition(fromNode.asInt());
                            }

                            JsonNode toNode = eNode.get("to");
                            if(toNode != null) {
                                ev11.setToPosition(toNode.asInt());
                            }

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev11.setTargetId(targetNode.asText());
                                ev11.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev11.setValueId(valueNode.asText());
                                ev11.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev11;
                        }
                        break;

                        case "move-in-eattribute": {
                            MoveWithinEAttributeEvent ev12 = new MoveWithinEAttributeEvent();
                            ev12.setName(eNode.get("name").asText());

                            JsonNode fromNode = eNode.get("from");
                            if(fromNode != null) {
                                ev12.setFromPosition(fromNode.asInt());
                            }

                            JsonNode toNode = eNode.get("to");
                            if(toNode != null) {
                                ev12.setToPosition(toNode.asInt());
                            }

                            JsonNode targetNode = eNode.get("target-id");
                            JsonNode valueNode = eNode.get("value");
                            if(targetNode != null) {
                                ev12.setTargetId(targetNode.asText());
                                ev12.setTarget(eObjectMap.get(targetNode.asText()));
                            }
                            if(valueNode != null) {
                                ev12.setValueId(valueNode.asText());
                                ev12.setValue(eObjectMap.get(valueNode.asText()));
                            }
                            event = ev12;
                        }
                        break;

                        case "delete": {
                            String id = eNode.get("id").asText();
                            EClass eClass = eObjectMap.get(id);
                            event = new DeleteEObjectEvent(eClass, new NewCBPXMLResourceImpl(), id);
                        }
                        break;

                        default: {
                            throw new RuntimeException("ERROR: unrecognised element in the JSON data");
                        }
                    }
                    if(event != null && session != null) {
                        map.get(session).add(event);
                    } else {
                        throw new RuntimeException("ERROR: event deserialization error occurred");
                    }
                }
                return new ChangeEventsMap(map, eObjectMap);
            } else {
                return new ChangeEventsMap(null, null);
            }
        }
        //System.out.println(map);
        return new ChangeEventsMap(map, eObjectMap);
    }
}