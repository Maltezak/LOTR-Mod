package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderTauredainShaman extends LOTRRenderTauredain {
    private static LOTRRandomSkins outfits;

    public LOTRRenderTauredainShaman() {
        outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/shaman_outfit");
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityTauredain tauredain = (LOTREntityTauredain) entity;
        if(pass == 1 && tauredain.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(outfits.getRandomSkin(tauredain));
            return 1;
        }
        return super.shouldRenderPass(tauredain, pass, f);
    }
}
