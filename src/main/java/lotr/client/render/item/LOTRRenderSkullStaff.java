package lotr.client.render.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderSkullStaff implements IItemRenderer {
    private static ModelBase staffModel = new ModelBase() {
        private ModelRenderer staff = new ModelRenderer(this, 0, 0);
        {
            this.staff.addBox(-0.5f, 8.0f, -6.0f, 1, 1, 28, 0.0f);
            this.staff.addBox(-2.5f, 6.0f, -11.0f, 5, 5, 5, 0.0f);
            this.staff.rotateAngleY = (float) Math.toRadians(90.0);
            this.staff.rotateAngleZ = (float) Math.toRadians(-20.0);
        }

        @Override
        public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
            this.staff.render(f5);
        }
    };
    private static ResourceLocation staffTexture = new ResourceLocation("lotr:item/skullStaff.png");

    @Override
    public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(staffTexture);
        if(type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotatef(-70.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
        }
        staffModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }

}
