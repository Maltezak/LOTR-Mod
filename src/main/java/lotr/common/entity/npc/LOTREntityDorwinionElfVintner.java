package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfVintner extends LOTREntityDorwinionElf implements LOTRTradeable {
    public LOTREntityDorwinionElfVintner(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DORWINION_VINTNER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DORWINION_VINTNER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        ItemStack drink = this.getBuyPool().getRandomTrades(this.rand)[0].createTradeItem();
        this.npcItemsInv.setIdleItem(drink);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        if(type == LOTRTradeEntries.TradeType.BUY) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyWineVintner);
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
                return "dorwinion/elfVintner/friendly";
            }
            return "dorwinion/elfVintner/neutral";
        }
        return "dorwinion/elf/hostile";
    }
}
