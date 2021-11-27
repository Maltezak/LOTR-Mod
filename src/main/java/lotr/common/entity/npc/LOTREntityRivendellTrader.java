package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityRivendellTrader extends LOTREntityRivendellElf implements LOTRTravellingTrader {
    public LOTREntityRivendellTrader(World world) {
        super(world);
        this.tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.6, false));
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.RIVENDELL_TRADER;
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RIVENDELL_TRADER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RIVENDELL_TRADER_SELL;
    }

    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityRivendellElf(this.worldObj);
    }

    @Override
    public String getDepartureSpeech() {
        return "rivendell/trader/departure";
    }

    @Override
    public int getTotalArmorValue() {
        return 10;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.isEntityAlive() && this.travellingTraderInfo.timeUntilDespawn == 0) {
            this.worldObj.setEntityState(this, (byte) 15);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 5 + this.rand.nextInt(3);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte) 15);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b != 15) {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 75.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellTrader);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }

    @Override
    public boolean shouldRenderNPCHair() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "rivendell/trader/friendly";
            }
            return "rivendell/trader/neutral";
        }
        return "rivendell/trader/hostile";
    }
}
