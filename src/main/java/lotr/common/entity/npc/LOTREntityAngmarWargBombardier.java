package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityAngmarWargBombardier extends LOTREntityWargBombardier {
    public LOTREntityAngmarWargBombardier(World world) {
        super(world);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
