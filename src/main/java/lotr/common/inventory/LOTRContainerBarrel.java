package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerBarrel
extends Container {
    public LOTRTileEntityBarrel theBarrel;
    private int barrelMode = 0;
    private int brewingTime = 0;

    public LOTRContainerBarrel(InventoryPlayer inv, LOTRTileEntityBarrel barrel) {
        int i;
        int j;
        this.theBarrel = barrel;
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 3; ++j) {
                LOTRSlotBarrel slot = new LOTRSlotBarrel(barrel, j + i * 3, 14 + j * 18, 34 + i * 18);
                if (i == 2) {
                    slot.setWaterSource();
                }
                this.addSlotToContainer(slot);
            }
        }
        this.addSlotToContainer(new LOTRSlotBarrelResult(barrel, 9, 108, 52));
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 25 + j * 18, 139 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 25 + i * 18, 197));
        }
        if (!barrel.getWorldObj().isRemote && inv.player instanceof EntityPlayerMP) {
            barrel.players.add(inv.player);
        }
    }

    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.theBarrel.barrelMode);
        crafting.sendProgressBarUpdate(this, 1, this.theBarrel.brewingTime);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting)this.crafters.get(i);
            if (this.barrelMode != this.theBarrel.barrelMode) {
                crafting.sendProgressBarUpdate(this, 0, this.theBarrel.barrelMode);
            }
            if (this.brewingTime == this.theBarrel.brewingTime) continue;
            crafting.sendProgressBarUpdate(this, 1, this.theBarrel.brewingTime);
        }
        this.barrelMode = this.theBarrel.barrelMode;
        this.brewingTime = this.theBarrel.brewingTime;
    }

    @SideOnly(value=Side.CLIENT)
    public void updateProgressBar(int i, int j) {
        if (i == 0) {
            this.theBarrel.barrelMode = j;
        }
        if (i == 1) {
            this.theBarrel.brewingTime = j;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.theBarrel.isUseableByPlayer(entityplayer);
    }

    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!this.theBarrel.getWorldObj().isRemote && entityplayer instanceof EntityPlayerMP) {
            this.theBarrel.players.remove(entityplayer);
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < 9) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }
            } else if (i != 9) {
                boolean flag = false;
                Slot aBarrelSlot = (Slot)this.inventorySlots.get(0);
                if (aBarrelSlot.isItemValid(itemstack1)) {
                    flag = LOTRBrewingRecipes.isWaterSource(itemstack1) ? this.mergeItemStack(itemstack1, 6, 9, false) : this.mergeItemStack(itemstack1, 0, 6, false);
                }
                if (!flag && (i >= 10 && i < 37 ? !this.mergeItemStack(itemstack1, 37, 46, false) : !this.mergeItemStack(itemstack1, 10, 37, false))) {
                    return null;
                }
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack1);
        }
        return itemstack;
    }
}

