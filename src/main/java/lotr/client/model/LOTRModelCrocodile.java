package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelCrocodile extends ModelBase {
    private ModelRenderer body = new ModelRenderer(this, 18, 83).setTextureSize(128, 128);
    private ModelRenderer tail1;
    private ModelRenderer tail2;
    private ModelRenderer tail3;
    private ModelRenderer jaw;
    private ModelRenderer head;
    private ModelRenderer legFrontLeft;
    private ModelRenderer legBackLeft;
    private ModelRenderer legFrontRight;
    private ModelRenderer legBackRight;
    private ModelRenderer spines;

    public LOTRModelCrocodile() {
        this.body.addBox(-8.0f, -5.0f, 0.0f, 16, 9, 36);
        this.body.setRotationPoint(0.0f, 17.0f, -16.0f);
        this.tail1 = new ModelRenderer(this, 0, 28).setTextureSize(128, 128);
        this.tail1.addBox(-7.0f, 0.0f, 0.0f, 14, 7, 19);
        this.tail1.setRotationPoint(0.0f, 13.0f, 18.0f);
        this.tail2 = new ModelRenderer(this, 0, 55).setTextureSize(128, 128);
        this.tail2.addBox(-6.0f, 1.5f, 17.0f, 12, 5, 16);
        this.tail2.setRotationPoint(0.0f, 13.0f, 18.0f);
        this.tail3 = new ModelRenderer(this, 0, 77).setTextureSize(128, 128);
        this.tail3.addBox(-5.0f, 3.0f, 31.0f, 10, 3, 14);
        this.tail3.setRotationPoint(0.0f, 13.0f, 18.0f);
        this.jaw = new ModelRenderer(this, 58, 18).setTextureSize(128, 128);
        this.jaw.addBox(-6.5f, 0.3f, -19.0f, 13, 4, 19);
        this.jaw.setRotationPoint(0.0f, 17.0f, -16.0f);
        this.head = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
        this.head.addBox(-7.5f, -6.0f, -21.0f, 15, 6, 21);
        this.head.setRotationPoint(0.0f, 18.5f, -16.0f);
        this.legFrontLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
        this.legFrontLeft.addBox(0.0f, 0.0f, -3.0f, 16, 3, 6);
        this.legFrontLeft.setRotationPoint(6.0f, 15.0f, -11.0f);
        this.legBackLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
        this.legBackLeft.addBox(0.0f, 0.0f, -3.0f, 16, 3, 6);
        this.legBackLeft.setRotationPoint(6.0f, 15.0f, 15.0f);
        this.legFrontRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
        this.legFrontRight.mirror = true;
        this.legFrontRight.addBox(-16.0f, 0.0f, -3.0f, 16, 3, 6);
        this.legFrontRight.setRotationPoint(-6.0f, 15.0f, -11.0f);
        this.legBackRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
        this.legBackRight.mirror = true;
        this.legBackRight.addBox(-16.0f, 0.0f, -3.0f, 16, 3, 6);
        this.legBackRight.setRotationPoint(-6.0f, 15.0f, 15.0f);
        this.spines = new ModelRenderer(this, 46, 45).setTextureSize(128, 128);
        this.spines.addBox(-5.0f, 0.0f, 0.0f, 10, 4, 32);
        this.spines.setRotationPoint(0.0f, 9.5f, -14.0f);
        this.legBackLeft.rotateAngleZ = 0.43633232f;
        this.legBackRight.rotateAngleZ = -0.43633232f;
        this.legFrontLeft.rotateAngleZ = 0.43633232f;
        this.legFrontRight.rotateAngleZ = -0.43633232f;
        this.spines.rotateAngleX = -0.034906585f;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.tail1.render(f5);
        this.tail2.render(f5);
        this.tail3.render(f5);
        this.jaw.render(f5);
        this.head.render(f5);
        this.legFrontLeft.render(f5);
        this.legBackLeft.render(f5);
        this.legFrontRight.render(f5);
        this.legBackRight.render(f5);
        this.spines.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = f2 * 3.1415927f * -0.3f;
        this.legBackRight.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
        this.legBackLeft.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
        this.legFrontRight.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
        this.legFrontLeft.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
        this.tail1.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.5f;
        this.tail2.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.5625f;
        this.tail3.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.59375f;
    }
}
