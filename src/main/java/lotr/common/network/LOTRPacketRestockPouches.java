package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.entity.player.*;

public class LOTRPacketRestockPouches
implements IMessage {
    public void toBytes(ByteBuf data) {
    }

    public void fromBytes(ByteBuf data) {
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketRestockPouches, IMessage> {
        public IMessage onMessage(LOTRPacketRestockPouches packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            if (LOTRItemPouch.restockPouches(entityplayer)) {
                entityplayer.openContainer.detectAndSendChanges();
                entityplayer.worldObj.playSoundAtEntity(entityplayer, "mob.horse.leather", 0.5f, 1.0f);
            }
            return null;
        }
    }

}

