package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketPledgeSet implements IMessage {
    private LOTRFaction pledgeFac;

    public LOTRPacketPledgeSet() {
    }

    public LOTRPacketPledgeSet(LOTRFaction f) {
        this.pledgeFac = f;
    }

    @Override
    public void toBytes(ByteBuf data) {
        int facID = this.pledgeFac == null ? -1 : this.pledgeFac.ordinal();
        data.writeByte(facID);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte facID = data.readByte();
        this.pledgeFac = facID == -1 ? null : LOTRFaction.forID(facID);
    }

    public static class Handler implements IMessageHandler<LOTRPacketPledgeSet, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketPledgeSet packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRFaction fac = packet.pledgeFac;
            if(fac == null) {
                pd.revokePledgeFaction(entityplayer, true);
            }
            else if(pd.canPledgeTo(fac) && pd.canMakeNewPledge()) {
                pd.setPledgeFaction(fac);
            }
            return null;
        }
    }

}
