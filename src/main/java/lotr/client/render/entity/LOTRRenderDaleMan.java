package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDaleMan extends LOTRRenderBiped {
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    protected ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

    public LOTRRenderDaleMan() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel(this.outfitModel);
        skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_male");
        skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_female");
        skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_soldier");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityDaleMan man = (LOTREntityDaleMan) entity;
        if(man.familyInfo.isMale()) {
            if(man instanceof LOTREntityDaleLevyman) {
                return skinsSoldier.getRandomSkin(man);
            }
            return skinsMale.getRandomSkin(man);
        }
        return skinsFemale.getRandomSkin(man);
    }
}
