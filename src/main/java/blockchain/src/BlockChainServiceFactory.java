package blockchain.src;

public class BlockChainServiceFactory {

    public BlockChainServiceFactory() {}

    public BlockChainServiceImpl createService() {
        return new BlockChainServiceImpl();
    }
}