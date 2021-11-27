package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelMug extends ModelBase {
    private ModelRenderer[] mugParts = new ModelRenderer[5];
    private ModelRenderer[] handleParts = new ModelRenderer[3];

    public LOTRModelMug() {
        this.mugParts[0] = new ModelRenderer(this, 0, 0);
        this.mugParts[0].addBox(-3.0f, -8.0f, -2.0f, 1, 8, 4);
        this.mugParts[1] = new ModelRenderer(this, 10, 3);
        this.mugParts[1].addBox(-3.0f, -8.0f, -3.0f, 6, 8, 1);
        this.mugParts[2] = new ModelRenderer(this, 24, 0);
        this.mugParts[2].addBox(2.0f, -8.0f, -2.0f, 1, 8, 4);
        this.mugParts[3] = new ModelRenderer(this, 34, 3);
        this.mugParts[3].addBox(-3.0f, -8.0f, 2.0f, 6, 8, 1);
        this.mugParts[4] = new ModelRenderer(this, 0, 12);
        this.mugParts[4].addBox(-2.0f, -1.0f, -2.0f, 4, 1, 4);
        this.handleParts[0] = new ModelRenderer(this, 0, 17);
        this.handleParts[0].addBox(3.0f, -7.0f, -0.5f, 2, 1, 1);
        this.handleParts[1] = new ModelRenderer(this, 0, 19);
        this.handleParts[1].addBox(4.0f, -6.0f, -0.5f, 1, 4, 1);
        this.handleParts[2] = new ModelRenderer(this, 0, 24);
        this.handleParts[2].addBox(3.0f, -2.0f, -0.5f, 2, 1, 1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        for(ModelRenderer part : this.mugParts) {
            part.render(f5);
        }
        for(ModelRenderer part : this.handleParts) {
            part.render(f5);
        }
    }
}
