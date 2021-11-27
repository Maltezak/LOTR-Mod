package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGulfChestplate extends LOTRModelBiped {
    public LOTRModelGulfChestplate() {
        this(0.0f);
    }

    public LOTRModelGulfChestplate(float f) {
        super(f);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        this.bipedBody.setTextureOffset(16, 0);
        this.bipedBody.addBox(-4.0f, 0.0f, -3.0f - f, 8, 3, 1, 0.0f);
        ModelRenderer chestHorn1 = new ModelRenderer(this, 0, 0);
        chestHorn1.setRotationPoint(-2.5f - f, 2.5f, -3.0f - f);
        chestHorn1.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn1.rotateAngleX = (float) Math.toRadians(-25.0);
        chestHorn1.rotateAngleZ = (float) Math.toRadians(25.0);
        this.bipedBody.addChild(chestHorn1);
        ModelRenderer chestHorn2 = new ModelRenderer(this, 0, 0);
        chestHorn2.setRotationPoint(0.0f, 3.0f, -3.0f - f);
        chestHorn2.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn2.rotateAngleX = (float) Math.toRadians(-25.0);
        chestHorn2.rotateAngleZ = (float) Math.toRadians(0.0);
        this.bipedBody.addChild(chestHorn2);
        ModelRenderer chestHorn3 = new ModelRenderer(this, 0, 0);
        chestHorn3.setRotationPoint(2.5f + f, 2.5f, -3.0f - f);
        chestHorn3.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
        chestHorn3.rotateAngleX = (float) Math.toRadians(-25.0);
        chestHorn3.rotateAngleZ = (float) Math.toRadians(-25.0);
        this.bipedBody.addChild(chestHorn3);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.setTextureOffset(40, 0);
        this.bipedRightArm.addBox(-4.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
        ModelRenderer rightHorn1 = new ModelRenderer(this, 4, 0);
        rightHorn1.setRotationPoint(-2.5f, -2.0f - f, 0.0f);
        rightHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        rightHorn1.rotateAngleZ = (float) Math.toRadians(-10.0);
        this.bipedRightArm.addChild(rightHorn1);
        ModelRenderer rightHorn2 = new ModelRenderer(this, 8, 0);
        rightHorn2.setRotationPoint(-0.5f, -2.0f - f, 0.0f);
        rightHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        rightHorn2.rotateAngleZ = (float) Math.toRadians(-10.0);
        this.bipedRightArm.addChild(rightHorn2);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.setTextureOffset(40, 0);
        this.bipedLeftArm.addBox(-1.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
        ModelRenderer leftHorn1 = new ModelRenderer(this, 4, 0);
        leftHorn1.setRotationPoint(2.5f, -2.0f - f, 0.0f);
        leftHorn1.mirror = true;
        leftHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
        leftHorn1.rotateAngleZ = (float) Math.toRadians(10.0);
        this.bipedLeftArm.addChild(leftHorn1);
        ModelRenderer leftHorn2 = new ModelRenderer(this, 8, 0);
        leftHorn2.setRotationPoint(0.5f, -2.0f - f, 0.0f);
        leftHorn2.mirror = true;
        leftHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
        leftHorn2.rotateAngleZ = (float) Math.toRadians(10.0);
        this.bipedLeftArm.addChild(leftHorn2);
        this.bipedHead.cubeList.clear();
        this.bipedHeadwear.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
