package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipCreate implements IMessage {
    private String fellowshipName;

    public LOTRPacketFellowshipCreate() {
    }

    public LOTRPacketFellowshipCreate(String name) {
        this.fellowshipName = name;
    }

    @Override
    public void toBytes(ByteBuf data) {
        byte[] nameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte nameLength = data.readByte();
        ByteBuf nameBytes = data.readBytes(nameLength);
        this.fellowshipName = nameBytes.toString(Charsets.UTF_8);
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipCreate, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipCreate packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            playerData.createFellowship(packet.fellowshipName, true);
            return null;
        }
    }

}
