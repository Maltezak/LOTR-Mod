package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabClayTileDyed extends LOTRBlockSlabBase {
    public LOTRBlockSlabClayTileDyed(boolean flag) {
        super(flag, Material.rock, 8);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.25f);
        this.setResistance(7.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return LOTRMod.clayTileDyed.getIcon(i, j &= 7);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
