package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTREntityBreeHobbitTrader
extends LOTREntityBreeHobbit
implements LOTRTradeable {
    public LOTREntityBreeHobbitTrader(World world) {
        super(world);
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendlyAndAligned(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBreeMarketTrader);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            return "bree/marketTrader/hobbit/friendly";
        }
        return "bree/marketTrader/hobbit/hostile";
    }
}

