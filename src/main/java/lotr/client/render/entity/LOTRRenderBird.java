package lotr.client.render.entity;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelBird;
import lotr.common.entity.animal.*;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBird extends RenderLiving {
    private static Map<String, LOTRRandomSkins> birdTypeSkins = new HashMap<>();
    public static boolean renderStolenItem = true;

    public LOTRRenderBird() {
        super(new LOTRModelBird(), 0.2f);
    }

    private LOTRRandomSkins getBirdSkins(String s) {
        LOTRRandomSkins skins = birdTypeSkins.get(s);
        if(skins == null) {
            skins = LOTRRandomSkins.loadSkinsList("lotr:mob/bird/" + s);
            birdTypeSkins.put(s, skins);
        }
        return skins;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityBird bird = (LOTREntityBird) entity;
        String type = bird.getBirdTextureDir();
        LOTRRandomSkins skins = this.getBirdSkins(type);
        return skins.getRandomSkin(bird);
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float f) {
        if(entity instanceof LOTREntityCrebain) {
            float scale = LOTREntityCrebain.CREBAIN_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
        else if(entity instanceof LOTREntityGorcrow) {
            float scale = LOTREntityGorcrow.GORCROW_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
        else if(entity instanceof LOTREntitySeagull) {
            float scale = LOTREntitySeagull.SEAGULL_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entity, float f) {
        LOTREntityBird bird = (LOTREntityBird) entity;
        if(bird.isBirdStill() && bird.flapTime > 0) {
            return bird.flapTime - f;
        }
        return super.handleRotationFloat(entity, f);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float f) {
        LOTREntityBird bird = (LOTREntityBird) entity;
        if(renderStolenItem) {
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            ItemStack stolenItem = bird.getStolenItem();
            if(stolenItem != null) {
                GL11.glPushMatrix();
                ((LOTRModelBird) this.mainModel).head.postRender(0.0625f);
                GL11.glTranslatef(0.05f, 1.4f, -0.1f);
                float scale = 0.25f;
                GL11.glScalef(scale, scale, scale);
                this.renderManager.itemRenderer.renderItem(entity, stolenItem, 0);
                GL11.glPopMatrix();
            }
        }
    }
}
