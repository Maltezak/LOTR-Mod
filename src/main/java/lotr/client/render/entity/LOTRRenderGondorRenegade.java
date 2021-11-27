package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityGondorRenegade;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderGondorRenegade extends LOTRRenderGondorMan {
    private static LOTRRandomSkins hoodSkins;

    public LOTRRenderGondorRenegade() {
        hoodSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/renegade_hood");
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityGondorRenegade renegade = (LOTREntityGondorRenegade) entity;
        if(pass == 0 && renegade.getEquipmentInSlot(4) == null) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(hoodSkins.getRandomSkin(renegade));
            return 1;
        }
        return super.shouldRenderPass(renegade, pass, f);
    }
}
