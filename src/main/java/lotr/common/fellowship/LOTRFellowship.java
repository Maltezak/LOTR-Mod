package lotr.common.fellowship;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRLevelData;
import lotr.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeHooks;

public class LOTRFellowship {
    private boolean needsSave = false;
    private UUID fellowshipUUID;
    private String fellowshipName;
    private boolean disbanded = false;
    private ItemStack fellowshipIcon;
    private UUID ownerUUID;
    private List<UUID> memberUUIDs = new ArrayList<>();
    private Set<UUID> adminUUIDs = new HashSet<>();
    private boolean preventPVP = true;
    private boolean preventHiredFF = true;
    private boolean showMapLocations = true;

    public LOTRFellowship() {
        this.fellowshipUUID = UUID.randomUUID();
    }

    public LOTRFellowship(UUID fsID) {
        this.fellowshipUUID = fsID;
    }

    public LOTRFellowship(UUID owner, String name) {
        this();
        this.ownerUUID = owner;
        this.fellowshipName = name;
    }

    public void createAndRegister() {
        LOTRFellowshipData.addFellowship(this);
        LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
        this.updateForAllMembers(new FellowshipUpdateType.Full());
        this.markDirty();
    }

    public void validate() {
        if (this.fellowshipUUID == null) {
            this.fellowshipUUID = UUID.randomUUID();
        }
        if (this.ownerUUID == null) {
            this.ownerUUID = UUID.randomUUID();
        }
    }

    public void markDirty() {
        this.needsSave = true;
    }

    public boolean needsSave() {
        return this.needsSave;
    }

