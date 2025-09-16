# xml2blockchain
## Technic specifications
The project _"xml2blockchain"_ aims to parse and save a model into a BlockChain. The project is divided in three main process groups:
- **Parsing**: the first part of the project is to parse a CBP (Change Based Persistence) file into a iterable data structure. The file (BPMN2.cbpxml) contains some instructions executed onto a model (for example create a node, adding some attributes, changing them value, or adding a reference to another node). In this first step we aim to parse those instructions into, as said before, an iterable Java data structure, so that we can (in the following step) save those instance of the specified instructions into a BlockChain. Most of the parsing code has been taken from https://github.com/MDEGroup/NEMO, with some variation to it.
- **Serializing/Deserializing**: to sava actual data in the BlockChain we need to serialize (or deserialize, depending on either we are pushing in bc on reading) the Data Structure in a less complex format: to archive this goal we used Jackson (https://github.com/FasterXML/jackson), a popular Java library used to serialize or deserialize more complex Data Structures. Using Jackson, we transformed the Data Structure in JSON, so that we can push those data in the BlockChain
- **Saving**: the second main step is to save the data in the Java data structure in a BlockChain: in this regard, we are going to use the SDK provided by Hyperledger Fabric (https://hyperledger-fabric.readthedocs.io/en/release-2.5/index.html). Tis SDK (https://hyperledger.github.io/fabric-gateway-java/) allows us to save our model in BlockChain. More precisely, we are going to save every session of events (ChangeEvent) as an independent block in the BlockChain: every block will have an ID (build like so: "block{id}") that is going to be used to get the information relating to that block.

To import and use all those libraries we used Apache Maven (https://maven.apache.org/).

_All the paths in the project are written as relative paths_

## Blockchain specification
For the two main Blockchain operations (Saving all sessions, and getting the data form a specified block) we wrote a custom ChainCode in JavaScript (https://github.com/ScappaMarco/xml2blockchain/blob/main/src/main/java/blockchain/chaicode.js) to define custom operations:
- **CreateBlock**: this operation take as arguments the **blockId** and the **JSONdata**: this data are all the events in the sessions serialized as JSON
- **ReadBlock**: this operation take as argument the **blockId** and then return the data contained in the specified block as JSON: then this data i deserialized to give us an event list 
Due to memory space, before pushing the Blocks in BlockChain they are compressed and then sent. The same procedure is done whenever we want to read a block: we first decompress the data and then we deserialize

### Blockchain main logic
The design of the BlockChain has been defined in the BlockChainService directory's files (https://github.com/ScappaMarco/xml2blockchain/tree/main/src/main/java/blockchain): here you can find the definitions of the BlockChain two main functions (save, read). 