package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class LOTRWorldGenRangerStructure extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block brickCarvedBlock;
    protected int brickCarvedMeta;
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
    protected Block wallBlock;
    protected int wallMeta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block barsBlock;
    protected Block tableBlock;
    protected Block bedBlock;
    protected Block plateBlock;
    protected Block cropBlock;
    protected int cropMeta;
    protected Item seedItem;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRChestContents chestContentsHouse;
    protected LOTRChestContents chestContentsRanger;

    public LOTRWorldGenRangerStructure(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        if(random.nextInt(3) == 0) {
            this.brickBlock = LOTRMod.brick2;
            this.brickMeta = 3;
            this.brickSlabBlock = LOTRMod.slabSingle4;
            this.brickSlabMeta = 1;
            this.brickStairBlock = LOTRMod.stairsArnorBrick;
            this.brickWallBlock = LOTRMod.wall2;
            this.brickWallMeta = 4;
            this.brickCarvedBlock = LOTRMod.brick2;
            this.brickCarvedMeta = 6;
        }
        else {
            this.brickBlock = Blocks.stonebrick;
            this.brickMeta = 0;
            this.brickSlabBlock = Blocks.stone_slab;
            this.brickSlabMeta = 5;
            this.brickStairBlock = Blocks.stone_brick_stairs;
            this.brickWallBlock = LOTRMod.wallStoneV;
            this.brickWallMeta = 1;
            this.brickCarvedBlock = Blocks.stonebrick;
            this.brickCarvedMeta = 3;
        }
        this.cobbleBlock = Blocks.cobblestone;
        this.cobbleMeta = 0;
        this.cobbleSlabBlock = Blocks.stone_slab;
        this.cobbleSlabMeta = 3;
        this.cobbleStairBlock = Blocks.stone_stairs;
        int randomWood = random.nextInt(7);
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
            this.logBlock = Blocks.log;
            this.logMeta = 1;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 1;
            this.plankStairBlock = Blocks.spruce_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 1;
            this.fenceGateBlock = LOTRMod.fenceGateSpruce;
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 1;
            this.doorBlock = LOTRMod.doorSpruce;
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
        else if(randomWood == 6) {
            this.logBlock = LOTRMod.wood4;
            this.logMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 0;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsChestnut;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 0;
            this.fenceGateBlock = LOTRMod.fenceGateChestnut;
            this.woodBeamBlock = LOTRMod.woodBeam4;
            this.woodBeamMeta = 0;
            this.doorBlock = LOTRMod.doorChestnut;
        }
        if(random.nextBoolean()) {
            this.wallBlock = LOTRMod.daub;
            this.wallMeta = 0;
        }
        else {
            this.wallBlock = this.plankBlock;
            this.wallMeta = this.plankMeta;
        }
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 0;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 0;
        this.roofStairBlock = LOTRMod.stairsThatch;
        this.barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
        this.tableBlock = LOTRMod.rangerTable;
        this.bedBlock = LOTRMod.strawBed;
        this.plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
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
        this.bannerType = LOTRItemBanner.BannerType.RANGER_NORTH;
        this.chestContentsHouse = LOTRChestContents.RANGER_HOUSE;
        this.chestContentsRanger = LOTRChestContents.RANGER_TENT;
    }

    protected ItemStack getRangerFramedItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.rangerBow), new ItemStack(Items.bow), new ItemStack(Items.arrow)};
        return items[random.nextInt(items.length)].copy();
    }
}