    public void save(NBTTagCompound fsData) {
        if (this.ownerUUID != null) {
            fsData.setString("Owner", this.ownerUUID.toString());
        }
        NBTTagList memberTags = new NBTTagList();
        for (UUID member : this.memberUUIDs) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Member", member.toString());
            if (this.adminUUIDs.contains(member)) {
                nbt.setBoolean("Admin", true);
            }
            memberTags.appendTag(nbt);
        }
        fsData.setTag("Members", memberTags);
        if (this.fellowshipName != null) {
            fsData.setString("Name", this.fellowshipName);
        }
        if (this.fellowshipIcon != null) {
            NBTTagCompound itemData = new NBTTagCompound();
            this.fellowshipIcon.writeToNBT(itemData);
            fsData.setTag("Icon", itemData);
        }
        fsData.setBoolean("PreventPVP", this.preventPVP);
        fsData.setBoolean("PreventHiredFF", this.preventHiredFF);
        fsData.setBoolean("ShowMap", this.showMapLocations);
        this.needsSave = false;
    }

    public void load(NBTTagCompound fsData) {
        if (fsData.hasKey("Owner")) {
            this.ownerUUID = UUID.fromString(fsData.getString("Owner"));
        }
        this.memberUUIDs.clear();
        this.adminUUIDs.clear();
        NBTTagList memberTags = fsData.getTagList("Members", 10);
        for (int i = 0; i < memberTags.tagCount(); ++i) {
            NBTTagCompound nbt = memberTags.getCompoundTagAt(i);
            UUID member = UUID.fromString(nbt.getString("Member"));
            if (member == null) continue;
            this.memberUUIDs.add(member);
            if (!nbt.hasKey("Admin") || !(nbt.getBoolean("Admin"))) continue;
            this.adminUUIDs.add(member);
        }
        if (fsData.hasKey("Name")) {
            this.fellowshipName = fsData.getString("Name");
        }
        if (fsData.hasKey("Icon")) {
            NBTTagCompound itemData = fsData.getCompoundTag("Icon");
            this.fellowshipIcon = ItemStack.loadItemStackFromNBT(itemData);
        }
        if (fsData.hasKey("PreventPVP")) {
            this.preventPVP = fsData.getBoolean("PreventPVP");
        }
        if (fsData.hasKey("PreventPVP")) {
            this.preventHiredFF = fsData.getBoolean("PreventHiredFF");
        }
        if (fsData.hasKey("ShowMap")) {
            this.showMapLocations = fsData.getBoolean("ShowMap");
        }
        this.validate();
    }

    public UUID getFellowshipID() {
        return this.fellowshipUUID;
    }

    public UUID getOwner() {
        return this.ownerUUID;
    }

    public boolean isOwner(UUID player) {
        return this.ownerUUID.equals(player);
    }

    public void setOwner(UUID owner) {
        UUID prevOwner = this.ownerUUID;
        if (prevOwner != null && !this.memberUUIDs.contains(prevOwner)) {
            this.memberUUIDs.add(0, prevOwner);
        }
        this.ownerUUID = owner;
        if (this.memberUUIDs.contains(owner)) {
            this.memberUUIDs.remove(owner);
        }
        if (this.adminUUIDs.contains(owner)) {
            this.adminUUIDs.remove(owner);
        }
        LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
        this.updateForAllMembers(new FellowshipUpdateType.SetOwner(this.ownerUUID));
        this.markDirty();
    }

    public String getName() {
        return this.fellowshipName;
    }

    public void setName(String name) {
        this.fellowshipName = name;
        this.updateForAllMembers(new FellowshipUpdateType.Rename());
        this.markDirty();
    }

    public boolean containsPlayer(UUID player) {
        return this.isOwner(player) || this.hasMember(player);
    }

    public boolean hasMember(UUID player) {
        return this.memberUUIDs.contains(player);
    }

    public void addMember(UUID player) {
        if (!this.isOwner(player) && !this.memberUUIDs.contains(player)) {
            this.memberUUIDs.add(player);
            LOTRLevelData.getData(player).addFellowship(this);
            this.updateForAllMembers(new FellowshipUpdateType.AddMember(player));
            this.markDirty();
        }
    }

    public void removeMember(UUID player) {
        if (this.memberUUIDs.contains(player)) {
            this.memberUUIDs.remove(player);
            if (this.adminUUIDs.contains(player)) {
                this.adminUUIDs.remove(player);
            }
            LOTRLevelData.getData(player).removeFellowship(this);
            this.updateForAllMembers(new FellowshipUpdateType.RemoveMember(player));
            this.markDirty();
        }
    }

    public List<UUID> getMemberUUIDs() {
        return this.memberUUIDs;
    }

    public List<UUID> getAllPlayerUUIDs() {
        ArrayList<UUID> list = new ArrayList<>();
        list.add(this.ownerUUID);
        list.addAll(this.memberUUIDs);
        return list;
    }

    public boolean isAdmin(UUID player) {
        return this.hasMember(player) && this.adminUUIDs.contains(player);
    }

    public void setAdmin(UUID player, boolean flag) {
        if (this.memberUUIDs.contains(player)) {
            if (flag && !this.adminUUIDs.contains(player)) {
                this.adminUUIDs.add(player);
                this.updateForAllMembers(new FellowshipUpdateType.SetAdmin(player));
                this.markDirty();
            } else if (!flag && this.adminUUIDs.contains(player)) {
                this.adminUUIDs.remove(player);
                this.updateForAllMembers(new FellowshipUpdateType.RemoveAdmin(player));
                this.markDirty();
            }
        }
    }

    public ItemStack getIcon() {
        return this.fellowshipIcon;
    }

    public void setIcon(ItemStack itemstack) {
        this.fellowshipIcon = itemstack;
        this.updateForAllMembers(new FellowshipUpdateType.ChangeIcon());
        this.markDirty();
    }

    public boolean getPreventPVP() {
        return this.preventPVP;
    }

    public void setPreventPVP(boolean flag) {
        this.preventPVP = flag;
        this.updateForAllMembers(new FellowshipUpdateType.TogglePvp());
        this.markDirty();
    }

    public boolean getPreventHiredFriendlyFire() {
        return this.preventHiredFF;
    }

    public void setPreventHiredFriendlyFire(boolean flag) {
        this.preventHiredFF = flag;
        this.updateForAllMembers(new FellowshipUpdateType.ToggleHiredFriendlyFire());
        this.markDirty();
    }

    public boolean getShowMapLocations() {
        return this.showMapLocations;
    }

    public void setShowMapLocations(boolean flag) {
        this.showMapLocations = flag;
        this.updateForAllMembers(new FellowshipUpdateType.ToggleShowMapLocations());
        this.markDirty();
    }

    public void updateForAllMembers(FellowshipUpdateType updateType) {
        for (UUID player : this.getAllPlayerUUIDs()) {
            LOTRLevelData.getData(player).updateFellowship(this, updateType);
        }
    }

    public void disband() {
        this.disbanded = true;
        ArrayList<UUID> copyMemberIDs = new ArrayList<>(this.memberUUIDs);
        for (UUID player : copyMemberIDs) {
            this.removeMember(player);
        }
    }

    public boolean isDisbanded() {
        return this.disbanded;
    }

    public void sendFellowshipMessage(EntityPlayerMP sender, String message) {
        if (sender.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            ChatComponentTranslation msgCannotSend = new ChatComponentTranslation("chat.cannotSend");
            msgCannotSend.getChatStyle().setColor(EnumChatFormatting.RED);
            sender.playerNetServerHandler.sendPacket(new S02PacketChat(msgCannotSend));
        } else {
            sender.func_143004_u();
            message = StringUtils.normalizeSpace(message);
            if (StringUtils.isBlank(message)) {
                return;
            }
            for (int i = 0; i < message.length(); ++i) {
                if (ChatAllowedCharacters.isAllowedCharacter(message.charAt(i))) continue;
                sender.playerNetServerHandler.kickPlayerFromServer("Illegal characters in chat");
                return;
            }
            ClickEvent fMsgClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fmsg \"" + this.getName() + "\" ");
            IChatComponent msgComponent = ForgeHooks.newChatWithLinks(message);
            msgComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            IChatComponent senderComponent = sender.func_145748_c_();
            senderComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
            ChatComponentTranslation chatComponent = new ChatComponentTranslation("chat.type.text", senderComponent, "");
            chatComponent = ForgeHooks.onServerChatEvent(sender.playerNetServerHandler, message, chatComponent);
            if (chatComponent == null) {
                return;
            }
            chatComponent.appendSibling(msgComponent);
            ChatComponentTranslation fsComponent = new ChatComponentTranslation("commands.lotr.fmsg.fsPrefix", this.getName());
            fsComponent.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            fsComponent.getChatStyle().setChatClickEvent(fMsgClickEvent);
            ChatComponentTranslation fullChatComponent = new ChatComponentTranslation("%s %s", fsComponent, chatComponent);
            MinecraftServer server = MinecraftServer.getServer();
            server.addChatMessage(fullChatComponent);
            S02PacketChat packetChat = new S02PacketChat(fullChatComponent, false);
            for (Object player : server.getConfigurationManager().playerEntityList) {
                EntityPlayerMP entityplayer = (EntityPlayerMP)player;
                if (!this.containsPlayer(entityplayer.getUniqueID())) continue;
                entityplayer.playerNetServerHandler.sendPacket(packetChat);
            }
        }
    }

    public void sendNotification(EntityPlayer entityplayer, String key, Object ... args) {
        ChatComponentTranslation message = new ChatComponentTranslation(key, args);
        message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        entityplayer.addChatMessage(message);
        LOTRPacketFellowshipNotification packet = new LOTRPacketFellowshipNotification(message);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }
}

