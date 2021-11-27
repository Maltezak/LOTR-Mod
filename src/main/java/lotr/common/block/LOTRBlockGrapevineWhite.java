package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockGrapevineWhite extends LOTRBlockGrapevine {
    public LOTRBlockGrapevineWhite() {
        super(true);
    }

    @Override
    public Item getGrapeItem() {
        return LOTRMod.grapeWhite;
    }

    @Override
    public Item getGrapeSeedsItem() {
        return LOTRMod.seedsGrapeWhite;
    }
}
