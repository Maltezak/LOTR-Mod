package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class LOTRInventoryGollum implements IInventory {
    private ItemStack[] inventory = new ItemStack[9];
    private LOTREntityGollum theGollum;

    public LOTRInventoryGollum(LOTREntityGollum gollum) {
        this.theGollum = gollum;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(this.inventory[i] != null) {
            if(this.inventory[i].stackSize <= j) {
                ItemStack itemstack = this.inventory[i];
                this.inventory[i] = null;
                this.markDirty();
                return itemstack;
            }
            ItemStack itemstack = this.inventory[i].splitStack(j);
            if(this.inventory[i].stackSize == 0) {
                this.inventory[i] = null;
            }
            this.markDirty();
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if(this.inventory[i] != null) {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.inventory[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public String getInventoryName() {
        return "Gollum";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte byte0 = itemData.getByte("Slot");
            if(byte0 < 0 || byte0 >= this.inventory.length) continue;
            this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.length; ++i) {
            if(this.inventory[i] == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            this.inventory[i].writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
    }

    public void dropAllItems() {
        for(int i = 0; i < this.inventory.length; ++i) {
            if(this.inventory[i] == null) continue;
            this.theGollum.entityDropItem(this.inventory[i], 0.0f);
            this.inventory[i] = null;
        }
    }
}
