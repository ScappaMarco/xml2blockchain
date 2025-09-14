package cbp.src.resource;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.*;

public class NewCBPXMLResourceImpl extends CBPResource {

    protected int persistedEvents = 0;
    private boolean optimised;
    private Map<String, EClass> eObjectMap = new HashMap<>();

    public NewCBPXMLResourceImpl() {
        super();
    }

    public NewCBPXMLResourceImpl(URI uri) {
        super(uri);
    }

    public int getPersistedEvents() {
        return this.persistedEvents;
    }

    public void setPersistedEvents(int persistedEvents) {
        this.persistedEvents = persistedEvents;
    }

    public boolean isOptimised() {
        return this.optimised;
    }

    public void setOptimised(boolean optimised) {
        this.optimised = optimised;
    }

    public Map<String, EClass> geteObjectMap() {
        return this.eObjectMap;
    }

    @Override
    public ChangeEventsMap replayEvents(InputStream inputStream) throws FactoryConfigurationError, IOException {
        String errorMessage = null;

        int eventNumber = this.getPersistedEvents();
        try {
            InputStream stream = new ByteArrayInputStream(new byte[0]);
            ByteArrayInputStream header = new ByteArrayInputStream("<?xml version='1.0' encoding='ISO-8859-1' ?>".getBytes());
            ByteArrayInputStream begin = new ByteArrayInputStream("<m>".getBytes());
            ByteArrayInputStream end = new ByteArrayInputStream("</m>".getBytes());

            stream = new SequenceInputStream(stream, header);
            stream = new SequenceInputStream(stream, begin);
            stream = new SequenceInputStream(stream, inputStream);
            stream = new SequenceInputStream(stream, end);

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(stream, "ISO-8859-1");

            ChangeEvent<?> event = null;
            boolean ignore = false;

            //TreeMap with comparator -> guaranteed session order (ordered by session-id, lowest to highest)
            //session-id: BPMN2-0000001
            Map<StartNewSessionEvent, List<ChangeEvent>> eventMap = new TreeMap<>(
                    Comparator.comparingInt(e -> Integer.parseInt(e.getSessionId().split("-")[1]))
            );
            StartNewSessionEvent currentSession = null;

            while (xmlEventReader.hasNext()) {
                event = null;
                XMLEvent xmlEvent = xmlEventReader.nextEvent();

                if(xmlEvent.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement e = xmlEvent.asStartElement();
                    String name = e.getName().getLocalPart();

                    if(name.equals("m")) {
                        continue;
                    }
                    if(!(name.equals("value")) && !(name.equals("old-value"))) {
                        if(this.isOptimised()) {
                            if(ignoreSet.remove(eventNumber)) {
                                //ignoreSet -> CBPResource
                                //eventNumber -> ChangeEventAdapter
                                ignore = true;
                            }
                        }

                        if(!(ignore)) {
                            errorMessage = name;
                            switch (name) {
                                case "session": {
                                    String sessionId = e.getAttributeByName(new QName("id")).getValue();
                                    String sessionTime = e.getAttributeByName(new QName("time")).getValue();
                                    event = new StartNewSessionEvent(sessionId, sessionTime);
                                    currentSession = (StartNewSessionEvent) event;
                                    //System.out.println("Computing a session...");
                                }
                                break;
                                case "register": {
                                    String packageName = e.getAttributeByName(new QName("epackage")).getValue();
                                    errorMessage = errorMessage + ", package: " + packageName;
                                    EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
                                    ePackage.setName(packageName);
                                    event = new RegisterEPackageEvent(ePackage, changeEventAdapter);
                                    //changeEventAdapter -> CBPResource
                                }
                                break;
                                case "cancel": {
                                    int lineToCancelOffset = Integer.parseInt(e.getAttributeByName( new QName("offset")).getValue());
                                    event = new CancelEvent(lineToCancelOffset);
                                }
                                break;
                                case "create": {
                                    String packageName = e.getAttributeByName(new QName("epackage")).getValue();
                                    String className = e.getAttributeByName(new QName("eclass")).getValue();
                                    String id = e.getAttributeByName(new QName("id")).getValue();
                                    errorMessage = errorMessage + ", package: " + packageName + ", class: " + className + ", id: " + id;
                                    EClass clazz = EcoreFactory.eINSTANCE.createEClass();
                                    this.geteObjectMap().put(id, clazz);
                                    clazz.setName(className);
                                    event = new CreateEObjectEvent(clazz, this, id);
                                }
                                break;
                                case "add-to-resource": {
                                    AddToResourceEvent eventino02 = new AddToResourceEvent();
                                    eventino02.setPosition(Integer.parseInt(e.getAttributeByName(new QName("position")).getValue()));

                                    this.setValueId(eventino02, xmlEventReader, "add-to-resource", "eobject");
                                    event = eventino02;
                                }
                                break;
                                case "remove-from-resource": {
                                    RemoveFromResourceEvent eventino11 = new RemoveFromResourceEvent();

                                    this.setValueId(eventino11, xmlEventReader, "remove-from-resource", "eobject");
                                    event = eventino11;
                                }
                                break;
                                case "add-to-ereference": {
                                    AddToEReferenceEvent eventino3 = new AddToEReferenceEvent();
                                    eventino3.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino3.setName(e.getAttributeByName(new QName("name")).getValue().toString());
                                    eventino3.setPosition(Integer.parseInt(e.getAttributeByName(new QName("position")).getValue()));
                                    eventino3.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino3, xmlEventReader, "add-to-ereference", "eobject");
                                    event = eventino3;
                                    //System.out.println("Computing a add-to-ereference...");
                                }
                                break;
                                case "remove-from-ereference": {
                                    RemoveFromEReferenceEvent eventino4 = new RemoveFromEReferenceEvent();
                                    eventino4.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino4.setName(e.getAttributeByName(new QName("name")).getValue().toString());
                                    eventino4.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino4, xmlEventReader, "remove-from-ereference", "eobject");
                                    event = eventino4;
                                }
                                break;
                                case "set-eattribute": {
                                    SetEAttributeEvent eventino = new SetEAttributeEvent();
                                    eventino.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino.setValue(e.getAttributeByName(new QName("name")).getValue().toString());
                                    eventino.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino, xmlEventReader, "set-eattribute", "literal");
                                    event = eventino;
                                    //System.out.println("Computing a set-attribute...");
                                }
                                break;
                                case "set-ereference": {
                                    SetEReferenceEvent eventino2 = new SetEReferenceEvent();
                                    eventino2.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino2.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino2.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino2, xmlEventReader, "set-ereference", "eobject");
                                    event = eventino2;
                                }
                                break;
                                case "unset-eattribute": {
                                    UnsetEAttributeEvent eventino5 = new UnsetEAttributeEvent();
                                    eventino5.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino5.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino5.setTargetId(e.getAttributeByName(new QName("target")).getValue());
                                    event = eventino5;
                                }
                                break;
                                case "unset-ereference": {
                                    UnsetEReferenceEvent eventino6 = new UnsetEReferenceEvent();
                                    eventino6.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino6.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino6.setTargetId(e.getAttributeByName(new QName("target")).getValue());
                                    event = eventino6;
                                }
                                break;
                                case "add-to-eattribute": {
                                    AddToEAttributeEvent eventino7 = new AddToEAttributeEvent();
                                    eventino7.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino7.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino7.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino7, xmlEventReader, "add-to-eattribute", "literal");
                                    event = eventino7;
                                }
                                break;
                                case "remove-from-eattribute": {
                                    RemoveFromEAttributeEvent eventino8 = new RemoveFromEAttributeEvent();
                                    eventino8.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino8.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino8.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino8, xmlEventReader, "remove-from-eattribute", "literal");
                                    event = eventino8;
                                }
                                break;
                                case "move-in-eattribute": {
                                    MoveWithinEAttributeEvent eventino9 = new MoveWithinEAttributeEvent();
                                    eventino9.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino9.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino9.setFromPosition(Integer.parseInt(e.getAttributeByName(new QName("from")).getValue()));
                                    eventino9.setToPosition(Integer.parseInt(e.getAttributeByName(new QName("target")).getValue()));
                                    eventino9.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino9, xmlEventReader, "move-in-eattribute", "literal");
                                    event = eventino9;
                                }
                                break;
                                case "move-in-ereference": {
                                    MoveWithinEReferenceEvent eventino10 = new MoveWithinEReferenceEvent();
                                    eventino10.setTarget(this.geteObjectMap().get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino10.setName(e.getAttributeByName(new QName("name")).getValue());
                                    eventino10.setFromPosition(Integer.parseInt(e.getAttributeByName(new QName("from")).getValue()));
                                    eventino10.setToPosition(Integer.parseInt(e.getAttributeByName(new QName("to")).getValue()));
                                    eventino10.setTargetId(e.getAttributeByName(new QName("target")).getValue());

                                    this.setValueId(eventino10, xmlEventReader, "move-in-ereference", "eobject");
                                    event = eventino10;
                                }
                                break;
                                case "delete": {
                                    String packageName = e.getAttributeByName(new QName("epackage")).getValue();
                                    String className = e.getAttributeByName(new QName("eclass")).getValue();
                                    String id = e.getAttributeByName(new QName("id")).getValue();
                                    errorMessage = errorMessage + ", package: " + packageName + ", class: " + className + ", id: " + id;
                                    EClass eClass = this.geteObjectMap().get(id);
                                    event = new DeleteEObjectEvent(eClass, this, id);
                                }
                                break;
                            }

