package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;

public interface LOTRNPCMount {
    boolean isMountSaddled();

    boolean getBelongsToNPC();

    void setBelongsToNPC(boolean var1);

    void super_moveEntityWithHeading(float var1, float var2);

    float getStepHeightWhileRiddenByPlayer();

    String getMountArmorTexture();

    boolean isMountArmorValid(ItemStack var1);
}

