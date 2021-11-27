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

public class LOTRWorldGenLairelosse
extends WorldGenAbstractTree {
    private int minHeight = 5;
    private int maxHeight = 8;
    private int extraTrunk = 0;
    private Block woodBlock = LOTRMod.wood7;
    private int woodMeta = 2;
    private Block leafBlock = LOTRMod.leaves7;
    private int leafMeta = 2;

    public LOTRWorldGenLairelosse(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenLairelosse setMinMaxHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }

    public LOTRWorldGenLairelosse setExtraTrunkWidth(int i) {
        this.extraTrunk = i;
        return this;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        int leafStart = j + 1 + this.extraTrunk + random.nextInt(3);
        int leafTop = j + height + 1;
        boolean flag = true;
        if (j >= 1 && j + height + 1 <= 256) {
            int k1;
            int i1;
            Block below;
            for (int j1 = j; j1 <= j + height + 1; ++j1) {
                int range = 1;
                if (j1 == j) {
                    range = 0;
                }
                if (j1 >= leafStart) {
                    range = 2;
                }
                for (int i12 = i - range; i12 <= i + this.extraTrunk + range && flag; ++i12) {
                    for (int k12 = k - range; k12 <= k + this.extraTrunk + range && flag; ++k12) {
                        if (j1 >= 0 && j1 < 256) {
                            if (this.isReplaceable(world, i12, j1, k12)) continue;
                            flag = false;
                            continue;
                        }
                        flag = false;
                    }
                }
            }
            if (!flag) {
                return false;
            }
            boolean canGrow = true;
            for (i1 = i; i1 <= i + this.extraTrunk && canGrow; ++i1) {
                for (k1 = k; k1 <= k + this.extraTrunk && canGrow; ++k1) {
                    below = world.getBlock(i1, j - 1, k1);
                    if (below.canSustainPlant((IBlockAccess)world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.sapling)) continue;
                    canGrow = false;
                }
            }
            if (canGrow) {
                int k13;
                int j1;
                int i13;
                for (i1 = i; i1 <= i + this.extraTrunk; ++i1) {
                    for (k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                        below = world.getBlock(i1, j - 1, k1);
                        below.onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
                    }
                }
                int leafRange = 0;
                int maxRange = 2;
                for (j1 = leafTop; j1 >= leafStart; --j1) {
                    if (j1 >= leafTop - 1) {
                        leafRange = 0;
                    } else if (++leafRange > 2) {
                        leafRange = 1;
                    }
                    for (i13 = i - maxRange; i13 <= i + this.extraTrunk + maxRange; ++i13) {
                        for (k13 = k - maxRange; k13 <= k + this.extraTrunk + maxRange; ++k13) {
                            Block block;
                            int i2 = Math.abs(i13 - i);
                            int k2 = Math.abs(k13 - k);
                            if (i13 > i) {
                                i2 -= this.extraTrunk;
                            }
                            if (k13 > k) {
                                k2 -= this.extraTrunk;
                            }
                            if (i2 + k2 > leafRange || !(block = world.getBlock(i13, j1, k13)).isReplaceable(world, i13, j1, k13) && !block.isLeaves(world, i13, j1, k13)) continue;
                            this.setBlockAndNotifyAdequately(world, i13, j1, k13, this.leafBlock, this.leafMeta);
                        }
                    }
                }
                for (j1 = j; j1 < j + height; ++j1) {
                    for (i13 = i; i13 <= i + this.extraTrunk; ++i13) {
                        for (k13 = k; k13 <= k + this.extraTrunk; ++k13) {
                            this.setBlockAndNotifyAdequately(world, i13, j1, k13, this.woodBlock, this.woodMeta);
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

