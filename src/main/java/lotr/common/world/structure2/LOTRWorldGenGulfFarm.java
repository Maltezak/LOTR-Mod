package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenGulfFarm extends LOTRWorldGenGulfStructure {
    private Block crop1Block;
    private Item seed1;
    private Block crop2Block;
    private Item seed2;

    public LOTRWorldGenGulfFarm(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomCrop;
        super.setupRandomBlocks(random);
        if(random.nextBoolean()) {
            this.crop1Block = Blocks.wheat;
            this.seed1 = Items.wheat_seeds;
        }
        else {
            randomCrop = random.nextInt(4);
            if(randomCrop == 0) {
                this.crop1Block = Blocks.carrots;
                this.seed1 = Items.carrot;
            }
            else if(randomCrop == 1) {
                this.crop1Block = Blocks.potatoes;
                this.seed1 = Items.potato;
            }
            else if(randomCrop == 2) {
                this.crop1Block = LOTRMod.lettuceCrop;
                this.seed1 = LOTRMod.lettuce;
            }
            else if(randomCrop == 3) {
                this.crop1Block = LOTRMod.turnipCrop;
                this.seed1 = LOTRMod.turnip;
            }
        }
        if(random.nextBoolean()) {
            this.crop2Block = Blocks.wheat;
            this.seed2 = Items.wheat_seeds;
        }
        else {
            randomCrop = random.nextInt(4);
            if(randomCrop == 0) {
                this.crop2Block = Blocks.carrots;
                this.seed2 = Items.carrot;
            }
            else if(randomCrop == 1) {
                this.crop2Block = Blocks.potatoes;
                this.seed2 = Items.potato;
            }
            else if(randomCrop == 2) {
                this.crop2Block = LOTRMod.lettuceCrop;
                this.seed2 = LOTRMod.lettuce;
            }
            else if(randomCrop == 3) {
                this.crop2Block = LOTRMod.turnipCrop;
                this.seed2 = LOTRMod.turnip;
            }
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -5; i1 <= 5; ++i1) {
                for(int k1 = -5; k1 <= 5; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(int i1 = -5; i1 <= 5; ++i1) {
            for(int k1 = -5; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == 5 && k2 == 5) continue;
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("gulf_farm");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("CROP1", this.crop1Block, 7);
        this.associateBlockMetaAlias("CROP2", this.crop2Block, 7);
        this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        if(random.nextInt(4) == 0) {
            LOTREntityGulfFarmer farmer = new LOTREntityGulfFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
        }
        LOTREntityHaradSlave farmhand1 = new LOTREntityHaradSlave(world);
        farmhand1.seedsItem = this.seed1;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
        LOTREntityHaradSlave farmhand2 = new LOTREntityHaradSlave(world);
        farmhand2.seedsItem = this.seed2;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
        return true;
    }
}
