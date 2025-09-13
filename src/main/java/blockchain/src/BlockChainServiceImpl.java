package blockchain.src;

import blockchain.util.ChangeEventMapSerializer;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import org.eclipse.emf.ecore.xmi.impl.XMIHandler;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import wallet.WalletManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public List<String> serializeChangeEventMap(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap) {
        return ChangeEventMapSerializer.changeEventMapToStringList(eventListMap);
    }

    @Override
    public void saveModelToBlockchain(ChangeEventsMap map) throws IOException {
        try {
            Contract contract = this.fabricConnect("/home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            long serializationStart = System.currentTimeMillis();
            List<String> changeEventStringList = this.serializeChangeEventMap(map.getChangeEvents());
            long serializationEnd = System.currentTimeMillis();
            System.out.println("SERIALIZATION TIME: the serialization of the model took " + (serializationEnd - serializationStart) + "ms (milliseconds");

            int i = 1;
            //System.out.println(changeEventStringList);
            System.out.println("Number of serialized session(s): " + changeEventStringList.size());
            for(String s : changeEventStringList) {
                String blockId = "block" + (i);
                i++;
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
    public List<ChangeEvent> getBlock(String blockId) {
        List<ChangeEvent> resultArrayList = null;

        try {
            Contract contract = this.fabricConnect("/home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            byte[] resultByteArray = contract.evaluateTransaction("ReadBlock", blockId);

            if(resultByteArray == null || resultByteArray.length == 0) {
                throw new RuntimeException("ERROR: no block found fot this ID: " + blockId);
            }
            String json = new String(resultByteArray);
            System.out.print("BLOCK DATA: this is the data of the specified Block: " + json);

            //deserialization...
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }

        return resultArrayList;
    }

    private Contract fabricConnect(String networkConfigStringPAth) throws IOException {
        this.inizializeWallet("src/main/resources/wallet",
                "/home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore",
                "/home/marco/Desktop//fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/cert.pem");
        try {
            // /home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
            return FabricConnector.connect(networkConfigStringPAth);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: fabric network connection error occurred");
        }
    }

    private void inizializeWallet(String walletPathString, String keyPathString, String certPathString) throws IOException {
        Path walletPath= Paths.get(walletPathString);
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
            System.out.println("Wallet manager initialization was already done");
        }
    }
}