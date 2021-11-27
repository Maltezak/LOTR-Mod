package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityGondorBannerBearer extends LOTREntityGondorSoldier implements LOTRBannerBearer {
    public LOTREntityGondorBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.GONDOR;
    }
}
