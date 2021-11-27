package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityBlackrootBannerBearer extends LOTREntityBlackrootSoldier implements LOTRBannerBearer {
    public LOTREntityBlackrootBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.BLACKROOT_VALE;
    }
}
