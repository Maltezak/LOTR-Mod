package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradMerchant extends LOTREntityNearHaradrim implements LOTRTravellingTrader {
    private static int[] robeColors = new int[] {15723226, 14829087, 12653845, 8526876, 2625038};

    public LOTREntityNearHaradMerchant(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.NEAR_HARAD_MERCHANT_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.NEAR_HARAD_MERCHANT_SELL;
    }

    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityNearHaradrim(this.worldObj);
    }

    @Override
    public String getDepartureSpeech() {
        return "nearHarad/merchant/departure";
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
        int robeColor = robeColors[this.rand.nextInt(robeColors.length)];
        ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
        LOTRItemHaradRobes.setRobesColor(turban, robeColor);
        if(this.rand.nextBoolean()) {
            LOTRItemHaradTurban.setHasOrnament(turban, true);
        }
        this.setCurrentItemOrArmor(1, null);
        this.setCurrentItemOrArmor(2, null);
        this.setCurrentItemOrArmor(3, null);
        this.setCurrentItemOrArmor(4, turban);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNearHaradMerchant);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "nearHarad/merchant/friendly";
        }
        return "nearHarad/merchant/hostile";
    }
}
