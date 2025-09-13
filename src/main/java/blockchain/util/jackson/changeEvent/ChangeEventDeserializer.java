package blockchain.util.jackson;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.ChangeEventAdapter;
import cbp.src.event.RegisterEPackageEvent;
import cbp.src.event.StartNewSessionEvent;
import cbp.src.resource.CBPResource;
import cbp.src.resource.NewCBPXMLResourceImpl;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;
import org.bouncycastle.pkcs.jcajce.JcaPKCS12SafeBagBuilder;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import javax.json.Json;
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

        if(rootNode.get("type").asText().equals("session")) {
            String sessionId = rootNode.get("session-id").asText();
            String sessionTimestamp = rootNode.get("timestamp").asText();

            StartNewSessionEvent session = new StartNewSessionEvent(sessionId, sessionTimestamp);
        } else {
            throw new RuntimeException("ERROR: session not present in the JSON node");
        }

        JsonNode eventsListNode = rootNode.get("events");
        List<ChangeEvent> eventsList = new ArrayList<>();

        if(eventsListNode != null && eventsListNode.isArray()) {
            for (JsonNode eNode : eventsListNode) {
                String eventType = eNode.get("type").asText();
                ChangeEvent event = null;

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

                    }
                    break;

                    case "cancel": {

                    }
                    break;

                    case "add-to-resource": {

                    }
                    break;

                    case "remove-from-resource": {

                    }
                    break;

                    case "add-to-ereference": {

                    }
                    break;

                    case "remove-from-ereference": {

                    }
                    break;

                    case "set-eattribute": {

                    }
                    break;

                    case "set-ereference": {

                    }
                    break;

                    case "unset-eattribute": {

                    }
                    break;

                    case "unset-ereference": {

                    }
                    break;

                    case "add-to-eattribute": {

                    }
                    break;

                    case "remove-from-eattribute": {

                    }
                    break;

                    case "move-in-ereference": {

                    }
                    break;

                    case "move-in-eattribute": {

                    }
                    break;

                    case "delete": {

                    }
                    break;
                }
            }
        }


        return new ChangeEventsMap(map, new HashMap<>());
    }
}
