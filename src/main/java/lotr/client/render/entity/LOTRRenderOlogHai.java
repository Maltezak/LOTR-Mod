package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityOlogHai;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderOlogHai extends LOTRRenderTroll {
    private static LOTRRandomSkins ologSkins;
    private static LOTRRandomSkins ologArmorSkins;

    public LOTRRenderOlogHai() {
        ologSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai");
        ologArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai_armor");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ologSkins.getRandomSkin((LOTREntityOlogHai) entity);
    }

    @Override
    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        ((LOTRModelTroll) this.mainModel).renderWarhammer(0.0625f);
    }

    @Override
    protected void bindTrollOutfitTexture(EntityLivingBase entity) {
        this.bindTexture(ologArmorSkins.getRandomSkin((LOTREntityOlogHai) entity));
    }
}
