package lotr.client.render.entity;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelFish;
import lotr.common.entity.animal.LOTREntityFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderFish extends RenderLiving {
    private static Map<String, LOTRRandomSkins> fishTypeSkins = new HashMap<>();

    public LOTRRenderFish() {
        super(new LOTRModelFish(), 0.0f);
    }

    private LOTRRandomSkins getFishSkins(String s) {
        LOTRRandomSkins skins = fishTypeSkins.get(s);
        if(skins == null) {
            skins = LOTRRandomSkins.loadSkinsList("lotr:mob/fish/" + s);
            fishTypeSkins.put(s, skins);
        }
        return skins;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityFish fish = (LOTREntityFish) entity;
        String type = fish.getFishTextureDir();
        LOTRRandomSkins skins = this.getFishSkins(type);
        return skins.getRandomSkin(fish);
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float f) {
        if(!entity.isInWater()) {
            GL11.glTranslatef(0.0f, -0.05f, 0.0f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        }
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entity, float f) {
        return super.handleRotationFloat(entity, f);
    }
}
