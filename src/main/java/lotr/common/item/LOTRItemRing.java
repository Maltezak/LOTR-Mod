package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class LOTRItemRing extends Item {
    @SideOnly(value = Side.CLIENT)
    public static IIcon saxIcon;

    public LOTRItemRing() {
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        super.registerIcons(iconregister);
        saxIcon = iconregister.registerIcon("lotr:sax");
    }
}
