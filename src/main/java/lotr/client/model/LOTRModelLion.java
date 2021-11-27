package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelLion extends ModelBase {
    protected ModelRenderer head;
    protected ModelRenderer mane;
    protected ModelRenderer body;
    protected ModelRenderer leg1;
    protected ModelRenderer leg2;
    protected ModelRenderer leg3;
    protected ModelRenderer leg4;
    protected ModelRenderer tail;

    public LOTRModelLion() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 48, 0);
        this.head.setRotationPoint(0.0f, 3.0f, -10.0f);
        this.head.addBox(-5.0f, -6.0f, -10.0f, 10, 10, 10);
        this.head.setTextureOffset(78, 0).addBox(-3.0f, -1.0f, -14.0f, 6, 5, 4);
        this.head.setTextureOffset(98, 0).addBox(-1.0f, -2.0f, -14.2f, 2, 2, 5);
        this.head.setTextureOffset(0, 0);
        this.head.addBox(-4.0f, -9.0f, -7.5f, 3, 3, 1);
        this.head.mirror = true;
        this.head.addBox(1.0f, -9.0f, -7.5f, 3, 3, 1);
        this.mane = new ModelRenderer(this, 0, 0);
        this.mane.setRotationPoint(0.0f, 3.0f, -10.0f);
        this.mane.addBox(-8.0f, -10.0f, -6.0f, 16, 16, 8);
        this.body = new ModelRenderer(this, 0, 24);
        this.body.setRotationPoint(0.0f, 6.0f, 1.0f);
        this.body.addBox(-7.0f, -6.5f, -11.0f, 14, 14, 24);
        this.leg1 = new ModelRenderer(this, 52, 24);
        this.leg1.setRotationPoint(-4.0f, 4.0f, 11.0f);
        this.leg1.addBox(-6.0f, -2.0f, -3.5f, 6, 10, 8);
        this.leg1.setTextureOffset(106, 24).addBox(-5.5f, 8.0f, -2.5f, 5, 12, 5);
        this.leg2 = new ModelRenderer(this, 52, 24);
        this.leg2.setRotationPoint(4.0f, 4.0f, 11.0f);
        this.leg2.mirror = true;
        this.leg2.addBox(0.0f, -2.0f, -3.5f, 6, 10, 8);
        this.leg2.setTextureOffset(106, 24).addBox(0.5f, 8.0f, -2.5f, 5, 12, 5);
        this.leg3 = new ModelRenderer(this, 80, 24);
        this.leg3.setRotationPoint(-4.0f, 5.0f, -5.0f);
        this.leg3.addBox(-6.0f, -2.0f, -3.5f, 6, 9, 7);
        this.leg3.setTextureOffset(106, 24).addBox(-5.5f, 7.0f, -2.5f, 5, 12, 5);
        this.leg4 = new ModelRenderer(this, 80, 24);
        this.leg4.setRotationPoint(4.0f, 5.0f, -5.0f);
        this.leg4.mirror = true;
        this.leg4.addBox(0.0f, -2.0f, -3.5f, 6, 9, 7);
        this.leg4.setTextureOffset(106, 24).addBox(0.5f, 7.0f, -2.5f, 5, 12, 5);
        this.tail = new ModelRenderer(this, 100, 50);
        this.tail.setRotationPoint(0.0f, 4.0f, 13.0f);
        this.tail.addBox(-1.0f, -1.0f, 0.0f, 2, 2, 12);
        this.tail.setTextureOffset(86, 57).addBox(-1.5f, -1.5f, 12.0f, 3, 3, 4);
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
            this.tail.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.mane.render(f5);
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
        this.head.rotateAngleX = (float) Math.toRadians(f4);
        this.head.rotateAngleY = (float) Math.toRadians(f3);
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
        this.tail.rotateAngleX = (float) Math.toRadians(-60.0);
        this.tail.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.5f * f1;
    }
}
