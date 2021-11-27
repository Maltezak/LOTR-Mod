package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellSmith extends LOTREntityRivendellElf implements LOTRTradeable.Smith {
    public LOTREntityRivendellSmith(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RIVENDELL_SMITH_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RIVENDELL_SMITH_SELL;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        if(type == LOTRTradeEntries.TradeType.BUY) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellSmith);
        }
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public boolean shouldRenderNPCHair() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "rivendell/smith/friendly";
            }
            return "rivendell/smith/neutral";
        }
        return "rivendell/smith/hostile";
    }
}
