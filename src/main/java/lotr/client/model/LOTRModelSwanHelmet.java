package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSwanHelmet extends LOTRModelBiped {
    private ModelRenderer wingRight;
    private ModelRenderer wingLeft;

    public LOTRModelSwanHelmet() {
        this(0.0f);
    }

    public LOTRModelSwanHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -9.0f, -3.5f, 1, 1, 7, f);
        this.wingRight = new ModelRenderer(this, 0, 16);
        this.wingRight.addBox(-4.0f - f, -6.0f, 1.0f + f, 1, 1, 9, 0.0f);
        this.wingRight.setTextureOffset(20, 16).addBox(-3.5f - f, -5.0f, 1.9f + f, 0, 6, 8, 0.0f);
        this.wingLeft = new ModelRenderer(this, 0, 16);
        this.wingLeft.mirror = true;
        this.wingLeft.addBox(3.0f + f, -6.0f, 1.0f + f, 1, 1, 9, 0.0f);
        this.wingLeft.setTextureOffset(20, 16).addBox(3.5f + f, -5.0f, 1.9f + f, 0, 6, 8, 0.0f);
        this.bipedHead.addChild(this.wingRight);
        this.bipedHead.addChild(this.wingLeft);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float wingYaw = (float) Math.toRadians(-25.0);
        float wingPitch = (float) Math.toRadians(20.0);
        this.wingRight.rotateAngleY = wingYaw;
        this.wingLeft.rotateAngleY = -wingYaw;
        this.wingRight.rotateAngleX = wingPitch;
        this.wingLeft.rotateAngleX = wingPitch;
    }
}
