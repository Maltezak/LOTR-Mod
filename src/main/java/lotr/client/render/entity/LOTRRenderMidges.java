package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelMidge;
import lotr.common.entity.animal.LOTREntityMidges;
import lotr.common.entity.animal.LOTREntityMidges.Midge;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMidges extends RenderLiving {
    private static ResourceLocation midgeTexture = new ResourceLocation("lotr:mob/midge.png");
    private float renderTick;

    public LOTRRenderMidges() {
        super(new LOTRModelMidge(), 0.0f);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderTick = f1;
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bindEntityTexture(entity);
        this.mainModel.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        LOTREntityMidges midges = (LOTREntityMidges) entity;
        for(Midge midge : midges.midges) {
            GL11.glPushMatrix();
            GL11.glTranslatef(midge.midge_prevPosX + (midge.midge_posX - midge.midge_prevPosX) * this.renderTick, midge.midge_prevPosY + (midge.midge_posY - midge.midge_prevPosY) * this.renderTick, midge.midge_prevPosZ + (midge.midge_posZ - midge.midge_prevPosZ) * this.renderTick);
            GL11.glRotatef(midge.midge_rotation, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.2f, 0.2f, 0.2f);
            this.mainModel.render(entity, f, f1, f2, f3, f4, f5);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return midgeTexture;
    }
}
