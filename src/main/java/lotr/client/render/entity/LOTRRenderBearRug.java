package lotr.client.render.entity;

import lotr.client.model.LOTRModelBearRug;
import lotr.common.entity.item.LOTREntityBearRug;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBearRug extends LOTRRenderRugBase {
    public LOTRRenderBearRug() {
        super(new LOTRModelBearRug());
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityBearRug rug = (LOTREntityBearRug) entity;
        return LOTRRenderBear.getBearSkin(rug.getRugType());
    }

    @Override
    protected void preRenderCallback() {
        LOTRRenderBear.scaleBearModel();
    }
}
