package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class LOTRWorldGenBreeStructure
extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brick2Block;
    protected int brick2Meta;
    protected Block brick2SlabBlock;
    protected int brick2SlabMeta;
    protected Block brick2StairBlock;
    protected Block brick2WallBlock;
    protected int brick2WallMeta;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block stoneWallBlock;
    protected int stoneWallMeta;
    protected Block woodBlock;
    protected int woodMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block doorBlock;
    protected Block trapdoorBlock;
    protected Block beamBlock;
    protected int beamMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block bedBlock;
    protected Block tableBlock;

    public LOTRWorldGenBreeStructure(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.brickBlock = LOTRMod.cobblebrick;
        this.brickMeta = 0;
        this.brick2Block = Blocks.stonebrick;
        this.brick2Meta = 0;
        this.brick2SlabBlock = Blocks.stone_slab;
        this.brick2SlabMeta = 5;
        this.brick2StairBlock = Blocks.stone_brick_stairs;
        this.brick2WallBlock = LOTRMod.wallStoneV;
        this.brick2WallMeta = 1;
        this.floorBlock = Blocks.cobblestone;
        this.floorMeta = 0;
        this.stoneWallBlock = Blocks.cobblestone_wall;
        this.stoneWallMeta = 0;
        int randomWood = random.nextInt(7);
        if (randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.fence_gate;
            this.doorBlock = Blocks.wooden_door;
            this.trapdoorBlock = Blocks.trapdoor;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 0;
        } else if (randomWood == 1) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
            this.fenceGateBlock = LOTRMod.fenceGateBeech;
            this.doorBlock = LOTRMod.doorBeech;
            this.trapdoorBlock = LOTRMod.trapdoorBeech;
            this.beamBlock = LOTRMod.woodBeam2;
            this.beamMeta = 1;
        } else if (randomWood == 2) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 2;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 2;
            this.plankStairBlock = Blocks.birch_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 2;
            this.fenceGateBlock = LOTRMod.fenceGateBirch;
            this.doorBlock = LOTRMod.doorBirch;
            this.trapdoorBlock = LOTRMod.trapdoorBirch;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 2;
        } else if (randomWood == 3) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 1;
            this.plankStairBlock = Blocks.spruce_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 1;
            this.fenceGateBlock = LOTRMod.fenceGateSpruce;
            this.doorBlock = LOTRMod.doorSpruce;
            this.trapdoorBlock = LOTRMod.trapdoorSpruce;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 1;
        } else if (randomWood == 4) {
            this.woodBlock = LOTRMod.wood4;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 0;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsChestnut;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 0;
            this.fenceGateBlock = LOTRMod.fenceGateChestnut;
            this.doorBlock = LOTRMod.doorChestnut;
            this.trapdoorBlock = LOTRMod.trapdoorChestnut;
            this.beamBlock = LOTRMod.woodBeam4;
            this.beamMeta = 0;
        } else if (randomWood == 5) {
            this.woodBlock = LOTRMod.wood3;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 12;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsMaple;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 12;
            this.fenceGateBlock = LOTRMod.fenceGateMaple;
            this.doorBlock = LOTRMod.doorMaple;
            this.trapdoorBlock = LOTRMod.trapdoorMaple;
            this.beamBlock = LOTRMod.woodBeam3;
            this.beamMeta = 0;
        } else if (randomWood == 6) {
            this.woodBlock = LOTRMod.wood7;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 12;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsAspen;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 12;
            this.fenceGateBlock = LOTRMod.fenceGateAspen;
            this.doorBlock = LOTRMod.doorAspen;
            this.trapdoorBlock = LOTRMod.trapdoorAspen;
            this.beamBlock = LOTRMod.woodBeam7;
            this.beamMeta = 0;
        }
        this.doorBlock = LOTRMod.doorBeech;
        this.trapdoorBlock = LOTRMod.trapdoorBeech;
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 0;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 0;
        this.roofStairBlock = LOTRMod.stairsThatch;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 12;
        this.bedBlock = LOTRMod.strawBed;
        this.tableBlock = LOTRMod.breeTable;
    }

    public static Block getRandomPieBlock(Random random) {
        int i = random.nextInt(3);
        if (i == 0) {
            return LOTRMod.appleCrumble;
        }
        if (i == 1) {
            return LOTRMod.cherryPie;
        }
        if (i == 2) {
            return LOTRMod.berryPie;
        }
        return LOTRMod.appleCrumble;
    }

    protected ItemStack getRandomBreeWeapon(Random random) {
        ItemStack[] items = new ItemStack[]{new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.rollingPin)};
        return items[random.nextInt(items.length)].copy();
    }

    protected ItemStack getRandomTavernItem(Random random) {
        ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.rollingPin), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.wooden_axe), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather)};
        return items[random.nextInt(items.length)].copy();
    }

    protected void placeRandomFloor(World world, Random random, int i, int j, int k) {
        float randFloor = random.nextFloat();
        if (randFloor < 0.25f) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.grass, 0);
        } else if (randFloor < 0.5f) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.dirt, 1);
        } else if (randFloor < 0.75f) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.gravel, 0);
        } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.dirtPath, 0);
        }
    }
}

