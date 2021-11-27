package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelOrc extends LOTRModelBiped implements LOTRGlowingEyes.Model {
    private ModelRenderer nose = new ModelRenderer(this, 14, 17);
    private ModelRenderer earRight;
    private ModelRenderer earLeft;

    public LOTRModelOrc() {
        this(0.0f);
    }

    public LOTRModelOrc(float f) {
        super(f);
        this.nose.addBox(-0.5f, -4.0f, -4.8f, 1, 2, 1, f);
        this.nose.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earRight = new ModelRenderer(this, 0, 0);
        this.earRight.addBox(-3.5f, -5.5f, 2.0f, 1, 2, 3, f);
        this.earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earRight.rotateAngleX = 0.2617994f;
        this.earRight.rotateAngleY = -0.5235988f;
        this.earRight.rotateAngleZ = -0.22689281f;
        this.earLeft = new ModelRenderer(this, 24, 0);
        this.earLeft.addBox(2.5f, -5.5f, 2.0f, 1, 2, 3, f);
        this.earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.earLeft.rotateAngleX = 0.2617994f;
        this.earLeft.rotateAngleY = 0.5235988f;
        this.earLeft.rotateAngleZ = 0.22689281f;
        this.bipedHead.addChild(this.nose);
        this.bipedHead.addChild(this.earRight);
        this.bipedHead.addChild(this.earLeft);
    }

    @Override
    public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedHead.render(f5);
    }
}
