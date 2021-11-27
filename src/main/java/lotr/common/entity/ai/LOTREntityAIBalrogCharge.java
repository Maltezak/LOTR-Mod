package lotr.common.entity.ai;

import java.util.List;

import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class LOTREntityAIBalrogCharge extends LOTREntityAIAttackOnCollide {
    private LOTREntityBalrog theBalrog;
    private float chargeDist;
    private int frustrationTime;
    private boolean hitChargeTarget = false;
    private int chargingTick;

    public LOTREntityAIBalrogCharge(LOTREntityBalrog balrog, double speed, float dist, int fr) {
        super(balrog, speed, false);
        this.theBalrog = balrog;
        this.chargeDist = dist;
        this.frustrationTime = fr;
    }

    @Override
    public boolean shouldExecute() {
        if(this.theBalrog.isBalrogCharging()) {
            return false;
        }
        boolean flag = super.shouldExecute();
        if(flag) {
            if(this.theBalrog.chargeFrustration >= this.frustrationTime) {
                return true;
            }
            double dist = this.theBalrog.getDistanceSqToEntity(this.attackTarget);
            return dist >= this.chargeDist * this.chargeDist;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.theBalrog.isEntityAlive()) {
            return false;
        }
        this.attackTarget = this.theBalrog.getAttackTarget();
        if(this.attackTarget == null || !this.attackTarget.isEntityAlive()) {
            return false;
        }
        return this.chargingTick > 0 && !this.hitChargeTarget;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.theBalrog.setBalrogCharging(true);
        this.hitChargeTarget = false;
        this.chargingTick = 200;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.theBalrog.setBalrogCharging(false);
        this.hitChargeTarget = false;
        this.chargingTick = 0;
    }

    @Override
    public void updateTask() {
        this.updateLookAndPathing();
        if(this.chargingTick > 0) {
            --this.chargingTick;
        }
        AxisAlignedBB targetBB = this.theBalrog.boundingBox.expand(0.5, 0.0, 0.5);
        double motionExtent = 2.0;
        float angleRad = (float) Math.atan2(this.theBalrog.motionZ, this.theBalrog.motionX);
        targetBB = targetBB.getOffsetBoundingBox(MathHelper.cos(angleRad) * motionExtent, 0.0, MathHelper.sin(angleRad) * motionExtent);
        List hitTargets = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.theBalrog, targetBB);
        for(Object hitTarget : hitTargets) {
            EntityLivingBase hitEntity;
            Entity obj = (Entity) hitTarget;
            if(!(obj instanceof EntityLivingBase) || (hitEntity = (EntityLivingBase) obj) == this.theBalrog.riddenByEntity) continue;
            float attackStr = (float) this.theBalrog.getEntityAttribute(LOTREntityBalrog.balrogChargeDamage).getAttributeValue();
            boolean flag = hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this.theBalrog), attackStr);
            if(!flag) continue;
            float knock = 2.5f;
            float knockY = 0.5f;
            hitEntity.addVelocity(-MathHelper.sin((float) Math.toRadians(this.theBalrog.rotationYaw)) * knock, knockY, MathHelper.cos((float) Math.toRadians(this.theBalrog.rotationYaw)) * knock);
            hitEntity.setFire(6);
            if(hitEntity != this.attackTarget) continue;
            this.hitChargeTarget = true;
        }
    }
}
