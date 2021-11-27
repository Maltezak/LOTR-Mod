package lotr.common.network;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerAnvil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketAnvilRename
implements IMessage {
    private String rename;

    public LOTRPacketAnvilRename() {
    }

    public LOTRPacketAnvilRename(String s) {
        this.rename = s;
    }

    public void toBytes(ByteBuf data) {
        byte[] nameBytes = this.rename.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    public void fromBytes(ByteBuf data) {
        short nameLength = data.readShort();
        ByteBuf nameBytes = data.readBytes(nameLength);
        this.rename = nameBytes.toString(Charsets.UTF_8);
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketAnvilRename, IMessage> {
        public IMessage onMessage(LOTRPacketAnvilRename packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if (container instanceof LOTRContainerAnvil) {
                LOTRContainerAnvil anvil = (LOTRContainerAnvil)container;
                String rename = packet.rename;
                if (rename != null && !StringUtils.isBlank(rename)) {
                    if (rename.length() <= 30) {
                        anvil.updateItemName(rename);
                    }
                } else {
                    anvil.updateItemName("");
                }
            }
            return null;
        }
    }

}

