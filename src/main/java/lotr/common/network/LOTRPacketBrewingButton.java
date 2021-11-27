package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.inventory.LOTRContainerBarrel;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketBrewingButton implements IMessage {
    @Override
    public void toBytes(ByteBuf data) {
    }

    @Override
    public void fromBytes(ByteBuf data) {
    }

    public static class Handler implements IMessageHandler<LOTRPacketBrewingButton, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBrewingButton packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerBarrel) {
                ((LOTRContainerBarrel) container).theBarrel.handleBrewingButtonPress();
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.brewDrinkInBarrel);
            }
            return null;
        }
    }

}
