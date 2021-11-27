package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHayBales extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenHayBales(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int width = 1 + random.nextInt(3);
        int size = 4 + width * width * (2 + random.nextInt(3));
        block0: for(int l = 0; l < size; ++l) {
            int r = MathHelper.getRandomIntegerInRange(random, 0, width * width);
            int dist = (int) Math.round(Math.sqrt(r));
            float angle = 6.2831855f * random.nextFloat();
            int i1 = Math.round(MathHelper.cos(angle) * dist);
            int k1 = Math.round(MathHelper.sin(angle) * dist);
            for(int j1 = 12; j1 >= -12; --j1) {
                if(!this.isSurface(world, i1, j1 - 1, k1) && this.getBlock(world, i1, j1 - 1, k1) != Blocks.hay_block) continue;
                Block block = this.getBlock(world, i1, j1, k1);
                if(!this.isAir(world, i1, j1, k1) && !this.isReplaceable(world, i1, j1, k1) && block.getMaterial() != Material.plants) continue;
                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.hay_block, 0);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                continue block0;
            }
        }
        return true;
    }
}
