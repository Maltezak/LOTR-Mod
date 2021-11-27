package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketSetOption implements IMessage {
    private int option;

    public LOTRPacketSetOption() {
    }

    public LOTRPacketSetOption(int i) {
        this.option = i;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.option);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.option = data.readByte();
    }

    public static class Handler implements IMessageHandler<LOTRPacketSetOption, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketSetOption packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if(packet.option == 0) {
                boolean flag = pd.getFriendlyFire();
                pd.setFriendlyFire(!flag);
            }
            else if(packet.option == 1) {
                boolean flag = pd.getEnableHiredDeathMessages();
                pd.setEnableHiredDeathMessages(!flag);
            }
            else if(packet.option == 2) {
                boolean flag = pd.getHideAlignment();
                pd.setHideAlignment(!flag);
            }
            else if(packet.option == 3) {
                boolean flag = pd.getHideMapLocation();
                pd.setHideMapLocation(!flag);
            }
            else if(packet.option == 4) {
                boolean flag = pd.getFemRankOverride();
                pd.setFemRankOverride(!flag);
            }
            else if(packet.option == 5) {
                boolean flag = pd.getEnableConquestKills();
                pd.setEnableConquestKills(!flag);
            }
            return null;
        }
    }

}
