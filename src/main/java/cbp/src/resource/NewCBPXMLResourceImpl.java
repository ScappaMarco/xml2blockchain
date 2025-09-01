package cbp.src.resource;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.*;
import org.checkerframework.checker.units.qual.N;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
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
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

        }

    }
}