package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWaterPlant extends WorldGenerator {
    private Block plant;

    public LOTRWorldGenWaterPlant(Block block) {
        this.plant = block;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        for(int l = 0; l < 32; ++l) {
            int j1;
            int k1;
            int i1 = i + random.nextInt(4) - random.nextInt(4);
            if(!world.isAirBlock(i1, j1 = j, k1 = k + random.nextInt(4) - random.nextInt(4)) || !this.plant.canPlaceBlockAt(world, i1, j1, k1)) continue;
            world.setBlock(i1, j1, k1, this.plant, 0, 2);
        }
        return true;
    }
}
