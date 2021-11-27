package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHobbitTrader
extends LOTRRenderHobbit {
    private ResourceLocation traderOutfit;

    public LOTRRenderHobbitTrader(String s) {
        this.traderOutfit = new ResourceLocation("lotr:mob/hobbit/" + s + ".png");
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
        if (pass == 1 && hobbit.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(this.traderOutfit);
            return 1;
        }
        return super.shouldRenderPass(hobbit, pass, f);
    }
}

