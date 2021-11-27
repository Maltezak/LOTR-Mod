package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingLamp extends LOTRWorldGenEasterlingStructure {
    public LOTRWorldGenEasterlingLamp(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -1; i1 <= 1; ++i1) {
                for(k1 = -1; k1 <= 1; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    if(i2 == 1 && k2 == 1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    }
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                if(i2 == 0 && k2 == 0) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickBlock, this.brickMeta);
                }
                if(i2 + k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, this.brickWallBlock, this.brickWallMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 3, 0, Blocks.glowstone, 0);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.trapdoor, 4);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.trapdoor, 5);
        this.setBlockAndMetadata(world, -1, 3, 0, Blocks.trapdoor, 7);
        this.setBlockAndMetadata(world, 1, 3, 0, Blocks.trapdoor, 6);
        return true;
    }
}
