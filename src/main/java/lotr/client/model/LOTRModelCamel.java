package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelCamel extends ModelBase {
    private ModelRenderer body;
    private ModelRenderer humps;
    private ModelRenderer tail;
    private ModelRenderer head;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer chest;

    public LOTRModelCamel() {
        this(0.0f);
    }

    public LOTRModelCamel(float f) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 16);
        this.body.setRotationPoint(0.0f, 10.0f, 0.0f);
        this.body.addBox(-4.5f, -5.0f, -10.0f, 9, 10, 22, f);
        this.humps = new ModelRenderer(this, 34, 0);
        this.humps.setRotationPoint(0.0f, 10.0f, 0.0f);
        this.humps.addBox(-3.0f, -9.0f, -8.0f, 6, 4, 6, f);
        this.humps.addBox(-3.0f, -9.0f, 5.0f, 6, 4, 6, f);
        this.tail = new ModelRenderer(this, 54, 52);
        this.tail.setRotationPoint(0.0f, 7.0f, 12.0f);
        this.tail.addBox(-1.0f, -1.0f, 0.0f, 2, 10, 2);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0f, 6.0f, -10.0f);
        this.head.addBox(-3.0f, -13.0f, -10.5f, 6, 5, 11, f);
        this.head.addBox(-2.5f, -15.0f, -1.0f, 2, 2, 1, f);
        this.head.mirror = true;
        this.head.addBox(0.5f, -15.0f, -1.0f, 2, 2, 1, f);
        this.head.mirror = false;
        this.head.setTextureOffset(0, 16).addBox(-2.5f, -9.0f, -5.0f, 5, 14, 5, f);
        this.leg1 = new ModelRenderer(this, 0, 52);
        this.leg1.setRotationPoint(-4.5f, 7.0f, 8.0f);
        this.leg1.addBox(-4.0f, -1.0f, -2.5f, 4, 7, 5, f);
        this.leg1.setTextureOffset(18, 53).addBox(-3.5f, 6.0f, -1.5f, 3, 8, 3, f);
        this.leg1.setTextureOffset(30, 57).addBox(-4.0f, 14.0f, -2.0f, 4, 3, 4, f);
        this.leg2 = new ModelRenderer(this, 0, 52);
        this.leg2.mirror = true;
        this.leg2.setRotationPoint(4.5f, 7.0f, 8.0f);
        this.leg2.addBox(0.0f, -1.0f, -2.5f, 4, 7, 5, f);
        this.leg2.setTextureOffset(18, 53).addBox(0.5f, 6.0f, -1.5f, 3, 8, 3, f);
        this.leg2.setTextureOffset(30, 57).addBox(0.0f, 14.0f, -2.0f, 4, 3, 4, f);
        this.leg3 = new ModelRenderer(this, 0, 52);
        this.leg3.setRotationPoint(-4.5f, 7.0f, -5.0f);
        this.leg3.addBox(-4.0f, -1.0f, -2.5f, 4, 7, 5, f);
        this.leg3.setTextureOffset(18, 53).addBox(-3.5f, 6.0f, -1.5f, 3, 8, 3, f);
        this.leg3.setTextureOffset(30, 57).addBox(-4.0f, 14.0f, -2.0f, 4, 3, 4, f);
        this.leg4 = new ModelRenderer(this, 0, 52);
        this.leg4.mirror = true;
        this.leg4.setRotationPoint(4.5f, 7.0f, -5.0f);
        this.leg4.addBox(0.0f, -1.0f, -2.5f, 4, 7, 5, f);
        this.leg4.setTextureOffset(18, 53).addBox(0.5f, 6.0f, -1.5f, 3, 8, 3, f);
        this.leg4.setTextureOffset(30, 57).addBox(0.0f, 14.0f, -2.0f, 4, 3, 4, f);
        this.chest = new ModelRenderer(this, 40, 22);
        this.chest.setRotationPoint(0.0f, 10.0f, 0.0f);
        this.chest.addBox(-7.5f, -4.5f, -2.5f, 3, 8, 8, f);
        this.chest.mirror = true;
        this.chest.addBox(4.5f, -4.5f, -2.5f, 3, 8, 8, f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        LOTREntityCamel camel = (LOTREntityCamel) entity;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.body.render(f5);
            this.tail.render(f5);
            this.head.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.body.render(f5);
            this.humps.render(f5);
            this.tail.render(f5);
            this.head.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            if(camel.isChested()) {
                this.chest.render(f5);
            }
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = f4 / (float) Math.toDegrees(1.0);
        this.head.rotateAngleY = f3 / (float) Math.toDegrees(1.0);
        this.head.rotateAngleX += MathHelper.cos(f * 0.3331f) * 0.1f * f1;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.8f * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.8f * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
        this.tail.rotateAngleZ = 0.1f * MathHelper.cos(f * 0.3331f + 3.1415927f) * f1;
    }
}
