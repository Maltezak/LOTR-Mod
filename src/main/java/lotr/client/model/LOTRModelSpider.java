package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelSpider extends ModelBase implements LOTRGlowingEyes.Model {
    protected ModelRenderer head = new ModelRenderer(this, 32, 0);
    protected ModelRenderer thorax;
    protected ModelRenderer abdomen;
    protected ModelRenderer leg1;
    protected ModelRenderer leg2;
    protected ModelRenderer leg3;
    protected ModelRenderer leg4;
    protected ModelRenderer leg5;
    protected ModelRenderer leg6;
    protected ModelRenderer leg7;
    protected ModelRenderer leg8;

    public LOTRModelSpider() {
        this(0.0f);
    }

    public LOTRModelSpider(float f) {
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f);
        this.head.setRotationPoint(0.0f, 17.0f, -3.0f);
        this.thorax = new ModelRenderer(this, 0, 0);
        this.thorax.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, f);
        this.thorax.setRotationPoint(0.0f, 17.0f, 0.0f);
        this.abdomen = new ModelRenderer(this, 0, 12);
        this.abdomen.addBox(-5.0f, -4.0f, -0.5f, 10, 8, 12, f);
        this.abdomen.setRotationPoint(0.0f, 17.0f, 3.0f);
        this.leg1 = new ModelRenderer(this, 36, 16);
        this.leg1.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg1.setRotationPoint(-4.0f, 17.0f, 2.0f);
        this.leg1.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg2 = new ModelRenderer(this, 36, 16);
        this.leg2.mirror = true;
        this.leg2.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg2.setRotationPoint(4.0f, 17.0f, 2.0f);
        this.leg2.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg3 = new ModelRenderer(this, 36, 16);
        this.leg3.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg3.setRotationPoint(-4.0f, 17.0f, 1.0f);
        this.leg3.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg4 = new ModelRenderer(this, 36, 16);
        this.leg4.mirror = true;
        this.leg4.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg4.setRotationPoint(4.0f, 17.0f, 1.0f);
        this.leg4.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg5 = new ModelRenderer(this, 36, 16);
        this.leg5.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg5.setRotationPoint(-4.0f, 17.0f, 0.0f);
        this.leg5.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg6 = new ModelRenderer(this, 36, 16);
        this.leg6.mirror = true;
        this.leg6.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg6.setRotationPoint(4.0f, 17.0f, 0.0f);
        this.leg6.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg7 = new ModelRenderer(this, 36, 16);
        this.leg7.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg7.setRotationPoint(-4.0f, 17.0f, -1.0f);
        this.leg7.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
        this.leg8 = new ModelRenderer(this, 36, 16);
        this.leg8.mirror = true;
        this.leg8.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
        this.leg8.setRotationPoint(4.0f, 17.0f, -1.0f);
        this.leg8.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
        this.thorax.render(f5);
        this.abdomen.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.leg5.render(f5);
        this.leg6.render(f5);
        this.leg7.render(f5);
        this.leg8.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleY = f3 / 57.295776f;
        this.head.rotateAngleX = f4 / 57.295776f;
        this.abdomen.rotateAngleY = MathHelper.cos(f * 0.6662f) * 0.5f * f1;
        float f6 = -0.51460177f;
        this.leg1.rotateAngleZ = -f6;
        this.leg2.rotateAngleZ = f6;
        this.leg3.rotateAngleZ = -f6 * 0.74f;
        this.leg4.rotateAngleZ = f6 * 0.74f;
        this.leg5.rotateAngleZ = -f6 * 0.74f;
        this.leg6.rotateAngleZ = f6 * 0.74f;
        this.leg7.rotateAngleZ = -f6;
        this.leg8.rotateAngleZ = f6;
        float f7 = -0.0f;
        float f8 = 0.3926991f;
        this.leg1.rotateAngleY = f8 * 2.0f + f7;
        this.leg2.rotateAngleY = -f8 * 2.0f - f7;
        this.leg3.rotateAngleY = f8 * 1.0f + f7;
        this.leg4.rotateAngleY = -f8 * 1.0f - f7;
        this.leg5.rotateAngleY = -f8 * 1.0f + f7;
        this.leg6.rotateAngleY = f8 * 1.0f - f7;
        this.leg7.rotateAngleY = -f8 * 2.0f + f7;
        this.leg8.rotateAngleY = f8 * 2.0f - f7;
        float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f1;
        float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
        float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
        float f12 = -(MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f1;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662f + 4.712389f) * 0.4f) * f1;
        this.leg1.rotateAngleY += f9;
        this.leg2.rotateAngleY += -f9;
        this.leg3.rotateAngleY += f10;
        this.leg4.rotateAngleY += -f10;
        this.leg5.rotateAngleY += f11;
        this.leg6.rotateAngleY += -f11;
        this.leg7.rotateAngleY += f12;
        this.leg8.rotateAngleY += -f12;
        this.leg1.rotateAngleZ += f13;
        this.leg2.rotateAngleZ += -f13;
        this.leg3.rotateAngleZ += f14;
        this.leg4.rotateAngleZ += -f14;
        this.leg5.rotateAngleZ += f15;
        this.leg6.rotateAngleZ += -f15;
        this.leg7.rotateAngleZ += f16;
        this.leg8.rotateAngleZ += -f16;
    }

    @Override
    public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
    }
}
