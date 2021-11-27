package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemAncientItemParts extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] icons;
    private String[] partNames = new String[] {"swordTip", "swordBlade", "swordHilt", "armorPlate"};

    public LOTRItemAncientItemParts() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        if(i >= this.icons.length) {
            i = 0;
        }
        return this.icons[i];
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.icons = new IIcon[this.partNames.length];
        for(int i = 0; i < this.partNames.length; ++i) {
            this.icons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.partNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i <= 3; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
