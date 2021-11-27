package lotr.common.enchant;

import lotr.common.LOTRDamage;
import lotr.common.item.*;
import lotr.common.network.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentWeaponSpecial extends LOTREnchantment {
    private boolean compatibleBane = true;
    private boolean compatibleOtherSpecial = false;

    public LOTREnchantmentWeaponSpecial(String s) {
        super(s, new LOTREnchantmentType[] {LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE, LOTREnchantmentType.RANGED_LAUNCHER});
        this.setValueModifier(3.0f);
        this.setBypassAnvilLimit();
    }

    public LOTREnchantmentWeaponSpecial setIncompatibleBane() {
        this.compatibleBane = false;
        return this;
    }

    public LOTREnchantmentWeaponSpecial setCompatibleOtherSpecial() {
        this.compatibleOtherSpecial = true;
        return this;
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        if(LOTRWeaponStats.isMeleeWeapon(itemstack)) {
            return StatCollector.translateToLocalFormatted("lotr.enchant." + this.enchantName + ".desc.melee");
        }
        return StatCollector.translateToLocalFormatted("lotr.enchant." + this.enchantName + ".desc.ranged");
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean canApply(ItemStack itemstack, boolean considering) {
        if(super.canApply(itemstack, considering)) {
            Item item = itemstack.getItem();
            return !(item instanceof LOTRItemBalrogWhip) || this != LOTREnchantment.fire && this != LOTREnchantment.chill;
        }
        return false;
    }

    @Override
    public boolean isCompatibleWith(LOTREnchantment other) {
        if(!this.compatibleBane && other instanceof LOTREnchantmentBane) {
            return false;
        }
        return this.compatibleOtherSpecial || !(other instanceof LOTREnchantmentWeaponSpecial) || ((LOTREnchantmentWeaponSpecial) other).compatibleOtherSpecial;
    }

    public static int getFireAmount() {
        return 2;
    }

    public static void doChillAttack(EntityLivingBase entity) {
        if(entity instanceof EntityPlayerMP) {
            LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
        }
        int duration = 5;
        entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));
        LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.CHILLING, entity);
        LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entity, 64.0));
    }
}
