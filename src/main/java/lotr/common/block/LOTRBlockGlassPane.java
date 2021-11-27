package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGlassPane extends LOTRBlockPane {
    public LOTRBlockGlassPane() {
        super("lotr:glass", "lotr:glass_pane_top", Material.glass, false);
        this.setHardness(0.3f);
        this.setStepSound(Block.soundTypeGlass);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
    }

    @Override
    public boolean canPaneConnectTo(IBlockAccess world, int i, int j, int k, ForgeDirection dir) {
        return super.canPaneConnectTo(world, i, j, k, dir) || world.getBlock(i, j, k) instanceof LOTRBlockGlass;
    }
}
