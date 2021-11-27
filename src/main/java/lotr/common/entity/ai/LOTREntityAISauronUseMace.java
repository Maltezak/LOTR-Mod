package lotr.common.entity.ai;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntitySauron;
import lotr.common.item.LOTRItemSauronMace;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityAISauronUseMace extends EntityAIBase {
    private LOTREntitySauron theSauron;
    private int attackTick = 0;
    private World theWorld;

    public LOTREntityAISauronUseMace(LOTREntitySauron sauron) {
        this.theSauron = sauron;
        this.theWorld = this.theSauron.worldObj;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        int targets = 0;
        List nearbyEntities = this.theWorld.getEntitiesWithinAABB(EntityLivingBase.class, this.theSauron.boundingBox.expand(6.0, 6.0, 6.0));
        for(Object nearbyEntitie : nearbyEntities) {
            EntityLivingBase entity = (EntityLivingBase) nearbyEntitie;
            if(!entity.isEntityAlive()) continue;
            if(entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                if(entityplayer.capabilities.isCreativeMode || (LOTRLevelData.getData(entityplayer).getAlignment(this.theSauron.getFaction()) >= 0.0f) && this.theSauron.getAttackTarget() != entityplayer) continue;
                ++targets;
                continue;
            }
            if(this.theSauron.getFaction().isBadRelation(LOTRMod.getNPCFaction(entity))) {
                ++targets;
                continue;
            }
            if(this.theSauron.getAttackTarget() != entity && (!(entity instanceof EntityLiving) || ((EntityLiving) entity).getAttackTarget() != this.theSauron)) continue;
            ++targets;
        }
        if(targets >= 4) {
            return true;
        }
        return targets > 0 && this.theSauron.getRNG().nextInt(100) == 0;
    }

    @Override
    public boolean continueExecuting() {
        return this.theSauron.getIsUsingMace();
    }

    @Override
    public void startExecuting() {
        this.attackTick = 40;
        this.theSauron.setIsUsingMace(true);
    }

    @Override
    public void resetTask() {
        this.attackTick = 40;
        this.theSauron.setIsUsingMace(false);
    }

    @Override
    public void updateTask() {
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if(this.attackTick <= 0) {
            this.attackTick = 40;
            LOTRItemSauronMace.useSauronMace(this.theSauron.getEquipmentInSlot(0), this.theWorld, this.theSauron);
            this.theSauron.setIsUsingMace(false);
        }
    }
}
