package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentRangedKnockback extends LOTREnchantment {
    public final int knockback;

    public LOTREnchantmentRangedKnockback(String s, int i) {
        super(s, LOTREnchantmentType.RANGED_LAUNCHER);
        this.knockback = i;
        this.setValueModifier((this.knockback + 2) / 2.0f);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.rangedKnockback.desc", this.formatAdditiveInt(this.knockback));
    }

    @Override
    public boolean isBeneficial() {
        return this.knockback >= 0;
    }
}
