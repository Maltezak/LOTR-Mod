package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronStables extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronStables(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -4; i1 <= 8; ++i1) {
                for(int k1 = -7; k1 <= 7; ++k1) {
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
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -4; i1 <= 8; ++i1) {
            for(int k1 = -7; k1 <= 7; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 <= 4 && k2 <= 7 || i1 >= 5 && i1 <= 8 && k2 <= 6) {
                    for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                    }
                    for(j1 = 1; j1 <= 8; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if((i2 > 3 || k2 > 6) && (i1 < 4 || i1 > 7 || k2 > 5)) continue;
                random.nextInt(2);
                if(random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
                }
                if(random.nextInt(4) != 0) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        this.loadStrScan("southron_stable");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeChest(world, random, -3, 1, 6, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        int numHaradrim = 1 + random.nextInt(2);
        for(int l = 0; l < numHaradrim; ++l) {
            LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 8);
        }
        for(int k1 : new int[] {-4, 0, 4}) {
            int i1 = 5;
            int j12 = 1;
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, i1, j12, k1, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
        }
        return true;
    }
}
