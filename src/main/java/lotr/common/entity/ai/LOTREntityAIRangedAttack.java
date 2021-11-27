package lotr.common.entity.ai;

import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.*;

public class LOTREntityAIRangedAttack extends EntityAIBase {
    private EntityLiving theOwner;
    private IRangedAttackMob theOwnerRanged;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime = -1;
    private double moveSpeed;
    private double moveSpeedFlee = 1.5;
    private int repathDelay;
    private int attackTimeMin;
    private int attackTimeMax;
    private float attackRange;
    private float attackRangeSq;

    public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int time, float range) {
        this(entity, speed, time, time, range);
    }

    public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int min, int max, float range) {
        this.theOwnerRanged = entity;
        this.theOwner = (EntityLiving) (entity);
        this.moveSpeed = speed;
        this.attackTimeMin = min;
        this.attackTimeMax = max;
        this.attackRange = range;
        this.attackRangeSq = range * range;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.theOwner.getAttackTarget();
        if(target == null) {
            return false;
        }
        this.attackTarget = target;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.theOwner.isEntityAlive()) {
            return false;
        }
        this.attackTarget = this.theOwner.getAttackTarget();
        return this.attackTarget != null && this.attackTarget.isEntityAlive();
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.repathDelay = 0;
        this.rangedAttackTime = -1;
    }

    @Override
    public void updateTask() {
        double distanceSq = this.theOwner.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean canSee = this.theOwner.getEntitySenses().canSee(this.attackTarget);
        this.repathDelay = canSee ? ++this.repathDelay : 0;
        if(distanceSq <= this.attackRangeSq) {
            if(this.theOwner.getDistanceSqToEntity(this.attackTarget) < 25.0) {
                Vec3 vec = LOTREntityAIRangedAttack.findPositionAwayFrom(this.theOwner, this.attackTarget, 8, 16);
                if(vec != null) {
                    this.theOwner.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, this.moveSpeedFlee);
                }
            }
            else if(this.repathDelay >= 20) {
                this.theOwner.getNavigator().clearPathEntity();
                this.repathDelay = 0;
            }
        }
        else {
            this.theOwner.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.moveSpeed);
        }
        this.theOwner.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        --this.rangedAttackTime;
        if(this.rangedAttackTime == 0) {
            float distanceRatio;
            if(distanceSq > this.attackRangeSq || !canSee) {
                return;
            }
            float power = distanceRatio = MathHelper.sqrt_double(distanceSq) / this.attackRange;
            power = MathHelper.clamp_float(power, 0.1f, 1.0f);
            this.theOwnerRanged.attackEntityWithRangedAttack(this.attackTarget, power);
            this.rangedAttackTime = MathHelper.floor_float(distanceRatio * (this.attackTimeMax - this.attackTimeMin) + this.attackTimeMin);
        }
        else if(this.rangedAttackTime < 0) {
            float distanceRatio = MathHelper.sqrt_double(distanceSq) / this.attackRange;
            this.rangedAttackTime = MathHelper.floor_float(distanceRatio * (this.attackTimeMax - this.attackTimeMin) + this.attackTimeMin);
        }
    }

    public static Vec3 findPositionAwayFrom(EntityLivingBase entity, EntityLivingBase target, int min, int max) {
        Random random = entity.getRNG();
        for(int l = 0; l < 24; ++l) {
            int k;
            int j;
            int i = MathHelper.floor_double(entity.posX) - max + random.nextInt(max * 2 + 1);
            double d = target.getDistanceSq(i, j = MathHelper.floor_double(entity.boundingBox.minY) - 4 + random.nextInt(9), k = MathHelper.floor_double(entity.posZ) - max + random.nextInt(max * 2 + 1));
            if((d <= min * min) || (d >= max * max)) continue;
            return Vec3.createVectorHelper(i, j, k);
        }
        return null;
    }
}
