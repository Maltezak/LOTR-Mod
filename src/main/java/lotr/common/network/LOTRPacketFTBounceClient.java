package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketFTBounceClient implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketFTBounceClient, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFTBounceClient packet, MessageContext context) {
            LOTRMod.proxy.getClientPlayer();
            LOTRPacketFTBounceServer packetResponse = new LOTRPacketFTBounceServer();
            LOTRPacketHandler.networkWrapper.sendToServer(packetResponse);
            return null;
        }
    }

}
