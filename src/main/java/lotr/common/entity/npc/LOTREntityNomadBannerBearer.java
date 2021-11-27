package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityNomadBannerBearer extends LOTREntityNomadWarrior implements LOTRBannerBearer {
    public LOTREntityNomadBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.HARAD_NOMAD;
    }
}
