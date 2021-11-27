package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenTauredainHouse extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
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
    protected Block thatchBlock;
    protected int thatchMeta;
    protected Block thatchSlabBlock;
    protected int thatchSlabMeta;
    protected Block thatchStairBlock;
    protected Block bedBlock;
    protected Block plateBlock;

    public LOTRWorldGenTauredainHouse(boolean flag) {
        super(flag);
    }

    protected abstract int getOffset();

    protected boolean useStoneBrick() {
        return false;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        if(this.useStoneBrick()) {
            this.brickBlock = LOTRMod.brick4;
            this.brickMeta = 0;
            this.brickSlabBlock = LOTRMod.slabSingle8;
            this.brickSlabMeta = 0;
            this.brickStairBlock = LOTRMod.stairsTauredainBrick;
            this.brickWallBlock = LOTRMod.wall4;
            this.brickWallMeta = 0;
        }
        else {
            this.brickBlock = LOTRMod.brick5;
            this.brickMeta = 0;
            this.brickSlabBlock = LOTRMod.slabSingle9;
            this.brickSlabMeta = 5;
            this.brickStairBlock = LOTRMod.stairsMudBrick;
            this.brickWallBlock = LOTRMod.wall3;
            this.brickWallMeta = 8;
        }
        if(random.nextBoolean()) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 7;
        }
        else {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 12;
        }
        if(random.nextInt(3) == 0) {
            this.woodBlock = LOTRMod.wood6;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 8;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 0;
            this.plankStairBlock = LOTRMod.stairsMahogany;
            this.doorBlock = LOTRMod.doorMahogany;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 8;
            this.fenceGateBlock = LOTRMod.fenceGateMahogany;
        }
        else {
            this.woodBlock = Blocks.log;
            this.woodMeta = 3;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 3;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 3;
            this.plankStairBlock = Blocks.jungle_stairs;
            this.doorBlock = LOTRMod.doorJungle;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 3;
            this.fenceGateBlock = LOTRMod.fenceGateJungle;
        }
        this.thatchBlock = LOTRMod.thatch;
        this.thatchMeta = 1;
        this.thatchSlabBlock = LOTRMod.slabSingleThatch;
        this.thatchSlabMeta = 1;
        this.thatchStairBlock = LOTRMod.stairsReed;
        this.bedBlock = LOTRMod.strawBed;
        this.plateBlock = LOTRMod.woodPlateBlock;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, this.getOffset());
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int range = this.getOffset();
            for(int i1 = -range; i1 <= range; ++i1) {
                for(int k1 = -range; k1 <= range; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        return true;
    }

    protected void layFoundation(World world, int i, int k) {
        for(int j = 0; (((j == 0) || !this.isOpaque(world, i, j, k)) && (this.getY(j) >= 0)); --j) {
            this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }

    protected void placeTauredainFlowerPot(World world, int i, int j, int k, Random random) {
        ItemStack plant = null;
        if(random.nextInt(3) == 0) {
            plant = this.getRandomFlower(world, random);
        }
        else {
            int l = random.nextInt(6);
            if(l == 0) {
                plant = new ItemStack(Blocks.sapling, 1, 3);
            }
            else if(l == 1) {
                plant = new ItemStack(LOTRMod.sapling6, 1, 0);
            }
            else if(l == 2) {
                plant = new ItemStack(LOTRMod.fruitSapling, 1, 3);
            }
            else if(l == 3) {
                plant = new ItemStack(Blocks.tallgrass, 1, 2);
            }
            else if(l == 4) {
                plant = new ItemStack(Blocks.tallgrass, 1, 1);
            }
            else if(l == 5) {
                plant = new ItemStack(LOTRMod.tallGrass, 1, 5);
            }
        }
        this.placeFlowerPot(world, i, j, k, plant);
    }

    protected void placeTauredainTorch(World world, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.tauredainDoubleTorch, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.tauredainDoubleTorch, 1);
    }
}
