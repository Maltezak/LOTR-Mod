package lotr.common.world.structure2;

import java.util.*;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenSouthronTownTree extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronTownTree(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int j12;
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j12 = 1; j12 <= 12; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.stoneBlock, this.stoneMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                }
                if(i2 == 2 || k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.stoneBlock, this.stoneMeta);
                    if((i2 + k2) % 2 != 0) continue;
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 1, k1, Blocks.grass, 0);
            }
        }
        for(int l = 0; l < 16; ++l) {
            int i12 = 0;
            j1 = 2;
            int k12 = 0;
            LOTRTreeType tree = this.getRandomTree(random);
            WorldGenAbstractTree treeGen = tree.create(this.notifyChanges, random);
            if(treeGen != null && treeGen.generate(world, random, this.getX(i12, k12), this.getY(j1), this.getZ(i12, k12))) break;
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                if(i1 == 0 && k1 == 0 || !this.isAir(world, i1, 2, k1)) continue;
                this.plantTallGrass(world, random, i1, 2, k1);
            }
        }
        return true;
    }

    private LOTRTreeType getRandomTree(Random random) {
        ArrayList<LOTRTreeType> treeList = new ArrayList<>();
        treeList.add(LOTRTreeType.CEDAR);
        treeList.add(LOTRTreeType.CYPRESS);
        treeList.add(LOTRTreeType.PALM);
        treeList.add(LOTRTreeType.DATE_PALM);
        treeList.add(LOTRTreeType.OLIVE);
        return(treeList.get(random.nextInt(treeList.size())));
    }
}
