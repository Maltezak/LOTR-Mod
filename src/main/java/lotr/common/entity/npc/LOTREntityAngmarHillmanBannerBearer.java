package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanBannerBearer extends LOTREntityAngmarHillmanWarrior implements LOTRBannerBearer {
    public LOTREntityAngmarHillmanBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.RHUDAUR;
    }
}
