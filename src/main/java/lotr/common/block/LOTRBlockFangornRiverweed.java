package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockFangornRiverweed extends BlockLilyPad {
    public LOTRBlockFangornRiverweed() {
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getBlockColor() {
        return 16777215;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderColor(int meta) {
        return 16777215;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        return 16777215;
    }
}
