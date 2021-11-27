package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHuman extends LOTRModelBiped {
    public ModelRenderer bipedChest = new ModelRenderer(this, 24, 0);

    public LOTRModelHuman() {
        this(0.0f, false);
    }

    public LOTRModelHuman(float f, boolean armor) {
        super(f, 0.0f, 64, armor ? 32 : 64);
        this.bipedChest.addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
        this.bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addChild(this.bipedChest);
        if(!armor) {
            this.bipedHeadwear = new ModelRenderer(this, 0, 32);
            this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 16, 8, 0.5f + f);
            this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
        if(this.isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            this.bipedHead.render(f5);
            this.bipedHeadwear.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            GL11.glPopMatrix();
        }
        else {
            this.bipedHead.render(f5);
            this.bipedHeadwear.render(f5);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
        }
    }
}
