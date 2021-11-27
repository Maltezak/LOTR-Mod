package lotr.common.inventory;

import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerBookshelf extends Container {
    public LOTRTileEntityBookshelf shelfInv;
    private int numRows;

    public LOTRContainerBookshelf(IInventory player, LOTRTileEntityBookshelf shelf) {
        int j;
        int i;
        this.shelfInv = shelf;
        this.numRows = this.shelfInv.getSizeInventory() / 9;
        this.shelfInv.openInventory();
        int playerSlotY = (this.numRows - 4) * 18;
        for(j = 0; j < this.numRows; ++j) {
            for(i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(this.shelfInv, i + j * 9, 8 + i * 18, 18 + j * 18) {

                    @Override
                    public boolean isItemValid(ItemStack itemstack) {
                        return LOTRTileEntityBookshelf.isBookItem(itemstack);
                    }
                });
            }
        }
        for(j = 0; j < 3; ++j) {
            for(i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(player, i + j * 9 + 9, 8 + i * 18, 103 + j * 18 + playerSlotY));
            }
        }
        for(int i2 = 0; i2 < 9; ++i2) {
            this.addSlotToContainer(new Slot(player, i2, 8 + i2 * 18, 161 + playerSlotY));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.shelfInv.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i < this.numRows * 9) {
                if(!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if(LOTRTileEntityBookshelf.isBookItem(itemstack)) {
                if(!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
                    return null;
                }
            }
            else {
                return null;
            }
            if(itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        boolean anyContents = false;
        for(int i = 0; i < this.shelfInv.getSizeInventory(); ++i) {
            if(this.shelfInv.getStackInSlot(i) == null) continue;
            anyContents = true;
            break;
        }
        super.onContainerClosed(entityplayer);
        this.shelfInv.closeInventory();
        if(!anyContents && this.shelfInv.numPlayersUsing <= 0) {
            World world = this.shelfInv.getWorldObj();
            if(!world.isRemote) {
                world.setBlock(this.shelfInv.xCoord, this.shelfInv.yCoord, this.shelfInv.zCoord, Blocks.bookshelf, 0, 3);
            }
        }
    }

}
