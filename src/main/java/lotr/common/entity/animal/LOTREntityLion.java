package lotr.common.entity.animal;

import lotr.common.item.LOTRItemLionRug;
import net.minecraft.world.World;

public class LOTREntityLion extends LOTREntityLionBase {
    public LOTREntityLion(World world) {
        super(world);
    }

    @Override
    public boolean isMale() {
        return true;
    }

    @Override
    protected LOTRItemLionRug.LionRugType getLionRugType() {
        return LOTRItemLionRug.LionRugType.LION;
    }
}
