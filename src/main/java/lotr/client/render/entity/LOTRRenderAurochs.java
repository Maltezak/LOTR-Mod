package lotr.client.render.entity;

import lotr.client.model.LOTRModelAurochs;
import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderAurochs extends RenderLiving {
    private static LOTRRandomSkins aurochsSkins;

    public LOTRRenderAurochs() {
        super(new LOTRModelAurochs(), 0.5f);
        aurochsSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/aurochs");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityAurochs aurochs = (LOTREntityAurochs) entity;
        ResourceLocation skin = aurochsSkins.getRandomSkin(aurochs);
        return skin;
    }
}
