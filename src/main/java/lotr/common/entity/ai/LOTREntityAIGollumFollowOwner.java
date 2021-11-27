package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityAIGollumFollowOwner extends EntityAIBase {
    private LOTREntityGollum theGollum;
    private EntityPlayer theOwner;
    private double moveSpeed;
    private PathNavigate theGollumPathfinder;
    private int followTick;
    private float maxDist;
    private float minDist;
    private boolean avoidsWater;
    private World theWorld;

    public LOTREntityAIGollumFollowOwner(LOTREntityGollum entity, double d, float f, float f1) {
        this.theGollum = entity;
        this.moveSpeed = d;
        this.theGollumPathfinder = entity.getNavigator();
        this.minDist = f;
        this.maxDist = f1;
        this.theWorld = entity.worldObj;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityPlayer entityplayer = this.theGollum.getGollumOwner();
        if(entityplayer == null) {
            return false;
        }
        if(this.theGollum.isGollumSitting()) {
            return false;
        }
        if(this.theGollum.getDistanceSqToEntity(entityplayer) < this.minDist * this.minDist) {
            return false;
        }
        this.theOwner = entityplayer;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        return this.theGollum.getGollumOwner() != null && !this.theGollumPathfinder.noPath() && this.theGollum.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.theGollum.isGollumSitting();
    }

    @Override
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.theGollum.getNavigator().getAvoidsWater();
        this.theGollum.getNavigator().setAvoidsWater(false);
    }

    @Override
    public void resetTask() {
        this.theOwner = null;
        this.theGollumPathfinder.clearPathEntity();
        this.theGollum.getNavigator().setAvoidsWater(this.avoidsWater);
    }

    @Override
    public void updateTask() {
        this.theGollum.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0f, this.theGollum.getVerticalFaceSpeed());
        if(!this.theGollum.isGollumSitting() && --this.followTick <= 0) {
            this.followTick = 10;
            if(!this.theGollumPathfinder.tryMoveToEntityLiving(this.theOwner, this.moveSpeed) && this.theGollum.getDistanceSqToEntity(this.theOwner) >= 256.0) {
                int i = MathHelper.floor_double(this.theOwner.posX);
                int j = MathHelper.floor_double(this.theOwner.boundingBox.minY);
                int k = MathHelper.floor_double(this.theOwner.posZ);
                float f = this.theGollum.width / 2.0f;
                float f1 = this.theGollum.height;
                AxisAlignedBB theGollumBoundingBox = AxisAlignedBB.getBoundingBox(this.theOwner.posX - f, this.theOwner.posY - this.theGollum.yOffset + this.theGollum.ySize, this.theOwner.posZ - f, this.theOwner.posX + f, this.theOwner.posY - this.theGollum.yOffset + this.theGollum.ySize + f1, this.theOwner.posZ + f);
                if(this.theWorld.func_147461_a(theGollumBoundingBox).isEmpty() && this.theWorld.getBlock(i, j - 1, k).isSideSolid(this.theWorld, i, j - 1, k, ForgeDirection.UP)) {
                    this.theGollum.setLocationAndAngles(this.theOwner.posX, this.theOwner.boundingBox.minY, this.theOwner.posZ, this.theGollum.rotationYaw, this.theGollum.rotationPitch);
                    this.theGollum.fallDistance = 0.0f;
                    this.theGollum.getNavigator().clearPathEntity();
                }
            }
        }
    }
}
