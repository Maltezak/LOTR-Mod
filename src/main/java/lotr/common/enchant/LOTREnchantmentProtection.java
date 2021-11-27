package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtection extends LOTREnchantment {
    public final int protectLevel;

    public LOTREnchantmentProtection(String s, int level) {
        this(s, LOTREnchantmentType.ARMOR, level);
    }

    public LOTREnchantmentProtection(String s, LOTREnchantmentType type, int level) {
        super(s, type);
        this.protectLevel = level;
        if(this.protectLevel >= 0) {
            this.setValueModifier((2.0f + this.protectLevel) / 2.0f);
        }
        else {
            this.setValueModifier((4.0f + this.protectLevel) / 4.0f);
        }
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protect.desc", this.formatAdditiveInt(this.protectLevel));
    }

    @Override
    public boolean isBeneficial() {
        return this.protectLevel >= 0;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            Item item = itemstack.getItem();
            if(item instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) item;
                if(armor.getArmorMaterial() == LOTRMaterial.GALVORN.toArmorMaterial()) {
                    return false;
                }
                int prot = armor.damageReduceAmount;
                int total = prot + this.protectLevel;
                if(total > 0) {
                    if(considering) {
                        return true;
                    }
                    return total <= LOTRMaterial.MITHRIL.toArmorMaterial().getDamageReductionAmount(armor.armorType);
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
