package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDaleTrader extends LOTRRenderDaleMan {
    private ResourceLocation traderOutfit;

    public LOTRRenderDaleTrader(String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/dale/" + s + ".png");
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityDaleMan man = (LOTREntityDaleMan) entity;
        if(pass == 1 && man.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass(man, pass, f);
    }
}
