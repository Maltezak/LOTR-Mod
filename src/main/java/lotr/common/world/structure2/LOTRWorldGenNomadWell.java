package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNomadWell extends LOTRWorldGenNomadStructure {
    public LOTRWorldGenNomadWell(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    int j12 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j12, k1)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 2) continue;
                    return false;
                }
            }
        }
        for(int i12 = -2; i12 <= 2; ++i12) {
            for(int k12 = -2; k12 <= 2; ++k12) {
                if(!this.isSurface(world, i12, 0, k12)) {
                    this.laySandBase(world, i12, 0, k12);
                }
                for(int j13 = 1; j13 <= 4; ++j13) {
                    this.setAir(world, i12, j13, k12);
                }
            }
        }
        int waterDepth = 3 + random.nextInt(3);
        for(int i13 = -2; i13 <= 2; ++i13) {
            for(int k13 = -2; k13 <= 2; ++k13) {
                int i2 = Math.abs(i13);
                int k2 = Math.abs(k13);
                if(i2 == 2 || k2 == 2) {
                    this.setBlockAndMetadata(world, i13, 0, k13, Blocks.grass, 0);
                    this.setBlockAndMetadata(world, i13, 1, k13, this.fenceBlock, this.fenceMeta);
                    if(i2 == 2 && k2 == 2) {
                        this.setBlockAndMetadata(world, i13, 2, k13, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i13, 3, k13, Blocks.torch, 5);
                    }
                }
                for(int j14 = 0; j14 >= -waterDepth; --j14) {
                    if(this.isOpaque(world, i13, j14, k13)) continue;
                    this.setBlockAndMetadata(world, i13, j14, k13, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i13, j14 - 1, k13);
                }
                if(i2 > 1 || k2 > 1) continue;
                int minY = -waterDepth + 1 + random.nextInt(2);
                for(j1 = 0; j1 >= minY; --j1) {
                    this.setBlockAndMetadata(world, i13, j1, k13, Blocks.water, 0);
                }
            }
        }
        int grassRadius = 5;
        for(i1 = -grassRadius; i1 <= grassRadius; ++i1) {
            for(k1 = -grassRadius; k1 <= grassRadius; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 < 3 && k2 < 3 || i2 * i2 + k2 * k2 >= grassRadius * grassRadius || random.nextInt(3) == 0 || !this.isSurface(world, i1, j1 = this.getTopBlock(world, i1, k1) - 1, k1)) continue;
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
            }
        }
        this.setBlockAndMetadata(world, -2, 1, 0, this.fenceGateBlock, 3);
        this.setBlockAndMetadata(world, 2, 1, 0, this.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, 0, 1, -2, this.fenceGateBlock, 2);
        this.setBlockAndMetadata(world, 0, 1, 2, this.fenceGateBlock, 0);
        return true;
    }
}
