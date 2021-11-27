package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillageTower extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleVillageTower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i12 = -3; i12 <= 3; ++i12) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i12, k1) - 1;
                    Block block = this.getBlock(world, i12, j1, k1);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -3; k1 <= 3; ++k1) {
                int j1;
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k1);
                if(i2 <= 2 && k2 <= 2) {
                    for(j1 = 0; (((j1 == 0) || !this.isOpaque(world, i12, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.floorBlock, this.floorMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k1);
                    }
                    for(j1 = 1; j1 <= 10; ++j1) {
                        this.setAir(world, i12, j1, k1);
                    }
                }
                if(i2 == 2 && k2 == 3 || k2 == 2 && i2 == 3) {
                    j1 = 1;
                    while(!this.isOpaque(world, i12, j1, k1) && this.getY(j1) >= 0) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.brickWallBlock, this.brickWallMeta);
                        --j1;
                    }
                }
                if(i2 > 2 || k2 > 2) continue;
                if(i2 == 2 && k2 == 2) {
                    for(j1 = 1; j1 <= 6; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.brickBlock, this.brickMeta);
                    }
                }
                else if(i2 == 2 || k2 == 2) {
                    for(j1 = 4; j1 <= 6; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if(i2 == 2 && k2 == 2) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.brickWallBlock, this.brickWallMeta);
                }
                if(i2 == 2 && k2 == 1 || k2 == 2 && i2 == 1) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.brickBlock, this.brickMeta);
                }
                if(i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.barsBlock, 0);
                    this.setBlockAndMetadata(world, i12, 8, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i12, 9, k1, this.brickSlabBlock, this.brickSlabMeta);
                }
                if(i2 > 1 || k2 > 1) continue;
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i12, 6, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                    this.setBlockAndMetadata(world, i12, 7, k1, this.brickWallBlock, this.brickWallMeta);
                    for(j1 = 8; j1 <= 11; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.brickBlock, this.brickMeta);
                    }
                    this.setBlockAndMetadata(world, i12, 12, k1, this.brickWallBlock, this.brickWallMeta);
                }
                if(i2 == 1 && k2 == 0 || k2 == 1 && i2 == 0) {
                    this.setBlockAndMetadata(world, i12, 9, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i12, 10, k1, this.brickBlock, this.brickMeta);
                }
                if(i2 != 0 || k2 != 0) continue;
                this.setBlockAndMetadata(world, i12, 9, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i12, 10, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i12, 11, k1, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i12, 12, k1, Blocks.fire, 0);
                this.setBlockAndMetadata(world, i12, 13, k1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i12, 14, k1, this.roofSlabBlock, this.roofSlabMeta);
            }
        }
        for(int i13 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i13, 3, -1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i13, 3, 1, this.brickStairBlock, 6);
        }
        for(int k12 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, -1, 3, k12, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 3, k12, this.brickStairBlock, 5);
        }
        for(int i14 = -3; i14 <= 3; ++i14) {
            this.setBlockAndMetadata(world, i14, 5, -3, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i14, 5, 3, this.brickStairBlock, 7);
            if(IntMath.mod(i14, 2) != 1) continue;
            this.setBlockAndMetadata(world, i14, 6, -3, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i14, 6, 3, this.brickWallBlock, this.brickWallMeta);
        }
        for(int k13 = -2; k13 <= 2; ++k13) {
            this.setBlockAndMetadata(world, -3, 5, k13, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k13, this.brickStairBlock, 4);
            if(IntMath.mod(k13, 2) != 1) continue;
            this.setBlockAndMetadata(world, -3, 6, k13, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 6, k13, this.brickWallBlock, this.brickWallMeta);
        }
        for(int i13 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i13, 8, -1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i13, 8, 1, this.brickStairBlock, 3);
        }
        for(int k12 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, -1, 8, k12, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 1, 8, k12, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 8, 0, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 8, 0, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 8, -1, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 8, 1, this.brickStairBlock, 6);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 10, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 10, 2, this.roofStairBlock, 3);
        }
        for(int k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, -2, 10, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 10, k14, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 11, 0, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 11, 0, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 11, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 1, this.brickStairBlock, 3);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 13, -1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 13, 1, this.roofStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, 13, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 13, 0, this.roofStairBlock, 0);
        this.placeWallBanner(world, 0, 4, -2, LOTRItemBanner.BannerType.DALE, 2);
        this.placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DALE, 3);
        this.placeWallBanner(world, 0, 4, 2, LOTRItemBanner.BannerType.DALE, 0);
        this.placeWallBanner(world, 2, 4, 0, LOTRItemBanner.BannerType.DALE, 1);
        this.setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 4);
        return true;
    }
}
