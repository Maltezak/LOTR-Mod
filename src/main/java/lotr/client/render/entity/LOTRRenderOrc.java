package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelOrc;
import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderOrc extends LOTRRenderBiped {
    private static LOTRRandomSkins orcSkins;
    private static LOTRRandomSkins urukSkins;
    private static LOTRRandomSkins blackUrukSkins;
    private LOTRModelOrc eyesModel = new LOTRModelOrc(0.05f);

    public LOTRRenderOrc() {
        super(new LOTRModelOrc(), 0.5f);
        orcSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/orc");
        urukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/urukHai");
        blackUrukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/blackUruk");
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new LOTRModelOrc(1.0f);
        this.field_82425_h = new LOTRModelOrc(0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityOrc orc = (LOTREntityOrc) entity;
        if(orc instanceof LOTREntityUrukHai) {
            return urukSkins.getRandomSkin(orc);
        }
        if(orc instanceof LOTREntityBlackUruk) {
            return blackUrukSkins.getRandomSkin(orc);
        }
        return orcSkins.getRandomSkin(orc);
    }

    @Override
    protected void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.renderModel(entity, f, f1, f2, f3, f4, f5);
        ResourceLocation eyes = LOTRTextures.getEyesTexture(this.getEntityTexture(entity), new int[][] {{9, 11}, {13, 11}}, 2, 1);
        LOTRGlowingEyes.renderGlowingEyes(entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        LOTREntityOrc orc = (LOTREntityOrc) entity;
        if(orc.isWeakOrc) {
            GL11.glScalef(0.85f, 0.85f, 0.85f);
        }
        else if(orc instanceof LOTREntityUrukHaiBerserker) {
            float scale = LOTREntityUrukHaiBerserker.BERSERKER_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
    }
}
