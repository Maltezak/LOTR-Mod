package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface LOTRTradeable {
    String getNPCName();

    LOTRFaction getFaction();

    LOTRTradeEntries getBuyPool();

    LOTRTradeEntries getSellPool();

    boolean canTradeWith(EntityPlayer var1);

    void onPlayerTrade(EntityPlayer var1, LOTRTradeEntries.TradeType var2, ItemStack var3);

    boolean shouldTraderRespawn();

    public interface Smith extends LOTRTradeable {
    }

    public interface Bartender extends LOTRTradeable {
    }

}
