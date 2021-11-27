package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowSpouse extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private LOTREntityNPC theSpouse;
    private double moveSpeed;
    private int followTick;

    public LOTREntityAINPCFollowSpouse(LOTREntityNPC npc, double d) {
        this.theNPC = npc;
        this.moveSpeed = d;
    }

    @Override
    public boolean shouldExecute() {
        LOTREntityNPC spouse = this.theNPC.familyInfo.getSpouse();
        if(spouse == null) {
            return false;
        }
        if(!spouse.isEntityAlive() || this.theNPC.getDistanceSqToEntity(spouse) < 36.0 || this.theNPC.getDistanceSqToEntity(spouse) >= 256.0) {
            return false;
        }
        this.theSpouse = spouse;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.theSpouse.isEntityAlive()) {
            return false;
        }
        double d = this.theNPC.getDistanceSqToEntity(this.theSpouse);
        return d >= 36.0 && d <= 256.0;
    }

    @Override
    public void startExecuting() {
        this.followTick = 200;
    }

    @Override
    public void resetTask() {
        this.theSpouse = null;
    }

    @Override
    public void updateTask() {
        --this.followTick;
        if(this.theNPC.getDistanceSqToEntity(this.theSpouse) > 144.0 || this.followTick <= 0) {
            this.followTick = 200;
            this.theNPC.getNavigator().tryMoveToEntityLiving(this.theSpouse, this.moveSpeed);
        }
    }
}
