package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMountainTrollChieftain extends LOTRRenderMountainTroll {
    private static ResourceLocation armorTexture = new ResourceLocation("lotr:mob/troll/mountainTrollChieftain_armor.png");
    private LOTRModelTroll helmetModel = new LOTRModelTroll(1.5f, 2);
    private LOTRModelTroll chestplateModel = new LOTRModelTroll(1.5f, 3);

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain) entity;
        if(troll.addedToChunk) {
            BossStatus.setBossStatus(troll, false);
        }
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain) entity;
        this.bindTexture(armorTexture);
        if(pass == 2 && troll.getTrollArmorLevel() >= 2) {
            this.helmetModel.onGround = this.mainModel.onGround;
            this.setRenderPassModel(this.helmetModel);
            return 1;
        }
        if(pass == 3 && troll.getTrollArmorLevel() >= 1) {
            this.chestplateModel.onGround = this.mainModel.onGround;
            this.setRenderPassModel(this.chestplateModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        GL11.glTranslatef(0.0f, -((LOTREntityMountainTrollChieftain) entity).getSpawningOffset(f), 0.0f);
    }
}
