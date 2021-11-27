package lotr.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class LOTREnchantmentProtectionSpecial extends LOTREnchantment {
    public final int protectLevel;

    public LOTREnchantmentProtectionSpecial(String s, int level) {
        this(s, LOTREnchantmentType.ARMOR, level);
    }

    public LOTREnchantmentProtectionSpecial(String s, LOTREnchantmentType type, int level) {
        super(s, type);
        this.protectLevel = level;
        this.setValueModifier((2.0f + this.protectLevel) / 2.0f);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean isCompatibleWith(LOTREnchantment other) {
        if(super.isCompatibleWith(other)) {
            if(other instanceof LOTREnchantmentProtectionSpecial) {
                return this.isCompatibleWithOtherProtection() || ((LOTREnchantmentProtectionSpecial) other).isCompatibleWithOtherProtection();
            }
            return true;
        }
        return false;
    }

    protected boolean isCompatibleWithOtherProtection() {
        return false;
    }

    protected abstract boolean protectsAgainst(DamageSource var1);

    public final int calcSpecialProtection(DamageSource source) {
        if(source.canHarmInCreative()) {
            return 0;
        }
        if(this.protectsAgainst(source)) {
            return this.calcIntProtection();
        }
        return 0;
    }

    protected abstract int calcIntProtection();
}
