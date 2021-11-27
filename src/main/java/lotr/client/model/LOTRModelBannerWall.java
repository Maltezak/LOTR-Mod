package lotr.client.model;

import net.minecraft.client.model.*;

public class LOTRModelBannerWall extends ModelBase {
    private ModelRenderer post;
    private ModelRenderer banner;

    public LOTRModelBannerWall() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.post = new ModelRenderer(this, 4, 18);
        this.post.setRotationPoint(0.0f, -8.0f, 0.0f);
        this.post.addBox(-8.0f, 0.0f, -0.5f, 16, 1, 1);
        this.banner = new ModelRenderer(this, 0, 0);
        this.banner.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.banner.addBox(-8.0f, 0.0f, 0.0f, 16, 32, 0);
    }

    public void renderPost(float f) {
        this.post.render(f);
    }

    public void renderBanner(float f) {
        this.banner.render(f);
    }
}
