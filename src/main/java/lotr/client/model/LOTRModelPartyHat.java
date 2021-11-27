package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.item.LOTRItemPartyHat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class LOTRModelPartyHat extends LOTRModelBiped {
    private ItemStack hatItem;

    public LOTRModelPartyHat() {
        this(0.0f);
    }

    public LOTRModelPartyHat(float f) {
        super(f);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedHead.addBox(-4.0f, -14.0f, -4.0f, 8, 8, 8, f);
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
        int hatColor = LOTRItemPartyHat.getHatColor(this.hatItem);
        float r = (hatColor >> 16 & 0xFF) / 255.0f;
        float g = (hatColor >> 8 & 0xFF) / 255.0f;
        float b = (hatColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        this.bipedHead.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
