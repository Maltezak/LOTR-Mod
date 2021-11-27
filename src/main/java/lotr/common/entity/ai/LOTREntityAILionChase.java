package lotr.common.entity.ai;

import java.util.*;

import lotr.common.entity.animal.LOTREntityLionBase;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;

public class LOTREntityAILionChase extends EntityAIBase {
    private LOTREntityLionBase theLion;
    private EntityCreature targetEntity;
    private double speed;
    private int chaseTimer;
    private int lionRePathDelay;
    public LOTREntityAILionChase(LOTREntityLionBase lion, double d) {
        this.theLion = lion;
        this.speed = d;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theLion.isChild() || this.theLion.isInLove()) {
            return false;
        }
        if(this.theLion.getRNG().nextInt(800) != 0) {
            return false;
        }
        List entities = this.theLion.worldObj.getEntitiesWithinAABB(EntityAnimal.class, this.theLion.boundingBox.expand(12.0, 12.0, 12.0));
        ArrayList<EntityAnimal> validTargets = new ArrayList<>();
        for(Object entitie : entities) {
            EntityAnimal entity = (EntityAnimal) entitie;
            if(entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) != null) continue;
            validTargets.add(entity);
        }
        if(validTargets.isEmpty()) {
            return false;
        }
        this.targetEntity = validTargets.get(this.theLion.getRNG().nextInt(validTargets.size()));
        return true;
    }

    @Override
    public void startExecuting() {
        this.chaseTimer = 300 + this.theLion.getRNG().nextInt(400);
    }

    @Override
    public void updateTask() {
        Vec3 vec3;
        --this.chaseTimer;
        this.theLion.getLookHelper().setLookPositionWithEntity(this.targetEntity, 30.0f, 30.0f);
        --this.lionRePathDelay;
        if(this.lionRePathDelay <= 0) {
            this.lionRePathDelay = 10;
            this.theLion.getNavigator().tryMoveToEntityLiving(this.targetEntity, this.speed);
        }
        if(this.targetEntity.getNavigator().noPath() && (vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.targetEntity, 16, 7, Vec3.createVectorHelper(this.theLion.posX, this.theLion.posY, this.theLion.posZ))) != null) {
            this.targetEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 2.0);
        }
    }

    @Override
    public boolean continueExecuting() {
        return this.targetEntity != null && this.targetEntity.isEntityAlive() && this.chaseTimer > 0 && this.theLion.getDistanceSqToEntity(this.targetEntity) < 256.0;
    }

    @Override
    public void resetTask() {
        this.chaseTimer = 0;
        this.lionRePathDelay = 0;
    }
}
