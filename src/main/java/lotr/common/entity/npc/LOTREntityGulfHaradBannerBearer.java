package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityGulfHaradBannerBearer extends LOTREntityGulfHaradWarrior implements LOTRBannerBearer {
    public LOTREntityGulfHaradBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.HARAD_GULF;
    }
}
