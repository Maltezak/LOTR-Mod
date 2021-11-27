package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerPouch;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketRenamePouch implements IMessage {
    private String name;

    public LOTRPacketRenamePouch() {
    }

    public LOTRPacketRenamePouch(String s) {
        this.name = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        byte[] nameData = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameData.length);
        data.writeBytes(nameData);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        short length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }

    public static class Handler implements IMessageHandler<LOTRPacketRenamePouch, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketRenamePouch packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerPouch) {
                LOTRContainerPouch pouch = (LOTRContainerPouch) container;
                pouch.renamePouch(packet.name);
            }
            return null;
        }
    }

}
