package lotr.common.entity.ai;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHiredHorseRemainStill extends EntityAIBase {
    private LOTRNPCMount theHorse;
    private EntityCreature livingHorse;

    public LOTREntityAIHiredHorseRemainStill(LOTRNPCMount entity) {
        this.theHorse = entity;
        this.livingHorse = (EntityCreature) (this.theHorse);
        this.setMutexBits(5);
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
        LOTREntityNPC ridingNPC = (LOTREntityNPC) rider;
        if(!ridingNPC.hiredNPCInfo.isActive) {
            return false;
        }
        if(this.livingHorse.isInWater()) {
            return false;
        }
        if(!this.livingHorse.onGround) {
            return false;
        }
        return ridingNPC.hiredNPCInfo.isHalted() && (ridingNPC.getAttackTarget() == null || !ridingNPC.getAttackTarget().isEntityAlive());
    }

    @Override
    public void startExecuting() {
        this.livingHorse.getNavigator().clearPathEntity();
    }
}
