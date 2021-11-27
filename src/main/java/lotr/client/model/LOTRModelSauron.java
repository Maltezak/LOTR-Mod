package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSauron extends LOTRModelBiped {
    private ModelRenderer bipedCape;

    public LOTRModelSauron() {
        this.bipedHead = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
        this.bipedHead.setRotationPoint(0.0f, -12.0f, 0.0f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -15.0f, -3.5f, 1, 7, 1);
        this.bipedHead.setTextureOffset(32, 0).addBox(-2.5f, -13.0f, -3.5f, 1, 5, 1);
        this.bipedHead.setTextureOffset(32, 0).addBox(1.5f, -13.0f, -3.5f, 1, 5, 1);
        this.bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -16.0f, 2.5f, 1, 8, 1);
        this.bipedHead.setTextureOffset(32, 0).addBox(-3.5f, -16.0f, -0.5f, 1, 8, 1);
        this.bipedHead.setTextureOffset(32, 0).addBox(2.5f, -16.0f, -0.5f, 1, 8, 1);
        this.bipedBody = new ModelRenderer(this, 40, 42).setTextureSize(64, 64);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 18, 4);
        this.bipedBody.setRotationPoint(0.0f, -12.0f, 0.0f);
        this.bipedRightArm = new ModelRenderer(this, 0, 43).setTextureSize(64, 64);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 17, 4);
        this.bipedRightArm.setRotationPoint(-5.0f, -8.0f, 0.0f);
        this.bipedRightArm.setTextureOffset(16, 52).addBox(-4.0f, -3.0f, -3.0f, 6, 6, 6);
        this.bipedLeftArm = new ModelRenderer(this, 0, 43).setTextureSize(64, 64);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 17, 4);
        this.bipedLeftArm.setRotationPoint(5.0f, -8.0f, 0.0f);
        this.bipedLeftArm.setTextureOffset(16, 52).addBox(-2.0f, -3.0f, -3.0f, 6, 6, 6);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 18, 4);
        this.bipedRightLeg.setRotationPoint(-2.0f, 6.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 18, 4);
        this.bipedLeftLeg.setRotationPoint(2.0f, 6.0f, 0.0f);
        this.bipedCape = new ModelRenderer(this, 38, 0).setTextureSize(64, 64);
        this.bipedCape.addBox(-6.0f, 1.0f, 1.0f, 12, 32, 1);
        this.bipedCape.setRotationPoint(0.0f, -12.0f, 0.0f);
        this.bipedCape.rotateAngleX = 0.15f;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
        this.bipedCape.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(this.isSneak) {
            this.bipedRightLeg.rotationPointY = 3.0f;
            this.bipedLeftLeg.rotationPointY = 3.0f;
            this.bipedHead.rotationPointY = -11.0f;
        }
        else {
            this.bipedRightLeg.rotationPointY = 6.0f;
            this.bipedLeftLeg.rotationPointY = 6.0f;
            this.bipedHead.rotationPointY = -12.0f;
        }
    }
}
