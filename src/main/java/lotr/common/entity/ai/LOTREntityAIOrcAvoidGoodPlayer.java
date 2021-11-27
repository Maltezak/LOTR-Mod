package lotr.common.entity.ai;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.*;
import net.minecraft.util.Vec3;

public class LOTREntityAIOrcAvoidGoodPlayer extends EntityAIBase {
    private LOTREntityOrc theOrc;
    private double speed;
    private EntityLivingBase closestEnemyPlayer;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;

    public LOTREntityAIOrcAvoidGoodPlayer(LOTREntityOrc orc, float f, double d) {
        this.theOrc = orc;
        this.distanceFromEntity = f;
        this.speed = d;
        this.entityPathNavigate = orc.getNavigator();
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theOrc.isWeakOrc || this.theOrc.hiredNPCInfo.isActive) {
            return false;
        }
        if(this.theOrc.getFaction() == LOTRFaction.MORDOR) {
            return false;
        }
        if(this.theOrc.currentRevengeTarget != null || this.anyNearbyOrcsAttacked()) {
            return false;
        }
        List validPlayers = this.theOrc.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, this.theOrc.boundingBox.expand(this.distanceFromEntity, this.distanceFromEntity / 2.0, this.distanceFromEntity), new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity entity) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                if(entityplayer.capabilities.isCreativeMode || theOrc.currentRevengeTarget == entityplayer) {
                    return false;
                }
                float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction());
                return alignment <= -500.0f;
            }
        });
        if(validPlayers.isEmpty()) {
            return false;
        }
        this.closestEnemyPlayer = (EntityLivingBase) validPlayers.get(0);
        Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theOrc, 16, 7, Vec3.createVectorHelper(this.closestEnemyPlayer.posX, this.closestEnemyPlayer.posY, this.closestEnemyPlayer.posZ));
        if(fleePath == null) {
            return false;
        }
        if(this.closestEnemyPlayer.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < this.closestEnemyPlayer.getDistanceSqToEntity(this.theOrc)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
        return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(fleePath);
    }

    private boolean anyNearbyOrcsAttacked() {
        List nearbyAllies = this.theOrc.worldObj.selectEntitiesWithinAABB(EntityLiving.class, this.theOrc.boundingBox.expand(this.distanceFromEntity, this.distanceFromEntity / 2.0, this.distanceFromEntity), new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity entity) {
                if(entity != LOTREntityAIOrcAvoidGoodPlayer.this.theOrc) {
                    return LOTRMod.getNPCFaction(entity).isGoodRelation(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction());
                }
                return false;
            }
        });
        for(Object obj : nearbyAllies) {
            EntityLiving ally = (EntityLiving) obj;
            if(!(ally instanceof LOTREntityOrc ? ((LOTREntityOrc) ally).currentRevengeTarget instanceof EntityPlayer : ally.getAttackTarget() instanceof EntityPlayer)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath() && this.theOrc.getAITarget() != this.closestEnemyPlayer && !this.anyNearbyOrcsAttacked();
    }

    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.speed);
    }

    @Override
    public void resetTask() {
        this.closestEnemyPlayer = null;
    }

}
