package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenMoredainHut extends LOTRWorldGenStructureBase2 {
    protected Block clayBlock = Blocks.hardened_clay;
    protected int clayMeta = 0;
    protected Block stainedClayBlock = Blocks.stained_hardened_clay;
    protected int stainedClayMeta = 1;
    protected Block brickBlock = LOTRMod.brick3;
    protected int brickMeta = 10;
    protected Block brickSlabBlock = LOTRMod.slabSingle7;
    protected int brickSlabMeta = 0;
    protected Block plankBlock = Blocks.planks;
    protected int plankMeta = 4;
    protected Block plankSlabBlock = Blocks.wooden_slab;
    protected int plankSlabMeta = 4;
    protected Block fenceBlock = Blocks.fence;
    protected int fenceMeta = 4;
    protected Block thatchBlock = LOTRMod.thatch;
    protected int thatchMeta = 0;
    protected Block thatchSlabBlock = LOTRMod.slabSingleThatch;
    protected int thatchSlabMeta = 0;

    public LOTRWorldGenMoredainHut(boolean flag) {
        super(flag);
    }

    protected abstract int getOffset();

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, this.getOffset());
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int range = this.getOffset();
            for(int i1 = -range; i1 <= range; ++i1) {
                for(int k1 = -range; k1 <= range; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1);
                    Block block = this.getBlock(world, i1, j1 - 1, k1);
                    if(block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.stone) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 5) continue;
                    return false;
                }
            }
        }
        return true;
    }

    protected void layFoundation(World world, int i, int k) {
        for(int j = 0; (((j == 0) || !this.isOpaque(world, i, j, k)) && (this.getY(j) >= 0)); --j) {
            this.setBlockAndMetadata(world, i, j, k, this.clayBlock, this.clayMeta);
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }

    protected void dropFence(World world, int i, int j, int k) {
        do {
            this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
            if(this.isOpaque(world, i, j - 1, k)) break;
            --j;
        }
        while(true);
    }
}
