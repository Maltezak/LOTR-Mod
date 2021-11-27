package lotr.common.world.structure2;

import java.util.*;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenGondorTownTrees extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorTownTrees(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -6; i1 <= 6; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    int j12 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j12, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -6; i1 <= 6; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 % 4 != 2 && k2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
                }
                if(i2 % 4 != 2 || k2 != 2) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
                this.setBlockAndMetadata(world, i1, 2, k1, Blocks.torch, 5);
            }
        }
        for(int i12 : new int[] {-4, 0, 4}) {
            WorldGenAbstractTree treeGen;
            j1 = 1;
            int k12 = 0;
            for(int l = 0; ((l < 16) && (((treeGen = (LOTRWorldGenGondorTownTrees.getRandomTree(random)).create(this.notifyChanges, random)) == null) || !treeGen.generate(world, random, this.getX(i12, k12), this.getY(j1), this.getZ(i12, k12)))); ++l) {
            }
        }
        return true;
    }

    public static LOTRTreeType getRandomTree(Random random) {
        ArrayList<LOTRTreeType> treeList = new ArrayList<>();
        treeList.add(LOTRTreeType.CYPRESS);
        return(treeList.get(random.nextInt(treeList.size())));
    }
}
