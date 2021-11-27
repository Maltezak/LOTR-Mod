package lotr.client.render.entity;

import lotr.client.model.LOTRModelRhino;
import lotr.common.entity.animal.LOTREntityRhino;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRhino extends RenderLiving {
    private static ResourceLocation rhinoTexture = new ResourceLocation("lotr:mob/rhino/rhino.png");
    private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/rhino/saddle.png");

    public LOTRRenderRhino() {
        super(new LOTRModelRhino(), 0.5f);
        this.setRenderPassModel(new LOTRModelRhino(0.5f));
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityRhino rhino = (LOTREntityRhino) entity;
        return LOTRRenderHorse.getLayeredMountTexture(rhino, rhinoTexture);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        if(pass == 0 && ((LOTREntityRhino) entity).isMountSaddled()) {
            this.bindTexture(saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
