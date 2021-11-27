package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelFish extends ModelBase {
    private ModelRenderer body = new ModelRenderer(this, 0, 0);
    private ModelRenderer finTop;
    private ModelRenderer finRight;
    private ModelRenderer finLeft;
    private ModelRenderer finBack;

    public LOTRModelFish() {
        this.body.setRotationPoint(0.0f, 22.0f, -1.0f);
        this.body.addBox(-0.5f, -2.0f, -3.0f, 1, 3, 6);
        this.finTop = new ModelRenderer(this, 14, 0);
        this.finTop.setRotationPoint(0.0f, 0.0f, -1.5f);
        this.finTop.addBox(0.0f, -2.0f, 0.0f, 0, 2, 4);
        this.body.addChild(this.finTop);
        this.finRight = new ModelRenderer(this, 22, 0);
        this.finRight.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.finRight.addBox(-0.5f, -1.0f, 0.0f, 0, 2, 3);
        this.body.addChild(this.finRight);
        this.finLeft = new ModelRenderer(this, 22, 0);
        this.finLeft.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.finLeft.addBox(0.5f, -1.0f, 0.0f, 0, 2, 3);
        this.body.addChild(this.finLeft);
        this.finBack = new ModelRenderer(this, 0, 9);
        this.finBack.setRotationPoint(0.0f, -0.5f, 1.5f);
        this.finBack.addBox(0.0f, -5.0f, 0.0f, 0, 5, 5);
        this.body.addChild(this.finBack);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.finTop.rotateAngleX = (float) Math.toRadians(27.0);
        this.finRight.rotateAngleX = (float) Math.toRadians(-15.0);
        this.finRight.rotateAngleY = (float) Math.toRadians(-30.0);
        this.finRight.rotateAngleY += MathHelper.cos(f2 * 0.5f + 3.1415927f) * (float) Math.toRadians(10.0);
        this.finLeft.rotateAngleX = this.finRight.rotateAngleX;
        this.finLeft.rotateAngleY = -this.finRight.rotateAngleY;
        this.finBack.rotateAngleX = (float) Math.toRadians(-45.0);
    }
}
