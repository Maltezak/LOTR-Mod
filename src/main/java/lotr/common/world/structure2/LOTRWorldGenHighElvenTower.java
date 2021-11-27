package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenTower extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock = LOTRMod.brick3;
    protected int brickMeta = 2;
    protected Block brickSlabBlock = LOTRMod.slabSingle5;
    protected int brickSlabMeta = 5;
    protected Block brickStairBlock = LOTRMod.stairsHighElvenBrick;
    protected Block brickWallBlock = LOTRMod.wall2;
    protected int brickWallMeta = 11;
    protected Block pillarBlock = LOTRMod.pillar;
    protected int pillarMeta = 10;
    protected Block floorBlock = Blocks.double_stone_slab;
    protected int floorMeta = 0;
    protected Block roofBlock = LOTRMod.clayTileDyed;
    protected int roofMeta = 3;
    protected Block roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
    protected int roofSlabMeta = 3;
    protected Block roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
    protected Block plankBlock = Blocks.planks;
    protected int plankMeta = 2;
    protected Block plankSlabBlock = Blocks.wooden_slab;
    protected int plankSlabMeta = 2;
    protected Block plankStairBlock = Blocks.birch_stairs;
    protected Block fenceBlock = Blocks.fence;
    protected int fenceMeta = 2;
    protected Block plateBlock = LOTRMod.plateBlock;
    protected Block leafBlock = Blocks.leaves;
    protected int leafMeta = 6;

    public LOTRWorldGenHighElvenTower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k2;
        int j1;
        int distSq;
        int i1;
        int j12;
        int k1;
        int i12;
        int i13;
        int i14;
        int i2;
        int k12;
        int i22;
        int k13;
        int k142;
        int k15;
        int i15;
        int i16;
        int radius = 7;
        int radiusPlusOne = radius + 1;
        int sections = 2 + random.nextInt(3);
        int sectionHeight = 8;
        this.setOriginAndRotation(world, i, j, k, rotation, radius + 3);
        double radiusD = radius - 0.5;
        double radiusDPlusOne = radiusD + 1.0;
        int wallThresholdMin = (int) (radiusD * radiusD);
        int wallThresholdMax = (int) (radiusDPlusOne * radiusDPlusOne);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i17 = -radiusPlusOne; i17 <= radiusPlusOne; ++i17) {
                for(k142 = -radiusPlusOne; k142 <= radiusPlusOne; ++k142) {
                    distSq = i17 * i17 + k142 * k142;
                    if(distSq >= wallThresholdMax) continue;
                    j1 = this.getTopBlock(world, i17, k142) - 1;
                    if(!this.isSurface(world, i17, j1, k142)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 16) continue;
                    return false;
                }
            }
        }
        for(i1 = -radius; i1 <= radius; ++i1) {
            for(k13 = -radius; k13 <= radius; ++k13) {
                i22 = Math.abs(i1);
                k2 = Math.abs(k13);
                distSq = i1 * i1 + k13 * k13;
                if(distSq >= wallThresholdMax) continue;
                this.layFoundation(world, i1, k13);
                if(distSq >= wallThresholdMin) {
                    this.setBlockAndMetadata(world, i1, 1, k13, this.pillarBlock, this.pillarMeta);
                    for(j1 = 2; j1 <= 6; ++j1) {
                        if(i22 == 5 && k2 == 5 || i22 == radius && k2 == 2 || k2 == radius && i22 == 2) {
                            this.setBlockAndMetadata(world, i1, j1, k13, this.pillarBlock, this.pillarMeta);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i1, j1, k13, this.brickBlock, this.brickMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 7, k13, this.pillarBlock, this.pillarMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 1, k13, this.brickBlock, this.brickMeta);
                for(j1 = 2; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k13);
                }
                this.setBlockAndMetadata(world, i1, 7, k13, this.brickBlock, this.brickMeta);
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k13 = -4; k13 <= 4; ++k13) {
                i22 = Math.abs(i1);
                k2 = Math.abs(k13);
                if(i22 == 4 || k2 == 4) {
                    this.setBlockAndMetadata(world, i1, 1, k13, this.floorBlock, this.floorMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 1, k13, this.pillarBlock, this.pillarMeta);
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(int j13 = 2; j13 <= 4; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, -radius, LOTRMod.gateHighElven, 2);
            }
            for(k13 = -radius; k13 <= -4; ++k13) {
                this.setBlockAndMetadata(world, i1, 1, k13, this.pillarBlock, this.pillarMeta);
            }
        }
        for(int k16 = -6; k16 <= -5; ++k16) {
            this.setBlockAndMetadata(world, -2, 1, k16, this.floorBlock, this.floorMeta);
            this.setBlockAndMetadata(world, 2, 1, k16, this.floorBlock, this.floorMeta);
        }
        this.setBlockAndMetadata(world, 0, 1, -radius - 1, this.brickBlock, this.brickMeta);
        this.layFoundation(world, 0, -radius - 1);
        this.setBlockAndMetadata(world, 0, 1, -radius - 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 1, -radius - 1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 1, -radius - 1, this.brickStairBlock, 2);
        this.layFoundation(world, 0, -radius - 2);
        this.layFoundation(world, -1, -radius - 1);
        this.layFoundation(world, 1, -radius - 1);
        this.setBlockAndMetadata(world, -2, 1, -radius - 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 1, -radius - 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 1, -radius - 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 1, -radius - 1, this.brickStairBlock, 0);
        this.layFoundation(world, -2, -radius - 1);
        this.layFoundation(world, -1, -radius - 2);
        this.layFoundation(world, 1, -radius - 2);
        this.layFoundation(world, 2, -radius - 1);
        for(int i18 : new int[] {-radius + 1, radius - 1}) {
            this.setBlockAndMetadata(world, i18, 2, -2, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i18, 2, 2, this.plankStairBlock, 6);
            for(int k17 = -1; k17 <= 1; ++k17) {
                this.setBlockAndMetadata(world, i18, 2, k17, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
        }
        this.setBlockAndMetadata(world, -2, 2, radius - 1, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 2, radius - 1, this.plankStairBlock, 5);
        for(int i19 = -1; i19 <= 1; ++i19) {
            this.setBlockAndMetadata(world, i19, 2, radius - 1, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        for(int i18 : new int[] {-radius + 2, radius - 2}) {
            this.setBlockAndMetadata(world, i18, 2, -4, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i18, 2, -3, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i18, 2, 3, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i18, 2, 4, this.plankStairBlock, 6);
        }
        int[] i19 = new int[] {-radius + 2, radius - 2};
        k13 = i19.length;
        for(i22 = 0; i22 < k13; ++i22) {
            k142 = i19[i22];
            this.setBlockAndMetadata(world, -4, 2, k142, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 2, k142, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 2, k142, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 4, 2, k142, this.plankStairBlock, 5);
        }
        for(i15 = -radius; i15 <= radius; ++i15) {
            for(k13 = -radius; k13 <= radius; ++k13) {
                i22 = Math.abs(i15);
                k2 = Math.abs(k13);
                if((i22 == radius - 1 && k2 <= 2 || k13 == radius - 1 && i22 <= 2 || i22 == radius - 2 && k2 >= 3 && k2 <= 4 || k2 == radius - 2 && i22 >= 3 && i22 <= 4) && random.nextInt(3) == 0) {
                    if(random.nextInt(3) == 0) {
                        this.placeMug(world, random, i15, 3, k13, random.nextInt(4), LOTRFoods.ELF_DRINK);
                    }
                    else {
                        this.placePlate(world, random, i15, 3, k13, this.plateBlock, LOTRFoods.ELF);
                    }
                }
                if(k13 == -radius + 1 && i22 == 2) {
                    for(int j14 = 2; j14 <= 4; ++j14) {
                        this.setBlockAndMetadata(world, i15, j14, k13, this.brickWallBlock, this.brickWallMeta);
                    }
                    this.setBlockAndMetadata(world, i15, 5, k13, LOTRMod.highElvenTorch, 5);
                }
                if(i22 == radius && k2 == 0 || k13 == radius && i22 == 0) {
                    this.setBlockAndMetadata(world, i15, 3, k13, LOTRMod.highElfWoodBars, 0);
                    this.setBlockAndMetadata(world, i15, 4, k13, LOTRMod.highElfWoodBars, 0);
                }
                if((i22 != radius - 1 || k2 != 1) && (k13 != radius - 1 || i22 != 1)) continue;
                this.setBlockAndMetadata(world, i15, 4, k13, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i15, 5, k13, LOTRMod.highElvenTorch, 5);
            }
        }
        for(i15 = -2; i15 <= 2; ++i15) {
            this.setBlockAndMetadata(world, i15, 6, -radius + 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 6, radius - 1, this.brickStairBlock, 6);
        }
        for(int k18 = -2; k18 <= 2; ++k18) {
            this.setBlockAndMetadata(world, -radius + 1, 6, k18, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, radius - 1, 6, k18, this.brickStairBlock, 5);
        }
        for(i15 = -4; i15 <= -3; ++i15) {
            this.setBlockAndMetadata(world, i15, 6, -radius + 2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 6, radius - 2, this.brickStairBlock, 6);
        }
        for(i15 = 3; i15 <= 4; ++i15) {
            this.setBlockAndMetadata(world, i15, 6, -radius + 2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 6, radius - 2, this.brickStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -radius + 2, 6, -4, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, 6, -3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, radius - 2, 6, -4, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, 6, -3, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -radius + 2, 6, 3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, 6, 4, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, 6, 3, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, 6, 4, this.brickStairBlock, 6);
        for(int k1421 : new int[] {-radius + 2, radius - 2}) {
            this.setBlockAndMetadata(world, -2, 6, k1421, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k1421, this.brickStairBlock, 5);
        }
        for(int i18 : new int[] {-radius + 2, radius - 2}) {
            this.setBlockAndMetadata(world, i18, 6, -2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i18, 6, 2, this.brickStairBlock, 6);
        }
        for(int k1421 : new int[] {-4, 4}) {
            this.setBlockAndMetadata(world, -4, 6, k1421, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 4, 6, k1421, this.brickStairBlock, 5);
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            this.setBlockAndMetadata(world, i12, 8, -radius, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i12, 8, radius, this.roofStairBlock, 3);
        }
        for(int k19 = -2; k19 <= 2; ++k19) {
            this.setBlockAndMetadata(world, -radius, 8, k19, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius, 8, k19, this.roofStairBlock, 0);
        }
        for(i12 = -4; i12 <= -3; ++i12) {
            this.setBlockAndMetadata(world, i12, 8, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i12, 8, radius - 1, this.roofStairBlock, 3);
        }
        for(i12 = 3; i12 <= 4; ++i12) {
            this.setBlockAndMetadata(world, i12, 8, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i12, 8, radius - 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -radius + 1, 8, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, 8, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, 8, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, 8, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, 8, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, 8, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, 8, -5, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, 8, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, 8, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, 8, -5, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, 8, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, 8, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, 8, 5, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, 8, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, 8, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, 8, 5, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, 8, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 8, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 8, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, 8, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 8, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 8, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, 8, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 8, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 8, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, 8, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 8, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 8, radius - 1, this.roofStairBlock, 0);
        int sRadius = radius - 1;
        double sRadiusD = sRadius - 0.7;
        double sRadiusDPlusOne = sRadiusD + 1.0;
        int sWallThresholdMin = (int) (sRadiusD * sRadiusD);
        int sWallThresholdMax = (int) (sRadiusDPlusOne * sRadiusDPlusOne);
        for(int l = 0; l < sections; ++l) {
            int i110;
            int sectionBase = 7 + l * sectionHeight;
            for(i110 = -sRadius; i110 <= sRadius; ++i110) {
                for(k12 = -sRadius; k12 <= sRadius; ++k12) {
                    int i23 = Math.abs(i110);
                    int k22 = Math.abs(k12);
                    int distSq2 = i110 * i110 + k12 * k12;
                    if(distSq2 < sWallThresholdMax) {
                        for(int j15 = sectionBase + 1; j15 <= sectionBase + sectionHeight; ++j15) {
                            if(distSq2 >= sWallThresholdMin) {
                                if(i23 == 4 && k22 == 4 || i23 == sRadius && k22 == 1 || k22 == sRadius && i23 == 1) {
                                    this.setBlockAndMetadata(world, i110, j15, k12, this.pillarBlock, this.pillarMeta);
                                    continue;
                                }
                                this.setBlockAndMetadata(world, i110, j15, k12, this.brickBlock, this.brickMeta);
                                continue;
                            }
                            if(j15 == sectionBase + sectionHeight) {
                                this.setBlockAndMetadata(world, i110, j15, k12, this.brickBlock, this.brickMeta);
                                continue;
                            }
                            this.setAir(world, i110, j15, k12);
                        }
                    }
                    if(i23 == 0 && k22 == sRadius || k22 == 0 && i23 == sRadius) {
                        this.setBlockAndMetadata(world, i110, sectionBase + 1, k12, this.pillarBlock, this.pillarMeta);
                        this.setBlockAndMetadata(world, i110, sectionBase + 3, k12, LOTRMod.highElfWoodBars, 0);
                        this.setBlockAndMetadata(world, i110, sectionBase + 4, k12, LOTRMod.highElfWoodBars, 0);
                        this.setBlockAndMetadata(world, i110, sectionBase + 6, k12, this.pillarBlock, this.pillarMeta);
                    }
                    if(i23 == 1 && k22 == sRadius - 1 || k22 == 1 && i23 == sRadius - 1) {
                        this.setBlockAndMetadata(world, i110, sectionBase + 4, k12, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i110, sectionBase + 5, k12, LOTRMod.highElvenTorch, 5);
                    }
                    if((i23 != 3 || k22 != 4) && (k22 != 3 || i23 != 4)) continue;
                    this.setBlockAndMetadata(world, i110, sectionBase + 1, k12, this.plankBlock, this.plankMeta);
                    if(random.nextInt(4) == 0) {
                        if(random.nextBoolean()) {
                            this.placeMug(world, random, i110, sectionBase + 2, k12, random.nextInt(4), LOTRFoods.ELF_DRINK);
                        }
                        else {
                            this.placePlate(world, random, i110, sectionBase + 2, k12, this.plateBlock, LOTRFoods.ELF);
                        }
                    }
                    this.setBlockAndMetadata(world, i110, sectionBase + 6, k12, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i110, sectionBase + 7, k12, this.leafBlock, this.leafMeta);
                }
            }
            for(i110 = -1; i110 <= 1; ++i110) {
                this.setBlockAndMetadata(world, i110, sectionBase + 1, -sRadius + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i110, sectionBase + 1, sRadius - 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i110, sectionBase + 7, -sRadius + 1, this.plankStairBlock, 7);
                this.setBlockAndMetadata(world, i110, sectionBase + 7, sRadius - 1, this.plankStairBlock, 6);
            }
            for(int k110 = -1; k110 <= 1; ++k110) {
                this.setBlockAndMetadata(world, -sRadius + 1, sectionBase + 1, k110, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, sRadius - 1, sectionBase + 1, k110, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, -sRadius + 1, sectionBase + 7, k110, this.plankStairBlock, 4);
                this.setBlockAndMetadata(world, sRadius - 1, sectionBase + 7, k110, this.plankStairBlock, 5);
            }
            this.setBlockAndMetadata(world, -sRadius, sectionBase + 2, 0, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, sRadius, sectionBase + 2, 0, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 0, sectionBase + 2, -sRadius, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, 0, sectionBase + 2, sRadius, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, -sRadius, sectionBase + 5, 0, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, sRadius, sectionBase + 5, 0, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 0, sectionBase + 5, -sRadius, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, 0, sectionBase + 5, sRadius, this.brickStairBlock, 6);
            LOTREntityHighElfWarrior warrior = new LOTREntityHighElfWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 3, sectionBase + 1, 0, 16);
        }
        int sectionTopHeight = 7 + sections * sectionHeight;
        for(int j16 = 2; j16 <= sectionTopHeight; ++j16) {
            int i111;
            int j2;
            this.setBlockAndMetadata(world, 0, j16, 0, this.pillarBlock, this.pillarMeta);
            int step = (j16 + 2) % 4;
            if(step == 0) {
                for(i111 = -2; i111 <= -1; ++i111) {
                    this.setBlockAndMetadata(world, i111, j16, 0, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, i111, j16, 1, this.brickSlabBlock, this.brickSlabMeta | 8);
                    this.setBlockAndMetadata(world, i111, j16, 2, this.brickSlabBlock, this.brickSlabMeta | 8);
                    for(j2 = j16 + 1; j2 <= j16 + 3; ++j2) {
                        this.setAir(world, i111, j2, 0);
                        this.setAir(world, i111, j2, 1);
                        this.setAir(world, i111, j2, 2);
                    }
                }
                this.setBlockAndMetadata(world, 0, j16, 1, LOTRMod.highElvenTorch, 3);
                continue;
            }
            if(step == 1) {
                for(k12 = 1; k12 <= 2; ++k12) {
                    this.setBlockAndMetadata(world, 0, j16, k12, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, 1, j16, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
                    this.setBlockAndMetadata(world, 2, j16, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
                    for(j2 = j16 + 1; j2 <= j16 + 3; ++j2) {
                        this.setAir(world, 0, j2, k12);
                        this.setAir(world, 1, j2, k12);
                        this.setAir(world, 2, j2, k12);
                    }
                }
                continue;
            }
            if(step == 2) {
                for(i111 = 1; i111 <= 2; ++i111) {
                    this.setBlockAndMetadata(world, i111, j16, 0, this.brickSlabBlock, this.brickSlabMeta);
                    this.setBlockAndMetadata(world, i111, j16, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
                    this.setBlockAndMetadata(world, i111, j16, -2, this.brickSlabBlock, this.brickSlabMeta | 8);
                    for(j2 = j16 + 1; j2 <= j16 + 3; ++j2) {
                        this.setAir(world, i111, j2, 0);
                        this.setAir(world, i111, j2, -1);
                        this.setAir(world, i111, j2, -2);
                    }
                }
                this.setBlockAndMetadata(world, 0, j16, -1, LOTRMod.highElvenTorch, 4);
                continue;
            }
            if(step != 3) continue;
            for(k12 = -2; k12 <= -1; ++k12) {
                this.setBlockAndMetadata(world, 0, j16, k12, this.brickSlabBlock, this.brickSlabMeta);
                this.setBlockAndMetadata(world, -1, j16, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
                this.setBlockAndMetadata(world, -2, j16, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
                for(j2 = j16 + 1; j2 <= j16 + 3; ++j2) {
                    this.setAir(world, 0, j2, k12);
                    this.setAir(world, -1, j2, k12);
                    this.setAir(world, -2, j2, k12);
                }
            }
        }
        for(i14 = -radius; i14 <= radius; ++i14) {
            for(int k111 = -radius; k111 <= radius; ++k111) {
                int j17;
                i2 = Math.abs(i14);
                int k23 = Math.abs(k111);
                int distSq3 = i14 * i14 + k111 * k111;
                if(distSq3 >= wallThresholdMax) continue;
                if(distSq3 >= wallThresholdMin) {
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, k111, this.pillarBlock, this.pillarMeta);
                    for(j17 = sectionTopHeight + 2; j17 <= sectionTopHeight + 5; ++j17) {
                        if(i2 == 5 && k23 == 5 || i2 == radius && k23 == 2 || k23 == radius && i2 == 2) {
                            this.setBlockAndMetadata(world, i14, j17, k111, this.pillarBlock, this.pillarMeta);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i14, j17, k111, this.brickBlock, this.brickMeta);
                    }
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 6, k111, this.pillarBlock, this.pillarMeta);
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 7, k111, this.roofBlock, this.roofMeta);
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 8, k111, this.roofBlock, this.roofMeta);
                }
                else {
                    for(j17 = sectionTopHeight + 1; j17 <= sectionTopHeight + 6; ++j17) {
                        this.setAir(world, i14, j17, k111);
                    }
                }
                if(i2 == 2 && k23 == radius - 1 || k23 == 2 && i2 == radius - 1) {
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 4, k111, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i14, sectionTopHeight + 5, k111, LOTRMod.highElvenTorch, 5);
                }
                if((((i2 > 1) || (k23 != radius - 1)) && ((k23 > 1) || (i2 != radius - 1)) && ((i2 < 3) || (i2 > 4) || (k23 != radius - 2))) && (k23 < 3 || k23 > 4 || i2 != radius - 2)) continue;
                this.setBlockAndMetadata(world, i14, sectionTopHeight + 6, k111, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i14, sectionTopHeight + 7, k111, this.leafBlock, this.leafMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 0, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 2, 0, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 3, 0, this.roofSlabBlock, this.roofSlabMeta);
        for(i14 = -2; i14 <= 2; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight, -radius, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i14, sectionTopHeight, radius, this.roofStairBlock, 7);
        }
        for(k15 = -2; k15 <= 2; ++k15) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight, k15, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, radius, sectionTopHeight, k15, this.roofStairBlock, 4);
        }
        for(i14 = -4; i14 <= -3; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i14, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
        }
        for(i14 = 3; i14 <= 4; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i14, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -5, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -5, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 5, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 5, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, sectionTopHeight, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, sectionTopHeight, -radius + 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, sectionTopHeight, -radius + 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, sectionTopHeight, -radius + 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, sectionTopHeight, radius - 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, sectionTopHeight, radius - 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, sectionTopHeight, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, sectionTopHeight, radius - 1, this.roofStairBlock, 4);
        for(i14 = -2; i14 <= 2; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, -radius + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, radius - 1, this.brickStairBlock, 2);
        }
        for(k15 = -2; k15 <= 2; ++k15) {
            this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 1, k15, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 1, k15, this.brickStairBlock, 1);
        }
        for(i14 = -4; i14 <= -3; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
        }
        for(i14 = 3; i14 <= 4; ++i14) {
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i14, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
        for(int k112 : new int[] {-radius + 2, radius - 2}) {
            this.setBlockAndMetadata(world, -2, sectionTopHeight + 1, k112, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 2, sectionTopHeight + 1, k112, this.brickStairBlock, 1);
        }
        for(int i112 : new int[] {-radius + 2, radius - 2}) {
            this.setBlockAndMetadata(world, i112, sectionTopHeight + 1, -2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i112, sectionTopHeight + 1, 2, this.brickStairBlock, 2);
        }
        int[] i113 = new int[] {-4, 4};
        int k111 = i113.length;
        for(i2 = 0; i2 < k111; ++i2) {
            int k112;
            k112 = i113[i2];
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 1, k112, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 1, k112, this.brickStairBlock, 1);
        }
        for(i16 = -1; i16 <= 1; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 2, -radius, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 3, -radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 4, -radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 5, -radius, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 2, radius, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 3, radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 4, radius, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 5, radius, this.brickStairBlock, 6);
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 2, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 3, k1, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 4, k1, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 5, k1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 2, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 3, k1, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 4, k1, LOTRMod.highElfWoodBars, 0);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 5, k1, this.brickStairBlock, 5);
        }
        this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
        this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
        this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 3);
        this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 3);
        for(i16 = -3; i16 <= 3; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 7, -radius - 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 7, radius + 1, this.roofStairBlock, 3);
        }
        for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -radius - 1, sectionTopHeight + 7, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius + 1, sectionTopHeight + 7, k1, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 4);
        for(i16 = -2; i16 <= 2; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, -radius, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, radius, this.roofStairBlock, 3);
        }
        for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, -radius, sectionTopHeight + 9, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, radius, sectionTopHeight + 9, k1, this.roofStairBlock, 0);
        }
        for(i16 = -4; i16 <= -3; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
        }
        for(i16 = 3; i16 <= 4; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);
        for(j12 = sectionTopHeight + 9; j12 <= sectionTopHeight + 10; ++j12) {
            for(i13 = -1; i13 <= 1; ++i13) {
                this.setBlockAndMetadata(world, i13, j12, -radius + 1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i13, j12, radius - 1, this.roofBlock, this.roofMeta);
            }
            for(k111 = -1; k111 <= 1; ++k111) {
                this.setBlockAndMetadata(world, -radius + 1, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, radius - 1, j12, k111, this.roofBlock, this.roofMeta);
            }
            for(i13 = -3; i13 <= 3; ++i13) {
                this.setBlockAndMetadata(world, i13, j12, -radius + 2, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i13, j12, radius - 2, this.roofBlock, this.roofMeta);
            }
            for(k111 = -3; k111 <= 3; ++k111) {
                this.setBlockAndMetadata(world, -radius + 2, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, radius - 2, j12, k111, this.roofBlock, this.roofMeta);
            }
            this.setBlockAndMetadata(world, -4, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j12, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j12, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j12, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j12, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, j12, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j12, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j12, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, j12, 4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, 4, this.roofBlock, this.roofMeta);
        }
        for(i16 = -2; i16 <= 2; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 10, -4, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 10, 4, this.roofStairBlock, 6);
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, k1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, k1, this.roofStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 4, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -4, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 1);
        for(j12 = sectionTopHeight + 11; j12 <= sectionTopHeight + 12; ++j12) {
            for(i13 = -2; i13 <= 2; ++i13) {
                this.setBlockAndMetadata(world, i13, j12, -4, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i13, j12, 4, this.roofBlock, this.roofMeta);
            }
            for(k111 = -2; k111 <= 2; ++k111) {
                this.setBlockAndMetadata(world, -4, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 4, j12, k111, this.roofBlock, this.roofMeta);
            }
            this.setBlockAndMetadata(world, -3, j12, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -2, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, j12, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j12, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -3, j12, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -2, j12, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, j12, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, j12, 3, this.roofBlock, this.roofMeta);
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -radius + 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, radius - 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 1, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 2, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -1, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -2, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
        for(i16 = -2; i16 <= 2; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 13, -4, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 13, 4, this.roofStairBlock, 3);
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, k1, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, -3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, -3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, 3, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, 3, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);
        for(j12 = sectionTopHeight + 13; j12 <= sectionTopHeight + 14; ++j12) {
            for(i13 = -1; i13 <= 1; ++i13) {
                this.setBlockAndMetadata(world, i13, j12, -3, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i13, j12, 3, this.roofBlock, this.roofMeta);
            }
            for(i13 = -2; i13 <= 2; ++i13) {
                this.setBlockAndMetadata(world, i13, j12, -2, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i13, j12, 2, this.roofBlock, this.roofMeta);
            }
            for(k111 = -1; k111 <= 1; ++k111) {
                this.setBlockAndMetadata(world, -3, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, -2, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 2, j12, k111, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 3, j12, k111, this.roofBlock, this.roofMeta);
            }
        }
        for(i16 = -1; i16 <= 1; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 14, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 14, 1, this.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 14, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 14, 0, this.roofStairBlock, 5);
        for(i16 = -1; i16 <= 1; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 15, -3, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 15, 3, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, 2, this.roofStairBlock, 0);
        for(j12 = sectionTopHeight + 15; j12 <= sectionTopHeight + 16; ++j12) {
            for(i13 = -1; i13 <= 1; ++i13) {
                for(k12 = -1; k12 <= 1; ++k12) {
                    this.setBlockAndMetadata(world, i13, j12, k12, this.roofBlock, this.roofMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, -2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, -2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, 2, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, 2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);
        for(i16 = -1; i16 <= 1; ++i16) {
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 17, -1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i16, sectionTopHeight + 17, 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 17, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 17, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 17, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 18, 0, this.roofSlabBlock, this.roofSlabMeta);
        for(j12 = sectionTopHeight + 10; j12 <= sectionTopHeight + 14; ++j12) {
            this.setBlockAndMetadata(world, 0, j12, 0, Blocks.fence, 2);
        }
        for(i16 = -2; i16 <= 2; ++i16) {
            for(k111 = -2; k111 <= 2; ++k111) {
                if(i16 == 0 || k111 == 0) {
                    this.setBlockAndMetadata(world, i16, sectionTopHeight + 10, k111, Blocks.fence, 2);
                }
                if((i16 != 0 || Math.abs(k111) != 2) && (k111 != 0 || Math.abs(i16) != 2)) continue;
                this.setBlockAndMetadata(world, i16, sectionTopHeight + 9, k111, LOTRMod.chandelier, 10);
            }
        }
        this.setBlockAndMetadata(world, -1, sectionTopHeight + 1, 6, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, sectionTopHeight + 1, 6, LOTRMod.highElvenTable, 0);
        this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, -4, LOTRMod.commandTable, 0);
        LOTREntityHighElfLord elfLord = new LOTREntityHighElfLord(world);
        elfLord.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(elfLord, world, 0, sectionTopHeight + 1, 1, 16);
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityHighElfWarrior.class);
        respawner.setCheckRanges(12, -16, 50, 12);
        respawner.setSpawnRanges(6, 0, 20, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    private void layFoundation(World world, int i, int k) {
        for(int j = 0; (((j == 0) || !this.isOpaque(world, i, j, k)) && (this.getY(j) >= 0)); --j) {
            this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
