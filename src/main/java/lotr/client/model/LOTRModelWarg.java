package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelWarg extends ModelBase implements LOTRGlowingEyes.Model {
    public ModelRenderer body = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public LOTRModelWarg() {
        this(0.0f);
    }

    public LOTRModelWarg(float f) {
        this.body.addBox(-8.0f, -2.0f, -14.0f, 16, 14, 14, f);
        this.body.setRotationPoint(0.0f, 2.0f, 1.0f);
        this.body.setTextureOffset(0, 28).addBox(-6.5f, 0.0f, 0.0f, 13, 11, 18, f);
        this.tail = new ModelRenderer(this, 98, 55).setTextureSize(128, 64);
        this.tail.addBox(-1.0f, -1.0f, 0.0f, 2, 1, 8, f);
        this.tail.setRotationPoint(0.0f, 4.0f, 18.0f);
        this.head = new ModelRenderer(this, 92, 0).setTextureSize(128, 64);
        this.head.addBox(-5.0f, -5.0f, -8.0f, 10, 10, 8, f);
        this.head.setRotationPoint(0.0f, 8.0f, -13.0f);
        this.head.setTextureOffset(108, 18).addBox(-3.0f, -1.0f, -12.0f, 6, 5, 4, f);
        this.head.setTextureOffset(102, 18).addBox(-4.0f, -7.8f, -3.0f, 2, 3, 1, f);
        this.head.setTextureOffset(102, 18).addBox(2.0f, -7.8f, -3.0f, 2, 3, 1, f);
        this.leg1 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
        this.leg1.mirror = true;
        this.leg1.addBox(-6.0f, -1.0f, -2.5f, 6, 9, 8, f);
        this.leg1.setRotationPoint(-4.0f, 6.0f, 12.0f);
        this.leg1.setTextureOffset(66, 17).addBox(-5.5f, 8.0f, -1.0f, 5, 10, 5, f);
        this.leg2 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
        this.leg2.addBox(0.0f, -1.0f, -2.5f, 6, 9, 8, f);
        this.leg2.setRotationPoint(4.0f, 6.0f, 12.0f);
        this.leg2.setTextureOffset(66, 17).addBox(0.5f, 8.0f, -1.0f, 5, 10, 5, f);
        this.leg3 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
        this.leg3.mirror = true;
        this.leg3.addBox(-6.0f, -1.0f, -2.5f, 6, 9, 8, f);
        this.leg3.setRotationPoint(-6.0f, 5.0f, -8.0f);
        this.leg3.setTextureOffset(66, 17).addBox(-5.5f, 8.0f, -1.0f, 5, 11, 5, f);
        this.leg4 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
        this.leg4.addBox(0.0f, -1.0f, -2.5f, 6, 9, 8, f);
        this.leg4.setRotationPoint(6.0f, 5.0f, -8.0f);
        this.leg4.setTextureOffset(66, 17).addBox(0.5f, 8.0f, -1.0f, 5, 11, 5, f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.tail.render(f5);
        this.head.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = f4 / 57.295776f;
        this.head.rotateAngleY = f3 / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
        this.tail.rotateAngleX = ((LOTREntityWarg) entity).getTailRotation();
    }

    @Override
    public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
    }
}
