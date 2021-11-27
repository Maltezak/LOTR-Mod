package lotr.client.model;

import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBird extends ModelBase {
    public ModelRenderer body = new ModelRenderer(this, 0, 7);
    public ModelRenderer head;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;

    public LOTRModelBird() {
        this.body.addBox(-1.5f, -2.0f, -2.0f, 3, 3, 5);
        this.body.setTextureOffset(8, 0).addBox(-1.0f, -1.5f, 3.0f, 2, 1, 3);
        this.body.setTextureOffset(8, 4).addBox(-1.0f, -0.5f, 3.0f, 2, 1, 2);
        this.body.setRotationPoint(0.0f, 21.0f, 0.0f);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.0f, -1.5f, -1.5f, 2, 2, 2);
        this.head.setTextureOffset(0, 4).addBox(-0.5f, -0.5f, -2.5f, 1, 1, 1);
        this.head.setTextureOffset(15, 0).addBox(-0.5f, -0.5f, -3.5f, 1, 1, 2);
        this.head.setRotationPoint(0.0f, -2.0f, -2.0f);
        this.body.addChild(this.head);
        this.wingRight = new ModelRenderer(this, 16, 7);
        this.wingRight.addBox(0.0f, 0.0f, -2.0f, 0, 5, 4);
        this.wingRight.setRotationPoint(-1.5f, -1.5f, 0.5f);
        this.body.addChild(this.wingRight);
        this.wingLeft = new ModelRenderer(this, 16, 7);
        this.wingLeft.mirror = true;
        this.wingLeft.addBox(0.0f, 0.0f, -2.0f, 0, 5, 4);
        this.wingLeft.setRotationPoint(1.5f, -1.5f, 0.5f);
        this.body.addChild(this.wingLeft);
        this.legRight = new ModelRenderer(this, 0, 16);
        this.legRight.addBox(-1.0f, 0.0f, -1.5f, 1, 2, 2);
        this.legRight.setRotationPoint(-0.3f, 1.0f, 0.5f);
        this.body.addChild(this.legRight);
        this.legLeft = new ModelRenderer(this, 0, 16);
        this.legLeft.mirror = true;
        this.legLeft.addBox(0.0f, 0.0f, -1.5f, 1, 2, 2);
        this.legLeft.setRotationPoint(0.3f, 1.0f, 0.5f);
        this.body.addChild(this.legLeft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        LOTREntityBird bird = (LOTREntityBird) entity;
        if(bird.isBirdStill()) {
            this.body.rotateAngleX = (float) Math.toRadians(-10.0);
            this.head.rotateAngleX = (float) Math.toRadians(20.0);
            this.wingRight.rotateAngleZ = bird.flapTime > 0 ? (float) Math.toRadians(90.0) + MathHelper.cos(f2 * 1.5f) * (float) Math.toRadians(30.0) : (float) Math.toRadians(30.0);
            this.wingLeft.rotateAngleZ = -this.wingRight.rotateAngleZ;
            this.legRight.rotateAngleX = this.legLeft.rotateAngleX = -this.body.rotateAngleX;
            this.legRight.rotateAngleX += MathHelper.cos(f * 0.6662f) * f1;
            this.legLeft.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.141593f) * f1;
            this.legRight.rotationPointY = 1.0f;
            this.legLeft.rotationPointY = 1.0f;
        }
        else {
            this.body.rotateAngleX = 0.0f;
            this.head.rotateAngleX = 0.0f;
            this.wingRight.rotateAngleZ = (float) Math.toRadians(90.0) + MathHelper.cos(f2 * 1.5f) * (float) Math.toRadians(30.0);
            this.wingLeft.rotateAngleZ = -this.wingRight.rotateAngleZ;
            this.legLeft.rotateAngleX = 0.0f;
            this.legRight.rotateAngleX = 0.0f;
            this.legRight.rotationPointY = 0.0f;
            this.legLeft.rotationPointY = 0.0f;
        }
    }
}
