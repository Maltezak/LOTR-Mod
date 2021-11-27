package lotr.common.block;

import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;

public class LOTRBlockMordorPlant extends LOTRBlockFlower {
    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
    }
}
