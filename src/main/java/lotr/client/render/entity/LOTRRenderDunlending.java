package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunlending extends LOTRRenderDunlendingBase {
    private static LOTRRandomSkins dunlendingOutfits;
    private static ResourceLocation outfitApron;
    private ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

    public LOTRRenderDunlending() {
        this.setRenderPassModel(this.outfitModel);
        dunlendingOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/outfit");
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityDunlending dunlending = (LOTREntityDunlending) entity;
        if(pass == 1 && dunlending.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.outfitModel);
            if(dunlending instanceof LOTREntityDunlendingBartender) {
                this.bindTexture(outfitApron);
            }
            else {
                this.bindTexture(dunlendingOutfits.getRandomSkin(dunlending));
            }
            return 1;
        }
        return super.shouldRenderPass(dunlending, pass, f);
    }

    static {
        outfitApron = new ResourceLocation("lotr:mob/dunland/bartender_apron.png");
    }
}
