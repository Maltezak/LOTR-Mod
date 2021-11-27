package lotr.common.inventory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class LOTREntityInventory extends InventoryBasic {
    protected EntityLivingBase theEntity;
    private String nbtName;

    public LOTREntityInventory(String s, EntityLivingBase npc, int i) {
        super(s, true, i);
        this.theEntity = npc;
        this.nbtName = s;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            itemstack.writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag(this.nbtName, items);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList items = nbt.getTagList(this.nbtName, 10);
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte slot = itemData.getByte("Slot");
            if(slot < 0 || slot >= this.getSizeInventory()) continue;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
        }
    }

    public void dropAllItems() {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack == null) continue;
            this.dropItem(itemstack);
            this.setInventorySlotContents(i, null);
        }
    }

    protected void dropItem(ItemStack itemstack) {
        this.theEntity.entityDropItem(itemstack, 0.0f);
    }

    public boolean isEmpty() {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            if(this.getStackInSlot(i) == null) continue;
            return false;
        }
        return true;
    }

    public boolean isFull() {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            if(this.getStackInSlot(i) != null) continue;
            return false;
        }
        return true;
    }

    public boolean addItemToInventory(ItemStack itemstack) {
        int origStack = itemstack.stackSize;
        if(itemstack != null && itemstack.stackSize > 0) {
            for(int i = 0; i < this.getSizeInventory() && itemstack.stackSize > 0; ++i) {
                ItemStack itemInSlot = this.getStackInSlot(i);
                if(itemInSlot != null && (itemInSlot.stackSize >= itemInSlot.getMaxStackSize() || !itemstack.isItemEqual(itemInSlot) || !ItemStack.areItemStackTagsEqual(itemInSlot, itemstack))) continue;
                if(itemInSlot == null) {
                    ItemStack copy = itemstack.copy();
                    copy.stackSize = Math.min(copy.stackSize, this.getInventoryStackLimit());
                    this.setInventorySlotContents(i, copy);
                    itemstack.stackSize -= copy.stackSize;
                    continue;
                }
                int maxStackSize = itemInSlot.getMaxStackSize();
                maxStackSize = Math.min(maxStackSize, this.getInventoryStackLimit());
                int difference = maxStackSize - itemInSlot.stackSize;
                difference = Math.min(difference, itemstack.stackSize);
                itemstack.stackSize -= difference;
                itemInSlot.stackSize += difference;
                this.setInventorySlotContents(i, itemInSlot);
            }
        }
        return itemstack != null && itemstack.stackSize < origStack;
    }
}
