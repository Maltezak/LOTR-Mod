package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityRangerNorthBannerBearer extends LOTREntityRangerNorth implements LOTRBannerBearer {
    public LOTREntityRangerNorthBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.RANGER_NORTH;
    }
}
