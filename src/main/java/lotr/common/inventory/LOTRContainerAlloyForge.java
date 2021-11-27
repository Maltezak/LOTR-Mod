package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerAlloyForge extends Container {
    private LOTRTileEntityAlloyForgeBase theForge;
    private int currentSmeltTime = 0;
    private int forgeSmeltTime = 0;
    private int currentItemFuelValue = 0;

    public LOTRContainerAlloyForge(InventoryPlayer inv, LOTRTileEntityAlloyForgeBase forge) {
        int i;
        this.theForge = forge;
        for(i = 0; i < 4; ++i) {
            this.addSlotToContainer(new Slot(forge, i, 53 + i * 18, 21));
        }
        for(i = 0; i < 4; ++i) {
            this.addSlotToContainer(new Slot(forge, i + 4, 53 + i * 18, 39));
        }
        for(i = 0; i < 4; ++i) {
            this.addSlotToContainer(new SlotFurnace(inv.player, forge, i + 8, 53 + i * 18, 85));
        }
        this.addSlotToContainer(new Slot(forge, 12, 80, 129));
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 209));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.theForge.currentSmeltTime);
        crafting.sendProgressBarUpdate(this, 1, this.theForge.forgeSmeltTime);
        crafting.sendProgressBarUpdate(this, 2, this.theForge.currentItemFuelValue);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(Object element : this.crafters) {
            ICrafting crafting = (ICrafting) element;
            if(this.currentSmeltTime != this.theForge.currentSmeltTime) {
                crafting.sendProgressBarUpdate(this, 0, this.theForge.currentSmeltTime);
            }
            if(this.forgeSmeltTime != this.theForge.forgeSmeltTime) {
                crafting.sendProgressBarUpdate(this, 1, this.theForge.forgeSmeltTime);
            }
            if(this.currentItemFuelValue == this.theForge.currentItemFuelValue) continue;
            crafting.sendProgressBarUpdate(this, 2, this.theForge.currentItemFuelValue);
        }
        this.currentSmeltTime = this.theForge.currentSmeltTime;
        this.forgeSmeltTime = this.theForge.forgeSmeltTime;
        this.currentItemFuelValue = this.theForge.currentItemFuelValue;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void updateProgressBar(int i, int j) {
        if(i == 0) {
            this.theForge.currentSmeltTime = j;
        }
        if(i == 1) {
            this.theForge.forgeSmeltTime = j;
        }
        if(i == 2) {
            this.theForge.currentItemFuelValue = j;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.theForge.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i >= 8 && i < 12) {
                if(!this.mergeItemStack(itemstack1, 13, 49, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(i >= 8 && i != 12 ? (this.theForge.getSmeltingResult(itemstack1) != null ? !this.mergeItemStack(itemstack1, 4, 8, false) : (TileEntityFurnace.isItemFuel(itemstack1) ? !this.mergeItemStack(itemstack1, 12, 13, false) : (i >= 13 && i < 40 ? !this.mergeItemStack(itemstack1, 40, 49, false) : i >= 40 && i < 49 && !this.mergeItemStack(itemstack1, 13, 40, false)))) : !this.mergeItemStack(itemstack1, 13, 49, false)) {
                return null;
            }
            if(itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if(itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack1);
        }
        return itemstack;
    }
}
