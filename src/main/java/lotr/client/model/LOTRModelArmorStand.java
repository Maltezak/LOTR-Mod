package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelArmorStand extends ModelBase {
    private ModelRenderer base = new ModelRenderer(this, 0, 0);
    private ModelRenderer head;
    private ModelRenderer spine;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightHip;
    private ModelRenderer leftHip;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightFoot;
    private ModelRenderer leftFoot;

    public LOTRModelArmorStand() {
        this.base.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 2);
        this.base.setRotationPoint(0.0f, 30.0f, 0.0f);
        this.base.rotateAngleX = -1.5707964f;
        this.head = new ModelRenderer(this, 40, 0);
        this.head.addBox(-3.0f, 0.0f, -2.0f, 6, 8, 4);
        this.head.setRotationPoint(0.0f, -11.0f, 0.0f);
        this.spine = new ModelRenderer(this, 60, 0);
        this.spine.addBox(-0.5f, 0.0f, -0.5f, 1, 25, 1);
        this.spine.setRotationPoint(0.0f, -3.0f, 0.0f);
        this.rightArm = new ModelRenderer(this, 44, 12);
        this.rightArm.addBox(-7.5f, 0.0f, -0.5f, 7, 1, 1);
        this.rightArm.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.leftArm = new ModelRenderer(this, 44, 12);
        this.leftArm.mirror = true;
        this.leftArm.addBox(0.5f, 0.0f, -0.5f, 7, 1, 1);
        this.leftArm.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.rightHip = new ModelRenderer(this, 42, 30);
        this.rightHip.addBox(-3.5f, 0.0f, -0.5f, 3, 1, 1);
        this.rightHip.setRotationPoint(0.0f, 9.0f, 0.0f);
        this.leftHip = new ModelRenderer(this, 42, 30);
        this.leftHip.mirror = true;
        this.leftHip.addBox(0.5f, 0.0f, -0.5f, 3, 1, 1);
        this.leftHip.setRotationPoint(0.0f, 9.0f, 0.0f);
        this.rightLeg = new ModelRenderer(this, 50, 23);
        this.rightLeg.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
        this.rightLeg.setRotationPoint(-2.0f, 10.0f, 0.0f);
        this.leftLeg = new ModelRenderer(this, 50, 23);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
        this.leftLeg.setRotationPoint(2.0f, 10.0f, 0.0f);
        this.rightFoot = new ModelRenderer(this, 54, 27);
        this.rightFoot.addBox(-2.0f, 0.0f, -1.0f, 3, 3, 2);
        this.rightFoot.setRotationPoint(-2.0f, 18.0f, 0.0f);
        this.leftFoot = new ModelRenderer(this, 54, 27);
        this.leftFoot.mirror = true;
        this.leftFoot.addBox(-1.0f, 0.0f, -1.0f, 3, 3, 2);
        this.leftFoot.setRotationPoint(2.0f, 18.0f, 0.0f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.base.render(f5);
        this.head.render(f5);
        this.spine.render(f5);
        this.rightArm.render(f5);
        this.leftArm.render(f5);
        this.rightHip.render(f5);
        this.leftHip.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        this.rightFoot.render(f5);
        this.leftFoot.render(f5);
    }
}
