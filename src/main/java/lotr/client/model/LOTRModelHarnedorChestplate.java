package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorChestplate extends LOTRModelBiped {
    public LOTRModelHarnedorChestplate() {
        this(0.0f);
    }

    public LOTRModelHarnedorChestplate(float f) {
        super(f);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedRightArm.setTextureOffset(46, 0);
        this.bipedRightArm.addBox(-4.0f - f, -3.0f - f, -2.0f, 5, 1, 4, 0.0f);
        ModelRenderer rightBarbs1 = new ModelRenderer(this, 29, 0);
        rightBarbs1.setRotationPoint(-1.5f, -2.5f - f, -2.0f);
        rightBarbs1.addBox(-2.5f, 0.0f, -2.0f, 5, 0, 2, 0.0f);
        rightBarbs1.rotateAngleX = (float) Math.toRadians(30.0);
        this.bipedRightArm.addChild(rightBarbs1);
        ModelRenderer rightBarbs2 = new ModelRenderer(this, 29, 3);
        rightBarbs2.setRotationPoint(-1.5f, -2.5f - f, 2.0f);
        rightBarbs2.addBox(-2.5f, 0.0f, 0.0f, 5, 0, 2, 0.0f);
        rightBarbs2.rotateAngleX = (float) Math.toRadians(-30.0);
        this.bipedRightArm.addChild(rightBarbs2);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm.setTextureOffset(46, 0);
        this.bipedLeftArm.addBox(-1.0f + f, -3.0f - f, -2.0f, 5, 1, 4, 0.0f);
        ModelRenderer leftBarbs1 = new ModelRenderer(this, 29, 0);
        leftBarbs1.setRotationPoint(1.5f, -2.5f - f, -2.0f);
        leftBarbs1.mirror = true;
        leftBarbs1.addBox(-2.5f, 0.0f, -2.0f, 5, 0, 2, 0.0f);
        leftBarbs1.rotateAngleX = (float) Math.toRadians(30.0);
        this.bipedLeftArm.addChild(leftBarbs1);
        ModelRenderer leftBarbs2 = new ModelRenderer(this, 29, 3);
        leftBarbs2.setRotationPoint(1.5f, -2.5f - f, 2.0f);
        leftBarbs2.mirror = true;
        leftBarbs2.addBox(-2.5f, 0.0f, 0.0f, 5, 0, 2, 0.0f);
        leftBarbs2.rotateAngleX = (float) Math.toRadians(-30.0);
        this.bipedLeftArm.addChild(leftBarbs2);
        this.bipedHead.cubeList.clear();
        this.bipedHeadwear.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
