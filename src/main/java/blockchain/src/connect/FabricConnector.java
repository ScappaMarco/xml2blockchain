package blockchain.src.connect;

import org.fusesource.jansi.Ansi;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FabricConnector {

    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = System.getProperty("contractName", "block-contract-dc");

    /*
    block-contract: blockchain without duplicate checker
    block-contract-dc: blockchain with duplicate checker
    System.getProperty("contractName", "block-contract-dc"): input from mvn command line (default -> with duplicate checker)
     */

    public static Contract connect(String networkConfigString) throws IOException {
        //networkConfigPath = ../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
        Path walletPath = Paths.get("src/main/resources/wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get(networkConfigString);

        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println("Network config file exists: " + networkConfigPath.toFile().exists());
        System.out.println("Wallet directory exists: " + walletPath.toFile().exists());
        System.out.println("Network config absolute path: " + networkConfigPath.toAbsolutePath());

        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----CONNECTION DEBUG DATA-----").reset());
        System.out.println("\t - DEBUG: connection profile absolute path: " + networkConfigPath.toAbsolutePath());
        System.out.println("\t - DEBUG: wallet path absolute: " + walletPath.toAbsolutePath());
        System.out.println("\t - DEBUG: wallet contains appUser? " + (wallet.get("appUser") != null));

        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(false);

        //try (Gateway gateway = builder.connect();){
        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork(CHANNEL_NAME);
        try {
            Identity identity = wallet.get("appUser");
            if(identity instanceof X509Identity) {
                X509Identity x509Identity = (X509Identity) identity;
                System.out.println(("\t - MSP ID: " + x509Identity.getMspId()));
                System.out.println("\t - CERTIFICATE SUBJECT: " + x509Identity.getCertificate().getSubjectDN());
            }
        } catch (Exception e) {
            System.out.println(Ansi.ansi().fgRed().bold().a("\t ERROR: getting identity info: " + e.getMessage()).reset());
        }

        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----CONNECTION INFO-----").reset());
        System.out.println("\t - CONNECTION DONE: " + CHANNEL_NAME);
        return network.getContract(CONTRACT_NAME);
    }

    public static Contract connectToSpecifiedChaincode(String networkConfigString, String chaincode) throws IOException {
        //networkConfigPath = ../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
        Path walletPath = Paths.get("src/main/resources/wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get(networkConfigString);

        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----CONNECTION DEBUG DATA-----").reset());
        System.out.println("\t - DEBUG: connection profile absolute path: " + networkConfigPath.toAbsolutePath());
        System.out.println("\t - DEBUG: wallet path absolute: " + walletPath.toAbsolutePath());
        System.out.println("\t - DEBUG: wallet contains appUser? " + (wallet.get("appUser") != null));

        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(false);

        //try (Gateway gateway = builder.connect();){
        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork(chaincode);
        try {
            Identity identity = wallet.get("appUser");
            if(identity instanceof X509Identity) {
                X509Identity x509Identity = (X509Identity) identity;
                System.out.println(("\t - MSP ID: " + x509Identity.getMspId()));
                System.out.println("\t - CERTIFICATE SUBJECT: " + x509Identity.getCertificate().getSubjectDN());
            }
        } catch (Exception e) {
            System.out.println(Ansi.ansi().fgRed().bold().a("\t ERROR: getting identity info: " + e.getMessage()).reset());
        }

        System.out.println();
        System.out.println(Ansi.ansi().bold().a("-----CONNECTION INFO-----").reset());
        System.out.println("\t - CONNECTION DONE: " + chaincode);
        return network.getContract(CONTRACT_NAME);
    }
}