package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentKnockback extends LOTREnchantment {
    public final int knockback;

    public LOTREnchantmentKnockback(String s, int i) {
        super(s, new LOTREnchantmentType[] {LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
        this.knockback = i;
        this.setValueModifier((this.knockback + 2) / 2.0f);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.knockback.desc", this.formatAdditiveInt(this.knockback));
    }

    @Override
    public boolean isBeneficial() {
        return this.knockback >= 0;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        return super.canApply(itemstack, considering) && LOTRWeaponStats.getBaseExtraKnockback(itemstack) + this.knockback <= LOTRWeaponStats.MAX_MODIFIABLE_KNOCKBACK;
    }
}
