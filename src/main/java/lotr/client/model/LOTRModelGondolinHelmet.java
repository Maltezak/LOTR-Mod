package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondolinHelmet extends LOTRModelBiped {
    public LOTRModelGondolinHelmet() {
        this(0.0f);
    }

    public LOTRModelGondolinHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(46, 0).addBox(-0.5f, -14.0f - f, -4.5f, 1, 6, 1, 0.0f);
        this.bipedHead.setTextureOffset(50, 0).addBox(-0.5f, -12.0f - f, -0.5f, 1, 4, 1, 0.0f);
        this.bipedHead.setTextureOffset(54, 0).addBox(-0.5f, -10.0f - f, 3.5f, 1, 2, 1, 0.0f);
        this.bipedHead.setTextureOffset(32, -7).addBox(0.0f, -13.5f - f, -3.5f, 0, 6, 7, 0.0f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
