package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipRespondInvite extends LOTRPacketFellowshipDo {
    private boolean acceptOrReject;

    public LOTRPacketFellowshipRespondInvite() {
    }

    public LOTRPacketFellowshipRespondInvite(LOTRFellowshipClient fs, boolean accept) {
        super(fs);
        this.acceptOrReject = accept;
    }

    @Override
    public void toBytes(ByteBuf data) {
        super.toBytes(data);
        data.writeBoolean(this.acceptOrReject);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        super.fromBytes(data);
        this.acceptOrReject = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRespondInvite, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipRespondInvite packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            if(fellowship != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                if(packet.acceptOrReject) {
                    playerData.acceptFellowshipInvite(fellowship);
                }
                else {
                    playerData.rejectFellowshipInvite(fellowship);
                }
            }
            return null;
        }
    }

}
