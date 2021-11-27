package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityBlackUrukBannerBearer extends LOTREntityBlackUruk implements LOTRBannerBearer {
    public LOTREntityBlackUrukBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.BLACK_URUK;
    }
}
