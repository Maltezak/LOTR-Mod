package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityLossarnachBannerBearer extends LOTREntityLossarnachAxeman implements LOTRBannerBearer {
    public LOTREntityLossarnachBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.LOSSARNACH;
    }
}
