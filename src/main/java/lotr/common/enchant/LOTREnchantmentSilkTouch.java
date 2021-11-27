package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentSilkTouch extends LOTREnchantment {
    public LOTREnchantmentSilkTouch(String s) {
        super(s, LOTREnchantmentType.TOOL);
        this.setValueModifier(3.0f);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant." + this.enchantName + ".desc");
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean isCompatibleWith(LOTREnchantment other) {
        return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentLooting);
    }
}
