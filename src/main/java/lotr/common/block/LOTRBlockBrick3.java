package lotr.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick3 extends LOTRBlockBrickBase {
    public LOTRBlockBrick3() {
        this.setBrickNames("blueCarved", "redCarved", "highElven", "highElvenMossy", "highElvenCracked", "woodElven", "woodElvenMossy", "woodElvenCracked", "nearHaradCarved", "dolAmroth", "moredain", "nearHaradCracked", "dwarvenGlowing", "nearHaradRed", "nearHaradRedCracked", "nearHaradRedCarved");
    }

    @Override
    public int getLightValue(IBlockAccess world, int i, int j, int k) {
        if(world.getBlockMetadata(i, j, k) == 12) {
            return Blocks.glowstone.getLightValue();
        }
        return 0;
    }
}
