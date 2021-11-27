package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketClientMQEvent implements IMessage {
    private ClientMQEvent type;

    public LOTRPacketClientMQEvent() {
    }

    public LOTRPacketClientMQEvent(ClientMQEvent t) {
        this.type = t;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.type.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte typeID = data.readByte();
        if(typeID >= 0 && typeID < ClientMQEvent.values().length) {
            this.type = ClientMQEvent.values()[typeID];
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketClientMQEvent, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketClientMQEvent packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if(packet.type == ClientMQEvent.MAP) {
                pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewMap());
            }
            else if(packet.type == ClientMQEvent.FACTIONS) {
                pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewFactions());
            }
            return null;
        }
    }

    public enum ClientMQEvent {
        MAP, FACTIONS;

    }

}
