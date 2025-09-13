package blockchain.util.jackson;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class JacksonConfig {

    private static final ObjectMapper mapper = createMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule customSerializers = new SimpleModule();

        customSerializers.addSerializer(ChangeEvent.class, new ChangeEventSerializer());
        customSerializers.addDeserializer(ChangeEventsMap.class, new ChangeEventDeserializer());
        //customSerializers.addSerializer(EObject.class, new EObjectSerializer());

        mapper.registerModule(customSerializers);
        return mapper;
    }
}