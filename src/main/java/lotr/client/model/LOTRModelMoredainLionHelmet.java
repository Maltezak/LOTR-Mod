package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMoredainLionHelmet extends LOTRModelBiped {
    private ModelRenderer panelRight;
    private ModelRenderer panelLeft;
    private ModelRenderer panelBack;
    private ModelRenderer panelTop;

    public LOTRModelMoredainLionHelmet() {
        this(0.0f);
    }

    public LOTRModelMoredainLionHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.setTextureOffset(34, 16).addBox(-4.5f, -9.0f, -2.5f, 9, 2, 5, f);
        this.bipedHead.setTextureOffset(0, 17).addBox(-2.5f, -10.0f, -7.0f, 5, 3, 12, f);
        this.bipedHead.setTextureOffset(34, 23).addBox(-1.0f, -10.4f, -7.2f, 2, 2, 7, f);
        this.bipedHead.setTextureOffset(0, 0).addBox(-2.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
        this.bipedHead.mirror = true;
        this.bipedHead.addBox(1.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
        this.panelRight = new ModelRenderer(this, 32, 0);
        this.panelRight.addBox(-5.0f - f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
        this.panelRight.rotateAngleZ = (float) Math.toRadians(4.0);
        this.panelLeft = new ModelRenderer(this, 32, 0);
        this.panelLeft.mirror = true;
        this.panelLeft.addBox(5.0f + f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
        this.panelLeft.rotateAngleZ = (float) Math.toRadians(-4.0);
        this.panelBack = new ModelRenderer(this, 44, 0);
        this.panelBack.addBox(-4.0f, -8.0f, 4.8f + f, 8, 10, 0, 0.0f);
        this.panelBack.rotateAngleX = (float) Math.toRadians(4.0);
        this.panelTop = new ModelRenderer(this, 52, 25);
        this.panelTop.addBox(-2.5f, -16.0f - f, -2.0f, 5, 7, 0, 0.0f);
        this.panelTop.rotateAngleX = (float) Math.toRadians(-10.0);
        this.bipedHead.addChild(this.panelRight);
        this.bipedHead.addChild(this.panelLeft);
        this.bipedHead.addChild(this.panelBack);
        this.bipedHead.addChild(this.panelTop);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
