package lotr.client.model;

import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelButterfly extends ModelBase {
    private ModelRenderer body = new ModelRenderer(this, 0, 0);
    private ModelRenderer rightWing;
    private ModelRenderer leftWing;

    public LOTRModelButterfly() {
        this.body.addBox(-1.0f, -6.0f, -1.0f, 2, 12, 2);
        this.rightWing = new ModelRenderer(this, 10, 0);
        this.rightWing.addBox(-12.0f, -10.5f, 0.0f, 12, 21, 0);
        this.leftWing = new ModelRenderer(this, 10, 0);
        this.leftWing.mirror = true;
        this.leftWing.addBox(0.0f, -10.5f, 0.0f, 12, 21, 0);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.leftWing);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        LOTREntityButterfly butterfly = (LOTREntityButterfly) entity;
        if(butterfly.isButterflyStill()) {
            this.body.setRotationPoint(0.0f, 24.0f, 0.0f);
            this.body.rotateAngleX = 1.5707964f;
            this.rightWing.rotateAngleY = butterfly.flapTime > 0 ? MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f : 0.31415927f;
            this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        }
        else {
            this.body.setRotationPoint(0.0f, 8.0f, 0.0f);
            this.body.rotateAngleX = 0.7853982f + MathHelper.cos(f2 * 0.1f) * 0.15f;
            this.rightWing.rotateAngleY = MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f;
            this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        }
        this.body.render(f5);
    }
}
