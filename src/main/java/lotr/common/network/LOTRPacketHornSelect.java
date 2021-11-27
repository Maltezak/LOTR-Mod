package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class LOTRPacketHornSelect implements IMessage {
    private int hornType;

    public LOTRPacketHornSelect() {
    }

    public LOTRPacketHornSelect(int h) {
        this.hornType = h;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.hornType);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.hornType = data.readByte();
    }

    public static class Handler implements IMessageHandler<LOTRPacketHornSelect, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketHornSelect packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if(itemstack != null && itemstack.getItem() == LOTRMod.commandHorn && itemstack.getItemDamage() == 0) {
                itemstack.setItemDamage(packet.hornType);
            }
            return null;
        }
    }

}
