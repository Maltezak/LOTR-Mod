package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTownWall extends LOTRWorldGenEasterlingStructure {
    private int xMin;
    private int xMax;
    private boolean isCentre;

    private LOTRWorldGenEasterlingTownWall(boolean flag, int x0, int x1, boolean c) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
        this.isCentre = c;
    }

    public static LOTRWorldGenEasterlingTownWall Centre(boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -7, 7, true);
    }

    public static LOTRWorldGenEasterlingTownWall Left(boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -4, 3, false);
    }

    public static LOTRWorldGenEasterlingTownWall Right(boolean flag) {
        return new LOTRWorldGenEasterlingTownWall(flag, -3, 4, false);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = this.xMin; i1 <= this.xMax; ++i1) {
            Math.abs(i1);
            this.findSurface(world, i1, 0);
            for(int k1 = -1; k1 <= 1; ++k1) {
                int j1;
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                this.setBlockAndMetadata(world, i1, 1, k1, this.brickRedBlock, this.brickRedMeta);
                for(j1 = 2; j1 <= 5; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                }
                if(k1 != 0) continue;
                this.setBlockAndMetadata(world, i1, 5, k1, this.brickRedBlock, this.brickRedMeta);
            }
            if(IntMath.mod(i1, 2) == (this.isCentre ? 1 : 0)) {
                this.setBlockAndMetadata(world, i1, 5, -2, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i1, 6, -2, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i1, 7, -2, this.brickStairBlock, 3);
                continue;
            }
            this.setBlockAndMetadata(world, i1, 6, -2, this.brickWallBlock, this.brickWallMeta);
        }
        return true;
    }
}
