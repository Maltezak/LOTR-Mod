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

public class LOTRWorldGenDaleFortress extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleFortress(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int i12;
        int j1;
        int j12;
        int k2;
        Block block;
        int k1;
        int i132;
        int i2;
        int k12;
        int i14;
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int maxEdgeHeight = 0;
            for(i132 = -12; i132 <= 12; ++i132) {
                for(int k13 = -12; k13 <= 12; ++k13) {
                    j12 = this.getTopBlock(world, i132, k13) - 1;
                    block = this.getBlock(world, i132, j12, k13);
                    if(block != Blocks.grass) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight > 8) {
                        return false;
                    }
                    if(Math.abs(i132) != 12 && Math.abs(k13) != 12 || j12 <= maxEdgeHeight) continue;
                    maxEdgeHeight = j12;
                }
            }
            this.originY = this.getY(maxEdgeHeight);
        }
        for(int i15 = -11; i15 <= 11; ++i15) {
            for(k1 = -11; k1 <= 11; ++k1) {
                int j13;
                i2 = Math.abs(i15);
                k2 = Math.abs(k1);
                this.layFoundation(world, i15, k1);
                if(i2 > 9 || k2 > 9) continue;
                for(j13 = 1; j13 <= 8; ++j13) {
                    this.setAir(world, i15, j13, k1);
                }
                if(i2 <= 8 && k2 <= 8 && (i2 == 8 && k2 >= 4 || k2 == 8 && i2 >= 4)) {
                    for(j13 = 1; j13 <= 3; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if(i2 == 9 && k2 == 2 || k2 == 9 && i2 == 2) {
                    for(j13 = 1; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.pillarBlock, this.pillarMeta);
                    }
                }
                else if(i2 == 9 && (k2 == 3 || k2 == 5 || k2 == 9) || k2 == 9 && (i2 == 3 || i2 == 5 || i2 == 9)) {
                    for(j13 = 1; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.brickBlock, this.brickMeta);
                    }
                }
                else if(i2 == 9 || k2 == 9) {
                    for(j13 = 4; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if(i2 == 9) {
                    this.setBlockAndMetadata(world, i15, 3, -8, this.brickStairBlock, 7);
                    this.setBlockAndMetadata(world, i15, 3, -6, this.brickStairBlock, 6);
                    this.setBlockAndMetadata(world, i15, 3, 6, this.brickStairBlock, 7);
                    this.setBlockAndMetadata(world, i15, 3, 8, this.brickStairBlock, 6);
                }
                if(k2 == 9) {
                    this.setBlockAndMetadata(world, -8, 3, k1, this.brickStairBlock, 4);
                    this.setBlockAndMetadata(world, -6, 3, k1, this.brickStairBlock, 5);
                    this.setBlockAndMetadata(world, 6, 3, k1, this.brickStairBlock, 4);
                    this.setBlockAndMetadata(world, 8, 3, k1, this.brickStairBlock, 5);
                }
                if(i2 == 9 && k2 <= 5 || k2 == 9 && i2 <= 5) {
                    this.setBlockAndMetadata(world, i15, 6, k1, this.brickWallBlock, this.brickWallMeta);
                }
                if(i2 == 6 && k2 <= 5 || k2 == 6 && i2 <= 5) {
                    this.setBlockAndMetadata(world, i15, 6, k1, this.brickWallBlock, this.brickWallMeta);
                    if(i2 == 6 && k2 <= 3 || k2 == 6 && i2 <= 3) {
                        this.setBlockAndMetadata(world, i15, 5, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i15, 5, k1, this.brickBlock, this.brickMeta);
                    }
                    if(i15 == -5) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.brickStairBlock, 4);
                    }
                    else if(i15 == 5) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.brickStairBlock, 5);
                    }
                    else if(k1 == -5) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.brickStairBlock, 7);
                    }
                    else if(k1 == 5) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.brickStairBlock, 6);
                    }
                }
                if(i2 <= 8 && k2 <= 8 && (i2 >= 7 || k2 >= 7)) {
                    if(i2 <= 2 || k2 <= 2) {
                        this.setBlockAndMetadata(world, i15, 5, k1, this.plankBlock, this.plankMeta);
                    }
                    else if(i15 == -3) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.plankStairBlock, 4);
                        this.setBlockAndMetadata(world, i15, 5, k1, this.plankStairBlock, 1);
                    }
                    else if(i15 == 3) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.plankStairBlock, 5);
                        this.setBlockAndMetadata(world, i15, 5, k1, this.plankStairBlock, 0);
                    }
                    else if(k1 == -3) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.plankStairBlock, 7);
                        this.setBlockAndMetadata(world, i15, 5, k1, this.plankStairBlock, 2);
                    }
                    else if(k1 == 3) {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.plankStairBlock, 6);
                        this.setBlockAndMetadata(world, i15, 5, k1, this.plankStairBlock, 3);
                    }
                    else {
                        this.setBlockAndMetadata(world, i15, 4, k1, this.plankBlock, this.plankMeta);
                    }
                }
                if(i2 == 6 && k2 == 6) {
                    for(j13 = 1; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if((((i2 == 6) || (i2 == 9)) && ((k2 == 6) || (k2 == 9)))) {
                    for(j13 = 6; j13 <= 7; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if((i2 != 9 || k2 != 7 && k2 != 8) && (k2 != 9 || i2 != 7 && i2 != 8)) continue;
                for(j13 = 6; j13 <= 7; ++j13) {
                    this.setBlockAndMetadata(world, i15, j13, k1, this.barsBlock, 0);
                }
            }
        }
        for(int j14 = 1; j14 <= 3; ++j14) {
            for(i14 = -1; i14 <= 1; ++i14) {
                this.setBlockAndMetadata(world, i14, j14, -9, LOTRMod.gateWooden, 2);
                this.setBlockAndMetadata(world, i14, j14, 9, this.brickBlock, this.brickMeta);
            }
            for(k1 = -1; k1 <= 1; ++k1) {
                this.setBlockAndMetadata(world, -9, j14, k1, LOTRMod.gateWooden, 5);
                this.setBlockAndMetadata(world, 9, j14, k1, LOTRMod.gateWooden, 4);
            }
        }
        for(int i1321 : new int[] {-9, 6}) {
            for(int k14 : new int[] {-9, 6}) {
                int k22;
                int i22;
                for(i22 = i1321; i22 <= i1321 + 3; ++i22) {
                    this.setBlockAndMetadata(world, i22, 8, k14, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i22, 8, k14 + 3, this.brickBlock, this.brickMeta);
                }
                for(k22 = k14 + 1; k22 <= k14 + 2; ++k22) {
                    this.setBlockAndMetadata(world, i1321, 8, k22, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1321 + 3, 8, k22, this.brickBlock, this.brickMeta);
                }
                for(i22 = i1321; i22 <= i1321 + 3; ++i22) {
                    for(int k23 = k14; k23 <= k14 + 3; ++k23) {
                        this.setBlockAndMetadata(world, i22, 9, k23, this.roofBlock, this.roofMeta);
                        this.setBlockAndMetadata(world, i22, 10, k23, this.roofSlabBlock, this.roofSlabMeta);
                    }
                }
                for(i22 = i1321 - 1; i22 <= i1321 + 4; ++i22) {
                    this.setBlockAndMetadata(world, i22, 9, k14 - 1, this.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i22, 9, k14 + 4, this.roofStairBlock, 3);
                }
                for(k22 = k14; k22 <= k14 + 3; ++k22) {
                    this.setBlockAndMetadata(world, i1321 - 1, 9, k22, this.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i1321 + 4, 9, k22, this.roofStairBlock, 0);
                }
                this.setBlockAndMetadata(world, i1321, 8, k14 - 1, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i1321 + 3, 8, k14 - 1, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, i1321 + 4, 8, k14, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, i1321 + 4, 8, k14 + 3, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, i1321 + 3, 8, k14 + 4, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i1321, 8, k14 + 4, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i1321 - 1, 8, k14 + 3, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i1321 - 1, 8, k14, this.roofStairBlock, 5);
            }
        }
        this.setBlockAndMetadata(world, -6, 8, -8, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -6, 8, -7, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -7, 8, -6, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, -6, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 6, 8, -8, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 6, 8, -7, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 7, 8, -6, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 8, 8, -6, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -6, 8, 8, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 8, 7, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -7, 8, 6, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, 6, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 6, 8, 8, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 8, 7, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 8, 6, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 8, 8, 6, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 8, -8, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 8, -8, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -8, 8, 8, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 8, 8, Blocks.torch, 1);
        int[] j14 = new int[] {-2, 2};
        k1 = j14.length;
        for(i2 = 0; i2 < k1; ++i2) {
            i132 = j14[i2];
            for(int j16 = 6; j16 <= 8; ++j16) {
                this.setBlockAndMetadata(world, i132, j16, -9, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i132, 9, -9, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i132, 5, -10, Blocks.torch, 4);
            this.placeWallBanner(world, i132, 8, -9, LOTRItemBanner.BannerType.DALE, 2);
        }
        for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, -7, j1, -7, Blocks.ladder, 4);
            this.setBlockAndMetadata(world, 7, j1, -7, Blocks.ladder, 5);
        }
        this.setBlockAndMetadata(world, 0, 4, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 4, 8, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -8, 4, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 8, 4, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 3, -6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 6, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 3, -6, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 3, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -5, 3, 6, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 6, 3, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 5, 3, 6, Blocks.torch, 1);
        for(k12 = -8; k12 <= -4; ++k12) {
            this.setBlockAndMetadata(world, -3, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, -2, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, 2, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, 3, 0, k12, Blocks.grass, 0);
        }
        for(k12 = 4; k12 <= 8; ++k12) {
            this.setBlockAndMetadata(world, -3, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, -2, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, 2, 0, k12, Blocks.grass, 0);
            this.setBlockAndMetadata(world, 3, 0, k12, Blocks.grass, 0);
        }
        for(i1 = -8; i1 <= -4; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, -3, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, -2, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, 2, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, 3, Blocks.grass, 0);
        }
        for(i1 = 4; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, -3, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, -2, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, 2, Blocks.grass, 0);
            this.setBlockAndMetadata(world, i1, 0, 3, Blocks.grass, 0);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 2, k1, this.brickBlock, this.brickMeta);
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickWallBlock, this.brickWallMeta);
                }
                else if(i2 == 1 || k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 5, k1, this.brickWallBlock, this.brickWallMeta);
                }
                if(i1 != 0 || k1 != 0) continue;
                this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 4, k1, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i1, 5, k1, Blocks.fire, 0);
            }
        }
        this.setBlockAndMetadata(world, 0, 6, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -1, 6, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 6, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 6, 1, this.roofStairBlock, 3);
        for(k12 = -7; k12 <= -4; ++k12) {
            for(i14 = -7; i14 <= -4; ++i14) {
                this.setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
            }
            for(i14 = 4; i14 <= 7; ++i14) {
                this.setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
            }
        }
        this.setBlockAndMetadata(world, -4, 1, -5, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -5, 1, -4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 5, 1, -4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 4, 1, -5, this.brickWallBlock, this.brickWallMeta);
        for(k12 = 4; k12 <= 7; ++k12) {
            for(i14 = -7; i14 <= -4; ++i14) {
                this.setBlockAndMetadata(world, i14, 0, k12, this.plankBlock, this.plankMeta);
            }
            for(i14 = 4; i14 <= 7; ++i14) {
                this.setBlockAndMetadata(world, i14, 0, k12, this.plankBlock, this.plankMeta);
            }
        }
        for(k12 = 4; k12 <= 6; ++k12) {
            for(i14 = -6; i14 <= -4; ++i14) {
                this.setBlockAndMetadata(world, i14, 4, k12, Blocks.wool, 11);
            }
            for(i14 = 4; i14 <= 6; ++i14) {
                this.setBlockAndMetadata(world, i14, 4, k12, Blocks.wool, 11);
            }
        }
        for(j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, -4, j1, 4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 4, j1, 4, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -4, 0, -4, Blocks.grass, 0);
        this.setBlockAndMetadata(world, -3, 0, -3, Blocks.grass, 0);
        this.setBlockAndMetadata(world, 4, 0, -4, Blocks.grass, 0);
        this.setBlockAndMetadata(world, 3, 0, -3, Blocks.grass, 0);
        this.setBlockAndMetadata(world, -4, 0, 4, Blocks.grass, 0);
        this.setBlockAndMetadata(world, -3, 0, 3, Blocks.grass, 0);
        this.setBlockAndMetadata(world, 4, 0, 4, Blocks.grass, 0);
        this.setBlockAndMetadata(world, 3, 0, 3, Blocks.grass, 0);
        for(j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, -7, j1, 7, this.plankBlock, this.plankMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, 6, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 3, 7, this.plankStairBlock, 4);
        for(int j17 : new int[] {1, 2}) {
            this.setBlockAndMetadata(world, -7, j17, 5, LOTRMod.strawBed, 0);
            this.setBlockAndMetadata(world, -7, j17, 6, LOTRMod.strawBed, 8);
            this.setBlockAndMetadata(world, -5, j17, 7, LOTRMod.strawBed, 3);
            this.setBlockAndMetadata(world, -6, j17, 7, LOTRMod.strawBed, 11);
        }
        for(int j18 = 1; j18 <= 3; ++j18) {
            this.setBlockAndMetadata(world, 7, j18, 7, this.plankBlock, this.plankMeta);
        }
        this.setBlockAndMetadata(world, 7, 3, 6, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 3, 7, this.plankStairBlock, 5);
        for(i12 = 4; i12 <= 6; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, 7, this.plankBlock, this.plankMeta);
            if(i12 > 5) continue;
            this.placeBarrel(world, random, i12, 2, 7, 2, LOTRFoods.DALE_DRINK);
        }
        for(int k15 = 4; k15 <= 6; ++k15) {
            this.setBlockAndMetadata(world, 7, 1, k15, this.plankBlock, this.plankMeta);
            if(k15 > 5) continue;
            this.placeBarrel(world, random, 7, 2, k15, 5, LOTRFoods.DALE_DRINK);
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            this.setBlockAndMetadata(world, i12, 0, 8, this.floorBlock, this.floorMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, 8, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 1, 8, this.plankBlock, this.plankMeta);
        this.placeChest(world, random, -3, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
        this.placeChest(world, random, -2, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
        this.setBlockAndMetadata(world, 2, 1, 8, LOTRMod.daleTable, 0);
        this.setBlockAndMetadata(world, 3, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, 1, 7, LOTRMod.commandTable, 0);
        this.setBlockAndMetadata(world, 6, 1, 6, Blocks.furnace, 2);
        LOTREntityDaleCaptain captain = new LOTREntityDaleCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 3, 16);
        int soldiers = 3 + random.nextInt(4);
        for(int l = 0; l < soldiers; ++l) {
            LOTREntityDaleSoldier soldier = random.nextInt(3) == 0 ? new LOTREntityDaleArcher(world) : new LOTREntityDaleSoldier(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 3, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDaleSoldier.class, LOTREntityDaleArcher.class);
        respawner.setCheckRanges(16, -6, 10, 12);
        respawner.setSpawnRanges(10, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    private void layFoundation(World world, int i, int k) {
        for(int j = 0; j == 0 || !this.isOpaque(world, i, j, k) && this.getY(j) >= 0; --j) {
            this.setBlockAndMetadata(world, i, j, k, this.floorBlock, this.floorMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
