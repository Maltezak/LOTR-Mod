package lotr.common.network;

import java.io.IOException;
import java.util.UUID;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public abstract class LOTRPacketFellowshipPartialUpdate
implements IMessage {
    protected UUID fellowshipID;

    protected LOTRPacketFellowshipPartialUpdate() {
    }

    protected LOTRPacketFellowshipPartialUpdate(LOTRFellowship fs) {
        this.fellowshipID = fs.getFellowshipID();
    }

    public void toBytes(ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
    }

    public void fromBytes(ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
    }

    public abstract void updateClient(LOTRFellowshipClient var1);

    public static class ToggleShowMap
    extends LOTRPacketFellowshipPartialUpdate {
        private boolean showMapLocations;

        public ToggleShowMap() {
        }

        public ToggleShowMap(LOTRFellowship fs) {
            super(fs);
            this.showMapLocations = fs.getShowMapLocations();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.showMapLocations);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.showMapLocations = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setShowMapLocations(this.showMapLocations);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<ToggleShowMap> {
        }

    }

    public static class ToggleHiredFriendlyFire
    extends LOTRPacketFellowshipPartialUpdate {
        private boolean preventHiredFF;

        public ToggleHiredFriendlyFire() {
        }

        public ToggleHiredFriendlyFire(LOTRFellowship fs) {
            super(fs);
            this.preventHiredFF = fs.getPreventHiredFriendlyFire();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.preventHiredFF);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.preventHiredFF = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setPreventHiredFriendlyFire(this.preventHiredFF);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<ToggleHiredFriendlyFire> {
        }

    }

    public static class TogglePvp
    extends LOTRPacketFellowshipPartialUpdate {
        private boolean preventPVP;

        public TogglePvp() {
        }

        public TogglePvp(LOTRFellowship fs) {
            super(fs);
            this.preventPVP = fs.getPreventPVP();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.preventPVP);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.preventPVP = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setPreventPVP(this.preventPVP);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<TogglePvp> {
        }

    }

    public static class ChangeIcon
    extends LOTRPacketFellowshipPartialUpdate {
        private ItemStack fellowshipIcon;

        public ChangeIcon() {
        }

        public ChangeIcon(LOTRFellowship fs) {
            super(fs);
            this.fellowshipIcon = fs.getIcon();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            NBTTagCompound iconData = new NBTTagCompound();
            if (this.fellowshipIcon != null) {
                this.fellowshipIcon.writeToNBT(iconData);
            }
            try {
                new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
            }
            catch (IOException e) {
                FMLLog.severe("LOTR: Error writing fellowship icon data");
                e.printStackTrace();
            }
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            NBTTagCompound iconData = new NBTTagCompound();
            try {
                iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
            }
            catch (IOException e) {
                FMLLog.severe("LOTR: Error reading fellowship icon data");
                e.printStackTrace();
            }
            this.fellowshipIcon = ItemStack.loadItemStackFromNBT(iconData);
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setIcon(this.fellowshipIcon);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<ChangeIcon> {
        }

    }

    public static class Rename
    extends LOTRPacketFellowshipPartialUpdate {
        private String fellowshipName;

        public Rename() {
        }

        public Rename(LOTRFellowship fs) {
            super(fs);
            this.fellowshipName = fs.getName();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            byte[] fsNameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
            data.writeByte(fsNameBytes.length);
            data.writeBytes(fsNameBytes);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            byte fsNameLength = data.readByte();
            ByteBuf fsNameBytes = data.readBytes(fsNameLength);
            this.fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setName(this.fellowshipName);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<Rename> {
        }

    }

    public static class UpdatePlayerTitle
    extends OnePlayerUpdate {
        private LOTRTitle.PlayerTitle playerTitle;

        public UpdatePlayerTitle() {
        }

        public UpdatePlayerTitle(LOTRFellowship fs, UUID player, LOTRTitle.PlayerTitle title) {
            super(fs, player);
            this.playerTitle = title;
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            LOTRTitle.PlayerTitle.writeNullableTitle(data, this.playerTitle);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.updatePlayerTitle(this.playerName, this.playerTitle);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<UpdatePlayerTitle> {
        }

    }

    public static class RemoveAdmin
    extends OnePlayerUpdate {
        private boolean isAdminned;

        public RemoveAdmin() {
        }

        public RemoveAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
            super(fs, admin);
            this.isAdminned = adminned;
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.isAdminned);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.isAdminned = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.removeAdmin(this.playerName, this.isAdminned);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<RemoveAdmin> {
        }

    }

    public static class SetAdmin
    extends OnePlayerUpdate {
        private boolean isAdminned;

        public SetAdmin() {
        }

        public SetAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
            super(fs, admin);
            this.isAdminned = adminned;
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.isAdminned);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.isAdminned = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setAdmin(this.playerName, this.isAdminned);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<SetAdmin> {
        }

    }

    public static class RemoveMember
    extends OnePlayerUpdate {
        public RemoveMember() {
        }

        public RemoveMember(LOTRFellowship fs, UUID member) {
            super(fs, member);
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.removeMember(this.playerName);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<RemoveMember> {
        }

    }

    public static class AddMember
    extends OnePlayerUpdate {
        private LOTRTitle.PlayerTitle playerTitle;

        public AddMember() {
        }

        public AddMember(LOTRFellowship fs, UUID member) {
            super(fs, member);
            this.playerTitle = LOTRLevelData.getData(member).getPlayerTitle();
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            LOTRTitle.PlayerTitle.writeNullableTitle(data, this.playerTitle);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.addMember(this.playerName, this.playerTitle);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<AddMember> {
        }

    }

    public static class SetOwner
    extends OnePlayerUpdate {
        private boolean isOwned;

        public SetOwner() {
        }

        public SetOwner(LOTRFellowship fs, UUID owner, boolean owned) {
            super(fs, owner);
            this.isOwned = owned;
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            data.writeBoolean(this.isOwned);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            this.isOwned = data.readBoolean();
        }

        @Override
        public void updateClient(LOTRFellowshipClient fellowship) {
            fellowship.setOwner(this.playerName, this.isOwned);
        }

        public static class Handler
        extends LOTRPacketFellowshipPartialUpdate.Handler<SetOwner> {
        }

    }

    public static abstract class OnePlayerUpdate
    extends LOTRPacketFellowshipPartialUpdate {
        protected String playerName;

        public OnePlayerUpdate() {
        }

        public OnePlayerUpdate(LOTRFellowship fs, UUID player) {
            super(fs);
            this.playerName = LOTRPacketFellowship.getPlayerUsername(player);
        }

        @Override
        public void toBytes(ByteBuf data) {
            super.toBytes(data);
            byte[] playerNameBytes = this.playerName.getBytes(Charsets.UTF_8);
            data.writeByte(playerNameBytes.length);
            data.writeBytes(playerNameBytes);
        }

        @Override
        public void fromBytes(ByteBuf data) {
            super.fromBytes(data);
            byte playerNameLength = data.readByte();
            ByteBuf playerNameBytes = data.readBytes(playerNameLength);
            this.playerName = playerNameBytes.toString(Charsets.UTF_8);
        }
    }

    public static abstract class Handler<P extends LOTRPacketFellowshipPartialUpdate>
    implements IMessageHandler<P, IMessage> {
        public IMessage onMessage(P packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRFellowshipClient fellowship = pd.getClientFellowshipByID(((LOTRPacketFellowshipPartialUpdate)packet).fellowshipID);
            if (fellowship != null) {
                ((LOTRPacketFellowshipPartialUpdate)packet).updateClient(fellowship);
            } else {
                LOTRLog.logger.warn("Client couldn't find fellowship for ID " + ((LOTRPacketFellowshipPartialUpdate)packet).fellowshipID);
            }
            return null;
        }
    }

}

