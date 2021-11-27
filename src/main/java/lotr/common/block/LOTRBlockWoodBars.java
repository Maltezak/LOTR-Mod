package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockWoodBars extends LOTRBlockPane {
    public LOTRBlockWoodBars() {
        super("", "", Material.wood, true);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
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
