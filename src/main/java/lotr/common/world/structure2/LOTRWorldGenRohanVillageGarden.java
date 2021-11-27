package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageGarden extends LOTRWorldGenRohanStructure {
    private Block leafBlock;
    private int leafMeta;

    public LOTRWorldGenRohanVillageGarden(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.leafBlock = Blocks.leaves;
        this.leafMeta = 4;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(k1 = -1; k1 <= 1; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                Math.abs(k1);
                boolean foundSurface = false;
                for(j1 = 5; j1 >= -5; --j1) {
                    if(!this.isSurface(world, i1, j1 - 1, k1)) continue;
                    foundSurface = true;
                    break;
                }
                if(!foundSurface) continue;
                if(i2 <= 2) {
                    if(random.nextInt(3) == 0) {
                        this.plantFlower(world, random, i1, j1, k1);
                    }
                    else {
                        int j2 = j1;
                        if(random.nextInt(5) == 0) {
                            ++j2;
                        }
                        for(int j3 = j1; j3 <= j2; ++j3) {
                            this.setBlockAndMetadata(world, i1, j3, k1, this.leafBlock, this.leafMeta);
                        }
                    }
                }
                if(i2 != 3) continue;
                this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        return true;
    }
}
