package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;

public class LOTRRenderGaladhrimWarden extends LOTRRenderElf {
    private void doElfInvisibility() {
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.05f);
    }

    private void undoElfInvisibility() {
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        if(((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        int j = super.shouldRenderPass(entity, pass, f);
        if(j > 0 && ((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
        return j;
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float f) {
        if(((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.doElfInvisibility();
            return;
        }
        super.renderEquippedItems(entity, f);
        if(((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.undoElfInvisibility();
        }
    }

    @Override
    protected void renderNPCCape(LOTREntityNPC entity) {
        if(((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.doElfInvisibility();
        }
        super.renderNPCCape(entity);
        if(((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
            this.undoElfInvisibility();
        }
    }
}
