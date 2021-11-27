package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRanger extends LOTRRenderDunedain {
    private static LOTRRandomSkins ithilienSkins;

    public LOTRRenderRanger() {
        ithilienSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/ranger");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityRanger ranger = (LOTREntityRanger) entity;
        if(ranger instanceof LOTREntityRangerIthilien) {
            return ithilienSkins.getRandomSkin(ranger);
        }
        return super.getEntityTexture(entity);
    }

    private void doRangerInvisibility() {
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
    }

    private void undoRangerInvisibility() {
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        if(((LOTREntityRanger) entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        int i = super.shouldRenderPass(entity, pass, f);
        if(i > 0 && ((LOTREntityRanger) entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        return i;
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float f) {
        if(((LOTREntityRanger) entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        super.renderEquippedItems(entity, f);
        if(((LOTREntityRanger) entity).isRangerSneaking()) {
            this.undoRangerInvisibility();
        }
    }

    @Override
    protected void renderNPCCape(LOTREntityNPC entity) {
        if(((LOTREntityRanger) entity).isRangerSneaking()) {
            this.doRangerInvisibility();
        }
        super.renderNPCCape(entity);
        if(((LOTREntityRanger) entity).isRangerSneaking()) {
            this.undoRangerInvisibility();
        }
    }
}
