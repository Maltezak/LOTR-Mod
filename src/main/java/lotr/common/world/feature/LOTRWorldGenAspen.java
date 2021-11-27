package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenAspen
extends WorldGenAbstractTree {
    private Block woodBlock = LOTRMod.wood7;
    private int woodMeta = 0;
    private Block leafBlock = LOTRMod.leaves7;
    private int leafMeta = 0;
    private int minHeight = 8;
    private int maxHeight = 15;
    private int extraTrunk = 0;

    public LOTRWorldGenAspen(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenAspen setMinMaxHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }

    public LOTRWorldGenAspen setBlocks(Block b1, int m1, Block b2, int m2) {
        this.woodBlock = b1;
        this.woodMeta = m1;
        this.leafBlock = b2;
        this.leafMeta = m2;
        return this;
    }

    public LOTRWorldGenAspen setExtraTrunkWidth(int w) {
        this.extraTrunk = w;
        return this;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int j1;
        int i1;
        int k1;
        int i12;
        Block below;
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        int leafMin = 3 + random.nextInt(3);
        leafMin = j + leafMin - 1;
        int leafTop = j + height + 1;
        boolean flag = true;
        if (j >= 1 && height + 1 <= 256) {
            for (int j12 = j; j12 <= j + height + 1; ++j12) {
                int range = 1;
                if (j12 == j) {
                    range = 0;
                }
                if (j12 >= leafMin) {
                    range = 2;
                }
                for (i1 = i - range; i1 <= i + this.extraTrunk + range && flag; ++i1) {
                    for (int k12 = k - range; k12 <= k + this.extraTrunk + range && flag; ++k12) {
                        if (j12 >= 0 && j12 < 256) {
                            if (this.isReplaceable(world, i1, j12, k12)) continue;
                            flag = false;
                            continue;
                        }
                        flag = false;
                    }
                }
            }
        } else {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        boolean canGrow = true;
        for (i12 = i; i12 <= i + this.extraTrunk && canGrow; ++i12) {
            for (k1 = k; k1 <= k + this.extraTrunk && canGrow; ++k1) {
                below = world.getBlock(i12, j - 1, k1);
                if (below.canSustainPlant((IBlockAccess)world, i12, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.sapling)) continue;
                canGrow = false;
            }
        }
        if (!canGrow) {
            return false;
        }
        for (i12 = i; i12 <= i + this.extraTrunk; ++i12) {
            for (k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                below = world.getBlock(i12, j - 1, k1);
                below.onPlantGrow(world, i12, j - 1, k1, i12, j, k1);
            }
        }
        for (j1 = leafMin; j1 <= leafTop; ++j1) {
            int leafWidth = 2;
            if (j1 >= leafTop - 1) {
                leafWidth = 0;
            } else if (j1 >= leafTop - 3 || j1 <= leafMin + 1 || random.nextInt(4) == 0) {
                leafWidth = 1;
            }
            int branches = 4 + random.nextInt(5);
            for (int b = 0; b < branches; ++b) {
                Block block;
                int i13 = i;
                int k13 = k;
                if (this.extraTrunk > 0) {
                    i13 += random.nextInt(this.extraTrunk + 1);
                    k13 += random.nextInt(this.extraTrunk + 1);
                }
                int i2 = i13;
                int k2 = k13;
                int length = 4 + random.nextInt(8);
                for (int l = 0; l < (length *= this.extraTrunk + 1) && Math.abs(i2 - i13) <= leafWidth && Math.abs(k2 - k13) <= leafWidth && ((block = world.getBlock(i2, j1, k2)).isReplaceable(world, i2, j1, k2) || block.isLeaves(world, i2, j1, k2)); ++l) {
                    this.setBlockAndNotifyAdequately(world, i2, j1, k2, this.leafBlock, this.leafMeta);
                    int dir = random.nextInt(4);
                    if (dir == 0) {
                        --i2;
                        continue;
                    }
                    if (dir == 1) {
                        ++i2;
                        continue;
                    }
                    if (dir == 2) {
                        --k2;
                        continue;
                    }
                    if (dir != 3) continue;
                    ++k2;
                }
            }
        }
        for (j1 = j; j1 < j + height; ++j1) {
            for (i1 = i; i1 <= i + this.extraTrunk; ++i1) {
                for (int k14 = k; k14 <= k + this.extraTrunk; ++k14) {
                    this.setBlockAndNotifyAdequately(world, i1, j1, k14, this.woodBlock, this.woodMeta);
                }
            }
        }
        return true;
    }
}

