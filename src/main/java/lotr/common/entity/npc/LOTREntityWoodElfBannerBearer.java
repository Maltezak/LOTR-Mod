package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityWoodElfBannerBearer extends LOTREntityWoodElfWarrior implements LOTRBannerBearer {
    public LOTREntityWoodElfBannerBearer(World world) {
        super(world);
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.WOOD_ELF;
    }
}
