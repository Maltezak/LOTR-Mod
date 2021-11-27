package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenDragonblood extends WorldGenAbstractTree {
    private int minHeight;
    private int maxHeight;
    private int trunkWidth;
    private Block woodBlock = LOTRMod.wood9;
    private int woodMeta = 0;
    private Block leafBlock = LOTRMod.leaves9;
    private int leafMeta = 0;

    public LOTRWorldGenDragonblood(boolean flag, int i, int j, int k) {
        super(flag);
        this.minHeight = i;
        this.maxHeight = j;
        this.trunkWidth = k;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if(j >= 1 && j + height + 5 <= 256) {
            int k1;
            int i1;
            int j1;
            for(j1 = j; j1 <= j + height + 5; ++j1) {
                int range = this.trunkWidth + 1;
                if(j1 == j) {
                    range = this.trunkWidth;
                }
                if(j1 >= j + height + 2) {
                    range = this.trunkWidth + 2;
                }
                for(int i12 = i - range; i12 <= i + range && flag; ++i12) {
                    for(int k12 = k - range; k12 <= k + range && flag; ++k12) {
                        if(j1 >= 0 && j1 < 256) {
                            if(this.isReplaceable(world, i12, j1, k12)) continue;
                            flag = false;
                            continue;
                        }
                        flag = false;
                    }
                }
            }
            for(i1 = i - this.trunkWidth; i1 <= i + this.trunkWidth && flag; ++i1) {
                for(k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth && flag; ++k1) {
                    Block block = world.getBlock(i1, j - 1, k1);
                    boolean isSoil = block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
                    if(isSoil) continue;
                    flag = false;
                }
            }
            if(!flag) {
                return false;
            }
            for(i1 = i - this.trunkWidth; i1 <= i + this.trunkWidth; ++i1) {
                for(k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth; ++k1) {
                    world.getBlock(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
                }
            }
            for(j1 = 0; j1 < height; ++j1) {
                for(int i13 = i - this.trunkWidth; i13 <= i + this.trunkWidth; ++i13) {
                    for(int k13 = k - this.trunkWidth; k13 <= k + this.trunkWidth; ++k13) {
                        this.setBlockAndNotifyAdequately(world, i13, j + j1, k13, this.woodBlock, this.woodMeta);
                    }
                }
            }
            if(this.trunkWidth >= 1) {
                int deg = 0;
                while(deg < 360) {
                    float angle = (float) Math.toRadians(deg += (40 + random.nextInt(30)) / this.trunkWidth);
                    float cos = MathHelper.cos(angle);
                    float sin = MathHelper.sin(angle);
                    float angleY = random.nextFloat() * (float) Math.toRadians(40.0);
                    MathHelper.cos(angleY);
                    float sinY = MathHelper.sin(angleY);
                    int length = 3 + random.nextInt(6);
                    int i14 = i;
                    int k14 = k;
                    int j12 = j + height - 1 - random.nextInt(5);
                    for(int l = 0; l < (length *= 1 + random.nextInt(this.trunkWidth)); ++l) {
                        Block block;
                        if(Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
                            i14 = (int) (i14 + Math.signum(cos));
                        }
                        if(Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
                            k14 = (int) (k14 + Math.signum(sin));
                        }
                        if(Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
                            j12 = (int) (j12 + Math.signum(sinY));
                        }
                        if(!(block = world.getBlock(i14, j12, k14)).isReplaceable(world, i14, j12, k14) && !block.isWood(world, i14, j12, k14) && !block.isLeaves(world, i14, j12, k14)) break;
                        this.setBlockAndNotifyAdequately(world, i14, j12, k14, this.woodBlock, this.woodMeta | 0xC);
                    }
                    this.growLeafCanopy(world, random, i14, j12, k14);
                }
            }
            else {
                this.growLeafCanopy(world, random, i, j + height - 1, k);
            }
            for(i1 = i - 1 - this.trunkWidth; i1 <= i + 1 + this.trunkWidth; ++i1) {
                for(int k15 = k - 1 - this.trunkWidth; k15 <= k + 1 + this.trunkWidth; ++k15) {
                    int i2 = i1 - i;
                    int k2 = k15 - k;
                    if(Math.abs(i2) == Math.abs(k2)) continue;
                    int rootX = i1;
                    int rootY = j + random.nextInt(2 + this.trunkWidth);
                    int rootZ = k15;
                    int roots = 0;
                    while(world.getBlock(rootX, rootY, k15).isReplaceable(world, rootX, rootY, rootZ)) {
                        this.setBlockAndNotifyAdequately(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 0xC);
                        world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                        --rootY;
                        if(++roots <= 4 + random.nextInt(3)) continue;
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
        int leafTop = j + 4;
        int maxRange = 3;
        for(j1 = leafStart; j1 <= leafTop; ++j1) {
            int j2 = j1 - (leafTop + 1);
            int leafRange = maxRange - j2;
            int leafRangeSq = leafRange * leafRange;
            for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
                for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
                    int k2;
                    boolean grow;
                    int i2 = Math.abs(i1 - i);
                    int dist = i2 * i2 + (k2 = Math.abs(k1 - k)) * k2;
                    grow = dist < leafRangeSq;
                    if(i2 == leafRange - 1 || k2 == leafRange - 1) {
                        grow &= random.nextInt(4) > 0;
                    }
                    if(!grow) continue;
                    int below = 0;
                    for(int j3 = j1; j3 >= j1 - below; --j3) {
                        Block block = world.getBlock(i1, j3, k1);
                        if(!block.isReplaceable(world, i1, j3, k1) && !block.isLeaves(world, i1, j3, k1)) continue;
                        this.setBlockAndNotifyAdequately(world, i1, j3, k1, this.leafBlock, this.leafMeta);
                    }
                }
            }
        }
        for(j1 = j; j1 <= j + 2; ++j1) {
            for(int i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
                for(int k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
                    Block block;
                    int i2 = Math.abs(i1 - i);
                    int k2 = Math.abs(k1 - k);
                    int j2 = j1 - j;
                    if((((i2 != 0) || (k2 != 0)) && ((i2 != k2) || (i2 != j2))) && (i2 != 0 && k2 != 0 || i2 == k2 || i2 != j2 + 1 && k2 != j2 + 1) || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) continue;
                    this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.woodBlock, this.woodMeta | 0xC);
                }
            }
        }
    }
}
