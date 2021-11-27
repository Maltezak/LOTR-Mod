package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRWorldGenDaleHouse extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int i12;
        int j1;
        int i13;
        int i14;
        int k1;
        int k12;
        int k13;
        int fillBlock22;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i16 = -8; i16 <= 3; ++i16) {
                for(int k14 = -1; k14 <= 9; ++k14) {
                    int j12 = this.getTopBlock(world, i16, k14) - 1;
                    Block block = this.getBlock(world, i16, j12, k14);
                    if(block != Blocks.grass) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 5) continue;
                    return false;
                }
            }
        }
        for(int i17 = -7; i17 <= 2; ++i17) {
            for(k1 = 0; k1 <= 8; ++k1) {
                int j12;
                if(i17 < -2 && k1 > 4) continue;
                for(int j13 = 1; j13 <= 10; ++j13) {
                    this.setAir(world, i17, j13, k1);
                }
                Block fillBlock221 = null;
                int fillMeta = 0;
                if((((i17 == -2) || (i17 == 2)) && ((k1 == 0) || (k1 == 4) || (k1 == 8)))) {
                    fillBlock221 = this.brickBlock;
                    fillMeta = this.brickMeta;
                    for(j12 = 1; j12 <= 7; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.brickBlock, this.brickMeta);
                    }
                }
                else if((k1 == 0 || k1 == 4) && i17 >= -6 && i17 <= -4) {
                    fillBlock221 = this.brickBlock;
                    fillMeta = this.brickMeta;
                    for(j12 = 1; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.brickBlock, this.brickMeta);
                    }
                    for(j12 = 5; j12 <= 6; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.plankBlock, this.plankMeta);
                    }
                    this.setBlockAndMetadata(world, i17, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                }
                else if(i17 == -7 && k1 >= 1 && k1 <= 3) {
                    fillBlock221 = this.brickBlock;
                    fillMeta = this.brickMeta;
                    for(j12 = 1; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.brickBlock, this.brickMeta);
                    }
                    for(j12 = 5; j12 <= 6; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.plankBlock, this.plankMeta);
                    }
                    this.setBlockAndMetadata(world, i17, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                }
                else if((((k1 == 0) || (k1 == 4)) && ((i17 == -7) || (i17 == -3)))) {
                    fillBlock221 = this.woodBlock;
                    fillMeta = this.woodMeta;
                    for(j12 = 1; j12 <= 7; ++j12) {
                        this.setBlockAndMetadata(world, i17, j12, k1, this.woodBlock, this.woodMeta);
                    }
                }
                else {
                    fillBlock221 = this.floorBlock;
                    fillMeta = this.floorMeta;
                }
                for(j12 = 0; (((j12 == 0) || !this.isOpaque(world, i17, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i17, j12, k1, fillBlock221, fillMeta);
                    this.setGrassToDirt(world, i17, j12 - 1, k1);
                }
            }
        }
        for(int[] pos : new int[][] {{-3, -1}, {-7, -1}, {-8, 0}, {-8, 4}, {-7, 5}}) {
            i1 = pos[0];
            int k15 = pos[1];
            for(int j14 = 7; (((j14 >= 4) || !this.isOpaque(world, i1, j14, k15)) && (this.getY(j14) >= 0)); --j14) {
                this.setBlockAndMetadata(world, i1, j14, k15, this.fenceBlock, this.fenceMeta);
            }
        }
        for(int k16 : new int[] {0, 4, 8}) {
            this.setBlockAndMetadata(world, -1, 3, k16, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 3, k16, this.brickStairBlock, 5);
        }
        int[] i17 = new int[] {-2, 2};
        k1 = i17.length;
        for(fillBlock22 = 0; fillBlock22 < k1; ++fillBlock22) {
            int i132 = i17[fillBlock22];
            this.setBlockAndMetadata(world, i132, 3, 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i132, 3, 3, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i132, 3, 5, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i132, 3, 7, this.brickStairBlock, 6);
        }
        for(int j15 = 1; j15 <= 2; ++j15) {
            this.setBlockAndMetadata(world, -2, j15, 1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, -2, j15, 3, this.brickWallBlock, this.brickWallMeta);
        }
        for(int k132 = 1; k132 <= 3; ++k132) {
            for(j1 = 1; j1 <= 3; ++j1) {
                this.setBlockAndMetadata(world, -3, j1, k132, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -3, 1, 2, this.doorBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 2, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 1, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 1, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 2, 7, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 1, 1, 6, Blocks.hay_block, 0);
        for(int i142 = -2; i142 <= 2; ++i142) {
            this.setBlockAndMetadata(world, i142, 4, -1, this.brickStairBlock, 6);
            if(IntMath.mod(i142, 2) != 1) continue;
            this.setBlockAndMetadata(world, i142, 5, -1, this.brickWallBlock, this.brickWallMeta);
        }
        for(k13 = -1; k13 <= 9; ++k13) {
            this.setBlockAndMetadata(world, 3, 4, k13, this.brickStairBlock, 4);
            if(IntMath.mod(k13, 2) != 1) continue;
            this.setBlockAndMetadata(world, 3, 5, k13, this.brickWallBlock, this.brickWallMeta);
        }
        for(i13 = 2; i13 >= -2; --i13) {
            this.setBlockAndMetadata(world, i13, 4, 9, this.brickStairBlock, 7);
            if(IntMath.mod(i13, 2) != 1) continue;
            this.setBlockAndMetadata(world, i13, 5, 9, this.brickWallBlock, this.brickWallMeta);
        }
        for(k13 = 9; k13 >= 5; --k13) {
            this.setBlockAndMetadata(world, -3, 4, k13, this.brickStairBlock, 5);
            if(IntMath.mod(k13, 2) != 1) continue;
            this.setBlockAndMetadata(world, -3, 5, k13, this.brickWallBlock, this.brickWallMeta);
        }
        for(i13 = -4; i13 >= -6; --i13) {
            this.setBlockAndMetadata(world, i13, 4, 5, this.brickStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -7, 4, 5, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -8, 4, 5, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, -8, 4, 4, this.brickBlock, this.brickMeta);
        for(k13 = 3; k13 >= 1; --k13) {
            this.setBlockAndMetadata(world, -8, 4, k13, this.brickStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -8, 4, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -8, 4, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, -7, 4, -1, this.brickBlock, this.brickMeta);
        for(int i18 = -6; i18 <= -4; ++i18) {
            this.setBlockAndMetadata(world, i18, 4, -1, this.brickStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -3, 4, -1, this.brickBlock, this.brickMeta);
        for(int k14 : new int[] {0, 4, 8}) {
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 4, k14, this.brickBlock, this.brickMeta);
                if(k14 != 0 && k14 != 8) continue;
                this.setBlockAndMetadata(world, i1, 5, k14, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 6, k14, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 7, k14, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        int[] i18 = new int[] {-2, 2};
        j1 = i18.length;
        for(fillBlock22 = 0; fillBlock22 < j1; ++fillBlock22) {
            int i15 = i18[fillBlock22];
            for(int k122 = 1; k122 <= 3; ++k122) {
                this.setBlockAndMetadata(world, i15, 4, k122, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i15, 5, k122, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i15, 6, k122, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i15, 7, k122, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
            for(k12 = 5; k12 <= 7; ++k12) {
                this.setBlockAndMetadata(world, i15, 4, k12, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i15, 5, k12, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i15, 6, k12, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i15, 7, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
        }
        for(int i15 = -1; i15 <= 1; ++i15) {
            for(k1 = 1; k1 <= 3; ++k1) {
                this.setBlockAndMetadata(world, i15, 4, k1, this.plankBlock, this.plankMeta);
            }
            for(k1 = 5; k1 <= 7; ++k1) {
                this.setBlockAndMetadata(world, i15, 4, k1, this.plankBlock, this.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, -5, 2, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 6, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 8, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 6, 6, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -5, 6, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -7, 6, 2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -5, 6, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 7, -1, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 2, 7, -1, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 7, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 7, 4, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 7, 8, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 7, 9, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -2, 7, 9, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 7, 8, this.brickStairBlock, 5);
        for(i14 = -8; i14 <= 3; ++i14) {
            for(k1 = -1; k1 <= 9; ++k1) {
                if(i14 < -3 && k1 > 5) continue;
                this.setBlockAndMetadata(world, i14, 8, k1, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -8, 8, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, -8, 8, 5, this.brickSlabBlock, this.brickSlabMeta | 8);
        for(i14 = -8; i14 <= 3; ++i14) {
            this.setBlockAndMetadata(world, i14, 9, -1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i14, 10, 0, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i14, 11, 1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i14, 11, 2, this.roofBlock, this.roofMeta);
            if(i14 <= -1 || i14 >= 1) {
                this.setBlockAndMetadata(world, i14, 11, 3, this.roofStairBlock, 3);
            }
            if(i14 <= -2 || i14 >= 2) {
                this.setBlockAndMetadata(world, i14, 10, 4, this.roofStairBlock, 3);
            }
            if(i14 > -3 && i14 < 3) continue;
            this.setBlockAndMetadata(world, i14, 9, 5, this.roofStairBlock, 3);
        }
        for(int k17 = 3; k17 <= 9; ++k17) {
            if(k17 >= 6) {
                this.setBlockAndMetadata(world, -3, 9, k17, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 3, 9, k17, this.roofStairBlock, 0);
            }
            if(k17 >= 5) {
                this.setBlockAndMetadata(world, -2, 10, k17, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 2, 10, k17, this.roofStairBlock, 0);
            }
            if(k17 >= 4) {
                this.setBlockAndMetadata(world, -1, 11, k17, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k17, this.roofStairBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 11, k17, this.roofBlock, this.roofMeta);
        }
        for(int i15 : new int[] {-7, 2}) {
            for(k12 = 0; k12 <= 4; ++k12) {
                this.setBlockAndMetadata(world, i15, 9, k12, this.brickBlock, this.brickMeta);
            }
            for(k12 = 1; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, i15, 10, k12, this.brickBlock, this.brickMeta);
            }
        }
        for(int i15 : new int[] {-8, 3}) {
            this.setBlockAndMetadata(world, i15, 9, 0, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 10, 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 10, 3, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i15, 9, 4, this.roofStairBlock, 6);
        }
        for(int i122 = -2; i122 <= 2; ++i122) {
            this.setBlockAndMetadata(world, i122, 9, 8, this.brickBlock, this.brickMeta);
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            this.setBlockAndMetadata(world, i12, 10, 8, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 9, 9, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 10, 9, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 9, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 9, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 12, 2, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 13, 2, Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 11, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 12, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 12, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -1, 13, 2, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 13, 2, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 13, 3, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 14, 1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 14, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 14, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 14, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 14, 2, this.roofBlock, this.roofMeta);
        for(int j16 = 1; j16 <= 7; ++j16) {
            this.setBlockAndMetadata(world, -5, j16, 2, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -4, 3, 1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -6, 7, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 7, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -5, 1, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -6, 1, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -6, 2, 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -6, 2, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -5, 2, 3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -5, 3, 3, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 3, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 3, 2, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 4, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 4, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -5, 4, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -5, 5, 1, this.fenceBlock, this.fenceMeta);
        for(int k18 = 1; k18 <= 3; ++k18) {
            this.setBlockAndMetadata(world, -3, 4, k18, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, -2, 5, k18, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -2, 6, k18, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 5, 2, this.doorBlock, 2);
        this.setBlockAndMetadata(world, -2, 6, 2, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 7, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 7, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 7, 5, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 5, 1, LOTRMod.strawBed, 1);
        this.setBlockAndMetadata(world, 1, 5, 1, LOTRMod.strawBed, 9);
        this.setBlockAndMetadata(world, 1, 5, 2, this.woodBeamBlock, this.woodBeamMeta);
        this.placeMug(world, random, 1, 6, 2, 1, LOTRFoods.DALE_DRINK);
        this.placeChest(world, random, 1, 5, 3, 5, LOTRChestContents.DALE_HOUSE);
        this.spawnItemFrame(world, 2, 7, 1, 3, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 1, 5, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 6, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 7, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 7, 4, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 7, 4, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 5, 6, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 1, 5, 7, this.brickBlock, this.brickMeta);
        this.placePlateWithCertainty(world, random, 1, 6, 7, this.plateBlock, LOTRFoods.DALE);
        this.setBlockAndMetadata(world, 0, 5, 7, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -1, 5, 7, LOTRMod.daleTable, 0);
        this.setBlockAndMetadata(world, -1, 7, 7, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 7, 7, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 7, 7, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 7, 6, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 7, 5, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 10, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 10, 5, LOTRMod.chandelier, 0);
        if(random.nextInt(3) == 0) {
            i12 = MathHelper.getRandomIntegerInRange(random, -6, 1);
            k1 = MathHelper.getRandomIntegerInRange(random, 0, 4);
            int chestDir = Direction.directionToFacing[random.nextInt(4)];
            this.placeChest(world, random, i12, 9, k1, chestDir, LOTRChestContents.DALE_HOUSE_TREASURE);
        }
        LOTREntityDaleMan daleMan = new LOTREntityDaleMan(world);
        this.spawnNPCAndSetHome(daleMan, world, -1, 5, 2, 16);
        return true;
    }
}
