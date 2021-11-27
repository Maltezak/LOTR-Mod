package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityEasterlingBannerBearer extends LOTREntityEasterlingWarrior implements LOTRBannerBearer {
    public LOTREntityEasterlingBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.RHUN;
    }
}
