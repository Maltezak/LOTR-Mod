package lotr.common.inventory;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerPouch
extends Container {
    private final int thePouchSlot;
    private ItemStack thePouchItem;
    public LOTRInventoryPouch pouchInventory;
    public int capacity;

    public LOTRContainerPouch(EntityPlayer entityplayer, int slot) {
        int i;
        int j;
        this.thePouchSlot = slot;
        this.thePouchItem = entityplayer.inventory.getStackInSlot(this.thePouchSlot);
        this.pouchInventory = new LOTRInventoryPouch(entityplayer, this, slot);
        this.capacity = this.pouchInventory.getSizeInventory();
        int rows = this.capacity / 9;
        for (i = 0; i < rows; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlotToContainer(new LOTRSlotPouch(this.pouchInventory, j + i * 9, 8 + j * 18, 30 + i * 18));
            }
        }
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, 156));
        }
    }

    public String getDisplayName() {
        return this.pouchInventory.getInventoryName();
    }

    public void renamePouch(String name) {
        if (StringUtils.isBlank(name)) {
            this.pouchInventory.getPouchItem().func_135074_t();
        } else {
            this.pouchInventory.getPouchItem().setStackDisplayName(name);
        }
        this.syncPouchItem(this.pouchInventory.getPouchItem());
    }

    public void syncPouchItem(ItemStack itemstack) {
        this.thePouchItem = itemstack;
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return ItemStack.areItemStacksEqual(this.thePouchItem, this.pouchInventory.getPouchItem());
    }

    public ItemStack slotClick(int slotNo, int subActionNo, int actionNo, EntityPlayer entityplayer) {
        if (LOTRContainerPouch.isPouchSlot(this, slotNo, entityplayer, this.thePouchSlot)) {
            return null;
        }
        if (actionNo == 2 && subActionNo == this.thePouchSlot) {
            return null;
        }
        return super.slotClick(slotNo, subActionNo, actionNo, entityplayer);
    }

    public static boolean isPouchSlot(Container container, int slotNo, EntityPlayer entityplayer, int pouchSlotNo) {
        if (slotNo >= 0 && slotNo < container.inventorySlots.size()) {
            Slot slot = (Slot)container.inventorySlots.get(slotNo);
            if (slot.inventory == entityplayer.inventory && slot.getSlotIndex() == pouchSlotNo) {
                return true;
            }
        }
        return false;
    }

    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);
        Slot aPouchSlot = this.getSlotFromInventory(this.pouchInventory, 0);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < this.capacity ? !this.mergeItemStack(itemstack1, this.capacity, this.capacity + 36, true) : aPouchSlot.isItemValid(itemstack1) && !this.mergeItemStack(itemstack1, 0, this.capacity, false)) {
                return null;
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

