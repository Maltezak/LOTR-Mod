package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTREnchantmentProtectionFire extends LOTREnchantmentProtectionSpecial {
    public LOTREnchantmentProtectionFire(String s, int level) {
        super(s, level);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectFire.desc", this.formatAdditiveInt(this.calcIntProtection()));
    }

    @Override
    protected boolean protectsAgainst(DamageSource source) {
        return source.isFireDamage();
    }

    @Override
    protected int calcIntProtection() {
        return 1 + this.protectLevel;
    }
}
