package blockchain.src;

import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BlockChainService {

    public List<String> serializeChangeEventMap(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap);
    public void saveModelToBlockchain(ChangeEventsMap map) throws IOException;
    public Map<StartNewSessionEvent, List<ChangeEvent>> getBlock(String sessionId);
    public Map<StartNewSessionEvent, List<ChangeEvent>> getFisrtBlock();
}