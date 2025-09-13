package loader.src;

import blockchain.src.BlockChainServiceFactory;
import blockchain.src.BlockChainServiceImpl;
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
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Loader {

    public static void main(String[] args) throws FactoryConfigurationError, IOException, CertificateException, InvalidKeyException {
        cbp2map();
    }

    private static void cbp2map() throws FactoryConfigurationError, IOException, CertificateException, InvalidKeyException {
        NewCBPXMLResourceImpl cbpxmlResource = (NewCBPXMLResourceImpl) new CBPXMLResourceFactory().createResource(URI.createFileURI("BPMN2-smaller.cbpxml"));
        System.out.println("Computing...");
        long parsingStart = System.currentTimeMillis();
        ChangeEventsMap changeEventsMap = cbpxmlResource.replayEvents(new FileInputStream(new File("BPMN2-smaller.cbpxml")));
        long parsingEnd = System.currentTimeMillis();
        System.out.println("PARSING TIME: the parsing took "  + (parsingEnd - parsingStart) + "ms (milliseconds)");

        System.out.println("Parsing visualization: ");
        Map<StartNewSessionEvent, List<ChangeEvent>> result = changeEventsMap.getChangeEvents();

        //test and debug
        System.out.println("Session(s): " + result.size());
        for(Map.Entry<StartNewSessionEvent, List<ChangeEvent>> entry : result.entrySet()) {
            System.out.println("Session ID: " + entry.getKey().getSessionId() + ", Events: " + entry.getValue());
        }
        mapInBlockchain(changeEventsMap);
    }

    public static void mapInBlockchain(ChangeEventsMap changeEventsMap) throws CertificateException, IOException, InvalidKeyException {
        Scanner scanner = new Scanner(System.in);
        BlockChainServiceImpl blockChainService = new BlockChainServiceFactory().createService();
        List<ChangeEvent> eventBlockList = null;

        long savingStart = System.currentTimeMillis();
        blockChainService.saveModelToBlockchain(changeEventsMap);
        long savingEnd = System.currentTimeMillis();
        System.out.println("BLOCKCHAIN TIME: saving the model to the BlockChain took " + (savingEnd - savingStart) + "ms (milliseconds)");

        System.out.println("MODEL SAVED IN BLOCKCHAIN: the model has been saved in blockchain");
        if(changeEventsMap.getChangeEvents().size() == 1) {
            System.out.println("The model contains only 1 entry");
            eventBlockList = blockChainService.getBlock("block1");
            if(eventBlockList != null) {
                System.out.println("CHANGE EVENT LIST: this is the list of event(s) stored in the data of the Block");
                System.out.print(eventBlockList);
            } else {
                System.out.print("ERROR: event list null");
            }
        }  else if(changeEventsMap.getChangeEvents().isEmpty()) {
            System.out.println("The model has no entry to select from");
        } else {
            System.out.println("Model length: " + changeEventsMap.getChangeEvents().size());
            System.out.println("Enter the ID of the Block you want to read: the Blocks have id's from 1 to " + (changeEventsMap.getChangeEvents().size()));
            System.out.print(":> ");
            int blockChoice = scanner.nextInt();
            if (blockChoice < 0 || blockChoice > changeEventsMap.getChangeEvents().size()) {
                System.out.println("ILLEGAL ARGUMENT: the Block ID has to be in the range 1 to " + (changeEventsMap.getChangeEvents().size()));
            } else {
                eventBlockList = blockChainService.getBlock("block" + (blockChoice));
                if (eventBlockList != null) {
                    System.out.print("The data of the Block with the ID " + (blockChoice) + " has been taken");
                    System.out.println("CHANGE EVENT LIST: this is the list of event(s) stored in the data of the Block");
                    System.out.print(eventBlockList);
                } else {
                    System.out.print("ERROR: event list null");
                }
            }
        }
    }
}