package lotr.client.gui;

import lotr.common.entity.npc.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRGuiMercenaryHire extends LOTRGuiHireBase {
    public LOTRGuiMercenaryHire(EntityPlayer entityplayer, LOTRMercenary mercenary, World world) {
        super(entityplayer, mercenary, world);
        LOTRMercenaryTradeEntry e = LOTRMercenaryTradeEntry.createFor(mercenary);
        LOTRUnitTradeEntries trades = new LOTRUnitTradeEntries(0.0f, e);
        this.setTrades(trades);
    }
}
