package lotr.client.render.entity;

import lotr.client.model.*;
import lotr.common.entity.animal.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderLion extends RenderLiving {
    public static ResourceLocation textureLion = new ResourceLocation("lotr:mob/lion/lion.png");
    public static ResourceLocation textureLioness = new ResourceLocation("lotr:mob/lion/lioness.png");
    private static ResourceLocation textureTicket = new ResourceLocation("lotr:mob/lion/ticketlion.png");
    private static ModelBase lionModel = new LOTRModelLion();
    private static ModelBase lionModelOld = new LOTRModelLionOld();

    public LOTRRenderLion() {
        super(lionModel, 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityLionBase lion = (LOTREntityLionBase) entity;
        if(LOTRRenderLion.isTicket(lion)) {
            return textureTicket;
        }
        return lion instanceof LOTREntityLioness ? textureLioness : textureLion;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        LOTREntityLionBase lion = (LOTREntityLionBase) entity;
        this.mainModel = LOTRRenderLion.isTicket(lion) ? lionModelOld : lionModel;
        super.doRender(entity, d, d1, d2, f, f1);
    }

    private static boolean isTicket(LOTREntityLionBase lion) {
        return lion.hasCustomNameTag() && lion.getCustomNameTag().equalsIgnoreCase("ticket lion");
    }
}
