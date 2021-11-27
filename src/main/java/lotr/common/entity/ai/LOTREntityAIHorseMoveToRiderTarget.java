package lotr.common.entity.ai;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;

public class LOTREntityAIHorseMoveToRiderTarget extends EntityAIBase {
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;
    private double speed;
    private PathEntity entityPathEntity;
    private int pathCheckTimer;

    public LOTREntityAIHorseMoveToRiderTarget(LOTRNPCMount horse) {
        this.theHorse = horse;
        this.livingHorse = (EntityCreature) (this.theHorse);
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
        EntityLivingBase riderTarget = ((LOTREntityNPC) rider).getAttackTarget();
        if(riderTarget == null || !riderTarget.isEntityAlive()) {
            return false;
        }
        this.entityPathEntity = this.livingHorse.getNavigator().getPathToEntityLiving(riderTarget);
        return this.entityPathEntity != null;
    }

    @Override
    public boolean continueExecuting() {
        if(this.livingHorse.riddenByEntity == null || !this.livingHorse.riddenByEntity.isEntityAlive() || !(this.livingHorse.riddenByEntity instanceof LOTREntityNPC)) {
            return false;
        }
        LOTREntityNPC rider = (LOTREntityNPC) this.livingHorse.riddenByEntity;
        EntityLivingBase riderTarget = rider.getAttackTarget();
        return riderTarget != null && riderTarget.isEntityAlive() && !this.livingHorse.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.speed = ((LOTREntityNPC) this.livingHorse.riddenByEntity).getEntityAttribute(LOTREntityNPC.horseAttackSpeed).getAttributeValue();
        this.livingHorse.getNavigator().setPath(this.entityPathEntity, this.speed);
        this.pathCheckTimer = 0;
    }

    @Override
    public void resetTask() {
        this.livingHorse.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        boolean aimingBow;
        LOTREntityNPC rider = (LOTREntityNPC) this.livingHorse.riddenByEntity;
        EntityLivingBase riderTarget = rider.getAttackTarget();
        aimingBow = rider.isAimingRanged() && this.livingHorse.getEntitySenses().canSee(riderTarget);
        if(!aimingBow) {
            this.livingHorse.getLookHelper().setLookPositionWithEntity(riderTarget, 30.0f, 30.0f);
            rider.rotationYaw = this.livingHorse.rotationYaw;
            rider.rotationYawHead = this.livingHorse.rotationYawHead;
        }
        if(--this.pathCheckTimer <= 0) {
            this.pathCheckTimer = 4 + this.livingHorse.getRNG().nextInt(7);
            this.livingHorse.getNavigator().tryMoveToEntityLiving(riderTarget, this.speed);
        }
        if(aimingBow) {
            if(rider.getDistanceSqToEntity(riderTarget) < 25.0) {
                Vec3 vec = LOTREntityAIRangedAttack.findPositionAwayFrom(rider, riderTarget, 8, 16);
                if(vec != null) {
                    this.livingHorse.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.speed);
                }
            }
            else {
                this.livingHorse.getNavigator().clearPathEntity();
            }
        }
    }
}
