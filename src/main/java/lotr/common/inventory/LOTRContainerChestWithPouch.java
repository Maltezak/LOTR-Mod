package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerChestWithPouch
extends ContainerChest {
    public IInventory chestInv;
    public LOTRContainerPouch pouchContainer;
    private final int thePouchSlot;
    private int numChestRows;
    private int numPouchRows;

    public LOTRContainerChestWithPouch(EntityPlayer entityplayer, int pouchSlot, IInventory chest) {
        super(entityplayer.inventory, chest);
        int i;
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();
        this.chestInv = chest;
        this.numChestRows = chest.getSizeInventory() / 9;
        this.thePouchSlot = pouchSlot;
        this.pouchContainer = new LOTRContainerPouch(entityplayer, this.thePouchSlot);
        this.numPouchRows = this.pouchContainer.capacity / 9;
        for (int j = 0; j < this.numChestRows; ++j) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.addSlotToContainer(new Slot(chest, i2 + j * 9, 8 + i2 * 18, 18 + j * 18));
            }
        }
        int pouchSlotsY = 103 + (this.numChestRows - 4) * 18;
        for (int j = 0; j < this.numPouchRows; ++j) {
            for (i = 0; i < 9; ++i) {
                int pouchSlotID = i + j * 9;
                Slot slot = this.pouchContainer.getSlotFromInventory(this.pouchContainer.pouchInventory, pouchSlotID);
                slot.xDisplayPosition = 8 + i * 18;
                slot.yDisplayPosition = pouchSlotsY + j * 18;
                this.addSlotToContainer(slot);
            }
        }
        int playerSlotsY = pouchSlotsY + 67;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, playerSlotsY + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, playerSlotsY + 58));
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.chestInv.isUseableByPlayer(entityplayer) && this.pouchContainer.canInteractWith(entityplayer);
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

    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);
        Slot aPouchSlot = this.getSlotFromInventory(this.pouchContainer.pouchInventory, 0);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < this.numChestRows * 9 ? aPouchSlot.isItemValid(itemstack1) && !this.mergeItemStack(itemstack1, this.numChestRows * 9, (this.numChestRows + this.numPouchRows) * 9, true) : (i < (this.numChestRows + this.numPouchRows) * 9 ? !this.mergeItemStack(itemstack1, 0, this.numChestRows * 9, false) : !this.mergeItemStack(itemstack1, 0, this.numChestRows * 9, false))) {
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

    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
    }
}

