package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenDeadTrees extends WorldGenAbstractTree {
    private Block woodBlock;
    private int woodMeta;

    public LOTRWorldGenDeadTrees(Block block, int i) {
        super(false);
        this.woodBlock = block;
        this.woodMeta = i;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        Block below = world.getBlock(i, j - 1, k);
        if(!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) (Blocks.sapling)) && below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        int height = 3 + random.nextInt(4);
        for(int j1 = j; j1 < j + height; ++j1) {
            this.setBlockAndNotifyAdequately(world, i, j1, k, this.woodBlock, this.woodMeta);
        }
        for(int branch = 0; branch < 4; ++branch) {
            int branchLength = 3 + random.nextInt(5);
            int branchHorizontalPos = 0;
            int branchVerticalPos = j + height - 1 - random.nextInt(2);
            block8: for(int l = 0; l < branchLength; ++l) {
                if(random.nextInt(4) == 0) {
                    ++branchHorizontalPos;
                }
                if(random.nextInt(3) != 0) {
                    ++branchVerticalPos;
                }
                switch(branch) {
                    case 0: {
                        this.setBlockAndNotifyAdequately(world, i - branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 0xC);
                        continue block8;
                    }
                    case 1: {
                        this.setBlockAndNotifyAdequately(world, i, branchVerticalPos, k + branchHorizontalPos, this.woodBlock, this.woodMeta | 0xC);
                        continue block8;
                    }
                    case 2: {
                        this.setBlockAndNotifyAdequately(world, i + branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 0xC);
                        continue block8;
                    }
                    case 3: {
                        this.setBlockAndNotifyAdequately(world, i, branchVerticalPos, k - branchHorizontalPos, this.woodBlock, this.woodMeta | 0xC);
                    }
                }
            }
        }
        return true;
    }
}
