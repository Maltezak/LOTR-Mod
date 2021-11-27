package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.model.*;
import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGandalf
extends LOTRRenderBiped {
    private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/gandalf.png");
    private static ResourceLocation hat = new ResourceLocation("lotr:mob/char/gandalf_hat.png");
    private static ResourceLocation cloak = new ResourceLocation("lotr:mob/char/gandalf_cloak.png");
    private static ResourceLocation skin_santa = new ResourceLocation("lotr:mob/char/santa.png");
    private static ResourceLocation hat_santa = new ResourceLocation("lotr:mob/char/santa_hat.png");
    private static ResourceLocation cloak_santa = new ResourceLocation("lotr:mob/char/santa_cloak.png");
    private ModelBiped hatModel = new LOTRModelWizardHat(1.0f);
    private ModelBiped cloakModel = new LOTRModelHuman(0.6f, false);

    public LOTRRenderGandalf() {
        super(new LOTRModelHuman(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        if (LOTRMod.isChristmas()) {
            return skin_santa;
        }
        return skin;
    }

    @Override
    public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
        LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
        super.doRender(gandalf, d, d1, d2, f, f1);
        if (Minecraft.isGuiEnabled() && !LOTRSpeechClient.hasSpeech(gandalf)) {
            this.func_147906_a(gandalf, gandalf.getCommandSenderName(), d, d1 + 0.75, d2, 64);
        }
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
        if (pass == 0 && gandalf.getEquipmentInSlot(4) == null) {
            this.setRenderPassModel(this.hatModel);
            if (LOTRMod.isChristmas()) {
                this.bindTexture(hat_santa);
            } else {
                this.bindTexture(hat);
            }
            return 1;
        }
        if (pass == 1 && gandalf.getEquipmentInSlot(3) == null) {
            this.setRenderPassModel(this.cloakModel);
            if (LOTRMod.isChristmas()) {
                this.bindTexture(cloak_santa);
            } else {
                this.bindTexture(cloak);
            }
            return 1;
        }
        return super.shouldRenderPass(gandalf, pass, f);
    }

    @Override
    protected ResourceLocation getCapeToRender(LOTREntityNPC entity) {
        if (LOTRMod.isChristmas()) {
            return LOTRCapes.GANDALF_SANTA;
        }
        return super.getCapeToRender(entity);
    }
}

