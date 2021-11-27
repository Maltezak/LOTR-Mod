package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIGollumRemainStill extends EntityAIBase {
    private LOTREntityGollum theGollum;

    public LOTREntityAIGollumRemainStill(LOTREntityGollum entity) {
        this.theGollum = entity;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theGollum.getGollumOwner() == null) {
            return false;
        }
        if(this.theGollum.isInWater()) {
            return false;
        }
        if(!this.theGollum.onGround) {
            return false;
        }
        return this.theGollum.isGollumSitting();
    }

    @Override
    public void startExecuting() {
        this.theGollum.getNavigator().clearPathEntity();
    }
}
