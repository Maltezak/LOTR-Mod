package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRohanMarshalHelmet extends LOTRModelBiped {
    private ModelRenderer[] manes;

    public LOTRModelRohanMarshalHelmet() {
        this(0.0f);
    }

    public LOTRModelRohanMarshalHelmet(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
        this.bipedHead.setTextureOffset(0, 16).addBox(-1.0f, -11.5f - f, -4.5f - f, 2, 7, 6, 0.0f);
        this.manes = new ModelRenderer[3];
        for(int i = 0; i < this.manes.length; ++i) {
            ModelRenderer mane;
            this.manes[i] = mane = new ModelRenderer(this, 32, 0);
            mane.setRotationPoint(0.0f, -f, f);
            mane.addBox(0.0f, -11.0f, -1.0f, 0, 14, 12, 0.0f);
            this.bipedHead.addChild(mane);
        }
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float mid = this.manes.length / 2.0f - 0.5f;
        for(int i = 0; i < this.manes.length; ++i) {
            ModelRenderer mane = this.manes[i];
            mane.rotateAngleX = (mid - Math.abs(i - mid)) / mid * 0.22f;
            mane.rotateAngleY = (i - mid) / mid * 0.17f;
            mane.rotateAngleX += MathHelper.sin(f * 0.4f) * f1 * 0.2f;
        }
    }
}
