package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMordorWargBombardier extends LOTREntityWargBombardier {
    public LOTREntityMordorWargBombardier(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.setWargType(LOTREntityWarg.WargType.BLACK);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
