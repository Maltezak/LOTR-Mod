package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelDorwinionElfHelmet extends LOTRModelBiped {
    public LOTRModelDorwinionElfHelmet() {
        this(0.0f);
    }

    public LOTRModelDorwinionElfHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(20, 16).addBox(0.0f, -10.0f, 4.0f, 0, 10, 4, 0.0f);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        ModelRenderer crest = new ModelRenderer(this, 0, 16);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-1.0f, -11.0f, -6.0f, 2, 5, 8, 0.0f);
        crest.rotateAngleX = (float) Math.toRadians(-15.0);
        this.bipedHead.addChild(crest);
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
