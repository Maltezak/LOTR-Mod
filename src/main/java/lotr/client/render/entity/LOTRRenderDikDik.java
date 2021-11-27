package lotr.client.render.entity;

import lotr.client.model.LOTRModelDikDik;
import lotr.common.entity.animal.LOTREntityDikDik;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDikDik extends RenderLiving {
    private static LOTRRandomSkins skins;

    public LOTRRenderDikDik() {
        super(new LOTRModelDikDik(), 0.8f);
        skins = LOTRRandomSkins.loadSkinsList("lotr:mob/dikdik");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityDikDik dikdik = (LOTREntityDikDik) entity;
        return skins.getRandomSkin(dikdik);
    }
}
