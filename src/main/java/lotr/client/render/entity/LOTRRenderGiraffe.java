package lotr.client.render.entity;

import lotr.client.model.LOTRModelGiraffe;
import lotr.common.entity.animal.LOTREntityGiraffe;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGiraffe extends RenderLiving {
    public static ResourceLocation texture = new ResourceLocation("lotr:mob/giraffe/giraffe.png");
    private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/giraffe/saddle.png");

    public LOTRRenderGiraffe() {
        super(new LOTRModelGiraffe(0.0f), 0.5f);
        this.setRenderPassModel(new LOTRModelGiraffe(0.5f));
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        if(pass == 0 && ((LOTREntityGiraffe) entity).isMountSaddled()) {
            this.bindTexture(saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
