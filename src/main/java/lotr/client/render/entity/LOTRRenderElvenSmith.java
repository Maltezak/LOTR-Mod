package lotr.client.render.entity;

import lotr.client.model.LOTRModelElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenSmith extends LOTRRenderElf {
    private ResourceLocation outfitTexture;
    private ResourceLocation capeTexture;
    private ModelBiped outfitModel = new LOTRModelElf(0.5f);

    public LOTRRenderElvenSmith(String s, String s1) {
        this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
        this.capeTexture = new ResourceLocation("lotr:mob/elf/" + s1 + ".png");
        this.setRenderPassModel(this.outfitModel);
    }

    @Override
    public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
        ((LOTREntityNPC) entity).npcCape = this.capeTexture;
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        if(pass == 0) {
            this.setRenderPassModel(this.outfitModel);
            this.bindTexture(this.outfitTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
