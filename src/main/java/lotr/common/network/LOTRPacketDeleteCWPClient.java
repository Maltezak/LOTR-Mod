package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketDeleteCWPClient implements IMessage {
    private int cwpID;
    private UUID sharingPlayer;

    public LOTRPacketDeleteCWPClient() {
    }

    public LOTRPacketDeleteCWPClient(int id) {
        this.cwpID = id;
    }

    public LOTRPacketDeleteCWPClient setSharingPlayer(UUID player) {
        this.sharingPlayer = player;
        return this;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.cwpID);
        boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if(shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.cwpID = data.readInt();
        boolean shared = data.readBoolean();
        if(shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketDeleteCWPClient, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketDeleteCWPClient packet, MessageContext context) {
            LOTRCustomWaypoint cwp;
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if(packet.sharingPlayer != null) {
                LOTRCustomWaypoint cwp2;
                if(!LOTRMod.proxy.isSingleplayer() && (cwp2 = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
                    pd.removeSharedCustomWaypoint(cwp2);
                }
            }
            else if(!LOTRMod.proxy.isSingleplayer() && (cwp = pd.getCustomWaypointByID(packet.cwpID)) != null) {
                pd.removeCustomWaypoint(cwp);
            }
            return null;
        }
    }

}
