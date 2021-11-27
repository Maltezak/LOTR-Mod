package lotr.common.entity.npc;

import lotr.common.world.spawning.LOTRInvasions;

public interface LOTRUnitTradeable
extends LOTRHireableBase {
    LOTRUnitTradeEntries getUnits();

    LOTRInvasions getWarhorn();
}

