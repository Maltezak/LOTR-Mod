package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorHelmet extends LOTRModelBiped {
    public LOTRModelHarnedorHelmet() {
        this(0.0f);
    }

    public LOTRModelHarnedorHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(0, 5).addBox(0.0f, -11.0f, -7.0f, 0, 10, 14, 0.0f);
        this.bipedHead.setTextureOffset(16, 19).addBox(-6.0f, -2.0f, -6.0f, 12, 0, 12, 0.0f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
