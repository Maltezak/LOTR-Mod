package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBear extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer nose;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public LOTRModelBear() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 32, 0);
        this.head.setRotationPoint(0.0f, 8.0f, -9.0f);
        this.head.addBox(-4.0f, -5.0f, -4.0f, 8, 9, 6);
        this.head.setTextureOffset(0, 0).addBox(-4.5f, -5.5f, -11.0f, 9, 10, 7);
        this.nose = new ModelRenderer(this, 0, 17);
        this.nose.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.nose.addBox(-2.5f, -2.0f, -17.0f, 5, 6, 6);
        this.nose.setTextureOffset(0, 29).addBox(-1.5f, -2.5f, -17.5f, 3, 3, 7);
        this.head.addChild(this.nose);
        ModelRenderer earRight = new ModelRenderer(this, 23, 17);
        earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
        earRight.addBox(-4.0f, -8.0f, -6.0f, 3, 3, 1);
        earRight.rotateAngleZ = (float) Math.toRadians(-15.0);
        this.head.addChild(earRight);
        ModelRenderer earLeft = new ModelRenderer(this, 23, 17);
        earLeft.mirror = true;
        earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
        earLeft.addBox(1.0f, -8.0f, -6.0f, 3, 3, 1);
        earLeft.rotateAngleZ = (float) Math.toRadians(15.0);
        this.head.addChild(earLeft);
        this.body = new ModelRenderer(this, 40, 0);
        this.body.setRotationPoint(0.0f, 10.0f, -2.0f);
        this.body.addBox(-6.0f, -8.0f, -9.0f, 12, 14, 28);
        this.body.setTextureOffset(92, 0).addBox(-2.5f, -6.0f, 19.0f, 5, 5, 2);
        this.leg1 = new ModelRenderer(this, 56, 44);
        this.leg1.setRotationPoint(-4.0f, 6.0f, 10.0f);
        this.leg1.addBox(-6.0f, -2.0f, -3.5f, 6, 9, 9);
        this.leg1.setTextureOffset(86, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 11, 5);
        this.leg2 = new ModelRenderer(this, 56, 44);
        this.leg2.mirror = true;
        this.leg2.setRotationPoint(4.0f, 6.0f, 10.0f);
        this.leg2.addBox(0.0f, -2.0f, -3.5f, 6, 9, 9);
        this.leg2.setTextureOffset(86, 44).addBox(0.5f, 7.0f, -1.5f, 5, 11, 5);
        this.leg3 = new ModelRenderer(this, 0, 44);
        this.leg3.setRotationPoint(-3.0f, 6.0f, -5.0f);
        this.leg3.addBox(-6.0f, -2.0f, -3.0f, 6, 9, 8);
        this.leg3.setTextureOffset(28, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 12, 5);
        this.leg4 = new ModelRenderer(this, 0, 44);
        this.leg4.mirror = true;
        this.leg4.setRotationPoint(3.0f, 6.0f, -5.0f);
        this.leg4.addBox(0.0f, -2.0f, -3.0f, 6, 9, 8);
        this.leg4.setTextureOffset(28, 44).addBox(0.5f, 7.0f, -1.5f, 5, 12, 5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
            this.head.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = (float) Math.toRadians(10.0);
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleX += (float) Math.toRadians(f4);
        this.head.rotateAngleY += (float) Math.toRadians(f3);
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
        this.nose.rotationPointZ = this.isChild ? 3.0f : 0.0f;
    }
}
