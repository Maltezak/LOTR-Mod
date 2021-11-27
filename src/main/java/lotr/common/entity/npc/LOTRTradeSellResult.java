package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;

public class LOTRTradeSellResult {
    public final int tradeIndex;
    public final int tradeValue;
    public final int tradeStackSize;
    public final int tradesMade;
    public final int itemsSold;
    public final int totalSellValue;

    public LOTRTradeSellResult(int index, LOTRTradeEntry trade, ItemStack sellItem) {
        ItemStack tradeItem = trade.createTradeItem();
        this.tradeIndex = index;
        this.tradeValue = trade.getCost();
        this.tradeStackSize = tradeItem.stackSize;
        this.tradesMade = !trade.isAvailable() ? 0 : sellItem.stackSize / this.tradeStackSize;
        this.itemsSold = this.tradesMade * tradeItem.stackSize;
        this.totalSellValue = this.tradesMade * this.tradeValue;
    }
}
