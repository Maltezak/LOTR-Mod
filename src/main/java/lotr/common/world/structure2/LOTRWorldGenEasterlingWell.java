package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingWell extends LOTRWorldGenEasterlingStructure {
    public LOTRWorldGenEasterlingWell(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int wellBottom;
        int i1;
        int j1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    int j12 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j12, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.logBlock, this.logMeta);
                    for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta);
                }
                if(i2 == 0 && k2 == 1 || k2 == 0 && i2 == 1) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(i2 != 0 || k2 != 0) continue;
                this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i1, 7, k1, Blocks.torch, 5);
                for(j1 = 3; j1 <= 4; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.gateWoodenCross, 0);
        int depth = 0 + random.nextInt(2);
        int waterDepth = 2 + random.nextInt(4);
        int wellTop = -1;
        for(j1 = wellBottom = wellTop - depth - waterDepth - 1; j1 <= wellTop; ++j1) {
            for(int i12 = -1; i12 <= 1; ++i12) {
                for(int k12 = -1; k12 <= 1; ++k12) {
                    int i2 = Math.abs(i12);
                    int k2 = Math.abs(k12);
                    if(j1 == wellBottom) {
                        this.setBlockAndMetadata(world, i12, j1, k12, LOTRMod.dirtPath, 0);
                        continue;
                    }
                    if(i2 == 0 && k2 == 0) {
                        if(j1 <= wellBottom + waterDepth) {
                            this.setBlockAndMetadata(world, i12, j1, k12, Blocks.water, 0);
                            continue;
                        }
                        this.setAir(world, i12, j1, k12);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i12, j1, k12, LOTRMod.dirtPath, 0);
                }
            }
        }
        for(j1 = wellBottom + waterDepth + 1; j1 <= wellTop; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, 0, Blocks.ladder, 2);
        }
        return true;
    }
}
