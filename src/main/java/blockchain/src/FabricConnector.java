package blockchain.src;

import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class FabricConnector {

    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "block-contract";

    public static Contract connect(String networkConfigString) throws IOException {
        //networkConfigPath = ../fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
        Path walletPath = Paths.get("src/main/resources/wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get(networkConfigString);

        System.out.println("DEBUG: connection profile absolute path: " + networkConfigPath.toAbsolutePath());
        System.out.println("DEBUG: wallet path absolute: " + walletPath.toAbsolutePath());
        System.out.println("DEBUG: wallet contains appUser? " + (wallet.get("appUser") != null));

        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(false);

        Gateway gateway = builder.connect();

        Network network = gateway.getNetwork(CHANNEL_NAME);

        try {
            Identity identity = wallet.get("appUser");
            if(identity instanceof X509Identity) {
                X509Identity x509Identity = (X509Identity) identity;
                System.out.println("MSP ID: " + x509Identity.getMspId() + "-----------------------------------");
                System.out.println("CERTIFICATE SUBJECT: " + x509Identity.getCertificate().getSubjectDN());
            }
        } catch (Exception e) {
            System.out.println("Error getting identity info: " + e.getMessage());
        }

        System.out.println("CONNECTION DONE: " + CHANNEL_NAME);
        return network.getContract(CONTRACT_NAME);
    }
}