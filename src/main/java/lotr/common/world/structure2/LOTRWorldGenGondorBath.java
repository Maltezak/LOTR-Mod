package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBath extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorBath(boolean flag) {
        super(flag);
    }

    protected LOTREntityNPC createBather(World world) {
        return new LOTREntityGondorMan(world);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int i2;
        int k2;
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -11; i12 <= 11; ++i12) {
                for(int k12 = -9; k12 <= 9; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12) - 1;
                    if(!this.isSurface(world, i12, j1, k12)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(int i13 = -11; i13 <= 11; ++i13) {
            for(k1 = -9; k1 <= 9; ++k1) {
                i2 = Math.abs(i13);
                k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= -1) || !this.isOpaque(world, i13, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i13, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i13, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i13, j1, k1);
                }
                if(i2 > 6 || k2 > 4 || i2 + k2 > 8) continue;
                this.setBlockAndMetadata(world, i13, 0, k1, Blocks.water, 0);
            }
        }
        for(int i14 = -10; i14 <= 10; ++i14) {
            for(k1 = -8; k1 <= 8; ++k1) {
                i2 = Math.abs(i14);
                k2 = Math.abs(k1);
                if(i2 == 10 && k2 % 4 == 0 || k2 == 8 && i2 % 4 == 2) {
                    for(j1 = 1; j1 <= 4; ++j1) {
                        this.setBlockAndMetadata(world, i14, j1, k1, this.pillarBlock, this.pillarMeta);
                    }
                    this.setBlockAndMetadata(world, i14 - 1, 1, k1, this.brickStairBlock, 1);
                    this.setBlockAndMetadata(world, i14 + 1, 1, k1, this.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i14, 1, k1 - 1, this.brickStairBlock, 2);
                    this.setBlockAndMetadata(world, i14, 1, k1 + 1, this.brickStairBlock, 3);
                    this.setBlockAndMetadata(world, i14 - 1, 4, k1, this.brickStairBlock, 5);
                    this.setBlockAndMetadata(world, i14 + 1, 4, k1, this.brickStairBlock, 4);
                    this.setBlockAndMetadata(world, i14, 4, k1 - 1, this.brickStairBlock, 6);
                    this.setBlockAndMetadata(world, i14, 4, k1 + 1, this.brickStairBlock, 7);
                }
                if(i2 != 10 && k2 != 8) continue;
                this.setBlockAndMetadata(world, i14, 5, k1, this.brickBlock, this.brickMeta);
            }
        }
        int[] i14 = new int[] {-6, 6};
        k1 = i14.length;
        for(i2 = 0; i2 < k1; ++i2) {
            int i15 = i14[i2];
            for(int k13 : new int[] {-4, 4}) {
                for(int j12 = 1; j12 <= 7; ++j12) {
                    this.setBlockAndMetadata(world, i15, j12, k13, this.pillarBlock, this.pillarMeta);
                }
                this.setBlockAndMetadata(world, i15 - 1, 1, k13, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i15 + 1, 1, k13, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i15, 1, k13 - 1, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i15, 1, k13 + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i15 - 1, 7, k13, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i15 + 1, 7, k13, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i15, 7, k13 - 1, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i15, 7, k13 + 1, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, i15 - 1, 4, k13, Blocks.torch, 1);
                this.setBlockAndMetadata(world, i15 + 1, 4, k13, Blocks.torch, 2);
                this.setBlockAndMetadata(world, i15, 4, k13 - 1, Blocks.torch, 4);
                this.setBlockAndMetadata(world, i15, 4, k13 + 1, Blocks.torch, 3);
            }
        }
        for(int step = 0; step <= 3; ++step) {
            int j13 = 5 + step;
            int i12 = 11 - step;
            int k12 = 9 - step;
            for(int i22 = -i12; i22 <= i12; ++i22) {
                this.setBlockAndMetadata(world, i22, j13, -k12, this.brick2StairBlock, 2);
                this.setBlockAndMetadata(world, i22, j13, k12, this.brick2StairBlock, 3);
            }
            for(int k22 = -k12 + 1; k22 <= k12 - 1; ++k22) {
                this.setBlockAndMetadata(world, -i12, j13, k22, this.brick2StairBlock, 1);
                this.setBlockAndMetadata(world, i12, j13, k22, this.brick2StairBlock, 0);
            }
            if(step < 2) continue;
            for(int i22 = -i12 + 1; i22 <= i12 - 1; ++i22) {
                this.setBlockAndMetadata(world, i22, j13 - 1, -k12, this.brick2StairBlock, 7);
                this.setBlockAndMetadata(world, i22, j13 - 1, k12, this.brick2StairBlock, 6);
            }
            for(int k22 = -k12; k22 <= k12; ++k22) {
                this.setBlockAndMetadata(world, -i12, j13 - 1, k22, this.brick2StairBlock, 4);
                this.setBlockAndMetadata(world, i12, j13 - 1, k22, this.brick2StairBlock, 5);
            }
        }
        for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
                this.setBlockAndMetadata(world, i1, 8, k1, this.brick2Block, this.brick2Meta);
            }
        }
        for(i1 = -9; i1 <= 9; ++i1) {
            for(k1 = -7; k1 <= 7; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if((i2 != 9 || k2 % 4 != 0) && (k2 != 7 || i2 % 4 != 2)) continue;
                for(int j14 = 5; j14 <= 6; ++j14) {
                    this.setBlockAndMetadata(world, i1, j14, k1, this.brickBlock, this.brickMeta);
                }
            }
        }
        int bathers = 2 + random.nextInt(4);
        for(int l = 0; l < bathers; ++l) {
            LOTREntityNPC man = this.createBather(world);
            this.spawnNPCAndSetHome(man, world, 0, 0, 0, 16);
        }
        return true;
    }
}
