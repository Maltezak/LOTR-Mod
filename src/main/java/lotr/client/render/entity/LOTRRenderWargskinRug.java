package lotr.client.render.entity;

import lotr.client.model.LOTRModelWargskinRug;
import lotr.common.entity.item.LOTREntityWargskinRug;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWargskinRug extends LOTRRenderRugBase {
    public LOTRRenderWargskinRug() {
        super(new LOTRModelWargskinRug());
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityWargskinRug rug = (LOTREntityWargskinRug) entity;
        return LOTRRenderWarg.getWargSkin(rug.getRugType());
    }
}
