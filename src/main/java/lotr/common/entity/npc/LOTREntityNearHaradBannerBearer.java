package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityNearHaradBannerBearer extends LOTREntityNearHaradrimWarrior implements LOTRBannerBearer {
    public LOTREntityNearHaradBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.NEAR_HARAD;
    }
}
