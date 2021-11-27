package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketAlignDrain implements IMessage {
    private int numFactions;

    public LOTRPacketAlignDrain() {
    }

    public LOTRPacketAlignDrain(int num) {
        this.numFactions = num;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.numFactions);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.numFactions = data.readByte();
    }

    public static class Handler implements IMessageHandler<LOTRPacketAlignDrain, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketAlignDrain packet, MessageContext context) {
            LOTRMod.proxy.displayAlignDrain(packet.numFactions);
            return null;
        }
    }

}
