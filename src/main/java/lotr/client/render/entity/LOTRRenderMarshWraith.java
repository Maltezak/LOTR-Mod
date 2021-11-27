package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelMarshWraith;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMarshWraith extends RenderLiving {
    private static ResourceLocation skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");

    public LOTRRenderMarshWraith() {
        super(new LOTRModelMarshWraith(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return skin;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        float f1 = 0.9375f;
        GL11.glScalef(f1, f1, f1);
        LOTREntityMarshWraith wraith = (LOTREntityMarshWraith) entity;
        if(wraith.getSpawnFadeTime() < 30) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getSpawnFadeTime() / 30.0f);
        }
        else if(wraith.getDeathFadeTime() > 0) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getDeathFadeTime() / 30.0f);
        }
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase entity) {
        return 0.0f;
    }
}
