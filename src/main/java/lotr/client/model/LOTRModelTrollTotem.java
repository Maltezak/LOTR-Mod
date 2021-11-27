package lotr.client.model;

import net.minecraft.client.model.*;

public class LOTRModelTrollTotem extends ModelBase {
    private ModelRenderer head;
    private ModelRenderer jaw;
    private ModelRenderer body;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer base;

    public LOTRModelTrollTotem() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-6.0f, -10.0f, -10.0f, 12, 10, 12);
        this.head.setRotationPoint(0.0f, 22.0f, 4.0f);
        this.head.addBox(-1.0f, -5.0f, -12.0f, 2, 3, 2);
        this.head.setTextureOffset(40, 0).addBox(-7.0f, -6.0f, -6.0f, 1, 4, 3);
        this.head.mirror = true;
        this.head.addBox(6.0f, -6.0f, -6.0f, 1, 4, 3);
        this.jaw = new ModelRenderer(this, 48, 0);
        this.jaw.addBox(-6.0f, -2.0f, -6.0f, 12, 2, 12);
        this.jaw.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.body = new ModelRenderer(this, 0, 24);
        this.body.addBox(-5.0f, 0.0f, -5.0f, 10, 16, 10);
        this.body.setRotationPoint(0.0f, 8.0f, 0.0f);
        this.rightArm = new ModelRenderer(this, 40, 24);
        this.rightArm.addBox(-3.0f, 0.0f, -3.0f, 3, 10, 6);
        this.rightArm.setRotationPoint(-5.0f, 9.0f, 0.0f);
        this.leftArm = new ModelRenderer(this, 40, 24);
        this.leftArm.mirror = true;
        this.leftArm.addBox(0.0f, 0.0f, -3.0f, 3, 10, 6);
        this.leftArm.setRotationPoint(5.0f, 9.0f, 0.0f);
        this.rightLeg = new ModelRenderer(this, 0, 50);
        this.rightLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 7, 6);
        this.rightLeg.setRotationPoint(-4.0f, 8.0f, 0.0f);
        this.rightLeg.setTextureOffset(24, 50).addBox(-2.5f, 7.0f, -2.5f, 5, 7, 5);
        this.leftLeg = new ModelRenderer(this, 0, 50);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 7, 6);
        this.leftLeg.setRotationPoint(4.0f, 8.0f, 0.0f);
        this.leftLeg.setTextureOffset(24, 50).addBox(-2.5f, 7.0f, -2.5f, 5, 7, 5);
        this.base = new ModelRenderer(this, 48, 46);
        this.base.addBox(-8.0f, 0.0f, -8.0f, 16, 2, 16);
        this.base.setRotationPoint(0.0f, 22.0f, 0.0f);
    }

    public void renderHead(float f, float f1) {
        this.head.rotateAngleX = f1 / 180.0f * 3.1415927f;
        this.head.render(f);
        this.jaw.render(f);
    }

    public void renderBody(float f) {
        this.body.render(f);
        this.rightArm.render(f);
        this.leftArm.render(f);
    }

    public void renderBase(float f) {
        this.rightLeg.render(f);
        this.leftLeg.render(f);
        this.base.render(f);
    }
}
