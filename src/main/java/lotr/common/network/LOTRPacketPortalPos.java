package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;

public class LOTRPacketPortalPos implements IMessage {
    private int portalX;
    private int portalY;
    private int portalZ;

    public LOTRPacketPortalPos() {
    }

    public LOTRPacketPortalPos(int i, int j, int k) {
        this.portalX = i;
        this.portalY = j;
        this.portalZ = k;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.portalX);
        data.writeInt(this.portalY);
        data.writeInt(this.portalZ);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.portalX = data.readInt();
        this.portalY = data.readInt();
        this.portalZ = data.readInt();
    }

    public static class Handler implements IMessageHandler<LOTRPacketPortalPos, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketPortalPos packet, MessageContext context) {
            LOTRLevelData.middleEarthPortalX = packet.portalX;
            LOTRLevelData.middleEarthPortalY = packet.portalY;
            LOTRLevelData.middleEarthPortalZ = packet.portalZ;
            return null;
        }
    }

}
