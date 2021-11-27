package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityHarnedorBannerBearer extends LOTREntityHarnedorWarrior implements LOTRBannerBearer {
    public LOTREntityHarnedorBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.NEAR_HARAD;
    }
}
