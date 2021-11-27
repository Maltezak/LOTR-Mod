package lotr.common.network;

import java.util.List;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.entity.item.*;
import lotr.common.fellowship.LOTRFellowshipProfile;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketBannerData
implements IMessage {
    private int entityID;
    private boolean openGui;
    public boolean playerSpecificProtection;
    public boolean selfProtection;
    public boolean structureProtection;
    public int customRange;
    public float alignmentProtection;
    public int whitelistLength;
    public String[] whitelistSlots;
    public int[] whitelistPerms;
    public int defaultPerms;
    public boolean thisPlayerHasPermission;

    public LOTRPacketBannerData() {
    }

    public LOTRPacketBannerData(int id, boolean flag) {
        this.entityID = id;
        this.openGui = flag;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.openGui);
        data.writeBoolean(this.playerSpecificProtection);
        data.writeBoolean(this.selfProtection);
        data.writeBoolean(this.structureProtection);
        data.writeShort(this.customRange);
        data.writeFloat(this.alignmentProtection);
        data.writeShort(this.whitelistLength);
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
        data.writeShort(this.defaultPerms);
        data.writeBoolean(this.thisPlayerHasPermission);
    }

    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        this.openGui = data.readBoolean();
        this.playerSpecificProtection = data.readBoolean();
        this.selfProtection = data.readBoolean();
        this.structureProtection = data.readBoolean();
        this.customRange = data.readShort();
        this.alignmentProtection = data.readFloat();
        this.whitelistLength = data.readShort();
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
        this.defaultPerms = data.readShort();
        this.thisPlayerHasPermission = data.readBoolean();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketBannerData, IMessage> {
        public IMessage onMessage(LOTRPacketBannerData packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityBanner) {
                LOTREntityBanner banner = (LOTREntityBanner)entity;
                banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
                banner.setSelfProtection(packet.selfProtection);
                banner.setStructureProtection(packet.structureProtection);
                banner.setCustomRange(packet.customRange);
                banner.setAlignmentProtection(packet.alignmentProtection);
                banner.resizeWhitelist(packet.whitelistLength);
                for (int index = 0; index < packet.whitelistSlots.length; ++index) {
                    String username = packet.whitelistSlots[index];
                    if (StringUtils.isNullOrEmpty(username)) {
                        banner.whitelistPlayer(index, null);
                    } else if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
                        String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
                        LOTRFellowshipProfile profile = new LOTRFellowshipProfile(banner, null, fsName);
                        banner.whitelistPlayer(index, profile);
                    } else {
                        GameProfile profile = new GameProfile(null, username);
                        banner.whitelistPlayer(index, profile);
                    }
                    LOTRBannerWhitelistEntry entry = banner.getWhitelistEntry(index);
                    if (entry == null) continue;
                    entry.decodePermBitFlags(packet.whitelistPerms[index]);
                }
                List<LOTRBannerProtection.Permission> defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
                banner.setDefaultPermissions(defaultPerms);
                banner.setClientside_playerHasPermissionInSurvival(packet.thisPlayerHasPermission);
                if (packet.openGui) {
                    LOTRMod.proxy.displayBannerGui(banner);
                }
            }
            return null;
        }
    }

}

