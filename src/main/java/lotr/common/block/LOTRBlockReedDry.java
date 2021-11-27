package lotr.common.block;

import net.minecraft.world.World;

public class LOTRBlockReedDry extends LOTRBlockReed {
    @Override
    protected boolean canReedGrow(World world, int i, int j, int k) {
        return false;
    }
}
