package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityBandit;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBandit extends LOTRRenderBiped {
    private LOTRRandomSkins banditSkins;

    public LOTRRenderBandit(String s) {
        super(new LOTRModelHuman(), 0.5f);
        this.banditSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/bandit/" + s);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return this.banditSkins.getRandomSkin((LOTREntityBandit) entity);
    }
}