                            if(event instanceof EStructuralFeatureEvent<?>) {
                                String sTarget = e.getAttributeByName(new QName("target")).getValue();
                                String sName = e.getAttributeByName(new QName("name")).getValue();
                                errorMessage = errorMessage + ", target: " + sTarget + ", name: " + sName;
                                EObject target = getEObject(sTarget);
                                if(target != null) {
                                    EStructuralFeature eStructuralFeature = target.eClass().getEStructuralFeature(sName);
                                    ((EStructuralFeatureEvent<?>) event).setEStructuralFeature(eStructuralFeature);
                                    ((EStructuralFeatureEvent<?>) event).setTarget(target);
                                }
                            } else if(event instanceof ResourceEvent) {
                                ((ResourceEvent) event).setResource(this);
                            }

                            if(event instanceof AddToEAttributeEvent || event instanceof AddToEReferenceEvent || event instanceof AddToResourceEvent) {
                                String sPosition = e.getAttributeByName(new QName("position")).getValue();
                                errorMessage = errorMessage + ", target: " + sPosition;
                                event.setPosition(Integer.parseInt(sPosition));
                            }
                            if(event instanceof FromPositionEvent) {
                                String sTo = e.getAttributeByName(new QName("to")).getValue();
                                errorMessage = errorMessage + ", to: " + sTo;
                                String sFrom = e.getAttributeByName(new QName("from")).getValue();
                                errorMessage = errorMessage + ", from: " + sFrom;
                                event.setPosition(Integer.parseInt(sTo));
                                ((FromPositionEvent) event).setFromPosition(Integer.parseInt(sFrom));
                            }
                        }
                    }
                }
                if (xmlEvent.getEventType() == XMLStreamConstants.END_ELEMENT) {
                    EndElement endElement = xmlEvent.asEndElement();
                    String endName = endElement.getName().getLocalPart();
                    event = null;
                }

                if(event != null) {
                    if(event instanceof StartNewSessionEvent) {
                        currentSession = (StartNewSessionEvent) event;
                        eventMap.put((StartNewSessionEvent) event, new ArrayList<>());
                    } else if(currentSession != null) {
                        eventMap.get(currentSession).add(event);
                    } else {
                        throw new IllegalStateException("No current session is active: the event can not be added");
                    }
                }
            }
            begin.close();
            end.close();
            inputStream.close();
            stream.close();
            xmlEventReader.close();

            return new ChangeEventsMap(eventMap, this.geteObjectMap());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: event number " + eventNumber + " : " + errorMessage);
            throw new IOException("Error: event Number " + eventNumber + " : " + errorMessage + "\n" + e.toString() + "\n");
        }
    }

    private void setValueId(ChangeEvent event, XMLEventReader xmlEventReader, String eventName, String attName) throws XMLStreamException {
        while(xmlEventReader.hasNext()) {
            XMLEvent innerEvent = xmlEventReader.peek();
            if(innerEvent.isStartElement() && innerEvent.asStartElement().getName().getLocalPart().equals("value")) {
                StartElement valueElem = xmlEventReader.nextEvent().asStartElement();
                Attribute eobjectAttribute = valueElem.getAttributeByName(new QName(attName));
                if(eobjectAttribute != null) {
                    String value = eobjectAttribute.getValue();
                    event.setValueId(value);
                    event.setValue(this.geteObjectMap().get(value));
                    break;
                } else {
                    event.setValueId(null);
                    event.setValue(null);
                }
            } else if(innerEvent.isEndElement() && innerEvent.asEndElement().getName().equals(eventName)) {
                break;
            } else {
                xmlEventReader.nextEvent();
            }
        }
    }
}