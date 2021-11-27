package lotr.common.entity.ai;

import java.util.List;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHobbitChildFollowGoodPlayer extends EntityAIBase {
    private LOTREntityHobbit theHobbit;
    private EntityPlayer playerToFollow;
    private float range;
    private double speed;
    private int followDelay;

    public LOTREntityAIHobbitChildFollowGoodPlayer(LOTREntityHobbit hobbit, float f, double d) {
        this.theHobbit = hobbit;
        this.range = f;
        this.speed = d;
    }

    @Override
    public boolean shouldExecute() {
        if(this.theHobbit.familyInfo.getAge() >= 0) {
            return false;
        }
        List<EntityPlayer> list = this.theHobbit.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.theHobbit.boundingBox.expand(this.range, 3.0, this.range));
        EntityPlayer entityplayer = null;
        double distanceSq = Double.MAX_VALUE;
        for(EntityPlayer playerCandidate : list) {
            double d;
            if((LOTRLevelData.getData(playerCandidate).getAlignment(this.theHobbit.getFaction()) < 200.0f) || ((d = this.theHobbit.getDistanceSqToEntity(playerCandidate)) > distanceSq)) continue;
            distanceSq = d;
            entityplayer = playerCandidate;
        }
        if(entityplayer == null) {
            return false;
        }
        if(distanceSq < 9.0) {
            return false;
        }
        this.playerToFollow = entityplayer;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.playerToFollow.isEntityAlive() || this.theHobbit.familyInfo.getAge() >= 0) {
            return false;
        }
        double distanceSq = this.theHobbit.getDistanceSqToEntity(this.playerToFollow);
        return distanceSq >= 9.0 && distanceSq <= 256.0;
    }

    @Override
    public void startExecuting() {
        this.followDelay = 0;
    }

    @Override
    public void resetTask() {
        this.playerToFollow = null;
    }

    @Override
    public void updateTask() {
        if(--this.followDelay <= 0) {
            this.followDelay = 10;
            this.theHobbit.getNavigator().tryMoveToEntityLiving(this.playerToFollow, this.speed);
        }
    }
}
