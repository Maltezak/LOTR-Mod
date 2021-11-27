package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTavern extends LOTRWorldGenEasterlingStructure {
    private String[] tavernName;
    private String[] tavernNameSign;
    private String tavernNameNPC;

    public LOTRWorldGenEasterlingTavern(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = LOTRMod.strawBed;
        this.tavernName = LOTRNames.getRhunTavernName(random);
        this.tavernNameSign = new String[] {"", this.tavernName[0], this.tavernName[1], ""};
        this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int i12;
        int i13;
        int i14;
        int j12;
        int k2;
        int k22;
        int k1;
        int step;
        int k12;
        int i2;
        int j13;
        int j14;
        int i152;
        int k13;
        int i22;
        int k14;
        int k15;
        this.setOriginAndRotation(world, i, j, k, rotation, 11);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i13 = -9; i13 <= 9; ++i13) {
                for(k1 = -12; k1 <= 11; ++k1) {
                    j13 = this.getTopBlock(world, i13, k1) - 1;
                    if(!this.isSurface(world, i13, j13, k1)) {
                        return false;
                    }
                    if(j13 < minHeight) {
                        minHeight = j13;
                    }
                    if(j13 > maxHeight) {
                        maxHeight = j13;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(i14 = -8; i14 <= 8; ++i14) {
            for(k12 = -10; k12 <= 10; ++k12) {
                i22 = Math.abs(i14);
                k22 = Math.abs(k12);
                for(j13 = 1; j13 <= 12; ++j13) {
                    this.setAir(world, i14, j13, k12);
                }
                if(i22 == 8 && k22 % 4 == 2 || k22 == 10 && i22 % 4 == 0) {
                    for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    continue;
                }
                if(i22 == 8 || k22 == 10) {
                    for(j13 = 3; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    if(k22 == 10) {
                        this.setBlockAndMetadata(world, i14, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                        continue;
                    }
                    if(i22 != 8) continue;
                    this.setBlockAndMetadata(world, i14, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
                    continue;
                }
                for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                    this.setBlockAndMetadata(world, i14, j13, k12, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i14, j13 - 1, k12);
                }
                if(i22 % 4 != 2 || k22 % 4 != 0) continue;
                this.setBlockAndMetadata(world, i14, 0, k12, this.logBlock, this.logMeta);
            }
        }
        for(i14 = -7; i14 <= 7; ++i14) {
            for(k12 = -9; k12 <= 9; ++k12) {
                i22 = Math.abs(i14);
                k22 = Math.abs(k12);
                if(i22 <= 4 && k22 <= 9) {
                    this.setBlockAndMetadata(world, i14, 4, k12, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(i22 % 4 == 0 && k22 <= 9) {
                    this.setBlockAndMetadata(world, i14, 0, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
                    this.setBlockAndMetadata(world, i14, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
                }
                if(k22 % 4 == 2 && i22 <= 7) {
                    this.setBlockAndMetadata(world, i14, 0, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                    if(k22 == 2) {
                        this.setBlockAndMetadata(world, i14, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                    }
                }
                if(k22 != 2 || i22 % 4 != 0) continue;
                for(j13 = 1; j13 <= 3; ++j13) {
                    this.setBlockAndMetadata(world, i14, j13, k12, this.woodBeamBlock, this.woodBeamMeta);
                }
            }
        }
        for(i14 = -8; i14 <= 8; ++i14) {
            i2 = Math.abs(i14);
            if(i2 == 2) {
                this.setBlockAndMetadata(world, i14, 2, -10, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, i14, 3, -10, this.brickStairBlock, 6);
            }
            if(i2 == 6) {
                this.setBlockAndMetadata(world, i14 - 1, 2, -10, this.brickStairBlock, 4);
                this.setAir(world, i14, 2, -10);
                this.setBlockAndMetadata(world, i14 + 1, 2, -10, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i14, 3, -10, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i14 - 1, 2, 10, this.brickStairBlock, 4);
                this.setAir(world, i14, 2, 10);
                this.setBlockAndMetadata(world, i14 + 1, 2, 10, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i14, 3, 10, this.brickStairBlock, 7);
            }
            if(i2 == 4) {
                this.setBlockAndMetadata(world, i14, 3, -11, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i14, 3, 11, Blocks.torch, 3);
            }
            if(i2 == 0) {
                this.setBlockAndMetadata(world, i14, 3, 11, Blocks.torch, 3);
            }
            if(i2 != 8) continue;
            this.setBlockAndMetadata(world, i14, 3, -11, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i14, 3, 11, this.fenceBlock, this.fenceMeta);
        }
        for(int k16 = -10; k16 <= 10; ++k16) {
            k2 = Math.abs(k16);
            if(k2 % 4 == 0) {
                this.setBlockAndMetadata(world, -8, 2, k16 - 1, this.brickStairBlock, 7);
                this.setAir(world, -8, 2, k16);
                this.setBlockAndMetadata(world, -8, 2, k16 + 1, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, -8, 3, k16, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, 8, 2, k16 - 1, this.brickStairBlock, 7);
                this.setAir(world, 8, 2, k16);
                this.setBlockAndMetadata(world, 8, 2, k16 + 1, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, 8, 3, k16, this.brickStairBlock, 4);
            }
            if(k2 % 4 == 2) {
                this.setBlockAndMetadata(world, -9, 3, k16, Blocks.torch, 1);
                this.setBlockAndMetadata(world, 9, 3, k16, Blocks.torch, 2);
            }
            if(k2 != 10) continue;
            this.setBlockAndMetadata(world, -9, 3, k16, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 9, 3, k16, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, 0, 0, -10, this.woodBeamBlock, this.woodBeamMeta | 8);
        this.setBlockAndMetadata(world, 0, 1, -10, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -10, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -10, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 4, -11, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -12, this.plankBlock, this.plankMeta);
        this.placeSign(world, -1, 4, -12, Blocks.wall_sign, 5, this.tavernNameSign);
        this.placeSign(world, 1, 4, -12, Blocks.wall_sign, 4, this.tavernNameSign);
        this.placeSign(world, 0, 4, -13, Blocks.wall_sign, 2, this.tavernNameSign);
        for(i14 = -4; i14 <= 4; ++i14) {
            for(k12 = -9; k12 <= 9; ++k12) {
                i22 = Math.abs(i14);
                k22 = Math.abs(k12);
                if(i22 == 4 && k22 == 2 || k22 == 9 && i22 % 4 == 0) {
                    for(j13 = 5; j13 <= 8; ++j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    if(i22 != 0) continue;
                    for(j13 = 9; j13 <= 11; ++j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    continue;
                }
                if(i22 != 4 && k22 != 9) continue;
                for(j13 = 5; j13 <= 7; ++j13) {
                    this.setBlockAndMetadata(world, i14, j13, k12, this.brickBlock, this.brickMeta);
                }
                if(k22 == 9) {
                    this.setBlockAndMetadata(world, i14, 8, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                    continue;
                }
                if(i22 != 4) continue;
                this.setBlockAndMetadata(world, i14, 8, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
        }
        for(int i1521 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i1521, 6, -9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i1521, 7, -9, this.brickStairBlock, 6);
            if(i1521 < 0) continue;
            this.setBlockAndMetadata(world, i1521, 6, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i1521, 7, 9, this.brickStairBlock, 7);
        }
        for(int i1521 : new int[] {-4, 4}) {
            this.setBlockAndMetadata(world, i1521, 8, -10, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1521, 8, 10, this.fenceBlock, this.fenceMeta);
        }
        int[] i16 = new int[] {-9, 9};
        k12 = i16.length;
        for(i22 = 0; i22 < k12; ++i22) {
            k1 = i16[i22];
            this.setBlockAndMetadata(world, -5, 8, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 5, 8, k1, this.fenceBlock, this.fenceMeta);
        }
        for(int step2 = 0; step2 <= 1; ++step2) {
            j1 = 5 + step2;
            for(k13 = -10 + step2; k13 <= 10 - step2; ++k13) {
                this.setBlockAndMetadata(world, -8 + step2, j1, k13, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -7 + step2, j1, k13, this.roofStairBlock, 4);
            }
            for(i13 = -7 + step2; i13 <= -5; ++i13) {
                this.setBlockAndMetadata(world, i13, j1, -10 + step2, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i13, j1, -9 + step2, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i13, j1, 10 - step2, this.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i13, j1, 9 - step2, this.roofStairBlock, 6);
            }
            for(k13 = -10 + step2; k13 <= 10 - step2; ++k13) {
                this.setBlockAndMetadata(world, 8 - step2, j1, k13, this.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 7 - step2, j1, k13, this.roofStairBlock, 5);
            }
            for(i13 = 5; i13 <= 7 - step2; ++i13) {
                this.setBlockAndMetadata(world, i13, j1, -10 + step2, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i13, j1, -9 + step2, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i13, j1, 10 - step2, this.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i13, j1, 9 - step2, this.roofStairBlock, 6);
            }
            if(step2 != 1) continue;
            for(k13 = -9 + step2; k13 <= 9 - step2; ++k13) {
                for(i152 = -7 + step2; i152 <= -5; ++i152) {
                    this.setBlockAndMetadata(world, i152, j1 + 1, k13, this.roofSlabBlock, this.roofSlabMeta);
                }
                for(i152 = 5; i152 <= 7 - step2; ++i152) {
                    this.setBlockAndMetadata(world, i152, j1 + 1, k13, this.roofSlabBlock, this.roofSlabMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 5, -10, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 5, -10, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 5, -10, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 1, 5, -10, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, -10, this.roofStairBlock, 1);
        for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, 10, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 0, 5, 10, this.roofBlock, this.roofMeta);
        for(i1 = -9; i1 <= 9; ++i1) {
            i2 = Math.abs(i1);
            if(i2 == 9 || i2 == 6 || i2 == 2) {
                this.setBlockAndMetadata(world, i1, 5, -11, this.roofSlabBlock, this.roofSlabMeta);
            }
            if(i1 == -8 || i1 == 7) {
                this.setBlockAndMetadata(world, i1, 4, -11, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i1 + 1, 4, -11, this.roofStairBlock, 4);
            }
            if(i2 == 4) {
                this.setBlockAndMetadata(world, i1 - 1, 4, -11, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i1, 4, -11, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i1 + 1, 4, -11, this.roofStairBlock, 4);
            }
            if(i2 <= 1) {
                this.setBlockAndMetadata(world, i1, 5, -11, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
            if(i2 == 9 || i2 == 6 || i2 == 2) {
                this.setBlockAndMetadata(world, i1, 5, 11, this.roofSlabBlock, this.roofSlabMeta);
            }
            if(i1 == -8 || i1 == 7) {
                this.setBlockAndMetadata(world, i1, 4, 11, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i1 + 1, 4, 11, this.roofStairBlock, 4);
            }
            if(i2 != 4 && i2 != 0) continue;
            this.setBlockAndMetadata(world, i1 - 1, 4, 11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, i1, 4, 11, this.roofStairBlock, 3);
            this.setBlockAndMetadata(world, i1 + 1, 4, 11, this.roofStairBlock, 4);
        }
        for(k14 = -10; k14 <= 10; ++k14) {
            k2 = Math.abs(k14);
            if(k2 % 4 == 0) {
                this.setBlockAndMetadata(world, -9, 5, k14, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, 9, 5, k14, this.roofSlabBlock, this.roofSlabMeta);
            }
            if(k14 == -10 || k14 == 9) {
                this.setBlockAndMetadata(world, -9, 4, k14, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -9, 4, k14 + 1, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 9, 4, k14, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 9, 4, k14 + 1, this.roofStairBlock, 7);
            }
            if(k2 > 6 || k2 % 4 != 2) continue;
            this.setBlockAndMetadata(world, -9, 4, k14 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -9, 4, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -9, 4, k14 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 9, 4, k14 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 9, 4, k14, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 9, 4, k14 + 1, this.roofStairBlock, 7);
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            i2 = Math.abs(i1);
            int[] k17 = new int[] {-10, 10};
            i152 = k17.length;
            for(j13 = 0; j13 < i152; ++j13) {
                int k18 = k17[j13];
                if(i2 == 2 || i2 == 5) {
                    this.setBlockAndMetadata(world, i1, 9, k18, this.roofSlabBlock, this.roofSlabMeta);
                }
                if(i2 == 0) {
                    this.setBlockAndMetadata(world, i1 - 1, 8, k18, this.roofStairBlock, 5);
                    this.setBlockAndMetadata(world, i1, 8, k18, this.roofBlock, this.roofMeta);
                    this.setBlockAndMetadata(world, i1 + 1, 8, k18, this.roofStairBlock, 4);
                    this.setBlockAndMetadata(world, i1, 6, k18, this.roofWallBlock, this.roofWallMeta);
                    this.setBlockAndMetadata(world, i1, 7, k18, this.roofWallBlock, this.roofWallMeta);
                }
                if(i1 != -4 && i1 != 3) continue;
                this.setBlockAndMetadata(world, i1, 8, k18, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, i1 + 1, 8, k18, this.roofStairBlock, 4);
            }
        }
        for(k14 = -9; k14 <= 9; ++k14) {
            k2 = Math.abs(k14);
            if(k2 == 0 || k2 == 4 || k2 == 7) {
                this.setBlockAndMetadata(world, -5, 9, k14, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, 5, 9, k14, this.roofSlabBlock, this.roofSlabMeta);
            }
            if(k2 == 2) {
                this.setBlockAndMetadata(world, -5, 8, k14 - 1, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, -5, 8, k14, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, -5, 8, k14 + 1, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, 5, 8, k14 - 1, this.roofStairBlock, 6);
                this.setBlockAndMetadata(world, 5, 8, k14, this.roofStairBlock, 0);
                this.setBlockAndMetadata(world, 5, 8, k14 + 1, this.roofStairBlock, 7);
            }
            if(k14 != -9 && k14 != -6 && k14 != 5 && k14 != 8) continue;
            this.setBlockAndMetadata(world, -5, 8, k14, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -5, 8, k14 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 5, 8, k14, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 5, 8, k14 + 1, this.roofStairBlock, 7);
        }
        for(k14 = -9; k14 <= 9; ++k14) {
            for(step = 0; step <= 3; ++step) {
                j14 = 9 + step;
                this.setBlockAndMetadata(world, -4 + step, j14, k14, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - step, j14, k14, this.roofStairBlock, 0);
                if(step <= 0) continue;
                this.setBlockAndMetadata(world, -4 + step, j14 - 1, k14, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 4 - step, j14 - 1, k14, this.roofStairBlock, 5);
            }
            this.setBlockAndMetadata(world, 0, 12, k14, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 13, k14, this.roofSlabBlock, this.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 12, -10, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 13, -10, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 12, 10, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 13, 10, this.roofStairBlock, 2);
        int[] k19 = new int[] {-8, 8};
        step = k19.length;
        for(j14 = 0; j14 < step; ++j14) {
            k1 = k19[j14];
            for(int step3 = 0; step3 <= 2; ++step3) {
                int j15 = 9 + step3;
                for(int i17 = -3 + step3; i17 <= 3 - step3; ++i17) {
                    this.setBlockAndMetadata(world, i17, j15, k1, this.plankBlock, this.plankMeta);
                }
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            this.setBlockAndMetadata(world, i12, 8, -8, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i12, 8, 8, this.plankStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -4, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 4, 3, -6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -6, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 6, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -6, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 6, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -4, 3, 6, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 4, 3, 6, LOTRMod.chandelier, 0);
        this.placeTable(world, random, -5, -4, 1, -7, -6);
        for(i12 = -7; i12 <= -4; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, -9, this.plankStairBlock, 3);
        }
        for(k15 = -8; k15 <= -6; ++k15) {
            this.setBlockAndMetadata(world, -7, 1, k15, this.plankStairBlock, 0);
        }
        this.placeTable(world, random, 4, 5, 1, -7, -6);
        for(i12 = 4; i12 <= 7; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, -9, this.plankStairBlock, 3);
        }
        for(k15 = -8; k15 <= -6; ++k15) {
            this.setBlockAndMetadata(world, 7, 1, k15, this.plankStairBlock, 1);
        }
        this.placeTable(world, random, -7, -6, 1, 0, 0);
        for(i12 = -7; i12 <= -6; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, -2, this.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i12, 1, 2, this.plankStairBlock, 2);
        }
        this.placeTable(world, random, 4, 5, 1, -1, 1);
        for(k15 = -1; k15 <= 1; ++k15) {
            this.setBlockAndMetadata(world, 7, 1, k15, this.plankStairBlock, 1);
        }
        this.placeTable(world, random, -7, -6, 1, 8, 9);
        for(i12 = -7; i12 <= -6; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, 6, this.plankStairBlock, 3);
        }
        for(k15 = 8; k15 <= 9; ++k15) {
            this.setBlockAndMetadata(world, -4, 1, k15, this.plankStairBlock, 1);
        }
        this.placeTable(world, random, 6, 7, 1, 8, 9);
        for(i12 = 6; i12 <= 7; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, 6, this.plankStairBlock, 3);
        }
        for(k15 = 8; k15 <= 9; ++k15) {
            this.setBlockAndMetadata(world, 4, 1, k15, this.plankStairBlock, 0);
        }
        for(i12 = -3; i12 <= -1; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, -2, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 3, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i12, 3, 2, this.fenceBlock, this.fenceMeta);
        }
        for(k15 = -1; k15 <= 1; ++k15) {
            this.setBlockAndMetadata(world, -4, 1, k15, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -4, 3, k15, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, 1, k15, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, k15, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, 2, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -2, 1, 2, this.fenceGateBlock, 0);
        this.setBlockAndMetadata(world, -1, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -4, 1, 1, Blocks.cauldron, 3);
        this.placeChest(world, random, -1, 0, -1, 3, LOTRChestContents.EASTERLING_HOUSE);
        this.placeBarrel(world, random, -3, 2, -2, 2, LOTRFoods.RHUN_DRINK);
        this.placeBarrel(world, random, 0, 2, -1, 4, LOTRFoods.RHUN_DRINK);
        for(i12 = -4; i12 <= 0; ++i12) {
            for(k12 = -2; k12 <= 2; ++k12) {
                if((((i12 != -4) || (k12 < -1) || (k12 > 0)) && ((k12 != -2) || (i12 < -2) || (i12 > -1))) && (i12 != 0 || k12 < 0 || k12 > 1)) continue;
                if(random.nextBoolean()) {
                    this.placeMug(world, random, i12, 2, k12, random.nextInt(4), LOTRFoods.RHUN_DRINK);
                    continue;
                }
                this.placePlate(world, random, i12, 2, k12, this.plateBlock, LOTRFoods.RHUN);
            }
        }
        for(i12 = -3; i12 <= -1; ++i12) {
            for(k12 = 8; k12 <= 10; ++k12) {
                for(j14 = 0; j14 <= 4; ++j14) {
                    this.setBlockAndMetadata(world, i12, j14, k12, this.brickBlock, this.brickMeta);
                }
            }
            for(k12 = 8; k12 <= 9; ++k12) {
                for(j14 = 5; j14 <= 8; ++j14) {
                    this.setBlockAndMetadata(world, i12, j14, k12, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(j12 = 1; j12 <= 7; ++j12) {
            this.setAir(world, -2, j12, 9);
        }
        this.setBlockAndMetadata(world, -2, 0, 9, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, -2, 1, 9, Blocks.fire, 0);
        this.setBlockAndMetadata(world, -2, 1, 8, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 2, 8, Blocks.furnace, 2);
        this.spawnItemFrame(world, -2, 3, 8, 2, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 6, 8, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -2, 7, 8, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, 8, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 8, 8, this.brickStairBlock, 0);
        for(j12 = 5; j12 <= 7; ++j12) {
            this.setBlockAndMetadata(world, -2, j12, 10, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 8, 10, this.brickStairBlock, 3);
        for(j12 = 9; j12 <= 13; ++j12) {
            this.setBlockAndMetadata(world, -2, j12, 9, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 14, 9, Blocks.flower_pot, 0);
        for(int step4 = 0; step4 <= 3; ++step4) {
            j1 = 1 + step4;
            k13 = 4 + step4;
            for(i152 = 2; i152 <= 3; ++i152) {
                this.setAir(world, i152, 4, k13);
                this.setBlockAndMetadata(world, i152, j1, k13, this.plankStairBlock, 2);
                this.setBlockAndMetadata(world, i152, j1, k13 + 1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i152, j1, k13 + 2, this.plankStairBlock, 7);
            }
        }
        for(i12 = 1; i12 <= 3; ++i12) {
            this.setBlockAndMetadata(world, i12, 5, 3, this.fenceBlock, this.fenceMeta);
        }
        for(k15 = 4; k15 <= 6; ++k15) {
            this.setBlockAndMetadata(world, 1, 5, k15, this.fenceBlock, this.fenceMeta);
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(j1 = 5; j1 <= 7; ++j1) {
                this.setBlockAndMetadata(world, i12, j1, -2, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, i12, 8, -2, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(k15 = -8; k15 <= 8; ++k15) {
            if(k15 <= -2) {
                for(j1 = 5; j1 <= 7; ++j1) {
                    this.setBlockAndMetadata(world, 0, j1, k15, this.plankBlock, this.plankMeta);
                }
            }
            this.setBlockAndMetadata(world, 0, 8, k15, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(j12 = 5; j12 <= 7; ++j12) {
            this.setBlockAndMetadata(world, 0, j12, -2, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.placeTable(world, random, -3, -2, 5, 4, 5);
        for(i12 = -3; i12 <= -2; ++i12) {
            this.setBlockAndMetadata(world, i12, 5, 2, this.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i12, 5, 7, this.plankStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -3, 7, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 7, 8, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 5, -2, this.doorBlock, 3);
        this.setBlockAndMetadata(world, -2, 6, -2, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 2, 5, -2, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 2, 6, -2, this.doorBlock, 8);
        this.setBlockAndMetadata(world, -3, 5, -3, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 5, -5, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, -3, 6, -5, this.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, -3, 5, -6, Blocks.chest, 4);
        for(int i1521 : new int[] {-3, -1}) {
            this.setBlockAndMetadata(world, i1521, 5, -7, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i1521, 5, -8, this.bedBlock, 10);
        }
        this.spawnItemFrame(world, 0, 6, -5, 3, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
        this.setBlockAndMetadata(world, -3, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 5, -3, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 5, -5, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 6, -5, this.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, 3, 5, -6, Blocks.chest, 5);
        for(int i1521 : new int[] {1, 3}) {
            this.setBlockAndMetadata(world, i1521, 5, -7, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i1521, 5, -8, this.bedBlock, 10);
        }
        this.spawnItemFrame(world, 0, 6, -5, 1, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
        this.setBlockAndMetadata(world, 3, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 6, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 6, -8, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 6, -8, Blocks.torch, 3);
        LOTREntityEasterlingBartender bartender = new LOTREntityEasterlingBartender(world);
        bartender.setSpecificLocationName(this.tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, -2, 1, 0, 2);
        int men = 6 + random.nextInt(5);
        for(int l = 0; l < men; ++l) {
            LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 2, 1, 0, 16);
        }
        return true;
    }

    private void placeTable(World world, Random random, int i1, int i2, int j, int k1, int k2) {
        for(int i = i1; i <= i2; ++i) {
            for(int k = k1; k <= k2; ++k) {
                this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
                if(random.nextInt(3) == 0) continue;
                if(random.nextBoolean()) {
                    this.placeMug(world, random, i, j + 1, k, random.nextInt(4), LOTRFoods.RHUN_DRINK);
                    continue;
                }
                this.placePlate(world, random, i, j + 1, k, this.plateBlock, LOTRFoods.RHUN);
            }
        }
    }
}
