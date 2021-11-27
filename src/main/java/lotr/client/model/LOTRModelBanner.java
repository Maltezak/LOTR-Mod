package lotr.client.model;

import net.minecraft.client.model.*;

public class LOTRModelBanner extends ModelBase {
    private ModelRenderer stand;
    private ModelRenderer post;
    private ModelRenderer lowerPost;
    private ModelRenderer bannerFront;
    private ModelRenderer bannerBack;

    public LOTRModelBanner() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.stand = new ModelRenderer(this, 0, 0);
        this.stand.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.stand.addBox(-6.0f, -2.0f, -6.0f, 12, 2, 12);
        this.post = new ModelRenderer(this, 0, 14);
        this.post.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.post.addBox(-0.5f, -48.0f, -0.5f, 1, 47, 1);
        this.post.setTextureOffset(4, 14).addBox(-8.0f, -43.0f, -1.5f, 16, 1, 3);
        this.lowerPost = new ModelRenderer(this, 0, 14);
        this.lowerPost.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.lowerPost.addBox(-0.5f, -1.0f, -0.5f, 1, 24, 1);
        this.bannerFront = new ModelRenderer(this, 0, 0);
        this.bannerFront.setRotationPoint(0.0f, -18.0f, 0.0f);
        this.bannerFront.addBox(-8.0f, 0.0f, -1.0f, 16, 32, 0);
        this.bannerBack = new ModelRenderer(this, 0, 0);
        this.bannerBack.setRotationPoint(0.0f, -18.0f, 0.0f);
        this.bannerBack.addBox(-8.0f, 0.0f, -1.0f, 16, 32, 0);
        this.bannerBack.rotateAngleY = 3.1415927f;
    }

    public void renderStand(float f) {
        this.stand.render(f);
    }

    public void renderPost(float f) {
        this.post.render(f);
    }

    public void renderLowerPost(float f) {
        this.lowerPost.render(f);
    }

    public void renderBanner(float f) {
        this.bannerFront.render(f);
        this.bannerBack.render(f);
    }
}
