package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.WeightedRandom;

public class LOTRItemModifierTemplate extends Item {
    public LOTRItemModifierTemplate() {
        this.setMaxStackSize(1);
        this.setCreativeTab(LOTRCreativeTabs.tabMaterials);
    }

    public static LOTREnchantment getModifier(ItemStack itemstack) {
        NBTTagCompound nbt = itemstack.getTagCompound();
        if(nbt != null) {
            String s = nbt.getString("ScrollModifier");
            return LOTREnchantment.getEnchantmentByName(s);
        }
        return null;
    }

    public static void setModifier(ItemStack itemstack, LOTREnchantment ench) {
        String s = ench.enchantName;
        itemstack.setTagInfo("ScrollModifier", new NBTTagString(s));
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        String s = super.getItemStackDisplayName(itemstack);
        LOTREnchantment mod = LOTRItemModifierTemplate.getModifier(itemstack);
        if(mod != null) {
            s = String.format(s, mod.getDisplayName());
        }
        return s;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        super.addInformation(itemstack, entityplayer, list, flag);
        LOTREnchantment mod = LOTRItemModifierTemplate.getModifier(itemstack);
        if(mod != null) {
            String desc = mod.getNamedFormattedDescription(itemstack);
            list.add(desc);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if(!ench.hasTemplateItem()) continue;
            ItemStack itemstack = new ItemStack(this);
            LOTRItemModifierTemplate.setModifier(itemstack, ench);
            list.add(itemstack);
        }
    }

    public static ItemStack getRandomCommonTemplate(Random random) {
        ArrayList<LOTREnchantmentHelper.WeightedRandomEnchant> applicable = new ArrayList<>();
        for(LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if(!ench.hasTemplateItem()) continue;
            int weight = LOTREnchantmentHelper.getSkilfulWeight(ench);
            LOTREnchantmentHelper.WeightedRandomEnchant wre = new LOTREnchantmentHelper.WeightedRandomEnchant(ench, weight);
            applicable.add(wre);
        }
        LOTREnchantmentHelper.WeightedRandomEnchant chosenWre = (LOTREnchantmentHelper.WeightedRandomEnchant) WeightedRandom.getRandomItem(random, applicable);
        LOTREnchantment chosenEnch = chosenWre.theEnchant;
        ItemStack itemstack = new ItemStack(LOTRMod.modTemplate);
        LOTRItemModifierTemplate.setModifier(itemstack, chosenEnch);
        return itemstack;
    }
}
