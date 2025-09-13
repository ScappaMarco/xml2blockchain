package wallet;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class WalletManager {

    private String walletPath;
    private String certificatePath;
    private String keyPath;

    public WalletManager(String walletPath, String certificatePath, String keyPath) {
        this.walletPath = walletPath;
        this.certificatePath = certificatePath;
        this.keyPath = keyPath;
    }

    public String getWalletPath() {
        return this.walletPath;
    }

    public void setWalletPath(String newWalletPath) {
        this.walletPath = newWalletPath;
    }

    public String getCertificatePath() {
        return this.certificatePath;
    }

    public void setCertificatePath(String newCertificatePath) {
        this.certificatePath = newCertificatePath;
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPAth(String newKeyPath) {
        this.keyPath = newKeyPath;
    }

    public void initialize() throws IOException, CertificateException, InvalidKeyException {
        Path walletPath = Paths.get(this.getWalletPath());
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        String userId = "appUser";
        if(wallet.get(userId) != null) {
            return;
        }

        Path certPath = Paths.get(this.getCertificatePath());
        Path keyPath = Paths.get(this.getKeyPath());

        X509Certificate certificate = Identities.readX509Certificate(Files.newBufferedReader(certPath));
        PrivateKey privateKey = Identities.readPrivateKey(Files.newBufferedReader(keyPath));

        Identity identity = Identities.newX509Identity("Org1MSP", certificate, privateKey);

        wallet.put(userId, identity);
    }
}