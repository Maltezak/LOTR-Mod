package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketIsOpResponse implements IMessage {
    private boolean isOp;

    public LOTRPacketIsOpResponse() {
    }

    public LOTRPacketIsOpResponse(boolean flag) {
        this.isOp = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeBoolean(this.isOp);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.isOp = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketIsOpResponse, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketIsOpResponse packet, MessageContext context) {
            LOTRMod.proxy.setMapIsOp(packet.isOp);
            return null;
        }
    }

}
