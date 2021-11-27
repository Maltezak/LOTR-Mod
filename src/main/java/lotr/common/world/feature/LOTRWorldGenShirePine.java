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

public class LOTRWorldGenShirePine
extends WorldGenAbstractTree {
    private Block woodBlock = LOTRMod.wood;
    private int woodMeta = 0;
    private Block leafBlock = LOTRMod.leaves;
    private int leafMeta = 0;
    private int minHeight = 10;
    private int maxHeight = 20;

    public LOTRWorldGenShirePine(boolean flag) {
        super(flag);
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        int leafHeight = 6 + random.nextInt(4);
        int minLeafHeight = j + height - leafHeight;
        int maxLeafWidth = 2 + random.nextInt(2);
        boolean flag = true;
        if (j >= 1 && j + height + 1 <= 256) {
            for (int j1 = j; j1 <= j + 1 + height && flag; ++j1) {
                int checkRange;
                checkRange = j1 < minLeafHeight ? 0 : maxLeafWidth;
                for (int i1 = i - checkRange; i1 <= i + checkRange && flag; ++i1) {
                    for (int k1 = k - checkRange; k1 <= k + checkRange && flag; ++k1) {
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
            Block below = world.getBlock(i, j - 1, k);
            if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
                below.onPlantGrow(world, i, j - 1, k, i, j, k);
                int leafWidth = random.nextInt(2);
                int leafWidthLimit = 1;
                int nextLeafWidth = 0;
                for (int j1 = j + height; j1 >= minLeafHeight; --j1) {
                    for (int i1 = i - leafWidth; i1 <= i + leafWidth; ++i1) {
                        for (int k1 = k - leafWidth; k1 <= k + leafWidth; ++k1) {
                            Block block;
                            int i2 = i1 - i;
                            int k2 = k1 - k;
                            if (leafWidth > 0 && Math.abs(i2) == leafWidth && Math.abs(k2) == leafWidth || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) continue;
                            this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                        }
                    }
                    if (leafWidth >= leafWidthLimit) {
                        leafWidth = nextLeafWidth;
                        nextLeafWidth = 1;
                        if (++leafWidthLimit <= maxLeafWidth) continue;
                        leafWidthLimit = maxLeafWidth;
                        continue;
                    }
                    ++leafWidth;
                }
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
            return false;
        }
        return false;
    }
}

