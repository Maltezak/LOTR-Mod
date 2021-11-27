package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeSpeed extends LOTREnchantment {
    public final float speedFactor;

    public LOTREnchantmentMeleeSpeed(String s, float speed) {
        super(s, LOTREnchantmentType.MELEE);
        this.speedFactor = speed;
        this.setValueModifier(this.speedFactor);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.meleeSpeed.desc", this.formatMultiplicative(this.speedFactor));
    }

    @Override
    public boolean isBeneficial() {
        return this.speedFactor >= 1.0f;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            float speed = LOTRWeaponStats.getMeleeSpeed(itemstack);
            return (speed *= this.speedFactor) <= LOTRWeaponStats.MAX_MODIFIABLE_SPEED;
        }
        return false;
    }
}
