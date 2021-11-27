package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingStatue extends LOTRWorldGenEasterlingStructure {
    public LOTRWorldGenEasterlingStatue(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int k12;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -6; i1 <= 6; ++i1) {
                for(k12 = -5; k12 <= 5; ++k12) {
                    int j12 = this.getTopBlock(world, i1, k12) - 1;
                    if(this.isSurface(world, i1, j12, k12)) continue;
                    return false;
                }
            }
        }
        for(i1 = -6; i1 <= 6; ++i1) {
            for(k12 = -5; k12 <= 5; ++k12) {
                int j13;
                Math.abs(i1);
                Math.abs(k12);
                for(j13 = 1; (((j13 >= 0) || !this.isOpaque(world, i1, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                    this.setBlockAndMetadata(world, i1, j13, k12, LOTRMod.rock, 4);
                    this.setGrassToDirt(world, i1, j13 - 1, k12);
                }
                for(j13 = 2; j13 <= 25; ++j13) {
                    this.setAir(world, i1, j13, k12);
                }
                if(i1 == -6) {
                    this.setBlockAndMetadata(world, i1, 1, k12, LOTRMod.stairsRedRock, 1);
                    continue;
                }
                if(i1 == 6) {
                    this.setBlockAndMetadata(world, i1, 1, k12, LOTRMod.stairsRedRock, 0);
                    continue;
                }
                if(k12 == -5) {
                    this.setBlockAndMetadata(world, i1, 1, k12, LOTRMod.stairsRedRock, 2);
                    continue;
                }
                if(k12 != 5) continue;
                this.setBlockAndMetadata(world, i1, 1, k12, LOTRMod.stairsRedRock, 3);
            }
        }
        for(k1 = 0; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -2, 2, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 2, k1, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -2, 2, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 2, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -2, 3, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 3, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 3, 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 3, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 4, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 4, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 4, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 4, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 5, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 5, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 5, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 5, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 5, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -2, 6, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 6, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 6, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 6, 2, this.brickBlock, this.brickMeta);
        for(k1 = -1; k1 <= 0; ++k1) {
            this.setBlockAndMetadata(world, 1, 2, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 2, k1, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, 1, 2, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 2, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 3, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 3, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 3, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 4, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 4, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 4, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 4, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 5, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 5, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 5, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 3, 5, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 6, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 6, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 6, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 6, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 6, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 6, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 7, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 7, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 7, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 7, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 7, 1, this.brickStairBlock, 5);
        for(i1 = -1; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 7, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 7, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 7, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 7, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -1, 7, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 7, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 7, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 7, 2, this.brickBlock, this.brickMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 0, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 8, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 8, 1, this.brickStairBlock, 5);
        for(i1 = -1; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 1, this.brickBlock, this.brickMeta);
        }
        for(i1 = -2; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 8, 2, this.brickStairBlock, 4);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 9, 0, this.brickWallBlock, this.brickWallMeta);
        }
        this.setBlockAndMetadata(world, 2, 9, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -2, 9, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 9, 1, this.brickWallBlock, this.brickWallMeta);
        for(i1 = 0; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 9, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 9, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -3, 9, 2, this.brickStairBlock, 5);
        for(i1 = -2; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 9, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 9, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 10, 0, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 10, 0, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 10, 0, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 10, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 2, 10, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -2, 10, 2, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 10, 2, this.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 2, 10, 2, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 11, 0, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 11, 0, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 11, 0, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -2, 11, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, 2, 11, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -2, 11, 2, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 11, 2, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, 2, 11, 2, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 12, 0, this.brickWallBlock, this.brickWallMeta);
        }
        this.setBlockAndMetadata(world, -2, 12, 1, this.brickStairBlock, 5);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 12, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 12, 1, this.brickStairBlock, 4);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 12, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -1, 13, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 13, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 13, 0, this.brickStairBlock, 4);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 13, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 13, 2, this.brickStairBlock, 5);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 13, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 13, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 14, 0, this.brickStairBlock, 1);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, 0, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 14, 0, this.brickStairBlock, 0);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 14, 2, this.brickStairBlock, 1);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 14, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 15, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 15, 0, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 15, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -2, 15, 1, this.brickStairBlock, 1);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 15, 1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 2, 15, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 15, 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 15, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 15, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -4, 2, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 2, 0, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 2, 2, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 2, 3, this.brickBlock, this.brickMeta);
        for(k1 = -1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -4, 3, k1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -4, 4, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 4, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 4, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -4, 4, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 4, 3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 5, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 5, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -4, 5, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, -1, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 6, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, 1, this.pillarBlock, this.pillarMeta);
        this.setBlockAndMetadata(world, -4, 6, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 6, 3, this.brickStairBlock, 7);
        for(k1 = -1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -4, 7, k1, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -5, 8, -1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 8, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 8, 0, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 8, 2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -5, 9, -1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 9, -1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 9, 0, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 10, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 10, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 10, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 11, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 11, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -4, 11, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 11, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 12, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 12, 0, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 12, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 12, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 12, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 13, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 13, 0, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 13, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 13, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 13, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 14, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 14, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 14, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 14, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 14, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 14, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 15, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 15, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 15, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 15, 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 16, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 16, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 15, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 15, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 15, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 3, 15, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, -1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 4, 14, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 14, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 4, 14, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 14, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 13, -3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 13, -3, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 13, -2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 3, 13, -2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 13, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 3, 13, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 4, 13, -1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 4, 13, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 13, 1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 12, -3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 2, 12, -3, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 12, -2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 3, 12, -2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 12, -1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 4, 12, -1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 4, 12, 0, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 2, -3, this.brickBlock, this.brickMeta);
        for(j1 = 3; j1 <= 11; ++j1) {
            this.setBlockAndMetadata(world, 1, j1, -3, this.brickWallBlock, this.brickWallMeta);
        }
        for(j1 = 14; j1 <= 18; ++j1) {
            this.setBlockAndMetadata(world, 1, j1, -3, this.brickWallBlock, this.brickWallMeta);
        }
        for(j1 = 19; j1 <= 27; ++j1) {
            this.setBlockAndMetadata(world, 1, j1, -3, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 1, 28, -3, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 21, -4, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 21, -2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 23, -4, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 24, -5, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 1, 24, -4, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 25, -5, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 25, -4, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 26, -4, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 16, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 16, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 16, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 16, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 16, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 16, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 16, 2, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, -1, 17, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 17, 0, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, 1, 17, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 17, 1, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 17, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 17, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 17, 2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 17, 2, this.brickStairBlock, 4);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 18, -1, this.brickStairBlock, 6);
        }
        for(k1 = 0; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, -2, 18, k1, this.brickStairBlock, 5);
            for(int i12 = -1; i12 <= 1; ++i12) {
                this.setBlockAndMetadata(world, i12, 18, k1, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, 2, 18, k1, this.brickStairBlock, 4);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 18, 3, this.brickStairBlock, 7);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 19, 0, this.brickStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -1, 19, 1, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 19, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 19, 1, this.brickStairBlock, 0);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 19, 2, this.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -2, 20, 2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 20, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 20, 2, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 20, 2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 21, 2, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 2, 21, 2, this.brickStairBlock, 1);
        return true;
    }
}
