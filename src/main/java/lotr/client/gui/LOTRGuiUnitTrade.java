package lotr.client.gui;

import lotr.common.entity.npc.LOTRUnitTradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRGuiUnitTrade extends LOTRGuiHireBase {
    public LOTRGuiUnitTrade(EntityPlayer entityplayer, LOTRUnitTradeable trader, World world) {
        super(entityplayer, trader, world);
        this.setTrades(trader.getUnits());
    }
}
