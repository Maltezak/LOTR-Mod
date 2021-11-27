package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorFarm extends LOTRWorldGenHarnedorStructure {
    private Block crop1Block;
    private Item seed1;
    private Block crop2Block;
    private Item seed2;

    public LOTRWorldGenHarnedorFarm(boolean flag) {
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
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -4; i1 <= 4; ++i1) {
                for(int k1 = -4; k1 <= 4; ++k1) {
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
        for(int i1 = -4; i1 <= 4; ++i1) {
            for(int k1 = -4; k1 <= 4; ++k1) {
                int j12 = -1;
                while(!this.isOpaque(world, i1, j12, k1) && this.getY(j12) >= 0) {
                    this.setBlockAndMetadata(world, i1, j12, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                    --j12;
                }
                for(j12 = 1; j12 <= 4; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
            }
        }
        this.loadStrScan("harnedor_farm");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockAlias("CROP1", this.crop1Block);
        this.associateBlockAlias("CROP2", this.crop2Block);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, random, 0, 4, 0);
        block6: for(int i1 : new int[] {-2, 2}) {
            j1 = 0;
            for(int step = 0; step < 6; ++step) {
                int j2;
                int k1 = -5 - step;
                if(this.isOpaque(world, i1, j1 + 1, k1)) {
                    this.setAir(world, i1, j1 + 1, k1);
                    this.setAir(world, i1, j1 + 2, k1);
                    this.setAir(world, i1, j1 + 3, k1);
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    j2 = j1 - 1;
                    while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                        this.setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i1, j2 - 1, k1);
                        --j2;
                    }
                    ++j1;
                    continue;
                }
                if(this.isOpaque(world, i1, j1, k1)) continue block6;
                this.setAir(world, i1, j1 + 1, k1);
                this.setAir(world, i1, j1 + 2, k1);
                this.setAir(world, i1, j1 + 3, k1);
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j2 - 1, k1);
                    --j2;
                }
                --j1;
            }
        }
        if(random.nextInt(4) == 0) {
            LOTREntityHarnedorFarmer farmer = new LOTREntityHarnedorFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
        }
        LOTREntityHarnedorFarmhand farmhand1 = new LOTREntityHarnedorFarmhand(world);
        farmhand1.seedsItem = this.seed1;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
        LOTREntityHarnedorFarmhand farmhand2 = new LOTREntityHarnedorFarmhand(world);
        farmhand2.seedsItem = this.seed2;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
        return true;
    }
}
