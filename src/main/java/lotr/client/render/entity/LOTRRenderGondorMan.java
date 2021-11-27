package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGondorMan extends LOTRRenderBiped {
    private static LOTRRandomSkins skinsMale;
    private static LOTRRandomSkins skinsFemale;
    private static LOTRRandomSkins skinsSoldier;
    private static LOTRRandomSkins outfits;
    private static LOTRRandomSkins headwearFemale;
    protected ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

    public LOTRRenderGondorMan() {
        super(new LOTRModelHuman(), 0.5f);
        this.setRenderPassModel(this.outfitModel);
        skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_male");
        skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_female");
        skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondorSoldier");
        outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/outfit");
        headwearFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/headwear_female");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityGondorMan gondorian = (LOTREntityGondorMan) entity;
        if(gondorian.familyInfo.isMale()) {
            if(gondorian instanceof LOTREntityGondorSoldier) {
                return skinsSoldier.getRandomSkin(gondorian);
            }
            return skinsMale.getRandomSkin(gondorian);
        }
        return skinsFemale.getRandomSkin(gondorian);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityGondorMan gondorian = (LOTREntityGondorMan) entity;
        if(pass == 1 && gondorian.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(outfits.getRandomSkin(gondorian));
            return 1;
        }
        if(pass == 0 && gondorian.getEquipmentInSlot(4) == null && !gondorian.familyInfo.isMale() && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(headwearFemale.getRandomSkin(gondorian));
            return 1;
        }
        return super.shouldRenderPass(gondorian, pass, f);
    }
}
