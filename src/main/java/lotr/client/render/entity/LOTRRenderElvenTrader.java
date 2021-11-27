package lotr.client.render.entity;

import lotr.client.model.LOTRModelElf;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElvenTrader extends LOTRRenderElf {
    private ResourceLocation outfitTexture;
    private ModelBiped outfitModel = new LOTRModelElf(0.5f);

    public LOTRRenderElvenTrader(String s) {
        this.setRenderPassModel(this.outfitModel);
        this.outfitTexture = new ResourceLocation("lotr:mob/elf/" + s + ".png");
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
