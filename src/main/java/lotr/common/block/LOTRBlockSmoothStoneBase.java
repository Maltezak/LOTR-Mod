package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockSmoothStoneBase extends LOTRBlockBrickBase {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] topIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] sideIcons;

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.brickNames.length) {
            j = 0;
        }
        if(i == 0 || i == 1) {
            return this.topIcons[j];
        }
        return this.sideIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.topIcons = new IIcon[this.brickNames.length];
        this.sideIcons = new IIcon[this.brickNames.length];
        for(int i = 0; i < this.brickNames.length; ++i) {
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i] + "_top");
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i] + "_side");
        }
    }
}
