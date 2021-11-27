package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRohirrim extends LOTRRenderBiped {
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    private static LOTRRandomSkins skinsShieldmaiden;
    protected ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

    public LOTRRenderRohirrim() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel(this.outfitModel);
        skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_male");
        skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_female");
        skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/warrior");
        skinsShieldmaiden = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/shieldmaiden");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityRohanMan rohirrim = (LOTREntityRohanMan) entity;
        if(rohirrim.familyInfo.isMale()) {
            if(rohirrim instanceof LOTREntityRohirrimWarrior) {
                return skinsSoldier.getRandomSkin(rohirrim);
            }
            return skinsMale.getRandomSkin(rohirrim);
        }
        if(rohirrim instanceof LOTREntityRohanShieldmaiden) {
            return skinsShieldmaiden.getRandomSkin(rohirrim);
        }
        return skinsFemale.getRandomSkin(rohirrim);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityRohanMan rohirrim = (LOTREntityRohanMan) entity;
        return super.shouldRenderPass(rohirrim, pass, f);
    }
}
