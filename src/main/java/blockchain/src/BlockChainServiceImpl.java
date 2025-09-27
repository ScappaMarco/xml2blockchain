package blockchain.src;

import blockchain.src.connect.FabricConnector;
import blockchain.util.ChangeEventMapSerializer;
import blockchain.util.jackson.JacksonConfig;
import cbp.src.dto.ChangeEventsMap;
import cbp.src.event.ChangeEvent;
import cbp.src.event.StartNewSessionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.StatusRuntimeException;
import org.fusesource.jansi.Ansi;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import wallet.WalletManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public List<String> serializeChangeEventMap(Map<StartNewSessionEvent, List<ChangeEvent>> eventListMap) {
        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----SERIALIZATION-----").reset());
        return ChangeEventMapSerializer.changeEventMapToStringList(eventListMap);
    }

    @Override
    public void saveModelToBlockchain(ChangeEventsMap map) throws IOException {
        String blockId = null;
        try {
            //String modelName = String.valueOf(map.toString().hashCode());
            Contract contract = this.fabricConnect("../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            long serializationStart = System.currentTimeMillis();
            List<String> changeEventStringList = this.serializeChangeEventMap(map.getChangeEvents());
            long serializationEnd = System.currentTimeMillis();

            System.out.println();
            System.out.println(Ansi.ansi().bold().fgBrightGreen().a("TIME RECORD - SERIALIZATION TIME: " + (serializationEnd - serializationStart) + "ms (milliseconds)").reset());
            System.out.println();

            int i = 1;
            //System.out.println(changeEventStringList);
            System.out.println("\t - Number of serialized session(s): " + changeEventStringList.size());
            System.out.println();
            System.out.println(Ansi.ansi().bold().a("-----BLOCKCHAIN SAVING-----").reset());

            for(String s : changeEventStringList) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);

                gzipOutputStream.write(s.getBytes("UTF-8"));
                gzipOutputStream.close();

                String compressedData = Base64.getEncoder().encodeToString(baos.toByteArray());
                //System.out.println("LENGHT aaaaaaaaaaaaaaaaa: " + compressedData.length());
                if(compressedData.length() > 2000000) {
                    System.out.println(Ansi.ansi().bgBrightYellow().fgBlack().bold().a("\t - WARNING: the Block with the ID \"block " + i + "\" is going to be split into chunks due to his dimensions").reset());
                    this.saveChunkedBlock(contract, "block" + (i), compressedData);
                } else {
                    this.saveRegularBlock(contract, "block" + (i), compressedData);
                }
                //blockId = "block" + (i);
                i++;

                /*
                try {
                    contract.submitTransaction("CreateBlock", blockId, compressedData);
                    System.out.println(Ansi.ansi().fgBlack().bg(Ansi.Color.BLUE).bold().a("\t - BLOCK SAVED: " + blockId).reset());
                } catch (ContractException e) {
                    if(e.getMessage().contains("already exists") || e.getMessage().contains("exists") || e.getMessage().contains("ALREADY EXiSTS") || e.getMessage().contains("EXISTS")) {
                        System.out.println(Ansi.ansi().bg(Ansi.Color.YELLOW).fgBlack().bold().a("ATTENTION: the block with the ID \"" + blockId + "\" already exists - skipping...").reset());
                    } else {
                        throw new RuntimeException("ERROR: connection contract error occurred");
                    }
                }
                 */
            }
        } catch (IOException e) {
            throw new RuntimeException("ERROR: fabric network connect error occurred");
        }
    }

    private void saveChunkedBlock(Contract contract, String blockID, String data) {
        final int chunkSize = 2000000;
        int totalChunks = (data.length() + chunkSize - 1) / chunkSize;

        for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
            int start = chunkIndex * chunkSize;
            int end = Math.min(start + chunkSize, data.length());

            String chunk = data.substring(start, end);

            String chunkID = blockID + "_chunk_" + chunkIndex;
            try {
                contract.submitTransaction("CreateBlock", chunkID, chunk);
                System.out.println(Ansi.ansi().fgBlack().bg(Ansi.Color.BLUE).bold().a("\t - CHUNKED BLOCK SAVED: " + chunkID).reset());
            } catch (ContractException e) {
                if(e.getMessage().contains("already exists") || e.getMessage().contains("exists") || e.getMessage().contains("ALREADY EXiSTS") || e.getMessage().contains("EXISTS")) {
                    System.out.println(Ansi.ansi().bg(Ansi.Color.YELLOW).fgBlack().bold().a("ATTENTION: the block with the ID \"" + chunkID + "\" already exists - skipping...").reset());
                } else {
                    throw new RuntimeException("ERROR: connection contract error occurred | " + e.getMessage());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException("ERROR: timeout error occurred");
            }
        }

        String metadata = String.format("{\"totalChunks\":%d,\"originalBlockId\":\"%s\"}", totalChunks, blockID);
        try {
            contract.submitTransaction("CreateBlock", blockID + "_meta", metadata);
            System.out.println(Ansi.ansi().fgBlack().bg(Ansi.Color.BLUE).bold().a("\t - METADATA BLOCK SAVED: " + blockID + "_meta").reset());
        } catch (ContractException e) {
            if(e.getMessage().contains("already exists") || e.getMessage().contains("exists") || e.getMessage().contains("ALREADY EXiSTS") || e.getMessage().contains("EXISTS")) {
                System.out.println(Ansi.ansi().bg(Ansi.Color.YELLOW).fgBlack().bold().a("ATTENTION: the block with the ID \"" + blockID + "_meta" + "\" already exists - skipping...").reset());
            } else {
                throw new RuntimeException("ERROR: connection contract error occurred");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException("ERROR: timeout error occurred");
        }
    }

    private void saveRegularBlock(Contract contract, String blockID, String data) {
        try {
            contract.submitTransaction("CreateBlock", blockID, data);
            System.out.println(Ansi.ansi().fgBlack().bg(Ansi.Color.BLUE).bold().a("\t - REGULAR BLOCK SAVED: " + blockID).reset());
        } catch (ContractException e) {
            if(e.getMessage().contains("already exists") || e.getMessage().contains("exists") || e.getMessage().contains("ALREADY EXiSTS") || e.getMessage().contains("EXISTS")) {
                System.out.println(Ansi.ansi().bg(Ansi.Color.YELLOW).fgBlack().bold().a("ATTENTION: the block with the ID \"" + blockID + "\" already exists - skipping...").reset());
            } else {
                throw new RuntimeException("ERROR: connection contract error occurred");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException("ERROR: timeout error occurred");
        }
    }

    @Override
    public Map<StartNewSessionEvent, List<ChangeEvent>> getBlock(String blockId) {
        //List<ChangeEvent> resultArrayList = null;
        ObjectMapper mapper = JacksonConfig.getMapper();
        byte[] metaData = null;

        try {
            Contract contract = this.fabricConnect("../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            try {
                metaData = contract.evaluateTransaction("ReadBlock", blockId + "_meta");
            } catch (ContractException e) {
                return readRegularBlock(contract, blockId);
            }
            //System.out.println("READING A CHUNKED BLOCK");
            System.out.println(Ansi.ansi().fgYellow().a("\t - WARNING: The chosen block has been divided into chunks"));
            return readChunckedBlock(contract, blockId, new String(metaData));

            /*
            byte[] resultByteArray = contract.evaluateTransaction("ReadBlock", blockId);

            if(resultByteArray == null || resultByteArray.length == 0) {
                throw new RuntimeException("ERROR: no block found fot this ID: " + blockId);
            }
            String compressedStringData = new String(resultByteArray);
            byte[] compressedBytes = Base64.getDecoder().decode(compressedStringData);

            ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            gzipInputStream.close();

            String json = baos.toString("UTF-8");
            //System.out.println("BC JSON: " + json);
            //System.out.println("\t - BLOCK DATA: this is the data of the specified Block: " + json);

            ChangeEventsMap map = mapper.readValue(json, ChangeEventsMap.class);
            return map.getChangeEvents();

            if(map.getChangeEvents().values() != null) {
                System.out.println("MAP VALUES: " + map.getChangeEvents().values());
            } else {
                System.out.println("EVENTS NULL");
            }

            if(map != null && !(map.getChangeEvents().isEmpty())) {
                if(!(map.getChangeEvents().values().contains(null))) {
                    resultArrayList = map.getChangeEvents().values();
                    System.out.println("\t - BLOCK DATA LENGTH: " + resultArrayList.size());
                } else {
                    System.out.println("\t - NO BLOCK DATA: session only");
                    resultArrayList = new ArrayList<>();
                }
            }
        } catch (IOException | ContractException e) {
            throw new RuntimeException(e);
        }

        return resultArrayList;
             */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<StartNewSessionEvent, List<ChangeEvent>> readChunckedBlock(Contract contract, String blockID, String metadata) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode metaNode = mapper.readTree(metadata);

        int totalChunks = metaNode.get("totalChunks").asInt();

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < totalChunks; i++) {
            String chunkID = blockID + "_chunk_" + i;
            try {
                byte[] chunkData = contract.evaluateTransaction("ReadBlock", chunkID);
                //System.out.println("In the reconstruction for loop: " + i);
                stringBuilder.append(new String(chunkData));
            } catch (ContractException e) {
                throw new RuntimeException(e);
            }
        }

        String compressedData = stringBuilder.toString();
        byte[] compressedBytes = Base64.getDecoder().decode(compressedData);

        ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
        GZIPInputStream gzipIn = new GZIPInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipIn.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        gzipIn.close();

        String json = baos.toString("UTF-8");

        ObjectMapper objectMapper = JacksonConfig.getMapper();
        ChangeEventsMap map = objectMapper.readValue(json, ChangeEventsMap.class);

        if(map != null && !(map.getChangeEvents().isEmpty()) && !(map.getChangeEvents().values().contains(null))) {
            return map.getChangeEvents();
        } else {
            throw new RuntimeException("ERROR: error during serialization of tha data occurred");
        }
    }

    private Map<StartNewSessionEvent, List<ChangeEvent>> readRegularBlock(Contract contract, String blockID) throws IOException {
        ObjectMapper mapper = JacksonConfig.getMapper();
        try {
            byte[] resultByteArray = contract.evaluateTransaction("ReadBlock", blockID);
            if(resultByteArray == null || resultByteArray.length == 0) {
                throw new RuntimeException("ERROR: no block found fot this ID: " + blockID);
            }
            String compressedStringData = new String(resultByteArray);
            byte[] compressedBytes = Base64.getDecoder().decode(compressedStringData);

            ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            gzipInputStream.close();

            String json = baos.toString("UTF-8");
            //System.out.println("BC JSON: " + json);
            //System.out.println("\t - BLOCK DATA: this is the data of the specified Block: " + json);

            ChangeEventsMap map = mapper.readValue(json, ChangeEventsMap.class);
            return map.getChangeEvents();
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<StartNewSessionEvent, List<ChangeEvent>> getFisrtBlock() {
        ObjectMapper mapper = JacksonConfig.getMapper();

        try {
            Contract contract = this.fabricConnect("../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
            byte[] resultByteArray = contract.evaluateTransaction("ReadBlock", "block1");

            if(resultByteArray == null || resultByteArray.length == 0) {
                throw new RuntimeException("ERROR: no block found fot this ID: block1");
            }

            String compressedStringData = new String(resultByteArray);
            byte[] compressedBytes = Base64.getDecoder().decode(compressedStringData);

            ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while((len = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            gzipInputStream.close();

            String json = baos.toString("UTF-8");
            //System.out.println("BC JSON: " + json);
            //System.out.println("\t - BLOCK DATA: this is the data of the specified Block: " + json);

            ChangeEventsMap map = mapper.readValue(json, ChangeEventsMap.class);
            return map.getChangeEvents();

        } catch (IOException | ContractException e) {
            throw new RuntimeException(e);
        }
    }

    private Contract fabricConnect(String networkConfigStringPAth) throws IOException {
        this.inizializeWallet("src/main/resources/wallet",
                "../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore",
                "../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/cert.pem");
        try {
            // /home/marco/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
            return FabricConnector.connect(networkConfigStringPAth);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: fabric network connection error occurred");
        }
    }

    private void inizializeWallet(String walletPathString, String keyPathString, String certPathString) throws IOException {
        Path walletPath= Paths.get(walletPathString);
        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----WALLET INIZIALIZATION-----").reset());
        if(Files.list(walletPath).findAny().isEmpty()) {
            System.out.println(Ansi.ansi().bgYellow().fgBlack().a("WALLET: The Wallet manager is being initialized").reset());
            Path keyPath = Files.list(Paths.get("../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/keystore"))
                    .findFirst().orElseThrow(() -> new RuntimeException("No KEY found in /keystore directory"));

            WalletManager manager = new WalletManager(
                    walletPath.toString(),
                    "../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/User1@org1.example.com/msp/signcerts/cert.pem",
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
            System.out.println("\t - WALLET: Fabric Wallet initialized");
        } else {
            System.out.println("\t - WALLET: Wallet manager initialization was already done");
        }
    }
}