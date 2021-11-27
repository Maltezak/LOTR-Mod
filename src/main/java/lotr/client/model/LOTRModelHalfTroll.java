package lotr.client.model;

import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHalfTroll extends LOTRModelBiped {
    private ModelRenderer mohawk;
    private ModelRenderer hornRight1;
    private ModelRenderer hornRight2;
    private ModelRenderer hornLeft1;
    private ModelRenderer hornLeft2;

    public LOTRModelHalfTroll() {
        this(0.0f);
    }

    public LOTRModelHalfTroll(float f) {
        super(f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.bipedHead.addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10, f);
        this.bipedHead.setTextureOffset(40, 5).addBox(-4.0f, -3.0f, -7.0f, 8, 3, 2, f);
        ModelRenderer nose = new ModelRenderer(this, 30, 0);
        nose.addBox(-1.0f, -4.5f, -8.0f, 2, 3, 3, f);
        nose.rotateAngleX = (float) Math.toRadians(-20.0);
        this.bipedHead.addChild(nose);
        ModelRenderer teeth = new ModelRenderer(this, 60, 7);
        teeth.addBox(-3.5f, -7.5f, -5.0f, 1, 2, 1, f);
        teeth.mirror = true;
        teeth.addBox(2.5f, -7.5f, -5.0f, 1, 2, 1, f);
        teeth.rotateAngleX = (float) Math.toRadians(30.0);
        this.bipedHead.addChild(teeth);
        ModelRenderer earRight = new ModelRenderer(this, 0, 0);
        earRight.addBox(-5.0f, -6.0f, -2.0f, 1, 3, 3, f);
        earRight.rotateAngleY = (float) Math.toRadians(-35.0);
        this.bipedHead.addChild(earRight);
        ModelRenderer earLeft = new ModelRenderer(this, 0, 0);
        earLeft.mirror = true;
        earLeft.addBox(4.0f, -6.0f, -2.0f, 1, 3, 3, f);
        earLeft.rotateAngleY = (float) Math.toRadians(35.0);
        this.bipedHead.addChild(earLeft);
        this.mohawk = new ModelRenderer(this, 40, 10);
        this.mohawk.addBox(-1.0f, -12.5f, -1.5f, 2, 10, 8, f);
        this.bipedHead.addChild(this.mohawk);
        this.hornRight1 = new ModelRenderer(this, 40, 0);
        this.hornRight1.addBox(-10.0f, -8.0f, 1.0f, 3, 2, 2, f);
        this.hornRight1.rotateAngleZ = (float) Math.toRadians(20.0);
        this.bipedHead.addChild(this.hornRight1);
        this.hornRight2 = new ModelRenderer(this, 50, 2);
        this.hornRight2.addBox(-14.5f, -4.0f, 1.5f, 3, 1, 1, f);
        this.hornRight2.rotateAngleZ = (float) Math.toRadians(40.0);
        this.bipedHead.addChild(this.hornRight2);
        this.hornLeft1 = new ModelRenderer(this, 40, 0);
        this.hornLeft1.mirror = true;
        this.hornLeft1.addBox(7.0f, -8.0f, 1.0f, 3, 2, 2, f);
        this.hornLeft1.rotateAngleZ = (float) Math.toRadians(-20.0);
        this.bipedHead.addChild(this.hornLeft1);
        this.hornLeft2 = new ModelRenderer(this, 50, 2);
        this.hornLeft2.mirror = true;
        this.hornLeft2.addBox(11.5f, -4.0f, 1.5f, 3, 1, 1, f);
        this.hornLeft2.rotateAngleZ = (float) Math.toRadians(-40.0);
        this.bipedHead.addChild(this.hornLeft2);
        this.bipedBody = new ModelRenderer(this, 0, 20);
        this.bipedBody.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.bipedBody.addBox(-6.0f, 0.0f, -4.0f, 12, 16, 8, f);
        this.bipedRightArm = new ModelRenderer(this, 20, 50);
        this.bipedRightArm.setRotationPoint(-8.5f, -6.0f, 0.0f);
        this.bipedRightArm.addBox(-3.5f, -2.0f, -3.0f, 6, 8, 6, f);
        this.bipedRightArm.setTextureOffset(0, 49).addBox(-3.0f, 6.0f, -2.5f, 5, 10, 5, f);
        this.bipedLeftArm = new ModelRenderer(this, 20, 50);
        this.bipedLeftArm.setRotationPoint(8.5f, -6.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-2.5f, -2.0f, -3.0f, 6, 8, 6, f);
        this.bipedLeftArm.setTextureOffset(0, 49).addBox(-2.0f, 6.0f, -2.5f, 5, 10, 5, f);
        this.bipedRightLeg = new ModelRenderer(this, 40, 28);
        this.bipedRightLeg.setRotationPoint(-3.2f, 8.0f, 0.0f);
        this.bipedRightLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
        this.bipedLeftLeg = new ModelRenderer(this, 40, 28);
        this.bipedLeftLeg.setRotationPoint(3.2f, 8.0f, 0.0f);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
        this.bipedHeadwear.isHidden = true;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll) entity;
        this.mohawk.showModel = halfTroll.hasMohawk();
        this.hornRight1.showModel = this.hornLeft1.showModel = halfTroll.hasHorns();
        this.hornRight2.showModel = this.hornLeft2.showModel = halfTroll.hasFullHorns();
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
}
