package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketRenameCWP implements IMessage {
    private int wpID;
    private String name;

    public LOTRPacketRenameCWP() {
    }

    public LOTRPacketRenameCWP(LOTRCustomWaypoint wp, String s) {
        this.wpID = wp.getID();
        this.name = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.wpID);
        byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.wpID = data.readInt();
        short length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }

    public static class Handler implements IMessageHandler<LOTRPacketRenameCWP, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketRenameCWP packet, MessageContext context) {
            String wpName;
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
            if(cwp != null && (wpName = LOTRCustomWaypoint.validateCustomName(packet.name)) != null) {
                pd.renameCustomWaypoint(cwp, wpName);
            }
            return null;
        }
    }

}
