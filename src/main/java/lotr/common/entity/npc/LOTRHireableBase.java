package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public interface LOTRHireableBase {
    String getNPCName();

    LOTRFaction getFaction();

    boolean canTradeWith(EntityPlayer var1);

    void onUnitTrade(EntityPlayer var1);
}
