package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelNearHaradWarlordHelmet extends LOTRModelBiped {
    private ModelRenderer stickRight;
    private ModelRenderer stickCentre;
    private ModelRenderer stickLeft;

    public LOTRModelNearHaradWarlordHelmet() {
        this(0.0f);
    }

    public LOTRModelNearHaradWarlordHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.setTextureOffset(6, 24).addBox(-2.5f, -3.0f, 4.1f, 5, 3, 2, 0.0f);
        this.bipedHead.setTextureOffset(0, 16).addBox(-9.0f, -16.0f, 5.5f, 18, 8, 0, 0.0f);
        this.stickRight = new ModelRenderer(this, 36, 0);
        this.stickRight.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickRight.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickRight.rotateAngleZ = (float) Math.toRadians(-28.0);
        this.bipedHead.addChild(this.stickRight);
        this.stickCentre = new ModelRenderer(this, 36, 0);
        this.stickCentre.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickCentre.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickCentre.rotateAngleZ = (float) Math.toRadians(0.0);
        this.bipedHead.addChild(this.stickCentre);
        this.stickLeft = new ModelRenderer(this, 36, 0);
        this.stickLeft.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
        this.stickLeft.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
        this.stickLeft.rotateAngleZ = (float) Math.toRadians(28.0);
        this.bipedHead.addChild(this.stickLeft);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
