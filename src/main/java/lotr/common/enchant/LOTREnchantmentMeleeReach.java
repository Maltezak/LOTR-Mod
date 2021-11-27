package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeReach extends LOTREnchantment {
    public final float reachFactor;

    public LOTREnchantmentMeleeReach(String s, float reach) {
        super(s, LOTREnchantmentType.MELEE);
        this.reachFactor = reach;
        this.setValueModifier(this.reachFactor);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.meleeReach.desc", this.formatMultiplicative(this.reachFactor));
    }

    @Override
    public boolean isBeneficial() {
        return this.reachFactor >= 1.0f;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
            return (reach *= this.reachFactor) <= LOTRWeaponStats.MAX_MODIFIABLE_REACH;
        }
        return false;
    }
}
