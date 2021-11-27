package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabThatch extends LOTRBlockSlabBase {
    public LOTRBlockSlabThatch(boolean flag) {
        super(flag, Material.grass, 2);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGrass);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return LOTRMod.thatch.getIcon(i, j &= 7);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
