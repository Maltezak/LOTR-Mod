package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronTownWall extends LOTRWorldGenSouthronStructure {
    protected boolean centrePillar;
    protected int leftExtent;
    protected int rightExtent;

    public LOTRWorldGenSouthronTownWall(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    protected boolean forceCedarWood() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = -this.leftExtent; i1 <= this.rightExtent; ++i1) {
            int k3;
            int j1;
            int pillarOffset;
            Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            for(int k32 = k1; k32 <= k1 + 1; ++k32) {
                int j12;
                for(j12 = 1; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k32)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k32, this.stoneBlock, this.stoneMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k32);
                }
                for(j12 = 2; j12 <= 4; ++j12) {
                    this.setBlockAndMetadata(world, i1, j12, k32, this.brickBlock, this.brickMeta);
                }
            }
            this.setBlockAndMetadata(world, i1, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 5, k1 + 1, this.stoneBlock, this.stoneMeta);
            this.setBlockAndMetadata(world, i1, 5, k1 + 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 6, k1 + 2, this.fenceBlock, this.fenceMeta);
            pillarOffset = this.centrePillar ? IntMath.mod(i1, 4) : IntMath.mod(i1 + 2, 4);
            if(pillarOffset == 0) {
                k3 = k1 - 1;
                for(j1 = 4; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k3)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k3, this.pillarBlock, this.pillarMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k3);
                }
                this.setBlockAndMetadata(world, i1, 5, k3, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 6, k3, this.brickWallBlock, this.brickWallMeta);
            }
            else if(pillarOffset == 1) {
                this.setBlockAndMetadata(world, i1, 5, k1 - 1, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i1, 6, k1 - 1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 7, k1 - 1, this.brickSlabBlock, this.brickSlabMeta);
            }
            else if(pillarOffset == 2) {
                this.setBlockAndMetadata(world, i1, 5, k1 - 1, this.brickSlabBlock, this.brickSlabMeta | 8);
                this.setBlockAndMetadata(world, i1, 6, k1 - 1, this.brickWallBlock, this.brickWallMeta);
            }
            else if(pillarOffset == 3) {
                this.setBlockAndMetadata(world, i1, 5, k1 - 1, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i1, 6, k1 - 1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 7, k1 - 1, this.brickSlabBlock, this.brickSlabMeta);
            }
            if(pillarOffset % 2 == 0) {
                this.setBlockAndMetadata(world, i1, 4, k1 + 2, this.plankStairBlock, 7);
                continue;
            }
            k3 = k1 + 2;
            for(j1 = 4; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k3)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k3, this.woodBeamBlock, this.woodBeamMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k3);
            }
        }
        return true;
    }

    public static class Extra extends LOTRWorldGenSouthronTownWall {
        public Extra(boolean flag) {
            super(flag);
            this.centrePillar = true;
            this.leftExtent = 1;
            this.rightExtent = 2;
        }
    }

    public static class SideMid extends LOTRWorldGenSouthronTownWall {
        public SideMid(boolean flag) {
            super(flag);
            this.centrePillar = false;
            this.leftExtent = 4;
            this.rightExtent = 4;
        }
    }

    public static class Long extends LOTRWorldGenSouthronTownWall {
        public Long(boolean flag) {
            super(flag);
            this.centrePillar = true;
            this.leftExtent = 2;
            this.rightExtent = 2;
        }
    }

    public static class Short extends LOTRWorldGenSouthronTownWall {
        public Short(boolean flag) {
            super(flag);
            this.centrePillar = true;
            this.leftExtent = 1;
            this.rightExtent = 1;
        }
    }

}
