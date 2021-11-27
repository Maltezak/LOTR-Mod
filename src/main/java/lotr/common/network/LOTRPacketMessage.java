package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;

public class LOTRPacketMessage implements IMessage {
    private LOTRGuiMessageTypes message;

    public LOTRPacketMessage() {
    }

    public LOTRPacketMessage(LOTRGuiMessageTypes m) {
        this.message = m;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.message.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte messageID = data.readByte();
        if(messageID < 0 || messageID >= LOTRGuiMessageTypes.values().length) {
            FMLLog.severe("Failed to display LOTR message: There is no message with ID " + messageID);
            this.message = null;
        }
        else {
            this.message = LOTRGuiMessageTypes.values()[messageID];
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketMessage, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketMessage packet, MessageContext context) {
            if(packet.message != null) {
                LOTRMod.proxy.displayMessage(packet.message);
            }
            return null;
        }
    }

}
