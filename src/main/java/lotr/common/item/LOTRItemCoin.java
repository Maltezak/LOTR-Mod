package lotr.common.item;

import java.util.List;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.quest.IPickpocketable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemCoin
extends Item {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] coinIcons;
    public static int[] values = new int[]{1, 10, 100};

    public LOTRItemCoin() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(LOTRCreativeTabs.tabMaterials);
    }

    private static int getSingleItemValue(ItemStack itemstack, boolean allowStolen) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemCoin) {
            if (!allowStolen && IPickpocketable.Helper.isPickpocketed(itemstack)) {
                return 0;
            }
            int i = itemstack.getItemDamage();
            if (i >= values.length) {
                i = 0;
            }
            return values[i];
        }
        return 0;
    }

    public static int getStackValue(ItemStack itemstack, boolean allowStolen) {
        if (itemstack == null) {
            return 0;
        }
        return LOTRItemCoin.getSingleItemValue(itemstack, allowStolen) * itemstack.stackSize;
    }

    public static int getInventoryValue(EntityPlayer entityplayer, boolean allowStolen) {
        int coins = 0;
        for (ItemStack itemstack : entityplayer.inventory.mainInventory) {
            coins += LOTRItemCoin.getStackValue(itemstack, allowStolen);
        }
        return coins += LOTRItemCoin.getStackValue(entityplayer.inventory.getItemStack(), allowStolen);
    }

    public static int getContainerValue(IInventory inv, boolean allowStolen) {
        if (inv instanceof InventoryPlayer) {
            return LOTRItemCoin.getInventoryValue(((InventoryPlayer)inv).player, allowStolen);
        }
        int coins = 0;
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            coins += LOTRItemCoin.getStackValue(itemstack, allowStolen);
        }
        return coins;
    }

    public static void takeCoins(int coins, EntityPlayer entityplayer) {
        int slot;
        int i;
        ItemStack coin;
        ItemStack is;
        ItemStack itemstack;
        int value;
        InventoryPlayer inv = entityplayer.inventory;
        int invValue = LOTRItemCoin.getInventoryValue(entityplayer, false);
        if (invValue < coins) {
            FMLLog.warning("Attempted to take " + coins + " coins from player " + entityplayer.getCommandSenderName() + " who has only " + invValue);
        }
        int initCoins = coins;
        block0: for (i = values.length - 1; i >= 0; --i) {
            value = values[i];
            if (value > initCoins) continue;
            coin = new ItemStack(LOTRMod.silverCoin, 1, i);
            for (slot = -1; slot < inv.mainInventory.length; ++slot) {
                while ((itemstack = slot == -1 ? inv.getItemStack() : inv.mainInventory[slot]) != null && itemstack.isItemEqual(coin)) {
                    if (slot == -1) {
                        is = inv.getItemStack();
                        if (is != null) {
                            --is.stackSize;
                            if (is.stackSize <= 0) {
                                inv.setItemStack(null);
                            }
                        }
                    } else {
                        inv.decrStackSize(slot, 1);
                    }
                    if ((coins -= value) >= value) continue;
                    continue block0;
                }
            }
        }
        if (coins > 0) {
            for (i = 0; i < values.length; ++i) {
                if (i == 0) continue;
                value = values[i];
                coin = new ItemStack(LOTRMod.silverCoin, 1, i);
                block4: for (slot = -1; slot < inv.mainInventory.length; ++slot) {
                    while ((itemstack = slot == -1 ? inv.getItemStack() : inv.mainInventory[slot]) != null && itemstack.isItemEqual(coin)) {
                        if (slot == -1) {
                            is = inv.getItemStack();
                            if (is != null) {
                                --is.stackSize;
                                if (is.stackSize <= 0) {
                                    inv.setItemStack(null);
                                }
                            }
                        } else {
                            inv.decrStackSize(slot, 1);
                        }
                        if ((coins -= value) >= 0) continue;
                        break block4;
                    }
                }
                if (coins < 0) break;
            }
        }
        if (coins < 0) {
            LOTRItemCoin.giveCoins(-coins, entityplayer);
        }
    }

    public static void giveCoins(int coins, EntityPlayer entityplayer) {
        int i;
        int value;
        ItemStack coin;
        InventoryPlayer inv = entityplayer.inventory;
        if (coins <= 0) {
            FMLLog.warning("Attempted to give a non-positive value of coins " + coins + " to player " + entityplayer.getCommandSenderName());
        }
        for (i = values.length - 1; i >= 0; --i) {
            value = values[i];
            coin = new ItemStack(LOTRMod.silverCoin, 1, i);
            while (coins >= value && inv.addItemStackToInventory(coin.copy())) {
                coins -= value;
            }
        }
        if (coins > 0) {
            for (i = values.length - 1; i >= 0; --i) {
                value = values[i];
                coin = new ItemStack(LOTRMod.silverCoin, 1, i);
                while (coins >= value) {
                    entityplayer.dropPlayerItemWithRandomChoice(coin.copy(), false);
                    coins -= value;
                }
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.coinIcons.length) {
            i = 0;
        }
        return this.coinIcons[i];
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i >= values.length) {
            i = 0;
        }
        return super.getUnlocalizedName() + "." + values[i];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconregister) {
        this.coinIcons = new IIcon[values.length];
        for (int i = 0; i < values.length; ++i) {
            this.coinIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + values[i]);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < values.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}

