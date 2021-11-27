package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class LOTRWorldGenRohanStructure extends LOTRWorldGenStructureBase2 {
    protected Block rockBlock;
    protected int rockMeta;
    protected Block rockSlabBlock;
    protected int rockSlabMeta;
    protected Block rockSlabDoubleBlock;
    protected int rockSlabDoubleMeta;
    protected Block rockStairBlock;
    protected Block rockWallBlock;
    protected int rockWallMeta;
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
    protected Block cobbleBlock;
    protected int cobbleMeta;
    protected Block cobbleSlabBlock;
    protected int cobbleSlabMeta;
    protected Block cobbleStairBlock;
    protected Block logBlock;
    protected int logMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected Block doorBlock;
    protected Block log2Block;
    protected int log2Meta;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block plank2SlabBlock;
    protected int plank2SlabMeta;
    protected Block plank2StairBlock;
    protected Block fence2Block;
    protected int fence2Meta;
    protected Block fenceGate2Block;
    protected Block woodBeam2Block;
    protected int woodBeam2Meta;
    protected Block woodBeamRohanBlock;
    protected int woodBeamRohanMeta;
    protected Block woodBeamRohanGoldBlock;
    protected int woodBeamRohanGoldMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block barsBlock;
    protected Block tableBlock;
    protected Block bedBlock;
    protected Block gateBlock;
    protected Block plateBlock;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block cropBlock;
    protected int cropMeta;
    protected Item seedItem;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContents;

    public LOTRWorldGenRohanStructure(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.rockBlock = LOTRMod.rock;
        this.rockMeta = 2;
        this.rockSlabBlock = LOTRMod.slabSingle2;
        this.rockSlabMeta = 1;
        this.rockSlabDoubleBlock = LOTRMod.slabDouble2;
        this.rockSlabDoubleMeta = 1;
        this.rockStairBlock = LOTRMod.stairsRohanRock;
        this.rockWallBlock = LOTRMod.wall;
        this.rockWallMeta = 8;
        this.brickBlock = LOTRMod.brick;
        this.brickMeta = 4;
        this.brickSlabBlock = LOTRMod.slabSingle;
        this.brickSlabMeta = 6;
        this.brickStairBlock = LOTRMod.stairsRohanBrick;
        this.brickWallBlock = LOTRMod.wall;
        this.brickWallMeta = 6;
        this.brickCarvedBlock = LOTRMod.brick5;
        this.brickCarvedMeta = 3;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 8;
        this.cobbleBlock = Blocks.cobblestone;
        this.cobbleMeta = 0;
        this.cobbleSlabBlock = Blocks.stone_slab;
        this.cobbleSlabMeta = 3;
        this.cobbleStairBlock = Blocks.stone_stairs;
        int randomWood = random.nextInt(6);
        if(randomWood == 0 || randomWood == 1 || randomWood == 2) {
            this.logBlock = Blocks.log;
            this.logMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.fence_gate;
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.doorBlock = Blocks.wooden_door;
        }
        else if(randomWood == 3) {
            this.logBlock = LOTRMod.wood2;
            this.logMeta = 1;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 1;
            this.plankStairBlock = LOTRMod.stairsBeech;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
            this.fenceGateBlock = LOTRMod.fenceGateBeech;
            this.woodBeamBlock = LOTRMod.woodBeam2;
            this.woodBeamMeta = 1;
            this.doorBlock = LOTRMod.doorBeech;
        }
        else if(randomWood == 4) {
            this.logBlock = LOTRMod.fruitWood;
            this.logMeta = 0;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsApple;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 4;
            this.fenceGateBlock = LOTRMod.fenceGateApple;
            this.woodBeamBlock = LOTRMod.woodBeamFruit;
            this.woodBeamMeta = 0;
            this.doorBlock = LOTRMod.doorApple;
        }
        else if(randomWood == 5) {
            this.logBlock = LOTRMod.wood5;
            this.logMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsPine;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 4;
            this.fenceGateBlock = LOTRMod.fenceGatePine;
            this.woodBeamBlock = LOTRMod.woodBeam5;
            this.woodBeamMeta = 0;
            this.doorBlock = LOTRMod.doorPine;
        }
        int randomWood2 = random.nextInt(4);
        if(randomWood2 == 0 || randomWood2 == 1 || randomWood2 == 2) {
            this.log2Block = Blocks.log;
            this.log2Meta = 1;
            this.plank2Block = Blocks.planks;
            this.plank2Meta = 1;
            this.plank2SlabBlock = Blocks.wooden_slab;
            this.plank2SlabMeta = 1;
            this.plank2StairBlock = Blocks.spruce_stairs;
            this.fence2Block = Blocks.fence;
            this.fence2Meta = 1;
            this.fenceGate2Block = LOTRMod.fenceGateSpruce;
            this.woodBeam2Block = LOTRMod.woodBeamV1;
            this.woodBeam2Meta = 1;
        }
        else if(randomWood2 == 3) {
            this.log2Block = LOTRMod.wood3;
            this.log2Meta = 1;
            this.plank2Block = LOTRMod.planks;
            this.plank2Meta = 13;
            this.plank2SlabBlock = LOTRMod.woodSlabSingle2;
            this.plank2SlabMeta = 5;
            this.plank2StairBlock = LOTRMod.stairsLarch;
            this.fence2Block = LOTRMod.fence;
            this.fence2Meta = 13;
            this.fenceGate2Block = LOTRMod.fenceGateLarch;
            this.woodBeam2Block = LOTRMod.woodBeam3;
            this.woodBeam2Meta = 1;
        }
        if(this.oneWoodType() && random.nextInt(3) == 0) {
            this.logBlock = this.log2Block;
            this.logMeta = this.log2Meta;
            this.plankBlock = this.plank2Block;
            this.plankMeta = this.plank2Meta;
            this.plankSlabBlock = this.plank2SlabBlock;
            this.plankSlabMeta = this.plank2SlabMeta;
            this.plankStairBlock = this.plank2StairBlock;
            this.fenceBlock = this.fence2Block;
            this.fenceMeta = this.fence2Meta;
            this.fenceGateBlock = this.fenceGate2Block;
            this.woodBeamBlock = this.woodBeam2Block;
            this.woodBeamMeta = this.woodBeam2Meta;
        }
        this.woodBeamRohanBlock = LOTRMod.woodBeamS;
        this.woodBeamRohanMeta = 0;
        this.woodBeamRohanGoldBlock = LOTRMod.woodBeamS;
        this.woodBeamRohanGoldMeta = 1;
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 0;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 0;
        this.roofStairBlock = LOTRMod.stairsThatch;
        this.barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
        this.tableBlock = LOTRMod.rohirricTable;
        this.bedBlock = LOTRMod.strawBed;
        this.gateBlock = LOTRMod.gateWooden;
        this.plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 13;
        if(random.nextBoolean()) {
            this.cropBlock = Blocks.wheat;
            this.cropMeta = 7;
            this.seedItem = Items.wheat_seeds;
        }
        else {
            int randomCrop = random.nextInt(5);
            if(randomCrop == 0) {
                this.cropBlock = Blocks.carrots;
                this.cropMeta = 7;
                this.seedItem = Items.carrot;
            }
            else if(randomCrop == 1) {
                this.cropBlock = Blocks.potatoes;
                this.cropMeta = 7;
                this.seedItem = Items.potato;
            }
            else if(randomCrop == 2) {
                this.cropBlock = LOTRMod.lettuceCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.lettuce;
            }
            else if(randomCrop == 3) {
                this.cropBlock = LOTRMod.leekCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.leek;
            }
            else if(randomCrop == 4) {
                this.cropBlock = LOTRMod.turnipCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.turnip;
            }
        }
        this.bannerType = LOTRItemBanner.BannerType.ROHAN;
        this.chestContents = LOTRChestContents.ROHAN_HOUSE;
    }

    protected boolean oneWoodType() {
        return false;
    }

    protected ItemStack getRohanFramedItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetRohan), new ItemStack(LOTRMod.bodyRohan), new ItemStack(LOTRMod.legsRohan), new ItemStack(LOTRMod.bootsRohan), new ItemStack(LOTRMod.swordRohan), new ItemStack(LOTRMod.battleaxeRohan), new ItemStack(LOTRMod.daggerRohan), new ItemStack(Items.wooden_sword), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.rohanBow), new ItemStack(Items.arrow), new ItemStack(LOTRMod.horn)};
        return items[random.nextInt(items.length)].copy();
    }

    protected ItemStack getRandomRohanWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.swordRohan), new ItemStack(LOTRMod.battleaxeRohan), new ItemStack(LOTRMod.daggerRohan), new ItemStack(LOTRMod.spearRohan)};
        return items[random.nextInt(items.length)].copy();
    }

    protected ItemStack[] getRohanArmourItems() {
        return new ItemStack[] {new ItemStack(LOTRMod.helmetRohan), new ItemStack(LOTRMod.bodyRohan), new ItemStack(LOTRMod.legsRohan), new ItemStack(LOTRMod.bootsRohan)};
    }

    protected Block getRandomCakeBlock(Random random) {
        int i = random.nextInt(3);
        if(i == 0) {
            return LOTRMod.appleCrumble;
        }
        if(i == 1) {
            return LOTRMod.cherryPie;
        }
        if(i == 2) {
            return LOTRMod.berryPie;
        }
        return null;
    }
}
