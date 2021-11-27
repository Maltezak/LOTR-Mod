package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelArnorHelmet extends LOTRModelBiped {
    public LOTRModelArnorHelmet() {
        this(0.0f);
    }

    public LOTRModelArnorHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-4.5f - f, -13.0f - f, -1.0f, 1, 8, 1, 0.0f);
        this.bipedHead.setTextureOffset(36, 0).addBox(-4.5f - f, -12.0f - f, 0.0f, 1, 7, 1, 0.0f);
        this.bipedHead.setTextureOffset(40, 0).addBox(-4.5f - f, -11.0f - f, 1.0f, 1, 5, 1, 0.0f);
        this.bipedHead.mirror = true;
        this.bipedHead.setTextureOffset(32, 0).addBox(3.5f + f, -13.0f - f, -1.0f, 1, 8, 1, 0.0f);
        this.bipedHead.setTextureOffset(36, 0).addBox(3.5f + f, -12.0f - f, 0.0f, 1, 7, 1, 0.0f);
        this.bipedHead.setTextureOffset(40, 0).addBox(3.5f + f, -11.0f - f, 1.0f, 1, 5, 1, 0.0f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
