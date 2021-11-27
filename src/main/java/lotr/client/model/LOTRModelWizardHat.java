package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelWizardHat extends LOTRModelBiped {
    private ModelRenderer hatBrim;
    private ModelRenderer hat1;
    private ModelRenderer hat2;
    private ModelRenderer hat3;

    public LOTRModelWizardHat() {
        this(0.0f);
    }

    public LOTRModelWizardHat(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hatBrim = new ModelRenderer(this, 0, 17);
        this.hatBrim.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.hatBrim.addBox(-7.0f, -8.0f - f, -7.0f, 14, 1, 14);
        this.hat1 = new ModelRenderer(this, 32, 3);
        this.hat1.setRotationPoint(0.0f, -8.0f - f, 0.0f);
        this.hat1.addBox(-4.0f, -5.0f, -4.0f, 8, 5, 8);
        this.hat2 = new ModelRenderer(this, 11, 7);
        this.hat2.setRotationPoint(0.0f, -4.0f, 0.0f);
        this.hat2.addBox(-2.5f, -4.0f, -2.5f, 5, 4, 5);
        this.hat3 = new ModelRenderer(this, 0, 22);
        this.hat3.setRotationPoint(0.0f, -3.5f, 0.0f);
        this.hat3.addBox(-1.5f, -3.0f, -1.0f, 3, 3, 3);
        this.bipedHead.addChild(this.hatBrim);
        this.hatBrim.addChild(this.hat1);
        this.hat1.addChild(this.hat2);
        this.hat2.addChild(this.hat3);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedHead.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.hat2.rotateAngleX = (float) Math.toRadians(-(10.0 + f1 * 10.0));
        this.hat3.rotateAngleX = (float) Math.toRadians(-(10.0 + f1 * 10.0));
    }
}
