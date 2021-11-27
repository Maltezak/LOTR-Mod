package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelTermite extends ModelBase {
    private ModelRenderer body = new ModelRenderer(this, 10, 5);
    private ModelRenderer head;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg5;
    private ModelRenderer leg4;
    private ModelRenderer leg6;
    private ModelRenderer rightfeeler;
    private ModelRenderer leftfeeler;

    public LOTRModelTermite() {
        this.body.addBox(0.0f, 0.0f, 0.0f, 6, 6, 21);
        this.body.setRotationPoint(-3.0f, 17.0f, -5.0f);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(0.0f, 0.0f, 0.0f, 8, 8, 7);
        this.head.setRotationPoint(-4.0f, 14.0f, -10.0f);
        this.leg1 = new ModelRenderer(this, 34, 0);
        this.leg1.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg1.setRotationPoint(-2.0f, 19.0f, 1.0f);
        this.leg2 = new ModelRenderer(this, 34, 0);
        this.leg2.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg2.setRotationPoint(2.0f, 19.0f, 1.0f);
        this.leg3 = new ModelRenderer(this, 34, 0);
        this.leg3.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg3.setRotationPoint(-2.0f, 19.0f, 0.0f);
        this.leg4 = new ModelRenderer(this, 34, 0);
        this.leg4.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg4.setRotationPoint(2.0f, 19.0f, 0.0f);
        this.leg5 = new ModelRenderer(this, 34, 0);
        this.leg5.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg5.setRotationPoint(-2.0f, 19.0f, -1.0f);
        this.leg6 = new ModelRenderer(this, 34, 0);
        this.leg6.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
        this.leg6.setRotationPoint(2.0f, 19.0f, -1.0f);
        this.rightfeeler = new ModelRenderer(this, 50, 18);
        this.rightfeeler.addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
        this.rightfeeler.setRotationPoint(-3.0f, 15.0f, -8.0f);
        this.rightfeeler.rotateAngleY = -0.1f;
        this.leftfeeler = new ModelRenderer(this, 50, 18);
        this.leftfeeler.addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
        this.leftfeeler.setRotationPoint(2.0f, 15.0f, -8.0f);
        this.leftfeeler.rotateAngleY = -0.1f;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.head.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.leg5.render(f5);
        this.leg6.render(f5);
        this.rightfeeler.render(f5);
        this.leftfeeler.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        float f6;
        this.leg1.rotateAngleZ = f6 = -0.51460177f;
        this.leg2.rotateAngleZ = -f6;
        this.leg3.rotateAngleZ = f6 * 0.74f;
        this.leg4.rotateAngleZ = -f6 * 0.74f;
        this.leg5.rotateAngleZ = f6 * 0.74f;
        this.leg6.rotateAngleZ = -f6 * 0.74f;
        float f7 = -0.0f;
        float f8 = 0.3926991f;
        this.leg1.rotateAngleY = f8 * 2.0f + f7;
        this.leg2.rotateAngleY = -f8 * 2.0f - f7;
        this.leg3.rotateAngleY = f8 * 1.0f + f7;
        this.leg4.rotateAngleY = -f8 * 1.0f - f7;
        this.leg5.rotateAngleY = -f8 * 1.0f + f7;
        this.leg6.rotateAngleY = f8 * 1.0f - f7;
        float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f1;
        float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
        float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
        MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f);
        float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
        this.leg1.rotateAngleY += f9;
        this.leg2.rotateAngleY += -f9;
        this.leg3.rotateAngleY += f10;
        this.leg4.rotateAngleY += -f10;
        this.leg5.rotateAngleY += f11;
        this.leg6.rotateAngleY += -f11;
        this.leg1.rotateAngleZ += f13;
        this.leg2.rotateAngleZ += -f13;
        this.leg3.rotateAngleZ += f14;
        this.leg4.rotateAngleZ += -f14;
        this.leg5.rotateAngleZ += f15;
        this.leg6.rotateAngleZ += -f15;
    }
}
