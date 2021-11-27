package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketSealCracker implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketSealCracker, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketSealCracker packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerDaleCracker) {
                LOTRContainerDaleCracker cracker = (LOTRContainerDaleCracker) container;
                cracker.receiveSealingPacket(entityplayer);
            }
            return null;
        }
    }

}
