package lotr.common.world.structure2;

import lotr.common.entity.npc.*;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarFarm extends LOTRWorldGenSouthronFarm {
    public LOTRWorldGenUmbarFarm(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }

    @Override
    protected LOTREntityNearHaradrimBase createFarmer(World world) {
        return new LOTREntityUmbarFarmer(world);
    }
}
