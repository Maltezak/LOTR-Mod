package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public abstract class LOTRWorldGenGulfStructure extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block brick2Block;
    protected int brick2Meta;
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
    protected Block beamBlock;
    protected int beamMeta;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block plank2SlabBlock;
    protected int plank2SlabMeta;
    protected Block plank2StairBlock;
    protected Block beam2Block;
    protected int beam2Meta;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block flagBlock;
    protected int flagMeta;
    protected Block boneBlock;
    protected int boneMeta;
    protected Block boneWallBlock;
    protected int boneWallMeta;
    protected Block bedBlock;

    public LOTRWorldGenGulfStructure(boolean flag) {
        super(flag);
    }

    protected boolean canUseRedBrick() {
        return true;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        if(this.canUseRedBrick() && random.nextInt(3) == 0) {
            this.brickBlock = LOTRMod.brick3;
            this.brickMeta = 13;
            this.brickSlabBlock = LOTRMod.slabSingle7;
            this.brickSlabMeta = 2;
            this.brickStairBlock = LOTRMod.stairsNearHaradBrickRed;
            this.brickWallBlock = LOTRMod.wall3;
            this.brickWallMeta = 4;
        }
        else {
            this.brickBlock = LOTRMod.brick;
            this.brickMeta = 15;
            this.brickSlabBlock = LOTRMod.slabSingle4;
            this.brickSlabMeta = 0;
            this.brickStairBlock = LOTRMod.stairsNearHaradBrick;
            this.brickWallBlock = LOTRMod.wall;
            this.brickWallMeta = 15;
        }
        this.brick2Block = LOTRMod.brick3;
        this.brick2Meta = 13;
        if(random.nextInt(5) == 0) {
            this.woodBlock = LOTRMod.wood9;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks3;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle5;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsDragon;
            this.fenceBlock = LOTRMod.fence3;
            this.fenceMeta = 4;
            this.fenceGateBlock = LOTRMod.fenceGateDragon;
            this.doorBlock = LOTRMod.doorDragon;
            this.beamBlock = LOTRMod.woodBeam9;
            this.beamMeta = 0;
        }
        else {
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
            this.doorBlock = LOTRMod.doorPalm;
            this.beamBlock = LOTRMod.woodBeam8;
            this.beamMeta = 3;
        }
        int randomWood2 = random.nextInt(3);
        if(randomWood2 == 0) {
            this.plank2Block = Blocks.planks;
            this.plank2Meta = 4;
            this.plank2SlabBlock = Blocks.wooden_slab;
            this.plank2SlabMeta = 4;
            this.plank2StairBlock = Blocks.acacia_stairs;
            this.beam2Block = LOTRMod.woodBeamV2;
            this.beam2Meta = 0;
        }
        else if(randomWood2 == 1) {
            this.plank2Block = LOTRMod.planks;
            this.plank2Meta = 14;
            this.plank2SlabBlock = LOTRMod.woodSlabSingle2;
            this.plank2SlabMeta = 6;
            this.plank2StairBlock = LOTRMod.stairsDatePalm;
            this.beam2Block = LOTRMod.woodBeam4;
            this.beam2Meta = 2;
        }
        else if(randomWood2 == 2) {
            this.plank2Block = LOTRMod.planks3;
            this.plank2Meta = 4;
            this.plank2SlabBlock = LOTRMod.woodSlabSingle5;
            this.plank2SlabMeta = 4;
            this.plank2StairBlock = LOTRMod.stairsDragon;
            this.beam2Block = LOTRMod.woodBeam9;
            this.beam2Meta = 0;
        }
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 1;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 1;
        this.roofStairBlock = LOTRMod.stairsReed;
        this.flagBlock = Blocks.wool;
        this.flagMeta = 14;
        this.boneBlock = LOTRMod.boneBlock;
        this.boneMeta = 0;
        this.boneWallBlock = LOTRMod.wallBone;
        this.boneWallMeta = 0;
        this.bedBlock = LOTRMod.strawBed;
    }

    protected ItemStack getRandomGulfWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.swordGulfHarad), new ItemStack(LOTRMod.swordGulfHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
        return items[random.nextInt(items.length)].copy();
    }
}
