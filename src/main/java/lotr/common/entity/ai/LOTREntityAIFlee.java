package lotr.common.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.Vec3;

public class LOTREntityAIFlee extends EntityAIBase {
    private EntityCreature theEntity;
    private double speed;
    private double attackerX;
    private double attackerY;
    private double attackerZ;
    private int timer;
    private boolean firstPath;

    public LOTREntityAIFlee(EntityCreature entity, double d) {
        this.theEntity = entity;
        this.speed = d;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase attacker = this.theEntity.getAITarget();
        if(attacker == null) {
            return false;
        }
        this.attackerX = attacker.posX;
        this.attackerY = attacker.posY;
        this.attackerZ = attacker.posZ;
        return true;
    }

    @Override
    public void startExecuting() {
        this.timer = 60 + this.theEntity.getRNG().nextInt(50);
    }

    @Override
    public boolean continueExecuting() {
        return this.timer > 0;
    }

    @Override
    public void updateTask() {
        Vec3 vec3;
        --this.timer;
        if((!this.firstPath || this.theEntity.getNavigator().noPath()) && (vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.attackerX, this.attackerY, this.attackerZ))) != null && this.theEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed)) {
            this.theEntity.setRevengeTarget(null);
            this.firstPath = true;
        }
    }

    @Override
    public void resetTask() {
        this.theEntity.getNavigator().clearPathEntity();
        this.timer = 0;
        this.firstPath = false;
    }
}
