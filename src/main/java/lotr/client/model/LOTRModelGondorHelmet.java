package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondorHelmet extends LOTRModelBiped {
    public LOTRModelGondorHelmet() {
        this(0.0f);
    }

    public LOTRModelGondorHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(0, 16).addBox(-1.5f, -9.0f, -3.5f, 3, 1, 7, f);
        this.bipedHead.setTextureOffset(20, 16).addBox(-0.5f, -10.0f, -3.5f, 1, 1, 7, f);
        this.bipedHead.setTextureOffset(24, 0).addBox(-1.5f, -10.5f - f, -4.5f - f, 3, 4, 1, 0.0f);
        this.bipedHead.setTextureOffset(24, 5).addBox(-0.5f, -11.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
        this.bipedHead.setTextureOffset(28, 5).addBox(-0.5f, -6.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-1.5f, -9.5f - f, 3.5f + f, 3, 3, 1, 0.0f);
        this.bipedHead.setTextureOffset(32, 4).addBox(-0.5f, -10.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
        this.bipedHead.setTextureOffset(36, 4).addBox(-0.5f, -6.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
