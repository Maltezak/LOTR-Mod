package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBarracks extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronBarracks(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -6; i1 <= 5; ++i1) {
                for(int k12 = -8; k12 <= 8; ++k12) {
                    int j1 = this.getTopBlock(world, i1, k12) - 1;
                    if(!this.isSurface(world, i1, j1, k12)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -5; i1 <= 4; ++i1) {
            for(int k13 = -7; k13 <= 7; ++k13) {
                for(int j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i1, j1, k13);
                }
            }
        }
        this.loadStrScan("southron_barracks");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.generateStrScan(world, random, 0, 0, 0);
        for(k1 = -4; k1 <= 4; k1 += 2) {
            if(random.nextBoolean()) {
                this.placeChest(world, random, -4, 1, k1, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, -4, 1, k1, LOTRMod.chestBasket, 4);
            }
            if(random.nextBoolean()) {
                this.placeChest(world, random, 3, 1, k1, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
                continue;
            }
            this.setBlockAndMetadata(world, 3, 1, k1, LOTRMod.chestBasket, 5);
        }
        for(k1 = -5; k1 <= 5; k1 += 2) {
            for(int j1 = 1; j1 <= 2; ++j1) {
                this.setBlockAndMetadata(world, -3, j1, k1, this.bedBlock, 3);
                this.setBlockAndMetadata(world, -4, j1, k1, this.bedBlock, 11);
                this.setBlockAndMetadata(world, 2, j1, k1, this.bedBlock, 1);
                this.setBlockAndMetadata(world, 3, j1, k1, this.bedBlock, 9);
            }
        }
        int warriors = 2 + random.nextInt(3);
        for(int l = 0; l < warriors; ++l) {
            LOTREntityNearHaradrimBase haradrim = this.createWarrior(world, random);
            this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -1 : 0, 1, 0, 16);
        }
        return true;
    }

    protected LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
        return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }
}
