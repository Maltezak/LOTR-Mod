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

public class LOTRWorldGenAlmond
extends WorldGenAbstractTree {
    private int minHeight = 4;
    private int maxHeight = 5;
    private Block woodBlock = LOTRMod.wood7;
    private int woodMeta = 3;
    private Block leafBlock = LOTRMod.leaves7;
    private int leafMeta = 3;

    public LOTRWorldGenAlmond(boolean flag) {
        super(flag);
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        int leafStart = j + height - 3;
        int leafTop = j + height;
        boolean flag = true;
        if (j >= 1 && j + height + 1 <= 256) {
            for (int j1 = j; j1 <= j + height + 1; ++j1) {
                int range = 1;
                if (j1 == j) {
                    range = 0;
                }
                if (j1 >= leafStart) {
                    range = 2;
                }
                for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
                    for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
                        if (j1 >= 0 && j1 < 256) {
                            if (this.isReplaceable(world, i1, j1, k1)) continue;
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
            Block below = world.getBlock(i, j - 1, k);
            if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
                canGrow = false;
            }
            if (canGrow) {
                int j1;
                below = world.getBlock(i, j - 1, k);
                below.onPlantGrow(world, i, j - 1, k, i, j, k);
                for (j1 = leafStart; j1 <= leafTop; ++j1) {
                    int leafRange;
                    int maxRange = 2;
                    int j2 = leafTop - j1;
                    leafRange = j2 == 0 ? 1 : (j2 == 1 ? 2 : (j2 == 2 ? 3 : 1));
                    for (int i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
                        for (int k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
                            Block block;
                            int i2 = Math.abs(i1 - i);
                            if (i2 + (Math.abs(k1 - k)) > leafRange || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) continue;
                            this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                        }
                    }
                }
                for (j1 = j; j1 < j + height; ++j1) {
                    Block block = world.getBlock(i, j1, k);
                    if (!block.isReplaceable(world, i, j1, k) && !block.isLeaves(world, i, j1, k)) continue;
                    this.setBlockAndNotifyAdequately(world, i, j1, k, this.woodBlock, this.woodMeta);
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

