package lotr.client;

import java.util.List;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;

public class LOTREntityRenderer extends EntityRenderer {
    private Minecraft theMC;
    private Entity thePointedEntity;

    public LOTREntityRenderer(Minecraft mc, IResourceManager irm) {
        super(mc, irm);
        this.theMC = mc;
    }

    @Override
    public void getMouseOver(float partialTick) {
        if(this.theMC.renderViewEntity != null && this.theMC.theWorld != null) {
            double reach;
            this.theMC.pointedEntity = null;
            this.thePointedEntity = null;
            double blockReach = this.theMC.playerController.getBlockReachDistance();
            float meleeReachFactor = LOTRWeaponStats.getMeleeReachFactor(this.theMC.thePlayer.getHeldItem());
            this.theMC.objectMouseOver = this.theMC.renderViewEntity.rayTrace(blockReach *= meleeReachFactor, partialTick);
            double maxDist = reach = LOTRWeaponStats.getMeleeReachDistance(this.theMC.thePlayer);
            Vec3 posVec = this.theMC.renderViewEntity.getPosition(partialTick);
            if(this.theMC.objectMouseOver != null) {
                maxDist = this.theMC.objectMouseOver.hitVec.distanceTo(posVec);
            }
            Vec3 lookVec = this.theMC.renderViewEntity.getLook(partialTick);
            Vec3 sightVec = posVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
            Vec3 targetVec = null;
            float lookWidth = LOTRWeaponStats.getMeleeExtraLookWidth();
            List entities = this.theMC.theWorld.getEntitiesWithinAABBExcludingEntity(this.theMC.renderViewEntity, this.theMC.renderViewEntity.boundingBox.addCoord(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach).expand(lookWidth, lookWidth, lookWidth));
            double leastDist = maxDist;
            for(Object entitie : entities) {
                double entityDist;
                Entity entity = (Entity) entitie;
                if(!entity.canBeCollidedWith()) continue;
                float f = entity.getCollisionBorderSize();
                AxisAlignedBB entityBB = entity.boundingBox.expand(f, f, f);
                MovingObjectPosition movingobjectposition = entityBB.calculateIntercept(posVec, sightVec);
                if(entityBB.isVecInside(posVec)) {
                    if((0.0 >= leastDist) && leastDist != 0.0) continue;
                    this.thePointedEntity = entity;
                    targetVec = movingobjectposition == null ? posVec : movingobjectposition.hitVec;
                    leastDist = 0.0;
                    continue;
                }
                if(movingobjectposition == null || ((entityDist = posVec.distanceTo(movingobjectposition.hitVec)) >= leastDist) && leastDist != 0.0) continue;
                if(entity == this.theMC.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
                    if(leastDist != 0.0) continue;
                    this.thePointedEntity = entity;
                    targetVec = movingobjectposition.hitVec;
                    continue;
                }
                this.thePointedEntity = entity;
                targetVec = movingobjectposition.hitVec;
                leastDist = entityDist;
            }
            if(this.thePointedEntity != null && (leastDist < maxDist || this.theMC.objectMouseOver == null)) {
                this.theMC.objectMouseOver = new MovingObjectPosition(this.thePointedEntity, targetVec);
                if(this.thePointedEntity instanceof EntityLivingBase || this.thePointedEntity instanceof EntityItemFrame) {
                    this.theMC.pointedEntity = this.thePointedEntity;
                }
            }
        }
    }

    @Override
    public void updateRenderer() {
        super.updateRenderer();
        if(Minecraft.isGuiEnabled()) {
            float wight = LOTRClientProxy.tickHandler.getWightLookFactor();
            float hand = LOTRReflectionClient.getHandFOV(this);
            LOTRReflectionClient.setHandFOV(this, hand + wight * 0.3f);
        }
    }
}
