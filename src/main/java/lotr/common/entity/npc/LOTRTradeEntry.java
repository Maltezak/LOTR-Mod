package lotr.common.entity.npc;

import lotr.common.item.LOTRItemMug;
import lotr.common.quest.IPickpocketable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class LOTRTradeEntry {
    private final ItemStack tradeItem;
    private int tradeCost;
    private int recentTradeValue;
    private int lockedTicks;
    private LOTRTraderNPCInfo theTrader;

    public LOTRTradeEntry(ItemStack itemstack, int cost) {
        this.tradeItem = itemstack;
        this.tradeCost = cost;
    }

    public ItemStack createTradeItem() {
        return this.tradeItem.copy();
    }

    public int getCost() {
        return this.tradeCost;
    }

    public void setCost(int cost) {
        this.tradeCost = cost;
    }

    public void setOwningTrader(LOTRTraderNPCInfo trader) {
        if (this.theTrader != null) {
            throw new IllegalArgumentException("Cannot assign already-owned trade entry to a different trader!");
        }
        this.theTrader = trader;
    }

    public boolean isAvailable() {
        if (this.theTrader != null && this.theTrader.shouldLockTrades()) {
            return this.recentTradeValue < this.theTrader.getLockTradeAtValue() && this.lockedTicks <= 0;
        }
        return true;
    }

    public float getLockedProgress() {
        if (this.theTrader != null && this.theTrader.shouldLockTrades()) {
            return (float)this.recentTradeValue / (float)this.theTrader.getLockTradeAtValue();
        }
        return 0.0f;
    }

    private int getLockedProgressInt(int i) {
        float f = this.getLockedProgress();
        return Math.round(f * i);
    }

    public int getLockedProgressForSlot() {
        return this.getLockedProgressInt(16);
    }

    public boolean updateAvailability(int tick) {
        boolean prevAvailable = this.isAvailable();
        int prevLockProgress = this.getLockedProgressForSlot();
        if (tick % this.theTrader.getValueDecayTicks() == 0 && this.recentTradeValue > 0) {
            --this.recentTradeValue;
        }
        if (this.lockedTicks > 0) {
            --this.lockedTicks;
        }
        if (this.isAvailable() != prevAvailable) {
            return true;
        }
        return this.getLockedProgressForSlot() != prevLockProgress;
    }

    public boolean matches(ItemStack itemstack) {
        if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
            return false;
        }
        ItemStack tradeCreated = this.createTradeItem();
        if (LOTRItemMug.isItemFullDrink(tradeCreated)) {
            ItemStack tradeDrink = LOTRItemMug.getEquivalentDrink(tradeCreated);
            ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
            return tradeDrink.getItem() == offerDrink.getItem();
        }
        return OreDictionary.itemMatches(tradeCreated, itemstack, false);
    }

    public void doTransaction(int value) {
        this.recentTradeValue += value;
    }

    public void setLockedForTicks(int ticks) {
        this.lockedTicks = ticks;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        this.tradeItem.writeToNBT(nbt);
        nbt.setInteger("Cost", this.tradeCost);
        nbt.setInteger("RecentTradeValue", this.recentTradeValue);
        nbt.setInteger("LockedTicks", this.lockedTicks);
    }

    public static LOTRTradeEntry readFromNBT(NBTTagCompound nbt) {
        ItemStack savedItem = ItemStack.loadItemStackFromNBT(nbt);
        if (savedItem != null) {
            int cost = nbt.getInteger("Cost");
            LOTRTradeEntry trade = new LOTRTradeEntry(savedItem, cost);
            if (nbt.hasKey("RecentTradeValue")) {
                trade.recentTradeValue = nbt.getInteger("RecentTradeValue");
            }
            trade.lockedTicks = nbt.getInteger("LockedTicks");
            return trade;
        }
        return null;
    }
}

