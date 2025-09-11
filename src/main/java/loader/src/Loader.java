package loader.src;

import blockchain.src.BlockChainServiceFactory;
import blockchain.src.BlockChainServiceImpl;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import cbp.src.resource.CBPXMLResourceFactory;
import cbp.src.resource.NewCBPXMLResourceImpl;
import org.eclipse.emf.common.util.URI;
import wallet.WalletManager;

import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

public class Loader {

    public static void main(String[] args) throws FactoryConfigurationError, IOException {
        cbp2map();
        //mapInBlockchain();
    }

    private static void cbp2map() throws FactoryConfigurationError, IOException {
        NewCBPXMLResourceImpl cbpxmlResource = (NewCBPXMLResourceImpl) new CBPXMLResourceFactory().createResource(URI.createFileURI("BPMN2-smaller.cbpxml"));
        System.out.println("Computing...");
        long start = System.currentTimeMillis();
        ChangeEventsMap changeEventsMap = cbpxmlResource.replayEvents(new FileInputStream(new File("BPMN2-smaller.cbpxml")));
        long end = System.currentTimeMillis();
        System.out.println("The parsing took "  + (end - start) + "ms (milliseconds)");

        System.out.println("Parsing visualization: ");
        Map<StartNewSessionEvent, List<ChangeEvent>> result = changeEventsMap.getChangeEvents();

        //test and debug
        System.out.println("Session(s): " + result.size());
        for(Map.Entry<StartNewSessionEvent, List<ChangeEvent>> entry : result.entrySet()) {
            System.out.println("Session ID: " + entry.getKey().getSessionId() + ", Events: " + entry.getValue());
        }

        BlockChainServiceImpl blockChainService = new BlockChainServiceFactory().createService();
        blockChainService.saveModelToBlockchain(changeEventsMap);
    }

    public static void mapInBlockchain(ChangeEventsMap changeEventsMap) throws CertificateException, IOException, InvalidKeyException {

    }
}