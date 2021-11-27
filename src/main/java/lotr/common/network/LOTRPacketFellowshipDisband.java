package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipDisband extends LOTRPacketFellowshipDo {
    public LOTRPacketFellowshipDisband() {
    }

    public LOTRPacketFellowshipDisband(LOTRFellowshipClient fs) {
        super(fs);
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipDisband, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipDisband packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            if(fellowship != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                playerData.disbandFellowship(fellowship);
            }
            return null;
        }
    }

}
