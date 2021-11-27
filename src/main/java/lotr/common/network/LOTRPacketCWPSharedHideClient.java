package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;

public class LOTRPacketCWPSharedHideClient implements IMessage {
    private int cwpID;
    private UUID sharingPlayer;
    private boolean hideCWP;

    public LOTRPacketCWPSharedHideClient() {
    }

    public LOTRPacketCWPSharedHideClient(int id, UUID player, boolean hide) {
        this.cwpID = id;
        this.sharingPlayer = player;
        this.hideCWP = hide;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.cwpID);
        data.writeLong(this.sharingPlayer.getMostSignificantBits());
        data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        data.writeBoolean(this.hideCWP);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.cwpID = data.readInt();
        this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        this.hideCWP = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketCWPSharedHideClient, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketCWPSharedHideClient packet, MessageContext context) {
            LOTRCustomWaypoint cwp;
            LOTRPlayerData pd;
            if(!LOTRMod.proxy.isSingleplayer() && (cwp = (pd = LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer())).getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
                pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
            }
            return null;
        }
    }

}
