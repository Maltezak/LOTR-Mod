package lotr.common.entity.ai;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHorseFollowHiringPlayer extends EntityAIBase {
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;
    private EntityPlayer theHiringPlayer;
    private double moveSpeed;
    private int followTick;
    private float maxNearDist;
    private float minFollowDist;
    private boolean avoidsWater;
    private boolean initSpeed;

    public LOTREntityAIHorseFollowHiringPlayer(LOTRNPCMount entity) {
        this.theHorse = entity;
        this.livingHorse = (EntityCreature) (this.theHorse);
        this.minFollowDist = 8.0f;
        this.maxNearDist = 6.0f;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theHorse.getBelongsToNPC()) {
            return false;
        }
        Entity rider = this.livingHorse.riddenByEntity;
        if(rider == null || !rider.isEntityAlive() || !(rider instanceof LOTREntityNPC)) {
            return false;
        }
        LOTREntityNPC ridingNPC = (LOTREntityNPC) rider;
        if(!ridingNPC.hiredNPCInfo.isActive) {
            return false;
        }
        EntityPlayer entityplayer = ridingNPC.hiredNPCInfo.getHiringPlayer();
        if(entityplayer == null) {
            return false;
        }
        if(!ridingNPC.hiredNPCInfo.shouldFollowPlayer()) {
            return false;
        }
        if(this.livingHorse.getDistanceSqToEntity(entityplayer) < this.minFollowDist * this.minFollowDist) {
            return false;
        }
        this.theHiringPlayer = entityplayer;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if(this.livingHorse.riddenByEntity == null || !this.livingHorse.riddenByEntity.isEntityAlive() || !(this.livingHorse.riddenByEntity instanceof LOTREntityNPC)) {
            return false;
        }
        LOTREntityNPC ridingNPC = (LOTREntityNPC) this.livingHorse.riddenByEntity;
        return ridingNPC.hiredNPCInfo.isActive && ridingNPC.hiredNPCInfo.getHiringPlayer() != null && ridingNPC.hiredNPCInfo.shouldFollowPlayer() && !this.livingHorse.getNavigator().noPath() && this.livingHorse.getDistanceSqToEntity(this.theHiringPlayer) > this.maxNearDist * this.maxNearDist;
    }

    @Override
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.livingHorse.getNavigator().getAvoidsWater();
        this.livingHorse.getNavigator().setAvoidsWater(false);
        if(!this.initSpeed) {
            double d = this.livingHorse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            this.moveSpeed = 1.0 / d * 0.37;
            this.initSpeed = true;
        }
    }

    @Override
    public void resetTask() {
        this.theHiringPlayer = null;
        this.livingHorse.getNavigator().clearPathEntity();
        this.livingHorse.getNavigator().setAvoidsWater(this.avoidsWater);
    }

    @Override
    public void updateTask() {
        LOTREntityNPC ridingNPC = (LOTREntityNPC) this.livingHorse.riddenByEntity;
        this.livingHorse.getLookHelper().setLookPositionWithEntity(this.theHiringPlayer, 10.0f, this.livingHorse.getVerticalFaceSpeed());
        ridingNPC.rotationYaw = this.livingHorse.rotationYaw;
        ridingNPC.rotationYawHead = this.livingHorse.rotationYawHead;
        if(ridingNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
            this.followTick = 10;
            if(!this.livingHorse.getNavigator().tryMoveToEntityLiving(this.theHiringPlayer, this.moveSpeed) && ridingNPC.hiredNPCInfo.teleportAutomatically) {
                double d = ridingNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
                d = Math.max(d, 24.0);
                if(ridingNPC.getDistanceSqToEntity(this.theHiringPlayer) > d * d) {
                    ridingNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
                }
            }
        }
    }
}
