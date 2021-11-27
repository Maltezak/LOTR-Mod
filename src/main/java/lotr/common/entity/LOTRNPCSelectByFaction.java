package lotr.common.entity;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public class LOTRNPCSelectByFaction
implements IEntitySelector {
    protected final LOTRFaction faction;

    public LOTRNPCSelectByFaction(LOTRFaction f) {
        this.faction = f;
    }

    public boolean isEntityApplicable(Entity entity) {
        return entity.isEntityAlive() && this.matchFaction(entity);
    }

    protected boolean matchFaction(Entity entity) {
        return LOTRMod.getNPCFaction(entity) == this.faction;
    }
}

