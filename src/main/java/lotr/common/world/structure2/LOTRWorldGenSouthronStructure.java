package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronStructure extends LOTRWorldGenStructureBase2 {
    protected Block stoneBlock;
    protected int stoneMeta;
    protected Block stoneStairBlock;
    protected Block stoneWallBlock;
    protected int stoneWallMeta;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block brick2Block;
    protected int brick2Meta;
    protected Block brick2SlabBlock;
    protected int brick2SlabMeta;
    protected Block brick2StairBlock;
    protected Block brick2WallBlock;
    protected int brick2WallMeta;
    protected Block brick2CarvedBlock;
    protected int brick2CarvedMeta;
    protected Block pillar2Block;
    protected int pillar2Meta;
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
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected int woodBeamMeta4;
    protected int woodBeamMeta8;
    protected Block doorBlock;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block gateMetalBlock;
    protected Block bedBlock;
    protected Block tableBlock;
    protected Block cropBlock;
    protected LOTRItemBanner.BannerType bannerType;

    public LOTRWorldGenSouthronStructure(boolean flag) {
        super(flag);
    }

    protected boolean isUmbar() {
        return false;
    }

    protected boolean canUseRedBricks() {
        return true;
    }

    protected boolean forceCedarWood() {
        return false;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.stoneBlock = Blocks.sandstone;
        this.stoneMeta = 0;
        this.stoneStairBlock = Blocks.sandstone_stairs;
        this.stoneWallBlock = LOTRMod.wallStoneV;
        this.stoneWallMeta = 4;
        if(this.canUseRedBricks() && random.nextInt(4) == 0) {
            this.brickBlock = LOTRMod.brick3;
            this.brickMeta = 13;
            this.brickSlabBlock = LOTRMod.slabSingle7;
            this.brickSlabMeta = 2;
            this.brickStairBlock = LOTRMod.stairsNearHaradBrickRed;
            this.brickWallBlock = LOTRMod.wall3;
            this.brickWallMeta = 4;
            this.pillarBlock = LOTRMod.pillar;
            this.pillarMeta = 15;
        }
        else {
            this.brickBlock = LOTRMod.brick;
            this.brickMeta = 15;
            this.brickSlabBlock = LOTRMod.slabSingle4;
            this.brickSlabMeta = 0;
            this.brickStairBlock = LOTRMod.stairsNearHaradBrick;
            this.brickWallBlock = LOTRMod.wall;
            this.brickWallMeta = 15;
            this.pillarBlock = LOTRMod.pillar;
            this.pillarMeta = 5;
        }
        this.brick2Block = LOTRMod.brick3;
        this.brick2Meta = 13;
        this.brick2SlabBlock = LOTRMod.slabSingle7;
        this.brick2SlabMeta = 2;
        this.brick2StairBlock = LOTRMod.stairsNearHaradBrickRed;
        this.brick2WallBlock = LOTRMod.wall3;
        this.brick2WallMeta = 4;
        this.brick2CarvedBlock = LOTRMod.brick3;
        this.brick2CarvedMeta = 15;
        this.pillar2Block = LOTRMod.pillar;
        this.pillar2Meta = 15;
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 1;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 1;
        this.roofStairBlock = LOTRMod.stairsReed;
        if(random.nextBoolean() || this.forceCedarWood()) {
            this.woodBlock = LOTRMod.wood4;
            this.woodMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 2;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCedar;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 2;
            this.fenceGateBlock = LOTRMod.fenceGateCedar;
            this.woodBeamBlock = LOTRMod.woodBeam4;
            this.woodBeamMeta = 2;
            this.doorBlock = LOTRMod.doorCedar;
        }
        else {
            int randomWood = random.nextInt(3);
            if(randomWood == 0) {
                this.woodBlock = LOTRMod.wood6;
                this.woodMeta = 3;
                this.plankBlock = LOTRMod.planks2;
                this.plankMeta = 11;
                this.plankSlabBlock = LOTRMod.woodSlabSingle4;
                this.plankSlabMeta = 3;
                this.plankStairBlock = LOTRMod.stairsOlive;
                this.fenceBlock = LOTRMod.fence2;
                this.fenceMeta = 11;
                this.fenceGateBlock = LOTRMod.fenceGateOlive;
                this.woodBeamBlock = LOTRMod.woodBeam6;
                this.woodBeamMeta = 3;
                this.doorBlock = LOTRMod.doorOlive;
            }
            else if(randomWood == 1) {
                this.woodBlock = LOTRMod.wood3;
                this.woodMeta = 2;
                this.plankBlock = LOTRMod.planks;
                this.plankMeta = 14;
                this.plankSlabBlock = LOTRMod.woodSlabSingle2;
                this.plankSlabMeta = 6;
                this.plankStairBlock = LOTRMod.stairsDatePalm;
                this.fenceBlock = LOTRMod.fence;
                this.fenceMeta = 14;
                this.fenceGateBlock = LOTRMod.fenceGateDatePalm;
                this.woodBeamBlock = LOTRMod.woodBeam3;
                this.woodBeamMeta = 2;
                this.doorBlock = LOTRMod.doorDatePalm;
            }
            else if(randomWood == 2) {
                this.woodBlock = LOTRMod.wood8;
                this.woodMeta = 3;
                this.plankBlock = LOTRMod.planks3;
                this.plankMeta = 3;
                this.plankSlabBlock = LOTRMod.woodSlabSingle5;
                this.plankSlabMeta = 3;
                this.plankStairBlock = LOTRMod.stairsPalm;
                this.fenceBlock = LOTRMod.fence3;
                this.fenceMeta = 3;
                this.fenceGateBlock = LOTRMod.fenceGatePalm;
                this.woodBeamBlock = LOTRMod.woodBeam8;
                this.woodBeamMeta = 3;
                this.doorBlock = LOTRMod.doorPalm;
            }
        }
        this.woodBeamMeta4 = this.woodBeamMeta | 4;
        this.woodBeamMeta8 = this.woodBeamMeta | 8;
        this.plank2Block = LOTRMod.planks2;
        this.plank2Meta = 11;
        this.gateMetalBlock = LOTRMod.gateBronzeBars;
        this.bedBlock = LOTRMod.strawBed;
        this.tableBlock = LOTRMod.nearHaradTable;
        if(random.nextBoolean()) {
            this.cropBlock = Blocks.wheat;
        }
        else {
            int randomCrop = random.nextInt(3);
            if(randomCrop == 0) {
                this.cropBlock = Blocks.carrots;
            }
            else if(randomCrop == 1) {
                this.cropBlock = Blocks.carrots;
            }
            else if(randomCrop == 2) {
                this.cropBlock = LOTRMod.lettuceCrop;
            }
        }
        this.bannerType = LOTRItemBanner.BannerType.NEAR_HARAD;
        if(this.isUmbar()) {
            this.stoneBlock = LOTRMod.brick2;
            this.stoneMeta = 11;
            this.stoneStairBlock = LOTRMod.stairsBlackGondorBrick;
            this.stoneWallBlock = LOTRMod.wall2;
            this.stoneWallMeta = 10;
            this.brickBlock = LOTRMod.brick2;
            this.brickMeta = 11;
            this.brickSlabBlock = LOTRMod.slabSingle5;
            this.brickSlabMeta = 3;
            this.brickStairBlock = LOTRMod.stairsBlackGondorBrick;
            this.brickWallBlock = LOTRMod.wall2;
            this.brickWallMeta = 10;
            this.pillarBlock = LOTRMod.pillar;
            this.pillarMeta = 9;
            int randomRoof = random.nextInt(4);
            if(randomRoof == 0) {
                this.roofBlock = LOTRMod.clayTileDyed;
                this.roofMeta = 15;
                this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
                this.roofSlabMeta = 7;
                this.roofStairBlock = LOTRMod.stairsClayTileDyedBlack;
            }
            else if(randomRoof == 1) {
                this.roofBlock = LOTRMod.clayTileDyed;
                this.roofMeta = 14;
                this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
                this.roofSlabMeta = 6;
                this.roofStairBlock = LOTRMod.stairsClayTileDyedRed;
            }
            else if(randomRoof == 2) {
                this.roofBlock = LOTRMod.clayTileDyed;
                this.roofMeta = 12;
                this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
                this.roofSlabMeta = 4;
                this.roofStairBlock = LOTRMod.stairsClayTileDyedBrown;
            }
            else if(randomRoof == 3) {
                this.roofBlock = LOTRMod.clayTileDyed;
                this.roofMeta = 7;
                this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
                this.roofSlabMeta = 7;
                this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
            }
            this.brick2Block = this.roofBlock;
            this.brick2Meta = this.roofMeta;
            this.brick2SlabBlock = this.roofSlabBlock;
            this.brick2SlabMeta = this.roofSlabMeta;
            this.brick2StairBlock = this.roofStairBlock;
            this.plankBlock = LOTRMod.brick6;
            this.plankMeta = 6;
            this.plankSlabBlock = LOTRMod.slabSingle13;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsUmbarBrick;
            this.woodBeamBlock = LOTRMod.pillar2;
            this.woodBeamMeta4 = this.woodBeamMeta = 10;
            this.woodBeamMeta8 = this.woodBeamMeta;
            if(random.nextBoolean() && !this.forceCedarWood()) {
                this.fenceBlock = LOTRMod.fence3;
                this.fenceMeta = 3;
                this.fenceGateBlock = LOTRMod.fenceGatePalm;
                this.doorBlock = LOTRMod.doorPalm;
            }
            this.gateMetalBlock = LOTRMod.gateIronBars;
            this.tableBlock = LOTRMod.umbarTable;
            this.bannerType = LOTRItemBanner.BannerType.UMBAR;
        }
    }

    protected LOTREntityNearHaradrimBase createHaradrim(World world) {
        if(this.isUmbar()) {
            return new LOTREntityUmbarian(world);
        }
        return new LOTREntityNearHaradrim(world);
    }

    protected ItemStack getRandomHaradWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
        return items[random.nextInt(items.length)].copy();
    }

    protected ItemStack getRandomHaradItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.gobletCopper), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing), new ItemStack(LOTRMod.doubleFlower, 1, 2), new ItemStack(LOTRMod.doubleFlower, 1, 3), new ItemStack(LOTRMod.gemsbokHorn), new ItemStack(LOTRMod.lionFur)};
        return items[random.nextInt(items.length)].copy();
    }
}
