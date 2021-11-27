package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorStructure extends LOTRWorldGenStructureBase2 {
    public GondorFiefdom strFief = GondorFiefdom.GONDOR;
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
    protected Block brickMossyBlock;
    protected int brickMossyMeta;
    protected Block brickMossySlabBlock;
    protected int brickMossySlabMeta;
    protected Block brickMossyStairBlock;
    protected Block brickMossyWallBlock;
    protected int brickMossyWallMeta;
    protected Block brickCrackedBlock;
    protected int brickCrackedMeta;
    protected Block brickCrackedSlabBlock;
    protected int brickCrackedSlabMeta;
    protected Block brickCrackedStairBlock;
    protected Block brickCrackedWallBlock;
    protected int brickCrackedWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block brick2Block;
    protected int brick2Meta;
    protected Block brick2SlabBlock;
    protected int brick2SlabMeta;
    protected Block brick2StairBlock;
    protected Block brick2WallBlock;
    protected int brick2WallMeta;
    protected Block pillar2Block;
    protected int pillar2Meta;
    protected Block cobbleBlock;
    protected int cobbleMeta;
    protected Block cobbleSlabBlock;
    protected int cobbleSlabMeta;
    protected Block cobbleStairBlock;
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
    protected Block gateBlock;
    protected Block plateBlock;
    protected Block cropBlock;
    protected int cropMeta;
    protected Item seedItem;
    protected LOTRItemBanner.BannerType bannerType;
    protected LOTRItemBanner.BannerType bannerType2;
    protected LOTRChestContents chestContents;

    public LOTRWorldGenGondorStructure(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.rockBlock = LOTRMod.rock;
        this.rockMeta = 1;
        this.rockSlabBlock = LOTRMod.slabSingle;
        this.rockSlabMeta = 2;
        this.rockSlabDoubleBlock = LOTRMod.slabDouble;
        this.rockSlabDoubleMeta = 2;
        this.rockStairBlock = LOTRMod.stairsGondorRock;
        this.rockWallBlock = LOTRMod.wall;
        this.rockWallMeta = 2;
        if(this.strFief == GondorFiefdom.GONDOR || this.strFief == GondorFiefdom.LEBENNIN || this.strFief == GondorFiefdom.PELARGIR) {
            this.brickBlock = LOTRMod.brick;
            this.brickMeta = 1;
            this.brickSlabBlock = LOTRMod.slabSingle;
            this.brickSlabMeta = 3;
            this.brickStairBlock = LOTRMod.stairsGondorBrick;
            this.brickWallBlock = LOTRMod.wall;
            this.brickWallMeta = 3;
            this.brickMossyBlock = LOTRMod.brick;
            this.brickMossyMeta = 2;
            this.brickMossySlabBlock = LOTRMod.slabSingle;
            this.brickMossySlabMeta = 4;
            this.brickMossyStairBlock = LOTRMod.stairsGondorBrickMossy;
            this.brickMossyWallBlock = LOTRMod.wall;
            this.brickMossyWallMeta = 4;
            this.brickCrackedBlock = LOTRMod.brick;
            this.brickCrackedMeta = 3;
            this.brickCrackedSlabBlock = LOTRMod.slabSingle;
            this.brickCrackedSlabMeta = 5;
            this.brickCrackedStairBlock = LOTRMod.stairsGondorBrickCracked;
            this.brickCrackedWallBlock = LOTRMod.wall;
            this.brickCrackedWallMeta = 5;
        }
        else if(this.strFief == GondorFiefdom.DOL_AMROTH) {
            this.brickBlock = LOTRMod.brick3;
            this.brickMeta = 9;
            this.brickSlabBlock = LOTRMod.slabSingle6;
            this.brickSlabMeta = 7;
            this.brickStairBlock = LOTRMod.stairsDolAmrothBrick;
            this.brickWallBlock = LOTRMod.wall2;
            this.brickWallMeta = 14;
            this.brickMossyBlock = this.brickBlock;
            this.brickMossyMeta = this.brickMeta;
            this.brickMossySlabBlock = this.brickSlabBlock;
            this.brickMossySlabMeta = this.brickSlabMeta;
            this.brickMossyStairBlock = this.brickStairBlock;
            this.brickMossyWallBlock = this.brickWallBlock;
            this.brickMossyWallMeta = this.brickWallMeta;
            this.brickCrackedBlock = this.brickBlock;
            this.brickCrackedMeta = this.brickMeta;
            this.brickCrackedSlabBlock = this.brickSlabBlock;
            this.brickCrackedSlabMeta = this.brickSlabMeta;
            this.brickCrackedStairBlock = this.brickStairBlock;
            this.brickCrackedWallBlock = this.brickWallBlock;
            this.brickCrackedWallMeta = this.brickWallMeta;
        }
        else {
            this.brickBlock = LOTRMod.brick5;
            this.brickMeta = 8;
            this.brickSlabBlock = LOTRMod.slabSingle11;
            this.brickSlabMeta = 0;
            this.brickStairBlock = LOTRMod.stairsGondorBrickRustic;
            this.brickWallBlock = LOTRMod.wall4;
            this.brickWallMeta = 7;
            this.brickMossyBlock = LOTRMod.brick5;
            this.brickMossyMeta = 9;
            this.brickMossySlabBlock = LOTRMod.slabSingle11;
            this.brickMossySlabMeta = 1;
            this.brickMossyStairBlock = LOTRMod.stairsGondorBrickRusticMossy;
            this.brickMossyWallBlock = LOTRMod.wall4;
            this.brickMossyWallMeta = 8;
            this.brickCrackedBlock = LOTRMod.brick5;
            this.brickCrackedMeta = 10;
            this.brickCrackedSlabBlock = LOTRMod.slabSingle11;
            this.brickCrackedSlabMeta = 2;
            this.brickCrackedStairBlock = LOTRMod.stairsGondorBrickRusticCracked;
            this.brickCrackedWallBlock = LOTRMod.wall4;
            this.brickCrackedWallMeta = 9;
        }
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 6;
        if(this.strFief == GondorFiefdom.GONDOR || this.strFief == GondorFiefdom.BLACKROOT_VALE) {
            this.brick2Block = LOTRMod.brick2;
            this.brick2Meta = 11;
            this.brick2SlabBlock = LOTRMod.slabSingle5;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = LOTRMod.stairsBlackGondorBrick;
            this.brick2WallBlock = LOTRMod.wall2;
            this.brick2WallMeta = 10;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 9;
        }
        else if(this.strFief == GondorFiefdom.PELARGIR) {
            this.brick2Block = LOTRMod.whiteSandstone;
            this.brick2Meta = 0;
            this.brick2SlabBlock = LOTRMod.slabSingle10;
            this.brick2SlabMeta = 6;
            this.brick2StairBlock = LOTRMod.stairsWhiteSandstone;
            this.brick2WallBlock = LOTRMod.wall3;
            this.brick2WallMeta = 14;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 9;
        }
        else if(this.strFief == GondorFiefdom.LAMEDON) {
            this.brick2Block = Blocks.cobblestone;
            this.brick2Meta = 0;
            this.brick2SlabBlock = Blocks.stone_slab;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = Blocks.stone_stairs;
            this.brick2WallBlock = Blocks.cobblestone_wall;
            this.brick2WallMeta = 0;
            this.pillar2Block = LOTRMod.pillar2;
            this.pillar2Meta = 2;
        }
        else if(this.strFief == GondorFiefdom.PINNATH_GELIN) {
            this.brick2Block = LOTRMod.clayTileDyed;
            this.brick2Meta = 13;
            this.brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = LOTRMod.stairsClayTileDyedGreen;
            this.brick2WallBlock = LOTRMod.wallClayTileDyed;
            this.brick2WallMeta = 13;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 6;
        }
        else if(this.strFief == GondorFiefdom.DOL_AMROTH) {
            this.brick2Block = LOTRMod.clayTileDyed;
            this.brick2Meta = 11;
            this.brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = LOTRMod.stairsClayTileDyedBlue;
            this.brick2WallBlock = LOTRMod.wallClayTileDyed;
            this.brick2WallMeta = 11;
            this.pillar2Block = LOTRMod.pillar;
            this.pillar2Meta = 6;
        }
        else {
            this.brick2Block = Blocks.stonebrick;
            this.brick2Meta = 0;
            this.brick2SlabBlock = Blocks.stone_slab;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = Blocks.stone_brick_stairs;
            this.brick2WallBlock = LOTRMod.wallStoneV;
            this.brick2WallMeta = 1;
            this.pillar2Block = LOTRMod.pillar2;
            this.pillar2Meta = 2;
        }
        this.cobbleBlock = Blocks.cobblestone;
        this.cobbleMeta = 0;
        this.cobbleSlabBlock = Blocks.stone_slab;
        this.cobbleSlabMeta = 3;
        this.cobbleStairBlock = Blocks.stone_stairs;
        if(this.strFief == GondorFiefdom.BLACKROOT_VALE) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 5;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 5;
            this.plankStairBlock = Blocks.dark_oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 5;
            this.fenceGateBlock = LOTRMod.fenceGateDarkOak;
            this.woodBeamBlock = LOTRMod.woodBeamV2;
            this.woodBeamMeta = 1;
            this.doorBlock = LOTRMod.doorDarkOak;
        }
        else {
            int randomWood = random.nextInt(7);
            if(randomWood == 0 || randomWood == 1 || randomWood == 2) {
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
            else if(randomWood == 5) {
                this.plankBlock = LOTRMod.planks;
                this.plankMeta = 8;
                this.plankSlabBlock = LOTRMod.woodSlabSingle2;
                this.plankSlabMeta = 0;
                this.plankStairBlock = LOTRMod.stairsLebethron;
                this.fenceBlock = LOTRMod.fence;
                this.fenceMeta = 8;
                this.fenceGateBlock = LOTRMod.fenceGateLebethron;
                this.woodBeamBlock = LOTRMod.woodBeam2;
                this.woodBeamMeta = 0;
                this.doorBlock = LOTRMod.doorLebethron;
            }
            else if(randomWood == 6) {
                this.plankBlock = Blocks.planks;
                this.plankMeta = 2;
                this.plankSlabBlock = Blocks.wooden_slab;
                this.plankSlabMeta = 2;
                this.plankStairBlock = Blocks.birch_stairs;
                this.fenceBlock = Blocks.fence;
                this.fenceMeta = 2;
                this.fenceGateBlock = LOTRMod.fenceGateBirch;
                this.woodBeamBlock = LOTRMod.woodBeamV1;
                this.woodBeamMeta = 2;
                this.doorBlock = LOTRMod.doorBirch;
            }
        }
        if(this.strFief == GondorFiefdom.LOSSARNACH) {
            this.pillarBlock = this.woodBeamBlock;
            this.pillarMeta = this.woodBeamMeta;
            this.brick2Block = this.plankBlock;
            this.brick2Meta = this.plankMeta;
            this.brick2SlabBlock = this.plankSlabBlock;
            this.brick2SlabMeta = this.plankSlabMeta;
            this.brick2StairBlock = this.plankStairBlock;
            this.brick2WallBlock = this.fenceBlock;
            this.brick2WallMeta = this.fenceMeta;
            this.pillar2Block = this.woodBeamBlock;
            this.pillar2Meta = this.woodBeamMeta;
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
        this.barsBlock = Blocks.iron_bars;
        this.tableBlock = LOTRMod.gondorianTable;
        this.bedBlock = LOTRMod.strawBed;
        this.gateBlock = this.strFief == GondorFiefdom.PINNATH_GELIN || this.strFief == GondorFiefdom.LOSSARNACH || this.strFief == GondorFiefdom.LAMEDON ? LOTRMod.gateWooden : (this.strFief == GondorFiefdom.DOL_AMROTH ? LOTRMod.gateDolAmroth : LOTRMod.gateGondor);
        this.plateBlock = this.strFief == GondorFiefdom.LOSSARNACH || this.strFief == GondorFiefdom.LAMEDON || this.strFief == GondorFiefdom.BLACKROOT_VALE ? (random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock) : LOTRMod.ceramicPlateBlock;
        if(random.nextBoolean()) {
            this.cropBlock = Blocks.wheat;
            this.cropMeta = 7;
            this.seedItem = Items.wheat_seeds;
        }
        else {
            int randomCrop = random.nextInt(6);
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
                this.cropBlock = LOTRMod.cornStalk;
                this.cropMeta = 0;
                this.seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
            }
            else if(randomCrop == 4) {
                this.cropBlock = LOTRMod.leekCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.leek;
            }
            else if(randomCrop == 5) {
                this.cropBlock = LOTRMod.turnipCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.turnip;
            }
        }
        if(this.strFief == GondorFiefdom.GONDOR) {
            this.bannerType = LOTRItemBanner.BannerType.GONDOR;
        }
        else if(this.strFief == GondorFiefdom.LOSSARNACH) {
            this.bannerType = LOTRItemBanner.BannerType.LOSSARNACH;
        }
        else if(this.strFief == GondorFiefdom.LEBENNIN) {
            this.bannerType = LOTRItemBanner.BannerType.LEBENNIN;
        }
        else if(this.strFief == GondorFiefdom.PELARGIR) {
            this.bannerType = LOTRItemBanner.BannerType.PELARGIR;
        }
        else if(this.strFief == GondorFiefdom.PINNATH_GELIN) {
            this.bannerType = LOTRItemBanner.BannerType.PINNATH_GELIN;
        }
        else if(this.strFief == GondorFiefdom.BLACKROOT_VALE) {
            this.bannerType = LOTRItemBanner.BannerType.BLACKROOT_VALE;
        }
        else if(this.strFief == GondorFiefdom.LAMEDON) {
            this.bannerType = LOTRItemBanner.BannerType.LAMEDON;
        }
        else if(this.strFief == GondorFiefdom.DOL_AMROTH) {
            this.bannerType = LOTRItemBanner.BannerType.DOL_AMROTH;
        }
        this.bannerType2 = this.strFief == GondorFiefdom.PELARGIR ? LOTRItemBanner.BannerType.LEBENNIN : LOTRItemBanner.BannerType.GONDOR;
        this.chestContents = LOTRChestContents.GONDOR_HOUSE;
    }

    protected ItemStack getGondorFramedItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.daggerGondor), new ItemStack(LOTRMod.spearGondor), new ItemStack(LOTRMod.gondorBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.ironCrossbow), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
        return items[random.nextInt(items.length)].copy();
    }

    public enum GondorFiefdom {
        GONDOR, LOSSARNACH, LEBENNIN, PELARGIR, PINNATH_GELIN, BLACKROOT_VALE, LAMEDON, DOL_AMROTH;

        public LOTREntityGondorMan createCaptain(World world) {
            if(this == GONDOR) {
                return new LOTREntityGondorianCaptain(world);
            }
            if(this == LOSSARNACH) {
                return new LOTREntityLossarnachCaptain(world);
            }
            if(this == LEBENNIN) {
                return new LOTREntityLebenninCaptain(world);
            }
            if(this == PELARGIR) {
                return new LOTREntityPelargirCaptain(world);
            }
            if(this == PINNATH_GELIN) {
                return new LOTREntityPinnathGelinCaptain(world);
            }
            if(this == BLACKROOT_VALE) {
                return new LOTREntityBlackrootCaptain(world);
            }
            if(this == LAMEDON) {
                return new LOTREntityLamedonCaptain(world);
            }
            if(this == DOL_AMROTH) {
                return new LOTREntityDolAmrothCaptain(world);
            }
            return null;
        }

        public Class[] getSoldierClasses() {
            if(this == GONDOR) {
                return new Class[] {LOTREntityGondorSoldier.class, LOTREntityGondorArcher.class};
            }
            if(this == LOSSARNACH) {
                return new Class[] {LOTREntityLossarnachAxeman.class, LOTREntityLossarnachAxeman.class};
            }
            if(this == LEBENNIN) {
                return new Class[] {LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
            }
            if(this == PELARGIR) {
                return new Class[] {LOTREntityPelargirMarine.class, LOTREntityPelargirMarine.class};
            }
            if(this == PINNATH_GELIN) {
                return new Class[] {LOTREntityPinnathGelinSoldier.class, LOTREntityPinnathGelinSoldier.class};
            }
            if(this == BLACKROOT_VALE) {
                return new Class[] {LOTREntityBlackrootArcher.class, LOTREntityBlackrootSoldier.class};
            }
            if(this == LAMEDON) {
                return new Class[] {LOTREntityLamedonSoldier.class, LOTREntityLamedonArcher.class};
            }
            if(this == DOL_AMROTH) {
                return new Class[] {LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class};
            }
            return null;
        }

        public Class[] getLevyClasses() {
            if(this == GONDOR) {
                return new Class[] {LOTREntityGondorLevyman.class, LOTREntityGondorSoldier.class};
            }
            if(this == LOSSARNACH) {
                return new Class[] {LOTREntityGondorLevyman.class, LOTREntityLossarnachAxeman.class};
            }
            if(this == LEBENNIN) {
                return new Class[] {LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
            }
            if(this == PELARGIR) {
                return new Class[] {LOTREntityLebenninLevyman.class, LOTREntityPelargirMarine.class};
            }
            if(this == PINNATH_GELIN) {
                return new Class[] {LOTREntityGondorLevyman.class, LOTREntityPinnathGelinSoldier.class};
            }
            if(this == BLACKROOT_VALE) {
                return new Class[] {LOTREntityGondorLevyman.class, LOTREntityBlackrootArcher.class};
            }
            if(this == LAMEDON) {
                return new Class[] {LOTREntityLamedonHillman.class, LOTREntityLamedonSoldier.class};
            }
            if(this == DOL_AMROTH) {
                return new Class[] {LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class};
            }
            return null;
        }

        protected ItemStack[] getFiefdomArmor() {
            if(this == GONDOR) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
            }
            if(this == LOSSARNACH) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetLossarnach), new ItemStack(LOTRMod.bodyLossarnach), new ItemStack(LOTRMod.legsLossarnach), new ItemStack(LOTRMod.bootsLossarnach)};
            }
            if(this == LEBENNIN) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
            }
            if(this == PELARGIR) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetPelargir), new ItemStack(LOTRMod.bodyPelargir), new ItemStack(LOTRMod.legsPelargir), new ItemStack(LOTRMod.bootsPelargir)};
            }
            if(this == PINNATH_GELIN) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetPinnathGelin), new ItemStack(LOTRMod.bodyPinnathGelin), new ItemStack(LOTRMod.legsPinnathGelin), new ItemStack(LOTRMod.bootsPinnathGelin)};
            }
            if(this == BLACKROOT_VALE) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetBlackroot), new ItemStack(LOTRMod.bodyBlackroot), new ItemStack(LOTRMod.legsBlackroot), new ItemStack(LOTRMod.bootsBlackroot)};
            }
            if(this == LAMEDON) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetLamedon), new ItemStack(LOTRMod.bodyLamedon), new ItemStack(LOTRMod.legsLamedon), new ItemStack(LOTRMod.bootsLamedon)};
            }
            if(this == DOL_AMROTH) {
                return new ItemStack[] {new ItemStack(LOTRMod.helmetDolAmroth), new ItemStack(LOTRMod.bodyDolAmroth), new ItemStack(LOTRMod.legsDolAmroth), new ItemStack(LOTRMod.bootsDolAmroth)};
            }
            return null;
        }
    }

}
