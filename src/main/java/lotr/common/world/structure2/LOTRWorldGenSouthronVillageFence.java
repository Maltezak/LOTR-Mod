package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronVillageFence extends LOTRWorldGenSouthronStructure {
    private int leftExtent;
    private int rightExtent;

    public LOTRWorldGenSouthronVillageFence(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenSouthronVillageFence setLeftRightExtent(int l, int r) {
        this.leftExtent = l;
        this.rightExtent = r;
        return this;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.fenceBlock = LOTRMod.fence2;
        this.fenceMeta = 2;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = -this.leftExtent; i1 <= this.rightExtent; ++i1) {
            int k1 = 0;
            int j1 = this.getTopBlock(world, i1, k1) - 1;
            if(!this.isSurface(world, i1, j1, k1) || this.isOpaque(world, i1, j1 + 1, k1)) continue;
            this.setBlockAndMetadata(world, i1, j1 + 1, k1, this.fenceBlock, this.fenceMeta);
        }
        return true;
    }
}
