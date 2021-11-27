package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerAnvil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketAnvilEngraveOwner
implements IMessage {
    public void toBytes(ByteBuf data) {
    }

    public void fromBytes(ByteBuf data) {
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketAnvilEngraveOwner, IMessage> {
        public IMessage onMessage(LOTRPacketAnvilEngraveOwner packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if (container instanceof LOTRContainerAnvil) {
                LOTRContainerAnvil anvil = (LOTRContainerAnvil)container;
                anvil.engraveOwnership();
            }
            return null;
        }
    }

}

