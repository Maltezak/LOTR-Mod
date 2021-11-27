package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockReedBars extends LOTRBlockPane {
    public LOTRBlockReedBars() {
        super("", "", Material.grass, true);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGrass);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcon = iconregister.registerIcon(this.getTextureName());
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon func_150097_e() {
        return this.blockIcon;
    }
}
