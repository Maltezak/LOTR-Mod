package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHighElfHouse extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block brickCarvedBlock;
    protected int brickCarvedMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block stoneBlock;
    protected int stoneMeta;
    protected Block stoneSlabBlock;
    protected int stoneSlabMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block leafBlock;
    protected int leafMeta;
    protected Block tableBlock;
    protected Block bedBlock;
    protected Block barsBlock;
    protected Block torchBlock;
    protected Block chandelierBlock;
    protected int chandelierMeta;
    protected Block plateBlock;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContents;

    public LOTRWorldGenHighElfHouse(boolean flag) {
        super(flag);
    }

    protected LOTREntityElf createElf(World world) {
        return new LOTREntityHighElf(world);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.slabSingle5;
        this.brickSlabMeta = 5;
        this.brickStairBlock = LOTRMod.stairsHighElvenBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 11;
        this.brickCarvedBlock = LOTRMod.brick2;
        this.brickCarvedMeta = 13;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 10;
        this.stoneBlock = LOTRMod.smoothStoneV;
        this.stoneMeta = 0;
        this.stoneSlabBlock = Blocks.stone_slab;
        this.stoneSlabMeta = 0;
        int randomRoof = random.nextInt(5);
        if(randomRoof == 0) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 11;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
        }
        else if(randomRoof == 1) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 3;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.roofSlabMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
        }
        else if(randomRoof == 2) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 9;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 1;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
        }
        else if(randomRoof == 3) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 8;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.roofSlabMeta = 0;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
        }
        else if(randomRoof == 4) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 7;
            this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.roofSlabMeta = 7;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
        }
        int randomWood = random.nextInt(4);
        if(randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
        }
        else if(randomWood == 1) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 2;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 2;
            this.plankStairBlock = Blocks.birch_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 2;
        }
        else if(randomWood == 2) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
        }
        else if(randomWood == 3) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsApple;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 4;
        }
        int randomLeaf = random.nextInt(3);
        if(randomLeaf == 0) {
            this.leafBlock = Blocks.leaves;
            this.leafMeta = 4;
        }
        else if(randomLeaf == 1) {
            this.leafBlock = Blocks.leaves;
            this.leafMeta = 6;
        }
        else if(randomLeaf == 2) {
            this.leafBlock = LOTRMod.leaves2;
            this.leafMeta = 5;
        }
        this.tableBlock = LOTRMod.highElvenTable;
        this.bedBlock = LOTRMod.highElvenBed;
        this.barsBlock = LOTRMod.highElfWoodBars;
        this.torchBlock = LOTRMod.highElvenTorch;
        this.chandelierBlock = LOTRMod.chandelier;
        this.chandelierMeta = 10;
        this.plateBlock = LOTRMod.plateBlock;
        this.bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
        this.chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int k12;
        int i1;
        int j1;
        int j12;
        int i12;
        int i13;
        int j13;
        int j14;
        int i2;
        int k13;
        int i22;
        int meta;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        boolean leafy = random.nextBoolean();
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i14 = -4; i14 <= 4; ++i14) {
                for(int k14 = -1; k14 <= 14; ++k14) {
                    j12 = this.getTopBlock(world, i14, k14) - 1;
                    if(!this.isSurface(world, i14, j12, k14)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k13 = 0; k13 <= 13; ++k13) {
                int j15;
                i2 = Math.abs(i12);
                for(j15 = 0; (((j15 >= 0) || !this.isOpaque(world, i12, j15, k13)) && (this.getY(j15) >= 0)); --j15) {
                    this.setBlockAndMetadata(world, i12, j15, k13, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j15 - 1, k13);
                }
                for(j15 = 1; j15 <= 12; ++j15) {
                    this.setAir(world, i12, j15, k13);
                }
                if(i2 <= 2 && k13 >= 1 && k13 <= 12) {
                    this.setBlockAndMetadata(world, i12, 0, k13, this.stoneBlock, this.stoneMeta);
                }
                if(i2 > 2 || k13 != 0) continue;
                this.setBlockAndMetadata(world, i12, 0, k13, this.pillarBlock, this.pillarMeta);
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            i22 = Math.abs(i12);
            if(i22 % 2 == 1) {
                this.setBlockAndMetadata(world, i12, 1, 0, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, i12, 2, 0, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i12, 3, 0, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i12, 4, 0, this.pillarBlock, this.pillarMeta);
                if(i22 == 1) {
                    this.setBlockAndMetadata(world, i12, 5, 0, this.pillarBlock, this.pillarMeta);
                }
            }
            if(i22 == 0) {
                this.setBlockAndMetadata(world, i12, 6, 0, this.pillarBlock, this.pillarMeta);
                for(j1 = 7; j1 <= 9; ++j1) {
                    this.setBlockAndMetadata(world, i12, j1, 0, this.brickWallBlock, this.brickWallMeta);
                }
                for(j1 = 10; j1 <= 11; ++j1) {
                    this.setBlockAndMetadata(world, i12, j1, 0, this.pillarBlock, this.pillarMeta);
                }
                continue;
            }
            if(i22 > 2) continue;
            this.setBlockAndMetadata(world, i12, 6, 0, this.brickWallBlock, this.brickWallMeta);
        }
        this.setBlockAndMetadata(world, -2, 5, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 5, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 2, 5, 0, this.brickStairBlock, 5);
        int[] i15 = new int[] {-3, 3};
        i22 = i15.length;
        for(j1 = 0; j1 < i22; ++j1) {
            int i16 = i15[j1];
            for(j12 = 1; j12 <= 4; ++j12) {
                this.setBlockAndMetadata(world, i16, j12, 1, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i16, 1, 2, this.brickStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 2, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 3, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 4, 2, this.plankStairBlock, i16 > 0 ? 0 : 1);
            this.setBlockAndMetadata(world, i16, 1, 3, this.brickStairBlock, i16 > 0 ? 0 : 1);
            this.setBlockAndMetadata(world, i16, 3, 3, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i16, 4, 3, this.plankStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 1, 4, this.brickStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 2, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 3, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 4, 4, this.plankStairBlock, i16 > 0 ? 0 : 1);
            for(j12 = 1; j12 <= 4; ++j12) {
                this.setBlockAndMetadata(world, i16, j12, 5, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, i16, j12, 8, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i16, 4, 6, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i16, 4, 7, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 5, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 8, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i16, 1, 9, this.brickStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 2, 9, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 3, 9, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 4, 9, this.plankStairBlock, i16 > 0 ? 0 : 1);
            this.setBlockAndMetadata(world, i16, 1, 10, this.brickStairBlock, i16 > 0 ? 0 : 1);
            this.setBlockAndMetadata(world, i16, 3, 10, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i16, 4, 10, this.plankStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 1, 11, this.brickStairBlock, i16 > 0 ? 4 : 5);
            this.setBlockAndMetadata(world, i16, 2, 11, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 3, 11, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i16, 4, 11, this.plankStairBlock, i16 > 0 ? 0 : 1);
            for(j12 = 1; j12 <= 4; ++j12) {
                this.setBlockAndMetadata(world, i16, j12, 12, this.pillarBlock, this.pillarMeta);
            }
            this.setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 12, this.brickWallBlock, this.brickWallMeta);
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            i22 = Math.abs(i1);
            if(i22 == 0) {
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, 13, this.pillarBlock, this.pillarMeta);
                }
                for(j1 = 7; j1 <= 9; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, 13, this.brickWallBlock, this.brickWallMeta);
                }
                for(j1 = 10; j1 <= 11; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, 13, this.pillarBlock, this.pillarMeta);
                }
            }
            else if(i22 == 1) {
                this.setBlockAndMetadata(world, i1, 1, 13, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i1, 3, 13, this.barsBlock, 0);
                this.setBlockAndMetadata(world, i1, 4, 13, this.plankStairBlock, 7);
            }
            else if(i22 == 2) {
                this.setBlockAndMetadata(world, i1, 1, 13, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 2, 13, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 3, 13, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 4, 13, this.plankStairBlock, i1 > 0 ? 0 : 1);
            }
            if(i22 < 1 || i22 > 2) continue;
            this.setBlockAndMetadata(world, i1, 5, 13, this.stoneSlabBlock, this.stoneSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 6, 13, this.brickWallBlock, this.brickWallMeta);
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k13 = 1; k13 <= 12; ++k13) {
                this.setBlockAndMetadata(world, i1, 5, k13, this.stoneBlock, this.stoneMeta);
            }
        }
        for(k12 = 0; k12 <= 13; ++k12) {
            Block block = this.roofBlock;
            meta = this.roofMeta;
            Block slabBlock = this.roofSlabBlock;
            int slabMeta = this.roofSlabMeta;
            Block stairBlock = this.roofStairBlock;
            if(k12 == 1 || k12 == 12) {
                block = this.brickBlock;
                meta = this.brickMeta;
                slabBlock = this.brickSlabBlock;
                slabMeta = this.brickSlabMeta;
                stairBlock = this.brickStairBlock;
            }
            this.setBlockAndMetadata(world, -4, 5, k12, stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k12, stairBlock, 4);
            this.setBlockAndMetadata(world, -3, 6, k12, block, meta);
            this.setBlockAndMetadata(world, -3, 7, k12, block, meta);
            this.setBlockAndMetadata(world, -3, 8, k12, stairBlock, 1);
            this.setBlockAndMetadata(world, -2, 8, k12, stairBlock, 4);
            this.setBlockAndMetadata(world, -2, 9, k12, block, meta);
            this.setBlockAndMetadata(world, -2, 10, k12, stairBlock, 1);
            this.setBlockAndMetadata(world, -1, 10, k12, stairBlock, 4);
            this.setBlockAndMetadata(world, 4, 5, k12, stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, k12, stairBlock, 5);
            this.setBlockAndMetadata(world, 3, 6, k12, block, meta);
            this.setBlockAndMetadata(world, 3, 7, k12, block, meta);
            this.setBlockAndMetadata(world, 3, 8, k12, stairBlock, 0);
            this.setBlockAndMetadata(world, 2, 8, k12, stairBlock, 5);
            this.setBlockAndMetadata(world, 2, 9, k12, block, meta);
            this.setBlockAndMetadata(world, 2, 10, k12, stairBlock, 0);
            this.setBlockAndMetadata(world, 1, 10, k12, stairBlock, 5);
            if(k12 <= 1 || k12 >= 12) {
                this.setBlockAndMetadata(world, -1, 11, k12, block, meta);
                this.setBlockAndMetadata(world, -1, 12, k12, stairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k12, block, meta);
                this.setBlockAndMetadata(world, 1, 12, k12, stairBlock, 0);
                continue;
            }
            if(k12 <= 4 || k12 >= 9) {
                this.setBlockAndMetadata(world, -1, 11, k12, stairBlock, 1);
                this.setBlockAndMetadata(world, 1, 11, k12, stairBlock, 0);
                continue;
            }
            if(k12 == 5) {
                this.setBlockAndMetadata(world, -1, 11, k12, stairBlock, 3);
                this.setBlockAndMetadata(world, 1, 11, k12, stairBlock, 3);
                continue;
            }
            if(k12 == 8) {
                this.setBlockAndMetadata(world, -1, 11, k12, stairBlock, 2);
                this.setBlockAndMetadata(world, 1, 11, k12, stairBlock, 2);
                continue;
            }
            this.setBlockAndMetadata(world, -1, 11, k12, slabBlock, slabMeta);
            this.setBlockAndMetadata(world, 1, 11, k12, slabBlock, slabMeta);
        }
        for(k12 = 0; k12 <= 13; ++k12) {
            this.setBlockAndMetadata(world, 0, 11, k12, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 12, -1, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 13, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 14, -1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 0, 12, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 0, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 12, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 4, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 5, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 8, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 10, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 11, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 12, 12, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 12, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 12, 13, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 13, 13, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 12, 14, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 13, 14, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 14, 14, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 4, 1, this.brickStairBlock, 4);
        for(j13 = 1; j13 <= 4; ++j13) {
            this.setBlockAndMetadata(world, -2, j13, 2, Blocks.bookshelf, 0);
        }
        this.setBlockAndMetadata(world, -2, 1, 3, this.brickStairBlock, 4);
        this.placeFlowerPot(world, -2, 2, 3, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 8);
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.grass, 0);
        this.setBlockAndMetadata(world, -1, 1, 4, Blocks.trapdoor, 6);
        this.setBlockAndMetadata(world, -2, 1, 5, Blocks.trapdoor, 5);
        this.setBlockAndMetadata(world, -2, 2, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 3, 4, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, -2, 4, 4, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 2, 4, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 1, 2, this.brickStairBlock, 5);
        this.placeMug(world, random, 2, 2, 2, 1, LOTRFoods.ELF_DRINK);
        this.setBlockAndMetadata(world, 2, 1, 3, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 8);
        for(j13 = 1; j13 <= 4; ++j13) {
            this.setBlockAndMetadata(world, 2, j13, 4, Blocks.bookshelf, 0);
        }
        for(i1 = -1; i1 <= 0; ++i1) {
            for(k13 = 2; k13 <= 3; ++k13) {
                this.setBlockAndMetadata(world, i1, 1, k13, Blocks.carpet, 3);
            }
        }
        int[] i17 = new int[] {5, 8};
        k13 = i17.length;
        for(meta = 0; meta < k13; ++meta) {
            int k15 = i17[meta];
            this.setBlockAndMetadata(world, -2, 4, k15, this.brickStairBlock, 4);
            for(int i18 = -1; i18 <= 1; ++i18) {
                this.setBlockAndMetadata(world, i18, 4, k15, this.brickSlabBlock, this.brickSlabMeta | 8);
            }
            this.setBlockAndMetadata(world, 2, 4, k15, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 3, k15, this.torchBlock, 2);
            this.setBlockAndMetadata(world, 2, 3, k15, this.torchBlock, 1);
        }
        for(i13 = 0; i13 <= 1; ++i13) {
            for(k13 = 7; k13 <= 8; ++k13) {
                this.setBlockAndMetadata(world, i13, 1, k13, Blocks.carpet, 11);
            }
        }
        this.setBlockAndMetadata(world, -2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 8);
        this.setBlockAndMetadata(world, 2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 8);
        this.setBlockAndMetadata(world, -2, 4, 12, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 4, 12, this.brickStairBlock, 5);
        this.spawnItemFrame(world, -3, 2, 9, 1, this.getElfFramedItem(random));
        this.spawnItemFrame(world, 3, 2, 9, 3, this.getElfFramedItem(random));
        this.spawnItemFrame(world, -3, 2, 11, 1, this.getElfFramedItem(random));
        this.spawnItemFrame(world, 3, 2, 11, 3, this.getElfFramedItem(random));
        if(leafy) {
            for(i13 = -2; i13 <= 2; ++i13) {
                for(k13 = 6; k13 <= 7; ++k13) {
                    if(random.nextInt(3) == 0) continue;
                    this.setBlockAndMetadata(world, i13, 4, k13, this.leafBlock, this.leafMeta);
                }
            }
        }
        for(i13 = 0; i13 <= 1; ++i13) {
            for(k13 = 9; k13 <= 11; ++k13) {
                this.setAir(world, i13, 5, k13);
            }
        }
        for(i13 = -1; i13 <= 1; ++i13) {
            for(k13 = 1; k13 <= 9; ++k13) {
                this.setBlockAndMetadata(world, i13, 10, k13, this.brickSlabBlock, this.brickSlabMeta | 8);
            }
            for(k13 = 10; k13 <= 12; ++k13) {
                this.setBlockAndMetadata(world, i13, 9, k13, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i13, 10, k13, this.brickBlock, this.brickMeta);
            }
        }
        for(k1 = 9; k1 <= 12; ++k1) {
            for(j14 = 6; j14 <= 8; ++j14) {
                this.setBlockAndMetadata(world, 2, j14, k1, this.brickBlock, this.brickMeta);
            }
        }
        for(k1 = 11; k1 <= 12; ++k1) {
            for(j14 = 6; j14 <= 8; ++j14) {
                this.setBlockAndMetadata(world, -2, j14, k1, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -2, 6, 1, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 7, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 8, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 9, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -1, 10, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 6, 1, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 7, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 8, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 9, 1, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 10, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 10, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -1, 10, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 10, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 3, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, -2, 6, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 7, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 8, 4, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 6, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 7, 4, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 2, 8, 4, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 10, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 6, this.chandelierBlock, this.chandelierMeta);
        this.setBlockAndMetadata(world, -1, 10, 7, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 7, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 10, 8, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 10, 8, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 9, 9, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 10, 9, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 9, 9, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 10, 9, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 8, 12, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 8, 12, this.brickStairBlock, 5);
        for(int j16 = 1; j16 <= 11; ++j16) {
            this.setBlockAndMetadata(world, 0, j16, 10, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, 10, this.brickCarvedBlock, this.brickCarvedMeta);
        this.placeWallBanner(world, 0, 3, 10, this.bannerType, 2);
        this.setBlockAndMetadata(world, -1, 1, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 10, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, -1, 2, 11, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 2, 11, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 3, 11, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 1, 3, 10, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 4, 9, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 0, 4, 9, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, -1, 5, 9, this.brickSlabBlock, this.brickSlabMeta);
        for(i13 = 0; i13 <= 2; ++i13) {
            this.setBlockAndMetadata(world, i13, 6, 8, this.brickWallBlock, this.brickWallMeta);
        }
        for(i13 = -1; i13 <= 1; ++i13) {
            for(k13 = 1; k13 <= 7; ++k13) {
                i2 = Math.abs(i13);
                int k2 = Math.abs(k13 - 4);
                if(i2 == 0 && k2 == 0) {
                    this.setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 0);
                    continue;
                }
                if(i2 == 0 && k2 <= 2 || i2 == 1 && k2 == 0) {
                    this.setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 3);
                    continue;
                }
                if((i2 != 0 || k2 != 3) && (i2 != 1 || k2 != 1)) continue;
                this.setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 11);
            }
        }
        int[] i19 = new int[] {-2, 2};
        k13 = i19.length;
        for(i2 = 0; i2 < k13; ++i2) {
            int i110 = i19[i2];
            this.setBlockAndMetadata(world, i110, 6, 3, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i110, 6, 2, this.bedBlock, 10);
        }
        this.setBlockAndMetadata(world, -2, 6, 5, this.plankStairBlock, 4);
        this.placeMug(world, random, -2, 7, 5, 3, LOTRFoods.ELF_DRINK);
        this.setBlockAndMetadata(world, 2, 6, 5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 6, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 2, 6, 7, this.plankStairBlock, 6);
        this.placeChest(world, random, 2, 7, 5, 5, this.chestContents);
        this.placePlateWithCertainty(world, random, 2, 7, 6, this.plateBlock, LOTRFoods.ELF);
        this.placeBarrel(world, random, 2, 7, 7, 5, LOTRFoods.ELF_DRINK);
        if(leafy) {
            for(int i111 = -4; i111 <= 4; ++i111) {
                for(k13 = 0; k13 <= 13; ++k13) {
                    for(j1 = 5; j1 <= 12; ++j1) {
                        if(Math.abs(i111) < 3 && j1 < 11 || random.nextInt(4) != 0 || !this.isAir(world, i111, j1, k13) || !this.isSolidRoofBlock(world, i111, j1 - 1, k13) && !this.isSolidRoofBlock(world, i111 - 1, j1, k13) && !this.isSolidRoofBlock(world, i111 + 1, j1, k13)) continue;
                        this.setBlockAndMetadata(world, i111, j1, k13, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
        int elves = 1 + random.nextInt(2);
        for(int l = 0; l < elves; ++l) {
            LOTREntityElf elf = this.createElf(world);
            this.spawnNPCAndSetHome(elf, world, 0, 1, 7, 16);
        }
        return true;
    }

    private boolean isSolidRoofBlock(World world, int i, int j, int k) {
        return this.getBlock(world, i, j, k).getMaterial().isOpaque();
    }

    protected ItemStack getElfFramedItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetHighElven), new ItemStack(LOTRMod.bodyHighElven), new ItemStack(LOTRMod.legsHighElven), new ItemStack(LOTRMod.bootsHighElven), new ItemStack(LOTRMod.daggerHighElven), new ItemStack(LOTRMod.swordHighElven), new ItemStack(LOTRMod.spearHighElven), new ItemStack(LOTRMod.longspearHighElven), new ItemStack(LOTRMod.highElvenBow), new ItemStack(Items.arrow), new ItemStack(Items.feather), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
        return items[random.nextInt(items.length)].copy();
    }
}
