package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSnowTroll extends LOTRRenderTroll {
    private static LOTRRandomSkins snowTrollSkins;

    public LOTRRenderSnowTroll() {
        snowTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/snowTroll");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return snowTrollSkins.getRandomSkin((LOTREntityTroll) entity);
    }

    @Override
    protected void bindTrollOutfitTexture(EntityLivingBase entity) {
    }

    @Override
    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        LOTREntitySnowTroll troll = (LOTREntitySnowTroll) entity;
        if(!troll.isThrowingSnow()) {
            ((LOTRModelTroll) this.mainModel).renderWoodenClubWithSpikes(0.0625f);
        }
    }
}
