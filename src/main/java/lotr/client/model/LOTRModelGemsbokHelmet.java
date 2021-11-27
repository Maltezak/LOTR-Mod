package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGemsbokHelmet extends LOTRModelBiped {
    private ModelRenderer hornRight;
    private ModelRenderer hornLeft;

    public LOTRModelGemsbokHelmet() {
        this(0.0f);
    }

    public LOTRModelGemsbokHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hornRight = new ModelRenderer(this, 32, 0);
        this.hornRight.addBox(-4.9f, -7.0f, 7.5f, 1, 1, 13);
        this.hornLeft = new ModelRenderer(this, 32, 0);
        this.hornLeft.mirror = true;
        this.hornLeft.addBox(3.9f, -7.0f, 7.5f, 1, 1, 13);
        this.hornRight.rotateAngleX = this.hornLeft.rotateAngleX = (float) Math.toRadians(20.0);
        this.bipedHead.addChild(this.hornRight);
        this.bipedHead.addChild(this.hornLeft);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
