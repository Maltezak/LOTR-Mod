package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityShirePony;
import net.minecraft.entity.EntityLivingBase;

public class LOTRRenderShirePony extends LOTRRenderHorse {
    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        float scale = LOTREntityShirePony.PONY_SCALE;
        GL11.glScalef(scale, scale, scale);
    }
}
