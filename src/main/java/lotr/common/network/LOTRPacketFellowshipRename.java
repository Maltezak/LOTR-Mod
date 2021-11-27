package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipRename extends LOTRPacketFellowshipDo {
    private String newName;

    public LOTRPacketFellowshipRename() {
    }

    public LOTRPacketFellowshipRename(LOTRFellowshipClient fs, String name) {
        super(fs);
        this.newName = name;
    }

    @Override
    public void toBytes(ByteBuf data) {
        super.toBytes(data);
        byte[] nameBytes = this.newName.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        super.fromBytes(data);
        byte nameLength = data.readByte();
        ByteBuf nameBytes = data.readBytes(nameLength);
        this.newName = nameBytes.toString(Charsets.UTF_8);
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipRename, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipRename packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            if(fellowship != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                playerData.renameFellowship(fellowship, packet.newName);
            }
            return null;
        }
    }

}
