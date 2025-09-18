'use strict';

const { Contract } = require('fabric-contract-api');

class BlockContract extends Contract {

    async CreateBlock(ctx, blockId, jsonData) {
        const exists = await ctx.stub.getState(blockId);
        if (exists && exists.length > 0) {
            throw new Error(`The Block with this ID: ${blockId} already exists`);
        }
        await ctx.stub.putState(blockId, Buffer.from(jsonData));
        return `The Block with this ID:  ${blockId} has been created successfully`;
    }

    async ReadBlock(ctx, blockId) {
        const blockBytes = await ctx.stub.getState(blockId);
        if (!blockBytes || blockBytes.length === 0) {
            throw new Error(`Block with this ID: ${blockId} does not exists`);
        }
        return blockBytes.toString();
    }
}

module.exports = BlockContract;