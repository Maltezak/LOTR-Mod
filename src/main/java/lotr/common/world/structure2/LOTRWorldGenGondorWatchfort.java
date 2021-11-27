package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWatchfort extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorWatchfort(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.barsBlock = Blocks.iron_bars;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int i12;
        int i13;
        int i14;
        int j12;
        int i15;
        int i16;
        int step;
        int k1;
        int j13;
        int i172;
        int j2;
        int j14;
        int k12;
        int k13;
        int j15;
        int k14;
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int x1 = -6;
            int x2 = 6;
            int z1 = -6;
            int z2 = 34;
            for(int i18 = x1; i18 <= x2; ++i18) {
                for(k1 = z1; k1 <= z2; ++k1) {
                    j1 = this.getTopBlock(world, i18, k1) - 1;
                    if(this.isSurface(world, i18, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i16 = -5; i16 <= 5; ++i16) {
            for(j12 = 1; j12 <= 11; ++j12) {
                for(k14 = -5; k14 <= 5; ++k14) {
                    if(Math.abs(i16) == 5 && Math.abs(k14) == 5) {
                        this.setBlockAndMetadata(world, i16, j12, k14, this.pillar2Block, this.pillar2Meta);
                        continue;
                    }
                    this.placeRandomBrick(world, random, i16, j12, k14);
                }
            }
        }
        for(i16 = -6; i16 <= 6; ++i16) {
            this.setBlockAndMetadata(world, i16, 1, -6, this.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, i16, 1, 6, this.brick2StairBlock, 3);
        }
        for(int k15 = -5; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, -6, 1, k15, this.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, 6, 1, k15, this.brick2StairBlock, 0);
        }
        for(i16 = -6; i16 <= 6; ++i16) {
            for(k12 = -6; k12 <= 6; ++k12) {
                j14 = 0;
                while(!this.isOpaque(world, i16, j14, k12) && this.getY(j14) >= 0) {
                    this.placeRandomBrick(world, random, i16, j14, k12);
                    this.setGrassToDirt(world, i16, j14 - 1, k12);
                    --j14;
                }
            }
        }
        for(i16 = -4; i16 <= 4; ++i16) {
            for(k12 = -4; k12 <= 4; ++k12) {
                for(j14 = 2; j14 <= 5; ++j14) {
                    this.setAir(world, i16, j14, k12);
                }
                for(j14 = 7; j14 <= 10; ++j14) {
                    this.setAir(world, i16, j14, k12);
                }
            }
        }
        int[] i19 = new int[] {4, 9};
        k12 = i19.length;
        for(j14 = 0; j14 < k12; ++j14) {
            j15 = i19[j14];
            this.setBlockAndMetadata(world, -4, j15, -2, Blocks.torch, 2);
            this.setBlockAndMetadata(world, -4, j15, 2, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 4, j15, -2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 4, j15, 2, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -2, j15, -4, Blocks.torch, 3);
            this.setBlockAndMetadata(world, 2, j15, -4, Blocks.torch, 3);
            this.setBlockAndMetadata(world, -2, j15, 4, Blocks.torch, 4);
            this.setBlockAndMetadata(world, 2, j15, 4, Blocks.torch, 4);
        }
        for(i14 = -4; i14 <= 4; ++i14) {
            for(j12 = 12; j12 <= 16; ++j12) {
                for(k14 = -4; k14 <= 4; ++k14) {
                    if(Math.abs(i14) == 4 && Math.abs(k14) == 4) {
                        this.setBlockAndMetadata(world, i14, j12, k14, this.pillar2Block, this.pillar2Meta);
                        continue;
                    }
                    this.placeRandomBrick(world, random, i14, j12, k14);
                }
            }
        }
        for(i14 = -5; i14 <= 5; ++i14) {
            this.setBlockAndMetadata(world, i14, 12, -5, this.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, i14, 12, 5, this.brick2StairBlock, 3);
        }
        for(k13 = -4; k13 <= 4; ++k13) {
            this.setBlockAndMetadata(world, -5, 12, k13, this.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, 5, 12, k13, this.brick2StairBlock, 0);
        }
        for(i14 = -3; i14 <= 3; ++i14) {
            for(k12 = -3; k12 <= 3; ++k12) {
                for(j14 = 12; j14 <= 15; ++j14) {
                    this.setAir(world, i14, j14, k12);
                }
            }
        }
        this.setBlockAndMetadata(world, -3, 14, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 14, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 14, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 14, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 14, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 14, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 14, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 14, 3, Blocks.torch, 4);
        for(i14 = -4; i14 <= 4; ++i14) {
            this.placeRandomWall(world, random, i14, 17, -4);
            this.placeRandomWall(world, random, i14, 17, 4);
        }
        for(k13 = -4; k13 <= 4; ++k13) {
            this.placeRandomWall(world, random, -4, 17, k13);
            this.placeRandomWall(world, random, 4, 17, k13);
        }
        int[] k16 = new int[] {-4, 4};
        k12 = k16.length;
        for(j14 = 0; j14 < k12; ++j14) {
            i172 = k16[j14];
            for(int k17 : new int[] {-4, 4}) {
                for(int j16 = 17; j16 <= 19; ++j16) {
                    this.setBlockAndMetadata(world, i172, j16, k17, this.pillar2Block, this.pillar2Meta);
                }
                this.placeRandomBrick(world, random, i172, 20, k17);
            }
        }
        this.setBlockAndMetadata(world, -4, 19, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 4, 19, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 19, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 19, 3, Blocks.torch, 4);
        for(i15 = -2; i15 <= 2; ++i15) {
            for(k12 = -2; k12 <= 2; ++k12) {
                this.setBlockAndMetadata(world, i15, 21, k12, this.brick2Block, this.brick2Meta);
                if(Math.abs(i15) <= 1 && Math.abs(k12) <= 1) {
                    this.setBlockAndMetadata(world, i15, 22, k12, this.brick2Block, this.brick2Meta);
                    continue;
                }
                this.setBlockAndMetadata(world, i15, 22, k12, this.brick2SlabBlock, this.brick2SlabMeta);
            }
        }
        for(i15 = -5; i15 <= 5; ++i15) {
            for(k12 = -5; k12 <= 5; ++k12) {
                this.setBlockAndMetadata(world, i15, 21, k12, this.brick2SlabBlock, this.brick2SlabMeta);
            }
        }
        int[] i110 = new int[] {-4, 4};
        k12 = i110.length;
        for(j14 = 0; j14 < k12; ++j14) {
            i172 = i110[j14];
            for(int k17 : new int[] {-4, 4}) {
                this.setBlockAndMetadata(world, i172, 20, k17 - 1, this.brick2StairBlock, 6);
                this.setBlockAndMetadata(world, i172, 20, k17 + 1, this.brick2StairBlock, 7);
                for(int k2 = k17 - 1; k2 <= k17 + 1; ++k2) {
                    this.setBlockAndMetadata(world, i172 - 1, 20, k2, this.brick2StairBlock, 5);
                    this.setBlockAndMetadata(world, i172 + 1, 20, k2, this.brick2StairBlock, 4);
                }
            }
        }
        this.setBlockAndMetadata(world, -4, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, -3, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, -2, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -4, 21, -1, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -4, 21, 0, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, 1, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -4, 21, 2, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -4, 21, 3, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -4, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -3, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -3, 21, -3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, -2, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, -1, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 0, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 1, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, -3, 21, 2, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, 3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -3, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -3, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, -2, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 4, 21, -1, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 4, 21, 0, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, 1, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 4, 21, 2, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 4, 21, 3, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 4, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 3, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 3, 21, -3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, -2, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, -1, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 0, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 1, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 3, 21, 2, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, 3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 3, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -2, 21, 4, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -1, 21, 4, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 0, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 1, 21, 4, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, 2, 21, 4, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, -2, 21, 3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -1, 21, 3, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 0, 21, 3, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 1, 21, 3, this.brick2StairBlock, 3);
        this.setBlockAndMetadata(world, 2, 21, 3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -2, 21, -4, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -1, 21, -4, this.brick2StairBlock, 0);
        this.setBlockAndMetadata(world, 0, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 1, 21, -4, this.brick2StairBlock, 1);
        this.setBlockAndMetadata(world, 2, 21, -4, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, -2, 21, -3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, -1, 21, -3, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 0, 21, -3, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 1, 21, -3, this.brick2StairBlock, 2);
        this.setBlockAndMetadata(world, 2, 21, -3, this.brick2Block, this.brick2Meta);
        this.placeBarredWindowOnZ(world, -5, 3, 0);
        this.placeBarredWindowOnZ(world, 5, 3, 0);
        this.placeBarredWindowOnX(world, 0, 3, -5);
        this.placeBarredWindowOnX(world, 0, 3, 5);
        this.placeBarredWindowOnZ(world, -5, 8, 0);
        this.placeBarredWindowOnZ(world, 5, 8, 0);
        this.placeBarredWindowOnX(world, 0, 8, -5);
        this.placeBarredWindowOnX(world, 0, 8, 5);
        this.placeBarredWindowOnZ(world, -4, 13, 0);
        this.placeBarredWindowOnZ(world, 4, 13, 0);
        this.placeBarredWindowOnX(world, 0, 13, -4);
        this.placeBarredWindowOnX(world, 0, 13, 4);
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k12 = -8; k12 <= -7; ++k12) {
                j14 = 0;
                while(!this.isOpaque(world, i12, j14, k12) && this.getY(j14) >= 0) {
                    this.placeRandomBrick(world, random, i12, j14, k12);
                    this.setGrassToDirt(world, i12, j14 - 1, k12);
                    --j14;
                }
            }
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            if(Math.abs(i12) == 2) {
                for(j12 = 1; j12 <= 4; ++j12) {
                    this.setBlockAndMetadata(world, i12, j12, -6, this.pillarBlock, this.pillarMeta);
                }
            }
            this.setBlockAndMetadata(world, i12, 5, -6, this.brick2SlabBlock, this.brick2SlabMeta);
            this.placeRandomStairs(world, random, i12, 1, -8, 2);
        }
        this.placeWallBanner(world, 0, 7, -5, this.bannerType, 2);
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k12 = -7; k12 <= -6; ++k12) {
                this.placeRandomBrick(world, random, i12, 1, k12);
            }
            this.placeRandomBrick(world, random, i12, 1, -5);
            this.setAir(world, i12, 2, -5);
            this.setAir(world, i12, 3, -5);
            this.setAir(world, i12, 4, -5);
        }
        this.placeRandomStairs(world, random, -2, 1, -7, 1);
        this.placeRandomStairs(world, random, 2, 1, -7, 0);
        for(i12 = -1; i12 <= 1; ++i12) {
            for(j12 = 2; j12 <= 4; ++j12) {
                this.setBlockAndMetadata(world, i12, j12, -6, this.gateBlock, 3);
            }
        }
        this.placeRandomSlab(world, random, -4, 2, -4, true);
        this.placeBarrel(world, random, -4, 3, -4, 4, LOTRFoods.GONDOR_DRINK);
        this.placeRandomSlab(world, random, -4, 2, -3, true);
        this.placeBarrel(world, random, -4, 3, -3, 4, LOTRFoods.GONDOR_DRINK);
        this.placeChest(world, random, -4, 2, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        this.placeRandomSlab(world, random, 4, 2, -4, true);
        this.placeBarrel(world, random, 4, 3, -4, 5, LOTRFoods.GONDOR_DRINK);
        this.placeRandomSlab(world, random, 4, 2, -3, true);
        this.placeBarrel(world, random, 4, 3, -3, 5, LOTRFoods.GONDOR_DRINK);
        this.placeChest(world, random, 4, 2, -2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        this.setBlockAndMetadata(world, -4, 2, 4, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 4, 2, 4, this.tableBlock, 0);
        for(i12 = -1; i12 <= 1; ++i12) {
            for(step = 0; step <= 3; ++step) {
                k14 = -1 + step;
                j15 = 2 + step;
                this.setAir(world, i12, 6, k14);
                for(j2 = 2; j2 < j15; ++j2) {
                    this.placeRandomBrick(world, random, i12, j2, k14);
                }
                this.placeRandomStairs(world, random, i12, j15, k14, 2);
            }
            this.placeRandomStairs(world, random, i12, 6, 3, 2);
        }
        this.placeChest(world, random, 0, 2, 2, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        this.setAir(world, 0, 3, 2);
        this.setBlockAndMetadata(world, 0, 7, -4, LOTRMod.commandTable, 0);
        int[] i111 = new int[] {-3, 3};
        step = i111.length;
        for(k14 = 0; k14 < step; ++k14) {
            i172 = i111[k14];
            for(int step2 = 0; step2 <= 4; ++step2) {
                k1 = 2 - step2;
                j1 = 7 + step2;
                this.setAir(world, i172, 11, k1);
                for(int j22 = 7; j22 < j1; ++j22) {
                    this.placeRandomBrick(world, random, i172, j22, k1);
                }
                this.placeRandomStairs(world, random, i172, j1, k1, 3);
            }
        }
        for(i13 = -1; i13 <= 1; ++i13) {
            for(step = 0; step <= 3; ++step) {
                k14 = -2 + step;
                j15 = 12 + step;
                this.setAir(world, i13, 16, k14);
                for(j2 = 12; j2 < j15; ++j2) {
                    this.placeRandomBrick(world, random, i13, j2, k14);
                }
                this.placeRandomStairs(world, random, i13, j15, k14, 2);
            }
            this.placeRandomStairs(world, random, i13, 16, 2, 2);
        }
        for(int k18 = 5; k18 <= 28; ++k18) {
            for(j12 = 12; j12 <= 15; ++j12) {
                for(int i112 = -2; i112 <= 2; ++i112) {
                    this.setAir(world, i112, j12, k18);
                }
            }
        }
        for(i13 = -1; i13 <= 1; ++i13) {
            this.placeRandomBrick(world, random, i13, 13, 4);
            this.placeRandomBrick(world, random, i13, 14, 4);
        }
        for(int i1721 : new int[] {-2, 2}) {
            this.placeRandomBrick(world, random, i1721, 12, 5);
            this.placeRandomBrick(world, random, i1721, 13, 5);
            this.setBlockAndMetadata(world, i1721, 14, 5, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, i1721, 15, 5, Blocks.torch, 5);
        }
        this.setBlockAndMetadata(world, 0, 12, 4, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 13, 4, this.doorBlock, 8);
        for(int k19 = 6; k19 <= 28; ++k19) {
            for(int i113 = -1; i113 <= 1; ++i113) {
                this.placeRandomBrick(world, random, i113, 11, k19);
            }
            this.placeRandomWall(world, random, -2, 12, k19);
            this.placeRandomWall(world, random, 2, 12, k19);
            this.placeRandomStairs(world, random, -2, 11, k19, 5);
            this.placeRandomStairs(world, random, 2, 11, k19, 4);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.placeRandomStairs(world, random, i1, 10, 6, 7);
            this.placeRandomStairs(world, random, i1, 10, 16, 6);
            this.placeRandomStairs(world, random, i1, 10, 18, 7);
            this.placeRandomStairs(world, random, i1, 10, 28, 6);
            j12 = 10;
            while(!this.isOpaque(world, i1, j12, 17) && this.getY(j12) >= 0) {
                this.placeRandomBrick(world, random, i1, j12, 17);
                this.setGrassToDirt(world, i1, j12 - 1, 17);
                --j12;
            }
        }
        for(j13 = 12; j13 <= 13; ++j13) {
            this.placeRandomBrick(world, random, -2, j13, 11);
            this.placeRandomBrick(world, random, 2, j13, 11);
            this.placeRandomBrick(world, random, -2, j13, 23);
            this.placeRandomBrick(world, random, 2, j13, 23);
        }
        this.setBlockAndMetadata(world, -1, 13, 11, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 13, 11, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 13, 23, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 13, 23, Blocks.torch, 1);
        this.placeBanner(world, -2, 14, 11, this.bannerType, 3);
        this.placeBanner(world, 2, 14, 11, this.bannerType, 1);
        this.placeBanner(world, -2, 14, 23, this.bannerType, 3);
        this.placeBanner(world, 2, 14, 23, this.bannerType, 1);
        for(j13 = 12; j13 <= 14; ++j13) {
            this.placeRandomBrick(world, random, -2, j13, 17);
            this.placeRandomBrick(world, random, 2, j13, 17);
        }
        this.placeRandomBrick(world, random, -2, 15, 17);
        this.placeRandomBrick(world, random, 2, 15, 17);
        this.placeRandomStairs(world, random, -1, 15, 17, 4);
        this.placeRandomStairs(world, random, 1, 15, 17, 5);
        this.placeRandomSlab(world, random, 0, 15, 17, true);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 16, 17, this.brick2Block, this.brick2Meta);
        }
        this.setBlockAndMetadata(world, -2, 16, 17, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 2, 16, 17, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, 0, 17, 17, this.brick2SlabBlock, this.brick2SlabMeta);
        this.setBlockAndMetadata(world, -2, 14, 16, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 14, 16, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 14, 18, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 14, 18, Blocks.torch, 3);
        LOTRWorldGenBeaconTower beaconTower = new LOTRWorldGenBeaconTower(this.notifyChanges);
        beaconTower.restrictions = false;
        beaconTower.generateRoom = false;
        beaconTower.strFief = this.strFief;
        int beaconX = 0;
        int beaconY = 12;
        int beaconZ = 34;
        beaconTower.generateWithSetRotationAndHeight(world, random, this.getX(beaconX, beaconZ), this.getY(beaconY), this.getZ(beaconX, beaconZ), (this.getRotationMode() + 2) % 4, -8);
        this.setAir(world, -1, 12, 29);
        this.setAir(world, 0, 12, 29);
        this.setAir(world, 1, 12, 29);
        Class[] soldierClasses = this.strFief.getSoldierClasses();
        int soldiers = 4 + random.nextInt(4);
        for(int l = 0; l < soldiers; ++l) {
            Class entityClass = soldierClasses[0];
            if(random.nextInt(3) == 0) {
                entityClass = soldierClasses[1];
            }
            LOTREntityGondorMan soldier = (LOTREntityGondorMan) LOTREntities.createEntityByClass(entityClass, world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 2, -3, 32);
        }
        LOTREntityGondorMan captain = this.strFief.createCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 15, 0, 8);
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
        respawner.setCheckRanges(24, -8, 18, 12);
        respawner.setSpawnRanges(4, 2, 17, 32);
        this.placeNPCRespawner(respawner, world, 0, 2, 0);
        return true;
    }

    private void placeRandomBrick(World world, Random random, int i, int j, int k) {
        if(random.nextInt(10) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossyBlock, this.brickMossyMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedBlock, this.brickCrackedMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
        }
    }

    private void placeRandomSlab(World world, Random random, int i, int j, int k, boolean inverted) {
        int flag;
        flag = inverted ? 8 : 0;
        if(random.nextInt(10) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossySlabBlock, this.brickMossySlabMeta | flag);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta | flag);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickSlabBlock, this.brickSlabMeta | flag);
        }
    }

    private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
        if(random.nextInt(10) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossyStairBlock, meta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedStairBlock, meta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
        }
    }

    private void placeRandomWall(World world, Random random, int i, int j, int k) {
        if(random.nextInt(10) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossyWallBlock, this.brickMossyWallMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedWallBlock, this.brickCrackedWallMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickWallBlock, this.brickWallMeta);
        }
    }

    private void placeBarredWindowOnX(World world, int i, int j, int k) {
        for(int i1 = -1; i1 <= 1; ++i1) {
            for(int j1 = 0; j1 <= 1; ++j1) {
                this.setBlockAndMetadata(world, i + i1, j + j1, k, this.barsBlock, 0);
            }
        }
    }

    private void placeBarredWindowOnZ(World world, int i, int j, int k) {
        for(int k1 = -1; k1 <= 1; ++k1) {
            for(int j1 = 0; j1 <= 1; ++j1) {
                this.setBlockAndMetadata(world, i, j + j1, k + k1, this.barsBlock, 0);
            }
        }
    }
}
