package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomadMerchant extends LOTREntityNomad implements LOTRTravellingTrader {
    private static int[] robeColors = new int[] {15723226, 13551017, 6512465, 2499615, 11376219, 7825215};

    public LOTREntityNomadMerchant(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.NOMAD_MERCHANT_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.NOMAD_MERCHANT_SELL;
    }

    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityNomad(this.worldObj);
    }

    @Override
    public String getDepartureSpeech() {
        return "nearHarad/nomad/merchant/departure";
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        ItemStack[] robe;
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
        int robeColor = robeColors[this.rand.nextInt(robeColors.length)];
        for(ItemStack item : robe = new ItemStack[] {new ItemStack(LOTRMod.bootsHaradRobes), new ItemStack(LOTRMod.legsHaradRobes), new ItemStack(LOTRMod.bodyHaradRobes), new ItemStack(LOTRMod.helmetHaradRobes)}) {
            LOTRItemHaradRobes.setRobesColor(item, robeColor);
        }
        if(this.rand.nextBoolean()) {
            LOTRItemHaradTurban.setHasOrnament(robe[3], true);
        }
        this.setCurrentItemOrArmor(1, robe[0]);
        this.setCurrentItemOrArmor(2, robe[1]);
        this.setCurrentItemOrArmor(3, robe[2]);
        this.setCurrentItemOrArmor(4, robe[3]);
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNomadMerchant);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "nearHarad/nomad/merchant/friendly";
        }
        return "nearHarad/nomad/merchant/hostile";
    }
}
