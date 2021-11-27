package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockBars extends LOTRBlockPane {
    public LOTRBlockBars() {
        super("", "", Material.iron, true);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeMetal);
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
