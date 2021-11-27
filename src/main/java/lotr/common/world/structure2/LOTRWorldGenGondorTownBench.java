package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenGondorTownBench extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorTownBench(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i12 = -2; i12 <= 2; ++i12) {
                for(int k1 = 0; k1 <= 2; ++k1) {
                    int j1 = this.getTopBlock(world, i12, k1) - 1;
                    if(this.isSurface(world, i12, j1, k1)) continue;
                    return false;
                }
            }
        }
        int k1 = 0;
        int j1 = this.getTopBlock(world, 0, k1);
        this.setBlockAndMetadata(world, 0, j1, k1, this.rockSlabBlock, this.rockSlabMeta);
        this.setBlockAndMetadata(world, -1, j1, k1, this.rockStairBlock, 0);
        this.setBlockAndMetadata(world, 1, j1, k1, this.rockStairBlock, 1);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setGrassToDirt(world, i1, j1 - 1, k1);
            this.layFoundation(world, i1, j1 - 1, k1);
        }
        k1 = 2;
        j1 = this.getTopBlock(world, 0, k1);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
        }
        for(int i13 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i13, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            this.setGrassToDirt(world, i13, j1 - 1, k1);
            this.layFoundation(world, i13, j1 - 1, k1);
        }
        return true;
    }

    private void layFoundation(World world, int i, int j, int k) {
        for(int j1 = j; (((j1 >= j) || !this.isOpaque(world, i, j1, k)) && (this.getY(j1) >= 0)); --j1) {
            this.setBlockAndMetadata(world, i, j1, k, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            this.setGrassToDirt(world, i, j1 - 1, k);
        }
    }
}
