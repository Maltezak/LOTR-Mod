package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderKineAraw extends LOTRRenderAurochs {
    private static LOTRRandomSkins kineSkins;

    public LOTRRenderKineAraw() {
        kineSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/kineAraw");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityKineAraw kine = (LOTREntityKineAraw) entity;
        ResourceLocation skin = kineSkins.getRandomSkin(kine);
        return skin;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        float scale = LOTREntityKineAraw.KINE_SCALE;
        GL11.glScalef(scale, scale, scale);
    }
}
