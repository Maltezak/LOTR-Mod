package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerAnvil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketAnvilReforge implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketAnvilReforge, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketAnvilReforge packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerAnvil) {
                LOTRContainerAnvil anvil = (LOTRContainerAnvil) container;
                anvil.reforgeItem();
            }
            return null;
        }
    }

}
