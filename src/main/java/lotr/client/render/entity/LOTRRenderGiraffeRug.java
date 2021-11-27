package lotr.client.render.entity;

import lotr.client.model.LOTRModelGiraffeRug;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGiraffeRug extends LOTRRenderRugBase {
    public LOTRRenderGiraffeRug() {
        super(new LOTRModelGiraffeRug());
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return LOTRRenderGiraffe.texture;
    }
}
