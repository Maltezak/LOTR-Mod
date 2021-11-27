package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class LOTRPacketFellowshipSetIcon extends LOTRPacketFellowshipDo {
    public LOTRPacketFellowshipSetIcon() {
    }

    public LOTRPacketFellowshipSetIcon(LOTRFellowshipClient fs) {
        super(fs);
    }

    @Override
    public void toBytes(ByteBuf data) {
        super.toBytes(data);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        super.fromBytes(data);
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipSetIcon, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipSetIcon packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            if(fellowship != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                ItemStack itemstack = entityplayer.getHeldItem();
                if(itemstack != null) {
                    ItemStack newStack = itemstack.copy();
                    newStack.stackSize = 1;
                    playerData.setFellowshipIcon(fellowship, newStack);
                }
                else {
                    playerData.setFellowshipIcon(fellowship, null);
                }
            }
            return null;
        }
    }

}
