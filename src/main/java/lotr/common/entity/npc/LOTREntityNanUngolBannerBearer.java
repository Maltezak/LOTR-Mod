package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityNanUngolBannerBearer extends LOTREntityMordorOrc implements LOTRBannerBearer {
    public LOTREntityNanUngolBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.NAN_UNGOL;
    }
}
