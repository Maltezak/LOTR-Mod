package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfBannerBearer extends LOTREntityDorwinionElfWarrior implements LOTRBannerBearer {
    public LOTREntityDorwinionElfBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.DORWINION;
    }
}
