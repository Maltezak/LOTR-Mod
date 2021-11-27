package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public class LOTRWorldGenGondorTownWall extends LOTRWorldGenGondorStructure {
    private int xMin;
    private int xMax;
    private int xMinInner;
    private int xMaxInner;

    private LOTRWorldGenGondorTownWall(boolean flag, int x0, int x1) {
        this(flag, x0, x1, x0, x1);
    }

    private LOTRWorldGenGondorTownWall(boolean flag, int x0, int x1, int xi0, int xi1) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
        this.xMinInner = xi0;
        this.xMaxInner = xi1;
    }

    public static LOTRWorldGenGondorTownWall Centre(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -5, 5);
    }

    public static LOTRWorldGenGondorTownWall Left(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -9, 6);
    }

    public static LOTRWorldGenGondorTownWall Right(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 9);
    }

    public static LOTRWorldGenGondorTownWall LeftEnd(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 6, -5, 6);
    }

    public static LOTRWorldGenGondorTownWall RightEnd(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 6, -6, 5);
    }

    public static LOTRWorldGenGondorTownWall LeftEndShort(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -5, 6);
    }

    public static LOTRWorldGenGondorTownWall RightEndShort(boolean flag) {
        return new LOTRWorldGenGondorTownWall(flag, -6, 5);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = this.xMin; i1 <= this.xMax; ++i1) {
            int j1;
            Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            for(j1 = 1; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
            for(j1 = 2; j1 <= 3; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, i1, 4, k1, this.brick2Block, this.brick2Meta);
            int i3 = IntMath.mod(i1, 4);
            if(i3 == 2) {
                this.setBlockAndMetadata(world, i1, 5, k1, this.rockWallBlock, this.rockWallMeta);
            }
            else {
                this.setBlockAndMetadata(world, i1, 5, k1, this.brickBlock, this.brickMeta);
                if(i3 == 3) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.brickStairBlock, 1);
                }
                else if(i3 == 0) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.brickBlock, this.brickMeta);
                }
                else if(i3 == 1) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.brickStairBlock, 0);
                }
            }
            if(i1 < this.xMinInner || i1 > this.xMaxInner) continue;
            for(k1 = 1; k1 <= 1; ++k1) {
                for(int j12 = 4; (((j12 >= 0) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                }
            }
        }
        return true;
    }
}
