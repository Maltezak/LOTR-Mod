package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;

public interface LOTRTravellingTrader extends LOTRTradeable {
    void startTraderVisiting(EntityPlayer var1);

    LOTREntityNPC createTravellingEscort();

    String getDepartureSpeech();
}
