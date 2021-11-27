package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class LOTREntityAIHiredRemainStill extends EntityAIBase {
    private LOTREntityNPC theNPC;

    public LOTREntityAIHiredRemainStill(LOTREntityNPC entity) {
        this.theNPC = entity;
        this.setMutexBits(6);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theNPC.hiredNPCInfo.isActive) {
            return false;
        }
        if(this.theNPC.isInWater()) {
            return false;
        }
        if(!this.theNPC.onGround) {
            return false;
        }
        return this.theNPC.hiredNPCInfo.isHalted() && (this.theNPC.getAttackTarget() == null || !this.theNPC.getAttackTarget().isEntityAlive());
    }

    @Override
    public void startExecuting() {
        this.theNPC.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        this.theNPC.getNavigator().clearPathEntity();
        Vec3 pos = Vec3.createVectorHelper(this.theNPC.posX, this.theNPC.posY + this.theNPC.getEyeHeight(), this.theNPC.posZ);
        Vec3 look = this.theNPC.getLookVec().normalize();
        Vec3 lookUp = pos.addVector(look.xCoord * 3.0, pos.yCoord + 0.25, look.zCoord * 3.0);
        this.theNPC.getLookHelper().setLookPosition(lookUp.xCoord, lookUp.yCoord, lookUp.zCoord, 20.0f, 20.0f);
    }
}
