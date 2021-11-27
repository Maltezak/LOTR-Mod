package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelScorpion;
import lotr.common.entity.animal.*;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderScorpion extends RenderLiving {
    private static ResourceLocation jungleTexture = new ResourceLocation("lotr:mob/scorpion/jungle.png");
    private static ResourceLocation desertTexture = new ResourceLocation("lotr:mob/scorpion/desert.png");

    public LOTRRenderScorpion() {
        super(new LOTRModelScorpion(), 1.0f);
        this.setRenderPassModel(new LOTRModelScorpion());
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if(entity instanceof LOTREntityDesertScorpion) {
            return desertTexture;
        }
        return jungleTexture;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        float scale = ((LOTREntityScorpion) entity).getScorpionScaleAmount();
        GL11.glScalef(scale, scale, scale);
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase entity) {
        return 180.0f;
    }

    @Override
    public float handleRotationFloat(EntityLivingBase entity, float f) {
        float strikeTime = ((LOTREntityScorpion) entity).getStrikeTime();
        if(strikeTime > 0.0f) {
            strikeTime -= f;
        }
        return strikeTime / 20.0f;
    }
}
