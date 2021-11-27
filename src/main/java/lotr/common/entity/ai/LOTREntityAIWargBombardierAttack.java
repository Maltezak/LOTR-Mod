package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIWargBombardierAttack extends EntityAIBase {
    private World worldObj;
    private LOTREntityWargBombardier theWarg;
    private EntityLivingBase entityTarget;
    private double moveSpeed;
    private PathEntity entityPathEntity;
    private int randomMoveTick;

    public LOTREntityAIWargBombardierAttack(LOTREntityWargBombardier entity, double speed) {
        this.theWarg = entity;
        this.worldObj = entity.worldObj;
        this.moveSpeed = speed;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entity = this.theWarg.getAttackTarget();
        if(entity == null) {
            return false;
        }
        this.entityTarget = entity;
        this.entityPathEntity = this.theWarg.getNavigator().getPathToEntityLiving(this.entityTarget);
        return this.entityPathEntity != null;
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase entity = this.theWarg.getAttackTarget();
        return entity != null && this.entityTarget.isEntityAlive();
    }

    @Override
    public void startExecuting() {
        this.theWarg.getNavigator().setPath(this.entityPathEntity, this.moveSpeed);
        this.randomMoveTick = 0;
    }

    @Override
    public void resetTask() {
        this.entityTarget = null;
        this.theWarg.getNavigator().clearPathEntity();
        this.theWarg.setBombFuse(35);
    }

    @Override
    public void updateTask() {
        this.theWarg.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0f, 30.0f);
        if(this.theWarg.getEntitySenses().canSee(this.entityTarget) && --this.randomMoveTick <= 0) {
            this.randomMoveTick = 4 + this.theWarg.getRNG().nextInt(7);
            this.theWarg.getNavigator().tryMoveToEntityLiving(this.entityTarget, this.moveSpeed);
        }
        if(this.theWarg.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ) <= 16.0) {
            if(this.theWarg.getBombFuse() > 20) {
                int i;
                for(i = this.theWarg.getBombFuse(); i > 20; i -= 10) {
                }
                this.theWarg.setBombFuse(i);
            }
            else if(this.theWarg.getBombFuse() > 0) {
                this.theWarg.setBombFuse(this.theWarg.getBombFuse() - 1);
            }
            else {
                this.worldObj.createExplosion(this.theWarg, this.theWarg.posX, this.theWarg.posY, this.theWarg.posZ, (this.theWarg.getBombStrengthLevel() + 1) * 4.0f, LOTRMod.canGrief(this.worldObj));
                this.theWarg.setDead();
            }
        }
        else if(this.theWarg.getBombFuse() <= 20) {
            int i;
            for(i = this.theWarg.getBombFuse(); i <= 20; i += 10) {
            }
            this.theWarg.setBombFuse(i);
        }
        else {
            this.theWarg.setBombFuse(this.theWarg.getBombFuse() - 1);
        }
    }
}
