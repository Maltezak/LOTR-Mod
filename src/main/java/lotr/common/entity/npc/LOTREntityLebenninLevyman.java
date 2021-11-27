package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLebenninLevyman extends LOTREntityGondorLevyman {
    public LOTREntityLebenninLevyman(World world) {
        super(world);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLebenninGambeson));
        return data;
    }
}
