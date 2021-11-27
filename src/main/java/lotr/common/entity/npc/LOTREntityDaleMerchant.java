package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleMerchant extends LOTREntityDaleMan implements LOTRTravellingTrader {
    private static final int[] hatColors = new int[] {8874591, 11895125, 4949452, 8298956, 5657939};
    private static final int[] featherColors = new int[] {16777215, 6736967, 15358290, 156402, 15719168};

    public LOTREntityDaleMerchant(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DALE_MERCHANT_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DALE_MERCHANT_SELL;
    }

    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityDaleMan(this.worldObj);
    }

    @Override
    public String getDepartureSpeech() {
        return "dale/merchant/departure";
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        int colorHat = hatColors[this.rand.nextInt(hatColors.length)];
        int colorFeather = featherColors[this.rand.nextInt(featherColors.length)];
        LOTRItemLeatherHat.setHatColor(hat, colorHat);
        LOTRItemLeatherHat.setFeatherColor(hat, colorFeather);
        this.setCurrentItemOrArmor(4, hat);
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
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDaleMerchant);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "dale/merchant/friendly";
        }
        return "dale/merchant/hostile";
    }
}
