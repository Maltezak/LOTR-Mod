package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabBone extends LOTRBlockSlabBase {
    public LOTRBlockSlabBone(boolean flag) {
        super(flag, Material.rock, 1);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return LOTRMod.boneBlock.getIcon(i, j &= 7);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
