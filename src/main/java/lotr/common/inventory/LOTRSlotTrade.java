package lotr.common.inventory;

import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRSlotTrade
extends LOTRSlotProtected {
    private LOTRContainerTrade theContainer;
    private LOTREntityNPC theEntity;
    private LOTRTradeEntries.TradeType tradeType;

    public LOTRSlotTrade(LOTRContainerTrade container, IInventory inv, int i, int j, int k, LOTREntityNPC entity, LOTRTradeEntries.TradeType type) {
        super(inv, i, j, k);
        this.theContainer = container;
        this.theEntity = entity;
        this.tradeType = type;
    }

    public int cost() {
        LOTRTradeEntry trade = this.getTrade();
        return trade == null ? 0 : trade.getCost();
    }

    public LOTRTradeEntry getTrade() {
        LOTRTradeEntry[] trades = null;
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            trades = this.theEntity.traderNPCInfo.getBuyTrades();
        } else if (this.tradeType == LOTRTradeEntries.TradeType.SELL) {
            trades = this.theEntity.traderNPCInfo.getSellTrades();
        }
        if (trades == null) {
            return null;
        }
        int i = this.getSlotIndex();
        if (i >= 0 && i < trades.length) {
            return trades[i];
        }
        return null;
    }

    public boolean canTakeStack(EntityPlayer entityplayer) {
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            if (this.getTrade() != null && !this.getTrade().isAvailable()) {
                return false;
            }
            int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
            if (coins < this.cost()) {
                return false;
            }
        }
        if (this.tradeType == LOTRTradeEntries.TradeType.SELL) {
            return false;
        }
        return super.canTakeStack(entityplayer);
    }

    public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY && !entityplayer.worldObj.isRemote) {
            LOTRItemCoin.takeCoins(this.cost(), entityplayer);
        }
        super.onPickupFromSlot(entityplayer, itemstack);
        if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
            LOTRTradeEntry trade = this.getTrade();
            if (!entityplayer.worldObj.isRemote && trade != null) {
                this.putStack(trade.createTradeItem());
                ((EntityPlayerMP)entityplayer).sendContainerToPlayer(this.theContainer);
                this.theEntity.traderNPCInfo.onTrade(entityplayer, trade, LOTRTradeEntries.TradeType.BUY, this.cost());
                this.theEntity.playTradeSound();
            }
        }
    }
}

