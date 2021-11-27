package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketWaypointRegion implements IMessage {
    private LOTRWaypoint.Region region;
    private boolean unlocking;

    public LOTRPacketWaypointRegion() {
    }

    public LOTRPacketWaypointRegion(LOTRWaypoint.Region r, boolean flag) {
        this.region = r;
        this.unlocking = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.region.ordinal());
        data.writeBoolean(this.unlocking);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.region = LOTRWaypoint.regionForID(data.readByte());
        this.unlocking = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketWaypointRegion, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketWaypointRegion packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRWaypoint.Region region = packet.region;
            if(region != null) {
                if(packet.unlocking) {
                    pd.unlockFTRegion(region);
                }
                else {
                    pd.lockFTRegion(region);
                }
            }
            return null;
        }
    }

}
