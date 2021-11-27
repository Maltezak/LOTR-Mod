package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowParent extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private LOTREntityNPC parentNPC;
    private double moveSpeed;
    private int followTick;

    public LOTREntityAINPCFollowParent(LOTREntityNPC npc, double d) {
        this.theNPC = npc;
        this.moveSpeed = d;
    }

    @Override
    public boolean shouldExecute() {
        if(this.theNPC.familyInfo.getAge() >= 0) {
            return false;
        }
        LOTREntityNPC parent = this.theNPC.familyInfo.getParentToFollow();
        if(parent == null) {
            return false;
        }
        if(this.theNPC.getDistanceSqToEntity(parent) < 9.0 || this.theNPC.getDistanceSqToEntity(parent) >= 256.0) {
            return false;
        }
        this.parentNPC = parent;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.parentNPC.isEntityAlive()) {
            return false;
        }
        double d = this.theNPC.getDistanceSqToEntity(this.parentNPC);
        return d >= 9.0 && d <= 256.0;
    }

    @Override
    public void startExecuting() {
        this.followTick = 0;
    }

    @Override
    public void resetTask() {
        this.parentNPC = null;
    }

    @Override
    public void updateTask() {
        if(this.followTick-- <= 0) {
            this.followTick = 10;
            this.theNPC.getNavigator().tryMoveToEntityLiving(this.parentNPC, this.moveSpeed);
        }
    }
}
