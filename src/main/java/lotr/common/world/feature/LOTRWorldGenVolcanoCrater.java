package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenVolcanoCrater
extends WorldGenerator {
    private int minWidth = 5;
    private int maxWidth = 15;
    private int heightCheck = 8;

    public boolean generate(World world, Random random, int i, int j, int k) {
        world.getBiomeGenForCoords(i, k);
        if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k) && world.getBlock(i, j - 1, k) != Blocks.stone) {
            return false;
        }
        int craterWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth);
        int highestHeight = j;
        int lowestHeight = j;
        for (int i1 = i - craterWidth; i1 <= i + craterWidth; ++i1) {
            for (int k1 = k - craterWidth; k1 <= k + craterWidth; ++k1) {
                int heightValue = world.getHeightValue(i1, k1);
                int j1 = heightValue - 1;
                if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i1, j1, k1) && world.getBlock(i1, j1, k1) != Blocks.stone) {
                    return false;
                }
                if (heightValue > highestHeight) {
                    highestHeight = heightValue;
                }
                if (heightValue >= lowestHeight) continue;
                lowestHeight = heightValue;
            }
        }
        if (highestHeight - lowestHeight > this.heightCheck) {
            return false;
        }
        int spheres = 1;
        for (int l = 0; l < spheres; ++l) {
            int posX = i;
            int posZ = k;
            int posY = world.getTopSolidOrLiquidBlock(posX, posZ);
            int sphereWidth = MathHelper.getRandomIntegerInRange(random, this.minWidth, this.maxWidth);
            for (int i1 = posX - sphereWidth; i1 <= posX + sphereWidth; ++i1) {
                for (int k1 = posZ - sphereWidth; k1 <= posZ + sphereWidth; ++k1) {
                    int j1;
                    int i2 = i1 - posX;
                    int k2 = k1 - posZ;
                    int xzDistSq = i2 * i2 + k2 * k2;
                    if (xzDistSq >= sphereWidth * sphereWidth && (xzDistSq >= (sphereWidth + 1) * (sphereWidth + 1) || random.nextInt(3) != 0)) continue;
                    for (int j2 = world.getTopSolidOrLiquidBlock(i1, k1); j2 > posY; --j2) {
                        this.setBlockAndNotifyAdequately(world, i1, j2, k1, Blocks.air, 0);
                    }
                    int depthHere = (int)((sphereWidth - Math.sqrt(xzDistSq)) * 0.7) + random.nextInt(2);
                    for (j1 = posY - depthHere - 1; j1 >= posY - (depthHere + this.heightCheck + 2 + random.nextInt(2)) && !world.getBlock(i1, j1, k1).isOpaqueCube(); --j1) {
                        this.setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.stone, 0);
                    }
                    for (j1 = posY; j1 >= posY - depthHere; --j1) {
                        int jDepth = posY - j1;
                        if (jDepth > 6) {
                            this.setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.lava, 0);
                            if (!world.getBlock(i1, j1 - 1, k1).isOpaqueCube()) {
                                this.setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, Blocks.obsidian, 0);
                            }
                        } else {
                            this.setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
                        }
                        if (jDepth > 4) {
                            this.setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, Blocks.obsidian, 0);
                            continue;
                        }
                        if (jDepth <= 2) continue;
                        if (random.nextInt(4) == 0) {
                            this.setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, Blocks.gravel, 0);
                            this.setBlockAndNotifyAdequately(world, i1, j1 - 2, k1, Blocks.stone, 0);
                            continue;
                        }
                        this.setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, LOTRMod.obsidianGravel, 0);
                        this.setBlockAndNotifyAdequately(world, i1, j1 - 2, k1, Blocks.obsidian, 0);
                    }
                }
            }
        }
        return true;
    }
}

