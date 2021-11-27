package lotr.common.enchant;

import java.text.DecimalFormat;
import java.util.*;

import lotr.common.entity.npc.*;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public abstract class LOTREnchantment {
    public static List<LOTREnchantment> allEnchantments = new ArrayList<>();
    private static Map<String, LOTREnchantment> enchantsByName = new HashMap<>();
    public static final LOTREnchantment strong1 = new LOTREnchantmentDamage("strong1", 0.5f).setEnchantWeight(10);
    public static final LOTREnchantment strong2 = new LOTREnchantmentDamage("strong2", 1.0f).setEnchantWeight(5);
    public static final LOTREnchantment strong3 = new LOTREnchantmentDamage("strong3", 2.0f).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment strong4 = new LOTREnchantmentDamage("strong4", 3.0f).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment weak1 = new LOTREnchantmentDamage("weak1", -0.5f).setEnchantWeight(6);
    public static final LOTREnchantment weak2 = new LOTREnchantmentDamage("weak2", -1.0f).setEnchantWeight(4);
    public static final LOTREnchantment weak3 = new LOTREnchantmentDamage("weak3", -2.0f).setEnchantWeight(2);
    public static final LOTREnchantment baneElf = new LOTREnchantmentBane("baneElf", 4.0f, LOTREntityElf.class).setEnchantWeight(0);
    public static final LOTREnchantment baneOrc = new LOTREnchantmentBane("baneOrc", 4.0f, LOTREntityOrc.class).setEnchantWeight(0);
    public static final LOTREnchantment baneDwarf = new LOTREnchantmentBane("baneDwarf", 4.0f, LOTREntityDwarf.class).setEnchantWeight(0);
    public static final LOTREnchantment baneWarg = new LOTREnchantmentBane("baneWarg", 4.0f, LOTREntityWarg.class).setEnchantWeight(0);
    public static final LOTREnchantment baneTroll = new LOTREnchantmentBane("baneTroll", 4.0f, LOTREntityTroll.class, LOTREntityHalfTroll.class).setEnchantWeight(0);
    public static final LOTREnchantment baneSpider = new LOTREnchantmentBane("baneSpider", 4.0f, EnumCreatureAttribute.ARTHROPOD).setEnchantWeight(0);
    public static final LOTREnchantment baneWight = new LOTREnchantmentBane("baneWight", 4.0f, EnumCreatureAttribute.UNDEAD).setEnchantWeight(0);
    public static final LOTREnchantment baneWraith = new LOTREnchantmentBane("baneWraith", 0.0f, LOTREntityMarshWraith.class).setUnachievable().setEnchantWeight(0);
    public static final LOTREnchantment durable1 = new LOTREnchantmentDurability("durable1", 1.25f).setEnchantWeight(15);
    public static final LOTREnchantment durable2 = new LOTREnchantmentDurability("durable2", 1.5f).setEnchantWeight(8);
    public static final LOTREnchantment durable3 = new LOTREnchantmentDurability("durable3", 2.0f).setEnchantWeight(4).setSkilful();
    public static final LOTREnchantment meleeSpeed1 = new LOTREnchantmentMeleeSpeed("meleeSpeed1", 1.25f).setEnchantWeight(6);
    public static final LOTREnchantment meleeSlow1 = new LOTREnchantmentMeleeSpeed("meleeSlow1", 0.75f).setEnchantWeight(4);
    public static final LOTREnchantment meleeReach1 = new LOTREnchantmentMeleeReach("meleeReach1", 1.25f).setEnchantWeight(6);
    public static final LOTREnchantment meleeUnreach1 = new LOTREnchantmentMeleeReach("meleeUnreach1", 0.75f).setEnchantWeight(4);
    public static final LOTREnchantment knockback1 = new LOTREnchantmentKnockback("knockback1", 1).setEnchantWeight(6);
    public static final LOTREnchantment knockback2 = new LOTREnchantmentKnockback("knockback2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment toolSpeed1 = new LOTREnchantmentToolSpeed("toolSpeed1", 1.5f).setEnchantWeight(20);
    public static final LOTREnchantment toolSpeed2 = new LOTREnchantmentToolSpeed("toolSpeed2", 2.0f).setEnchantWeight(10);
    public static final LOTREnchantment toolSpeed3 = new LOTREnchantmentToolSpeed("toolSpeed3", 3.0f).setEnchantWeight(5).setSkilful();
    public static final LOTREnchantment toolSpeed4 = new LOTREnchantmentToolSpeed("toolSpeed4", 4.0f).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment toolSlow1 = new LOTREnchantmentToolSpeed("toolSlow1", 0.75f).setEnchantWeight(10);
    public static final LOTREnchantment toolSilk = new LOTREnchantmentSilkTouch("toolSilk").setEnchantWeight(10).setSkilful();
    public static final LOTREnchantment looting1 = new LOTREnchantmentLooting("looting1", 1).setEnchantWeight(6);
    public static final LOTREnchantment looting2 = new LOTREnchantmentLooting("looting2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment looting3 = new LOTREnchantmentLooting("looting3", 3).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment protect1 = new LOTREnchantmentProtection("protect1", 1).setEnchantWeight(10);
    public static final LOTREnchantment protect2 = new LOTREnchantmentProtection("protect2", 2).setEnchantWeight(3).setSkilful();
    public static final LOTREnchantment protectWeak1 = new LOTREnchantmentProtection("protectWeak1", -1).setEnchantWeight(5);
    public static final LOTREnchantment protectWeak2 = new LOTREnchantmentProtection("protectWeak2", -2).setEnchantWeight(2);
    public static final LOTREnchantment protectFire1 = new LOTREnchantmentProtectionFire("protectFire1", 1).setEnchantWeight(5);
    public static final LOTREnchantment protectFire2 = new LOTREnchantmentProtectionFire("protectFire2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment protectFire3 = new LOTREnchantmentProtectionFire("protectFire3", 3).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment protectFall1 = new LOTREnchantmentProtectionFall("protectFall1", 1).setEnchantWeight(5);
    public static final LOTREnchantment protectFall2 = new LOTREnchantmentProtectionFall("protectFall2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment protectFall3 = new LOTREnchantmentProtectionFall("protectFall3", 3).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment protectRanged1 = new LOTREnchantmentProtectionRanged("protectRanged1", 1).setEnchantWeight(5);
    public static final LOTREnchantment protectRanged2 = new LOTREnchantmentProtectionRanged("protectRanged2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment protectRanged3 = new LOTREnchantmentProtectionRanged("protectRanged3", 3).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment protectMithril = new LOTREnchantmentProtectionMithril("protectMithril").setEnchantWeight(0);
    public static final LOTREnchantment rangedStrong1 = new LOTREnchantmentRangedDamage("rangedStrong1", 1.1f).setEnchantWeight(10);
    public static final LOTREnchantment rangedStrong2 = new LOTREnchantmentRangedDamage("rangedStrong2", 1.2f).setEnchantWeight(3);
    public static final LOTREnchantment rangedStrong3 = new LOTREnchantmentRangedDamage("rangedStrong3", 1.3f).setEnchantWeight(1).setSkilful();
    public static final LOTREnchantment rangedWeak1 = new LOTREnchantmentRangedDamage("rangedWeak1", 0.75f).setEnchantWeight(8);
    public static final LOTREnchantment rangedWeak2 = new LOTREnchantmentRangedDamage("rangedWeak2", 0.5f).setEnchantWeight(3);
    public static final LOTREnchantment rangedKnockback1 = new LOTREnchantmentRangedKnockback("rangedKnockback1", 1).setEnchantWeight(6);
    public static final LOTREnchantment rangedKnockback2 = new LOTREnchantmentRangedKnockback("rangedKnockback2", 2).setEnchantWeight(2).setSkilful();
    public static final LOTREnchantment fire = new LOTREnchantmentWeaponSpecial("fire").setEnchantWeight(0).setApplyToProjectile();
    public static final LOTREnchantment chill = new LOTREnchantmentWeaponSpecial("chill").setEnchantWeight(0).setApplyToProjectile();
    public static final LOTREnchantment headhunting = new LOTREnchantmentWeaponSpecial("headhunting").setCompatibleOtherSpecial().setIncompatibleBane().setEnchantWeight(0).setApplyToProjectile();
    public final String enchantName;
    public final List<LOTREnchantmentType> itemTypes;
    private int enchantWeight = 0;
    private float valueModifier = 1.0f;
    private boolean skilful = false;
    private boolean persistsReforge = false;
    private boolean bypassAnvilLimit = false;
    private boolean applyToProjectile = false;

    public LOTREnchantment(String s, LOTREnchantmentType type) {
        this(s, new LOTREnchantmentType[] {type});
    }

    public LOTREnchantment(String s, LOTREnchantmentType[] types) {
        this.enchantName = s;
        this.itemTypes = Arrays.asList(types);
        allEnchantments.add(this);
        enchantsByName.put(this.enchantName, this);
    }

    public int getEnchantWeight() {
        return this.enchantWeight;
    }

    public LOTREnchantment setEnchantWeight(int i) {
        this.enchantWeight = i;
        return this;
    }

    public float getValueModifier() {
        return this.valueModifier;
    }

    public LOTREnchantment setValueModifier(float f) {
        this.valueModifier = f;
        return this;
    }

    public boolean isSkilful() {
        return this.skilful;
    }

    public LOTREnchantment setSkilful() {
        this.skilful = true;
        return this;
    }

    public boolean persistsReforge() {
        return this.persistsReforge;
    }

    public LOTREnchantment setPersistsReforge() {
        this.persistsReforge = true;
        return this;
    }

    public boolean bypassAnvilLimit() {
        return this.bypassAnvilLimit;
    }

    public LOTREnchantment setBypassAnvilLimit() {
        this.bypassAnvilLimit = true;
        return this;
    }

    public boolean applyToProjectile() {
        return this.applyToProjectile;
    }

    public LOTREnchantment setApplyToProjectile() {
        this.applyToProjectile = true;
        return this;
    }

    public String getDisplayName() {
        return StatCollector.translateToLocal("lotr.enchant." + this.enchantName);
    }

    public abstract String getDescription(ItemStack var1);

    public final String getNamedFormattedDescription(ItemStack itemstack) {
        String s = StatCollector.translateToLocalFormatted("lotr.enchant.descFormat", this.getDisplayName(), this.getDescription(itemstack));
        s = this.isBeneficial() ? (EnumChatFormatting.GRAY) + s : (EnumChatFormatting.DARK_GRAY) + s;
        return s;
    }

    public abstract boolean isBeneficial();

    public IChatComponent getEarnMessage(ItemStack itemstack) {
        ChatComponentTranslation msg = new ChatComponentTranslation("lotr.enchant." + this.enchantName + ".earn", itemstack.getDisplayName());
        msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        return msg;
    }

    public IChatComponent getEarnMessageWithName(EntityPlayer entityplayer, ItemStack itemstack) {
        ChatComponentTranslation msg = new ChatComponentTranslation("lotr.enchant." + this.enchantName + ".earnName", entityplayer.getCommandSenderName(), itemstack.getDisplayName());
        msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        return msg;
    }

    public boolean canApply(ItemStack itemstack, boolean considering) {
        for(LOTREnchantmentType type : this.itemTypes) {
            if(!type.canApply(itemstack, considering)) continue;
            return true;
        }
        return false;
    }

    public boolean isCompatibleWith(LOTREnchantment other) {
        return this.getClass() != other.getClass();
    }

    public boolean hasTemplateItem() {
        return this.getEnchantWeight() > 0 && this.isBeneficial();
    }

    public static LOTREnchantment getEnchantmentByName(String s) {
        return enchantsByName.get(s);
    }

    protected final String formatAdditiveInt(int i) {
        String s = String.valueOf(i);
        if(i >= 0) {
            s = "+" + s;
        }
        return s;
    }

    protected final String formatAdditive(float f) {
        String s = this.formatDecimalNumber(f);
        if(f >= 0.0f) {
            s = "+" + s;
        }
        return s;
    }

    protected final String formatMultiplicative(float f) {
        String s = this.formatDecimalNumber(f);
        s = "x" + s;
        return s;
    }

    private final String formatDecimalNumber(float f) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(1);
        String s = df.format(f);
        return s;
    }
}
