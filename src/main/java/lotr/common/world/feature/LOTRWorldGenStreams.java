package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenStreams extends WorldGenerator {
    private Block liquidBlock;

    public LOTRWorldGenStreams(Block block) {
        this.liquidBlock = block;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        if(!this.isRock(world, i, j + 1, k)) {
            return false;
        }
        if(!this.isRock(world, i, j - 1, k)) {
            return false;
        }
        if(!world.isAirBlock(i, j, k) && !this.isRock(world, i, j, k)) {
            return false;
        }
        int sides = 0;
        if(this.isRock(world, i - 1, j, k)) {
            ++sides;
        }
        if(this.isRock(world, i + 1, j, k)) {
            ++sides;
        }
        if(this.isRock(world, i, j, k - 1)) {
            ++sides;
        }
        if(this.isRock(world, i, j, k + 1)) {
            ++sides;
        }
        int openAir = 0;
        if(world.isAirBlock(i - 1, j, k)) {
            ++openAir;
        }
        if(world.isAirBlock(i + 1, j, k)) {
            ++openAir;
        }
        if(world.isAirBlock(i, j, k - 1)) {
            ++openAir;
        }
        if(world.isAirBlock(i, j, k + 1)) {
            ++openAir;
        }
        if(sides == 3 && openAir == 1) {
            world.setBlock(i, j, k, this.liquidBlock, 0, 2);
            world.scheduledUpdatesAreImmediate = true;
            this.liquidBlock.updateTick(world, i, j, k, random);
            world.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }

    private boolean isRock(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        return block == Blocks.stone || block == Blocks.sandstone || block == LOTRMod.rock;
    }
}
