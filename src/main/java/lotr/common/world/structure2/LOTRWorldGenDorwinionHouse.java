package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenDorwinionHouse extends LOTRWorldGenStructureBase2 {
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block doorBlock;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block wallBlock;
    protected int wallMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block clayBlock;
    protected int clayMeta;
    protected Block claySlabBlock;
    protected int claySlabMeta;
    protected Block clayStairBlock;
    protected Block leafBlock;
    protected int leafMeta;
    protected Block plateBlock;

    public LOTRWorldGenDorwinionHouse(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomWood = random.nextInt(3);
        if(randomWood == 0) {
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        }
        else if(randomWood == 1) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 10;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCypress;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 10;
            this.doorBlock = LOTRMod.doorCypress;
        }
        else if(randomWood == 2) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 3;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 11;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsOlive;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 11;
            this.doorBlock = LOTRMod.doorOlive;
        }
        int randomFloor = random.nextInt(3);
        if(randomFloor == 0) {
            this.floorBlock = Blocks.cobblestone;
            this.floorMeta = 0;
        }
        else if(randomFloor == 1) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 8;
        }
        else if(randomFloor == 2) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 10;
        }
        int randomWall = random.nextInt(3);
        if(randomWall == 0) {
            this.wallBlock = Blocks.stonebrick;
            this.wallMeta = 0;
        }
        else if(randomWall == 1) {
            this.wallBlock = Blocks.stained_hardened_clay;
            this.wallMeta = 0;
        }
        else if(randomWall == 2) {
            this.wallBlock = Blocks.stained_hardened_clay;
            this.wallMeta = 4;
        }
        this.brickBlock = LOTRMod.brick5;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.slabSingle9;
        this.brickSlabMeta = 7;
        this.brickStairBlock = LOTRMod.stairsDorwinionBrick;
        this.brickWallBlock = LOTRMod.wall3;
        this.brickWallMeta = 10;
        this.pillarBlock = LOTRMod.pillar2;
        this.pillarMeta = 6;
        int randomClay = random.nextInt(5);
        if(randomClay == 0) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 10;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 2;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedPurple;
        }
        else if(randomClay == 1) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 2;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.claySlabMeta = 2;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedMagenta;
        }
        else if(randomClay == 2) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 14;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 6;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedRed;
        }
        else if(randomClay == 3) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 13;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 5;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedGreen;
        }
        else if(randomClay == 4) {
            this.clayBlock = LOTRMod.clayTileDyed;
            this.clayMeta = 12;
            this.claySlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.claySlabMeta = 4;
            this.clayStairBlock = LOTRMod.stairsClayTileDyedBrown;
        }
        this.leafBlock = LOTRMod.leaves6;
        this.leafMeta = 6;
        this.plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.plateBlock;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int beam;
        int k1;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i12 = -10; i12 <= 3; ++i12) {
                for(k1 = 0; k1 <= 10; ++k1) {
                    j1 = this.getTopBlock(world, i12, k1) - 1;
                    Block block = this.getBlock(world, i12, j1, k1);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        for(int i12 = -10; i12 <= 3; ++i12) {
            for(k1 = 0; k1 <= 10; ++k1) {
                boolean garden;
                int j12;
                for(j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
                beam = 0;
                if((i12 == -2 || i12 == 3) && k1 == 0) {
                    beam = 1;
                }
                if(i12 == 3 && k1 == 5) {
                    beam = 1;
                }
                if((i12 == 3 || i12 == -2 || i12 == -10) && k1 == 10) {
                    beam = 1;
                }
                if((i12 == -10 || i12 == -2) && k1 == 4) {
                    beam = 1;
                }
                boolean wall = false;
                if(k1 == 0 || k1 == 10) {
                    wall = true;
                }
                if(i12 == 3 || i12 == -10) {
                    wall = true;
                }
                if(i12 == -2 && k1 <= 4) {
                    wall = true;
                }
                if(k1 == 4 && i12 <= -2) {
                    wall = true;
                }
                garden = i12 >= -10 && i12 <= -3 && k1 >= 0 && k1 <= 3;
                if(garden) {
                    this.setBlockAndMetadata(world, i12, 0, k1, Blocks.grass, 0);
                    j12 = -1;
                    while(!this.isOpaque(world, i12, j12, k1) && this.getY(j12) >= 0) {
                        this.setBlockAndMetadata(world, i12, j12, k1, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i12, j12 - 1, k1);
                        --j12;
                    }
                    if(random.nextInt(3) != 0) continue;
                    BiomeGenBase biome = this.getBiome(world, i12, k1);
                    int j13 = 1;
                    biome.plantFlower(world, random, this.getX(i12, k1), this.getY(j13), this.getZ(i12, k1));
                    continue;
                }
                if(beam != 0) {
                    for(int j122 = 1; j122 <= 8; ++j122) {
                        this.setBlockAndMetadata(world, i12, j122, k1, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.wallBlock, this.wallMeta);
                        this.setGrassToDirt(world, i12, j12 - 1, k1);
                    }
                    continue;
                }
                if(wall) {
                    for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.wallBlock, this.wallMeta);
                        this.setGrassToDirt(world, i12, j12 - 1, k1);
                    }
                    this.setBlockAndMetadata(world, i12, 1, k1, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i12, 2, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i12, 3, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i12, 6, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i12, 7, k1, this.brickBlock, this.brickMeta);
                    if(i12 == -10 || i12 == -2 || i12 == 3) {
                        this.setBlockAndMetadata(world, i12, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                        this.setBlockAndMetadata(world, i12, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i12, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                    this.setBlockAndMetadata(world, i12, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                    continue;
                }
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i12, j12, k1, this.floorBlock, this.floorMeta);
                    this.setGrassToDirt(world, i12, j12 - 1, k1);
                }
                if(i12 >= 0 && i12 <= 1 && k1 >= 2 && k1 <= 8 || i12 >= -8 && i12 <= -2 && k1 >= 6 && k1 <= 8) {
                    this.setBlockAndMetadata(world, i12, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 4, k1, this.plankBlock, this.plankMeta);
            }
        }
        for(int j14 : new int[] {2, 6}) {
            this.setAir(world, 0, j14, 0);
            this.setAir(world, 1, j14, 0);
            this.setBlockAndMetadata(world, 0, j14 + 1, 0, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, j14 + 1, 0, this.brickStairBlock, 5);
            for(int k13 : new int[] {2, 7}) {
                this.setAir(world, 3, j14, k13);
                this.setAir(world, 3, j14, k13 + 1);
                this.setBlockAndMetadata(world, 3, j14 + 1, k13, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, 3, j14 + 1, k13 + 1, this.brickStairBlock, 6);
            }
            for(int i13 : new int[] {-4, -7}) {
                this.setAir(world, i13, j14, 10);
                this.setAir(world, i13 - 1, j14, 10);
                this.setBlockAndMetadata(world, i13, j14 + 1, 10, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i13 - 1, j14 + 1, 10, this.brickStairBlock, 4);
            }
            this.setAir(world, -10, j14, 8);
            this.setAir(world, -10, j14, 7);
            this.setAir(world, -10, j14, 6);
            this.setBlockAndMetadata(world, -10, j14 + 1, 8, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -10, j14 + 1, 7, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, -10, j14 + 1, 6, this.brickStairBlock, 7);
            for(int i13 : new int[] {-8, -5}) {
                this.setAir(world, i13, j14, 4);
                this.setAir(world, i13 + 1, j14, 4);
                this.setBlockAndMetadata(world, i13, j14 + 1, 4, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i13 + 1, j14 + 1, 4, this.brickStairBlock, 5);
            }
            this.setAir(world, -2, j14, 2);
            this.setBlockAndMetadata(world, -2, j14 + 1, 2, this.brickSlabBlock, this.brickSlabMeta | 8);
        }
        this.setAir(world, 1, 6, 10);
        this.setAir(world, 0, 6, 10);
        this.setBlockAndMetadata(world, 1, 7, 10, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 7, 10, this.brickStairBlock, 4);
        for(int i14 = -9; i14 <= -3; ++i14) {
            if(i14 % 3 == 0) {
                this.setBlockAndMetadata(world, i14, 1, 3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i14, 2, 3, this.brickSlabBlock, this.brickSlabMeta);
                this.setBlockAndMetadata(world, i14, 1, 2, this.brickSlabBlock, this.brickSlabMeta);
                this.setGrassToDirt(world, i14, 0, 3);
                this.setGrassToDirt(world, i14, 0, 2);
                continue;
            }
            this.setBlockAndMetadata(world, i14, 1, 3, this.leafBlock, this.leafMeta);
        }
        this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, 1, 0, 0, this.floorBlock, this.floorMeta);
        this.setAir(world, 0, 1, 0);
        this.setAir(world, 1, 1, 0);
        this.placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DORWINION, 2);
        this.placeWallBanner(world, 3, 4, 0, LOTRItemBanner.BannerType.DORWINION, 2);
        this.setBlockAndMetadata(world, -1, 2, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 4, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -4, 3, 7, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -9, 3, 5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 3, 9, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 3, 7, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 1, 5, Blocks.furnace, 3);
        this.setBlockAndMetadata(world, -4, 1, 5, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -5, 1, 5, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -6, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -7, 1, 5, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -8, 1, 5, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -9, 1, 5, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, -9, 2, 5, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -9, 1, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 1, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -9, 1, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 1, 9, this.plankBlock, this.plankMeta);
        this.placeFlowerPot(world, -9, 2, 9, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -8, 1, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -7, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -6, 1, 9, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -5, 1, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 1, 9, Blocks.cauldron, 3);
        for(int j15 = 1; j15 <= 9; ++j15) {
            this.setBlockAndMetadata(world, 1, j15, 8, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, 1, 2, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 1, 8, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 1, 9, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 2, 9, this.plankStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, 9, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 2, 9, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, 8, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 3, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 2, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 1, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 4, 7, this.plankStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 4, 6, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 4, 6, this.plankStairBlock, 3);
        this.setAir(world, 0, 4, 7);
        this.setAir(world, 0, 4, 8);
        this.setAir(world, 0, 4, 9);
        this.setAir(world, 1, 4, 9);
        this.setAir(world, 2, 4, 9);
        this.setBlockAndMetadata(world, -1, 5, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -1, 5, 8, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -1, 5, 9, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 5, 8, this.fenceBlock, this.fenceMeta);
        int[] j15 = new int[] {5, 9};
        k1 = j15.length;
        for(beam = 0; beam < k1; ++beam) {
            int k14 = j15[beam];
            this.setBlockAndMetadata(world, -3, 5, k14, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -4, 5, k14, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, -5, 5, k14, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -8, 5, k14, Blocks.bed, 3);
            this.setBlockAndMetadata(world, -9, 5, k14, Blocks.bed, 11);
        }
        this.placeChest(world, random, -6, 5, 5, 3, LOTRChestContents.DORWINION_HOUSE);
        this.placeChest(world, random, -6, 5, 9, 2, LOTRChestContents.DORWINION_HOUSE);
        this.placeMug(world, random, -4, 6, 5, 2, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -4, 6, 9, 0, LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, -9, 5, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 5, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -9, 5, 8, this.plankStairBlock, 6);
        this.placeBarrel(world, random, -9, 6, 7, 4, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -9, 6, 6, 3, LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, -9, 6, 8, 3, LOTRFoods.DORWINION_DRINK);
        this.spawnItemFrame(world, -10, 8, 7, 1, new ItemStack(Items.clock));
        this.setBlockAndMetadata(world, 0, 5, 2, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 5, 3, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 2, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, 3, this.plankStairBlock, 5);
        for(i1 = 0; i1 <= 1; ++i1) {
            for(k1 = 2; k1 <= 3; ++k1) {
                this.placePlate(world, random, i1, 6, k1, this.plateBlock, LOTRFoods.DORWINION);
            }
        }
        this.setBlockAndMetadata(world, -1, 6, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 6, 1, Blocks.torch, 3);
        for(i1 = -2; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, -1, this.brickStairBlock, 6);
        }
        for(int k122 = -1; k122 <= 11; ++k122) {
            this.setBlockAndMetadata(world, 4, 8, k122, this.brickStairBlock, 4);
            if(IntMath.mod(k122, 2) != 1) continue;
            this.setBlockAndMetadata(world, 4, 9, k122, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(i1 = -11; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 11, this.brickStairBlock, 7);
            if(i1 > -3 || IntMath.mod(i1, 2) != 1) continue;
            this.setBlockAndMetadata(world, i1, 9, 11, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(k12 = 4; k12 <= 10; ++k12) {
            this.setBlockAndMetadata(world, -11, 8, k12, this.brickStairBlock, 5);
        }
        for(i1 = -11; i1 <= -3; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 3, this.brickStairBlock, 6);
            if(IntMath.mod(i1, 2) != 1) continue;
            this.setBlockAndMetadata(world, i1, 9, 3, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(k12 = -1; k12 <= 2; ++k12) {
            this.setBlockAndMetadata(world, -3, 8, k12, this.brickStairBlock, 5);
            if(IntMath.mod(k12, 2) != 1) continue;
            this.setBlockAndMetadata(world, -3, 9, k12, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(i1 = -9; i1 <= -1; ++i1) {
            this.setBlockAndMetadata(world, i1, 9, 5, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 10, 6, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 10, 7, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 10, 8, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 9, 9, this.plankStairBlock, 6);
        }
        for(k12 = 1; k12 <= 9; ++k12) {
            if(k12 <= 5) {
                this.setBlockAndMetadata(world, -1, 9, k12, this.plankStairBlock, 4);
                this.setBlockAndMetadata(world, 0, 10, k12, this.plankStairBlock, 4);
            }
            this.setBlockAndMetadata(world, 1, 10, k12, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 2, 9, k12, this.plankStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -10, 9, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 9, 6, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -10, 10, 6, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 10, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 10, 8, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -10, 9, 8, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -10, 9, 9, this.plankBlock, this.plankMeta);
        for(int i15 : new int[] {-8, -4, 0}) {
            this.setBlockAndMetadata(world, i15, 10, 7, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i15, 9, 7, LOTRMod.chandelier, 2);
        }
        this.setBlockAndMetadata(world, 0, 10, 6, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 9, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 8, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 9, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 8, this.woodBeamBlock, this.woodBeamMeta);
        this.setBlockAndMetadata(world, -1, 9, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, 10, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 10, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 9, 10, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 10, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 9, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, 0, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 10, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 9, 0, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 0, this.plankBlock, this.plankMeta);
        for(int i16 = -11; i16 <= 0; ++i16) {
            if(i16 <= -2) {
                this.setBlockAndMetadata(world, i16, 9, 4, this.clayStairBlock, 2);
                this.setBlockAndMetadata(world, i16, 9, 10, this.clayStairBlock, 3);
            }
            if(i16 <= -1) {
                this.setBlockAndMetadata(world, i16, 10, 5, this.clayStairBlock, 2);
                this.setBlockAndMetadata(world, i16, 10, 9, this.clayStairBlock, 3);
            }
            this.setBlockAndMetadata(world, i16, 11, 6, this.clayStairBlock, 2);
            this.setBlockAndMetadata(world, i16, 11, 7, this.clayBlock, this.clayMeta);
            this.setBlockAndMetadata(world, i16, 11, 8, this.clayStairBlock, 3);
        }
        for(int k15 = -1; k15 <= 11; ++k15) {
            if(k15 <= 3 || k15 >= 11) {
                this.setBlockAndMetadata(world, -2, 9, k15, this.clayStairBlock, 1);
            }
            if(k15 <= 4 || k15 >= 10) {
                this.setBlockAndMetadata(world, -1, 10, k15, this.clayStairBlock, 1);
            }
            if(k15 <= 5 || k15 >= 9) {
                this.setBlockAndMetadata(world, 0, 11, k15, this.clayStairBlock, 1);
            }
            this.setBlockAndMetadata(world, 1, 11, k15, this.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 10, k15, this.clayStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 9, k15, this.clayStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -11, 9, 5, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, -11, 10, 6, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, -11, 10, 8, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -11, 9, 9, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 9, 11, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, 11, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, 11, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, 11, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 9, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 10, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 10, -1, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 9, -1, this.clayStairBlock, 5);
        String maleName = LOTRNames.getDorwinionName(random, true);
        String femaleName = LOTRNames.getDorwinionName(random, false);
        LOTREntityDorwinionMan dorwinionMale = new LOTREntityDorwinionMan(world);
        dorwinionMale.familyInfo.setName(maleName);
        dorwinionMale.familyInfo.setMale(true);
        this.spawnNPCAndSetHome(dorwinionMale, world, 0, 1, 6, 16);
        LOTREntityDorwinionMan dorwinionFemale = new LOTREntityDorwinionMan(world);
        dorwinionFemale.familyInfo.setName(femaleName);
        dorwinionFemale.familyInfo.setMale(false);
        this.spawnNPCAndSetHome(dorwinionFemale, world, 0, 1, 6, 16);
        return true;
    }
}
