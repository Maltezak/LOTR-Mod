package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronFortWall extends LOTRWorldGenSouthronStructure {
    protected boolean isLong;

    public LOTRWorldGenSouthronFortWall(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int xMin = -1;
        int xMax = 1;
        if(this.isLong) {
            xMin = -2;
            xMax = 2;
        }
        for(int i1 = xMin; i1 <= xMax; ++i1) {
            int j1;
            int j12;
            boolean beam;
            int i2 = Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            beam = i2 % 4 == 2;
            if(beam) {
                for(j12 = 6; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.woodBeamBlock, this.woodBeamMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                }
            }
            else {
                for(j12 = 5; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                }
                if(i2 % 2 == 1) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.plankStairBlock, 2);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                }
            }
            int k3 = k1 - 1;
            this.setBlockAndMetadata(world, i1, 2, k3, this.brickStairBlock, 2);
            for(j1 = 1; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k3)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k3, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k3);
            }
            k3 = k1 + 1;
            this.setBlockAndMetadata(world, i1, 2, k3, this.brickStairBlock, 3);
            for(j1 = 1; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k3)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k3, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k3);
            }
        }
        return true;
    }

    public static class Long extends LOTRWorldGenSouthronFortWall {
        public Long(boolean flag) {
            super(flag);
            this.isLong = true;
        }
    }

    public static class Short extends LOTRWorldGenSouthronFortWall {
        public Short(boolean flag) {
            super(flag);
            this.isLong = false;
        }
    }

}
