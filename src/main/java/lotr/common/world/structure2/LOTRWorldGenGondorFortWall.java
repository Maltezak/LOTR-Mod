package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorFortWall extends LOTRWorldGenGondorStructure {
    protected boolean isRight;

    public LOTRWorldGenGondorFortWall(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int xMin = -8;
        int xMax = 6;
        if(this.isRight) {
            xMin = -6;
            xMax = 8;
        }
        for(int i1 = xMin; i1 <= xMax; ++i1) {
            int j1;
            boolean pillar;
            int i2 = Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            pillar = i2 % 3 == 0;
            if(pillar) {
                for(j1 = 4; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.pillar2Block, this.pillar2Meta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
            }
            else {
                for(j1 = 3; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                this.setBlockAndMetadata(world, i1, 4, k1, this.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, i1, 5, k1, this.brick2WallBlock, this.brick2WallMeta);
            if(pillar) {
                this.setBlockAndMetadata(world, i1, 6, k1, Blocks.torch, 5);
            }
            this.setBlockAndMetadata(world, i1, 4, 1, this.rockSlabBlock, this.rockSlabMeta | 8);
        }
        return true;
    }

    public static class Right extends LOTRWorldGenGondorFortWall {
        public Right(boolean flag) {
            super(flag);
            this.isRight = true;
        }
    }

    public static class Left extends LOTRWorldGenGondorFortWall {
        public Left(boolean flag) {
            super(flag);
            this.isRight = false;
        }
    }

}
