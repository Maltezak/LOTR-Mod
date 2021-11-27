package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemDaleCracker extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] crackerIcons;
    private String[] crackerNames = new String[] {"red", "blue", "green", "silver", "gold"};
    private static int emptyMeta = 4096;
    public static final int CUSTOM_CAPACITY = 3;

    public LOTRItemDaleCracker() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
    }

    public static boolean isEmpty(ItemStack itemstack) {
        return (itemstack.getItemDamage() & emptyMeta) == emptyMeta;
    }

    public static ItemStack setEmpty(ItemStack itemstack, boolean flag) {
        int i = itemstack.getItemDamage();
        i = flag ? (i |= emptyMeta) : (i &= ~emptyMeta);
        itemstack.setItemDamage(i);
        return itemstack;
    }

    private static int getBaseCrackerMetadata(int i) {
        return i & ~emptyMeta;
    }

    public static String getSealingPlayerName(ItemStack itemstack) {
        if(itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SealingPlayer")) {
            return itemstack.getTagCompound().getString("SealingPlayer");
        }
        return null;
    }

    public static void setSealingPlayerName(ItemStack itemstack, String name) {
        if(itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if(name == null) {
            itemstack.getTagCompound().removeTag("SealingPlayer");
        }
        else {
            itemstack.getTagCompound().setString("SealingPlayer", name);
        }
    }

    public static IInventory loadCustomCrackerContents(ItemStack itemstack) {
        if(itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("CustomCracker")) {
            NBTTagCompound invData = itemstack.getTagCompound().getCompoundTag("CustomCracker");
            int size = invData.getInteger("Size");
            InventoryBasic inv = new InventoryBasic("cracker", false, size);
            NBTTagList items = invData.getTagList("Items", 10);
            for(int i = 0; i < items.tagCount(); ++i) {
                NBTTagCompound itemData = items.getCompoundTagAt(i);
                byte slot = itemData.getByte("Slot");
                if(slot < 0 || slot >= inv.getSizeInventory()) continue;
                inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
            }
            return inv;
        }
        return null;
    }

    public static void setCustomCrackerContents(ItemStack itemstack, IInventory inv) {
        if(itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if(inv == null) {
            itemstack.getTagCompound().removeTag("CustomCracker");
        }
        else {
            NBTTagCompound invData = new NBTTagCompound();
            int size = inv.getSizeInventory();
            invData.setInteger("Size", size);
            NBTTagList items = new NBTTagList();
            for(int i = 0; i < inv.getSizeInventory(); ++i) {
                ItemStack invItem = inv.getStackInSlot(i);
                if(invItem == null) continue;
                NBTTagCompound itemData = new NBTTagCompound();
                itemData.setByte("Slot", (byte) i);
                invItem.writeToNBT(itemData);
                items.appendTag(itemData);
            }
            invData.setTag("Items", items);
            itemstack.getTagCompound().setTag("CustomCracker", invData);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        if(LOTRItemDaleCracker.isEmpty(itemstack)) {
            String name = super.getItemStackDisplayName(itemstack);
            return StatCollector.translateToLocalFormatted("item.lotr.cracker.empty", name);
        }
        return super.getItemStackDisplayName(itemstack);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        if(!LOTRItemDaleCracker.isEmpty(itemstack)) {
            String name = LOTRItemDaleCracker.getSealingPlayerName(itemstack);
            if(name == null) {
                name = StatCollector.translateToLocal("item.lotr.cracker.sealedByDale");
            }
            list.add(StatCollector.translateToLocalFormatted("item.lotr.cracker.sealedBy", name));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 40;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!LOTRItemDaleCracker.isEmpty(itemstack)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        else {
            entityplayer.openGui(LOTRMod.instance, 48, world, 0, 0, 0);
        }
        return itemstack;
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!LOTRItemDaleCracker.isEmpty(itemstack)) {
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
                if(itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
            }
            world.playSoundAtEntity(entityplayer, "fireworks.blast", 1.0f, 0.9f + world.rand.nextFloat() * 0.1f);
            if(!world.isRemote) {
                IInventory crackerItems = null;
                IInventory customItems = LOTRItemDaleCracker.loadCustomCrackerContents(itemstack);
                if(customItems != null) {
                    crackerItems = customItems;
                }
                else {
                    int amount = 1;
                    if(world.rand.nextInt(3) == 0) {
                        ++amount;
                    }
                    if(LOTRMod.isChristmas()) {
                        amount += 1 + world.rand.nextInt(4);
                    }
                    crackerItems = new InventoryBasic("cracker", true, amount);
                    LOTRChestContents.fillInventory(crackerItems, world.rand, LOTRChestContents.DALE_CRACKER, amount);
                }
                for(int l = 0; l < crackerItems.getSizeInventory(); ++l) {
                    ItemStack loot = crackerItems.getStackInSlot(l);
                    if(entityplayer.inventory.addItemStackToInventory(loot)) continue;
                    entityplayer.dropPlayerItemWithRandomChoice(loot, false);
                }
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.openDaleCracker);
                return entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem);
            }
            return itemstack;
        }
        return itemstack;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        if((i = LOTRItemDaleCracker.getBaseCrackerMetadata(i)) >= this.crackerIcons.length) {
            i = 0;
        }
        return this.crackerIcons[i];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.crackerIcons = new IIcon[this.crackerNames.length];
        for(int i = 0; i < this.crackerNames.length; ++i) {
            this.crackerIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.crackerNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.crackerNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
            list.add(LOTRItemDaleCracker.setEmpty(new ItemStack(item, 1, i), true));
        }
    }
}
