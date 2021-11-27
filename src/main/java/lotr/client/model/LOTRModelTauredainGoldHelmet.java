package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainGoldHelmet extends LOTRModelBiped {
    public LOTRModelTauredainGoldHelmet() {
        this(0.0f);
    }

    public LOTRModelTauredainGoldHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        ModelRenderer crest = new ModelRenderer(this, 32, 0);
        crest.setRotationPoint(0.0f, -f, 0.0f);
        crest.addBox(-7.0f, -20.0f, 0.0f, 14, 12, 0, 0.0f);
        crest.rotateAngleX = (float) Math.toRadians(-4.0);
        ModelRenderer tusks1 = new ModelRenderer(this, 0, 16);
        tusks1.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks1.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks1.rotateAngleX = (float) Math.toRadians(20.0);
        tusks1.rotateAngleY = (float) Math.toRadians(30.0);
        ModelRenderer tusks2 = new ModelRenderer(this, 0, 16);
        tusks2.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks2.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks2.rotateAngleX = (float) Math.toRadians(20.0);
        tusks2.rotateAngleY = (float) Math.toRadians(-20.0);
        ModelRenderer tusks3 = new ModelRenderer(this, 0, 16);
        tusks3.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
        tusks3.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks3.rotateAngleX = (float) Math.toRadians(20.0);
        tusks3.rotateAngleY = (float) Math.toRadians(20.0);
        ModelRenderer tusks4 = new ModelRenderer(this, 0, 16);
        tusks4.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
        tusks4.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks4.rotateAngleX = (float) Math.toRadians(20.0);
        tusks4.rotateAngleY = (float) Math.toRadians(-30.0);
        this.bipedHead.addChild(crest);
        this.bipedHead.addChild(tusks1);
        this.bipedHead.addChild(tusks2);
        this.bipedHead.addChild(tusks3);
        this.bipedHead.addChild(tusks4);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }
}
