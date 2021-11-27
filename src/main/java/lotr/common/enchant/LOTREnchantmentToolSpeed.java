package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentToolSpeed extends LOTREnchantment {
    public final float speedFactor;

    public LOTREnchantmentToolSpeed(String s, float speed) {
        super(s, new LOTREnchantmentType[] {LOTREnchantmentType.TOOL, LOTREnchantmentType.SHEARS});
        this.speedFactor = speed;
        this.setValueModifier(this.speedFactor);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.toolSpeed.desc", this.formatMultiplicative(this.speedFactor));
    }

    @Override
    public boolean isBeneficial() {
        return this.speedFactor >= 1.0f;
    }
}
