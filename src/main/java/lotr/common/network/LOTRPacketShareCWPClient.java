package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.map.LOTRCustomWaypoint;

public class LOTRPacketShareCWPClient
implements IMessage {
    private int cwpID;
    private UUID fellowshipID;
    private boolean adding;

    public LOTRPacketShareCWPClient() {
    }

    public LOTRPacketShareCWPClient(int id, UUID fsID, boolean add) {
        this.cwpID = id;
        this.fellowshipID = fsID;
        this.adding = add;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.cwpID);
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
        data.writeBoolean(this.adding);
    }

    public void fromBytes(ByteBuf data) {
        this.cwpID = data.readInt();
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
        this.adding = data.readBoolean();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketShareCWPClient, IMessage> {
        public IMessage onMessage(LOTRPacketShareCWPClient packet, MessageContext context) {
            LOTRCustomWaypoint cwp;
            LOTRPlayerData pd;
            if (!LOTRMod.proxy.isSingleplayer() && (cwp = (pd = LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer())).getCustomWaypointByID(packet.cwpID)) != null) {
                if (packet.adding) {
                    LOTRFellowshipClient fsClient = pd.getClientFellowshipByID(packet.fellowshipID);
                    if (fsClient != null) {
                        pd.customWaypointAddSharedFellowshipClient(cwp, fsClient);
                    }
                } else {
                    pd.customWaypointRemoveSharedFellowshipClient(cwp, packet.fellowshipID);
                }
            }
            return null;
        }
    }

}

