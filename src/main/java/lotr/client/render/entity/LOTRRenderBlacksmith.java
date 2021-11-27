package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBlacksmith extends LOTRRenderBiped {
    private LOTRRandomSkins skins;
    private ResourceLocation apron;
    private ModelBiped standardRenderPassModel = new LOTRModelHuman(0.5f, false);

    public LOTRRenderBlacksmith(String s, String s1) {
        super(new LOTRModelHuman(), 0.5f);
        this.skins = LOTRRandomSkins.loadSkinsList("lotr:mob/" + s);
        this.apron = new ResourceLocation("lotr:mob/" + s1 + ".png");
        this.setRenderPassModel(this.standardRenderPassModel);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return this.skins.getRandomSkin((LOTREntityNPC) entity);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        if(pass == 1) {
            this.setRenderPassModel(this.standardRenderPassModel);
            this.bindTexture(this.apron);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
}
