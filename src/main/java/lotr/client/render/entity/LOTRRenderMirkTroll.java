package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkTroll extends LOTRRenderTroll {
    private static LOTRRandomSkins mirkSkins;
    private static LOTRRandomSkins mirkArmorSkins;

    public LOTRRenderMirkTroll() {
        mirkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll");
        mirkArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll_armor");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return mirkSkins.getRandomSkin((LOTREntityMirkTroll) entity);
    }

    @Override
    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        ((LOTRModelTroll) this.mainModel).renderBattleaxe(0.0625f);
    }

    @Override
    protected void bindTrollOutfitTexture(EntityLivingBase entity) {
        this.bindTexture(mirkArmorSkins.getRandomSkin((LOTREntityMirkTroll) entity));
    }
}
