package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class LOTRModelBoar extends ModelPig {
    private ModelRenderer tusks;

    public LOTRModelBoar() {
        this(0.0f);
    }

    public LOTRModelBoar(float f) {
        super(f);
        this.head.setTextureOffset(24, 0).addBox(-3.0f, 0.0f, -10.0f, 6, 4, 2, f);
        this.head.setTextureOffset(40, 0).addBox(-5.0f, -5.0f, -6.0f, 1, 2, 2, f);
        this.head.mirror = true;
        this.head.addBox(4.0f, -5.0f, -6.0f, 1, 2, 2, f);
        this.tusks = new ModelRenderer(this, 0, 0);
        this.tusks.addBox(-4.0f, 2.0f, -11.0f, 1, 1, 2, f);
        this.tusks.setTextureOffset(1, 1).addBox(-4.0f, 1.0f, -11.5f, 1, 1, 1, f);
        this.tusks.mirror = true;
        this.tusks.setTextureOffset(0, 0).addBox(3.0f, 2.0f, -11.0f, 1, 1, 2, f);
        this.tusks.setTextureOffset(1, 1).addBox(3.0f, 1.0f, -11.5f, 1, 1, 1, f);
        this.head.addChild(this.tusks);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.tusks.showModel = !this.isChild;
        super.render(entity, f, f1, f2, f3, f4, f5);
    }
}
