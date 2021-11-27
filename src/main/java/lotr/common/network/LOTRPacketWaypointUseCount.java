package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketWaypointUseCount
implements IMessage {
    private boolean isCustom;
    private int wpID;
    private int useCount;
    private UUID sharingPlayer;

    public LOTRPacketWaypointUseCount() {
    }

    public LOTRPacketWaypointUseCount(LOTRAbstractWaypoint wp, int count) {
        this.isCustom = wp instanceof LOTRCustomWaypoint;
        this.wpID = wp.getID();
        this.useCount = count;
        if (wp instanceof LOTRCustomWaypoint) {
            this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
        }
    }

    public void toBytes(ByteBuf data) {
        data.writeBoolean(this.isCustom);
        data.writeInt(this.wpID);
        data.writeInt(this.useCount);
        boolean shared = this.sharingPlayer != null;
        data.writeBoolean(shared);
        if (shared) {
            data.writeLong(this.sharingPlayer.getMostSignificantBits());
            data.writeLong(this.sharingPlayer.getLeastSignificantBits());
        }
    }

    public void fromBytes(ByteBuf data) {
        this.isCustom = data.readBoolean();
        this.wpID = data.readInt();
        this.useCount = data.readInt();
        boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketWaypointUseCount, IMessage> {
        public IMessage onMessage(LOTRPacketWaypointUseCount packet, MessageContext context) {
            boolean custom = packet.isCustom;
            int wpID = packet.wpID;
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRAbstractWaypoint waypoint = null;
            if (!custom) {
                if (wpID >= 0 && wpID < LOTRWaypoint.values().length) {
                    waypoint = LOTRWaypoint.values()[wpID];
                }
            } else {
                UUID sharingPlayerID = packet.sharingPlayer;
                waypoint = sharingPlayerID != null ? pd.getSharedCustomWaypointByID(sharingPlayerID, wpID) : pd.getCustomWaypointByID(wpID);
            }
            if (waypoint != null) {
                pd.setWPUseCount(waypoint, packet.useCount);
            }
            return null;
        }
    }

}

