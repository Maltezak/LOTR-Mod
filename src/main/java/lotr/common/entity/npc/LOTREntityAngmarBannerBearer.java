package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityAngmarBannerBearer extends LOTREntityAngmarOrc implements LOTRBannerBearer {
    public LOTREntityAngmarBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.ANGMAR;
    }
}
