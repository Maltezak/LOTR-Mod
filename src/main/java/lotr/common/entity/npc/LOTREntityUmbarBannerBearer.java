package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityUmbarBannerBearer extends LOTREntityUmbarWarrior implements LOTRBannerBearer {
    public LOTREntityUmbarBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.UMBAR;
    }
}
