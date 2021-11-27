package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelAleHorn extends ModelBase {
    private ModelRenderer horn = new ModelRenderer(this, 28, 16);
    private ModelRenderer horn1;
    private ModelRenderer horn2;
    private ModelRenderer horn3;
    private ModelRenderer stand;

    public LOTRModelAleHorn() {
        this.horn.setRotationPoint(-4.0f, -5.0f, 0.0f);
        this.horn.addBox(-1.0f, -1.0f, -1.0f, 2, 6, 2);
        this.horn1 = new ModelRenderer(this, 16, 16);
        this.horn1.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.horn1.addBox(-1.5f, -6.0f, -1.5f, 3, 6, 3);
        this.horn.addChild(this.horn1);
        this.horn2 = new ModelRenderer(this, 0, 16);
        this.horn2.setRotationPoint(0.0f, -5.0f, 0.0f);
        this.horn2.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 4);
        this.horn1.addChild(this.horn2);
        this.horn3 = new ModelRenderer(this, 0, 0);
        this.horn3.setRotationPoint(0.0f, -5.0f, 0.0f);
        this.horn3.addBox(-2.5f, -1.0f, -2.5f, 5, 1, 5);
        this.horn3.setTextureOffset(0, 6).addBox(-2.5f, -6.0f, -1.5f, 1, 5, 3);
        this.horn3.setTextureOffset(8, 8).addBox(-2.5f, -6.0f, -2.5f, 5, 5, 1);
        this.horn3.setTextureOffset(20, 6).addBox(1.5f, -6.0f, -1.5f, 1, 5, 3);
        this.horn3.setTextureOffset(28, 8).addBox(-2.5f, -6.0f, 1.5f, 5, 5, 1);
        this.horn2.addChild(this.horn3);
        this.horn.rotateAngleZ = (float) Math.toRadians(90.0);
        this.horn1.rotateAngleZ = (float) Math.toRadians(-20.0);
        this.horn2.rotateAngleZ = (float) Math.toRadians(-20.0);
        this.horn3.rotateAngleZ = (float) Math.toRadians(-20.0);
        this.stand = new ModelRenderer(this, 40, 16);
        this.stand.setRotationPoint(0.0f, -1.0f, 0.0f);
        this.stand.addBox(1.5f, -8.0f, -2.5f, 1, 9, 1);
        this.stand.addBox(1.5f, -8.0f, 1.5f, 1, 9, 1);
        this.stand.setTextureOffset(44, 16).addBox(-2.5f, -6.0f, -0.5f, 1, 7, 1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.horn.render(f5);
        this.stand.render(f5);
    }

    public void prepareLiquid(float f) {
        this.horn.postRender(f);
        this.horn1.postRender(f);
        this.horn2.postRender(f);
        this.horn3.postRender(f);
    }
}
