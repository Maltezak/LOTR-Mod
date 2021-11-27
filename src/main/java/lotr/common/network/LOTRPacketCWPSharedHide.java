package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketCWPSharedHide implements IMessage {
    private int cwpID;
    private UUID sharingPlayer;
    private boolean hideCWP;

    public LOTRPacketCWPSharedHide() {
    }

    public LOTRPacketCWPSharedHide(LOTRCustomWaypoint cwp, boolean hide) {
        this.cwpID = cwp.getID();
        this.sharingPlayer = cwp.getSharingPlayerID();
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

    public static class Handler implements IMessageHandler<LOTRPacketCWPSharedHide, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketCWPSharedHide packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
            if(cwp != null) {
                pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
            }
            return null;
        }
    }

}
