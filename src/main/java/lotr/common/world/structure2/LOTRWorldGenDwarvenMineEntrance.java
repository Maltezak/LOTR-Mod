package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDwarvenMineEntrance extends LOTRWorldGenStructureBase2 {
    private Block plankBlock;
    private int plankMeta;
    private Block plankSlabBlock;
    private int plankSlabMeta;
    private Block logBlock;
    private int logMeta;
    private Block fenceBlock;
    private int fenceMeta;
    public boolean isRuined = false;

    public LOTRWorldGenDwarvenMineEntrance(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.plankBlock = Blocks.planks;
        this.plankMeta = 1;
        this.plankSlabBlock = Blocks.wooden_slab;
        this.plankSlabMeta = 1;
        this.logBlock = Blocks.log;
        this.logMeta = 1;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 1;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int i12;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? 5 : 0);
        this.setupRandomBlocks(random);
        int coordDepth = 40;
        if(this.usingPlayer != null) {
            coordDepth = Math.max(this.getY(-30), 5);
        }
        int relDepth = coordDepth - this.originY;
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k1 = -4; k1 <= 4; ++k1) {
                int j12;
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k1);
                for(j12 = 1; j12 <= 5; ++j12) {
                    this.setAir(world, i12, j12, k1);
                }
                if(!this.isRuined) {
                    this.setBlockAndMetadata(world, i12, 0, k1, this.plankBlock, this.plankMeta);
                    if(i2 == 4 && k2 >= 2 || k2 == 4 && i2 >= 2) {
                        this.setBlockAndMetadata(world, i12, 1, k1, this.fenceBlock, this.fenceMeta);
                    }
                    if(i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
                        for(j12 = 2; j12 <= 3; ++j12) {
                            this.setBlockAndMetadata(world, i12, j12, k1, this.fenceBlock, this.fenceMeta);
                        }
                    }
                    if(i2 == 4 || k2 == 4) {
                        this.setBlockAndMetadata(world, i12, 4, k1, this.fenceBlock, this.fenceMeta);
                    }
                    if(i2 == 0 || k2 == 0) {
                        this.setBlockAndMetadata(world, i12, 4, k1, this.fenceBlock, this.fenceMeta);
                    }
                    if(i2 == 0 && k2 == 0) {
                        for(j12 = 1; j12 <= 3; ++j12) {
                            this.setBlockAndMetadata(world, i12, j12, k1, this.fenceBlock, this.fenceMeta);
                        }
                    }
                    if(i2 == 4 || k2 == 4 || i2 == 0 || k2 == 0 || i2 + k2 <= 2) {
                        this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta);
                    }
                }
                else if(i2 == 4 || k2 == 4) {
                    this.setBlockAndMetadata(world, i12, 0, k1, LOTRMod.pillar, 0);
                }
                else {
                    this.setAir(world, i12, 0, k1);
                }
                if(i2 != 4 || k2 != 4) continue;
                for(j12 = 1; j12 <= 3; ++j12) {
                    this.setBlockAndMetadata(world, i12, j12, k1, LOTRMod.pillar, 0);
                }
                if(this.isRuined) continue;
                this.setBlockAndMetadata(world, i12, 4, k1, LOTRMod.brick3, 12);
                this.setBlockAndMetadata(world, i12, 5, k1, LOTRMod.pillar, 0);
            }
        }
        for(j1 = -1; j1 > relDepth && this.getY(j1) >= 0; --j1) {
            for(i1 = -4; i1 <= 4; ++i1) {
                for(int k12 = -4; k12 <= 4; ++k12) {
                    int i2 = Math.abs(i1);
                    int k2 = Math.abs(k12);
                    if(i2 == 4 || k2 == 4) {
                        if(this.isRuined && random.nextInt(20) == 0) {
                            this.setAir(world, i1, j1, k12);
                            continue;
                        }
                        if(this.isRuined && random.nextInt(4) == 0) {
                            this.setBlockAndMetadata(world, i1, j1, k12, LOTRMod.brick4, 5);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i1, j1, k12, LOTRMod.brick, 6);
                        continue;
                    }
                    this.setAir(world, i1, j1, k12);
                }
            }
            this.setBlockAndMetadata(world, -3, j1, -3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, -3, j1, 3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, 3, j1, -3, LOTRMod.pillar, 0);
            this.setBlockAndMetadata(world, 3, j1, 3, LOTRMod.pillar, 0);
            if(this.isRuined || IntMath.mod(j1, 6) != 3) continue;
            this.setBlockAndMetadata(world, -3, j1, -3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, -3, j1, 3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, 3, j1, -3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, 3, j1, 3, LOTRMod.brick3, 12);
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -3; k1 <= 3; ++k1) {
                if(this.isOpaque(world, i12, relDepth, k1)) continue;
                this.setBlockAndMetadata(world, i12, relDepth, k1, Blocks.stone, 0);
            }
        }
        if(!this.isRuined) {
            for(i12 = -2; i12 <= 2; ++i12) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    this.setBlockAndMetadata(world, i12, relDepth, k1, LOTRMod.pillar, 0);
                }
            }
        }
        else {
            for(i12 = -2; i12 <= 2; ++i12) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    int h = 0;
                    if(random.nextInt(5) == 0) {
                        h += 1 + random.nextInt(1);
                    }
                    for(int j13 = 0; j13 <= h; ++j13) {
                        if(random.nextBoolean()) {
                            this.setBlockAndMetadata(world, i12, relDepth + h, k1, LOTRMod.pillar, 0);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i12, relDepth + h, k1, Blocks.stone, 0);
                    }
                }
            }
        }
        if(!this.isRuined) {
            for(j1 = 1; j1 > relDepth && this.getY(j1) >= 0; --j1) {
                this.setBlockAndMetadata(world, 0, j1, 0, this.logBlock, this.logMeta);
                this.setBlockAndMetadata(world, 0, j1, -1, Blocks.ladder, 2);
                this.setBlockAndMetadata(world, 0, j1, 1, Blocks.ladder, 3);
                this.setBlockAndMetadata(world, -1, j1, 0, Blocks.ladder, 5);
                this.setBlockAndMetadata(world, 1, j1, 0, Blocks.ladder, 4);
            }
        }
        for(j1 = relDepth + 1; j1 <= relDepth + 3; ++j1) {
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setAir(world, i1, j1, -4);
                this.setAir(world, i1, j1, 4);
            }
            for(k1 = -1; k1 <= 1; ++k1) {
                this.setAir(world, -4, j1, k1);
                this.setAir(world, 4, j1, k1);
            }
        }
        for(int k13 = -1; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, -4, relDepth + 1, k13, LOTRMod.slabSingle, 15);
            this.setBlockAndMetadata(world, 4, relDepth + 1, k13, LOTRMod.slabSingle, 15);
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            this.setBlockAndMetadata(world, i12, relDepth + 1, -4, LOTRMod.slabSingle, 15);
        }
        if(!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, -4, relDepth + 1, 0, LOTRMod.dwarvenForge, 4);
        }
        if(!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, 4, relDepth + 1, 0, LOTRMod.dwarvenForge, 5);
        }
        if(!this.isRuined || random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, 0, relDepth + 1, -4, LOTRMod.dwarvenForge, 3);
        }
        return true;
    }
}
