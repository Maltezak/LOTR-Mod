package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronHouse extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -3; i12 <= 3; ++i12) {
                for(int k12 = -5; k12 <= 6; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12) - 1;
                    if(!this.isSurface(world, i12, j1, k12)) {
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
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -6; k1 <= 6; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 <= 2 && k2 <= 4) {
                    j1 = 0;
                    while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                        --j1;
                    }
                    for(j1 = 1; j1 <= 7; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if((i2 > 2 || k1 != 5) && (i1 < -3 || i1 > 2 || k1 != 6)) continue;
                j1 = 0;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                for(j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_house");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        if(!this.isOpaque(world, 0, 0, 7) || this.isOpaque(world, 0, 1, 7)) {
            for(i1 = -4; i1 <= 2; ++i1) {
                for(k1 = 6; k1 <= 7; ++k1) {
                    int j12;
                    if(k1 != 7 && (i1 != -4 || k1 != 6)) continue;
                    for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i1, j12 - 1, k1);
                    }
                    for(j12 = 1; j12 <= 3; ++j12) {
                        this.setAir(world, i1, j12, k1);
                    }
                }
            }
        }
        this.placeWallBanner(world, -2, 3, 0, this.bannerType, 1);
        for(int k12 : new int[] {-2, 0, 2}) {
            int i13 = -1;
            int j13 = 2;
            if(random.nextBoolean()) {
                this.placePlate(world, random, i13, j13, k12, LOTRMod.woodPlateBlock, LOTRFoods.SOUTHRON);
                continue;
            }
            this.placeMug(world, random, i13, j13, k12, 1, LOTRFoods.SOUTHRON_DRINK);
        }
        this.setBlockAndMetadata(world, -1, 5, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -3, this.bedBlock, 10);
        this.setBlockAndMetadata(world, 1, 5, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, 1, 5, -3, this.bedBlock, 10);
        this.placeFlowerPot(world, 0, 6, -3, this.getRandomFlower(world, random));
        this.placeChest(world, random, -1, 5, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
        }
        return true;
    }
}
