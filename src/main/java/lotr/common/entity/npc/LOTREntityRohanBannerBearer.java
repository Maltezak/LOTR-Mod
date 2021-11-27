package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityRohanBannerBearer extends LOTREntityRohirrimWarrior implements LOTRBannerBearer {
    public LOTREntityRohanBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.ROHAN;
    }
}
