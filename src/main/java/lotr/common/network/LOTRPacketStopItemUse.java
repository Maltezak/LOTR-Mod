package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketStopItemUse implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketStopItemUse, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketStopItemUse packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            entityplayer.clearItemInUse();
            return null;
        }
    }

}
