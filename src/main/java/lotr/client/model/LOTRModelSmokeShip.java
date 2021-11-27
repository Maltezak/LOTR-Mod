package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelSmokeShip extends ModelBase {
    private ModelRenderer hull = new ModelRenderer(this);
    private ModelRenderer deck;
    private ModelRenderer mast1;
    private ModelRenderer sail1;
    private ModelRenderer mast2;
    private ModelRenderer sail2;
    private ModelRenderer mast3;
    private ModelRenderer sail3;
    private ModelRenderer bow;
    private ModelRenderer stern;

    public LOTRModelSmokeShip() {
        this.hull.addBox(-3.5f, 1.0f, -8.0f, 7, 5, 16);
        this.hull.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.deck = new ModelRenderer(this);
        this.deck.addBox(-5.0f, 0.0f, -8.0f, 10, 1, 16);
        this.deck.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.mast1 = new ModelRenderer(this);
        this.mast1.addBox(-1.0f, -9.0f, -6.0f, 2, 9, 2);
        this.mast1.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.sail1 = new ModelRenderer(this);
        this.sail1.addBox(-6.0f, -8.0f, -5.5f, 12, 6, 1);
        this.sail1.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.mast2 = new ModelRenderer(this);
        this.mast2.addBox(-1.0f, -12.0f, -1.0f, 2, 12, 2);
        this.mast2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.sail2 = new ModelRenderer(this);
        this.sail2.addBox(-8.0f, -11.0f, -0.5f, 16, 8, 1);
        this.sail2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.mast3 = new ModelRenderer(this);
        this.mast3.addBox(-1.0f, -9.0f, 4.0f, 2, 9, 2);
        this.mast3.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.sail3 = new ModelRenderer(this);
        this.sail3.addBox(-6.0f, -8.0f, 4.5f, 12, 6, 1);
        this.sail3.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bow = new ModelRenderer(this);
        this.bow.addBox(-3.5f, -1.0f, -12.0f, 7, 3, 4);
        this.bow.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.stern = new ModelRenderer(this);
        this.stern.addBox(-3.5f, -1.0f, 8.0f, 7, 3, 4);
        this.stern.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.hull.render(f5);
        this.deck.render(f5);
        this.mast1.render(f5);
        this.sail1.render(f5);
        this.mast2.render(f5);
        this.sail2.render(f5);
        this.mast3.render(f5);
        this.sail3.render(f5);
        this.bow.render(f5);
        this.stern.render(f5);
    }
}
