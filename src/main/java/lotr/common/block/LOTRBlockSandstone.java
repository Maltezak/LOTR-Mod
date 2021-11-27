package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSandstone extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconBottom;

    public LOTRBlockSandstone() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setStepSound(Block.soundTypeStone);
        this.setHardness(0.8f);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconBottom = iconregister.registerIcon(this.getTextureName() + "_bottom");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 0) {
            return this.iconBottom;
        }
        if(i == 1) {
            return this.iconTop;
        }
        return this.blockIcon;
    }
}
