package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketEnvironmentOverlay implements IMessage {
    private Overlay overlay;

    public LOTRPacketEnvironmentOverlay() {
    }

    public LOTRPacketEnvironmentOverlay(Overlay o) {
        this.overlay = o;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.overlay.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte overlayID = data.readByte();
        this.overlay = Overlay.values()[overlayID];
    }

    public static class Handler implements IMessageHandler<LOTRPacketEnvironmentOverlay, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketEnvironmentOverlay packet, MessageContext context) {
            if(packet.overlay == Overlay.FROST) {
                LOTRMod.proxy.showFrostOverlay();
            }
            else if(packet.overlay == Overlay.BURN) {
                LOTRMod.proxy.showBurnOverlay();
            }
            return null;
        }
    }

    public enum Overlay {
        FROST, BURN;

    }

}
