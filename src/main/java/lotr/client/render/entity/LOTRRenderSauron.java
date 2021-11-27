package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelSauron;
import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSauron extends RenderBiped {
    private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/sauron.png");

    public LOTRRenderSauron() {
        super(new LOTRModelSauron(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return skin;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        LOTREntitySauron sauron = (LOTREntitySauron) entity;
        if(sauron.getIsUsingMace()) {
            this.modelBipedMain.heldItemRight = 3;
            this.field_82425_h.heldItemRight = 3;
            this.field_82423_g.heldItemRight = 3;
            this.modelBipedMain.aimedBow = true;
            this.field_82425_h.aimedBow = true;
            this.field_82423_g.aimedBow = true;
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected void func_82422_c() {
        GL11.glTranslatef(0.0f, 0.4375f, 0.0f);
    }
}
