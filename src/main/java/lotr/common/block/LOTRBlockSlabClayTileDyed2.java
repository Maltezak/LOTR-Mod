package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabClayTileDyed2 extends LOTRBlockSlabBase {
    public LOTRBlockSlabClayTileDyed2(boolean flag) {
        super(flag, Material.rock, 8);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.25f);
        this.setResistance(7.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        j &= 7;
        return LOTRMod.clayTileDyed.getIcon(i, j += 8);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
