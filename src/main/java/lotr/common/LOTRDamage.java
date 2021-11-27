package lotr.common;

import lotr.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class LOTRDamage {
    public static DamageSource frost = new DamageSource("lotr.frost").setDamageBypassesArmor();
    public static DamageSource poisonDrink = new DamageSource("lotr.poisonDrink").setDamageBypassesArmor().setMagicDamage();
    public static DamageSource plantHurt = new DamageSource("lotr.plantHurt").setDamageBypassesArmor();

    public static void doFrostDamage(EntityPlayerMP entityplayer) {
        LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.FROST);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public static void doBurnDamage(EntityPlayerMP entityplayer) {
        LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.BURN);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }
}
