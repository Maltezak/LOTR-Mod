package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityRivendellBannerBearer extends LOTREntityRivendellWarrior implements LOTRBannerBearer {
    public LOTREntityRivendellBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.RIVENDELL;
    }
}
