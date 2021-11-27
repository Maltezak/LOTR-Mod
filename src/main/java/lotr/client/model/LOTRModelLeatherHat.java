package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LOTRModelLeatherHat extends LOTRModelBiped {
    private static ItemStack feather = new ItemStack(Items.feather);
    private ItemStack hatItem;

    public LOTRModelLeatherHat() {
        this(0.0f);
    }

    public LOTRModelLeatherHat(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-6.0f, -9.0f, -6.0f, 12, 2, 12, f);
        this.bipedHead.setTextureOffset(0, 14).addBox(-4.0f, -13.0f, -4.0f, 8, 4, 8, f);
        this.bipedHeadwear.cubeList.clear();
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    public void setHatItem(ItemStack itemstack) {
        this.hatItem = itemstack;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        int hatColor = LOTRItemLeatherHat.getHatColor(this.hatItem);
        float r = (hatColor >> 16 & 0xFF) / 255.0f;
        float g = (hatColor >> 8 & 0xFF) / 255.0f;
        float b = (hatColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        this.bipedHead.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        if(LOTRItemLeatherHat.hasFeather(this.hatItem)) {
            this.bipedHead.postRender(f5);
            GL11.glScalef(0.375f, 0.375f, 0.375f);
            GL11.glRotatef(130.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.25f, 1.5f, 0.75f);
            GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
            int featherColor = LOTRItemLeatherHat.getFeatherColor(this.hatItem);
            r = (featherColor >> 16 & 0xFF) / 255.0f;
            g = (featherColor >> 8 & 0xFF) / 255.0f;
            b = (featherColor & 0xFF) / 255.0f;
            GL11.glColor3f(r, g, b);
            if(entity instanceof EntityLivingBase) {
                RenderManager.instance.itemRenderer.renderItem((EntityLivingBase) entity, feather, 0);
            }
            else {
                RenderManager.instance.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, feather, 0);
            }
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
    }
}
