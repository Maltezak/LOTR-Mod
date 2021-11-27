package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenPalm extends WorldGenAbstractTree {
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private boolean hasDates = false;
    private int minHeight = 5;
    private int maxHeight = 8;

    public LOTRWorldGenPalm(boolean flag, Block b, int m, Block b1, int m1) {
        super(flag);
        this.woodBlock = b;
        this.woodMeta = m;
        this.leafBlock = b1;
        this.leafMeta = m1;
    }

    public LOTRWorldGenPalm setMinMaxHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }

    public LOTRWorldGenPalm setDates() {
        this.hasDates = true;
        return this;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        if(j < 1 || j + height + 2 > 256) {
            return false;
        }
        if(!this.isReplaceable(world, i, j, k)) {
            return false;
        }
        Block below = world.getBlock(i, j - 1, k);
        if(!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) (Blocks.sapling))) {
            return false;
        }
        for(int l = 1; l < height + 2; ++l) {
            for(int i1 = i - 1; i1 <= i + 1; ++i1) {
                for(int k1 = k - 1; k1 <= k + 1; ++k1) {
                    if(this.isReplaceable(world, i1, j + l, k1)) continue;
                    return false;
                }
            }
        }
        float trunkAngle = 6.2831855f * random.nextFloat();
        float trunkSin = MathHelper.sin(trunkAngle);
        float trunkCos = MathHelper.cos(trunkAngle);
        int trunkX = i;
        int trunkZ = k;
        int trunkSwitches = 0;
        int trunkSwitchesMax = MathHelper.getRandomIntegerInRange(random, 0, 3);
        for(int l = 0; l < height; ++l) {
            this.setBlockAndNotifyAdequately(world, trunkX, j + l, trunkZ, this.woodBlock, this.woodMeta);
            if(this.hasDates && l == height - 3) {
                for(int d = 0; d < 4; ++d) {
                    ForgeDirection dir = ForgeDirection.getOrientation(d + 2);
                    this.setBlockAndNotifyAdequately(world, trunkX + dir.getOpposite().offsetX, j + l, trunkZ + dir.getOpposite().offsetZ, LOTRMod.dateBlock, d);
                }
            }
            if(l <= height / 3 || l >= height - 1 || trunkSwitches >= trunkSwitchesMax || !random.nextBoolean()) continue;
            ++trunkSwitches;
            if(Math.abs(trunkCos) >= MathHelper.getRandomDoubleInRange(random, 0.25, 0.5)) {
                trunkX = (int) (trunkX + Math.signum(trunkCos));
            }
            if((Math.abs(trunkSin) < MathHelper.getRandomDoubleInRange(random, 0.25, 0.5))) continue;
            trunkZ = (int) (trunkZ + Math.signum(trunkSin));
        }
        int leafAngle = 0;
        block5: while(leafAngle < 360) {
            float angleR = (float) Math.toRadians(leafAngle += 15 + random.nextInt(15));
            float sin = MathHelper.sin(angleR);
            float cos = MathHelper.cos(angleR);
            float angleY = random.nextFloat() * (float) Math.toRadians(30.0);
            MathHelper.cos(angleY);
            float sinY = MathHelper.sin(angleY);
            int i1 = trunkX;
            int j1 = j + height - 1;
            int k1 = trunkZ;
            int branchLength = 5;
            for(int l = 1; l <= branchLength; ++l) {
                if(Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
                    j1 = (int) (j1 + Math.signum(sinY));
                }
                else {
                    boolean cosOrSin;
                    double dCos = Math.floor(Math.abs(cos * l)) - Math.floor(Math.abs(cos * (l - 1)));
                    double dSin = Math.floor(Math.abs(sin * l)) - Math.floor(Math.abs(sin * (l - 1)));
                    cosOrSin = (dCos = Math.abs(dCos)) == (dSin = Math.abs(dSin)) ? random.nextBoolean() : (cosOrSin = dCos > dSin);
                    if(cosOrSin) {
                        i1 = (int) (i1 + Math.signum(cos));
                    }
                    else {
                        k1 = (int) (k1 + Math.signum(sin));
                    }
                }
                boolean wood = l == 1;
                Block block = world.getBlock(i1, j1, k1);
                boolean replacingWood = block.isWood(world, i1, j1, k1);
                if(!block.isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1) && !replacingWood) continue block5;
                if(wood) {
                    this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                }
                else if(!replacingWood) {
                    this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                }
                if(l >= 5) continue block5;
            }
        }
        world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
        return true;
    }
}
