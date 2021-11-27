package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenRangerVillagePalisade extends LOTRWorldGenRangerStructure {
    public LOTRWorldGenRangerVillagePalisade(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions && !this.isSurface(world, i1 = 0, this.getTopBlock(world, i1, k1 = 0) - 1, k1)) {
            return false;
        }
        for(int j12 = 1; (((j12 >= 0) || !this.isOpaque(world, 0, j12, 0)) && (this.getY(j12) >= 0)); --j12) {
            this.setBlockAndMetadata(world, 0, j12, 0, this.cobbleBlock, this.cobbleMeta);
            this.setGrassToDirt(world, 0, j12 - 1, 0);
        }
        int height = 5 + random.nextInt(2);
        for(int j13 = 2; j13 <= height; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, 0, this.logBlock, this.logMeta);
        }
        return true;
    }
}
