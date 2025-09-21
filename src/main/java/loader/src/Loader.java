package loader.src;

import blockchain.src.BlockChainServiceFactory;
import blockchain.src.BlockChainServiceImpl;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import cbp.src.resource.CBPXMLResourceFactory;
import cbp.src.resource.NewCBPXMLResourceImpl;
import org.eclipse.emf.common.util.URI;
import org.fusesource.jansi.Ansi;

import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Loader {

    public static void main(String[] args) throws FactoryConfigurationError, IOException, CertificateException, InvalidKeyException {
        long generalStart = System.currentTimeMillis();
        //System.out.println("starting");
        cbp2map();
        long generalEnd = System.currentTimeMillis();

        System.out.println();
        System.out.println(Ansi.ansi().fgBrightGreen().bold().a("TIME RECORD - GENERAL TIME: " + (generalEnd - generalStart) + "ms (milliseconds)").reset());
        System.out.println();
    }

    private static void cbp2map() throws FactoryConfigurationError, IOException, CertificateException, InvalidKeyException {
        NewCBPXMLResourceImpl cbpxmlResource = (NewCBPXMLResourceImpl) new CBPXMLResourceFactory().createResource(URI.createFileURI("BPMN2.cbpxml"));
        System.out.println("Starting...");
        System.out.println(Ansi.ansi().bold().a("-----PARSING-----").reset());
        long parsingStart = System.currentTimeMillis();
        ChangeEventsMap changeEventsMap = cbpxmlResource.replayEvents(new FileInputStream(new File("BPMN2.cbpxml")));
        long parsingEnd = System.currentTimeMillis();

        System.out.println();
        System.out.println(Ansi.ansi().fgBrightGreen().bold().a("TIME RECORD - PARSING TIME: "  + (parsingEnd - parsingStart) + "ms (milliseconds)").reset());
        System.out.println();

        /*
        System.out.println();
        System.out.println(Ansi.ansi().fgBrightYellow().bold().a("-----PARSING VISUALIZATION----- ").reset());
        Map<StartNewSessionEvent, List<ChangeEvent>> result = changeEventsMap.getChangeEvents();

        //test and debug
        for(Map.Entry<StartNewSessionEvent, List<ChangeEvent>> entry : result.entrySet()) {
            System.out.println(Ansi.ansi().fgBrightYellow().bold().a("\t - Session ID: " + entry.getKey().getSessionId()).reset());
            System.out.print(Ansi.ansi().fgBrightYellow().bold().a("\t - Events: " + entry.getValue()).reset());
        }

         */
        mapInBlockchain(changeEventsMap);
    }

    public static void mapInBlockchain(ChangeEventsMap changeEventsMap) throws CertificateException, IOException, InvalidKeyException {
        Scanner scanner = new Scanner(System.in);
        BlockChainServiceImpl blockChainService = new BlockChainServiceFactory().createService();
        //List<ChangeEvent> eventBlockList = null;
        Map<StartNewSessionEvent, List<ChangeEvent>> returnMap = null;

        long savingStart = System.currentTimeMillis();
        System.out.println(Ansi.ansi().bold().a("-----SAVING IN BLOCKCHAIN-----").reset());
        blockChainService.saveModelToBlockchain(changeEventsMap);
        long savingEnd = System.currentTimeMillis();

        System.out.println();
        System.out.println(Ansi.ansi().bold().fgBrightGreen().a("TIME RECORD - BLOCKCHAIN TIME: " + (savingEnd - savingStart) + "ms (milliseconds)").reset());
        System.out.println();

        System.out.println();
        String modelName = "contract-" + changeEventsMap.toString().hashCode();
        System.out.println(Ansi.ansi().bold().a("-----BLOCKCHAIN BLOCK READING-----").reset());

        long deserializationStart;
        long deserializationEnd;

        System.out.println("\t - READING: we are going to read the first block: block1");
        if(returnMap == null) {
            deserializationStart = System.currentTimeMillis();
            returnMap = blockChainService.getFisrtBlock();
            deserializationEnd = System.currentTimeMillis();

            System.out.println();
            System.out.println(Ansi.ansi().fgBrightGreen().bold().a("TIME RECORD - DESERIALIZATION TIME: " + (deserializationEnd - deserializationStart) + "ms (milliseconds"));
            System.out.println();
        }
        if(returnMap != null) {
            System.out.println(Ansi.ansi().fgBrightGreen().a("\t - SUCCESS: The data of the Block with the ID \"block1\" has been stored in \"returnMap\"" ).reset());
        }
        /*
        if(changeEventsMap.getChangeEvents().size() == 1) {
            System.out.println("\t - The model contains only 1 entry");
            deserializationStart = System.currentTimeMillis();
            returnMap = blockChainService.getBlock("block1");
            deserializationEnd = System.currentTimeMillis();

            System.out.println();
            System.out.println(Ansi.ansi().fgBrightGreen().bold().a("TIME RECORD - DESERIALIZATION TIME: " + (deserializationEnd - deserializationStart) + "ms (milliseconds"));
            System.out.println();

            if(returnMap != null) {
                System.out.println(Ansi.ansi().fgBrightGreen().a("\t - SUCCESS: The data of the Block has been taken").reset());
                //System.out.println("\t - CHANGE EVENT LIST: this is the list of event(s) stored in the data of the Block");
                //System.out.println(returnMap);
            } else {
                System.out.println(Ansi.ansi().bgRed().a("ERROR: event list null").reset());
            }
        }  else if(changeEventsMap.getChangeEvents().isEmpty()) {
            System.out.println(Ansi.ansi().fgBrightRed().a("ATTENTION: The model has no entry to select from").reset());
        } else {
            System.out.println("\t - Model length: " + changeEventsMap.getChangeEvents().size());
            System.out.println("\t Enter the ID of the Block you want to read: the Blocks have id's from 1 to " + (changeEventsMap.getChangeEvents().size()));
            System.out.print("\t :> ");
            int blockChoice = scanner.nextInt();
            if (blockChoice < 0 || blockChoice > changeEventsMap.getChangeEvents().size()) {
                System.out.println(Ansi.ansi().fgBrightRed().a("ILLEGAL ARGUMENT: the Block ID has to be in the range 1 to " + (changeEventsMap.getChangeEvents().size())).reset());
            } else {
                deserializationStart = System.currentTimeMillis();
                returnMap = blockChainService.getBlock("block" + (blockChoice));
                deserializationEnd = System.currentTimeMillis();

                System.out.println();
                System.out.println(Ansi.ansi().fgBrightGreen().bold().a("TIME RECORD - DESERIALIZATION TIME: " + (deserializationEnd - deserializationStart) + "ms (milliseconds"));
                System.out.println();

                if (returnMap != null) {
                    System.out.println(Ansi.ansi().fgBrightGreen().a("\t - SUCCESS: The data of the Block with the ID " + (blockChoice) + " has been stored in \"returnMap\"" ).reset());

                    //System.out.println("\t - CHANGE EVENT LIST: this is the list of event(s) stored in the data of the Block");
                    //System.out.println(returnMap);

                } else {
                    System.out.println(Ansi.ansi().fgBrightRed().a("ERROR: event map null").reset());
                }
            }
        }
        */
    }
}