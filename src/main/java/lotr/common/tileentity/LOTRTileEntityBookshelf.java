package lotr.common.tileentity;

import java.util.List;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBookshelfStorage;
import lotr.common.inventory.LOTRContainerBookshelf;
import lotr.common.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityBookshelf extends TileEntity implements IInventory {
    private ItemStack[] chestContents = new ItemStack[this.getSizeInventory()];
    public int numPlayersUsing;
    private int ticksSinceSync;

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.chestContents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(this.chestContents[i] != null) {
            if(this.chestContents[i].stackSize <= j) {
                ItemStack itemstack = this.chestContents[i];
                this.chestContents[i] = null;
                this.markDirty();
                return itemstack;
            }
            ItemStack itemstack = this.chestContents[i].splitStack(j);
            if(this.chestContents[i].stackSize == 0) {
                this.chestContents[i] = null;
            }
            this.markDirty();
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if(this.chestContents[i] != null) {
            ItemStack itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.chestContents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return "container.lotr.bookshelf";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList itemTags = nbt.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < itemTags.tagCount(); ++i) {
            NBTTagCompound slotData = itemTags.getCompoundTagAt(i);
            int slot = slotData.getByte("Slot") & 0xFF;
            if(slot < 0 || slot >= this.chestContents.length) continue;
            this.chestContents[slot] = ItemStack.loadItemStackFromNBT(slotData);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList itemTags = new NBTTagList();
        for(int i = 0; i < this.chestContents.length; ++i) {
            if(this.chestContents[i] == null) continue;
            NBTTagCompound slotData = new NBTTagCompound();
            slotData.setByte("Slot", (byte) i);
            this.chestContents[i].writeToNBT(slotData);
            itemTags.appendTag(slotData);
        }
        nbt.setTag("Items", itemTags);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        ++this.ticksSinceSync;
        if(!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            float range = 16.0f;
            List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - range, this.yCoord - range, this.zCoord - range, this.xCoord + 1 + range, this.yCoord + 1 + range, this.zCoord + 1 + range));
            for(Object obj : players) {
                EntityPlayer entityplayer = (EntityPlayer) obj;
                if(!(entityplayer.openContainer instanceof LOTRContainerBookshelf) || (((LOTRContainerBookshelf) entityplayer.openContainer).shelfInv) != this) continue;
                ++this.numPlayersUsing;
            }
        }
    }

    @Override
    public void openInventory() {
        if(this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
    }

    @Override
    public void closeInventory() {
        if(this.getBlockType() instanceof LOTRBlockBookshelfStorage) {
            --this.numPlayersUsing;
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return LOTRTileEntityBookshelf.isBookItem(itemstack);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }

    public static boolean isBookItem(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof ItemBook || item instanceof ItemWritableBook || item instanceof ItemEditableBook) {
                return true;
            }
            if(item instanceof LOTRItemRedBook || item == LOTRMod.mithrilBook) {
                return true;
            }
            if(item instanceof ItemEnchantedBook) {
                return true;
            }
            if(item instanceof ItemMapBase) {
                return true;
            }
            if(item == Items.paper) {
                return true;
            }
            if(item instanceof LOTRItemModifierTemplate) {
                return true;
            }
        }
        return false;
    }
}
