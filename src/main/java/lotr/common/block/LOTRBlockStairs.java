package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;

public class LOTRBlockStairs
extends BlockStairs {
    public LOTRBlockStairs(Block block, int meta) {
        super(block, meta);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.useNeighborBrightness = true;
    }
}

