package lotr.common.quest;

import java.util.*;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public abstract class LOTRMiniQuestCollectBase
extends LOTRMiniQuest {
    public int collectTarget;
    public int amountGiven;

    public LOTRMiniQuestCollectBase(LOTRPlayerData pd) {
        super(pd);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Target", this.collectTarget);
        nbt.setInteger("Given", this.amountGiven);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.collectTarget = nbt.getInteger("Target");
        this.amountGiven = nbt.getInteger("Given");
    }

    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.collectTarget > 0;
    }

    @Override
    public String getQuestProgress() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.collect.progress", this.amountGiven, this.collectTarget);
    }

    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", this.amountGiven, this.collectTarget);
    }

    @Override
    public float getCompletionFactor() {
        return (float)this.amountGiven / (float)this.collectTarget;
    }

    @Override
    public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
        int prevAmountGiven = this.amountGiven;
        ArrayList<Integer> slotNumbers = new ArrayList<>();
        slotNumbers.add(entityplayer.inventory.currentItem);
        for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
            if (slotNumbers.contains(slot)) continue;
            slotNumbers.add(slot);
        }
        Iterator slot = slotNumbers.iterator();
        while (slot.hasNext()) {
            int slot2 = (Integer)slot.next();
            ItemStack itemstack = entityplayer.inventory.mainInventory[slot2];
            if (itemstack != null && this.isQuestItem(itemstack)) {
                int amountRemaining = this.collectTarget - this.amountGiven;
                if (itemstack.stackSize >= amountRemaining) {
                    itemstack.stackSize -= amountRemaining;
                    if (itemstack.stackSize <= 0) {
                        itemstack = null;
                    }
                    entityplayer.inventory.setInventorySlotContents(slot2, itemstack);
                    this.amountGiven += amountRemaining;
                } else {
                    this.amountGiven += itemstack.stackSize;
                    entityplayer.inventory.setInventorySlotContents(slot2, null);
                }
            }
            if (this.amountGiven < this.collectTarget) continue;
            this.complete(entityplayer, npc);
            break;
        }
        if (this.amountGiven > prevAmountGiven && !this.isCompleted()) {
            this.updateQuest();
        }
        if (!this.isCompleted()) {
            this.sendProgressSpeechbank(entityplayer, npc);
        }
    }

    protected abstract boolean isQuestItem(ItemStack var1);

    @Override
    public float getAlignmentBonus() {
        float f = this.collectTarget;
        return Math.max(f *= this.rewardFactor, 1.0f);
    }

    @Override
    public int getCoinBonus() {
        return Math.round(this.getAlignmentBonus() * 2.0f);
    }
}

