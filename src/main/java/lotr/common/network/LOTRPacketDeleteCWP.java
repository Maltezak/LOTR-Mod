package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketDeleteCWP implements IMessage {
    private int wpID;

    public LOTRPacketDeleteCWP() {
    }

    public LOTRPacketDeleteCWP(LOTRCustomWaypoint wp) {
        this.wpID = wp.getID();
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.wpID);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.wpID = data.readInt();
    }

    public static class Handler implements IMessageHandler<LOTRPacketDeleteCWP, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketDeleteCWP packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
            if(cwp != null) {
                pd.removeCustomWaypoint(cwp);
            }
            return null;
        }
    }

}
