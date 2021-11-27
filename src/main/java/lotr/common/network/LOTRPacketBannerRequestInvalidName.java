package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fellowship.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class LOTRPacketBannerRequestInvalidName
implements IMessage {
    private int bannerID;
    private int slot;
    private String username;

    public LOTRPacketBannerRequestInvalidName() {
    }

    public LOTRPacketBannerRequestInvalidName(LOTREntityBanner banner, int i, String s) {
        this.bannerID = banner.getEntityId();
        this.slot = i;
        this.username = s;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.bannerID);
        data.writeShort(this.slot);
        byte[] nameBytes = this.username.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    public void fromBytes(ByteBuf data) {
        this.bannerID = data.readInt();
        this.slot = data.readShort();
        byte length = data.readByte();
        this.username = data.readBytes(length).toString(Charsets.UTF_8);
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketBannerRequestInvalidName, IMessage> {
        public IMessage onMessage(LOTRPacketBannerRequestInvalidName packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity bEntity = world.getEntityByID(packet.bannerID);
            if (bEntity instanceof LOTREntityBanner) {
                LOTREntityBanner banner = (LOTREntityBanner)bEntity;
                String username = packet.username;
                boolean valid = false;
                if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                    String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                    LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
                    if (fellowship != null) {
                        valid = true;
                    }
                } else {
                    GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(packet.username);
                    if (profile != null) {
                        valid = true;
                    }
                }
                LOTRPacketBannerValidate packetResponse = new LOTRPacketBannerValidate(banner.getEntityId(), packet.slot, packet.username, valid);
                LOTRPacketHandler.networkWrapper.sendTo(packetResponse, entityplayer);
            }
            return null;
        }
    }

}

