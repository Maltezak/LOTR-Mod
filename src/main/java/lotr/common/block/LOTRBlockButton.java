package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;

public class LOTRBlockButton extends BlockButton {
    private String iconPath;

    public LOTRBlockButton(boolean flag, String s) {
        super(flag);
        this.iconPath = s;
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcon = iconregister.registerIcon(this.iconPath);
    }
}
