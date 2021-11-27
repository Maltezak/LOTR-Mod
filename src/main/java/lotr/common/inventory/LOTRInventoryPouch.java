package lotr.common.inventory;

import lotr.common.item.LOTRItemPouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class LOTRInventoryPouch
extends InventoryBasic {
    private LOTRContainerPouch theContainer;
    private EntityPlayer thePlayer;
    private int playerSlot;
    private boolean isTemporary;
    private ItemStack tempPouchItem;

    public LOTRInventoryPouch(EntityPlayer entityplayer, LOTRContainerPouch container, int slot) {
        super(entityplayer.inventory.getStackInSlot(slot).getDisplayName(), true, LOTRItemPouch.getCapacity(entityplayer.inventory.getStackInSlot(slot)));
        this.isTemporary = false;
        this.thePlayer = entityplayer;
        this.theContainer = container;
        this.playerSlot = slot;
        if (!this.thePlayer.worldObj.isRemote) {
            this.loadPouchContents();
        }
    }

    public LOTRInventoryPouch(ItemStack itemstack) {
        super("tempPouch", true, LOTRItemPouch.getCapacity(itemstack));
        this.isTemporary = true;
        this.tempPouchItem = itemstack;
        this.loadPouchContents();
    }

    public ItemStack getPouchItem() {
        if (this.isTemporary) {
            return this.tempPouchItem;
        }
        return this.thePlayer.inventory.getStackInSlot(this.playerSlot);
    }

    public String getInventoryName() {
        return this.getPouchItem().getDisplayName();
    }

    public void markDirty() {
        super.markDirty();
        if (this.isTemporary || !this.thePlayer.worldObj.isRemote) {
            this.savePouchContents();
        }
    }

    private void loadPouchContents() {
        if (this.getPouchItem().hasTagCompound() && this.getPouchItem().getTagCompound().hasKey("LOTRPouchData")) {
            NBTTagCompound nbt = this.getPouchItem().getTagCompound().getCompoundTag("LOTRPouchData");
            NBTTagList items = nbt.getTagList("Items", 10);
            for (int i = 0; i < items.tagCount(); ++i) {
                NBTTagCompound itemData = items.getCompoundTagAt(i);
                byte slot = itemData.getByte("Slot");
                if (slot < 0 || slot >= this.getSizeInventory()) continue;
                this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
            }
        }
        if (!this.isTemporary) {
            this.theContainer.syncPouchItem(this.getPouchItem());
        }
    }

    private void savePouchContents() {
        if (!this.getPouchItem().hasTagCompound()) {
            this.getPouchItem().setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte)i);
            itemstack.writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
        this.getPouchItem().getTagCompound().setTag("LOTRPouchData", nbt);
        if (!this.isTemporary) {
            this.theContainer.syncPouchItem(this.getPouchItem());
        }
    }
}

