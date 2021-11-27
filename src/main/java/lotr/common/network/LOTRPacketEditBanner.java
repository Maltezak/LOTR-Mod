package lotr.common.network;

import java.util.List;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.entity.item.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketEditBanner
implements IMessage {
    private int bannerID;
    public boolean playerSpecificProtection;
    public boolean selfProtection;
    public float alignmentProtection;
    public int whitelistLength;
    public String[] whitelistSlots;
    public int[] whitelistPerms;
    public int defaultPerms;

    public LOTRPacketEditBanner() {
    }

    public LOTRPacketEditBanner(LOTREntityBanner banner) {
        this.bannerID = banner.getEntityId();
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.bannerID);
        data.writeBoolean(this.playerSpecificProtection);
        data.writeBoolean(this.selfProtection);
        data.writeFloat(this.alignmentProtection);
        data.writeShort(this.whitelistLength);
        boolean sendWhitelist = this.whitelistSlots != null;
        data.writeBoolean(sendWhitelist);
        if (sendWhitelist) {
            data.writeShort(this.whitelistSlots.length);
            for (int index = 0; index < this.whitelistSlots.length; ++index) {
                data.writeShort(index);
                String username = this.whitelistSlots[index];
                if (StringUtils.isNullOrEmpty(username)) {
                    data.writeByte(-1);
                    continue;
                }
                byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
                data.writeByte(usernameBytes.length);
                data.writeBytes(usernameBytes);
                data.writeShort(this.whitelistPerms[index]);
            }
            data.writeShort(-1);
        }
        data.writeShort(this.defaultPerms);
    }

    public void fromBytes(ByteBuf data) {
        this.bannerID = data.readInt();
        this.playerSpecificProtection = data.readBoolean();
        this.selfProtection = data.readBoolean();
        this.alignmentProtection = data.readFloat();
        this.whitelistLength = data.readShort();
        boolean sendWhitelist = data.readBoolean();
        if (sendWhitelist) {
            this.whitelistSlots = new String[data.readShort()];
            this.whitelistPerms = new int[this.whitelistSlots.length];
            short index = 0;
            while ((index = data.readShort()) >= 0) {
                byte length = data.readByte();
                if (length == -1) {
                    this.whitelistSlots[index] = null;
                    continue;
                }
                ByteBuf usernameBytes = data.readBytes(length);
                this.whitelistSlots[index] = usernameBytes.toString(Charsets.UTF_8);
                this.whitelistPerms[index] = data.readShort();
            }
        }
        this.defaultPerms = data.readShort();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketEditBanner, IMessage> {
        public IMessage onMessage(LOTRPacketEditBanner packet, MessageContext context) {
            LOTREntityBanner banner;
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity bEntity = world.getEntityByID(packet.bannerID);
            if (bEntity instanceof LOTREntityBanner && (banner = (LOTREntityBanner)bEntity).canPlayerEditBanner(entityplayer)) {
                banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
                banner.setSelfProtection(packet.selfProtection);
                banner.setAlignmentProtection(packet.alignmentProtection);
                banner.resizeWhitelist(packet.whitelistLength);
                if (packet.whitelistSlots != null) {
                    for (int index = 0; index < packet.whitelistSlots.length; ++index) {
                        if (index == 0) continue;
                        String username = packet.whitelistSlots[index];
                        int perms = packet.whitelistPerms[index];
                        if (StringUtils.isNullOrEmpty(username)) {
                            banner.whitelistPlayer(index, null);
                            continue;
                        }
                        List<LOTRBannerProtection.Permission> decodedPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(perms);
                        if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                            String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                            LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
                            if (fellowship == null) continue;
                            banner.whitelistFellowship(index, fellowship, decodedPerms);
                            continue;
                        }
                        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
                        if (profile == null) continue;
                        banner.whitelistPlayer(index, profile, decodedPerms);
                    }
                }
                List<LOTRBannerProtection.Permission> defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
                banner.setDefaultPermissions(defaultPerms);
            }
            return null;
        }
    }

}

