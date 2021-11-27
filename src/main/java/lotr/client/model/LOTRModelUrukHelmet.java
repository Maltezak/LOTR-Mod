package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUrukHelmet extends LOTRModelBiped {
    private ModelRenderer crest;
    private ModelRenderer jaw;

    public LOTRModelUrukHelmet() {
        this(0.0f);
    }

    public LOTRModelUrukHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.crest = new ModelRenderer(this, 0, 22);
        this.crest.addBox(-10.0f, -16.0f, -1.0f, 20, 10, 0, 0.0f);
        this.crest.rotateAngleX = (float) Math.toRadians(-10.0);
        this.bipedHead.addChild(this.crest);
        this.jaw = new ModelRenderer(this, 0, 16);
        this.jaw.addBox(-6.0f, 2.0f, -4.0f, 12, 6, 0, 0.0f);
        this.jaw.rotateAngleX = (float) Math.toRadians(-60.0);
        this.bipedHead.addChild(this.jaw);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
