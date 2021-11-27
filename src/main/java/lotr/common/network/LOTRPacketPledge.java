package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketPledge implements IMessage {
    private LOTRFaction pledgeFac;

    public LOTRPacketPledge() {
    }

    public LOTRPacketPledge(LOTRFaction f) {
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

    public static class Handler implements IMessageHandler<LOTRPacketPledge, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketPledge packet, MessageContext context) {
            if(!LOTRMod.proxy.isSingleplayer()) {
                EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                pd.setPledgeFaction(packet.pledgeFac);
            }
            return null;
        }
    }

}
