package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityMordorBannerBearer extends LOTREntityMordorOrc implements LOTRBannerBearer {
    public LOTREntityMordorBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.MORDOR;
    }
}
