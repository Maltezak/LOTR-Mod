package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentDurability extends LOTREnchantment {
    public final float durabilityFactor;

    public LOTREnchantmentDurability(String s, float f) {
        super(s, LOTREnchantmentType.BREAKABLE);
        this.durabilityFactor = f;
        this.setValueModifier(this.durabilityFactor);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.durable.desc", this.formatMultiplicative(this.durabilityFactor));
    }

    @Override
    public boolean isBeneficial() {
        return this.durabilityFactor >= 1.0f;
    }
}
