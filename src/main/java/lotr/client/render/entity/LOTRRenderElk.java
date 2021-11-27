package lotr.client.render.entity;

import lotr.client.model.LOTRModelElk;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElk extends RenderLiving {
    private static LOTRRandomSkins elkSkins;
    private static ResourceLocation saddleTexture;

    public LOTRRenderElk() {
        super(new LOTRModelElk(), 0.5f);
        this.setRenderPassModel(new LOTRModelElk(0.5f));
        elkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elk/elk");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityElk elk = (LOTREntityElk) entity;
        ResourceLocation elkSkin = elkSkins.getRandomSkin(elk);
        return LOTRRenderHorse.getLayeredMountTexture(elk, elkSkin);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        if(pass == 0 && ((LOTREntityElk) entity).isMountSaddled()) {
            this.bindTexture(saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    static {
        saddleTexture = new ResourceLocation("lotr:mob/elk/saddle.png");
    }
}
