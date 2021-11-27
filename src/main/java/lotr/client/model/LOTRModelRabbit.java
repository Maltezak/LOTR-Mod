package lotr.client.model;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRabbit extends ModelBase {
    private ModelRenderer head = new ModelRenderer(this, 0, 0);
    private ModelRenderer body;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    public LOTRModelRabbit() {
        this.head.addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4);
        this.head.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.head.setTextureOffset(0, 8).addBox(-1.5f, 0.0f, -3.0f, 3, 2, 2);
        ModelRenderer rightEar = new ModelRenderer(this, 16, 0);
        rightEar.addBox(-1.2f, -4.5f, -0.5f, 2, 5, 1);
        rightEar.setRotationPoint(-1.0f, -1.5f, 0.0f);
        rightEar.rotateAngleX = (float) Math.toRadians(-20.0);
        ModelRenderer leftEar = new ModelRenderer(this, 16, 0);
        leftEar.mirror = true;
        leftEar.addBox(-0.8f, -4.5f, -0.5f, 2, 5, 1);
        leftEar.setRotationPoint(1.0f, -1.5f, 0.0f);
        leftEar.rotateAngleX = (float) Math.toRadians(-20.0);
        this.head.addChild(rightEar);
        this.head.addChild(leftEar);
        this.body = new ModelRenderer(this, 0, 19);
        this.body.addBox(-2.5f, -4.0f, -2.5f, 5, 8, 5);
        this.body.setRotationPoint(0.0f, 18.5f, 0.0f);
        this.body.setTextureOffset(0, 14).addBox(-1.5f, -6.0f, -1.5f, 3, 2, 3);
        ModelRenderer tail = new ModelRenderer(this, 32, 30);
        tail.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1);
        tail.setRotationPoint(0.0f, 4.5f, 2.5f);
        tail.rotateAngleX = (float) Math.toRadians(-45.0);
        this.body.addChild(this.head);
        this.body.addChild(tail);
        this.rightArm = new ModelRenderer(this, 32, 0);
        this.rightArm.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.rightArm.setRotationPoint(-1.5f, -2.0f, -2.5f);
        this.leftArm = new ModelRenderer(this, 32, 0);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
        this.leftArm.setRotationPoint(1.5f, -2.0f, -2.5f);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.leftArm);
        this.rightLeg = new ModelRenderer(this, 32, 8);
        this.rightLeg.addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
        this.rightLeg.setRotationPoint(-3.0f, 21.5f, 1.0f);
        ModelRenderer rightFoot = new ModelRenderer(this, 32, 16);
        rightFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
        rightFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
        rightFoot.rotateAngleX = (float) Math.toRadians(-15.0);
        this.rightLeg.addChild(rightFoot);
        this.leftLeg = new ModelRenderer(this, 32, 8);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
        this.leftLeg.setRotationPoint(3.0f, 21.5f, 1.0f);
        ModelRenderer leftFoot = new ModelRenderer(this, 32, 16);
        leftFoot.mirror = true;
        leftFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
        leftFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
        leftFoot.rotateAngleX = (float) Math.toRadians(-15.0);
        this.leftLeg.addChild(leftFoot);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.body.rotateAngleX = (float) Math.toRadians(45.0);
        this.head.rotateAngleX = (float) Math.toRadians(-45.0);
        this.rightArm.rotateAngleX = (float) Math.toRadians(-55.0);
        this.leftArm.rotateAngleX = (float) Math.toRadians(-55.0);
        float f6 = (float) Math.toRadians(45.0);
        f6 = LOTRMod.isAprilFools() ? (f6 *= f2) : (f6 *= f1);
        this.body.rotateAngleX += f6;
        this.head.rotateAngleX -= f6;
        this.rightArm.rotateAngleX -= f6;
        this.leftArm.rotateAngleX -= f6;
        if(((LOTREntityRabbit) entity).isRabbitEating()) {
            float f7 = (float) Math.toRadians(30.0);
            this.body.rotateAngleX += f7;
            this.rightArm.rotateAngleX += f7;
            this.leftArm.rotateAngleX += f7;
            this.head.rotateAngleX += f7 * 2.0f;
        }
        else {
            this.head.rotateAngleX += f4 / 57.295776f;
            this.head.rotateAngleY = MathHelper.cos(this.head.rotateAngleX) * f3 / 57.295776f;
            this.head.rotateAngleZ = MathHelper.sin(this.head.rotateAngleX) * f3 / 57.295776f;
        }
        this.rightArm.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * f1;
        this.leftArm.rotateAngleX += MathHelper.cos(f * 0.6662f) * f1;
        this.body.rotateAngleZ = MathHelper.cos(f * 0.6662f) * f1 * 0.3f;
        this.rightLeg.rotateAngleX = (float) Math.toRadians(15.0);
        this.leftLeg.rotateAngleX = (float) Math.toRadians(15.0);
        this.rightLeg.rotateAngleX += MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leftLeg.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
    }
}
