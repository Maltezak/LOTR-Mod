package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelBalrog;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRRenderBalrog
extends RenderLiving {
    private static LOTRRandomSkins balrogSkins;
    private static LOTRRandomSkins balrogSkinsBright;
    private static final ResourceLocation fireTexture;
    private LOTRModelBalrog balrogModel;
    private LOTRModelBalrog balrogModelBright;
    private LOTRModelBalrog fireModel;

    public LOTRRenderBalrog() {
        super(new LOTRModelBalrog(), 0.5f);
        this.balrogModel = (LOTRModelBalrog)this.mainModel;
        this.balrogModelBright = new LOTRModelBalrog(0.05f);
        this.fireModel = new LOTRModelBalrog(0.0f);
        this.fireModel.setFireModel();
        balrogSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog");
        balrogSkinsBright = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog_bright");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return balrogSkins.getRandomSkin((LOTREntityBalrog)entity);
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        ItemStack heldItem = balrog.getHeldItem();
        this.fireModel.heldItemRight = heldItem == null ? 0 : 2;
        this.balrogModel.heldItemRight = this.fireModel.heldItemRight;
        super.doRender(balrog, d, d1, d2, f, f1);
    }

    protected void preRenderCallback(EntityLivingBase entity, float f) {
        LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        float scale = 2.0f;
        GL11.glScalef(scale, scale, scale);
        if (balrog.isBalrogCharging()) {
            float lean = balrog.getInterpolatedChargeLean(f);
            GL11.glRotatef(lean *= 35.0f, 1.0f, 0.0f, 0.0f);
        }
    }

    private void setupFullBright() {
        int light = 15728880;
        int lx = light % 65536;
        int ly = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
        if (balrog.isWreathedInFlame()) {
            if (pass == 1) {
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                float f1 = balrog.ticksExisted + f;
                float f2 = f1 * 0.01f;
                float f3 = f1 * 0.01f;
                GL11.glTranslatef(f2, f3, 0.0f);
                GL11.glMatrixMode(5888);
                GL11.glAlphaFunc(516, 0.01f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                float alpha = balrog.isBalrogCharging() ? 0.6f + MathHelper.sin(f1 * 0.1f) * 0.15f : 0.3f + MathHelper.sin(f1 * 0.05f) * 0.15f;
                GL11.glColor4f(alpha, alpha, alpha, 1.0f);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                this.setRenderPassModel(this.fireModel);
                this.bindTexture(fireTexture);
                return 1;
            }
            if (pass == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glDepthMask(true);
                GL11.glDisable(2896);
                this.setupFullBright();
                this.setRenderPassModel(this.balrogModelBright);
                this.bindTexture(balrogSkinsBright.getRandomSkin(balrog));
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                return 1;
            }
            if (pass == 3) {
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        return -1;
    }

    protected void renderEquippedItems(EntityLivingBase entity, float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        ItemStack heldItem = entity.getHeldItem();
        if (heldItem != null) {
            GL11.glPushMatrix();
            this.balrogModel.body.postRender(0.0625f);
            this.balrogModel.rightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.25f, 1.5f, -0.125f);
            float scale = 1.25f;
            GL11.glScalef(scale, (-scale), scale);
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            this.renderManager.itemRenderer.renderItem(entity, heldItem, 0);
            if (heldItem.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++x) {
                    this.renderManager.itemRenderer.renderItem(entity, heldItem, x);
                }
            }
            GL11.glPopMatrix();
        }
    }

    static {
        fireTexture = new ResourceLocation("lotr:mob/balrog/fire.png");
    }
}

