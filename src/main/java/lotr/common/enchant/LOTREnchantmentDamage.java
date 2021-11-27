package lotr.common.enchant;

import lotr.common.item.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentDamage extends LOTREnchantment {
    private final float baseDamageBoost;

    public LOTREnchantmentDamage(String s, float boost) {
        super(s, new LOTREnchantmentType[] {LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
        this.baseDamageBoost = boost;
        if(this.baseDamageBoost >= 0.0f) {
            this.setValueModifier((7.0f + this.baseDamageBoost * 5.0f) / 7.0f);
        }
        else {
            this.setValueModifier((7.0f + this.baseDamageBoost) / 7.0f);
        }
    }

    public float getBaseDamageBoost() {
        return this.baseDamageBoost;
    }

    public float getEntitySpecificDamage(EntityLivingBase entity) {
        return 0.0f;
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemThrowingAxe) {
            return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc.throw", this.formatAdditive(this.baseDamageBoost));
        }
        return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc", this.formatAdditive(this.baseDamageBoost));
    }

    @Override
    public boolean isBeneficial() {
        return this.baseDamageBoost >= 0.0f;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            float dmg = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
            return (dmg += this.baseDamageBoost) > 0.0f;
        }
        return false;
    }
}
