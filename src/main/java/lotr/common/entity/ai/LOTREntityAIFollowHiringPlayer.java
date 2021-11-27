package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIFollowHiringPlayer extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private final boolean isBannerBearer;
    private EntityPlayer theHiringPlayer;
    private double moveSpeed;
    private int followTick;
    private float maxNearDist;
    private float minFollowDist;
    private boolean avoidsWater;
    private EntityLiving bannerBearerTarget;

    public LOTREntityAIFollowHiringPlayer(LOTREntityNPC entity) {
        this.theNPC = entity;
        this.isBannerBearer = entity instanceof LOTRBannerBearer;
        double entityMoveSpeed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        this.moveSpeed = 1.0 / entityMoveSpeed * 0.37;
        this.minFollowDist = 8.0f;
        this.maxNearDist = 6.0f;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theNPC.hiredNPCInfo.isActive) {
            return false;
        }
        EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if(entityplayer == null) {
            return false;
        }
        this.theHiringPlayer = entityplayer;
        if(!this.theNPC.hiredNPCInfo.shouldFollowPlayer()) {
            return false;
        }
        if(this.isBannerBearer) {
            ArrayList<EntityLiving> alliesToFollow = new ArrayList<>();
            List nearbyEntities = this.theNPC.worldObj.getEntitiesWithinAABB(EntityLiving.class, this.theNPC.boundingBox.expand(16.0, 16.0, 16.0));
            for(Object nearbyEntitie : nearbyEntities) {
                EntityLiving entity = (EntityLiving) nearbyEntitie;
                if(entity == this.theNPC || LOTRMod.getNPCFaction(entity) != this.theNPC.getFaction()) continue;
                if(entity instanceof LOTREntityNPC) {
                    LOTREntityNPC npc = (LOTREntityNPC) entity;
                    if(!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) continue;
                }
                alliesToFollow.add(entity);
            }
            EntityLiving entityToFollow = null;
            double d = Double.MAX_VALUE;
            for(EntityLiving entity : alliesToFollow) {
                double dist = this.theNPC.getDistanceSqToEntity(entity);
                if((dist >= d) || (dist <= this.minFollowDist * this.minFollowDist)) continue;
                d = dist;
                entityToFollow = entity;
            }
            if(entityToFollow != null) {
                this.bannerBearerTarget = entityToFollow;
                return true;
            }
        }
        return (this.theNPC.getDistanceSqToEntity(entityplayer) >= this.minFollowDist * this.minFollowDist);
    }

    @Override
    public boolean continueExecuting() {
        if(this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() != null && this.theNPC.hiredNPCInfo.shouldFollowPlayer() && !this.theNPC.getNavigator().noPath()) {
            EntityLivingBase target = this.bannerBearerTarget != null ? this.bannerBearerTarget : this.theHiringPlayer;
            return this.theNPC.getDistanceSqToEntity(target) > this.maxNearDist * this.maxNearDist;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.theNPC.getNavigator().getAvoidsWater();
        this.theNPC.getNavigator().setAvoidsWater(false);
    }

    @Override
    public void resetTask() {
        this.theHiringPlayer = null;
        this.bannerBearerTarget = null;
        this.theNPC.getNavigator().clearPathEntity();
        this.theNPC.getNavigator().setAvoidsWater(this.avoidsWater);
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.bannerBearerTarget != null ? this.bannerBearerTarget : this.theHiringPlayer;
        this.theNPC.getLookHelper().setLookPositionWithEntity(target, 10.0f, this.theNPC.getVerticalFaceSpeed());
        if(this.theNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
            this.followTick = 10;
            if(!this.theNPC.getNavigator().tryMoveToEntityLiving(target, this.moveSpeed) && this.theNPC.hiredNPCInfo.teleportAutomatically) {
                double d = this.theNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
                d = Math.max(d, 24.0);
                if(this.theNPC.getDistanceSqToEntity(this.theHiringPlayer) > d * d) {
                    this.theNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
                }
            }
        }
    }
}
