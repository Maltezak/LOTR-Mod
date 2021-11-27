package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainVillageTree extends LOTRWorldGenTauredainHouse {
    public LOTRWorldGenTauredainVillageTree(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 4;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                this.layFoundation(world, i1, k1);
                for(int j1 = 1; j1 <= 12; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == 3 || k2 == 3) {
                    if(i2 == 3 && k2 == 3) {
                        this.setBlockAndMetadata(world, i1, 1, k1, this.woodBlock, this.woodMeta);
                        this.setBlockAndMetadata(world, i1, 2, k1, this.woodBlock, this.woodMeta);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.mudGrass, 0);
                if(random.nextInt(2) != 0) continue;
                if(random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i1, 2, k1, Blocks.tallgrass, 1);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 2, k1, Blocks.tallgrass, 2);
            }
        }
        int treeX = 0;
        int treeY = 2;
        int treeZ = 0;
        this.setAir(world, treeX, treeY, treeZ);
        for(int attempts = 0; attempts < 20; ++attempts) {
            LOTRTreeType treeType = null;
            int randomTree = random.nextInt(4);
            if(randomTree == 0 || randomTree == 1) {
                treeType = LOTRTreeType.JUNGLE;
            }
            if(randomTree == 2) {
                treeType = LOTRTreeType.MANGO;
            }
            if(randomTree == 3) {
                treeType = LOTRTreeType.BANANA;
            }
            if(treeType.create(this.notifyChanges, random).generate(world, random, this.getX(treeX, treeZ), this.getY(treeY), this.getZ(treeX, treeZ))) break;
        }
        return true;
    }
}
