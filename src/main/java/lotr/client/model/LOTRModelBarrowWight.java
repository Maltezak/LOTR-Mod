package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBarrowWight extends LOTRModelBiped {
    public LOTRModelBarrowWight() {
        this(0.0f);
    }

    public LOTRModelBarrowWight(float f) {
        super(f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 1.0f);
        this.bipedBody = new ModelRenderer(this, 0, 16);
        this.bipedBody.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.bipedBody.addBox(-4.0f, 0.0f, -3.0f, 8, 32, 6, f);
        this.bipedRightArm = new ModelRenderer(this, 28, 16);
        this.bipedRightArm.setRotationPoint(-6.0f, -5.0f, 0.0f);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.5f, 5, 9, 5, f);
        this.bipedRightArm.setTextureOffset(28, 30).addBox(-2.0f, 7.0f, -1.5f, 3, 10, 3, f);
        this.bipedLeftArm = new ModelRenderer(this, 28, 16);
        this.bipedLeftArm.setRotationPoint(6.0f, -5.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-2.0f, -2.0f, -2.5f, 5, 9, 5, f);
        this.bipedLeftArm.setTextureOffset(28, 30).addBox(-1.0f, 7.0f, -1.5f, 3, 10, 3, f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        f1 = 0.0f;
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedLeftArm.rotateAngleX = this.bipedRightArm.rotateAngleX;
        this.bipedLeftArm.rotateAngleY = -this.bipedRightArm.rotateAngleY;
        this.bipedLeftArm.rotateAngleZ = -this.bipedRightArm.rotateAngleZ;
    }
}
