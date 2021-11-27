package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelDeer extends ModelBase {
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer leg1Foot;
    private ModelRenderer leg2Foot;
    private ModelRenderer leg3Foot;
    private ModelRenderer leg4Foot;
    private ModelRenderer tail;
    private ModelRenderer head;
    private ModelRenderer antlers;

    public LOTRModelDeer() {
        this(0.0f);
    }

    public LOTRModelDeer(float f) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.body.addBox(-3.5f, -4.0f, -7.0f, 7, 7, 15, f);
        this.leg1 = new ModelRenderer(this, 12, 46);
        this.leg1.setRotationPoint(-4.0f, 14.0f, 5.0f);
        this.leg1.addBox(-1.0f, -2.0f, -2.0f, 3, 6, 4, f);
        this.leg1Foot = new ModelRenderer(this, 12, 56);
        this.leg1Foot.setRotationPoint(0.5f, 4.0f, 0.0f);
        this.leg1Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
        this.leg1.addChild(this.leg1Foot);
        this.leg2 = new ModelRenderer(this, 12, 46);
        this.leg2.setRotationPoint(4.0f, 14.0f, 5.0f);
        this.leg2.mirror = true;
        this.leg2.addBox(-2.0f, -2.0f, -2.0f, 3, 6, 4, f);
        this.leg2Foot = new ModelRenderer(this, 12, 56);
        this.leg2Foot.setRotationPoint(-0.5f, 4.0f, 0.0f);
        this.leg2Foot.mirror = true;
        this.leg2Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
        this.leg2.addChild(this.leg2Foot);
        this.leg3 = new ModelRenderer(this, 0, 47);
        this.leg3.setRotationPoint(-3.0f, 14.0f, -4.0f);
        this.leg3.addBox(-1.5f, -2.0f, -1.5f, 3, 6, 3, f);
        this.leg3Foot = new ModelRenderer(this, 0, 56);
        this.leg3Foot.setRotationPoint(0.0f, 4.0f, 0.0f);
        this.leg3Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
        this.leg3.addChild(this.leg3Foot);
        this.leg4 = new ModelRenderer(this, 0, 47);
        this.leg4.setRotationPoint(3.0f, 14.0f, -4.0f);
        this.leg4.mirror = true;
        this.leg4.addBox(-1.5f, -2.0f, -1.5f, 3, 6, 3, f);
        this.leg4Foot = new ModelRenderer(this, 0, 56);
        this.leg4Foot.setRotationPoint(0.0f, 4.0f, 0.0f);
        this.leg4Foot.mirror = true;
        this.leg4Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
        this.leg4.addChild(this.leg4Foot);
        this.tail = new ModelRenderer(this, 20, 58);
        this.tail.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.tail.addBox(-1.5f, -8.0f, 3.0f, 3, 2, 4, f);
        this.head = new ModelRenderer(this, 0, 22);
        this.head.setRotationPoint(0.0f, 11.0f, -5.0f);
        this.head.addBox(-2.5f, -8.0f, -6.0f, 5, 4, 7, f);
        this.head.setTextureOffset(24, 22).addBox(-2.0f, -4.0f, -4.0f, 4, 7, 4, f);
        ModelRenderer earRight = new ModelRenderer(this, 0, 22);
        earRight.setRotationPoint(-2.0f, -8.0f, 0.0f);
        earRight.addBox(-1.0f, -2.0f, -0.5f, 2, 3, 1, f);
        earRight.rotateAngleY = (float) Math.toRadians(30.0);
        earRight.rotateAngleZ = (float) Math.toRadians(-50.0);
        this.head.addChild(earRight);
        ModelRenderer earLeft = new ModelRenderer(this, 0, 22);
        earLeft.setRotationPoint(2.0f, -8.0f, 0.0f);
        earLeft.mirror = true;
        earLeft.addBox(-1.0f, -2.0f, -0.5f, 2, 3, 1, f);
        earLeft.rotateAngleY = (float) Math.toRadians(-30.0);
        earLeft.rotateAngleZ = (float) Math.toRadians(50.0);
        this.head.addChild(earLeft);
        this.antlers = new ModelRenderer(this, 0, 0);
        this.antlers.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.head.addChild(this.antlers);
        ModelRenderer antlerRight1 = new ModelRenderer(this, 0, 33);
        antlerRight1.setRotationPoint(-2.0f, -7.0f, 1.0f);
        antlerRight1.addBox(-0.5f, -8.0f, -0.5f, 1, 9, 1, f);
        antlerRight1.rotateAngleX = (float) Math.toRadians(-40.0);
        antlerRight1.rotateAngleZ = (float) Math.toRadians(-35.0);
        this.antlers.addChild(antlerRight1);
        ModelRenderer antlerRight2 = new ModelRenderer(this, 4, 33);
        antlerRight2.setRotationPoint(-2.0f, -6.0f, 0.0f);
        antlerRight2.addBox(-0.5f, -6.0f, -0.5f, 1, 6, 1, f);
        antlerRight2.rotateAngleX = (float) Math.toRadians(-60.0);
        antlerRight2.rotateAngleY = (float) Math.toRadians(-50.0);
        antlerRight2.rotateAngleZ = (float) Math.toRadians(-20.0);
        this.antlers.addChild(antlerRight2);
        ModelRenderer antlerLeft1 = new ModelRenderer(this, 0, 33);
        antlerLeft1.setRotationPoint(2.0f, -7.0f, 1.0f);
        antlerLeft1.mirror = true;
        antlerLeft1.addBox(-0.5f, -8.0f, -0.5f, 1, 9, 1, f);
        antlerLeft1.rotateAngleX = (float) Math.toRadians(-40.0);
        antlerLeft1.rotateAngleZ = (float) Math.toRadians(35.0);
        this.antlers.addChild(antlerLeft1);
        ModelRenderer antlerLeft2 = new ModelRenderer(this, 4, 33);
        antlerLeft2.setRotationPoint(2.0f, -6.0f, 0.0f);
        antlerLeft2.mirror = true;
        antlerLeft2.addBox(-0.5f, -6.0f, -0.5f, 1, 6, 1, f);
        antlerLeft2.rotateAngleX = (float) Math.toRadians(-60.0);
        antlerLeft2.rotateAngleY = (float) Math.toRadians(50.0);
        antlerLeft2.rotateAngleZ = (float) Math.toRadians(20.0);
        this.antlers.addChild(antlerLeft2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        LOTREntityDeer deer = (LOTREntityDeer) entity;
        this.antlers.showModel = deer.isMale() && !this.isChild;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.head.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = (float) Math.toRadians(30.0);
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleX += (float) Math.toRadians(f4);
        this.head.rotateAngleY += (float) Math.toRadians(f3);
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.8f) * f1 * 1.4f;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.8f + 3.1415927f) * f1 * 1.4f;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.8f + 3.1415927f) * f1 * 1.4f;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.8f) * f1 * 1.4f;
        this.leg1Foot.rotateAngleX = this.leg1.rotateAngleX * -0.6f;
        this.leg2Foot.rotateAngleX = this.leg2.rotateAngleX * -0.6f;
        this.leg3Foot.rotateAngleX = this.leg3.rotateAngleX * -0.6f;
        this.leg4Foot.rotateAngleX = this.leg4.rotateAngleX * -0.6f;
        this.tail.rotateAngleX = (float) Math.toRadians(-50.0);
    }
}
