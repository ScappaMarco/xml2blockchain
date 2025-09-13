package blockchain.src;

import blockchain.util.ChangeEventMapSerializer;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import wallet.WalletManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public List<String> serializeChangeEventMap(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap) {
        return ChangeEventMapSerializer.changeEventMapToStringList(eventListMap);
    }

    @Override
    public void saveModelToBlockchain(ChangeEventsMap map) throws IOException {
        Path walletPath = Paths.get("src/main/resources/wallet");
        if(Files.list(walletPath).findAny().isEmpty()) {
            System.out.println("The Wallet manager is being initialized");
            Path keyPath = Files.list(Paths.get("/home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore"))
                    .findFirst().orElseThrow(() -> new RuntimeException("No KEY found in /keystore directory"));

            WalletManager manager = new WalletManager(
                    walletPath.toString(),
                    "/home/marco/Desktop//fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/cert.pem",
                    keyPath.toString()
                    );
            try {
                manager.initialize();
            } catch (CertificateException ex) {
                ex.printStackTrace();
                throw new RuntimeException("ERROR: wallet manager certificate error occurred");
            } catch (InvalidKeyException exx) {
                exx.printStackTrace();
                throw new RuntimeException("ERROR: wallet manager private key error occurred");
            }
            System.out.println("Fabric Wallet initialized");
        } else {
            System.out.println("Wallet manager initialization done");
        }

        try {
            Contract contract = FabricConnector.connect("/home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            List<String> changeEventStringList = this.serializeChangeEventMap(map.getChangeEvents());
            Properties props = new Properties();

            props.put("allowAllHostNames", "true");
            props.put("trustServerCertificate", "true");

            int i = 0;
            //System.out.println(changeEventStringList);
            System.out.println("Number of serialized session(s): " + changeEventStringList.size());
            for(String s : changeEventStringList) {
                String blockId = "block" + (++i);

                contract.submitTransaction("CreateBlock", blockId, s);

                System.out.println("BLOCK SAVED: " + blockId);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR: fabric network connect error occurred");
        }

        catch (ContractException e) {
            throw new RuntimeException("ERROR: contract error occurred");
        } catch (InterruptedException e) {
            throw new RuntimeException("ERROR: saving interrupted");
        } catch (TimeoutException e) {
            throw new RuntimeException("ERROR: time-out error occurred");
        }


    }

    @Override
    public List<ChangeEvent> getBlock(String sessionId) {
        return null;
        //TODO seatchin for the block wit the specified session ID
    }
}