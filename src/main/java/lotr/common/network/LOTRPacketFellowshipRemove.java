package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketFellowshipRemove implements IMessage {
    private UUID fellowshipID;
    private boolean isInvite;

    public LOTRPacketFellowshipRemove() {
    }

    public LOTRPacketFellowshipRemove(LOTRFellowship fs, boolean invite) {
        this.fellowshipID = fs.getFellowshipID();
        this.isInvite = invite;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
        data.writeBoolean(this.isInvite);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
        this.isInvite = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRemove, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipRemove packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            if(packet.isInvite) {
                LOTRLevelData.getData(entityplayer).removeClientFellowshipInvite(packet.fellowshipID);
            }
            else {
                LOTRLevelData.getData(entityplayer).removeClientFellowship(packet.fellowshipID);
            }
            return null;
        }
    }

}
