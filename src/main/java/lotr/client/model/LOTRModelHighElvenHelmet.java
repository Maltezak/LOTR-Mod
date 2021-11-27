package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHighElvenHelmet extends LOTRModelBiped {
    private ModelRenderer crest;

    public LOTRModelHighElvenHelmet() {
        this(0.0f);
    }

    public LOTRModelHighElvenHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-0.5f, -11.0f, -2.0f, 1, 3, 1, 0.0f);
        this.bipedHead.setTextureOffset(0, 4).addBox(-0.5f, -10.0f, 2.0f, 1, 2, 1, 0.0f);
        this.crest = new ModelRenderer(this, 32, 0);
        this.crest.addBox(-1.0f, -11.0f, -8.0f, 2, 1, 11, 0.0f);
        this.crest.setTextureOffset(32, 12).addBox(-1.0f, -10.0f, -8.0f, 2, 1, 1, 0.0f);
        this.crest.rotateAngleX = (float) Math.toRadians(-16.0);
        this.bipedHead.addChild(this.crest);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
