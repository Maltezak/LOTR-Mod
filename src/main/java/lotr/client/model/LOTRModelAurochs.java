package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelAurochs extends ModelBase {
    private ModelRenderer body;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer tail;
    private ModelRenderer head;
    private ModelRenderer horns;
    private ModelRenderer hornLeft1;
    private ModelRenderer hornLeft2;
    private ModelRenderer hornRight1;
    private ModelRenderer hornRight2;

    public LOTRModelAurochs() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0f, 2.0f, -1.0f);
        this.body.addBox(-8.0f, -6.0f, -11.0f, 16, 16, 26);
        this.body.setTextureOffset(28, 42).addBox(-8.0f, -8.0f, -8.0f, 16, 2, 10);
        this.body.setTextureOffset(84, 31).addBox(-3.0f, 10.0f, 4.0f, 6, 1, 6);
        this.leg1 = new ModelRenderer(this, 0, 42);
        this.leg1.setRotationPoint(-5.0f, 12.0f, 9.0f);
        this.leg1.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
        this.leg2 = new ModelRenderer(this, 0, 42);
        this.leg2.setRotationPoint(5.0f, 12.0f, 9.0f);
        this.leg2.mirror = true;
        this.leg2.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
        this.leg3 = new ModelRenderer(this, 0, 42);
        this.leg3.setRotationPoint(-5.0f, 12.0f, -7.0f);
        this.leg3.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
        this.leg4 = new ModelRenderer(this, 0, 42);
        this.leg4.setRotationPoint(5.0f, 12.0f, -7.0f);
        this.leg4.mirror = true;
        this.leg4.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
        this.tail = new ModelRenderer(this, 20, 42);
        this.tail.setRotationPoint(0.0f, 1.0f, 14.0f);
        this.tail.addBox(-1.0f, -1.0f, 0.0f, 2, 12, 1);
        this.head = new ModelRenderer(this, 58, 0);
        this.head.setRotationPoint(0.0f, -1.0f, -10.0f);
        this.head.addBox(-5.0f, -4.0f, -12.0f, 10, 10, 11);
        this.head.setTextureOffset(89, 0).addBox(-3.0f, 1.0f, -15.0f, 6, 4, 4);
        this.head.setTextureOffset(105, 0);
        this.head.addBox(-8.0f, -2.5f, -7.0f, 3, 2, 1);
        this.head.mirror = true;
        this.head.addBox(5.0f, -2.5f, -7.0f, 3, 2, 1);
        this.head.mirror = false;
        this.horns = new ModelRenderer(this, 98, 21);
        this.horns.setRotationPoint(0.0f, -3.5f, -5.0f);
        this.horns.addBox(-6.0f, -1.5f, -1.5f, 12, 3, 3);
        this.head.addChild(this.horns);
        this.hornLeft1 = new ModelRenderer(this, 112, 27);
        this.hornLeft1.setRotationPoint(-6.0f, 0.0f, 0.0f);
        this.hornLeft1.addBox(-5.0f, -1.0f, -1.0f, 6, 2, 2);
        this.hornLeft2 = new ModelRenderer(this, 114, 31);
        this.hornLeft2.setRotationPoint(-5.0f, 0.0f, 0.0f);
        this.hornLeft2.addBox(-5.0f, -0.5f, -0.5f, 6, 1, 1);
        this.hornLeft1.addChild(this.hornLeft2);
        this.horns.addChild(this.hornLeft1);
        this.hornRight1 = new ModelRenderer(this, 112, 27);
        this.hornRight1.mirror = true;
        this.hornRight1.setRotationPoint(6.0f, 0.0f, 0.0f);
        this.hornRight1.addBox(-1.0f, -1.0f, -1.0f, 6, 2, 2);
        this.hornRight2 = new ModelRenderer(this, 114, 31);
        this.hornRight2.mirror = true;
        this.hornRight2.setRotationPoint(5.0f, 0.0f, 0.0f);
        this.hornRight2.addBox(-1.0f, -0.5f, -0.5f, 6, 1, 1);
        this.hornRight1.addChild(this.hornRight2);
        this.horns.addChild(this.hornRight1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.horns.showModel = !this.isChild;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if(this.isChild) {
            float f6 = 2.0f;
            float f7 = 8.0f;
            float f8 = 6.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, f7 * f5, f8 * f5);
            this.head.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(f5);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            this.tail.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        LOTREntityAurochs aurochs = (LOTREntityAurochs) entity;
        this.head.rotateAngleX = 0.0f;
        this.head.rotateAngleY = 0.0f;
        this.head.rotateAngleZ = 0.0f;
        this.head.rotateAngleX += (float) Math.toRadians(f4);
        this.head.rotateAngleY += (float) Math.toRadians(f3);
        if(aurochs.isAurochsEnraged()) {
            this.head.rotateAngleX += (float) Math.toRadians(15.0);
        }
        this.head.rotateAngleX += MathHelper.cos(f * 0.2f) * f1 * 0.4f;
        this.hornLeft1.rotateAngleZ = (float) Math.toRadians(25.0);
        this.hornLeft2.rotateAngleZ = (float) Math.toRadians(15.0);
        this.hornRight1.rotateAngleZ = -this.hornLeft1.rotateAngleZ;
        this.hornRight2.rotateAngleZ = -this.hornLeft2.rotateAngleZ;
        this.hornLeft1.rotateAngleY = (float) Math.toRadians(-25.0);
        this.hornRight1.rotateAngleY = -this.hornLeft1.rotateAngleY;
        this.hornRight1.rotateAngleX = this.hornLeft1.rotateAngleX = (float) Math.toRadians(35.0);
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.4f) * f1 * 0.8f;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.8f;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.8f;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.4f) * f1 * 0.8f;
    }
}
