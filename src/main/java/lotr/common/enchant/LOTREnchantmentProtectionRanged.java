package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class LOTREnchantmentProtectionRanged extends LOTREnchantmentProtectionSpecial {
    public LOTREnchantmentProtectionRanged(String s, int level) {
        super(s, level);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant.protectRanged.desc", this.formatAdditiveInt(this.calcIntProtection()));
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            Item item = itemstack.getItem();
            return !(item instanceof ItemArmor) || ((ItemArmor) item).getArmorMaterial() != LOTRMaterial.GALVORN.toArmorMaterial();
        }
        return false;
    }

    @Override
    protected boolean protectsAgainst(DamageSource source) {
        return source.isProjectile();
    }

    @Override
    protected int calcIntProtection() {
        return this.protectLevel;
    }
}
