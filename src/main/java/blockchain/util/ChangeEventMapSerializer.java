package blockchain.util;

import blockchain.util.jackson.JacksonConfig;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeEventMapSerializer {

    public static List<String> changeEventMapToStringList(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap) {
        List<String> result = new ArrayList<>();
        ObjectMapper mapper = JacksonConfig.getMapper();
        String serializedChangeEvent = null;
        Map<String, Object> wrapper = new HashMap<>();

        for(Map.Entry<StartNewSessionEvent, List<ChangeEvent>> entry : eventListMap.entrySet()) {
            try {
                wrapper.put("session", entry.getKey());
                wrapper.put("events", entry.getValue());

                serializedChangeEvent = mapper.writeValueAsString(wrapper);
                result.add(serializedChangeEvent);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Change Event serializing error: " + entry.getKey().getSessionId());
            }
        }
        return result;
    }
}