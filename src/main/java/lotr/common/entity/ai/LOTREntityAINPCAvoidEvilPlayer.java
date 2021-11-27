package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.*;
import net.minecraft.util.Vec3;

public class LOTREntityAINPCAvoidEvilPlayer extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;

    public LOTREntityAINPCAvoidEvilPlayer(LOTREntityNPC npc, float f, double d, double d1) {
        this.theNPC = npc;
        this.distanceFromEntity = f;
        this.farSpeed = d;
        this.nearSpeed = d1;
        this.entityPathNavigate = npc.getNavigator();
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        ArrayList<EntityPlayer> validPlayers = new ArrayList<>();
        List list = this.theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.theNPC.boundingBox.expand(this.distanceFromEntity, this.distanceFromEntity / 2.0, this.distanceFromEntity));
        if(list.isEmpty()) {
            return false;
        }
        for(Object element : list) {
            EntityPlayer entityplayer = (EntityPlayer) element;
            if(entityplayer.capabilities.isCreativeMode) continue;
            float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction());
            if(((this.theNPC.familyInfo.getAge() >= 0) || (alignment >= 0.0f)) && (!(this.theNPC instanceof LOTREntityHobbit) || (alignment > -100.0f))) continue;
            validPlayers.add(entityplayer);
        }
        if(validPlayers.isEmpty()) {
            return false;
        }
        this.closestLivingEntity = validPlayers.get(0);
        Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theNPC, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if(fleePath == null) {
            return false;
        }
        if(this.closestLivingEntity.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theNPC)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
        return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(fleePath);
    }

    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    @Override
    public void updateTask() {
        if(this.theNPC.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theNPC.getNavigator().setSpeed(this.nearSpeed);
        }
        else {
            this.theNPC.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
