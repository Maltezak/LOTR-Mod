package lotr.client.render.entity;

import lotr.client.model.LOTRModelDwarf;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarfCommander extends LOTRRenderDwarf {
    private static ResourceLocation cloak = new ResourceLocation("lotr:mob/dwarf/commander_cloak.png");
    private static ResourceLocation blueCloak = new ResourceLocation("lotr:mob/dwarf/blueMountains_commander_cloak.png");
    private LOTRModelDwarf cloakModel = new LOTRModelDwarf(1.5f);

    protected ResourceLocation getCloakTexture(EntityLivingBase entity) {
        return entity instanceof LOTREntityBlueDwarf ? blueCloak : cloak;
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        if(pass == 0) {
            this.bindTexture(this.getCloakTexture(entity));
            this.cloakModel.bipedHead.showModel = false;
            this.cloakModel.bipedHeadwear.showModel = false;
            this.cloakModel.bipedBody.showModel = true;
            this.cloakModel.bipedRightArm.showModel = true;
            this.cloakModel.bipedLeftArm.showModel = true;
            this.cloakModel.bipedRightLeg.showModel = false;
            this.cloakModel.bipedLeftLeg.showModel = false;
            this.setRenderPassModel(this.cloakModel);
            this.cloakModel.onGround = this.mainModel.onGround;
            this.cloakModel.isRiding = this.mainModel.isRiding;
            this.cloakModel.isChild = this.mainModel.isChild;
            this.cloakModel.heldItemRight = this.modelBipedMain.heldItemRight;
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
