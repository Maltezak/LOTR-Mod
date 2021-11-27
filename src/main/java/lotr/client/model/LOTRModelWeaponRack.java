package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelWeaponRack extends ModelBase {
    private ModelRenderer base = new ModelRenderer(this, 0, 0);
    private ModelRenderer stand;
    private ModelRenderer holder;
    private ModelRenderer holderUpperParts;
    public boolean onWall = false;

    public LOTRModelWeaponRack() {
        this.base.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.base.addBox(-7.0f, -2.0f, -3.0f, 14, 2, 6);
        this.stand = new ModelRenderer(this, 34, 0);
        this.stand.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.stand.addBox(-4.0f, -4.0f, -0.5f, 8, 1, 1);
        this.stand.setTextureOffset(52, 0);
        this.stand.addBox(-6.0f, -6.0f, -1.0f, 2, 6, 2);
        this.stand.mirror = true;
        this.stand.addBox(4.0f, -6.0f, -1.0f, 2, 6, 2);
        this.holder = new ModelRenderer(this, 0, 8);
        this.holder.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.holder.addBox(-7.0f, -1.0f, -2.0f, 14, 1, 4);
        this.holder.setTextureOffset(6, 13);
        this.holder.addBox(-6.0f, -2.0f, -1.5f, 2, 1, 3);
        this.holder.mirror = true;
        this.holder.addBox(4.0f, -2.0f, -1.5f, 2, 1, 3);
        this.holder.mirror = false;
        this.holder.setTextureOffset(0, 13);
        this.holder.addBox(-6.0f, -3.0f, 0.5f, 2, 1, 1);
        this.holder.mirror = true;
        this.holder.addBox(4.0f, -3.0f, 0.5f, 2, 1, 1);
        this.holderUpperParts = new ModelRenderer(this, 0, 13);
        this.holderUpperParts.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.holderUpperParts.addBox(-6.0f, -3.0f, -1.5f, 2, 1, 1);
        this.holderUpperParts.mirror = true;
        this.holderUpperParts.addBox(4.0f, -3.0f, -1.5f, 2, 1, 1);
        this.base.addChild(this.stand);
        this.base.addChild(this.holder);
        this.holder.addChild(this.holderUpperParts);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if(this.onWall) {
            this.base.rotateAngleX = (float) Math.toRadians(-90.0);
            this.stand.isHidden = true;
            this.holder.rotateAngleX = 0.0f;
            this.holder.setRotationPoint(0.0f, -2.0f, 0.0f);
            this.holderUpperParts.showModel = false;
        }
        else {
            this.base.rotateAngleX = 0.0f;
            this.stand.isHidden = false;
            this.holder.rotateAngleX = 0.0f;
            this.holder.setRotationPoint(0.0f, -8.0f, 0.0f);
            this.holderUpperParts.showModel = true;
        }
        this.base.render(f5);
    }
}
