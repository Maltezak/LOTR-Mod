package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketMercenaryInteract extends LOTRPacketUnitTraderInteract {
    public LOTRPacketMercenaryInteract() {
    }

    public LOTRPacketMercenaryInteract(int idt, int a) {
        super(idt, a);
    }

    @Override
    protected void openTradeGUI(EntityPlayer entityplayer, LOTREntityNPC trader) {
        entityplayer.openGui(LOTRMod.instance, 59, entityplayer.worldObj, trader.getEntityId(), 0, 0);
    }

    public static class Handler implements IMessageHandler<LOTRPacketMercenaryInteract, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketMercenaryInteract message, MessageContext ctx) {
            return new LOTRPacketUnitTraderInteract.Handler().onMessage(message, ctx);
        }
    }

}
