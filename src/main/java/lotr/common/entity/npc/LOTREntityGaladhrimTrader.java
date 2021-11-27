package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimTrader extends LOTREntityGaladhrimElf implements LOTRTravellingTrader {
    public LOTREntityGaladhrimTrader(World world) {
        super(world);
        this.tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.6, false));
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.GALADHRIM_TRADER;
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GALADHRIM_TRADER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GALADHRIM_TRADER_SELL;
    }

    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityGaladhrimElf(this.worldObj);
    }

    @Override
    public String getDepartureSpeech() {
        return "galadhrim/trader/departure";
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
        if(b == 15) {
            for(int i = 0; i < 16; ++i) {
                double d = this.posX + (this.rand.nextDouble() - 0.5) * this.width;
                double d1 = this.posY + this.rand.nextDouble() * this.height;
                double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * this.width;
                double d3 = -0.2 + this.rand.nextFloat() * 0.4f;
                double d4 = -0.2 + this.rand.nextFloat() * 0.4f;
                double d5 = -0.2 + this.rand.nextFloat() * 0.4f;
                LOTRMod.proxy.spawnParticle("leafGold_" + (30 + this.rand.nextInt(30)), d, d1, d2, d3, d4, d5);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 75.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeElvenTrader);
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
                return "galadhrim/trader/friendly";
            }
            return "galadhrim/trader/neutral";
        }
        return "galadhrim/trader/hostile";
    }
}
