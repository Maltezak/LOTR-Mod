package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortress extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorFortress(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = Blocks.bed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int n;
        int j12;
        int k1;
        int k2;
        int i2;
        int k12;
        int step;
        int i12;
        int j13;
        int k13;
        int k22;
        int k14;
        int j14;
        int k152;
        this.setOriginAndRotation(world, i, j, k, rotation, 12);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -14; i13 <= 14; ++i13) {
                for(k152 = -14; k152 <= 14; ++k152) {
                    j12 = this.getTopBlock(world, i13, k152) - 1;
                    if(!this.isSurface(world, i13, j12, k152)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i14 = -11; i14 <= 11; ++i14) {
            for(k12 = -11; k12 <= 11; ++k12) {
                i2 = Math.abs(i14);
                k2 = Math.abs(k12);
                if(i2 >= 9 && i2 <= 11 && k2 <= 5 || k2 >= 9 && k2 <= 11 && i2 <= 5) {
                    boolean pillar = false;
                    if(i2 == 11) {
                        pillar = k2 == 2 || k2 == 5;
                    }
                    else if(k2 == 11) {
                        pillar = i2 == 2 || i2 == 5;
                    }
                    for(j14 = 5; (((j14 >= 0) || !this.isOpaque(world, i14, j14, k12)) && (this.getY(j14) >= 0)); --j14) {
                        if(pillar && j14 >= 1) {
                            this.setBlockAndMetadata(world, i14, j14, k12, this.pillarBlock, this.pillarMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i14, j14, k12, this.brickBlock, this.brickMeta);
                        }
                        this.setGrassToDirt(world, i14, j14 - 1, k12);
                    }
                    this.setBlockAndMetadata(world, i14, 6, k12, this.brickBlock, this.brickMeta);
                    for(j14 = 7; j14 <= 9; ++j14) {
                        this.setAir(world, i14, j14, k12);
                    }
                    if(i2 != 9 && i2 != 11 && k2 != 9 && k2 != 11) continue;
                    this.setBlockAndMetadata(world, i14, 7, k12, this.brick2WallBlock, this.brick2WallMeta);
                    if(i2 != 5 && k2 != 5) continue;
                    this.setBlockAndMetadata(world, i14, 8, k12, Blocks.torch, 5);
                    continue;
                }
                for(j12 = 0; (((j12 == 0) || !this.isOpaque(world, i14, j12, k12)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i14, j12, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i14, j12 - 1, k12);
                }
                for(j12 = 1; j12 <= 9; ++j12) {
                    this.setAir(world, i14, j12, k12);
                }
            }
        }
        for(int i15 : new int[] {-10, 10}) {
            for(int k11 : new int[] {-10, 10}) {
                int j15;
                for(int i22 = i15 - 4; i22 <= i15 + 4; ++i22) {
                    for(k22 = k11 - 4; k22 <= k11 + 4; ++k22) {
                        int j16;
                        int i3 = Math.abs(i22 - i15);
                        int k3 = Math.abs(k22 - k11);
                        int i4 = Math.abs(i22);
                        int k4 = Math.abs(k22);
                        if(i3 == 4 && k3 >= 3 || k3 == 4 && i3 >= 3) continue;
                        if(i3 == 4 && k3 <= 2 || k3 == 4 && i3 <= 2 || i3 == 3 && k3 == 3) {
                            int j17;
                            boolean pillar = false;
                            if(i3 == 4) {
                                pillar = k3 == 2;
                            }
                            else if(k3 == 4) {
                                pillar = i3 == 2;
                            }
                            for(j17 = 5; (((j17 >= 0) || !this.isOpaque(world, i22, j17, k22)) && (this.getY(j17) >= 0)); --j17) {
                                if(pillar && j17 >= 1) {
                                    this.setBlockAndMetadata(world, i22, j17, k22, this.pillarBlock, this.pillarMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i22, j17, k22, this.brickBlock, this.brickMeta);
                                }
                                this.setGrassToDirt(world, i22, j17 - 1, k22);
                            }
                            this.setBlockAndMetadata(world, i22, 6, k22, this.brickBlock, this.brickMeta);
                            for(j17 = 7; j17 <= 9; ++j17) {
                                this.setAir(world, i22, j17, k22);
                            }
                            if(i3 <= 1 || k3 <= 1) {
                                this.setBlockAndMetadata(world, i22, 7, k22, this.brick2WallBlock, this.brick2WallMeta);
                                if(i4 != 10 && k4 != 10) continue;
                                if(i4 <= 10 && k4 <= 10) {
                                    this.setAir(world, i22, 7, k22);
                                    continue;
                                }
                                this.setBlockAndMetadata(world, i22, 8, k22, Blocks.torch, 5);
                                continue;
                            }
                            this.setBlockAndMetadata(world, i22, 7, k22, this.brick2Block, this.brick2Meta);
                            this.setBlockAndMetadata(world, i22, 8, k22, this.brick2SlabBlock, this.brick2SlabMeta);
                            continue;
                        }
                        for(j16 = 0; (((j16 == 0) || !this.isOpaque(world, i22, j16, k22)) && (this.getY(j16) >= 0)); --j16) {
                            this.setBlockAndMetadata(world, i22, j16, k22, this.brickBlock, this.brickMeta);
                            this.setGrassToDirt(world, i22, j16 - 1, k22);
                        }
                        for(j16 = 1; j16 <= 9; ++j16) {
                            this.setAir(world, i22, j16, k22);
                        }
                        this.setBlockAndMetadata(world, i22, 6, k22, this.plankBlock, this.plankMeta);
                        if((i4 != 9 || k4 != 9) && (i4 != 11 || k4 != 11)) continue;
                        this.setBlockAndMetadata(world, i22, 5, k22, LOTRMod.chandelier, 2);
                    }
                }
                for(j15 = 1; j15 <= 8; ++j15) {
                    this.setBlockAndMetadata(world, i15, j15, k11, this.woodBeamBlock, this.woodBeamMeta);
                }
                this.setBlockAndMetadata(world, i15, 9, k11, this.plankSlabBlock, this.plankSlabMeta);
                this.setBlockAndMetadata(world, i15, 8, k11 - 1, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i15, 8, k11 + 1, Blocks.torch, 3);
                this.setBlockAndMetadata(world, i15 - 1, 8, k11, Blocks.torch, 1);
                this.setBlockAndMetadata(world, i15 + 1, 8, k11, Blocks.torch, 2);
                if(i15 < 0) {
                    for(j15 = 1; j15 <= 5; ++j15) {
                        this.setBlockAndMetadata(world, i15 + 1, j15, k11, Blocks.ladder, 4);
                    }
                    this.setBlockAndMetadata(world, i15 + 1, 6, k11, Blocks.trapdoor, 11);
                }
                if(i15 > 0) {
                    for(j15 = 1; j15 <= 5; ++j15) {
                        this.setBlockAndMetadata(world, i15 - 1, j15, k11, Blocks.ladder, 5);
                    }
                    this.setBlockAndMetadata(world, i15 - 1, 6, k11, Blocks.trapdoor, 10);
                }
                if(k11 < 0) {
                    for(j15 = 1; j15 <= 5; ++j15) {
                        this.setBlockAndMetadata(world, i15, j15, k11 + 1, Blocks.ladder, 3);
                    }
                    this.setBlockAndMetadata(world, i15, 6, k11 + 1, Blocks.trapdoor, 8);
                }
                if(k11 <= 0) continue;
                for(j15 = 1; j15 <= 5; ++j15) {
                    this.setBlockAndMetadata(world, i15, j15, k11 - 1, Blocks.ladder, 2);
                }
                this.setBlockAndMetadata(world, i15, 6, k11 - 1, Blocks.trapdoor, 9);
            }
        }
        for(int i15 : new int[] {-11, 11}) {
            int i23 = i15 + Integer.signum(i15) * -1;
            int[] j18 = new int[] {-4, 3};
            n = j18.length;
            for(k1 = 0; k1 < n; ++k1) {
                k13 = j18[k1];
                this.setBlockAndMetadata(world, i15, 2, k13, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i15, 2, k13 + 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i15, 4, k13, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i15, 4, k13 + 1, this.brickStairBlock, 6);
                for(k22 = k13; k22 <= k13 + 1; ++k22) {
                    this.setAir(world, i15, 3, k22);
                    this.setBlockAndMetadata(world, i23, 3, k22, LOTRMod.brick, 5);
                }
            }
            this.setBlockAndMetadata(world, i15, 2, -1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i15, 2, 0, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, i15, 2, 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i15, 4, -1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 4, 0, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i15, 4, 1, this.brickStairBlock, 6);
            for(int k23 = -1; k23 <= 1; ++k23) {
                this.setAir(world, i15, 3, k23);
                this.setBlockAndMetadata(world, i23, 3, k23, LOTRMod.brick, 5);
            }
        }
        for(int k1521 : new int[] {-11, 11}) {
            int k24 = k1521 + Integer.signum(k1521) * -1;
            int[] k23 = new int[] {-4, 3};
            n = k23.length;
            for(k1 = 0; k1 < n; ++k1) {
                i12 = k23[k1];
                this.setBlockAndMetadata(world, i12, 2, k1521, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i12 + 1, 2, k1521, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i12, 4, k1521, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i12 + 1, 4, k1521, this.brickStairBlock, 5);
                for(int i24 = i12; i24 <= i12 + 1; ++i24) {
                    this.setAir(world, i24, 3, k1521);
                    this.setBlockAndMetadata(world, i24, 3, k24, LOTRMod.brick, 5);
                }
            }
            if(k1521 <= 0) continue;
            this.setBlockAndMetadata(world, -1, 2, k1521, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 0, 2, k1521, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, 1, 2, k1521, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 4, k1521, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 4, k1521, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, 1, 4, k1521, this.brickStairBlock, 5);
            for(int i25 = -1; i25 <= 1; ++i25) {
                this.setAir(world, i25, 3, k1521);
                this.setBlockAndMetadata(world, i25, 3, k24, LOTRMod.brick, 5);
            }
        }
        for(int k1521 : new int[] {-14, 14}) {
            int k25 = k1521 + Integer.signum(k1521) * -1;
            int[] i25 = new int[] {-10, 10};
            n = i25.length;
            for(k1 = 0; k1 < n; ++k1) {
                i12 = i25[k1];
                this.setBlockAndMetadata(world, i12 - 1, 3, k1521, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i12, 3, k1521, this.brick2WallBlock, this.brick2WallMeta);
                this.setBlockAndMetadata(world, i12 + 1, 3, k1521, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i12 - 1, 4, k1521, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i12, 4, k1521, this.brick2WallBlock, this.brick2WallMeta);
                this.setBlockAndMetadata(world, i12 + 1, 4, k1521, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i12 - 1, 1, k25, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i12, 1, k25, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i12 + 1, 1, k25, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i12, 2, k25, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for(int i15 : new int[] {-14, 14}) {
            int i26 = i15 + Integer.signum(i15) * -1;
            int[] i25 = new int[] {-10, 10};
            n = i25.length;
            for(k1 = 0; k1 < n; ++k1) {
                k13 = i25[k1];
                this.setBlockAndMetadata(world, i15, 3, k13 - 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i15, 3, k13, this.brick2WallBlock, this.brick2WallMeta);
                this.setBlockAndMetadata(world, i15, 3, k13 + 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i15, 4, k13 - 1, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i15, 4, k13, this.brick2WallBlock, this.brick2WallMeta);
                this.setBlockAndMetadata(world, i15, 4, k13 + 1, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i26, 1, k13 - 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i26, 1, k13, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i26, 1, k13 + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i26, 2, k13, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        for(int i15 : new int[] {-10, 10}) {
            for(int i27 : new int[] {i15 - 2, i15 + 2}) {
                this.placeArmorStand(world, i27, 1, -7, 0, null);
                this.placeArmorStand(world, i27, 1, 7, 2, null);
            }
            this.placeChest(world, random, i15, 1, -6, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, i15, 2, -6);
            this.spawnItemFrame(world, i15, 3, -6, 2, this.getGondorFramedItem(random));
            this.placeChest(world, random, i15, 1, 6, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, i15, 2, 6);
            this.spawnItemFrame(world, i15, 3, 6, 0, this.getGondorFramedItem(random));
        }
        int[] i14 = new int[] {-10, 10};
        k12 = i14.length;
        for(i2 = 0; i2 < k12; ++i2) {
            k152 = i14[i2];
            for(int k26 : new int[] {k152 - 2, k152 + 2}) {
                this.placeArmorStand(world, -7, 1, k26, 1, null);
                this.placeArmorStand(world, 7, 1, k26, 3, null);
            }
            this.placeChest(world, random, -6, 1, k152, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, -6, 2, k152);
            this.spawnItemFrame(world, -6, 3, k152, 3, this.getGondorFramedItem(random));
            this.placeChest(world, random, 6, 1, k152, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
            this.setAir(world, 6, 2, k152);
            this.spawnItemFrame(world, 6, 3, k152, 1, this.getGondorFramedItem(random));
        }
        for(j13 = 1; j13 <= 4; ++j13) {
            for(int i16 = -1; i16 <= 1; ++i16) {
                this.setBlockAndMetadata(world, i16, j13, -10, this.gateBlock, 2);
                this.setAir(world, i16, j13, -9);
                this.setAir(world, i16, j13, -11);
            }
            this.setBlockAndMetadata(world, -2, j13, -9, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 2, j13, -9, this.pillarBlock, this.pillarMeta);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            k12 = -12;
            for(int j19 = 0; (((j19 <= 0) || !this.isOpaque(world, i1, j19, k12)) && (this.getY(j19) >= 0)); --j19) {
                this.setBlockAndMetadata(world, i1, j19, k12, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j19 - 1, k12);
            }
        }
        this.placeWallBanner(world, -2, 4, -11, this.bannerType2, 2);
        this.placeWallBanner(world, 2, 4, -11, this.bannerType, 2);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = -2; k12 <= 2; ++k12) {
                this.setBlockAndMetadata(world, i1, 0, k12, this.brick2Block, this.brick2Meta);
            }
        }
        for(i1 = -8; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 0, this.brick2Block, this.brick2Meta);
        }
        for(k14 = -12; k14 <= 8; ++k14) {
            this.setBlockAndMetadata(world, 0, 0, k14, this.brick2Block, this.brick2Meta);
        }
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.brick4, 6);
        for(j13 = 1; j13 <= 4; ++j13) {
            this.setBlockAndMetadata(world, -1, j13, -1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 1, j13, -1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, -1, j13, 1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 1, j13, 1, this.brickWallBlock, this.brickWallMeta);
        }
        this.setBlockAndMetadata(world, -1, 5, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 5, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 5, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 5, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 5, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 5, 1, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 5, 1, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 5, 1, this.brickStairBlock, 3);
        for(j13 = 6; j13 <= 9; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, 0, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.brick, 5);
        this.setBlockAndMetadata(world, 0, 11, 0, LOTRMod.beacon, 0);
        this.placeWallBanner(world, 0, 9, 0, this.bannerType, 0);
        this.placeWallBanner(world, 0, 9, 0, this.bannerType2, 1);
        this.placeWallBanner(world, 0, 9, 0, this.bannerType, 2);
        this.placeWallBanner(world, 0, 9, 0, this.bannerType2, 3);
        this.setBlockAndMetadata(world, 0, 4, 0, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 3, -8, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -3, 4, -8, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 3, 3, -8, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 3, 4, -8, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -8, 3, -3, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -8, 4, -3, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 8, 3, -3, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 8, 4, -3, Blocks.torch, 5);
        for(i1 = -7; i1 <= 7; ++i1) {
            int i28 = Math.abs(i1);
            if(i28 < 2) continue;
            for(int k16 = -7; k16 <= -2; ++k16) {
                k2 = k16 - -9;
                int d = Math.abs(i28 - k2);
                if(d == 0 && (i28 == 2 || i28 == 7)) {
                    d = 2;
                }
                if(d > 2) continue;
                this.setBlockAndMetadata(world, i1, 0, k16, Blocks.grass, 0);
                if(d == 0) {
                    this.setBlockAndMetadata(world, i1, 1, k16, Blocks.double_plant, 4);
                    this.setBlockAndMetadata(world, i1, 2, k16, Blocks.double_plant, 8);
                    continue;
                }
                if(d == 1) {
                    this.setBlockAndMetadata(world, i1, 1, k16, Blocks.red_flower, 6);
                    continue;
                }
                if(d != 2) continue;
                this.setBlockAndMetadata(world, i1, 1, k16, Blocks.red_flower, 4);
            }
        }
        this.setBlockAndMetadata(world, -7, 0, 1, this.brick2Block, this.brick2Meta);
        for(step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -7, 1 + step, 2 + step, this.brickStairBlock, 2);
            for(j1 = 1; j1 < 1 + step; ++j1) {
                this.setBlockAndMetadata(world, -7, j1, 2 + step, this.brickBlock, this.brickMeta);
            }
        }
        for(j13 = 1; j13 <= 3; ++j13) {
            this.setBlockAndMetadata(world, -7, j13, 5, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -7, j13, 6, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -8, j13, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -8, j13, 5, this.brickBlock, this.brickMeta);
        }
        for(step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -8, 4 + step, 3 - step, this.brickStairBlock, 3);
            for(j1 = 1; j1 < 4 + step; ++j1) {
                this.setBlockAndMetadata(world, -8, j1, 3 - step, this.brickBlock, this.brickMeta);
            }
        }
        for(k14 = -1; k14 <= 0; ++k14) {
            this.setBlockAndMetadata(world, -8, 5, k14, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, -8, 6, k14, this.brickBlock, this.brickMeta);
        }
        this.setAir(world, -9, 7, 0);
        this.setAir(world, -9, 7, 1);
        this.setBlockAndMetadata(world, -8, 7, -1, this.brick2WallBlock, this.brick2WallMeta);
        for(i1 = 6; i1 <= 8; ++i1) {
            for(k12 = -1; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, i1, 0, k12, this.brick2Block, this.brick2Meta);
                if(i1 >= 7 && k12 >= 0 && k12 <= 2) {
                    this.setBlockAndMetadata(world, i1, 4, k12, this.plankSlabBlock, this.plankSlabMeta | 8);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 4, k12, this.plankSlabBlock, this.plankSlabMeta);
            }
        }
        for(j13 = 1; j13 <= 3; ++j13) {
            this.setBlockAndMetadata(world, 6, j13, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 6, j13, 3, this.fenceBlock, this.fenceMeta);
        }
        for(i1 = 7; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 3, 3, this.fenceBlock, this.fenceMeta);
        }
        for(k14 = 0; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, 6, 3, k14, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, 8, 1, -1, LOTRMod.alloyForge, 5);
        this.setBlockAndMetadata(world, 8, 2, -1, Blocks.furnace, 5);
        this.placeArmorStand(world, 8, 1, 0, 1, this.strFief.getFiefdomArmor());
        this.setBlockAndMetadata(world, 8, 1, 1, this.tableBlock, 0);
        this.placeChest(world, random, 8, 1, 2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        this.setBlockAndMetadata(world, 8, 1, 3, Blocks.crafting_table, 0);
        this.spawnItemFrame(world, 9, 2, 1, 3, this.getGondorFramedItem(random));
        this.spawnItemFrame(world, 9, 2, 2, 3, this.getGondorFramedItem(random));
        this.setBlockAndMetadata(world, 6, 1, 1, Blocks.anvil, 0);
        this.setBlockAndMetadata(world, 8, 3, 1, Blocks.torch, 1);
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k12 = 4; k12 <= 8; ++k12) {
                int j110;
                i2 = Math.abs(i1);
                if(i2 == 5 && k12 == 4) continue;
                if(i2 <= 4 && k12 >= 5) {
                    this.setBlockAndMetadata(world, i1, 0, k12, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i1, 4, k12, this.brickBlock, this.brickMeta);
                    continue;
                }
                if(i2 == 1 || i2 == 4 || k12 == 5) {
                    for(j110 = 1; j110 <= 3; ++j110) {
                        this.setBlockAndMetadata(world, i1, j110, k12, this.woodBeamBlock, this.woodBeamMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i1, 1, k12, this.brickBlock, this.brickMeta);
                    for(j110 = 2; j110 <= 3; ++j110) {
                        this.setBlockAndMetadata(world, i1, j110, k12, this.plankBlock, this.plankMeta);
                    }
                }
                this.setBlockAndMetadata(world, i1, 4, k12, this.brickSlabBlock, this.brickSlabMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, 4, this.doorBlock, 8);
        for(i1 = -4; i1 <= 4; ++i1) {
            if(IntMath.mod(i1, 2) == 0) {
                this.setBlockAndMetadata(world, i1, 1, 7, this.bedBlock, 0);
                this.setBlockAndMetadata(world, i1, 1, 8, this.bedBlock, 8);
                continue;
            }
            this.placeChest(world, random, i1, 1, 8, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        }
        this.placeWallBanner(world, -2, 3, 9, this.bannerType, 2);
        this.placeWallBanner(world, 2, 3, 9, this.bannerType, 2);
        this.setBlockAndMetadata(world, -4, 1, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 5, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, -4, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 4, 1, 5, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 2, 5, this.plateBlock, LOTRFoods.GONDOR);
        this.placePlateWithCertainty(world, random, 4, 2, 5, this.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -3, 3, 6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 3, 3, 6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -5, 1, 2, LOTRMod.commandTable, 0);
        LOTREntityGondorMan captain = this.strFief.createCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
        Class[] soldierClasses = this.strFief.getSoldierClasses();
        int soldiers = 4 + random.nextInt(4);
        for(int l = 0; l < soldiers; ++l) {
            Class entityClass = soldierClasses[0];
            if(random.nextInt(3) == 0) {
                entityClass = soldierClasses[1];
            }
            LOTREntityGondorMan soldier = (LOTREntityGondorMan) LOTREntities.createEntityByClass(entityClass, world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
        respawner.setCheckRanges(20, -8, 12, 12);
        respawner.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
