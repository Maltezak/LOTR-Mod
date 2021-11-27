package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityDorwinionBannerBearer extends LOTREntityDorwinionGuard implements LOTRBannerBearer {
    public LOTREntityDorwinionBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.DORWINION;
    }
}
