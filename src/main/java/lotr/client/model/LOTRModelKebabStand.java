package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelKebabStand extends ModelBase {
    private ModelRenderer stand;
    private ModelRenderer[] kebab;

    public LOTRModelKebabStand() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.stand = new ModelRenderer(this, 0, 0);
        this.stand.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.stand.addBox(-7.0f, -1.0f, -7.0f, 14, 1, 14);
        this.stand.setTextureOffset(0, 15).addBox(-4.0f, -16.0f, 6.0f, 8, 15, 1);
        this.stand.setTextureOffset(0, 31).addBox(-4.0f, -16.0f, -2.0f, 8, 1, 8);
        this.stand.setTextureOffset(0, 40).addBox(-0.5f, -15.0f, -0.5f, 1, 14, 1);
        ModelRenderer panelRight = new ModelRenderer(this, 18, 15);
        panelRight.setRotationPoint(-4.0f, 0.0f, 6.0f);
        panelRight.addBox(-4.0f, -16.0f, 0.0f, 4, 15, 1);
        panelRight.rotateAngleY = (float) Math.toRadians(-45.0);
        this.stand.addChild(panelRight);
        ModelRenderer panelLeft = new ModelRenderer(this, 18, 15);
        panelLeft.setRotationPoint(4.0f, 0.0f, 6.0f);
        panelLeft.addBox(0.0f, -16.0f, 0.0f, 4, 15, 1);
        panelLeft.rotateAngleY = (float) Math.toRadians(45.0);
        this.stand.addChild(panelLeft);
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.kebab = new ModelRenderer[9];
        for(int i = 0; i < this.kebab.length; ++i) {
            ModelRenderer kb = new ModelRenderer(this, 0, 0);
            kb.setRotationPoint(0.0f, 10.0f, 0.0f);
            if(i > 0) {
                int width = i + 1;
                kb.addBox(-((float) width) / 2.0f, 0.0f, -((float) width) / 2.0f, width, 11, width);
            }
            this.kebab[i] = kb;
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.stand.render(f5);
    }

    public void renderKebab(float scale, int size, float spin) {
        if(size < 0 || size >= this.kebab.length) {
            size = 0;
        }
        this.kebab[size].rotateAngleY = (float) Math.toRadians(spin);
        this.kebab[size].render(scale);
    }
}
