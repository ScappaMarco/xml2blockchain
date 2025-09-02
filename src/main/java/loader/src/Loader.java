package loader.src;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import cbp.src.resource.CBPXMLResourceFactory;
import cbp.src.resource.NewCBPXMLResourceImpl;
import org.eclipse.emf.common.util.URI;

import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Loader {

    public static void main(String[] args) throws FactoryConfigurationError, IOException {
        cbp2map();
    }

    private static void cbp2map() throws FactoryConfigurationError, IOException {
        NewCBPXMLResourceImpl cbpxmlResource = (NewCBPXMLResourceImpl) new CBPXMLResourceFactory().createResource(URI.createFileURI("BPMN2-small.cbpxml"));
        System.out.println("Computing...");
        ChangeEventsMap n = cbpxmlResource.replayEvents(new FileInputStream(new File("BPMN2-small.cbpxml")));

        //test and debug
        /*
        Map<StartNewSessionEvent, List<ChangeEvent>> result = n.getChangeEvents();
        System.out.println("How many sessions? The are " + result.size() + " session");

        for(Map.Entry<StartNewSessionEvent, List<ChangeEvent>> entry : result.entrySet()) {
            System.out.println("Chiave = " + entry.getKey().getSessionId() + ", Valore = " + entry.getValue());
        }

         */
    }
}