package lotr.common.item;

import java.util.*;

import com.google.common.collect.Multimap;

import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.ISpecialArmor;

public class LOTRWeaponStats {
    private static int basePlayerMeleeTime = 15;
    private static int baseMobMeleeTime = 20;
    private static Map meleeSpeed = new HashMap();
    private static Map meleeReach = new HashMap();
    private static Map meleeExtraKnockback = new HashMap();
    public static float MAX_MODIFIABLE_REACH;
    public static float MAX_MODIFIABLE_SPEED;
    public static int MAX_MODIFIABLE_KNOCKBACK;

    public static boolean isMeleeWeapon(ItemStack itemstack) {
        Multimap weaponAttributes;
        if(itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers()) != null) {
            for(Object obj : weaponAttributes.entries()) {
                Map.Entry e = (Map.Entry) obj;
                AttributeModifier mod = (AttributeModifier) e.getValue();
                if(mod.getID() != LOTRItemSword.accessWeaponDamageModifier()) continue;
                return true;
            }
        }
        return false;
    }

    public static float getMeleeDamageBonus(ItemStack itemstack) {
        Multimap weaponAttributes;
        float damage = 0.0f;
        if(itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers()) != null) {
            for(Object obj : weaponAttributes.entries()) {
                Map.Entry e = (Map.Entry) obj;
                AttributeModifier mod = (AttributeModifier) e.getValue();
                if(mod.getID() != LOTRItemSword.accessWeaponDamageModifier()) continue;
                damage = (float) (damage + mod.getAmount());
                damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
            }
        }
        return damage;
    }

    public static void registerMeleeSpeed(Object obj, float f) {
        meleeSpeed.put(obj, Float.valueOf(f));
    }

    public static void registerMeleeReach(Object obj, float f) {
        meleeReach.put(obj, Float.valueOf(f));
    }

    public static void registerMeleeExtraKnockback(Object obj, int i) {
        meleeExtraKnockback.put(obj, i);
    }

    private static Object getClassOrItemProperty(ItemStack itemstack, Map propertyMap) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(propertyMap.containsKey(item)) {
                return propertyMap.get(item);
            }
            Class<?> itemClass = item.getClass();
            do {
                if(propertyMap.containsKey(itemClass)) {
                    return propertyMap.get(itemClass);
                }
                if(itemClass == Item.class) break;
                itemClass = itemClass.getSuperclass();
            }
            while(true);
        }
        return null;
    }

    public static int getAttackTimePlayer(ItemStack itemstack) {
        return LOTRWeaponStats.getAttackTimeWithBase(itemstack, basePlayerMeleeTime);
    }

    public static int getAttackTimeMob(ItemStack itemstack) {
        return LOTRWeaponStats.getAttackTimeWithBase(itemstack, baseMobMeleeTime);
    }

    public static int getAttackTimeWithBase(ItemStack itemstack, int baseTime) {
        float time = baseTime;
        Float factor = (Float) LOTRWeaponStats.getClassOrItemProperty(itemstack, meleeSpeed);
        if(factor != null) {
            time /= factor;
        }
        time /= LOTREnchantmentHelper.calcMeleeSpeedFactor(itemstack);
        time = Math.max(time, 1.0f);
        return Math.round(time);
    }

    public static float getMeleeSpeed(ItemStack itemstack) {
        int base = basePlayerMeleeTime;
        return 1.0f / ((float) LOTRWeaponStats.getAttackTimeWithBase(itemstack, base) / (float) base);
    }

    public static float getMeleeReachFactor(ItemStack itemstack) {
        float reach = 1.0f;
        Float factor = (Float) LOTRWeaponStats.getClassOrItemProperty(itemstack, meleeReach);
        if(factor != null) {
            reach *= factor;
        }
        return reach *= LOTREnchantmentHelper.calcMeleeReachFactor(itemstack);
    }

    public static float getMeleeReachDistance(EntityPlayer entityplayer) {
        float reach = 3.0f;
        reach *= LOTRWeaponStats.getMeleeReachFactor(entityplayer.getHeldItem());
        if(entityplayer.capabilities.isCreativeMode) {
            reach = (float) (reach + 3.0);
        }
        return reach;
    }

    public static float getMeleeExtraLookWidth() {
        return 1.0f;
    }

    public static int getBaseExtraKnockback(ItemStack itemstack) {
        int kb = 0;
        Integer extra = (Integer) LOTRWeaponStats.getClassOrItemProperty(itemstack, meleeExtraKnockback);
        if(extra != null) {
            kb = extra;
        }
        return kb;
    }

    public static int getTotalKnockback(ItemStack itemstack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack) + LOTRWeaponStats.getBaseExtraKnockback(itemstack) + LOTREnchantmentHelper.calcExtraKnockback(itemstack);
    }

    public static boolean isPoisoned(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            return item instanceof LOTRItemDagger && ((LOTRItemDagger) item).getDaggerEffect() == LOTRItemDagger.DaggerEffect.POISON;
        }
        return false;
    }

    public static boolean isRangedWeapon(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            return item instanceof ItemBow || item instanceof LOTRItemSpear || item instanceof LOTRItemBlowgun || item instanceof LOTRItemThrowingAxe;
        }
        return false;
    }

    public static float getRangedSpeed(ItemStack itemstack) {
        int base = 20;
        int time = 0;
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof LOTRItemCrossbow) {
                time = ((LOTRItemCrossbow) item).getMaxDrawTime();
            }
            else if(item instanceof LOTRItemBow) {
                time = ((LOTRItemBow) item).getMaxDrawTime();
            }
            else if(item == Items.bow) {
                time = 20;
            }
            if(item instanceof LOTRItemSpear) {
                time = ((LOTRItemSpear) item).getMaxDrawTime();
            }
            if(item instanceof LOTRItemBlowgun) {
                time = ((LOTRItemBlowgun) item).getMaxDrawTime();
            }
        }
        if(time > 0) {
            return 1.0f / ((float) time / (float) base);
        }
        return 0.0f;
    }

    public static float getRangedDamageFactor(ItemStack itemstack, boolean launchSpeedOnly) {
        float baseArrowFactor = 2.0f;
        float weaponFactor = 0.0f;
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof LOTRItemCrossbow) {
                weaponFactor = baseArrowFactor * (float) ((LOTRItemCrossbow) item).boltDamageFactor;
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
                if(!launchSpeedOnly) {
                    int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
                    if(power > 0) {
                        weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
                    }
                    weaponFactor *= 2.0f;
                }
            }
            else if(item instanceof ItemBow) {
                int power;
                weaponFactor = baseArrowFactor;
                if(item instanceof LOTRItemBow) {
                    weaponFactor *= (float) ((LOTRItemBow) item).arrowDamageFactor;
                }
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
                if(!launchSpeedOnly && (power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack)) > 0) {
                    weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
                }
            }
            else if(item instanceof LOTRItemBlowgun) {
                weaponFactor = baseArrowFactor;
                if(!launchSpeedOnly) {
                    weaponFactor *= 1.0f / baseArrowFactor;
                }
                weaponFactor *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
            }
            else if(item instanceof LOTRItemSpear) {
                weaponFactor = ((LOTRItemSpear) item).getRangedDamageMultiplier(itemstack, null, null);
            }
            else if(item instanceof LOTRItemThrowingAxe) {
                weaponFactor = ((LOTRItemThrowingAxe) item).getRangedDamageMultiplier(itemstack, null, null);
            }
        }
        if(weaponFactor > 0.0f) {
            return weaponFactor / baseArrowFactor;
        }
        return 0.0f;
    }

    public static int getRangedKnockback(ItemStack itemstack) {
        if(LOTRWeaponStats.isMeleeWeapon(itemstack) || itemstack != null && itemstack.getItem() instanceof LOTRItemThrowingAxe) {
            return LOTRWeaponStats.getTotalKnockback(itemstack);
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack) + LOTREnchantmentHelper.calcRangedKnockback(itemstack);
    }

    public static int getArmorProtection(ItemStack itemstack) {
        Item item;
        if(itemstack != null && (item = itemstack.getItem()) instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) item;
            int i = armor.damageReduceAmount;
            return i += LOTREnchantmentHelper.calcCommonArmorProtection(itemstack);
        }
        return 0;
    }

    public static int getTotalArmorValue(EntityPlayer entityplayer) {
        int protection = 0;
        for(int i = 0; i < entityplayer.inventory.armorInventory.length; ++i) {
            ItemStack stack = entityplayer.inventory.armorInventory[i];
            if(stack != null && stack.getItem() instanceof ISpecialArmor) {
                protection += ((ISpecialArmor) (stack.getItem())).getArmorDisplay(entityplayer, stack, i);
                continue;
            }
            if(stack == null || !(stack.getItem() instanceof ItemArmor)) continue;
            protection += LOTRWeaponStats.getArmorProtection(stack);
        }
        return protection;
    }

    static {
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemDagger.class, 1.5f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemSpear.class, 0.833f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemPolearm.class, 0.667f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemPolearmLong.class, 0.5f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemLance.class, 0.5f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemBattleaxe.class, 0.75f);
        LOTRWeaponStats.registerMeleeSpeed(LOTRItemHammer.class, 0.667f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemDagger.class, 0.75f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemSpear.class, 1.5f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemPolearm.class, 1.5f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemPolearmLong.class, 2.0f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemLance.class, 2.0f);
        LOTRWeaponStats.registerMeleeReach(LOTRItemBalrogWhip.class, 1.5f);
        LOTRWeaponStats.registerMeleeExtraKnockback(LOTRItemHammer.class, 1);
        LOTRWeaponStats.registerMeleeExtraKnockback(LOTRItemLance.class, 1);
        MAX_MODIFIABLE_REACH = 2.0f;
        MAX_MODIFIABLE_SPEED = 1.6f;
        MAX_MODIFIABLE_KNOCKBACK = 2;
    }
}
