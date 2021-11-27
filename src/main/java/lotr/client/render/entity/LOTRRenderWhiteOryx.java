package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityWhiteOryx;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWhiteOryx extends LOTRRenderGemsbok {
    private LOTRRandomSkins oryxSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/whiteOryx");

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return this.oryxSkins.getRandomSkin((LOTREntityWhiteOryx) entity);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        float scale = 0.9f;
        GL11.glScalef(scale, scale, scale);
    }
}
