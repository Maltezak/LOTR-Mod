package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerCoinExchange;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketCoinExchange implements IMessage {
    private int button;

    public LOTRPacketCoinExchange() {
    }

    public LOTRPacketCoinExchange(int i) {
        this.button = i;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.button);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.button = data.readByte();
    }

    public static class Handler implements IMessageHandler<LOTRPacketCoinExchange, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketCoinExchange packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerCoinExchange) {
                LOTRContainerCoinExchange coinExchange = (LOTRContainerCoinExchange) container;
                coinExchange.handleExchangePacket(packet.button);
            }
            return null;
        }
    }

}
