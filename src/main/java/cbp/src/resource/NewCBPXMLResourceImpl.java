package cbp.src.resource;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.*;
import org.checkerframework.checker.units.qual.Angle;
import org.checkerframework.checker.units.qual.N;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCBPXMLResourceImpl extends CBPResource {

    protected int persistedEvents = 0;
    private boolean optimised;

    public NewCBPXMLResourceImpl() {
        super();
    }

    public NewCBPXMLResourceImpl(URI uri) {
        super(uri);
    }

    @Override
    public ChangeEventsMap replayEvents(InputStream inputStream) throws FactoryConfigurationError, IOException {
        String errorMessage = null;

        int eventNumber = this.persistedEvents;
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

            Map<String, EClass> eObjectMap = new HashMap<>();
            Map<StartNewSessionEvent, List<ChangeEvent>> eventMap = new HashMap<>();
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
                        if(optimised) {
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
                                    eObjectMap.put(id, clazz);
                                    clazz.setName(className);
                                    event = new CreateEObjectEvent(clazz, this, id);
                                }
                                break;
                                case "add-to-resource": {
                                    event = new AddToResourceEvent();
                                }
                                break;
                                case "remove-from-resource": {
                                    event = new RemoveFromResourceEvent();
                                }
                                break;
                                case "add-to-ereference": {
                                    AddToEReferenceEvent eventino3 = new AddToEReferenceEvent();
                                    eventino3.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino3.setName(e.getAttributeByName(new QName("name")).getValue().toString());
                                    event = eventino3;
                                }
                                break;
                                case "remove-from-ereference": {
                                    RemoveFromEReferenceEvent eventino4 = new RemoveFromEReferenceEvent();
                                    eventino4.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino4.setName(e.getAttributeByName(new QName("name")).getValue().toString());
                                    event = eventino4;
                                }
                                break;
                                case "set-eattribute": {
                                    SetEAttributeEvent eventino = new SetEAttributeEvent();
                                    eventino.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino.setValue(e.getAttributeByName(new QName("name")).getValue().toString());
                                    event = eventino;
                                }
                                break;
                                case "set-ereference": {
                                    SetEReferenceEvent eventino2 = new SetEReferenceEvent();
                                    eventino2.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino2.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino2;
                                }
                                break;
                                case "unset-eattribute": {
                                    UnsetEAttributeEvent eventino5 = new UnsetEAttributeEvent();
                                    eventino5.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino5.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino5;
                                }
                                break;
                                case "unset-ereference": {
                                    UnsetEReferenceEvent eventino6 = new UnsetEReferenceEvent();
                                    eventino6.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino6.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino6;
                                }
                                break;
                                case "add-to-eattribute": {
                                    AddToEAttributeEvent eventino7 = new AddToEAttributeEvent();
                                    eventino7.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino7.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino7;
                                }
                                break;
                                case "remove-from-eattribute": {
                                    RemoveFromEAttributeEvent eventino8 = new RemoveFromEAttributeEvent();
                                    eventino8.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino8.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino8;
                                }
                                break;
                                case "move-in-eattribute": {
                                    MoveWithinEAttributeEvent eventino9 = new MoveWithinEAttributeEvent();
                                    eventino9.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino9.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino9;
                                }
                                break;
                                case "move-in-ereference": {
                                    MoveWithinEReferenceEvent eventino10 = new MoveWithinEReferenceEvent();
                                    eventino10.setTarget(eObjectMap.get(e.getAttributeByName(new QName("target")).getValue().toString()));
                                    eventino10.setName(e.getAttributeByName(new QName("name")).getValue());
                                    event = eventino10;
                                }
                                break;
                                case "delete": {
                                    String packageName = e.getAttributeByName(new QName("epackage")).getValue();
                                    String className = e.getAttributeByName(new QName("eclass")).getValue();
                                    String id = e.getAttributeByName(new QName("id")).getValue();
                                    errorMessage = errorMessage + ", package: " + packageName + ", class: " + className + ", id: " + id;
                                    EClass eClass = eObjectMap.get(id);
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
                        eventMap.put((StartNewSessionEvent) event, new ArrayList<>());
                    } else if(currentSession != null) {
                        eventMap.get(currentSession).add(event);
                    } else {
                        throw new IllegalStateException("No current session is active: the event can not be added");
                    }
                }
            }
            return new ChangeEventsMap(eventMap, eObjectMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: event number " + eventNumber + " : " + errorMessage);
            throw new IOException("Error: Event Number " + eventNumber + " : " + errorMessage + "\n" + e.toString() + "\n");
        }
    }
}