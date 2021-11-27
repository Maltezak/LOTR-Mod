package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class LOTRWorldGenHobbitStructure
extends LOTRWorldGenStructureBase2 {
    protected boolean isWealthy;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block beamBlock;
    protected int beamMeta;
    protected Block doorBlock;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block plank2SlabBlock;
    protected int plank2SlabMeta;
    protected Block plank2StairBlock;
    protected Block fence2Block;
    protected int fence2Meta;
    protected Block fenceGate2Block;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block floorStairBlock;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickStairBlock;
    protected Block wallBlock;
    protected int wallMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block chandelierBlock;
    protected int chandelierMeta;
    protected Block barsBlock;
    protected Block outFenceBlock;
    protected int outFenceMeta;
    protected Block outFenceGateBlock;
    protected Block pathBlock;
    protected int pathMeta;
    protected Block pathSlabBlock;
    protected int pathSlabMeta;
    protected Block hedgeBlock;
    protected int hedgeMeta;
    protected Block tileSlabBlock;
    protected int tileSlabMeta;
    protected Block bedBlock;
    protected Block tableBlock;
    protected Block gateBlock;
    protected Block plateBlock;
    protected String maleName;
    protected String femaleName;
    protected String homeName1;
    protected String homeName2;

    public LOTRWorldGenHobbitStructure(boolean flag) {
        super(flag);
    }

    protected boolean makeWealthy(Random random) {
        return random.nextInt(5) == 0;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomChandelier;
        int randomWood2;
        this.isWealthy = this.makeWealthy(random);
        int randomWood = random.nextInt(5);
        if (randomWood == 0) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = LOTRMod.woodSlabSingle;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsShirePine;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 0;
            this.fenceGateBlock = LOTRMod.fenceGateShirePine;
            this.beamBlock = LOTRMod.woodBeam1;
            this.beamMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        } else if (randomWood == 1) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.fence_gate;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        } else if (randomWood == 2) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 2;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 2;
            this.plankStairBlock = Blocks.birch_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 2;
            this.fenceGateBlock = LOTRMod.fenceGateBirch;
            this.beamBlock = LOTRMod.woodBeamV1;
            this.beamMeta = 0;
            this.doorBlock = LOTRMod.doorBirch;
        } else if (randomWood == 3) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 0;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsChestnut;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 0;
            this.fenceGateBlock = LOTRMod.fenceGateChestnut;
            this.beamBlock = LOTRMod.woodBeam4;
            this.beamMeta = 0;
            this.doorBlock = LOTRMod.doorChestnut;
        } else if (randomWood == 4) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 9;
            this.fenceGateBlock = LOTRMod.fenceGateBeech;
            this.beamBlock = LOTRMod.woodBeam2;
            this.beamMeta = 1;
            this.doorBlock = LOTRMod.doorBeech;
        }
        if (random.nextBoolean()) {
            this.doorBlock = LOTRMod.doorShirePine;
        }
        if (this.isWealthy) {
            randomWood2 = random.nextInt(3);
            if (randomWood2 == 0) {
                this.plank2Block = LOTRMod.planks;
                this.plank2Meta = 4;
                this.plank2SlabBlock = LOTRMod.woodSlabSingle;
                this.plank2SlabMeta = 4;
                this.plank2StairBlock = LOTRMod.stairsApple;
                this.fence2Block = LOTRMod.fence;
                this.fence2Meta = 4;
                this.fenceGate2Block = LOTRMod.fenceGateApple;
            } else if (randomWood2 == 1) {
                this.plank2Block = LOTRMod.planks;
                this.plank2Meta = 5;
                this.plank2SlabBlock = LOTRMod.woodSlabSingle;
                this.plank2SlabMeta = 5;
                this.plank2StairBlock = LOTRMod.stairsPear;
                this.fence2Block = LOTRMod.fence;
                this.fence2Meta = 5;
                this.fenceGate2Block = LOTRMod.fenceGatePear;
            } else if (randomWood2 == 2) {
                this.plank2Block = LOTRMod.planks;
                this.plank2Meta = 6;
                this.plank2SlabBlock = LOTRMod.woodSlabSingle;
                this.plank2SlabMeta = 6;
                this.plank2StairBlock = LOTRMod.stairsCherry;
                this.fence2Block = LOTRMod.fence;
                this.fence2Meta = 6;
                this.fenceGate2Block = LOTRMod.fenceGateCherry;
            }
        } else {
            randomWood2 = random.nextInt(3);
            if (randomWood2 == 0) {
                this.plank2Block = Blocks.planks;
                this.plank2Meta = 0;
                this.plank2SlabBlock = Blocks.wooden_slab;
                this.plank2SlabMeta = 0;
                this.plank2StairBlock = Blocks.oak_stairs;
                this.fence2Block = Blocks.fence;
                this.fence2Meta = 0;
                this.fenceGate2Block = Blocks.fence_gate;
            } else if (randomWood2 == 1) {
                this.plank2Block = Blocks.planks;
                this.plank2Meta = 1;
                this.plank2SlabBlock = Blocks.wooden_slab;
                this.plank2SlabMeta = 1;
                this.plank2StairBlock = Blocks.spruce_stairs;
                this.fence2Block = Blocks.fence;
                this.fence2Meta = 1;
                this.fenceGate2Block = LOTRMod.fenceGateSpruce;
            } else if (randomWood2 == 2) {
                this.plank2Block = LOTRMod.planks2;
                this.plank2Meta = 9;
                this.plank2SlabBlock = LOTRMod.woodSlabSingle4;
                this.plank2SlabMeta = 1;
                this.plank2StairBlock = LOTRMod.stairsWillow;
                this.fence2Block = LOTRMod.fence2;
                this.fence2Meta = 9;
                this.fenceGate2Block = LOTRMod.fenceGateWillow;
            }
        }
        int randomFloor = random.nextInt(3);
        if (randomFloor == 0) {
            this.floorBlock = Blocks.brick_block;
            this.floorMeta = 0;
            this.floorStairBlock = Blocks.brick_stairs;
        } else if (randomFloor == 1) {
            this.floorBlock = Blocks.cobblestone;
            this.floorMeta = 0;
            this.floorStairBlock = Blocks.stone_stairs;
        } else if (randomFloor == 2) {
            this.floorBlock = Blocks.stonebrick;
            this.floorMeta = 0;
            this.floorStairBlock = Blocks.stone_brick_stairs;
        }
        this.brickBlock = Blocks.brick_block;
        this.brickMeta = 0;
        this.brickStairBlock = Blocks.brick_stairs;
        if (random.nextBoolean()) {
            this.wallBlock = LOTRMod.daub;
            this.wallMeta = 0;
        } else {
            this.wallBlock = this.plankBlock;
            this.wallMeta = this.plankMeta;
        }
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 0;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 0;
        this.roofStairBlock = LOTRMod.stairsThatch;
        int randomCarpet = random.nextInt(5);
        if (randomCarpet == 0) {
            this.carpetBlock = Blocks.carpet;
            this.carpetMeta = 15;
        } else if (randomCarpet == 1) {
            this.carpetBlock = Blocks.carpet;
            this.carpetMeta = 14;
        } else if (randomCarpet == 2) {
            this.carpetBlock = Blocks.carpet;
            this.carpetMeta = 13;
        } else if (randomCarpet == 3) {
            this.carpetBlock = Blocks.carpet;
            this.carpetMeta = 12;
        } else if (randomCarpet == 4) {
            this.carpetBlock = Blocks.carpet;
            this.carpetMeta = 7;
        }
        if (this.isWealthy) {
            randomChandelier = random.nextInt(2);
            if (randomChandelier == 0) {
                this.chandelierBlock = LOTRMod.chandelier;
                this.chandelierMeta = 2;
            } else if (randomChandelier == 1) {
                this.chandelierBlock = LOTRMod.chandelier;
                this.chandelierMeta = 3;
            }
        } else {
            randomChandelier = random.nextInt(2);
            if (randomChandelier == 0) {
                this.chandelierBlock = LOTRMod.chandelier;
                this.chandelierMeta = 0;
            } else if (randomChandelier == 1) {
                this.chandelierBlock = LOTRMod.chandelier;
                this.chandelierMeta = 1;
            }
        }
        this.barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
        if (random.nextInt(3) == 0) {
            this.outFenceBlock = Blocks.fence;
            this.outFenceMeta = 0;
            this.outFenceGateBlock = Blocks.fence_gate;
        } else {
            this.outFenceBlock = Blocks.cobblestone_wall;
            this.outFenceMeta = 0;
            this.outFenceGateBlock = Blocks.fence_gate;
        }
        if (random.nextInt(3) == 0) {
            this.pathBlock = LOTRMod.dirtPath;
            this.pathMeta = 0;
            this.pathSlabBlock = LOTRMod.slabSingleDirt;
            this.pathSlabMeta = 1;
        } else {
            this.pathBlock = Blocks.gravel;
            this.pathMeta = 0;
            this.pathSlabBlock = LOTRMod.slabSingleGravel;
            this.pathSlabMeta = 0;
        }
        this.hedgeBlock = Blocks.leaves;
        this.hedgeMeta = 4;
        if (random.nextBoolean()) {
            this.tileSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.tileSlabMeta = 4;
        } else {
            this.tileSlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.tileSlabMeta = 5;
        }
        this.bedBlock = Blocks.bed;
        this.tableBlock = LOTRMod.hobbitTable;
        int randomGate = random.nextInt(4);
        if (randomGate == 0) {
            this.gateBlock = LOTRMod.gateHobbitGreen;
        } else if (randomGate == 1) {
            this.gateBlock = LOTRMod.gateHobbitBlue;
        } else if (randomGate == 2) {
            this.gateBlock = LOTRMod.gateHobbitRed;
        } else if (randomGate == 3) {
            this.gateBlock = LOTRMod.gateHobbitYellow;
        }
        this.plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.plateBlock;
        String[] hobbitNames = this.getHobbitCoupleAndHomeNames(random);
        this.maleName = hobbitNames[0];
        this.femaleName = hobbitNames[1];
        this.homeName1 = hobbitNames[2];
        this.homeName2 = hobbitNames[3];
    }

    protected String[] getHobbitCoupleAndHomeNames(Random random) {
        return LOTRNames.getHobbitCoupleAndHomeNames(random);
    }

    protected void spawnHobbitCouple(World world, int i, int j, int k, int homeRange) {
        LOTREntityHobbit hobbitMale = this.createHobbit(world);
        hobbitMale.familyInfo.setName(this.maleName);
        hobbitMale.familyInfo.setMale(true);
        this.spawnNPCAndSetHome(hobbitMale, world, i, j, k, homeRange);
        LOTREntityHobbit hobbitFemale = this.createHobbit(world);
        hobbitFemale.familyInfo.setName(this.femaleName);
        hobbitFemale.familyInfo.setMale(false);
        this.spawnNPCAndSetHome(hobbitFemale, world, i, j, k, homeRange);
        int maxChildren = hobbitMale.familyInfo.getRandomMaxChildren();
        hobbitMale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.hobbitRing));
        hobbitMale.familyInfo.spouseUniqueID = hobbitFemale.getUniqueID();
        hobbitMale.familyInfo.setMaxBreedingDelay();
        hobbitMale.familyInfo.maxChildren = maxChildren;
        hobbitFemale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.hobbitRing));
        hobbitFemale.familyInfo.spouseUniqueID = hobbitMale.getUniqueID();
        hobbitFemale.familyInfo.setMaxBreedingDelay();
        hobbitFemale.familyInfo.maxChildren = maxChildren;
    }

    protected LOTREntityHobbit createHobbit(World world) {
        return new LOTREntityHobbit(world);
    }

    public static Block getRandomCakeBlock(Random random) {
        int i = random.nextInt(5);
        if (i == 0) {
            return Blocks.cake;
        }
        if (i == 1) {
            return LOTRMod.appleCrumble;
        }
        if (i == 2) {
            return LOTRMod.cherryPie;
        }
        if (i == 3) {
            return LOTRMod.berryPie;
        }
        if (i == 4) {
            return LOTRMod.marzipanBlock;
        }
        return Blocks.cake;
    }

    protected ItemStack getRandomHobbitDecoration(World world, Random random) {
        if (random.nextInt(3) == 0) {
            return this.getRandomFlower(world, random);
        }
        ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.rollingPin), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather), new ItemStack(Items.clock), new ItemStack(LOTRMod.leatherHat), new ItemStack(LOTRMod.hobbitPipe), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom)};
        return items[random.nextInt(items.length)].copy();
    }
}

