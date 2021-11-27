package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRhino extends ModelBase {
    private ModelRenderer head;
    private ModelRenderer neck;
    private ModelRenderer horn1;
    private ModelRenderer horn2;
    private ModelRenderer body;
    private ModelRenderer tail;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;

    public LOTRModelRhino() {
        this(0.0f);
    }

    public LOTRModelRhino(float f) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0f, 3.0f, -12.0f);
        this.head.addBox(-5.0f, -2.0f, -22.0f, 10, 10, 16, f);
        this.head.addBox(-4.0f, -4.0f, -10.0f, 1, 2, 2, f);
        this.head.mirror = true;
        this.head.addBox(3.0f, -4.0f, -10.0f, 1, 2, 2, f);
        this.neck = new ModelRenderer(this, 52, 0);
        this.neck.setRotationPoint(0.0f, 3.0f, -12.0f);
        this.neck.addBox(-7.0f, -4.0f, -7.0f, 14, 13, 8, f);
        this.horn1 = new ModelRenderer(this, 36, 0);
        this.horn1.addBox(-1.0f, -14.0f, -20.0f, 2, 8, 2, f);
        this.horn1.rotateAngleX = (float) Math.toRadians(15.0);
        this.head.addChild(this.horn1);
        this.horn2 = new ModelRenderer(this, 44, 0);
        this.horn2.addBox(-1.0f, -3.0f, -17.0f, 2, 4, 2, f);
        this.horn2.rotateAngleX = (float) Math.toRadians(-10.0);
        this.head.addChild(this.horn2);
        this.body = new ModelRenderer(this, 0, 26);
        this.body.setRotationPoint(0.0f, 5.0f, 0.0f);
        this.body.addBox(-8.0f, -7.0f, -13.0f, 16, 16, 34, f);
        this.tail = new ModelRenderer(this, 100, 63);
        this.tail.setRotationPoint(0.0f, 7.0f, 21.0f);
        this.tail.addBox(-1.5f, -1.0f, -1.0f, 3, 8, 2, f);
        this.leg1 = new ModelRenderer(this, 30, 76);
        this.leg1.setRotationPoint(-8.0f, 3.0f, 14.0f);
        this.leg1.addBox(-8.0f, -3.0f, -5.0f, 8, 12, 10, f);
        this.leg1.setTextureOffset(0, 95).addBox(-7.0f, 9.0f, -3.0f, 6, 12, 6, f);
        this.leg2 = new ModelRenderer(this, 30, 76);
        this.leg2.setRotationPoint(8.0f, 3.0f, 14.0f);
        this.leg2.mirror = true;
        this.leg2.addBox(0.0f, -3.0f, -5.0f, 8, 12, 10, f);
        this.leg2.setTextureOffset(0, 95).addBox(1.0f, 9.0f, -3.0f, 6, 12, 6, f);
        this.leg3 = new ModelRenderer(this, 0, 76);
        this.leg3.setRotationPoint(-8.0f, 4.0f, -6.0f);
        this.leg3.addBox(-7.0f, -3.0f, -4.0f, 7, 11, 8, f);
        this.leg3.setTextureOffset(0, 95).addBox(-6.5f, 8.0f, -3.0f, 6, 12, 6, f);
        this.leg4 = new ModelRenderer(this, 0, 76);
        this.leg4.setRotationPoint(8.0f, 4.0f, -6.0f);
        this.leg4.mirror = true;
        this.leg4.addBox(0.0f, -3.0f, -4.0f, 7, 11, 8, f);
        this.leg4.setTextureOffset(0, 95).addBox(0.5f, 8.0f, -3.0f, 6, 12, 6, f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.horn2.showModel = !this.isChild;
        this.horn1.showModel = this.horn2.showModel;
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 8.0f * f5, 12.0f * f5);
            this.head.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f * f5);
            this.body.render(f5);
            this.tail.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.neck.render(f5);
            this.body.render(f5);
            this.tail.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = (float) Math.toRadians(12.0);
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleX += MathHelper.cos(f * 0.2f) * 0.3f * f1;
        this.head.rotateAngleX += (float) Math.toRadians(f4);
        this.head.rotateAngleY += (float) Math.toRadians(f3);
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleZ = this.head.rotateAngleZ;
        this.tail.rotateAngleX = (float) Math.toRadians(40.0);
        this.tail.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.5f * f1;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.4f) * 1.0f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * 1.0f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * 1.0f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.4f) * 1.0f * f1;
    }
}
