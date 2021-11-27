package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentRangedDamage extends LOTREnchantment {
    public final float damageFactor;

    public LOTREnchantmentRangedDamage(String s, float damage) {
        super(s, LOTREnchantmentType.RANGED_LAUNCHER);
        this.damageFactor = damage;
        if(this.damageFactor > 1.0f) {
            this.setValueModifier(this.damageFactor * 2.0f);
        }
        else {
            this.setValueModifier(this.damageFactor);
        }
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.rangedDamage.desc", this.formatMultiplicative(this.damageFactor));
    }

    @Override
    public boolean isBeneficial() {
        return this.damageFactor >= 1.0f;
    }
}
