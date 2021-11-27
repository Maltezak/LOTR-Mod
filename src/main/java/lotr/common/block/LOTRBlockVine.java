package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockVine extends BlockVine {
    public LOTRBlockVine() {
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getBlockColor() {
        return 16777215;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderColor(int i) {
        return 16777215;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        return 16777215;
    }
}
