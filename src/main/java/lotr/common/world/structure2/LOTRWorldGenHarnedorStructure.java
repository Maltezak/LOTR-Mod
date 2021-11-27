package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public abstract class LOTRWorldGenHarnedorStructure extends LOTRWorldGenStructureBase2 {
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
    protected Block roofBlock;
    protected int roofMeta;
    protected Block plank2Block;
    protected int plank2Meta;
    protected Block plank2SlabBlock;
    protected int plank2SlabMeta;
    protected Block plank2StairBlock;
    protected Block boneBlock;
    protected int boneMeta;
    protected Block bedBlock;

    public LOTRWorldGenHarnedorStructure(boolean flag) {
        super(flag);
    }

    protected boolean isRuined() {
        return false;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
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
        this.doorBlock = LOTRMod.doorCedar;
        int randomWool = random.nextInt(3);
        if(randomWool == 0) {
            this.roofBlock = Blocks.wool;
            this.roofMeta = 1;
        }
        else if(randomWool == 1) {
            this.roofBlock = Blocks.wool;
            this.roofMeta = 12;
        }
        else if(randomWool == 2) {
            this.roofBlock = Blocks.wool;
            this.roofMeta = 14;
        }
        int randomFloorWood = random.nextInt(2);
        if(randomFloorWood == 0) {
            this.plank2Block = LOTRMod.planks2;
            this.plank2Meta = 11;
            this.plank2SlabBlock = LOTRMod.woodSlabSingle4;
            this.plank2SlabMeta = 3;
            this.plank2StairBlock = LOTRMod.stairsOlive;
        }
        else if(randomFloorWood == 1) {
            this.plank2Block = LOTRMod.planks3;
            this.plank2Meta = 0;
            this.plank2SlabBlock = LOTRMod.woodSlabSingle5;
            this.plank2SlabMeta = 0;
            this.plank2StairBlock = LOTRMod.stairsPlum;
        }
        this.boneBlock = LOTRMod.boneBlock;
        this.boneMeta = 0;
        this.bedBlock = LOTRMod.strawBed;
        if(this.isRuined()) {
            if(random.nextBoolean()) {
                this.woodBlock = LOTRMod.wood;
                this.woodMeta = 3;
                this.plankBlock = LOTRMod.planks;
                this.plankMeta = 3;
                this.plankSlabBlock = LOTRMod.woodSlabSingle;
                this.plankSlabMeta = 3;
                this.plankStairBlock = LOTRMod.stairsCharred;
                this.fenceBlock = LOTRMod.fence;
                this.fenceMeta = 3;
                this.fenceGateBlock = LOTRMod.fenceGateCharred;
                this.doorBlock = LOTRMod.doorCharred;
            }
            if(random.nextBoolean()) {
                this.plank2Block = LOTRMod.planks;
                this.plank2Meta = 3;
                this.plank2SlabBlock = LOTRMod.woodSlabSingle;
                this.plank2SlabMeta = 3;
                this.plank2StairBlock = LOTRMod.stairsCharred;
            }
        }
    }

    protected ItemStack getRandomHarnedorWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
        return items[random.nextInt(items.length)].copy();
    }

    protected ItemStack getHarnedorFramedItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad), new ItemStack(LOTRMod.nearHaradBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing), new ItemStack(LOTRMod.doubleFlower, 1, 2), new ItemStack(LOTRMod.doubleFlower, 1, 3)};
        return items[random.nextInt(items.length)].copy();
    }
}
