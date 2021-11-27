package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class LOTRPacketTitle implements IMessage {
    private LOTRTitle.PlayerTitle playerTitle;

    public LOTRPacketTitle() {
    }

    public LOTRPacketTitle(LOTRTitle.PlayerTitle t) {
        this.playerTitle = t;
    }

    @Override
    public void toBytes(ByteBuf data) {
        if(this.playerTitle == null) {
            data.writeShort(-1);
            data.writeByte(-1);
        }
        else {
            data.writeShort(this.playerTitle.getTitle().titleID);
            data.writeByte(this.playerTitle.getColor().getFormattingCode());
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        short titleID = data.readShort();
        LOTRTitle title = LOTRTitle.forID(titleID);
        byte colorID = data.readByte();
        EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorID);
        if(title != null && color != null) {
            this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketTitle, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketTitle packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRTitle.PlayerTitle title = packet.playerTitle;
            if(title == null) {
                pd.setPlayerTitle(null);
            }
            else {
                pd.setPlayerTitle(title);
            }
            return null;
        }
    }

}
