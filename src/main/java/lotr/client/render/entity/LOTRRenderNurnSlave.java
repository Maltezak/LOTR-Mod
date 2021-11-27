package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderNurnSlave extends LOTRRenderBiped {
    private static LOTRRandomSkins slaveSkinsMale;
    private static LOTRRandomSkins slaveSkinsFemale;

    public LOTRRenderNurnSlave() {
        super(new LOTRModelHuman(), 0.5f);
        slaveSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nurn/slave_male");
        slaveSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nurn/slave_female");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityNurnSlave slave = (LOTREntityNurnSlave) entity;
        if(slave.familyInfo.isMale()) {
            return slaveSkinsMale.getRandomSkin(slave);
        }
        return slaveSkinsFemale.getRandomSkin(slave);
    }
}
