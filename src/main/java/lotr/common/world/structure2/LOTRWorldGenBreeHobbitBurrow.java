package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHobbitBurrow
extends LOTRWorldGenHobbitBurrow {
    public LOTRWorldGenBreeHobbitBurrow(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        LOTRWorldGenBreeHouse breeBlockProxy = new LOTRWorldGenBreeHouse(false);
        breeBlockProxy.setupRandomBlocks(random);
        this.brickBlock = breeBlockProxy.brickBlock;
        this.brickMeta = breeBlockProxy.brickMeta;
        this.floorBlock = Blocks.cobblestone;
        this.floorMeta = 0;
        this.plankBlock = breeBlockProxy.plankBlock;
        this.plankMeta = breeBlockProxy.plankMeta;
        this.plankSlabBlock = breeBlockProxy.plankSlabBlock;
        this.plankSlabMeta = breeBlockProxy.plankSlabMeta;
        this.plankStairBlock = breeBlockProxy.plankStairBlock;
        this.fenceBlock = breeBlockProxy.fenceBlock;
        this.fenceMeta = breeBlockProxy.fenceMeta;
        this.fenceGateBlock = breeBlockProxy.fenceGateBlock;
        this.doorBlock = breeBlockProxy.doorBlock;
        this.beamBlock = breeBlockProxy.beamBlock;
        this.beamMeta = breeBlockProxy.beamMeta;
        this.tableBlock = breeBlockProxy.tableBlock;
        this.burrowLoot = LOTRChestContents.BREE_HOUSE;
        this.foodPool = LOTRFoods.BREE;
    }

    @Override
    protected LOTREntityHobbit createHobbit(World world) {
        return new LOTREntityBreeHobbit(world);
    }

    @Override
    protected String[] getHobbitCoupleAndHomeNames(Random random) {
        return LOTRNames.getBreeHobbitCoupleAndHomeNames(random);
    }
}

