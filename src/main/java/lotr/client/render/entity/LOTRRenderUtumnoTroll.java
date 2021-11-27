package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderUtumnoTroll extends LOTRRenderTroll {
    private static LOTRRandomSkins utumnoTrollSkins;

    public LOTRRenderUtumnoTroll() {
        utumnoTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/utumno");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return utumnoTrollSkins.getRandomSkin((LOTREntityTroll) entity);
    }

    @Override
    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        ((LOTRModelTroll) this.mainModel).renderWoodenClubWithSpikes(0.0625f);
    }
}
