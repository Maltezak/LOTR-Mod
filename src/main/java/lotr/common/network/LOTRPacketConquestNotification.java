package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;

public class LOTRPacketConquestNotification implements IMessage {
    private LOTRFaction conqFac;
    private float conqVal;
    private boolean isCleansing;

    public LOTRPacketConquestNotification() {
    }

    public LOTRPacketConquestNotification(LOTRFaction fac, float f, boolean clean) {
        this.conqFac = fac;
        this.conqVal = f;
        this.isCleansing = clean;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.conqFac.ordinal());
        data.writeFloat(this.conqVal);
        data.writeBoolean(this.isCleansing);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte facID = data.readByte();
        this.conqFac = LOTRFaction.forID(facID);
        this.conqVal = data.readFloat();
        this.isCleansing = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketConquestNotification, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketConquestNotification packet, MessageContext context) {
            if(packet.conqFac != null) {
                LOTRMod.proxy.queueConquestNotification(packet.conqFac, packet.conqVal, packet.isCleansing);
            }
            return null;
        }
    }

}
