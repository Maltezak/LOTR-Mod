package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainChieftainHelmet extends LOTRModelBiped {
    public LOTRModelTauredainChieftainHelmet() {
        this(0.0f);
    }

    public LOTRModelTauredainChieftainHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-5.0f, -9.0f, 0.0f, 10, 6, 3, f);
        ModelRenderer crest = new ModelRenderer(this, 0, 16);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-8.0f, -23.0f, 0.0f, 16, 14, 0, 0.0f);
        crest.rotateAngleX = (float) Math.toRadians(-10.0);
        this.bipedHead.addChild(crest);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
