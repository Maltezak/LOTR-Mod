package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercDummy extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenMoredainMercDummy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i1 = -1; i1 <= 1; ++i1) {
                for(int k1 = -1; k1 <= 1; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(j1 != this.getTopBlock(world, 0, 0) - 1) {
                        return false;
                    }
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    for(int j2 = j1 + 1; j2 <= j1 + 3; ++j2) {
                        if(!this.isOpaque(world, i1, j2, k1)) continue;
                        return false;
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.fence2, 2);
        this.setBlockAndMetadata(world, 0, 2, 0, Blocks.wool, 12);
        this.placeSkull(world, random, 0, 3, 0);
        this.setBlockAndMetadata(world, -1, 2, 0, Blocks.lever, 1);
        this.setBlockAndMetadata(world, 1, 2, 0, Blocks.lever, 2);
        return true;
    }
}
