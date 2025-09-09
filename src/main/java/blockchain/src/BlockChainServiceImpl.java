package blockchain.src;

import blockchain.util.ChangeEventMapSerializer;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;

import java.util.List;
import java.util.Map;

public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public List<String> serializeChangeEventMap(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap) {
        return ChangeEventMapSerializer.changeEventMapToStringList(eventListMap);
    }

    @Override
    public void saveModelToBlockchain(ChangeEventsMap map) {
        List<String> changeEventStringList = this.serializeChangeEventMap(map.getChangeEvents());
        for(String s : changeEventStringList) {
            //TODO for loop to add every String as a Block in the blockchain
        }
    }

    @Override
    public List<ChangeEvent> getBlock(String sessionId) {
        return null;
        //TODO seatchin for the block wit the specified session ID
    }
}