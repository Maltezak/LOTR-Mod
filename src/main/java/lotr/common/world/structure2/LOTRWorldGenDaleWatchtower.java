package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleWatchtower extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleWatchtower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int i12;
        int j12;
        int k2;
        int i2;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i12 = -3; i12 <= 3; ++i12) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i12, k1);
                    Block block = this.getBlock(world, i12, j1 - 1, k1);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k1 = -2; k1 <= 2; ++k1) {
                for(j1 = 9; j1 <= 13; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
            }
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k1 = -2; k1 <= 2; ++k1) {
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                for(j12 = 8; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    if(i2 == 2 && k2 == 2) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.pillarBlock, this.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.brickBlock, this.brickMeta);
                    }
                    this.setGrassToDirt(world, i12, j12 - 1, k1);
                }
                if(i2 != 2 || k2 != 2) continue;
                for(j12 = 9; j12 <= 11; ++j12) {
                    this.setBlockAndMetadata(world, i12, j12, k1, this.pillarBlock, this.pillarMeta);
                }
                this.setBlockAndMetadata(world, i12, 12, k1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i12, 12, k1 - 1, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i12, 12, k1 + 1, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i12 - 1, 12, k1, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i12 + 1, 12, k1, this.roofStairBlock, 4);
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -3; k1 <= 3; ++k1) {
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                if((i2 != 1 || k2 != 3) && (i2 != 3 || k2 != 1)) continue;
                for(j12 = 3; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i12, j12, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j12 - 1, k1);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 4, -3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 4, -3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 4, 3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 4, 3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 4, -1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 4, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 4, -1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 4, 1, this.brickStairBlock, 0);
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k1 = -1; k1 <= 1; ++k1) {
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
                for(j1 = 5; j1 <= 7; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
                this.setBlockAndMetadata(world, i12, 4, k1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i12, 8, k1, this.plankBlock, this.plankMeta);
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            this.setBlockAndMetadata(world, i12, 9, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i12, 9, 2, this.fenceBlock, this.fenceMeta);
        }
        for(int k12 = -1; k12 <= 1; ++k12) {
            this.setBlockAndMetadata(world, -2, 9, k12, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2, 9, k12, this.fenceBlock, this.fenceMeta);
        }
        for(int j13 = 1; j13 <= 7; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, 1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 8, 1, Blocks.trapdoor, 9);
        this.setBlockAndMetadata(world, 0, 1, -2, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -2, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 0, 0, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -2, 1, -1, Blocks.crafting_table, 0);
        this.setAir(world, -2, 2, -1);
        this.setBlockAndMetadata(world, -2, 1, 1, LOTRMod.daleTable, 0);
        this.setAir(world, -2, 2, 1);
        this.setBlockAndMetadata(world, 2, 1, -1, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, 2, 2, -1, 5, LOTRFoods.DALE_DRINK);
        this.placeChest(world, random, 2, 1, 1, 5, LOTRChestContents.DALE_WATCHTOWER);
        this.setAir(world, 2, 2, 1);
        int[] j13 = new int[] {-1, 1};
        k1 = j13.length;
        for(j1 = 0; j1 < k1; ++j1) {
            int i13 = j13[j1];
            this.setBlockAndMetadata(world, i13, 5, 0, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i13, 5, -1, LOTRMod.strawBed, 10);
        }
        this.setBlockAndMetadata(world, 0, 7, -1, Blocks.torch, 3);
        this.placeWallBanner(world, 0, 7, -2, LOTRItemBanner.BannerType.DALE, 2);
        this.placeWallBanner(world, 0, 7, 2, LOTRItemBanner.BannerType.DALE, 0);
        this.placeWallBanner(world, -2, 7, 0, LOTRItemBanner.BannerType.DALE, 3);
        this.placeWallBanner(world, 2, 7, 0, LOTRItemBanner.BannerType.DALE, 1);
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 3 || k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 13, k1, this.roofSlabBlock, this.roofSlabMeta);
                    continue;
                }
                if(i2 == 2 || k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 13, k1, this.roofBlock, this.roofMeta);
                    continue;
                }
                if(i2 != 1 && k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, 14, k1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i1, 15, k1, this.roofSlabBlock, this.roofSlabMeta);
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 14, 2, this.roofStairBlock, 3);
        }
        for(int k13 = -2; k13 <= 2; ++k13) {
            this.setBlockAndMetadata(world, -2, 14, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 14, k13, this.roofStairBlock, 0);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 14, 1, this.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, 14, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 14, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 13, 1, Blocks.torch, 4);
        int soldiers = 1 + random.nextInt(2);
        for(int l = 0; l < soldiers; ++l) {
            LOTREntityDaleLevyman soldier = random.nextBoolean() ? new LOTREntityDaleLevyman(world) : new LOTREntityDaleSoldier(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 9, 0, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDaleLevyman.class, LOTREntityDaleSoldier.class);
        respawner.setCheckRanges(16, -12, 12, 4);
        respawner.setSpawnRanges(2, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 9, 0);
        return true;
    }
}
