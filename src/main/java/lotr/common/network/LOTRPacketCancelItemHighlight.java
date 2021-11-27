package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketCancelItemHighlight implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketCancelItemHighlight, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketCancelItemHighlight packet, MessageContext context) {
            LOTRMod.proxy.cancelItemHighlight();
            return null;
        }
    }

}
