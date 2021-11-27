package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTermite;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderTermite extends RenderLiving {
    private static ResourceLocation texture = new ResourceLocation("lotr:mob/termite.png");

    public LOTRRenderTermite() {
        super(new LOTRModelTermite(), 0.2f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        float scale = 0.25f;
        GL11.glScalef(scale, scale, scale);
    }
}
