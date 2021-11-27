package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenDunlandStructure extends LOTRWorldGenStructureBase2 {
    protected Block floorBlock;
    protected int floorMeta;
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
    protected Block roofSlabBlock;
    protected int roofSlabMeta;
    protected Block roofStairBlock;
    protected Block barsBlock;
    protected int barsMeta;
    protected Block bedBlock;

    public LOTRWorldGenDunlandStructure(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomFloor = random.nextInt(5);
        if(randomFloor == 0) {
            this.floorBlock = Blocks.cobblestone;
            this.floorMeta = 0;
        }
        else if(randomFloor == 1) {
            this.floorBlock = Blocks.hardened_clay;
            this.floorMeta = 0;
        }
        else if(randomFloor == 2) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 7;
        }
        else if(randomFloor == 3) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 12;
        }
        else if(randomFloor == 4) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 15;
        }
        if(random.nextBoolean()) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 1;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 1;
            this.plankStairBlock = Blocks.spruce_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 1;
            this.fenceGateBlock = LOTRMod.fenceGateSpruce;
            this.doorBlock = LOTRMod.doorSpruce;
        }
        else {
            int randomWood = random.nextInt(2);
            if(randomWood == 0) {
                this.woodBlock = Blocks.log;
                this.woodMeta = 0;
                this.plankBlock = Blocks.planks;
                this.plankMeta = 0;
                this.plankSlabBlock = Blocks.wooden_slab;
                this.plankSlabMeta = 0;
                this.plankStairBlock = Blocks.oak_stairs;
                this.fenceBlock = Blocks.fence;
                this.fenceMeta = 0;
                this.fenceGateBlock = Blocks.fence_gate;
                this.doorBlock = Blocks.wooden_door;
            }
            else if(randomWood == 1) {
                this.woodBlock = LOTRMod.wood5;
                this.woodMeta = 0;
                this.plankBlock = LOTRMod.planks2;
                this.plankMeta = 4;
                this.plankSlabBlock = LOTRMod.woodSlabSingle3;
                this.plankSlabMeta = 4;
                this.plankStairBlock = LOTRMod.stairsPine;
                this.fenceBlock = LOTRMod.fence2;
                this.fenceMeta = 4;
                this.fenceGateBlock = LOTRMod.fenceGatePine;
                this.doorBlock = LOTRMod.doorPine;
            }
        }
        this.roofBlock = LOTRMod.thatch;
        this.roofMeta = 0;
        this.roofSlabBlock = LOTRMod.slabSingleThatch;
        this.roofSlabMeta = 0;
        this.roofStairBlock = LOTRMod.stairsThatch;
        if(random.nextBoolean()) {
            this.barsBlock = Blocks.iron_bars;
            this.barsMeta = 0;
        }
        else {
            this.barsBlock = LOTRMod.bronzeBars;
            this.barsMeta = 0;
        }
        this.bedBlock = random.nextBoolean() ? LOTRMod.furBed : LOTRMod.strawBed;
    }

    protected ItemStack getRandomDunlandWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.daggerIron), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.dunlendingClub), new ItemStack(LOTRMod.dunlendingTrident)};
        return items[random.nextInt(items.length)].copy();
    }

    protected void placeDunlandItemFrame(World world, Random random, int i, int j, int k, int direction) {
        ItemStack[] items = new ItemStack[] {new ItemStack(Items.bone), new ItemStack(LOTRMod.fur), new ItemStack(Items.flint), new ItemStack(Items.iron_sword), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearStone), new ItemStack(Items.bow), new ItemStack(Items.arrow), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.skullCup)};
        ItemStack item = items[random.nextInt(items.length)].copy();
        this.spawnItemFrame(world, i, j, k, direction, item);
    }
}
