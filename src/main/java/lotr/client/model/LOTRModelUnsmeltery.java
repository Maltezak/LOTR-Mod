package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelUnsmeltery extends ModelBase {
    private ModelRenderer base;
    private ModelRenderer body;
    private ModelRenderer standRight;
    private ModelRenderer standLeft;

    public LOTRModelUnsmeltery() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0f, 21.0f, 0.0f);
        this.base.addBox(-7.0f, 0.0f, -7.0f, 14, 3, 14);
        this.body = new ModelRenderer(this, 0, 17);
        this.body.setRotationPoint(0.0f, 12.0f, 0.0f);
        this.body.addBox(-7.0f, -2.0f, -7.0f, 14, 10, 14);
        this.body.setTextureOffset(0, 41).addBox(-7.0f, -4.0f, -7.0f, 14, 2, 1);
        this.body.addBox(-7.0f, -4.0f, 6.0f, 14, 2, 1);
        this.body.setTextureOffset(0, 44).addBox(-7.0f, -4.0f, -6.0f, 1, 2, 12);
        this.body.addBox(6.0f, -4.0f, -6.0f, 1, 2, 12);
        this.standRight = new ModelRenderer(this, 56, 6);
        this.standRight.setRotationPoint(-7.0f, 23.0f, 0.0f);
        this.standRight.addBox(-0.9f, -12.0f, -1.0f, 1, 12, 2);
        ModelRenderer panelRight = new ModelRenderer(this, 56, 0);
        panelRight.setRotationPoint(0.0f, -11.0f, 0.0f);
        panelRight.addBox(-1.0f, -2.0f, -1.0f, 1, 3, 3);
        panelRight.rotateAngleX = (float) Math.toRadians(45.0);
        this.standRight.addChild(panelRight);
        this.standLeft = new ModelRenderer(this, 56, 6);
        this.standLeft.setRotationPoint(7.0f, 23.0f, 0.0f);
        this.standLeft.mirror = true;
        this.standLeft.addBox(-0.1f, -12.0f, -1.0f, 1, 12, 2);
        ModelRenderer panelLeft = new ModelRenderer(this, 56, 0);
        panelLeft.setRotationPoint(0.0f, -11.0f, 0.0f);
        panelLeft.mirror = true;
        panelLeft.addBox(0.0f, -2.0f, -1.0f, 1, 3, 3);
        panelLeft.rotateAngleX = (float) Math.toRadians(45.0);
        this.standLeft.addChild(panelLeft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.rotateAngleX = f * (float) Math.toRadians(20.0);
        this.base.render(f5);
        this.body.render(f5);
        this.standRight.render(f5);
        this.standLeft.render(f5);
    }
}
