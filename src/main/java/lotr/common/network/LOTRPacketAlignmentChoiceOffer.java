package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketAlignmentChoiceOffer implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketAlignmentChoiceOffer, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketAlignmentChoiceOffer packet, MessageContext context) {
            LOTRMod.proxy.displayAlignmentChoice();
            return null;
        }
    }

}
