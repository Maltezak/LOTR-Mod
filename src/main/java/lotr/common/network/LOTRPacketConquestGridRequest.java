package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketConquestGridRequest implements IMessage {
    private LOTRFaction conqFac;

    public LOTRPacketConquestGridRequest() {
    }

    public LOTRPacketConquestGridRequest(LOTRFaction fac) {
        this.conqFac = fac;
    }

    @Override
    public void toBytes(ByteBuf data) {
        int facID = this.conqFac.ordinal();
        data.writeByte(facID);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte facID = data.readByte();
        this.conqFac = LOTRFaction.forID(facID);
    }

    public static class Handler implements IMessageHandler<LOTRPacketConquestGridRequest, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketConquestGridRequest packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFaction fac = packet.conqFac;
            if(fac != null) {
                LOTRConquestGrid.ConquestViewableQuery query = LOTRConquestGrid.canPlayerViewConquest(entityplayer, fac);
                if(query.result == LOTRConquestGrid.ConquestViewable.CAN_VIEW) {
                    LOTRConquestGrid.sendConquestGridTo(entityplayer, fac);
                }
            }
            return null;
        }
    }

}
