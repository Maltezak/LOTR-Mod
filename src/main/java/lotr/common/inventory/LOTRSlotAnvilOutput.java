package lotr.common.inventory;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRSlotAnvilOutput
extends Slot {
    private LOTRContainerAnvil theAnvil;

    public LOTRSlotAnvilOutput(LOTRContainerAnvil container, IInventory inv, int id, int i, int j) {
        super(inv, id, i, j);
        this.theAnvil = container;
    }

    public boolean isItemValid(ItemStack itemstack) {
        return false;
    }

    public boolean canTakeStack(EntityPlayer entityplayer) {
        if (this.getHasStack()) {
            if (this.theAnvil.materialCost > 0) {
                return this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.materialCost);
            }
            return true;
        }
        return false;
    }

    public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
        int materials = this.theAnvil.materialCost;
        boolean wasSmithCombine = this.theAnvil.isSmithScrollCombine;
        this.theAnvil.invInput.setInventorySlotContents(0, null);
        ItemStack combinerItem = this.theAnvil.invInput.getStackInSlot(1);
        if (combinerItem != null) {
            --combinerItem.stackSize;
            if (combinerItem.stackSize <= 0) {
                this.theAnvil.invInput.setInventorySlotContents(1, null);
            } else {
                this.theAnvil.invInput.setInventorySlotContents(1, combinerItem);
            }
        }
        if (materials > 0) {
            this.theAnvil.takeMaterialOrCoinAmount(materials);
        }
        if (!entityplayer.worldObj.isRemote && wasSmithCombine) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.combineSmithScrolls);
        }
        this.theAnvil.materialCost = 0;
        this.theAnvil.isSmithScrollCombine = false;
        this.theAnvil.playAnvilSound();
    }
}

