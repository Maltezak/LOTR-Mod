package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelElf extends LOTRModelBiped implements LOTRGlowingEyes.Model {
    private ModelRenderer earRight = new ModelRenderer(this, 0, 0);
    private ModelRenderer earLeft;
    public ModelRenderer bipedChest;

    public LOTRModelElf() {
        this(0.0f);
    }

    public LOTRModelElf(float f) {
        this(f, 64, f == 0.0f ? 64 : 32);
    }

    public LOTRModelElf(float f, int width, int height) {
        super(f, 0.0f, width, height);
        this.earRight.addBox(-4.0f, -6.5f, -1.0f, 1, 4, 2);
        this.earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earRight.rotateAngleZ = -0.2617994f;
        this.earLeft = new ModelRenderer(this, 0, 0);
        this.earLeft.mirror = true;
        this.earLeft.addBox(3.0f, -6.5f, -1.0f, 1, 4, 2);
        this.earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earLeft.rotateAngleZ = 0.2617994f;
        this.bipedHead.addChild(this.earRight);
        this.bipedHead.addChild(this.earLeft);
        this.bipedChest = new ModelRenderer(this, 24, 0);
        this.bipedChest.addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
        this.bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addChild(this.bipedChest);
        if(height == 64) {
            this.bipedHeadwear = new ModelRenderer(this, 0, 32);
            this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 16, 8, 0.5f + f);
            this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            this.bipedHead.render(f5);
            this.bipedHeadwear.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.bipedHead.render(f5);
            this.bipedHeadwear.render(f5);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        LOTREntityElf elf;
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(entity instanceof LOTREntityElf && (elf = (LOTREntityElf) entity).isJazz() && elf.isSolo()) {
            this.bipedRightArm.rotateAngleY = (float) Math.toRadians(-45.0);
            this.bipedLeftArm.rotateAngleY = -this.bipedRightArm.rotateAngleY;
            this.bipedLeftArm.rotateAngleX = this.bipedRightArm.rotateAngleX = (float) Math.toRadians(-50.0);
        }
    }

    @Override
    public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedHead.render(f5);
    }
}
