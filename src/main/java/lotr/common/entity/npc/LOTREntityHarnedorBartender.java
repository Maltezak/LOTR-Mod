package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHarnedorBartender extends LOTREntityHarnedhrim implements LOTRTradeable.Bartender {
    public LOTREntityHarnedorBartender(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcLocationName = "entity.lotr.HarnedorBartender.locationName";
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HARNEDOR_BARTENDER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HARNEDOR_BARTENDER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gobletWood));
        return data;
    }

    @Override
    public void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int drinks = 1 + this.rand.nextInt(4) + i;
        for(int l = 0; l < drinks; ++l) {
            ItemStack drink = LOTRFoods.HARNEDOR_DRINK.getRandomFood(this.rand);
            this.entityDropItem(drink, 0.0f);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHaradBartender);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "nearHarad/harnennor/bartender/friendly";
        }
        return "nearHarad/harnennor/bartender/hostile";
    }
}
