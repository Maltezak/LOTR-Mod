package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerUnsmeltery extends Container {
    private LOTRTileEntityUnsmeltery theUnsmeltery;
    private int currentSmeltTime = 0;
    private int forgeSmeltTime = 0;
    private int currentItemFuelValue = 0;

    public LOTRContainerUnsmeltery(InventoryPlayer inv, LOTRTileEntityUnsmeltery unsmeltery) {
        int i;
        this.theUnsmeltery = unsmeltery;
        this.addSlotToContainer(new Slot(unsmeltery, 0, 56, 17));
        this.addSlotToContainer(new Slot(unsmeltery, 1, 56, 53));
        this.addSlotToContainer(new LOTRSlotUnsmeltResult(unsmeltery, 2, 116, 35));
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.theUnsmeltery.currentSmeltTime);
        crafting.sendProgressBarUpdate(this, 1, this.theUnsmeltery.forgeSmeltTime);
        crafting.sendProgressBarUpdate(this, 2, this.theUnsmeltery.currentItemFuelValue);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(Object element : this.crafters) {
            ICrafting crafting = (ICrafting) element;
            if(this.currentSmeltTime != this.theUnsmeltery.currentSmeltTime) {
                crafting.sendProgressBarUpdate(this, 0, this.theUnsmeltery.currentSmeltTime);
            }
            if(this.forgeSmeltTime != this.theUnsmeltery.forgeSmeltTime) {
                crafting.sendProgressBarUpdate(this, 1, this.theUnsmeltery.forgeSmeltTime);
            }
            if(this.currentItemFuelValue == this.theUnsmeltery.currentItemFuelValue) continue;
            crafting.sendProgressBarUpdate(this, 2, this.theUnsmeltery.currentItemFuelValue);
        }
        this.currentSmeltTime = this.theUnsmeltery.currentSmeltTime;
        this.forgeSmeltTime = this.theUnsmeltery.forgeSmeltTime;
        this.currentItemFuelValue = this.theUnsmeltery.currentItemFuelValue;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void updateProgressBar(int i, int j) {
        if(i == 0) {
            this.theUnsmeltery.currentSmeltTime = j;
        }
        if(i == 1) {
            this.theUnsmeltery.forgeSmeltTime = j;
        }
        if(i == 2) {
            this.theUnsmeltery.currentItemFuelValue = j;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.theUnsmeltery.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i == 2) {
                if(!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(i != 1 && i != 0 ? (this.theUnsmeltery.canBeUnsmelted(itemstack1) ? !this.mergeItemStack(itemstack1, 0, 1, false) : (TileEntityFurnace.isItemFuel(itemstack1) ? !this.mergeItemStack(itemstack1, 1, 2, false) : (i >= 3 && i < 30 ? !this.mergeItemStack(itemstack1, 30, 39, false) : i >= 30 && i < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)))) : !this.mergeItemStack(itemstack1, 3, 39, false)) {
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
