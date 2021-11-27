package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMountainTroll extends LOTRRenderTroll {
    private static LOTRRandomSkins mountainTrollSkins;
    private LOTREntityThrownRock heldRock;

    public LOTRRenderMountainTroll() {
        mountainTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mountainTroll");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return mountainTrollSkins.getRandomSkin((LOTREntityTroll) entity);
    }

    @Override
    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        LOTREntityMountainTroll troll = (LOTREntityMountainTroll) entity;
        if(troll.isThrowingRocks()) {
            if(((LOTRModelTroll) this.mainModel).onGround <= 0.0f) {
                if(this.heldRock == null) {
                    this.heldRock = new LOTREntityThrownRock(troll.worldObj);
                }
                this.heldRock.setWorld(troll.worldObj);
                this.heldRock.setPosition(troll.posX, troll.posY, troll.posZ);
                ((LOTRModelTroll) this.mainModel).rightArm.postRender(0.0625f);
                GL11.glTranslatef(0.375f, 1.5f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                this.scaleTroll(troll, true);
                this.renderManager.renderEntityWithPosYaw(this.heldRock, 0.0, 0.0, 0.0, 0.0f, f);
            }
        }
        else {
            ((LOTRModelTroll) this.mainModel).renderWoodenClubWithSpikes(0.0625f);
        }
    }
}
