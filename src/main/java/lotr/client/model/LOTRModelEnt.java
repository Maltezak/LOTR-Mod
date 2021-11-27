package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelEnt extends ModelBase implements LOTRGlowingEyes.Model {
    public ModelRenderer trunk;
    public ModelRenderer browRight;
    public ModelRenderer browLeft;
    public ModelRenderer eyeRight;
    public ModelRenderer eyeLeft;
    public ModelRenderer nose;
    public ModelRenderer beard;
    public ModelRenderer trophyBottomPanel;
    public ModelRenderer rightArm;
    public ModelRenderer rightHand;
    public ModelRenderer leftArm;
    public ModelRenderer leftHand;
    public ModelRenderer rightLeg;
    public ModelRenderer rightFoot;
    public ModelRenderer leftLeg;
    public ModelRenderer leftFoot;
    public ModelRenderer branches;

    public LOTRModelEnt() {
        this(0.0f);
    }

    public LOTRModelEnt(float f) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.trunk = new ModelRenderer(this, 0, 0);
        this.trunk.addBox(-8.0f, -48.0f, -6.0f, 16, 48, 12, f);
        this.trunk.setRotationPoint(0.0f, -10.0f, 0.0f);
        this.browRight = new ModelRenderer(this, 56, 26);
        this.browRight.addBox(-6.5f, 0.0f, -8.0f, 5, 1, 2, f);
        this.browRight.setRotationPoint(0.0f, -39.0f, 0.0f);
        this.browRight.rotateAngleZ = (float) Math.toRadians(10.0);
        this.trunk.addChild(this.browRight);
        this.browLeft = new ModelRenderer(this, 56, 26);
        this.browLeft.mirror = true;
        this.browLeft.addBox(1.5f, 0.0f, -8.0f, 5, 1, 2, f);
        this.browLeft.setRotationPoint(0.0f, -39.0f, 0.0f);
        this.browLeft.rotateAngleZ = (float) Math.toRadians(-10.0);
        this.trunk.addChild(this.browLeft);
        this.eyeRight = new ModelRenderer(this, 56, 29);
        this.eyeRight.addBox(-1.5f, -2.0f, -7.0f, 3, 3, 1, f + 0.2f);
        this.eyeRight.setRotationPoint(-3.5f, -36.0f, 0.0f);
        this.trunk.addChild(this.eyeRight);
        this.eyeLeft = new ModelRenderer(this, 56, 29);
        this.eyeLeft.mirror = true;
        this.eyeLeft.addBox(-1.5f, -2.0f, -7.0f, 3, 3, 1, f + 0.2f);
        this.eyeLeft.setRotationPoint(3.5f, -36.0f, 0.0f);
        this.trunk.addChild(this.eyeLeft);
        this.nose = new ModelRenderer(this, 56, 33);
        this.nose.addBox(-1.5f, -2.0f, -9.0f, 3, 6, 3, f);
        this.nose.setRotationPoint(0.0f, -36.0f, 0.0f);
        this.trunk.addChild(this.nose);
        this.beard = new ModelRenderer(this, 56, 0);
        this.beard.addBox(-5.0f, 0.0f, -8.0f, 10, 24, 2, f);
        this.beard.setRotationPoint(0.0f, -31.0f, 0.0f);
        this.trunk.addChild(this.beard);
        this.trophyBottomPanel = new ModelRenderer(this, 72, 116);
        this.trophyBottomPanel.setRotationPoint(0.0f, -24.0f, 0.0f);
        this.trophyBottomPanel.addBox(-8.0f, 0.0f, -6.0f, 16, 0, 12, f);
        this.trunk.addChild(this.trophyBottomPanel);
        this.trophyBottomPanel.showModel = false;
        this.rightArm = new ModelRenderer(this, 96, 28);
        this.rightArm.addBox(-8.0f, 0.0f, -4.0f, 8, 12, 8, f);
        this.rightArm.setTextureOffset(112, 48).addBox(-7.0f, 12.0f, -2.0f, 4, 16, 4, f);
        this.rightArm.setRotationPoint(-8.0f, -38.0f, 0.0f);
        this.trunk.addChild(this.rightArm);
        this.rightHand = new ModelRenderer(this, 102, 68);
        this.rightHand.addBox(-2.5f, 0.0f, -4.0f, 5, 16, 8, f);
        this.rightHand.setTextureOffset(102, 92).addBox(-2.0f, 16.0f, -4.0f, 3, 10, 2, f);
        this.rightHand.setTextureOffset(112, 92).addBox(-2.0f, 16.0f, -1.0f, 2, 8, 2, f);
        this.rightHand.setTextureOffset(120, 92).addBox(-2.0f, 16.0f, 2.0f, 2, 6, 2, f);
        this.rightHand.setRotationPoint(-5.0f, 28.0f, 0.0f);
        this.rightArm.addChild(this.rightHand);
        this.leftArm = new ModelRenderer(this, 96, 28);
        this.leftArm.mirror = true;
        this.leftArm.addBox(0.0f, 0.0f, -4.0f, 8, 12, 8, f);
        this.leftArm.setTextureOffset(112, 48).addBox(3.0f, 12.0f, -2.0f, 4, 16, 4, f);
        this.leftArm.setRotationPoint(8.0f, -38.0f, 0.0f);
        this.trunk.addChild(this.leftArm);
        this.leftHand = new ModelRenderer(this, 102, 68);
        this.leftHand.mirror = true;
        this.leftHand.addBox(-2.5f, 0.0f, -4.0f, 5, 16, 8, f);
        this.leftHand.setTextureOffset(102, 92).addBox(-1.0f, 16.0f, -4.0f, 3, 10, 2, f);
        this.leftHand.setTextureOffset(112, 92).addBox(0.0f, 16.0f, -1.0f, 2, 8, 2, f);
        this.leftHand.setTextureOffset(120, 92).addBox(0.0f, 16.0f, 2.0f, 2, 6, 2, f);
        this.leftHand.setRotationPoint(5.0f, 28.0f, 0.0f);
        this.leftArm.addChild(this.leftHand);
        this.rightLeg = new ModelRenderer(this, 0, 60);
        this.rightLeg.addBox(-7.0f, -4.0f, -4.0f, 6, 22, 8, f);
        this.rightLeg.setRotationPoint(-4.0f, -12.0f, 0.0f);
        this.rightFoot = new ModelRenderer(this, 28, 60);
        this.rightFoot.addBox(-4.0f, 0.0f, -5.0f, 8, 12, 10, f);
        this.rightFoot.setTextureOffset(0, 90).addBox(-5.0f, 12.0f, -7.0f, 10, 6, 15, f);
        this.rightFoot.setTextureOffset(0, 111).addBox(2.0f, 13.0f, -16.0f, 3, 5, 9, f);
        this.rightFoot.setTextureOffset(24, 113).addBox(-2.0f, 14.0f, -15.0f, 3, 4, 8, f);
        this.rightFoot.setTextureOffset(46, 115).addBox(-5.0f, 15.0f, -14.0f, 2, 3, 7, f);
        this.rightFoot.setRotationPoint(-4.0f, 18.0f, 0.0f);
        this.rightLeg.addChild(this.rightFoot);
        this.leftLeg = new ModelRenderer(this, 0, 60);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(1.0f, -4.0f, -4.0f, 6, 22, 8, f);
        this.leftLeg.setRotationPoint(4.0f, -12.0f, 0.0f);
        this.leftFoot = new ModelRenderer(this, 28, 60);
        this.leftFoot.mirror = true;
        this.leftFoot.addBox(-4.0f, 0.0f, -5.0f, 8, 12, 10, f);
        this.leftFoot.setTextureOffset(0, 90).addBox(-5.0f, 12.0f, -7.0f, 10, 6, 15, f);
        this.leftFoot.setTextureOffset(0, 111).addBox(-5.0f, 13.0f, -16.0f, 3, 5, 9, f);
        this.leftFoot.setTextureOffset(24, 113).addBox(-1.0f, 14.0f, -15.0f, 3, 4, 8, f);
        this.leftFoot.setTextureOffset(46, 115).addBox(3.0f, 15.0f, -14.0f, 2, 3, 7, f);
        this.leftFoot.setRotationPoint(4.0f, 18.0f, 0.0f);
        this.leftLeg.addChild(this.leftFoot);
        this.branches = new ModelRenderer(this, 0, 0);
        this.branches.setRotationPoint(0.0f, -48.0f, 0.0f);
        ModelRenderer branch1 = new ModelRenderer(this, 80, 16);
        branch1.addBox(-1.5f, -28.0f, -1.5f, 3, 32, 3, f);
        branch1.setTextureOffset(80, 0).addBox(-3.5f, -32.0f, -3.5f, 7, 7, 7, f);
        branch1.setRotationPoint(-1.0f, 0.0f, 0.0f);
        this.setRotation(branch1, -7.0f, 17.0f, 0.0f);
        this.branches.addChild(branch1);
        ModelRenderer branch1twig1 = new ModelRenderer(this, 80, 16);
        branch1twig1.addBox(-7.5f, -22.0f, -1.5f, 1, 12, 1, f);
        branch1twig1.setTextureOffset(80, 0).addBox(-8.5f, -23.0f, -2.5f, 3, 3, 3, f);
        branch1twig1.setRotationPoint(1.0f, -5.0f, -7.0f);
        this.setRotation(branch1twig1, -50.0f, 25.0f, 15.0f);
        this.branches.addChild(branch1twig1);
        ModelRenderer branch1twig2 = new ModelRenderer(this, 80, 16);
        branch1twig2.addBox(-14.0f, -26.0f, -5.5f, 2, 12, 2, f);
        branch1twig2.setTextureOffset(80, 0).addBox(-15.5f, -28.0f, -7.0f, 5, 5, 5, f);
        branch1twig2.setRotationPoint(-2.0f, 1.0f, 7.0f);
        this.setRotation(branch1twig2, 10.0f, 10.0f, 50.0f);
        this.branches.addChild(branch1twig2);
        ModelRenderer branch1twig3 = new ModelRenderer(this, 80, 16);
        branch1twig3.addBox(-7.5f, -24.0f, -3.5f, 1, 12, 1, f);
        branch1twig3.setTextureOffset(80, 0).addBox(-8.5f, -25.0f, -4.5f, 3, 3, 3, f);
        branch1twig3.setRotationPoint(8.0f, -6.0f, 9.0f);
        this.setRotation(branch1twig3, 15.0f, -20.0f, -30.0f);
        this.branches.addChild(branch1twig3);
        ModelRenderer branch2 = new ModelRenderer(this, 80, 16);
        branch2.addBox(-0.5f, -10.0f, -0.5f, 1, 14, 1, f);
        branch2.setTextureOffset(80, 0).addBox(-1.5f, -12.0f, -1.5f, 3, 3, 3, f);
        branch2.setRotationPoint(6.0f, 0.0f, 2.0f);
        this.setRotation(branch2, -20.0f, 42.0f, 0.0f);
        this.branches.addChild(branch2);
        ModelRenderer branch3 = new ModelRenderer(this, 80, 16);
        branch3.addBox(-1.0f, -16.0f, -1.0f, 2, 20, 2, f);
        branch3.setTextureOffset(80, 0).addBox(-2.5f, -18.0f, -2.5f, 5, 5, 5, f);
        branch3.setRotationPoint(3.0f, 0.0f, -3.0f);
        this.setRotation(branch3, 26.0f, -27.0f, 0.0f);
        this.branches.addChild(branch3);
        ModelRenderer branch4 = new ModelRenderer(this, 80, 16);
        branch4.addBox(-1.0f, -18.0f, -1.0f, 2, 22, 2, f);
        branch4.setTextureOffset(80, 0).addBox(-2.5f, -20.0f, -2.5f, 5, 5, 5, f);
        branch4.setRotationPoint(-5.0f, 0.0f, -4.0f);
        this.setRotation(branch4, 17.0f, 60.0f, 0.0f);
        this.branches.addChild(branch4);
        ModelRenderer branch4twig1 = new ModelRenderer(this, 80, 16);
        branch4twig1.addBox(8.5f, -21.0f, -7.5f, 1, 12, 1, f);
        branch4twig1.setTextureOffset(80, 0).addBox(7.0f, -22.0f, -9.0f, 4, 4, 4, f);
        branch4twig1.setRotationPoint(-12.0f, -6.0f, 8.0f);
        this.setRotation(branch4twig1, 50.0f, 15.0f, -10.0f);
        this.branches.addChild(branch4twig1);
        ModelRenderer branch5 = new ModelRenderer(this, 80, 16);
        branch5.addBox(-1.0f, -24.0f, -1.0f, 2, 28, 2, f);
        branch5.setTextureOffset(80, 0).addBox(-2.0f, -25.0f, -2.0f, 4, 4, 4, f);
        branch5.setRotationPoint(-5.0f, 0.0f, 3.0f);
        this.setRotation(branch5, -20.0f, -36.0f, 0.0f);
        this.branches.addChild(branch5);
        this.trunk.addChild(this.branches);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        LOTREntityEnt ent = (LOTREntityEnt) entity;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
        this.trunk.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        if(ent != null) {
            int numBranches = ent.getExtraHeadBranches();
            for(int l = 0; l < numBranches; ++l) {
                GL11.glPushMatrix();
                this.trunk.postRender(f5);
                float angle = 90.0f + (float) l / (float) numBranches * 360.0f;
                GL11.glTranslatef(0.0f, -2.7f, 0.0f);
                GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-60.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, 2.6f, 0.0f);
                this.branches.render(f5);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        boolean healing;
        LOTREntityEnt ent = (LOTREntityEnt) entity;
        this.trunk.rotateAngleX = 0.0f;
        healing = ent != null && ent.isHealingSapling();
        if(healing) {
            this.trunk.rotateAngleX = 0.3f + MathHelper.sin(f2 * 0.08f) * 0.1f;
        }
        this.eyeRight.showModel = ent != null && ent.eyesClosed > 0;
        this.eyeLeft.showModel = ent != null && ent.eyesClosed > 0;
        if(ent != null && ent.hurtTime > 0) {
            this.browRight.rotateAngleZ = (float) Math.toRadians(30.0);
            this.browLeft.rotateAngleZ = -this.browRight.rotateAngleZ;
            this.browLeft.rotationPointY = -40.0f;
            this.browRight.rotationPointY = -40.0f;
        }
        else {
            this.browRight.rotateAngleZ = (float) Math.toRadians(10.0);
            this.browLeft.rotateAngleZ = -this.browRight.rotateAngleZ;
            this.browLeft.rotationPointY = -39.0f;
            this.browRight.rotationPointY = -39.0f;
        }
        this.rightArm.rotateAngleX = 0.0f;
        this.rightHand.rotateAngleX = 0.0f;
        this.leftArm.rotateAngleX = 0.0f;
        this.leftHand.rotateAngleX = 0.0f;
        this.rightArm.rotateAngleZ = 0.0f;
        this.leftArm.rotateAngleZ = 0.0f;
        if(this.onGround > -9990.0f) {
            float f6 = this.onGround;
            f6 = 1.0f - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            float f8 = MathHelper.sin(f6 * 3.1415927f);
            float f9 = MathHelper.sin(this.onGround * 3.1415927f) * -(this.trunk.rotateAngleX - 0.7f) * 0.75f;
            this.rightArm.rotateAngleX -= f8 * 1.2f + f9;
            this.leftArm.rotateAngleX -= f8 * 1.2f + f9;
        }
        this.rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.rightArm.rotateAngleX += MathHelper.cos(f * 0.3f + 3.1415927f) * 0.8f * f1;
        this.leftArm.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.8f * f1;
        if(healing) {
            float armHealing = -0.5f + MathHelper.sin(f2 * 0.2f) * 0.4f;
            this.rightArm.rotateAngleX += armHealing;
            this.leftArm.rotateAngleX += armHealing;
        }
        if(this.rightArm.rotateAngleX < 0.0f) {
            this.rightHand.rotateAngleX = this.rightArm.rotateAngleX / 3.1415927f * 2.5f;
        }
        if(this.leftArm.rotateAngleX < 0.0f) {
            this.leftHand.rotateAngleX = this.leftArm.rotateAngleX / 3.1415927f * 2.5f;
        }
        this.rightLeg.rotateAngleX = 0.0f;
        this.rightFoot.rotateAngleX = 0.0f;
        this.leftLeg.rotateAngleX = 0.0f;
        this.leftFoot.rotateAngleX = 0.0f;
        this.rightLeg.rotateAngleX += MathHelper.cos(f * 0.3f + 3.1415927f) * 1.2f * f1;
        this.leftLeg.rotateAngleX += MathHelper.cos(f * 0.3f) * 1.2f * f1;
        if(this.rightLeg.rotateAngleX < 0.0f) {
            this.rightFoot.rotateAngleX = -(this.rightLeg.rotateAngleX / 3.1415927f) * 2.5f;
        }
        if(this.leftLeg.rotateAngleX < 0.0f) {
            this.leftFoot.rotateAngleX = -(this.leftLeg.rotateAngleX / 3.1415927f) * 2.5f;
        }
    }

    private void setRotation(ModelRenderer part, float x, float y, float z) {
        part.rotateAngleX = (float) Math.toRadians(x);
        part.rotateAngleY = (float) Math.toRadians(y);
        part.rotateAngleZ = (float) Math.toRadians(z);
    }

    @Override
    public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.trunk.render(f5);
    }
}
