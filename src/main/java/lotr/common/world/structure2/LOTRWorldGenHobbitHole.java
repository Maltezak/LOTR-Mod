package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenHobbitHole extends LOTRWorldGenHobbitStructure {
    public LOTRWorldGenHobbitHole(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int k12;
        int i12;
        int j12;
        int signMeta;
        int j13;
        int i13;
        int j14;
        int k13;
        int k14;
        int i14;
        int k15;
        int i15;
        int j15;
        boolean grass;
        this.setOriginAndRotation(world, i, j, k, rotation, 17);
        this.setupRandomBlocks(random);
        int radius = 16;
        int height = 7;
        int extraRadius = 2;
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i12 = -radius; i12 <= radius; ++i12) {
                for(int k16 = -radius; k16 <= radius; ++k16) {
                    if(i12 * i12 + k16 * k16 > radius * radius) continue;
                    int j16 = this.getTopBlock(world, i12, k16) - 1;
                    if(!this.isSurface(world, i12, j16, k16)) {
                        return false;
                    }
                    if(j16 < minHeight) {
                        minHeight = j16;
                    }
                    if(j16 <= maxHeight) continue;
                    maxHeight = j16;
                }
            }
            if(maxHeight - minHeight > 8) {
                return false;
            }
        }
        for(i13 = -radius; i13 <= radius; ++i13) {
            for(k12 = -radius; k12 <= radius; ++k12) {
                for(j13 = height; j13 >= 0; --j13) {
                    int i2 = i13;
                    int j2 = j13 + (radius - height);
                    int k2 = k12;
                    if(i2 * i2 + j2 * j2 + k2 * k2 >= (radius + extraRadius) * (radius + extraRadius)) continue;
                    boolean grass2 = !this.isOpaque(world, i13, j13 + 1, k12);
                    this.setBlockAndMetadata(world, i13, j13, k12, grass2 ? Blocks.grass : Blocks.dirt, 0);
                    this.setGrassToDirt(world, i13, j13 - 1, k12);
                }
            }
        }
        for(i13 = -radius; i13 <= radius; ++i13) {
            for(k12 = -radius; k12 <= radius; ++k12) {
                if(i13 * i13 + k12 * k12 >= radius * radius) continue;
                j13 = -1;
                while(!this.isOpaque(world, i13, j13, k12) && this.getY(j13) >= 0) {
                    grass = !this.isOpaque(world, i13, j13 + 1, k12);
                    this.setBlockAndMetadata(world, i13, j13, k12, grass ? Blocks.grass : Blocks.dirt, 0);
                    this.setGrassToDirt(world, i13, j13 - 1, k12);
                    --j13;
                }
            }
        }
        this.setGrassToDirt(world, 0, 7, 0);
        this.setBlockAndMetadata(world, 0, 8, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 0, 9, 0, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 0, 10, 0, Blocks.flower_pot, 0);
        for(k15 = -16; k15 <= -13; ++k15) {
            for(j12 = 1; j12 <= 4; ++j12) {
                for(i12 = -3; i12 <= 3; ++i12) {
                    this.setAir(world, i12, j12, k15);
                }
            }
        }
        for(int j17 = 1; j17 <= 3; ++j17) {
            for(i15 = -2; i15 <= 2; ++i15) {
                this.setAir(world, i15, j17, -12);
            }
        }
        for(k15 = -17; k15 <= -13; ++k15) {
            for(i15 = -5; i15 <= 5; ++i15) {
                for(j13 = 0; j13 == 0 || !this.isOpaque(world, i15, j13, k15) && this.getY(j13) >= 0; --j13) {
                    grass = j13 == 0;
                    this.setBlockAndMetadata(world, i15, j13, k15, grass ? Blocks.grass : Blocks.dirt, 0);
                }
                for(j13 = 1; j13 <= 3; ++j13) {
                    this.setAir(world, i15, j13, k15);
                }
            }
        }
        for(k15 = -16; k15 <= -13; ++k15) {
            this.setBlockAndMetadata(world, 4, 1, k15, this.outFenceBlock, this.outFenceMeta);
            this.setBlockAndMetadata(world, -4, 1, k15, this.outFenceBlock, this.outFenceMeta);
            this.setBlockAndMetadata(world, 0, 0, k15, this.pathBlock, this.pathMeta);
        }
        for(i13 = -1; i13 <= 1; ++i13) {
            this.setBlockAndMetadata(world, i13, 0, -12, this.pathBlock, this.pathMeta);
            this.setBlockAndMetadata(world, i13, 0, -11, this.pathBlock, this.pathMeta);
        }
        for(i13 = -3; i13 <= 3; ++i13) {
            this.setBlockAndMetadata(world, i13, 1, -16, this.outFenceBlock, this.outFenceMeta);
        }
        this.setBlockAndMetadata(world, 0, 1, -16, this.outFenceGateBlock, 0);
        if(random.nextInt(5) == 0) {
            String[] signLines = LOTRNames.getHobbitSign(random);
            int[] signPos = new int[] {-3, -2, -1, 1, 2, 3};
            i12 = signPos[random.nextInt(signPos.length)];
            signMeta = MathHelper.getRandomIntegerInRange(random, 6, 10) & 0xF;
            this.placeSign(world, i12, 2, -16, Blocks.standing_sign, signMeta, signLines);
        }
        for(k15 = -15; k15 <= -13; ++k15) {
            int[] signPos = new int[] {-1, 1};
            i12 = signPos.length;
            for(signMeta = 0; signMeta < i12; ++signMeta) {
                i14 = signPos[signMeta];
                int j18 = 1;
                this.plantFlower(world, random, i14, j18, k15);
            }
        }
        if(random.nextInt(3) == 0) {
            for(k15 = -14; k15 <= -13; ++k15) {
                int[] signPos = new int[] {-2, 2};
                i12 = signPos.length;
                for(signMeta = 0; signMeta < i12; ++signMeta) {
                    i14 = signPos[signMeta];
                    this.setBlockAndMetadata(world, i14, 1, k15, this.hedgeBlock, this.hedgeMeta);
                }
            }
        }
        for(i13 = -2; i13 <= 2; ++i13) {
            for(j12 = 1; j12 <= 3; ++j12) {
                this.setBlockAndMetadata(world, i13, j12, -10, Blocks.brick_block, 0);
            }
        }
        boolean gateFlip = random.nextBoolean();
        if(gateFlip) {
            for(i15 = 0; i15 <= 1; ++i15) {
                this.setBlockAndMetadata(world, i15, 0, -10, this.floorBlock, this.floorMeta);
                for(j13 = 1; j13 <= 2; ++j13) {
                    this.setAir(world, i15, j13, -11);
                    this.setBlockAndMetadata(world, i15, j13, -10, this.gateBlock, 2);
                }
            }
            this.setBlockAndMetadata(world, -2, 1, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -2, 2, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -2, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -2, 1, -12, this.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, -2, 3, -12, this.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, -1, 3, -12, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 1, -11, this.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -1, 2, -11, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 0, 3, -11, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 1, 3, -11, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 2, 1, -11, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 2, 2, -11, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 2, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 2, 1, -12, this.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, -12, this.plank2StairBlock, 6);
            this.placeSign(world, -2, 2, -12, Blocks.wall_sign, 2, new String[] {"", this.homeName1, this.homeName2, ""});
        }
        else {
            for(i15 = -1; i15 <= 0; ++i15) {
                this.setBlockAndMetadata(world, i15, 0, -10, this.floorBlock, this.floorMeta);
                for(j13 = 1; j13 <= 2; ++j13) {
                    this.setAir(world, i15, j13, -11);
                    this.setBlockAndMetadata(world, i15, j13, -10, this.gateBlock, 2);
                }
            }
            this.setBlockAndMetadata(world, 2, 1, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 2, 2, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 2, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 2, 1, -12, this.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, -12, this.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, 1, 3, -12, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 1, -11, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, 1, 2, -11, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 0, 3, -11, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 0, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, -1, 3, -11, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -1, 3, -12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, -2, 1, -11, this.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, -2, 2, -11, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, -2, 3, -11, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -2, 1, -12, this.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, -2, 3, -12, this.plank2StairBlock, 6);
            this.placeSign(world, 2, 2, -12, Blocks.wall_sign, 2, new String[] {"", this.homeName1, this.homeName2, ""});
        }
        for(j12 = 1; j12 <= 3; ++j12) {
            this.setBlockAndMetadata(world, -3, j12, -12, LOTRMod.woodBeamV1, 0);
            this.setBlockAndMetadata(world, 3, j12, -12, LOTRMod.woodBeamV1, 0);
        }
        for(i15 = -3; i15 <= 3; ++i15) {
            if(Math.abs(i15) <= 1) {
                this.setBlockAndMetadata(world, i15, 4, -13, LOTRMod.slabClayTileDyedSingle2, 5);
                continue;
            }
            this.setBlockAndMetadata(world, i15, 3, -13, LOTRMod.slabClayTileDyedSingle2, 13);
        }
        this.setBlockAndMetadata(world, -4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);
        this.setBlockAndMetadata(world, 4, 3, -13, LOTRMod.slabClayTileDyedSingle2, 5);
        for(k12 = -9; k12 <= 1; ++k12) {
            for(i12 = -2; i12 <= 2; ++i12) {
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k12);
                }
            }
            this.setBlockAndMetadata(world, 1, 0, k12, this.floorBlock, this.floorMeta);
            this.setBlockAndMetadata(world, 0, 0, k12, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 0, k12, this.floorBlock, this.floorMeta);
            this.setBlockAndMetadata(world, 2, 1, k12, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -2, 1, k12, this.plank2StairBlock, 0);
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, 3, j13, k12, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, -3, j13, k12, this.plank2Block, this.plank2Meta);
            }
            this.setBlockAndMetadata(world, 2, 3, k12, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -2, 3, k12, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 1, 3, k12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, -1, 3, k12, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 0, 4, k12, this.plank2Block, this.plank2Meta);
        }
        int[] k17 = new int[] {-8, -4, 0};
        j13 = k17.length;
        for(j15 = 0; j15 < j13; ++j15) {
            int k18 = k17[j15];
            this.setBlockAndMetadata(world, 0, 3, k18, this.chandelierBlock, this.chandelierMeta);
        }
        for(j14 = 1; j14 <= 3; ++j14) {
            for(i12 = -2; i12 <= 2; ++i12) {
                this.setBlockAndMetadata(world, i12, j14, 2, this.plank2Block, this.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 2, this.plankBlock, this.plankMeta);
        this.setAir(world, 0, 1, 2);
        this.setAir(world, 0, 2, 2);
        this.setBlockAndMetadata(world, -1, 1, 2, this.plank2StairBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, 2, this.plank2StairBlock, 1);
        this.setBlockAndMetadata(world, -1, 2, 2, this.plank2StairBlock, 4);
        this.setBlockAndMetadata(world, 1, 2, 2, this.plank2StairBlock, 5);
        for(k13 = 3; k13 <= 9; ++k13) {
            for(i12 = -3; i12 <= 3; ++i12) {
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 4, k13, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, i12, 0, k13, this.plankBlock, this.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 6, this.chandelierBlock, this.chandelierMeta);
        for(k13 = 5; k13 <= 7; ++k13) {
            for(i12 = -1; i12 <= 1; ++i12) {
                this.setBlockAndMetadata(world, i12, 1, k13, this.carpetBlock, this.carpetMeta);
            }
        }
        if(this.isWealthy && random.nextBoolean()) {
            this.placeChest(world, random, 0, 0, 6, 2, LOTRChestContents.HOBBIT_HOLE_TREASURE);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, 10, this.plank2Block, this.plank2Meta);
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 2, 10, LOTRMod.glassPane, 0);
            this.setBlockAndMetadata(world, i1, 3, 10, LOTRMod.glassPane, 0);
            for(k1 = 11; k1 <= 14; ++k1) {
                this.setBlockAndMetadata(world, i1, 1, k1, Blocks.grass, 0);
                for(j15 = 2; j15 <= 3; ++j15) {
                    this.setAir(world, i1, j15, k1);
                }
            }
            this.setBlockAndMetadata(world, i1, 4, 10, this.plank2Block, this.plank2Meta);
        }
        for(j14 = 1; j14 <= 3; ++j14) {
            this.setBlockAndMetadata(world, -3, j14, 3, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 3, j14, 3, this.plank2Block, this.plank2Meta);
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, 3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        }
        for(k13 = 4; k13 <= 9; ++k13) {
            this.setBlockAndMetadata(world, -3, 3, k13, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 3, 3, k13, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 9, Blocks.oak_stairs, 2);
        }
        this.setBlockAndMetadata(world, -3, 1, 8, Blocks.oak_stairs, 0);
        this.setBlockAndMetadata(world, 3, 1, 8, Blocks.oak_stairs, 1);
        for(k13 = 4; k13 <= 9; ++k13) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, -4, j13, k13, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, 4, j13, k13, this.plank2Block, this.plank2Meta);
            }
        }
        this.setAir(world, -4, 1, 6);
        this.setAir(world, -4, 2, 6);
        this.setBlockAndMetadata(world, -4, 1, 5, this.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, -4, 1, 7, this.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, -4, 2, 5, this.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, -4, 2, 7, this.plank2StairBlock, 6);
        this.setAir(world, 4, 1, 6);
        this.setAir(world, 4, 2, 6);
        this.setBlockAndMetadata(world, 4, 1, 5, this.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, 4, 1, 7, this.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, 4, 2, 5, this.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, 4, 2, 7, this.plank2StairBlock, 6);
        this.setBlockAndMetadata(world, -3, 2, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 2, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 2, 9, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 2, 9, Blocks.torch, 1);
        this.setAir(world, 2, 1, -6);
        this.setBlockAndMetadata(world, 2, 0, -6, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, 3, 0, -6, this.floorBlock, this.floorMeta);
        this.setAir(world, 3, 1, -6);
        this.setAir(world, 3, 2, -6);
        this.setBlockAndMetadata(world, 3, 1, -7, this.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, 3, 1, -5, this.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, 3, 2, -7, this.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, 3, 2, -5, this.plank2StairBlock, 6);
        for(k13 = -8; k13 <= -3; ++k13) {
            for(i12 = 4; i12 <= 8; ++i12) {
                if(i12 == 8 && k13 == -8) continue;
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 0, k13, this.floorBlock, this.floorMeta);
                if(i12 >= 7 && k13 <= -7) continue;
                this.setBlockAndMetadata(world, i12, 4, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(i1 = 4; i1 <= 7; ++i1) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, -2, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, i1, j13, -8, this.plank2Block, this.plank2Meta);
            }
            this.setBlockAndMetadata(world, i1, 3, -7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 3, -3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        }
        for(k13 = -7; k13 <= -3; ++k13) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, 8, j13, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(j14 = 1; j14 <= 2; ++j14) {
            for(i12 = 5; i12 <= 6; ++i12) {
                this.setAir(world, i12, j14, -8);
                this.setBlockAndMetadata(world, i12, j14, -9, Blocks.bookshelf, 0);
            }
            for(k1 = -6; k1 <= -4; ++k1) {
                this.setAir(world, 8, j14, k1);
                this.setBlockAndMetadata(world, 9, j14, k1, Blocks.bookshelf, 0);
            }
        }
        this.setBlockAndMetadata(world, 6, 3, -5, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, 5, 1, -5, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 5, 1, -3, Blocks.wooden_slab, 8);
        this.placeChest(world, random, 7, 1, -3, 2, LOTRChestContents.HOBBIT_HOLE_STUDY);
        if(random.nextBoolean()) {
            this.placeWallBanner(world, 3, 3, -4, LOTRItemBanner.BannerType.HOBBIT, 1);
        }
        this.setAir(world, -2, 1, -6);
        this.setBlockAndMetadata(world, -2, 0, -6, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, -3, 0, -6, this.floorBlock, this.floorMeta);
        this.setAir(world, -3, 1, -6);
        this.setAir(world, -3, 2, -6);
        this.setBlockAndMetadata(world, -3, 1, -7, this.plank2StairBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, -5, this.plank2StairBlock, 2);
        this.setBlockAndMetadata(world, -3, 2, -7, this.plank2StairBlock, 7);
        this.setBlockAndMetadata(world, -3, 2, -5, this.plank2StairBlock, 6);
        for(k13 = -7; k13 <= -4; ++k13) {
            for(i12 = -4; i12 >= -7; --i12) {
                this.setBlockAndMetadata(world, i12, 0, k13, this.floorBlock, this.floorMeta);
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 4, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(i1 = -4; i1 >= -7; --i1) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, -8, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, i1, j13, -3, this.plank2Block, this.plank2Meta);
            }
            this.setBlockAndMetadata(world, i1, 3, -7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 3, -4, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        }
        for(k13 = -7; k13 <= -3; ++k13) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, -8, j13, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(k13 = -7; k13 <= -6; ++k13) {
            for(i12 = -5; i12 >= -6; --i12) {
                this.setBlockAndMetadata(world, i12, 1, k13, this.carpetBlock, this.carpetMeta);
            }
        }
        for(i1 = -5; i1 >= -6; --i1) {
            this.setBlockAndMetadata(world, i1, 0, -8, this.floorBlock, this.floorMeta);
            this.setBlockAndMetadata(world, i1, 1, -8, Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, i1, 2, -8, Blocks.bookshelf, 0);
            this.setBlockAndMetadata(world, i1, 1, -9, this.plank2Block, this.plank2Meta);
        }
        this.setBlockAndMetadata(world, -4, 1, -4, Blocks.planks, 0);
        this.setBlockAndMetadata(world, -7, 1, -4, Blocks.planks, 0);
        this.setBlockAndMetadata(world, -4, 2, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -7, 2, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -5, 1, -5, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, -4, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -6, 1, -5, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, -4, this.bedBlock, 8);
        this.spawnItemFrame(world, -8, 2, -6, 1, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 4, 0, 6, this.plankBlock, this.plankMeta);
        for(i1 = 5; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 7, this.floorBlock, this.floorMeta);
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setAir(world, i1, j13, 7);
            }
            this.setBlockAndMetadata(world, i1, 4, 7, this.plank2Block, this.plank2Meta);
        }
        for(i1 = 5; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 6, this.floorBlock, this.floorMeta);
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setAir(world, i1, j13, 6);
            }
            this.setBlockAndMetadata(world, i1, 4, 6, this.plank2Block, this.plank2Meta);
        }
        for(i1 = 5; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 5, this.floorBlock, this.floorMeta);
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setAir(world, i1, j13, 5);
            }
            this.setBlockAndMetadata(world, i1, 4, 5, this.plank2Block, this.plank2Meta);
        }
        for(j14 = 1; j14 <= 3; ++j14) {
            this.setBlockAndMetadata(world, 7, j14, 7, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 8, j14, 6, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 9, j14, 5, this.plank2Block, this.plank2Meta);
        }
        this.setBlockAndMetadata(world, 7, 2, 6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 8, 2, 5, Blocks.torch, 1);
        for(k13 = 4; k13 >= -1; --k13) {
            for(i12 = 4; i12 <= 9; ++i12) {
                this.setBlockAndMetadata(world, i12, 0, k13, this.floorBlock, this.floorMeta);
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 4, k13, this.plank2Block, this.plank2Meta);
            }
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, 3, j13, k13, this.plank2Block, this.plank2Meta);
                this.setBlockAndMetadata(world, 10, j13, k13, this.plank2Block, this.plank2Meta);
            }
            this.setBlockAndMetadata(world, 4, 3, k13, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 9, 3, k13, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        }
        for(k13 = 2; k13 >= 0; --k13) {
            this.setBlockAndMetadata(world, 4, 1, k13, Blocks.oak_stairs, 0);
            this.setBlockAndMetadata(world, 9, 1, k13, Blocks.oak_stairs, 1);
        }
        this.setBlockAndMetadata(world, 4, 1, -1, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 4, 1, 3, Blocks.oak_stairs, 2);
        this.setBlockAndMetadata(world, 9, 1, -1, Blocks.oak_stairs, 3);
        this.setBlockAndMetadata(world, 9, 1, 3, Blocks.oak_stairs, 2);
        this.setBlockAndMetadata(world, 6, 3, 1, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, 7, 3, 1, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, 6, 1, 2, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 7, 1, 2, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 6, 1, 1, Blocks.wooden_slab, 9);
        this.setBlockAndMetadata(world, 7, 1, 1, Blocks.wooden_slab, 9);
        this.setBlockAndMetadata(world, 6, 1, 0, Blocks.planks, 1);
        this.setBlockAndMetadata(world, 7, 1, 0, Blocks.planks, 1);
        for(i1 = 6; i1 <= 7; ++i1) {
            for(k1 = 2; k1 >= 0; --k1) {
                if(random.nextInt(3) == 0) {
                    this.placeMug(world, random, i1, 2, k1, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
                    continue;
                }
                if(random.nextBoolean()) {
                    this.placePlateWithCertainty(world, random, i1, 2, k1, this.plateBlock, LOTRFoods.HOBBIT);
                    continue;
                }
                this.placePlate(world, random, i1, 2, k1, this.plateBlock, LOTRFoods.HOBBIT);
            }
        }
        this.setBlockAndMetadata(world, 5, 3, 7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        this.setBlockAndMetadata(world, 6, 3, 7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        this.setBlockAndMetadata(world, 7, 3, 6, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        this.setBlockAndMetadata(world, 8, 3, 5, this.plank2SlabBlock, this.plank2SlabMeta | 8);
        for(j14 = 1; j14 <= 3; ++j14) {
            for(i12 = 5; i12 <= 6; ++i12) {
                this.setBlockAndMetadata(world, i12, j14, 8, this.plank2Block, this.plank2Meta);
            }
            for(i12 = 8; i12 <= 9; ++i12) {
                this.setBlockAndMetadata(world, i12, j14, -2, this.plank2Block, this.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, -4, 0, 6, this.plankBlock, this.plankMeta);
        for(k13 = 7; k13 >= 3; --k13) {
            for(i12 = -5; i12 >= -7; --i12) {
                this.setBlockAndMetadata(world, i12, 0, k13, Blocks.double_stone_slab, 0);
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 4, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(k13 = 6; k13 >= 3; --k13) {
            for(i12 = -5; i12 >= -6; --i12) {
                this.setBlockAndMetadata(world, i12, 0, k13, this.floorBlock, this.floorMeta);
            }
        }
        this.setBlockAndMetadata(world, -5, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -6, 1, 8, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -7, 1, 8, LOTRMod.hobbitTable, 0);
        for(i1 = -7; i1 <= -5; ++i1) {
            this.setAir(world, i1, 2, 8);
            this.setBlockAndMetadata(world, i1, 2, 9, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, i1, 3, 8, Blocks.double_stone_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 8, this.plank2Block, this.plank2Meta);
        this.setBlockAndMetadata(world, -8, 2, 8, this.plank2Block, this.plank2Meta);
        for(k13 = 6; k13 <= 7; ++k13) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, -8, j13, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(k13 = 3; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, -8, 0, k13, Blocks.double_stone_slab, 0);
            this.setAir(world, -8, 2, k13);
            this.setBlockAndMetadata(world, -9, 2, k13, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -8, 3, k13, Blocks.double_stone_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 4, this.plank2Block, this.plank2Meta);
        this.setBlockAndMetadata(world, -8, 1, 5, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -8, 1, 3, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -8, 1, 4, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -6, 3, 5, this.chandelierBlock, this.chandelierMeta);
        for(i1 = -4; i1 >= -9; --i1) {
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, 2, this.plank2Block, this.plank2Meta);
            }
        }
        this.setBlockAndMetadata(world, -6, 0, 2, this.plankBlock, this.plankMeta);
        this.setAir(world, -6, 1, 2);
        this.setAir(world, -6, 2, 2);
        this.setBlockAndMetadata(world, -7, 1, 2, this.plank2StairBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, 2, this.plank2StairBlock, 1);
        this.setBlockAndMetadata(world, -7, 2, 2, this.plank2StairBlock, 4);
        this.setBlockAndMetadata(world, -5, 2, 2, this.plank2StairBlock, 5);
        for(k13 = -2; k13 <= 1; ++k13) {
            for(i12 = -9; i12 <= -4; ++i12) {
                this.setBlockAndMetadata(world, i12, 0, k13, this.plankBlock, this.plankMeta);
                for(j15 = 1; j15 <= 3; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                this.setBlockAndMetadata(world, i12, 4, k13, Blocks.double_stone_slab, 0);
            }
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, -10, j13, k13, this.plank2Block, this.plank2Meta);
            }
        }
        for(i1 = -9; i1 <= -4; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, -2, Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, i1, 3, -2, Blocks.wooden_slab, 0);
        }
        for(k13 = -2; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, -4, 1, k13, Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, -4, 3, k13, Blocks.wooden_slab, 0);
            this.setBlockAndMetadata(world, -9, 1, k13, Blocks.wooden_slab, 8);
            this.setBlockAndMetadata(world, -9, 3, k13, Blocks.wooden_slab, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, 1, Blocks.wooden_slab, 8);
        this.setBlockAndMetadata(world, -8, 3, 1, Blocks.wooden_slab, 0);
        this.setBlockAndMetadata(world, -6, 3, 1, Blocks.torch, 4);
        for(k13 = -2; k13 <= 1; ++k13) {
            if(random.nextInt(3) == 0) continue;
            Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
            this.setBlockAndMetadata(world, -4, 2, k13, cakeBlock, 0);
        }
        for(i1 = -7; i1 <= -6; ++i1) {
            this.placePlateWithCertainty(world, random, i1, 2, -2, this.plateBlock, LOTRFoods.HOBBIT);
        }
        this.placeBarrel(world, random, -5, 2, -2, 3, LOTRFoods.HOBBIT_DRINK);
        for(j14 = 1; j14 <= 3; ++j14) {
            this.setBlockAndMetadata(world, -9, j14, -3, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -4, j14, 3, this.plank2Block, this.plank2Meta);
        }
        this.placeChest(world, random, -8, 2, -2, Blocks.chest, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -9, 2, -1, Blocks.chest, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -9, 2, 0, Blocks.chest, 4, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.placeChest(world, random, -8, 2, 1, Blocks.chest, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
        if(gateFlip) {
            this.setBlockAndMetadata(world, -1, 2, -9, Blocks.tripwire_hook, 0);
        }
        else {
            this.setBlockAndMetadata(world, 1, 2, -9, Blocks.tripwire_hook, 0);
        }
        int grassRadius = radius - 3;
        int grass3 = MathHelper.getRandomIntegerInRange(random, 24, 40);
        for(int l = 0; l < grass3; ++l) {
            i14 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            k14 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            j1 = this.getTopBlock(world, i14, k14);
            this.plantTallGrass(world, random, i14, j1, k14);
        }
        int flowers = MathHelper.getRandomIntegerInRange(random, 2, 5);
        for(int l = 0; l < flowers; ++l) {
            int i16 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            int k19 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            int j19 = this.getTopBlock(world, i16, k19);
            this.plantFlower(world, random, i16, j19, k19);
        }
        if(random.nextInt(4) == 0) {
            i14 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            k14 = MathHelper.getRandomIntegerInRange(random, -grassRadius, grassRadius);
            j1 = this.getTopBlock(world, i14, k14);
            WorldGenAbstractTree treeGen = LOTRBiome.shire.func_150567_a(random);
            treeGen.generate(world, random, this.getX(i14, k14), this.getY(j1), this.getZ(i14, k14));
        }
        int homeRadius = MathHelper.floor_double(radius * 1.5);
        this.spawnHobbitCouple(world, 0, 1, 0, homeRadius);
        return true;
    }
}
