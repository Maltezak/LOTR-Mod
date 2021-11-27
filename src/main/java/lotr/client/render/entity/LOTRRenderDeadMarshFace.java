package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.model.LOTRModelMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDeadMarshFace extends Render {
    private static ResourceLocation skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");
    private ModelBase model = new LOTRModelMarshWraith();

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return skin;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        LOTREntityDeadMarshFace face = (LOTREntityDeadMarshFace) entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, face.faceAlpha);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(face.rotationYaw, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(face.rotationPitch, 0.0f, 1.0f, 0.0f);
        this.bindEntityTexture(face);
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
