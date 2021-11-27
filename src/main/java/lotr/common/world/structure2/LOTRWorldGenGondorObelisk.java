package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenGondorObelisk extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorObelisk(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int k12;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(k12 = -3; k12 <= 3; ++k12) {
                    j1 = this.getTopBlock(world, i1, k12) - 1;
                    if(this.isSurface(world, i1, j1, k12)) continue;
                    return false;
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k12 = -3; k12 <= 3; ++k12) {
                for(j1 = 3; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.placeRandomBrick(world, random, i1, j1, k12);
                    this.setGrassToDirt(world, i1, j1 - 1, k12);
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = -2; k12 <= 2; ++k12) {
                for(j1 = 4; j1 <= 8; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k12, this.rockBlock, this.rockMeta);
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.placeRandomStairs(world, random, i1, 4, -3, 2);
            this.placeRandomStairs(world, random, i1, 4, 3, 3);
        }
        for(k1 = -2; k1 <= 2; ++k1) {
            this.placeRandomStairs(world, random, -3, 4, k1, 1);
            this.placeRandomStairs(world, random, 3, 4, k1, 0);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k12 = -1; k12 <= 1; ++k12) {
                for(j1 = 9; j1 <= 14; ++j1) {
                    this.placeRandomBrick(world, random, i1, j1, k12);
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.placeRandomStairs(world, random, i1, 9, -2, 2);
            this.placeRandomStairs(world, random, i1, 9, 2, 3);
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            this.placeRandomStairs(world, random, -2, 9, k1, 1);
            this.placeRandomStairs(world, random, 2, 9, k1, 0);
        }
        for(int j12 = 15; j12 <= 18; ++j12) {
            this.placeRandomBrick(world, random, 0, j12, 0);
            this.placeRandomBrick(world, random, -1, j12, 0);
            this.placeRandomBrick(world, random, 1, j12, 0);
            this.placeRandomBrick(world, random, 0, j12, -1);
            this.placeRandomBrick(world, random, 0, j12, 1);
        }
        this.placeRandomStairs(world, random, -1, 19, 0, 1);
        this.placeRandomStairs(world, random, 1, 19, 0, 0);
        this.placeRandomStairs(world, random, 0, 19, -1, 2);
        this.placeRandomStairs(world, random, 0, 19, 1, 3);
        this.placeRandomBrick(world, random, 0, 19, 0);
        this.setBlockAndMetadata(world, 0, 20, 0, LOTRMod.beacon, 0);
        return true;
    }

    private void placeRandomBrick(World world, Random random, int i, int j, int k) {
        if(random.nextInt(4) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossyBlock, this.brickMossyMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedBlock, this.brickCrackedMeta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
        }
    }

    private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
        if(random.nextInt(4) == 0) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, this.brickMossyStairBlock, meta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.brickCrackedStairBlock, meta);
            }
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
        }
    }
}
