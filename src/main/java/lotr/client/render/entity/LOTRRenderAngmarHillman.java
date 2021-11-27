package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderAngmarHillman extends LOTRRenderBiped {
    private static LOTRRandomSkins hillmanSkinsMale;
    private static LOTRRandomSkins hillmanSkinsFemale;
    private static LOTRRandomSkins hillmanOutfits;
    private ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);
    private boolean useOutfits;

    public LOTRRenderAngmarHillman(boolean outfit) {
        super(new LOTRModelHuman(), 0.5f);
        this.useOutfits = outfit;
        this.setRenderPassModel(this.outfitModel);
        hillmanSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_male");
        hillmanSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_female");
        hillmanOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/outfit");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman) entity;
        if(hillman.familyInfo.isMale()) {
            return hillmanSkinsMale.getRandomSkin(hillman);
        }
        return hillmanSkinsFemale.getRandomSkin(hillman);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman) entity;
        if(this.useOutfits && pass == 1 && hillman.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(hillmanOutfits.getRandomSkin(hillman));
            return 1;
        }
        return super.shouldRenderPass(hillman, pass, f);
    }
}
