package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderStoneTroll extends Render {
    private static ResourceLocation texture = new ResourceLocation("lotr:mob/troll/stone.png");
    private static LOTRModelTroll model = new LOTRModelTroll();
    private static LOTRModelTroll shirtModel = new LOTRModelTroll(1.0f, 0);
    private static LOTRModelTroll trousersModel = new LOTRModelTroll(0.75f, 1);

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float) d, (float) d1 + 1.5f, (float) d2);
        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.bindTexture(LOTRRenderTroll.trollOutfits[((LOTREntityStoneTroll) entity).getTrollOutfit()]);
        shirtModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        trousersModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
}
