package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemKaftan extends LOTRItemHaradRobes {
    @SideOnly(value = Side.CLIENT)
    private IIcon overlayIcon;

    public LOTRItemKaftan(int slot) {
        super(LOTRMaterial.KAFTAN, slot);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        super.registerIcons(iconregister);
        this.overlayIcon = iconregister.registerIcon(this.getIconString() + "_overlay");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack itemstack, int pass) {
        if(pass >= 1) {
            return this.overlayIcon;
        }
        return super.getIcon(itemstack, pass);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemstack, int pass) {
        if(pass >= 1) {
            return 16777215;
        }
        return super.getColorFromItemStack(itemstack, pass);
    }

    @Override
    public int getColor(ItemStack itemstack) {
        return LOTRItemHaradRobes.getRobesColor(itemstack);
    }
}
