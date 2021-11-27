package lotr.common.item;

import lotr.common.*;
import lotr.common.enchant.*;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemBlowgun extends Item {
    public LOTRItemBlowgun(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemBlowgun(Item.ToolMaterial material) {
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        this.setFull3D();
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
        ItemStack dartItem = null;
        int dartSlot = -1;
        for(int l = 0; l < entityplayer.inventory.mainInventory.length; ++l) {
            ItemStack invItem = entityplayer.inventory.mainInventory[l];
            if(invItem == null || !(invItem.getItem() instanceof LOTRItemDart)) continue;
            dartItem = invItem;
            dartSlot = l;
            break;
        }
        if(dartItem == null && entityplayer.capabilities.isCreativeMode) {
            dartItem = new ItemStack(LOTRMod.tauredainDart);
        }
        if(dartItem != null) {
            int useTick = this.getMaxItemUseDuration(itemstack) - i;
            float charge = (float) useTick / (float) this.getMaxDrawTime();
            if(charge < 0.65f) {
                return;
            }
            charge = (charge * charge + charge * 2.0f) / 3.0f;
            charge = Math.min(charge, 1.0f);
            itemstack.damageItem(1, entityplayer);
            if(!entityplayer.capabilities.isCreativeMode && dartSlot >= 0) {
                --dartItem.stackSize;
                if(dartItem.stackSize <= 0) {
                    entityplayer.inventory.mainInventory[dartSlot] = null;
                }
            }
            world.playSoundAtEntity(entityplayer, "lotr:item.dart", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
            if(!world.isRemote) {
                ItemStack shotDart = dartItem.copy();
                shotDart.stackSize = 1;
                LOTREntityDart dart = ((LOTRItemDart) shotDart.getItem()).createDart(world, entityplayer, shotDart, charge * 2.0f * LOTRItemBlowgun.getBlowgunLaunchSpeedFactor(itemstack));
                if(dart.dartDamageFactor < 1.0f) {
                    dart.dartDamageFactor = 1.0f;
                }
                if(charge >= 1.0f) {
                    dart.setIsCritical(true);
                }
                LOTRItemBlowgun.applyBlowgunModifiers(dart, itemstack);
                if(entityplayer.capabilities.isCreativeMode) {
                    dart.canBePickedUp = 2;
                }
                world.spawnEntityInWorld(dart);
            }
        }
    }

    public static float getBlowgunLaunchSpeedFactor(ItemStack itemstack) {
        float f = 1.0f;
        if(itemstack != null) {
            f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
        }
        return f;
    }

    public static void applyBlowgunModifiers(LOTREntityDart dart, ItemStack itemstack) {
        int punch = LOTREnchantmentHelper.calcRangedKnockback(itemstack);
        if(punch > 0) {
            dart.knockbackStrength = punch;
        }
        if((EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack)) > 0) {
            dart.setFire(100);
        }
        for(LOTREnchantment ench : LOTREnchantment.allEnchantments) {
            if(!ench.applyToProjectile() || !LOTREnchantmentHelper.hasEnchant(itemstack, ench)) continue;
            LOTREnchantmentHelper.setProjectileEnchantment(dart, ench);
        }
    }

    public int getMaxDrawTime() {
        return 5;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        boolean anyDart = false;
        for(ItemStack invItem : entityplayer.inventory.mainInventory) {
            if(invItem == null || !(invItem.getItem() instanceof LOTRItemDart)) continue;
            anyDart = true;
            break;
        }
        if(anyDart || entityplayer.capabilities.isCreativeMode) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
        return repairItem.getItem() == Item.getItemFromBlock(LOTRMod.reeds);
    }
}
