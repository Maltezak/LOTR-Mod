package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelDikDik extends ModelBase {
    private ModelRenderer head = new ModelRenderer(this, 42, 23);
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;

    public LOTRModelDikDik() {
        this.head.addBox(-2.0f, -9.0f, -3.0f, 4, 4, 5);
        this.head.setRotationPoint(0.0f, 11.0f, -4.5f);
        this.head.setTextureOffset(18, 28).addBox(-1.0f, -7.3f, -5.0f, 2, 2, 2);
        this.head.setTextureOffset(0, 27).addBox(-2.8f, -11.0f, 0.5f, 1, 3, 2);
        this.head.setTextureOffset(8, 27).addBox(1.8f, -11.0f, 0.5f, 1, 3, 2);
        this.head.setTextureOffset(0, 21).addBox(-1.5f, -11.0f, 0.0f, 1, 2, 1);
        this.head.setTextureOffset(0, 21).addBox(0.5f, -11.0f, 0.0f, 1, 2, 1);
        this.head.setTextureOffset(28, 22).addBox(-1.5f, -8.0f, -2.0f, 3, 7, 3);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-3.0f, 0.0f, 0.0f, 6, 6, 14);
        this.body.setRotationPoint(0.0f, 9.0f, -7.0f);
        this.leg1 = new ModelRenderer(this, 56, 0);
        this.leg1.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.leg1.setRotationPoint(-1.7f, 14.0f, 5.0f);
        this.leg2 = new ModelRenderer(this, 56, 0);
        this.leg2.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.leg2.setRotationPoint(1.7f, 14.0f, 5.0f);
        this.leg3 = new ModelRenderer(this, 56, 0);
        this.leg3.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.leg3.setRotationPoint(-1.7f, 14.0f, -5.0f);
        this.leg4 = new ModelRenderer(this, 56, 0);
        this.leg4.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.leg4.setRotationPoint(1.7f, 14.0f, -5.0f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = f4 / 57.29578f;
        this.head.rotateAngleY = f3 / 57.29578f;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
    }
}
