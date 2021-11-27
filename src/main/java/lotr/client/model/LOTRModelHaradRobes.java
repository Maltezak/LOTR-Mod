package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class LOTRModelHaradRobes extends LOTRModelHuman {
    protected ItemStack robeItem;

    public LOTRModelHaradRobes() {
        this(0.0f);
    }

    public LOTRModelHaradRobes(float f) {
        super(f, true);
    }

    public void setRobeItem(ItemStack itemstack) {
        this.robeItem = itemstack;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        int robeColor = LOTRItemHaradRobes.getRobesColor(this.robeItem);
        float r = (robeColor >> 16 & 0xFF) / 255.0f;
        float g = (robeColor >> 8 & 0xFF) / 255.0f;
        float b = (robeColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        this.bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
        this.bipedHead.render(f5);
        this.bipedHeadwear.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
}
