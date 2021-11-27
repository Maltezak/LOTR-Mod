package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWickedDwarf extends LOTRRenderDwarf {
    private static LOTRRandomSkins wickedSkinsMale;
    private static final ResourceLocation apronTexture;

    public LOTRRenderWickedDwarf() {
        wickedSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/wicked_male");
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityDwarf dwarf = (LOTREntityDwarf) entity;
        return wickedSkinsMale.getRandomSkin(dwarf);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityDwarf dwarf = (LOTREntityDwarf) entity;
        if(pass == 1 && dwarf.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.standardRenderPassModel);
            this.bindTexture(apronTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    static {
        apronTexture = new ResourceLocation("lotr:mob/dwarf/wicked_apron.png");
    }
}
