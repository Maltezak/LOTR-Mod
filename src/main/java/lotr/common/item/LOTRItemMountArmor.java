package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemMountArmor extends Item {
    private ItemArmor.ArmorMaterial armorMaterial;
    private Mount mountType;
    private int damageReduceAmount;
    private Item templateItem;

    public LOTRItemMountArmor(LOTRMaterial material, Mount mount) {
        this(material.toArmorMaterial(), mount);
    }

    public LOTRItemMountArmor(ItemArmor.ArmorMaterial material, Mount mount) {
        this.armorMaterial = material;
        this.damageReduceAmount = material.getDamageReductionAmount(1) + material.getDamageReductionAmount(2);
        this.mountType = mount;
        this.setMaxStackSize(1);
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
    }

    public LOTRItemMountArmor setTemplateItem(Item item) {
        this.templateItem = item;
        return this;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        if(this.templateItem != null) {
            return this.templateItem.getItemStackDisplayName(this.createTemplateItemStack(itemstack));
        }
        return super.getItemStackDisplayName(itemstack);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconIndex(ItemStack itemstack) {
        if(this.templateItem != null) {
            return this.templateItem.getIconIndex(this.createTemplateItemStack(itemstack));
        }
        return super.getIconIndex(itemstack);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        if(this.templateItem != null) {
            return this.templateItem.getIconFromDamage(i);
        }
        return super.getIconFromDamage(i);
    }

    private ItemStack createTemplateItemStack(ItemStack source) {
        ItemStack template = new ItemStack(this.templateItem);
        template.stackSize = source.stackSize;
        template.setItemDamage(source.getItemDamage());
        if(source.getTagCompound() != null) {
            template.setTagCompound(source.getTagCompound());
        }
        return template;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        if(this.templateItem == null) {
            super.registerIcons(iconregister);
        }
    }

    public boolean isValid(LOTRNPCMount mount) {
        if(mount instanceof LOTREntityElk) {
            return this.mountType == Mount.ELK;
        }
        if(mount instanceof LOTREntityWildBoar) {
            return this.mountType == Mount.BOAR;
        }
        if(mount instanceof LOTREntityCamel) {
            return this.mountType == Mount.CAMEL;
        }
        if(mount instanceof LOTREntityWarg) {
            return this.mountType == Mount.WARG;
        }
        if(mount instanceof LOTREntityGiraffe) {
            return this.mountType == Mount.GIRAFFE;
        }
        if(mount instanceof LOTREntityRhino) {
            return this.mountType == Mount.RHINO;
        }
        return this.mountType == Mount.HORSE;
    }

    public int getDamageReduceAmount() {
        return this.damageReduceAmount;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    public ItemArmor.ArmorMaterial getMountArmorMaterial() {
        return this.armorMaterial;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
        return this.armorMaterial.func_151685_b() == repairItem.getItem() ? true : super.getIsRepairable(itemstack, repairItem);
    }

    public String getArmorTexture() {
        String path = null;
        if(this.templateItem != null) {
            int index = 0;
            if(this.templateItem == Items.iron_horse_armor) {
                index = 1;
            }
            if(this.templateItem == Items.golden_horse_armor) {
                index = 2;
            }
            if(this.templateItem == Items.diamond_horse_armor) {
                index = 3;
            }
            path = LOTRReflection.getHorseArmorTextures()[index];
        }
        else {
            String mountName = this.mountType.textureName;
            String materialName = this.armorMaterial.name().toLowerCase();
            if(materialName.startsWith("lotr_")) {
                materialName = materialName.substring("lotr_".length());
            }
            path = "lotr:armor/mount/" + mountName + "_" + materialName + ".png";
        }
        return path;
    }

    public enum Mount {
        HORSE("horse"), ELK("elk"), BOAR("boar"), CAMEL("camel"), WARG("warg"), GIRAFFE("giraffe"), RHINO("rhino");

        public final String textureName;

        Mount(String s) {
            this.textureName = s;
        }
    }

}
