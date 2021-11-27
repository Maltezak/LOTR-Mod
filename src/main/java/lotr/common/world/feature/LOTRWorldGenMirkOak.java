package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRWorldGenWoodElfPlatform;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMirkOak
extends WorldGenAbstractTree {
    private int minHeight;
    private int maxHeight;
    private int trunkWidth;
    private boolean isMirky;
    private boolean restrictions = true;
    private boolean isDead;
    private boolean hasRoots = true;
    private Block woodBlock = LOTRMod.wood;
    private int woodMeta = 2;
    private Block leafBlock = LOTRMod.leaves;
    private int leafMeta = 2;

    public LOTRWorldGenMirkOak(boolean flag, int i, int j, int k, boolean mirk) {
        super(flag);
        this.minHeight = i;
        this.maxHeight = j;
        this.trunkWidth = k;
        this.isMirky = mirk;
    }

    public LOTRWorldGenMirkOak setBlocks(Block b1, int m1, Block b2, int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }

    public LOTRWorldGenMirkOak setGreenOak() {
        return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves7, 1);
    }

    public LOTRWorldGenMirkOak setRedOak() {
        return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves, 3);
    }

    public LOTRWorldGenMirkOak disableRestrictions() {
        this.restrictions = false;
        return this;
    }

    public LOTRWorldGenMirkOak setDead() {
        this.isDead = true;
        return this;
    }

    public LOTRWorldGenMirkOak disableRoots() {
        this.hasRoots = false;
        return this;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (!this.restrictions || j >= 1 && j + height + 5 <= 256) {
            int i1;
            int j1;
            int k1;
            int i12;
            if (this.restrictions) {
                for (j1 = j; j1 <= j + height + 5; ++j1) {
                    int range = this.trunkWidth + 1;
                    if (j1 == j) {
                        range = this.trunkWidth;
                    }
                    if (j1 >= j + height + 2) {
                        range = this.trunkWidth + 2;
                    }
                    for (i1 = i - range; i1 <= i + range && flag; ++i1) {
                        for (int k12 = k - range; k12 <= k + range && flag; ++k12) {
                            if (j1 >= 0 && j1 < 256) {
                                if (this.isReplaceable(world, i1, j1, k12)) continue;
                                flag = false;
                                continue;
                            }
                            flag = false;
                        }
                    }
                }
                for (i12 = i - this.trunkWidth; i12 <= i + this.trunkWidth && flag; ++i12) {
                    for (k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth && flag; ++k1) {
                        Block block = world.getBlock(i12, j - 1, k1);
                        boolean isSoil = block.canSustainPlant(world, i12, j - 1, k1, ForgeDirection.UP, ((BlockSapling)Blocks.sapling));
                        if (isSoil) continue;
                        flag = false;
                    }
                }
                if (!flag) {
                    return false;
                }
            }
            if (this.restrictions) {
                for (i12 = i - this.trunkWidth; i12 <= i + this.trunkWidth; ++i12) {
                    for (k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth; ++k1) {
                        world.getBlock(i12, j - 1, k1).onPlantGrow(world, i12, j - 1, k1, i12, j, k1);
                    }
                }
            }
            for (j1 = 0; j1 < height; ++j1) {
                for (int i13 = i - this.trunkWidth; i13 <= i + this.trunkWidth; ++i13) {
                    for (int k13 = k - this.trunkWidth; k13 <= k + this.trunkWidth; ++k13) {
                        this.setBlockAndNotifyAdequately(world, i13, j + j1, k13, this.woodBlock, this.woodMeta);
                    }
                }
            }
            if (this.trunkWidth >= 1) {
                int deg = 0;
                while (deg < 360) {
                    float angle = (float)Math.toRadians(deg += (40 + random.nextInt(30)) / this.trunkWidth);
                    float cos = MathHelper.cos(angle);
                    float sin = MathHelper.sin(angle);
                    float angleY = random.nextFloat() * (float)Math.toRadians(40.0);
                    MathHelper.cos(angleY);
                    float sinY = MathHelper.sin(angleY);
                    int length = 3 + random.nextInt(6);
                    int i14 = i;
                    int k14 = k;
                    int j12 = j + height - 1 - random.nextInt(5);
                    for (int l = 0; l < (length *= 1 + random.nextInt(this.trunkWidth)); ++l) {
                        Block block;
                        if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
                            i14 = (int)(i14 + Math.signum(cos));
                        }
                        if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
                            k14 = (int)(k14 + Math.signum(sin));
                        }
                        if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
                            j12 = (int)(j12 + Math.signum(sinY));
                        }
                        if (!(block = world.getBlock(i14, j12, k14)).isReplaceable(world, i14, j12, k14) && !block.isWood(world, i14, j12, k14) && !block.isLeaves(world, i14, j12, k14)) break;
                        this.setBlockAndNotifyAdequately(world, i14, j12, k14, this.woodBlock, this.woodMeta);
                    }
                    this.growLeafCanopy(world, random, i14, j12, k14);
                }
                if (this.trunkWidth == 2) {
                    int platforms = 0;
                    if (random.nextInt(3) != 0) {
                        platforms = random.nextBoolean() ? 1 + random.nextInt(3) : 4 + random.nextInt(5);
                    }
                    for (int l = 0; l < platforms; ++l) {
                        int j13 = j + MathHelper.getRandomIntegerInRange(random, 10, height);
                        new LOTRWorldGenWoodElfPlatform(false).generate(world, random, i, j13, k);
                    }
                }
            } else {
                this.growLeafCanopy(world, random, i, j + height - 1, k);
            }
            if (this.hasRoots) {
                int roots = 4 + random.nextInt(5 * this.trunkWidth + 1);
                for (int l = 0; l < roots; ++l) {
                    i1 = i;
                    int j14 = j + 1 + random.nextInt(this.trunkWidth * 2 + 1);
                    int k15 = k;
                    int xDirection = 0;
                    int zDirection = 0;
                    int rootLength = 1 + random.nextInt(4);
                    if (random.nextBoolean()) {
                        if (random.nextBoolean()) {
                            i1 -= this.trunkWidth + 1;
                            xDirection = -1;
                        } else {
                            i1 += this.trunkWidth + 1;
                            xDirection = 1;
                        }
                        k15 -= this.trunkWidth + 1;
                        k15 += random.nextInt(this.trunkWidth * 2 + 2);
                    } else {
                        if (random.nextBoolean()) {
                            k15 -= this.trunkWidth + 1;
                            zDirection = -1;
                        } else {
                            k15 += this.trunkWidth + 1;
                            zDirection = 1;
                        }
                        i1 -= this.trunkWidth + 1;
                        i1 += random.nextInt(this.trunkWidth * 2 + 2);
                    }
                    for (int l1 = 0; l1 < rootLength; ++l1) {
                        int rootBlocks = 0;
                        int j2 = j14;
                        while (!world.getBlock(i1, j2, k15).isOpaqueCube()) {
                            this.setBlockAndNotifyAdequately(world, i1, j2, k15, this.woodBlock, this.woodMeta | 0xC);
                            world.getBlock(i1, j2 - 1, k15).onPlantGrow(world, i1, j2 - 1, k15, i1, j2, k15);
                            if (++rootBlocks > 5) break;
                            --j2;
                        }
                        --j14;
                        if (!random.nextBoolean()) continue;
                        if (xDirection == -1) {
                            --i1;
                            continue;
                        }
                        if (xDirection == 1) {
                            ++i1;
                            continue;
                        }
                        if (zDirection == -1) {
                            --k15;
                            continue;
                        }
                        if (zDirection != 1) continue;
                        ++k15;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void growLeafCanopy(World world, Random random, int i, int j, int k) {
        int j1;
        int leafStart = j + 2;
        int leafTop = j + 5;
        int maxRange = 3;
        if (!this.isDead) {
            for (j1 = leafStart; j1 <= leafTop; ++j1) {
                int j2 = j1 - leafTop;
                int leafRange = maxRange - j2;
                int leafRangeSq = leafRange * leafRange;
                for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
                    for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
                        int k2;
                        boolean grow;
                        int i2 = Math.abs(i1 - i);
                        int dist = i2 * i2 + (k2 = Math.abs(k1 - k)) * k2;
                        grow = dist < leafRangeSq;
                        if (i2 == leafRange - 1 || k2 == leafRange - 1) {
                            grow &= random.nextInt(4) > 0;
                        }
                        if (!grow) continue;
                        int below = 0;
                        if (this.isMirky && j1 == leafStart && i2 <= 3 && k2 <= 3 && random.nextInt(3) == 0) {
                            ++below;
                        }
                        for (int j3 = j1; j3 >= j1 - below; --j3) {
                            Block block = world.getBlock(i1, j3, k1);
                            if (!block.isReplaceable(world, i1, j3, k1) && !block.isLeaves(world, i1, j3, k1)) continue;
                            this.setBlockAndNotifyAdequately(world, i1, j3, k1, this.leafBlock, this.leafMeta);
                            if (!this.isMirky) continue;
                            if (random.nextInt(20) == 0 && world.isAirBlock(i1 - 1, j3, k1)) {
                                this.growVines(world, random, i1 - 1, j3, k1, 8);
                            }
                            if (random.nextInt(20) == 0 && world.isAirBlock(i1 + 1, j3, k1)) {
                                this.growVines(world, random, i1 + 1, j3, k1, 2);
                            }
                            if (random.nextInt(20) == 0 && world.isAirBlock(i1, j3, k1 - 1)) {
                                this.growVines(world, random, i1, j3, k1 - 1, 1);
                            }
                            if (random.nextInt(20) != 0 || !world.isAirBlock(i1, j3, k1 + 1)) continue;
                            this.growVines(world, random, i1, j3, k1 + 1, 4);
                        }
                    }
                }
            }
        }
        for (j1 = j; j1 <= j + 2; ++j1) {
            for (int i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
                for (int k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
                    Block block;
                    int i2 = Math.abs(i1 - i);
                    int k2 = Math.abs(k1 - k);
                    int j2 = j1 - j;
                    if ((((i2 != 0) || (k2 != 0)) && ((i2 != k2) || (i2 != j2))) && (i2 != 0 && k2 != 0 || i2 == k2 || i2 != j2 + 1 && k2 != j2 + 1) || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) continue;
                    this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                }
            }
        }
    }

    private void growVines(World world, Random random, int i, int j, int k, int meta) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mirkVines, meta);
        int length = 4 + random.nextInt(8);
        while (world.isAirBlock(i, --j, k) && length > 0) {
            this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mirkVines, meta);
            --length;
        }
    }
}

