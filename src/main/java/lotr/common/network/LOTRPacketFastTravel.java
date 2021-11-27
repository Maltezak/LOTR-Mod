package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class LOTRPacketFastTravel
implements IMessage {
    private boolean isCustom;
    private int wpID;
    private UUID sharingPlayer;

    public LOTRPacketFastTravel() {
    }

    public LOTRPacketFastTravel(LOTRAbstractWaypoint wp) {
        this.isCustom = wp instanceof LOTRCustomWaypoint;
        this.wpID = wp.getID();
        if (wp instanceof LOTRCustomWaypoint) {
            this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
        }
    }

    public void toBytes(ByteBuf data) {
        data.writeBoolean(this.isCustom);
        data.writeInt(this.wpID);
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
        boolean shared = data.readBoolean();
        if (shared) {
            this.sharingPlayer = new UUID(data.readLong(), data.readLong());
        }
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketFastTravel, IMessage> {
        public IMessage onMessage(LOTRPacketFastTravel packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            if (!LOTRConfig.enableFastTravel) {
                entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.ftDisabled"));
            } else {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                boolean isCustom = packet.isCustom;
                int waypointID = packet.wpID;
                LOTRAbstractWaypoint waypoint = null;
                if (!isCustom) {
                    if (waypointID >= 0 && waypointID < LOTRWaypoint.values().length) {
                        waypoint = LOTRWaypoint.values()[waypointID];
                    }
                } else {
                    UUID sharingPlayer = packet.sharingPlayer;
                    waypoint = sharingPlayer != null ? playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID) : playerData.getCustomWaypointByID(waypointID);
                }
                if (waypoint != null && waypoint.hasPlayerUnlocked(entityplayer)) {
                    if (playerData.getTimeSinceFT() < playerData.getWaypointFTTime(waypoint, entityplayer)) {
                        entityplayer.closeScreen();
                        entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.moreTime", waypoint.getDisplayName()));
                    } else {
                        boolean canTravel = playerData.canFastTravel();
                        if (!canTravel) {
                            entityplayer.closeScreen();
                            entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.underAttack"));
                        } else {
                            playerData.setTargetFTWaypoint(waypoint);
                        }
                    }
                }
            }
            return null;
        }
    }

}

