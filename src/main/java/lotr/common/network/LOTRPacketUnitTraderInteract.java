package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.world.World;

public class LOTRPacketUnitTraderInteract implements IMessage {
    private int traderID;
    private int traderAction;

    public LOTRPacketUnitTraderInteract() {
    }

    public LOTRPacketUnitTraderInteract(int idt, int a) {
        this.traderID = idt;
        this.traderAction = a;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.traderID);
        data.writeByte(this.traderAction);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.traderID = data.readInt();
        this.traderAction = data.readByte();
    }

    protected void openTradeGUI(EntityPlayer entityplayer, LOTREntityNPC trader) {
        entityplayer.openGui(LOTRMod.instance, 7, entityplayer.worldObj, trader.getEntityId(), 0, 0);
    }

    public static class Handler implements IMessageHandler<LOTRPacketUnitTraderInteract, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketUnitTraderInteract packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity trader = world.getEntityByID(packet.traderID);
            if(trader instanceof LOTRHireableBase) {
                LOTRHireableBase tradeableTrader = (LOTRHireableBase) (trader);
                LOTREntityNPC livingTrader = (LOTREntityNPC) trader;
                int action = packet.traderAction;
                boolean closeScreen = false;
                if(action == 0) {
                    livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
                    closeScreen = livingTrader.interactFirst(entityplayer);
                }
                else if(action == 1 && tradeableTrader.canTradeWith(entityplayer)) {
                    packet.openTradeGUI(entityplayer, livingTrader);
                }
                if(closeScreen) {
                    entityplayer.closeScreen();
                }
            }
            return null;
        }
    }

}
