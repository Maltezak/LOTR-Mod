package lotr.common.entity.npc;

import lotr.common.LOTRShields;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityEsgarothBannerBearer extends LOTREntityDaleSoldier implements LOTRBannerBearer {
    public LOTREntityEsgarothBannerBearer(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_ESGAROTH;
    }

    @Override
    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.ESGAROTH;
    }
}
