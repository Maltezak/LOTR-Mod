package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenPine
extends WorldGenAbstractTree {
    private Block woodBlock = LOTRMod.wood5;
    private int woodMeta = 0;
    private Block leafBlock = LOTRMod.leaves5;
    private int leafMeta = 0;
    private int minHeight = 12;
    private int maxHeight = 24;

    public LOTRWorldGenPine(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenPine setMinMaxHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        Block below;
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (j >= 1 && height + 1 <= 256) {
            for (int j1 = j; j1 <= j + height + 1; ++j1) {
                int range = 1;
                if (j1 == j) {
                    range = 0;
                }
                if (j1 >= j + height - 1) {
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
        } else {
            flag = false;
        }
        if (!((below = world.getBlock(i, j - 1, k)).canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling))) {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        this.setBlockAndNotifyAdequately(world, i, j + height, k, this.leafBlock, this.leafMeta);
        this.generateLeafLayer(world, random, i, j + height - 1, k, 1);
        int leafHeight = j + height - 3;
        int minLeafHeight = j + (int)(height * 0.5f);
        while (leafHeight > minLeafHeight) {
            int r = random.nextInt(3);
            if (r == 0) {
                this.generateLeafLayer(world, random, i, leafHeight, k, 1);
                leafHeight -= 2;
                continue;
            }
            if (r == 1) {
                this.generateLeafLayer(world, random, i, --leafHeight + 1, k, 1);
                this.generateLeafLayer(world, random, i, leafHeight, k, 2);
                this.generateLeafLayer(world, random, i, leafHeight - 1, k, 1);
                leafHeight -= 3;
                continue;
            }
            if (r != 2) continue;
            this.generateLeafLayer(world, random, i, --leafHeight + 1, k, 2);
            this.generateLeafLayer(world, random, i, leafHeight, k, 3);
            this.generateLeafLayer(world, random, i, leafHeight - 1, k, 2);
            leafHeight -= 3;
        }
        this.generateLeafLayer(world, random, i, leafHeight, k, 1);
        int lastDir = -1;
        for (int j1 = j; j1 < j + height; ++j1) {
            int i1;
            int k1;
            int dir;
            this.setBlockAndNotifyAdequately(world, i, j1, k, this.woodBlock, this.woodMeta);
            if (j1 < j + 3 || j1 >= minLeafHeight || random.nextInt(3) != 0 || (dir = random.nextInt(4)) == lastDir) continue;
            lastDir = dir;
            int length = 1;
            for (int l = 1; l <= length && this.isReplaceable(world, i1 = i + Direction.offsetX[dir] * l, j1, k1 = k + Direction.offsetZ[dir] * l); ++l) {
                if (dir == 0 || dir == 2) {
                    this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.woodBlock, this.woodMeta | 8);
                    continue;
                }
                this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.woodBlock, this.woodMeta | 4);
            }
        }
        return true;
    }

    private void generateLeafLayer(World world, Random random, int i, int j, int k, int range) {
        for (int i1 = i - range; i1 <= i + range; ++i1) {
            for (int k1 = k - range; k1 <= k + range; ++k1) {
                Block block;
                int i2 = Math.abs(i1 - i);
                if (i2 + (Math.abs(k1 - k)) > range || !(block = world.getBlock(i1, j, k1)).isReplaceable(world, i1, j, k1) && !block.isLeaves(world, i1, j, k1)) continue;
                this.setBlockAndNotifyAdequately(world, i1, j, k1, this.leafBlock, this.leafMeta);
            }
        }
    }
}

