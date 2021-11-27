package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUmbarHelmet extends LOTRModelBiped {
    public LOTRModelUmbarHelmet() {
        this(0.0f);
    }

    public LOTRModelUmbarHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
        this.bipedHead.setTextureOffset(0, 0);
        this.bipedHead.addBox(-0.5f, -11.0f - f, -3.0f, 1, 3, 1, 0.0f);
        this.bipedHead.addBox(-0.5f, -10.0f - f, 2.0f, 1, 2, 1, 0.0f);
        this.bipedHead.setTextureOffset(0, 16).addBox(0.0f, -13.0f - f, -6.0f, 0, 4, 12, 0.0f);
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
