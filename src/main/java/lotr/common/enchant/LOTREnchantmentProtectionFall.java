package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTREnchantmentProtectionFall extends LOTREnchantmentProtectionSpecial {
    public LOTREnchantmentProtectionFall(String s, int level) {
        super(s, LOTREnchantmentType.ARMOR_FEET, level);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectFall.desc", this.formatAdditiveInt(this.calcIntProtection()));
    }

    @Override
    protected boolean isCompatibleWithOtherProtection() {
        return true;
    }

    @Override
    protected boolean protectsAgainst(DamageSource source) {
        return source == DamageSource.fall;
    }

    @Override
    protected int calcIntProtection() {
        float f = (float) this.protectLevel * (float) (this.protectLevel + 1) / 2.0f;
        return 3 + MathHelper.floor_float(f);
    }
}
