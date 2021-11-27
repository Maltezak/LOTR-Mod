package lotr.common.inventory;

import lotr.common.entity.npc.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerTrade extends Container {
    public IInventory tradeInvBuy = new InventoryBasic("trade", false, 9);
    public IInventory tradeInvSell = new InventoryBasic("trade", false, 9);
    public IInventory tradeInvSellOffer = new InventoryBasic("trade", false, 9);
    public LOTRTradeable theTrader;
    public LOTREntityNPC theTraderNPC;
    private World theWorld;

    public LOTRContainerTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
        int i;
        this.theTrader = trader;
        this.theTraderNPC = (LOTREntityNPC) (trader);
        this.theWorld = world;
        if(!world.isRemote) {
            this.updateAllTradeSlots();
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new LOTRSlotTrade(this, this.tradeInvBuy, i, 8 + i * 18, 40, this.theTraderNPC, LOTRTradeEntries.TradeType.BUY));
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new LOTRSlotTrade(this, this.tradeInvSell, i, 8 + i * 18, 92, this.theTraderNPC, LOTRTradeEntries.TradeType.SELL));
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(this.tradeInvSellOffer, i, 8 + i * 18, 141));
        }
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 246));
        }
    }

    public void updateAllTradeSlots() {
        LOTRTradeEntry trade;
        int i;
        LOTRTradeEntry[] buyTrades = this.theTraderNPC.traderNPCInfo.getBuyTrades();
        LOTRTradeEntry[] sellTrades = this.theTraderNPC.traderNPCInfo.getSellTrades();
        if(buyTrades != null) {
            for(i = 0; i < this.tradeInvBuy.getSizeInventory(); ++i) {
                trade = null;
                if(i < buyTrades.length) {
                    trade = buyTrades[i];
                }
                if(trade != null) {
                    this.tradeInvBuy.setInventorySlotContents(i, trade.createTradeItem());
                    continue;
                }
                this.tradeInvBuy.setInventorySlotContents(i, null);
            }
        }
        if(sellTrades != null) {
            for(i = 0; i < this.tradeInvSell.getSizeInventory(); ++i) {
                trade = null;
                if(i < sellTrades.length) {
                    trade = sellTrades[i];
                }
                if(trade != null) {
                    this.tradeInvSell.setInventorySlotContents(i, trade.createTradeItem());
                    continue;
                }
                this.tradeInvSell.setInventorySlotContents(i, null);
            }
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        this.theTraderNPC.traderNPCInfo.sendClientPacket((EntityPlayer) (crafting));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.theTraderNPC != null && entityplayer.getDistanceToEntity(this.theTraderNPC) <= 12.0 && this.theTraderNPC.isEntityAlive() && this.theTraderNPC.getAttackTarget() == null && this.theTrader.canTradeWith(entityplayer);
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if(!this.theWorld.isRemote) {
            for(int i = 0; i < this.tradeInvSellOffer.getSizeInventory(); ++i) {
                ItemStack itemstack = this.tradeInvSellOffer.getStackInSlotOnClosing(i);
                if(itemstack == null) continue;
                entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if(slot != null && slot.getHasStack()) {
            boolean sellable;
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack1, this.theTraderNPC);
            sellable = sellResult != null && sellResult.tradesMade > 0;
            if(i < 9) {
                if(!this.mergeItemStack(itemstack1, 27, 63, true)) {
                    return null;
                }
            }
            else {
                if(i >= 9 && i < 18) {
                    return null;
                }
                if(i >= 18 && i < 27 ? !this.mergeItemStack(itemstack1, 27, 63, true) : (sellable ? !this.mergeItemStack(itemstack1, 18, 27, false) : (i >= 27 && i < 54 ? !this.mergeItemStack(itemstack1, 54, 63, false) : i >= 54 && i < 63 && !this.mergeItemStack(itemstack1, 27, 54, false)))) {
                    return null;
                }
            }
            if(itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if(itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack1);
        }
        return itemstack;
    }
}
