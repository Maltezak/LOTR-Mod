package lotr.common.inventory;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRSlotMillstone extends Slot {
    private EntityPlayer thePlayer;
    private int itemsTaken;

    public LOTRSlotMillstone(EntityPlayer entityplayer, IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
        this.thePlayer = entityplayer;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int i) {
        if(this.getHasStack()) {
            this.itemsTaken += Math.min(i, this.getStack().stackSize);
        }
        return super.decrStackSize(i);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
        this.onCrafting(itemstack);
        super.onPickupFromSlot(entityplayer, itemstack);
    }

    @Override
    protected void onCrafting(ItemStack itemstack, int i) {
        this.itemsTaken += i;
        this.onCrafting(itemstack);
    }

    @Override
    protected void onCrafting(ItemStack itemstack) {
        itemstack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.itemsTaken);
        this.itemsTaken = 0;
        if(!this.thePlayer.worldObj.isRemote && itemstack.getItem() == LOTRMod.obsidianShard) {
            LOTRLevelData.getData(this.thePlayer).addAchievement(LOTRAchievement.smeltObsidianShard);
        }
    }
}
