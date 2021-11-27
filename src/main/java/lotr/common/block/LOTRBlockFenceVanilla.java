package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class LOTRBlockFenceVanilla extends LOTRBlockFence {
    public LOTRBlockFenceVanilla() {
        super(Blocks.planks);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
