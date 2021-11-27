package lotr.common;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.command.LOTRCommandAdminHideMap;
import lotr.common.entity.npc.*;
import lotr.common.fac.*;
import lotr.common.fellowship.*;
import lotr.common.item.*;
import lotr.common.network.*;
import lotr.common.quest.*;
import lotr.common.util.LOTRLog;
import lotr.common.world.*;
import lotr.common.world.biome.*;
import lotr.common.world.map.*;
import lotr.common.world.map.LOTRWaypoint.Region;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRPlayerData {
    private UUID playerUUID;
    private boolean needsSave = false;
    private int pdTick = 0;
    private Map<LOTRFaction, Float> alignments = new HashMap<>();
    private Map<LOTRFaction, LOTRFactionData> factionDataMap = new HashMap<>();
    private LOTRFaction viewingFaction;
    private Map<LOTRDimension.DimensionRegion, LOTRFaction> prevRegionFactions = new HashMap<>();
    private boolean hideAlignment = false;
    private Set<LOTRFaction> takenAlignmentRewards = new HashSet<>();
    private LOTRFaction pledgeFaction;
    private int pledgeKillCooldown = 0;
    public static final int PLEDGE_KILL_COOLDOWN_MAX = 24000;
    private int pledgeBreakCooldown;
    private int pledgeBreakCooldownStart;
    private LOTRFaction brokenPledgeFaction = null;
    private boolean hasPre35Alignments = false;
    private boolean chosenUnwantedAlignments = false;
    private boolean hideOnMap = false;
    private boolean adminHideMap = false;
    private boolean showWaypoints = true;
    private boolean showCustomWaypoints = true;
    private boolean showHiddenSharedWaypoints = true;
    private boolean conquestKills = true;
    private List<LOTRAchievement> achievements = new ArrayList<>();
    private LOTRShields shield;
    private boolean friendlyFire = false;
    private boolean hiredDeathMessages = true;
    private ChunkCoordinates deathPoint;
    private int deathDim;
    private int alcoholTolerance;
    private List<LOTRMiniQuest> miniQuests = new ArrayList<>();
    private List<LOTRMiniQuest> miniQuestsCompleted = new ArrayList<>();
    private int completedMiniquestCount;
    private int completedBountyQuests;
    private UUID trackingMiniQuestID;
    private List<LOTRFaction> bountiesPlaced = new ArrayList<>();
    private LOTRWaypoint lastWaypoint;
    private LOTRBiome lastBiome;
    private Map<LOTRGuiMessageTypes, Boolean> sentMessageTypes = new HashMap<>();
    private LOTRTitle.PlayerTitle playerTitle;
    private boolean femRankOverride = false;
    private int ftSinceTick;
    private LOTRAbstractWaypoint targetFTWaypoint;
    private int ticksUntilFT;
    private static int ticksUntilFT_max = 200;
    private UUID uuidToMount;
    private int uuidToMountTime;
    private long lastOnlineTime = -1L;
    private Set<LOTRWaypoint.Region> unlockedFTRegions = new HashSet<>();
    private List<LOTRCustomWaypoint> customWaypoints = new ArrayList<>();
    private List<LOTRCustomWaypoint> customWaypointsShared = new ArrayList<>();
    private Set<CWPSharedKey> cwpSharedUnlocked = new HashSet<>();
    private Set<CWPSharedKey> cwpSharedHidden = new HashSet<>();
    private Map<LOTRWaypoint, Integer> wpUseCounts = new HashMap<>();
    private Map<Integer, Integer> cwpUseCounts = new HashMap<>();
    private Map<CWPSharedKey, Integer> cwpSharedUseCounts = new HashMap<>();
    private int nextCwpID = 20000;
    private List<UUID> fellowshipIDs = new ArrayList<>();
    private List<LOTRFellowshipClient> fellowshipsClient = new ArrayList<>();
    private List<LOTRFellowshipInvite> fellowshipInvites = new ArrayList<>();
    private List<LOTRFellowshipClient> fellowshipInvitesClient = new ArrayList<>();
    private UUID chatBoundFellowshipID;
    private boolean structuresBanned = false;
    private boolean teleportedME = false;
    private LOTRPlayerQuestData questData = new LOTRPlayerQuestData(this);
    private int siegeActiveTime;

    public LOTRPlayerData(UUID uuid) {
        this.playerUUID = uuid;
        this.viewingFaction = LOTRFaction.HOBBIT;
        this.ftSinceTick = LOTRLevelData.getWaypointCooldownMax() * 20;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    private EntityPlayer getPlayer() {
		World[] searchWorlds = LOTRMod.proxy.isClient() ? new World[] { LOTRMod.proxy.getClientWorld() } : MinecraftServer.getServer().worldServers;
		for (World world : searchWorlds) {
			EntityPlayer entityplayer = world.func_152378_a(playerUUID);
			if (entityplayer == null) {
				continue;
			}
			return entityplayer;
		}
		return null;
	}

    private EntityPlayer getOtherPlayer(UUID uuid) {
        for (WorldServer world : MinecraftServer.getServer().worldServers) {
            EntityPlayer entityplayer = world.func_152378_a(uuid);
            if (entityplayer == null) continue;
            return entityplayer;
        }
        return null;
    }

    public void markDirty() {
        this.needsSave = true;
    }

    public boolean needsSave() {
        return this.needsSave;
    }

    public void save(NBTTagCompound playerData) {
        NBTTagList alignmentTags = new NBTTagList();
        for (Map.Entry<LOTRFaction, Float> entry : this.alignments.entrySet()) {
            LOTRFaction faction = entry.getKey();
            float alignment = entry.getValue();
            NBTTagCompound nbt6 = new NBTTagCompound();
            nbt6.setString("Faction", faction.codeName());
            nbt6.setFloat("AlignF", alignment);
            alignmentTags.appendTag(nbt6);
        }
        playerData.setTag("AlignmentMap", alignmentTags);
        NBTTagList factionDataTags = new NBTTagList();
        for (Map.Entry<LOTRFaction, LOTRFactionData> entry : this.factionDataMap.entrySet()) {
            LOTRFaction faction = entry.getKey();
            LOTRFactionData data = entry.getValue();
            NBTTagCompound nbt4 = new NBTTagCompound();
            nbt4.setString("Faction", faction.codeName());
            data.save(nbt4);
            factionDataTags.appendTag(nbt4);
        }
        playerData.setTag("FactionData", factionDataTags);
        playerData.setString("CurrentFaction", this.viewingFaction.codeName());
        NBTTagList prevRegionFactionTags = new NBTTagList();
        for (Map.Entry<LOTRDimension.DimensionRegion, LOTRFaction> entry : this.prevRegionFactions.entrySet()) {
            LOTRDimension.DimensionRegion region = entry.getKey();
            LOTRFaction faction = entry.getValue();
            NBTTagCompound nbt3 = new NBTTagCompound();
            nbt3.setString("Region", region.codeName());
            nbt3.setString("Faction", faction.codeName());
            prevRegionFactionTags.appendTag(nbt3);
        }
        playerData.setTag("PrevRegionFactions", prevRegionFactionTags);
        playerData.setBoolean("HideAlignment", this.hideAlignment);
        NBTTagList takenRewardsTags = new NBTTagList();
        for (LOTRFaction faction : this.takenAlignmentRewards) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Faction", faction.codeName());
            takenRewardsTags.appendTag(nbt);
        }
        playerData.setTag("TakenAlignmentRewards", takenRewardsTags);
        if (this.pledgeFaction != null) {
            playerData.setString("PledgeFac", this.pledgeFaction.codeName());
        }
        playerData.setInteger("PledgeKillCD", this.pledgeKillCooldown);
        playerData.setInteger("PledgeBreakCD", this.pledgeBreakCooldown);
        playerData.setInteger("PledgeBreakCDStart", this.pledgeBreakCooldownStart);
        if (this.brokenPledgeFaction != null) {
            playerData.setString("BrokenPledgeFac", this.brokenPledgeFaction.codeName());
        }
        playerData.setBoolean("Pre35Align", this.hasPre35Alignments);
        playerData.setBoolean("Chosen35Align", this.chosenUnwantedAlignments);
        playerData.setBoolean("HideOnMap", this.hideOnMap);
        playerData.setBoolean("AdminHideMap", this.adminHideMap);
        playerData.setBoolean("ShowWP", this.showWaypoints);
        playerData.setBoolean("ShowCWP", this.showCustomWaypoints);
        playerData.setBoolean("ShowHiddenSWP", this.showHiddenSharedWaypoints);
        playerData.setBoolean("ConquestKills", this.conquestKills);
        NBTTagList achievementTags = new NBTTagList();
        for (LOTRAchievement achievement : this.achievements) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Category", achievement.category.name());
            nbt.setInteger("ID", achievement.ID);
            achievementTags.appendTag(nbt);
        }
        playerData.setTag("Achievements", achievementTags);
        if (this.shield != null) {
            playerData.setString("Shield", this.shield.name());
        }
        playerData.setBoolean("FriendlyFire", this.friendlyFire);
        playerData.setBoolean("HiredDeathMessages", this.hiredDeathMessages);
        if (this.deathPoint != null) {
            playerData.setInteger("DeathX", this.deathPoint.posX);
            playerData.setInteger("DeathY", this.deathPoint.posY);
            playerData.setInteger("DeathZ", this.deathPoint.posZ);
            playerData.setInteger("DeathDim", this.deathDim);
        }
        playerData.setInteger("Alcohol", this.alcoholTolerance);
        NBTTagList miniquestTags = new NBTTagList();
        for (LOTRMiniQuest quest : this.miniQuests) {
            NBTTagCompound nbt7 = new NBTTagCompound();
            quest.writeToNBT(nbt7);
            miniquestTags.appendTag(nbt7);
        }
        playerData.setTag("MiniQuests", miniquestTags);
        NBTTagList miniquestCompletedTags = new NBTTagList();
        for (LOTRMiniQuest quest : this.miniQuestsCompleted) {
            NBTTagCompound nbt8 = new NBTTagCompound();
            quest.writeToNBT(nbt8);
            miniquestCompletedTags.appendTag(nbt8);
        }
        playerData.setTag("MiniQuestsCompleted", miniquestCompletedTags);
        playerData.setInteger("MQCompleteCount", this.completedMiniquestCount);
        playerData.setInteger("MQCompletedBounties", this.completedBountyQuests);
        if (this.trackingMiniQuestID != null) {
            playerData.setString("MiniQuestTrack", this.trackingMiniQuestID.toString());
        }
        NBTTagList bountyTags = new NBTTagList();
        for (LOTRFaction fac : this.bountiesPlaced) {
            String fName = fac.codeName();
            bountyTags.appendTag(new NBTTagString(fName));
        }
        playerData.setTag("BountiesPlaced", bountyTags);
        if (this.lastWaypoint != null) {
            String lastWPName = this.lastWaypoint.getCodeName();
            playerData.setString("LastWP", lastWPName);
        }
        if (this.lastBiome != null) {
            int lastBiomeID = this.lastBiome.biomeID;
            playerData.setShort("LastBiome", (short)lastBiomeID);
        }
        NBTTagList sentMessageTags = new NBTTagList();
        for (Map.Entry<LOTRGuiMessageTypes, Boolean> entry : this.sentMessageTypes.entrySet()) {
            LOTRGuiMessageTypes message = entry.getKey();
            boolean sent = entry.getValue();
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Message", message.getSaveName());
            nbt.setBoolean("Sent", sent);
            sentMessageTags.appendTag(nbt);
        }
        playerData.setTag("SentMessageTypes", sentMessageTags);
        if (this.playerTitle != null) {
            playerData.setString("PlayerTitle", this.playerTitle.getTitle().getTitleName());
            playerData.setInteger("PlayerTitleColor", this.playerTitle.getColor().getFormattingCode());
        }
        playerData.setBoolean("FemRankOverride", this.femRankOverride);
        playerData.setInteger("FTSince", this.ftSinceTick);
        if (this.uuidToMount != null) {
            playerData.setString("MountUUID", this.uuidToMount.toString());
        }
        playerData.setInteger("MountUUIDTime", this.uuidToMountTime);
        playerData.setLong("LastOnlineTime", this.lastOnlineTime);
        NBTTagList unlockedFTRegionTags = new NBTTagList();
        for (LOTRWaypoint.Region region : this.unlockedFTRegions) {
            NBTTagCompound nbt9 = new NBTTagCompound();
            nbt9.setString("Name", region.name());
            unlockedFTRegionTags.appendTag(nbt9);
        }
        playerData.setTag("UnlockedFTRegions", unlockedFTRegionTags);
        NBTTagList customWaypointTags = new NBTTagList();
        for (LOTRCustomWaypoint waypoint : this.customWaypoints) {
            NBTTagCompound nbt = new NBTTagCompound();
            waypoint.writeToNBT(nbt, this);
            customWaypointTags.appendTag(nbt);
        }
        playerData.setTag("CustomWaypoints", customWaypointTags);
        NBTTagList cwpSharedUnlockedTags = new NBTTagList();
        for (CWPSharedKey key : this.cwpSharedUnlocked) {
            NBTTagCompound nbt10 = new NBTTagCompound();
            nbt10.setString("SharingPlayer", key.sharingPlayer.toString());
            nbt10.setInteger("CustomID", key.waypointID);
            cwpSharedUnlockedTags.appendTag(nbt10);
        }
        playerData.setTag("CWPSharedUnlocked", cwpSharedUnlockedTags);
        NBTTagList cwpSharedHiddenTags = new NBTTagList();
        for (CWPSharedKey key : this.cwpSharedHidden) {
            NBTTagCompound nbt11 = new NBTTagCompound();
            nbt11.setString("SharingPlayer", key.sharingPlayer.toString());
            nbt11.setInteger("CustomID", key.waypointID);
            cwpSharedHiddenTags.appendTag(nbt11);
        }
        playerData.setTag("CWPSharedHidden", cwpSharedHiddenTags);
        NBTTagList wpUseTags = new NBTTagList();
        for (Map.Entry<LOTRWaypoint, Integer> e : this.wpUseCounts.entrySet()) {
            LOTRAbstractWaypoint wp = e.getKey();
            int count = e.getValue();
            NBTTagCompound nbt12 = new NBTTagCompound();
            nbt12.setString("WPName", wp.getCodeName());
            nbt12.setInteger("Count", count);
            wpUseTags.appendTag(nbt12);
        }
        playerData.setTag("WPUses", wpUseTags);
        NBTTagList cwpUseTags = new NBTTagList();
        for (Map.Entry<Integer, Integer> e : this.cwpUseCounts.entrySet()) {
            int ID = e.getKey();
            int count = e.getValue();
            NBTTagCompound nbt5 = new NBTTagCompound();
            nbt5.setInteger("CustomID", ID);
            nbt5.setInteger("Count", count);
            cwpUseTags.appendTag(nbt5);
        }
        playerData.setTag("CWPUses", cwpUseTags);
        NBTTagList cwpSharedUseTags = new NBTTagList();
        for (Map.Entry<CWPSharedKey, Integer> e : this.cwpSharedUseCounts.entrySet()) {
            CWPSharedKey key = e.getKey();
            int count = e.getValue();
            NBTTagCompound nbt2 = new NBTTagCompound();
            nbt2.setString("SharingPlayer", key.sharingPlayer.toString());
            nbt2.setInteger("CustomID", key.waypointID);
            nbt2.setInteger("Count", count);
            cwpSharedUseTags.appendTag(nbt2);
        }
        playerData.setTag("CWPSharedUses", cwpSharedUseTags);
        playerData.setInteger("NextCWPID", this.nextCwpID);
        NBTTagList fellowshipTags = new NBTTagList();
        for (UUID fsID : this.fellowshipIDs) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("ID", fsID.toString());
            fellowshipTags.appendTag(nbt);
        }
        playerData.setTag("Fellowships", fellowshipTags);
        NBTTagList fellowshipInviteTags = new NBTTagList();
        for (LOTRFellowshipInvite invite : this.fellowshipInvites) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("ID", invite.fellowshipID.toString());
            if (invite.inviterID != null) {
                nbt.setString("InviterID", invite.inviterID.toString());
            }
            fellowshipInviteTags.appendTag(nbt);
        }
        playerData.setTag("FellowshipInvites", fellowshipInviteTags);
        if (this.chatBoundFellowshipID != null) {
            playerData.setString("ChatBoundFellowship", this.chatBoundFellowshipID.toString());
        }
        playerData.setBoolean("StructuresBanned", this.structuresBanned);
        playerData.setBoolean("TeleportedME", this.teleportedME);
        NBTTagCompound questNBT = new NBTTagCompound();
        this.questData.save(questNBT);
        playerData.setTag("QuestData", questNBT);
        this.needsSave = false;
    }

    public void load(NBTTagCompound playerData) {
        LOTRShields savedShield;
        UUID fsID;
        LOTRTitle title;
        LOTRFaction cur;
        LOTRFaction faction;
        this.alignments.clear();
        NBTTagList alignmentTags = playerData.getTagList("AlignmentMap", 10);
        for (int i = 0; i < alignmentTags.tagCount(); ++i) {
            float alignment;
            NBTTagCompound nbt = alignmentTags.getCompoundTagAt(i);
            LOTRFaction faction2 = LOTRFaction.forName(nbt.getString("Faction"));
            if (faction2 == null) continue;
            if (nbt.hasKey("Alignment")) {
                alignment = nbt.getInteger("Alignment");
                this.hasPre35Alignments = true;
            } else {
                alignment = nbt.getFloat("AlignF");
            }
            this.alignments.put(faction2, alignment);
        }
        this.factionDataMap.clear();
        NBTTagList factionDataTags = playerData.getTagList("FactionData", 10);
        for (int i = 0; i < factionDataTags.tagCount(); ++i) {
            NBTTagCompound nbt = factionDataTags.getCompoundTagAt(i);
            LOTRFaction faction3 = LOTRFaction.forName(nbt.getString("Faction"));
            if (faction3 == null) continue;
            LOTRFactionData data = new LOTRFactionData(this, faction3);
            data.load(nbt);
            this.factionDataMap.put(faction3, data);
        }
        if (playerData.hasKey("CurrentFaction") && (cur = LOTRFaction.forName(playerData.getString("CurrentFaction"))) != null) {
            this.viewingFaction = cur;
        }
        this.prevRegionFactions.clear();
        NBTTagList prevRegionFactionTags = playerData.getTagList("PrevRegionFactions", 10);
        for (int i = 0; i < prevRegionFactionTags.tagCount(); ++i) {
            NBTTagCompound nbt = prevRegionFactionTags.getCompoundTagAt(i);
            LOTRDimension.DimensionRegion region = LOTRDimension.DimensionRegion.forName(nbt.getString("Region"));
            faction = LOTRFaction.forName(nbt.getString("Faction"));
            if (region == null || faction == null) continue;
            this.prevRegionFactions.put(region, faction);
        }
        this.hideAlignment = playerData.getBoolean("HideAlignment");
        this.takenAlignmentRewards.clear();
        NBTTagList takenRewardsTags = playerData.getTagList("TakenAlignmentRewards", 10);
        for (int i = 0; i < takenRewardsTags.tagCount(); ++i) {
            NBTTagCompound nbt = takenRewardsTags.getCompoundTagAt(i);
            faction = LOTRFaction.forName(nbt.getString("Faction"));
            if (faction == null) continue;
            this.takenAlignmentRewards.add(faction);
        }
        this.pledgeFaction = null;
        if (playerData.hasKey("PledgeFac")) {
            this.pledgeFaction = LOTRFaction.forName(playerData.getString("PledgeFac"));
        }
        this.pledgeKillCooldown = playerData.getInteger("PledgeKillCD");
        this.pledgeBreakCooldown = playerData.getInteger("PledgeBreakCD");
        this.pledgeBreakCooldownStart = playerData.getInteger("PledgeBreakCDStart");
        this.brokenPledgeFaction = null;
        if (playerData.hasKey("BrokenPledgeFac")) {
            this.brokenPledgeFaction = LOTRFaction.forName(playerData.getString("BrokenPledgeFac"));
        }
        if (!this.hasPre35Alignments && playerData.hasKey("Pre35Align")) {
            this.hasPre35Alignments = playerData.getBoolean("Pre35Align");
        }
        if (playerData.hasKey("Chosen35Align")) {
            this.chosenUnwantedAlignments = playerData.getBoolean("Chosen35Align");
        }
        this.hideOnMap = playerData.getBoolean("HideOnMap");
        this.adminHideMap = playerData.getBoolean("AdminHideMap");
        if (playerData.hasKey("ShowWP")) {
            this.showWaypoints = playerData.getBoolean("ShowWP");
        }
        if (playerData.hasKey("ShowCWP")) {
            this.showCustomWaypoints = playerData.getBoolean("ShowCWP");
        }
        if (playerData.hasKey("ShowHiddenSWP")) {
            this.showHiddenSharedWaypoints = playerData.getBoolean("ShowHiddenSWP");
        }
        if (playerData.hasKey("ConquestKills")) {
            this.conquestKills = playerData.getBoolean("ConquestKills");
        }
        this.achievements.clear();
        NBTTagList achievementTags = playerData.getTagList("Achievements", 10);
        for (int i = 0; i < achievementTags.tagCount(); ++i) {
            NBTTagCompound nbt = achievementTags.getCompoundTagAt(i);
            String category = nbt.getString("Category");
            int ID = nbt.getInteger("ID");
            LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(LOTRAchievement.categoryForName(category), ID);
            if (achievement == null || this.achievements.contains(achievement)) continue;
            this.achievements.add(achievement);
        }
        this.shield = null;
        if (playerData.hasKey("Shield") && (savedShield = LOTRShields.shieldForName(playerData.getString("Shield"))) != null) {
            this.shield = savedShield;
        }
        if (playerData.hasKey("FriendlyFire")) {
            this.friendlyFire = playerData.getBoolean("FriendlyFire");
        }
        if (playerData.hasKey("HiredDeathMessages")) {
            this.hiredDeathMessages = playerData.getBoolean("HiredDeathMessages");
        }
        this.deathPoint = null;
        if (playerData.hasKey("DeathX") && playerData.hasKey("DeathY") && playerData.hasKey("DeathZ")) {
            this.deathPoint = new ChunkCoordinates(playerData.getInteger("DeathX"), playerData.getInteger("DeathY"), playerData.getInteger("DeathZ"));
            this.deathDim = playerData.hasKey("DeathDim") ? playerData.getInteger("DeathDim") : LOTRDimension.MIDDLE_EARTH.dimensionID;
        }
        this.alcoholTolerance = playerData.getInteger("Alcohol");
        this.miniQuests.clear();
        NBTTagList miniquestTags = playerData.getTagList("MiniQuests", 10);
        for (int i = 0; i < miniquestTags.tagCount(); ++i) {
            NBTTagCompound nbt = miniquestTags.getCompoundTagAt(i);
            LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(nbt, this);
            if (quest == null) continue;
            this.miniQuests.add(quest);
        }
        this.miniQuestsCompleted.clear();
        NBTTagList miniquestCompletedTags = playerData.getTagList("MiniQuestsCompleted", 10);
        for (int i = 0; i < miniquestCompletedTags.tagCount(); ++i) {
            NBTTagCompound nbt = miniquestCompletedTags.getCompoundTagAt(i);
            LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(nbt, this);
            if (quest == null) continue;
            this.miniQuestsCompleted.add(quest);
        }
        this.completedMiniquestCount = playerData.getInteger("MQCompleteCount");
        this.completedBountyQuests = playerData.getInteger("MQCompletedBounties");
        this.trackingMiniQuestID = null;
        if (playerData.hasKey("MiniQuestTrack")) {
            String s = playerData.getString("MiniQuestTrack");
            this.trackingMiniQuestID = UUID.fromString(s);
        }
        this.bountiesPlaced.clear();
        NBTTagList bountyTags = playerData.getTagList("BountiesPlaced", 8);
        for (int i = 0; i < bountyTags.tagCount(); ++i) {
            String fName = bountyTags.getStringTagAt(i);
            LOTRFaction fac = LOTRFaction.forName(fName);
            if (fac == null) continue;
            this.bountiesPlaced.add(fac);
        }
        this.lastWaypoint = null;
        if (playerData.hasKey("LastWP")) {
            String lastWPName = playerData.getString("LastWP");
            this.lastWaypoint = LOTRWaypoint.waypointForName(lastWPName);
        }
        this.lastBiome = null;
        if (playerData.hasKey("LastBiome")) {
            short lastBiomeID = playerData.getShort("LastBiome");
            LOTRBiome[] biomeList = LOTRDimension.MIDDLE_EARTH.biomeList;
            if (lastBiomeID >= 0 && lastBiomeID < biomeList.length) {
                this.lastBiome = biomeList[lastBiomeID];
            }
        }
        this.sentMessageTypes.clear();
        NBTTagList sentMessageTags = playerData.getTagList("SentMessageTypes", 10);
        for (int i = 0; i < sentMessageTags.tagCount(); ++i) {
            NBTTagCompound nbt = sentMessageTags.getCompoundTagAt(i);
            LOTRGuiMessageTypes message = LOTRGuiMessageTypes.forSaveName(nbt.getString("Message"));
            if (message == null) continue;
            boolean sent = nbt.getBoolean("Sent");
            this.sentMessageTypes.put(message, sent);
        }
        this.playerTitle = null;
        if (playerData.hasKey("PlayerTitle") && (title = LOTRTitle.forName(playerData.getString("PlayerTitle"))) != null) {
            int colorCode = playerData.getInteger("PlayerTitleColor");
            EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorCode);
            this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
        }
        if (playerData.hasKey("FemRankOverride")) {
            this.femRankOverride = playerData.getBoolean("FemRankOverride");
        }
        if (playerData.hasKey("FTSince")) {
            this.ftSinceTick = playerData.getInteger("FTSince");
        }
        this.targetFTWaypoint = null;
        this.uuidToMount = null;
        if (playerData.hasKey("MountUUID")) {
            this.uuidToMount = UUID.fromString(playerData.getString("MountUUID"));
        }
        this.uuidToMountTime = playerData.getInteger("MountUUIDTime");
        if (playerData.hasKey("LastOnlineTime")) {
            this.lastOnlineTime = playerData.getLong("LastOnlineTime");
        }
        this.unlockedFTRegions.clear();
        NBTTagList unlockedFTRegionTags = playerData.getTagList("UnlockedFTRegions", 10);
        for (int i = 0; i < unlockedFTRegionTags.tagCount(); ++i) {
            NBTTagCompound nbt = unlockedFTRegionTags.getCompoundTagAt(i);
            String regionName = nbt.getString("Name");
            LOTRWaypoint.Region region = LOTRWaypoint.regionForName(regionName);
            if (region == null) continue;
            this.unlockedFTRegions.add(region);
        }
        this.customWaypoints.clear();
        NBTTagList customWaypointTags = playerData.getTagList("CustomWaypoints", 10);
        for (int i = 0; i < customWaypointTags.tagCount(); ++i) {
            NBTTagCompound nbt = customWaypointTags.getCompoundTagAt(i);
            LOTRCustomWaypoint waypoint = LOTRCustomWaypoint.readFromNBT(nbt, this);
            this.customWaypoints.add(waypoint);
        }
        this.cwpSharedUnlocked.clear();
        NBTTagList cwpSharedUnlockedTags = playerData.getTagList("CWPSharedUnlocked", 10);
        for (int i = 0; i < cwpSharedUnlockedTags.tagCount(); ++i) {
            NBTTagCompound nbt = cwpSharedUnlockedTags.getCompoundTagAt(i);
            UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
            if (sharingPlayer == null) continue;
            int ID = nbt.getInteger("CustomID");
            CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
            this.cwpSharedUnlocked.add(key);
        }
        this.cwpSharedHidden.clear();
        NBTTagList cwpSharedHiddenTags = playerData.getTagList("CWPSharedHidden", 10);
        for (int i = 0; i < cwpSharedHiddenTags.tagCount(); ++i) {
            NBTTagCompound nbt = cwpSharedHiddenTags.getCompoundTagAt(i);
            UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
            if (sharingPlayer == null) continue;
            int ID = nbt.getInteger("CustomID");
            CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
            this.cwpSharedHidden.add(key);
        }
        this.wpUseCounts.clear();
        NBTTagList wpCooldownTags = playerData.getTagList("WPUses", 10);
        for (int i = 0; i < wpCooldownTags.tagCount(); ++i) {
            NBTTagCompound nbt = wpCooldownTags.getCompoundTagAt(i);
            String name = nbt.getString("WPName");
            int count = nbt.getInteger("Count");
            LOTRWaypoint wp = LOTRWaypoint.waypointForName(name);
            if (wp == null) continue;
            this.wpUseCounts.put(wp, count);
        }
        this.cwpUseCounts.clear();
        NBTTagList cwpCooldownTags = playerData.getTagList("CWPUses", 10);
        for (int i = 0; i < cwpCooldownTags.tagCount(); ++i) {
            NBTTagCompound nbt = cwpCooldownTags.getCompoundTagAt(i);
            int ID = nbt.getInteger("CustomID");
            int count = nbt.getInteger("Count");
            this.cwpUseCounts.put(ID, count);
        }
        this.cwpSharedUseCounts.clear();
        NBTTagList cwpSharedCooldownTags = playerData.getTagList("CWPSharedUses", 10);
        for (int i = 0; i < cwpSharedCooldownTags.tagCount(); ++i) {
            NBTTagCompound nbt = cwpSharedCooldownTags.getCompoundTagAt(i);
            UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
            if (sharingPlayer == null) continue;
            int ID = nbt.getInteger("CustomID");
            CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
            int count = nbt.getInteger("Count");
            this.cwpSharedUseCounts.put(key, count);
        }
        this.nextCwpID = 20000;
        if (playerData.hasKey("NextCWPID")) {
            this.nextCwpID = playerData.getInteger("NextCWPID");
        }
        this.fellowshipIDs.clear();
        NBTTagList fellowshipTags = playerData.getTagList("Fellowships", 10);
        for (int i = 0; i < fellowshipTags.tagCount(); ++i) {
            NBTTagCompound nbt = fellowshipTags.getCompoundTagAt(i);
            UUID fsID2 = UUID.fromString(nbt.getString("ID"));
            if (fsID2 == null) continue;
            this.fellowshipIDs.add(fsID2);
        }
        this.fellowshipInvites.clear();
        NBTTagList fellowshipInviteTags = playerData.getTagList("FellowshipInvites", 10);
        for (int i = 0; i < fellowshipInviteTags.tagCount(); ++i) {
            NBTTagCompound nbt = fellowshipInviteTags.getCompoundTagAt(i);
            UUID fsID3 = UUID.fromString(nbt.getString("ID"));
            if (fsID3 == null) continue;
            UUID inviterID = null;
            if (nbt.hasKey("InviterID")) {
                inviterID = UUID.fromString(nbt.getString("InviterID"));
            }
            this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID3, inviterID));
        }
        this.chatBoundFellowshipID = null;
        if (playerData.hasKey("ChatBoundFellowship") && (fsID = UUID.fromString(playerData.getString("ChatBoundFellowship"))) != null) {
            this.chatBoundFellowshipID = fsID;
        }
        this.structuresBanned = playerData.getBoolean("StructuresBanned");
        this.teleportedME = playerData.getBoolean("TeleportedME");
        if (playerData.hasKey("QuestData")) {
            NBTTagCompound questNBT = playerData.getCompoundTag("QuestData");
            this.questData.load(questNBT);
        }
    }

    public void sendPlayerData(EntityPlayerMP entityplayer) throws IOException {
        LOTRFellowship fs;
        NBTTagCompound nbt = new NBTTagCompound();
        this.save(nbt);
        nbt.removeTag("Achievements");
        nbt.removeTag("MiniQuests");
        nbt.removeTag("MiniQuestsCompleted");
        nbt.removeTag("CustomWaypoints");
        nbt.removeTag("Fellowships");
        nbt.removeTag("FellowshipInvites");
        LOTRPacketLoginPlayerData packet = new LOTRPacketLoginPlayerData(nbt);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
        for (LOTRAchievement achievement : this.achievements) {
            this.sendAchievementPacket(entityplayer, achievement, false);
        }
        for (LOTRMiniQuest quest : this.miniQuests) {
            this.sendMiniQuestPacket(entityplayer, quest, false);
        }
        for (LOTRMiniQuest quest : this.miniQuestsCompleted) {
            this.sendMiniQuestPacket(entityplayer, quest, true);
        }
        for (LOTRCustomWaypoint waypoint : this.customWaypoints) {
            LOTRPacketCreateCWPClient cwpPacket = waypoint.getClientPacket();
            LOTRPacketHandler.networkWrapper.sendTo(cwpPacket, entityplayer);
        }
        for (UUID fsID : this.fellowshipIDs) {
            fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null) continue;
            this.sendFellowshipPacket(fs);
        }
        for (LOTRFellowshipInvite invite : this.fellowshipInvites) {
            fs = LOTRFellowshipData.getFellowship(invite.fellowshipID);
            if (fs == null) continue;
            this.sendFellowshipInvitePacket(fs);
        }
        this.addSharedCustomWaypointsFromAllFellowships();
    }

    public void send35AlignmentChoice(EntityPlayerMP entityplayer, World world) {
        if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
            LOTRPacketAlignmentChoiceOffer pkt = new LOTRPacketAlignmentChoiceOffer();
            LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
        }
    }

    public void chooseUnwantedAlignments(EntityPlayerMP entityplayer, Set<LOTRFaction> setZeroFacs) {
        if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
            HashSet<LOTRFaction> validSelections = new HashSet<>();
            for (LOTRFaction fac : setZeroFacs) {
                boolean valid = false;
                if (this.getAlignment(fac) > 0.0f) {
                    for (LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
                        if (fac == otherFac || !this.doFactionsDrain(fac, otherFac) || (this.getAlignment(otherFac) <= 0.0f)) continue;
                        valid = true;
                        break;
                    }
                }
                if (!valid) continue;
                validSelections.add(fac);
            }
            for (LOTRFaction fac : validSelections) {
                this.setAlignment(fac, 0.0f);
                entityplayer.addChatMessage(new ChatComponentTranslation("Set %s alignment to zero", fac.factionName()));
            }
            this.hasPre35Alignments = false;
            this.chosenUnwantedAlignments = true;
            this.markDirty();
        }
    }

    private static boolean isTimerAutosaveTick() {
        return MinecraftServer.getServer() != null && MinecraftServer.getServer().getTickCounter() % 200 == 0;
    }

    public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
        ++this.pdTick;
        LOTRDimension.DimensionRegion currentRegion = this.viewingFaction.factionRegion;
        LOTRDimension currentDim = LOTRDimension.getCurrentDimensionWithFallback(world);
        if (currentRegion.getDimension() != currentDim) {
            currentRegion = currentDim.dimensionRegions.get(0);
            this.setViewingFaction(this.getRegionLastViewedFaction(currentRegion));
        }
        this.runAlignmentDraining(entityplayer);
        this.questData.onUpdate(entityplayer, world);
        if (!this.isSiegeActive()) {
            this.runAchievementChecks(entityplayer, world);
        }
        if (this.playerTitle != null && !this.playerTitle.getTitle().canPlayerUse(entityplayer)) {
            ChatComponentTranslation msg = new ChatComponentTranslation("chat.lotr.loseTitle", this.playerTitle.getFullTitleComponent(entityplayer));
            entityplayer.addChatMessage(msg);
            this.setPlayerTitle(null);
        }
        if (this.pledgeKillCooldown > 0) {
            --this.pledgeKillCooldown;
            if (this.pledgeKillCooldown == 0 || LOTRPlayerData.isTimerAutosaveTick()) {
                this.markDirty();
            }
        }
        if (this.pledgeBreakCooldown > 0) {
            this.setPledgeBreakCooldown(this.pledgeBreakCooldown - 1);
        }
        if (this.trackingMiniQuestID != null && this.getTrackingMiniQuest() == null) {
            this.setTrackingMiniQuest(null);
        }
        List<LOTRMiniQuest> activeMiniquests = this.getActiveMiniQuests();
        for (LOTRMiniQuest quest : activeMiniquests) {
            quest.onPlayerTick(entityplayer);
        }
        if (!this.bountiesPlaced.isEmpty()) {
            for (LOTRFaction fac : this.bountiesPlaced) {
                ChatComponentTranslation msg = new ChatComponentTranslation("chat.lotr.bountyPlaced", fac.factionName());
                msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                entityplayer.addChatMessage(msg);
            }
            this.bountiesPlaced.clear();
            this.markDirty();
        }
        this.setTimeSinceFT(this.ftSinceTick + 1);
        if (this.targetFTWaypoint != null) {
            if (this.ticksUntilFT > 0) {
                int seconds = this.ticksUntilFT / 20;
                if (this.ticksUntilFT == ticksUntilFT_max) {
                    entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.travelTicksStart", seconds));
                } else if (this.ticksUntilFT % 20 == 0 && seconds <= 5) {
                    entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.travelTicks", seconds));
                }
                --this.ticksUntilFT;
                this.setTicksUntilFT(this.ticksUntilFT);
            } else {
                this.sendFTBouncePacket(entityplayer);
            }
        } else {
            this.setTicksUntilFT(0);
        }
        this.lastOnlineTime = this.getCurrentOnlineTime();
        if (this.uuidToMount != null) {
            if (this.uuidToMountTime > 0) {
                --this.uuidToMountTime;
            } else {
                double range = 32.0;
                List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(range, range, range));
                for (Object obj : entities) {
                    Entity entity = (Entity)obj;
                    if (!entity.getUniqueID().equals(this.uuidToMount)) continue;
                    entityplayer.mountEntity(entity);
                    break;
                }
                this.setUUIDToMount(null);
            }
        }
        if (this.pdTick % 24000 == 0 && this.alcoholTolerance > 0) {
            --this.alcoholTolerance;
            this.setAlcoholTolerance(this.alcoholTolerance);
        }
        this.unlockSharedCustomWaypoints(entityplayer);
        if (this.pdTick % 100 == 0 && world.provider instanceof LOTRWorldProvider) {
            int i = MathHelper.floor_double(entityplayer.posX);
            int k = MathHelper.floor_double(entityplayer.posZ);
            LOTRBiome biome = (LOTRBiome)world.provider.getBiomeGenForCoords(i, k);
            if (biome.biomeDimension == LOTRDimension.MIDDLE_EARTH) {
                LOTRBiome prevLastBiome = this.lastBiome;
                this.lastBiome = biome;
                if (prevLastBiome != biome) {
                    this.markDirty();
                }
            }
        }
        if (this.adminHideMap) {
            boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
            if (!entityplayer.capabilities.isCreativeMode || !isOp) {
                this.setAdminHideMap(false);
                LOTRCommandAdminHideMap.notifyUnhidden(entityplayer);
            }
        }
        if (this.siegeActiveTime > 0) {
            --this.siegeActiveTime;
        }
    }

    private long getCurrentOnlineTime() {
        return MinecraftServer.getServer().worldServerForDimension(0).getTotalWorldTime();
    }

    public void updateFastTravelClockFromLastOnlineTime(EntityPlayerMP player, World world) {
        int diff;
        int ftClockIncrease;
        if (this.lastOnlineTime <= 0L) {
            return;
        }
        MinecraftServer server = MinecraftServer.getServer();
        if (!server.isSinglePlayer() && (ftClockIncrease = (int)((diff = (int)((this.getCurrentOnlineTime()) - this.lastOnlineTime)) * (0.1))) > 0) {
            this.setTimeSinceFTWithUpdate(this.ftSinceTick + ftClockIncrease);
            ChatComponentTranslation msg = new ChatComponentTranslation("chat.lotr.ft.offlineTick", LOTRLevelData.getHMSTime_Ticks(diff), LOTRLevelData.getHMSTime_Ticks(ftClockIncrease));
            player.addChatMessage(msg);
        }
    }

    public float getAlignment(LOTRFaction faction) {
        if (faction.hasFixedAlignment) {
            return faction.fixedAlignment;
        }
        Float alignment = this.alignments.get(faction);
        return alignment != null ? alignment : 0.0f;
    }

    public void setAlignment(LOTRFaction faction, float alignment) {
        EntityPlayer entityplayer = this.getPlayer();
        if (faction.isPlayableAlignmentFaction()) {
            float prevAlignment = this.getAlignment(faction);
            this.alignments.put(faction, alignment);
            this.markDirty();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
            }
            this.checkAlignmentAchievements(faction, prevAlignment);
        }
        if (entityplayer != null && !entityplayer.worldObj.isRemote && this.pledgeFaction != null && !this.canPledgeTo(this.pledgeFaction)) {
            this.revokePledgeFaction(entityplayer, false);
        }
    }

    public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, Entity entity) {
        return this.addAlignment(entityplayer, source, faction, null, entity);
    }

    public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, List<LOTRFaction> forcedBonusFactions, Entity entity) {
        return this.addAlignment(entityplayer, source, faction, forcedBonusFactions, entity.posX, entity.boundingBox.minY + entity.height * 0.7, entity.posZ);
    }

    public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, double posX, double posY, double posZ) {
        return this.addAlignment(entityplayer, source, faction, null, posX, posY, posZ);
    }

    public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, List<LOTRFaction> forcedBonusFactions, double posX, double posY, double posZ) {
		float bonus = source.bonus;
		LOTRAlignmentBonusMap factionBonusMap = new LOTRAlignmentBonusMap();
		float prevMainAlignment = getAlignment(faction);
		float conquestBonus = 0.0f;
		if (source.isKill) {
			List<LOTRFaction> killBonuses = faction.getBonusesForKilling();
			for (LOTRFaction bonusFaction : killBonuses) {
				if (!bonusFaction.isPlayableAlignmentFaction() || !bonusFaction.approvesWarCrimes && source.isCivilianKill) {
					continue;
				}
				if (!source.killByHiredUnit) {
					float mplier;
					mplier = forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction) ? 1.0f : bonusFaction.getControlZoneAlignmentMultiplier(entityplayer);
					if (mplier > 0.0f) {
						float alignment = getAlignment(bonusFaction);
						float factionBonus = Math.abs(bonus);
						factionBonus *= mplier;
						if (alignment >= bonusFaction.getPledgeAlignment() && !isPledgedTo(bonusFaction)) {
							factionBonus *= 0.5f;
						}
						factionBonus = checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
						setAlignment(bonusFaction, alignment += factionBonus);
						factionBonusMap.put(bonusFaction, factionBonus);
					}
				}
				if (bonusFaction != getPledgeFaction()) {
					continue;
				}
				float conq = bonus;
				if (source.killByHiredUnit) {
					conq *= 0.25f;
				}
				conquestBonus = LOTRConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, conq);
				getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
			}
			List<LOTRFaction> killPenalties = faction.getPenaltiesForKilling();
			for (LOTRFaction penaltyFaction : killPenalties) {
				if (!penaltyFaction.isPlayableAlignmentFaction() || source.killByHiredUnit) {
					continue;
				}
				float mplier;
				mplier = penaltyFaction == faction ? 1.0f : penaltyFaction.getControlZoneAlignmentMultiplier(entityplayer);
				if (mplier <= 0.0f) {
					continue;
				}
				float alignment = getAlignment(penaltyFaction);
				float factionPenalty = -Math.abs(bonus);
				factionPenalty *= mplier;
				factionPenalty = LOTRAlignmentValues.AlignmentBonus.scalePenalty(factionPenalty, alignment);
				setAlignment(penaltyFaction, alignment += factionPenalty);
				factionBonusMap.put(penaltyFaction, factionPenalty);
			}
		} else if (faction.isPlayableAlignmentFaction()) {
			float alignment = getAlignment(faction);
			float factionBonus = bonus;
			if (factionBonus > 0.0f && alignment >= faction.getPledgeAlignment() && !isPledgedTo(faction)) {
				factionBonus *= 0.5f;
			}
			factionBonus = checkBonusForPledgeEnemyLimit(faction, factionBonus);
			setAlignment(faction, alignment += factionBonus);
			factionBonusMap.put(faction, factionBonus);
		}
		if (!factionBonusMap.isEmpty() || conquestBonus != 0.0f) {
			sendAlignmentBonusPacket(source, faction, prevMainAlignment, factionBonusMap, conquestBonus, posX, posY, posZ);
		}
		return factionBonusMap;
	}

    public void givePureConquestBonus(EntityPlayer entityplayer, LOTRFaction bonusFac, LOTRFaction enemyFac, float conq, String title, double posX, double posY, double posZ) {
        conq = LOTRConquestGrid.onConquestKill(entityplayer, bonusFac, enemyFac, conq);
        this.getFactionData(bonusFac).addConquest(Math.abs(conq));
        if (conq != 0.0f) {
            LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(0.0f, title);
            LOTRPacketAlignmentBonus packet = new LOTRPacketAlignmentBonus(bonusFac, this.getAlignment(bonusFac), new LOTRAlignmentBonusMap(), conq, posX, posY, posZ, source);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    private void sendAlignmentBonusPacket(LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionMap, float conqBonus, double posX, double posY, double posZ) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            LOTRPacketAlignmentBonus packet = new LOTRPacketAlignmentBonus(faction, prevMainAlignment, factionMap, conqBonus, posX, posY, posZ, source);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void setAlignmentFromCommand(LOTRFaction faction, float set) {
        this.setAlignment(faction, set);
    }

    public void addAlignmentFromCommand(LOTRFaction faction, float add) {
        float alignment = this.getAlignment(faction);
        this.setAlignment(faction, alignment += add);
    }

    private float checkBonusForPledgeEnemyLimit(LOTRFaction fac, float bonus) {
        if (this.isPledgeEnemyAlignmentLimited(fac)) {
            float limit;
            float alignment = this.getAlignment(fac);
            if (alignment > (limit = this.getPledgeEnemyAlignmentLimit(fac))) {
                bonus = 0.0f;
            } else if (alignment + bonus > limit) {
                bonus = limit - alignment;
            }
        }
        return bonus;
    }

    public boolean isPledgeEnemyAlignmentLimited(LOTRFaction fac) {
        return this.pledgeFaction != null && this.doesFactionPreventPledge(this.pledgeFaction, fac);
    }

    public float getPledgeEnemyAlignmentLimit(LOTRFaction fac) {
        return 0.0f;
    }

    private void checkAlignmentAchievements(LOTRFaction faction, float prevAlignment) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            float alignment = this.getAlignment(faction);
            faction.checkAlignmentAchievements(entityplayer, alignment);
        }
    }

    private void runAlignmentDraining(EntityPlayerMP entityplayer) {
        if (LOTRConfig.alignmentDrain && this.pdTick % 1000 == 0) {
            ArrayList<LOTRFaction> drainFactions = new ArrayList<>();
            List<LOTRFaction> allFacs = LOTRFaction.getPlayableAlignmentFactions();
            for (LOTRFaction fac1 : allFacs) {
                for (LOTRFaction fac2 : allFacs) {
                    if (!this.doFactionsDrain(fac1, fac2)) continue;
                    float align1 = this.getAlignment(fac1);
                    float align2 = this.getAlignment(fac2);
                    if ((align1 <= 0.0f) || (align2 <= 0.0f)) continue;
                    if (!drainFactions.contains(fac1)) {
                        drainFactions.add(fac1);
                    }
                    if (drainFactions.contains(fac2)) continue;
                    drainFactions.add(fac2);
                }
            }
            if (!drainFactions.isEmpty()) {
                for (LOTRFaction fac : drainFactions) {
                    float align = this.getAlignment(fac);
                    float alignLoss = 5.0f;
                    alignLoss = Math.min(alignLoss, align - 0.0f);
                    this.setAlignment(fac, align -= alignLoss);
                }
                this.sendMessageIfNotReceived(LOTRGuiMessageTypes.ALIGN_DRAIN);
                LOTRPacketAlignDrain packet = new LOTRPacketAlignDrain(drainFactions.size());
                LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
            }
        }
    }

    public boolean doFactionsDrain(LOTRFaction fac1, LOTRFaction fac2) {
        return fac1.isMortalEnemy(fac2);
    }

    public LOTRFactionData getFactionData(LOTRFaction faction) {
        LOTRFactionData data = this.factionDataMap.get(faction);
        if (data == null) {
            data = new LOTRFactionData(this, faction);
            this.factionDataMap.put(faction, data);
        }
        return data;
    }

    public void updateFactionData(LOTRFaction faction, LOTRFactionData factionData) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            this.markDirty();
            NBTTagCompound nbt = new NBTTagCompound();
            factionData.save(nbt);
            LOTRPacketFactionData packet = new LOTRPacketFactionData(faction, nbt);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public LOTRFaction getPledgeFaction() {
        return this.pledgeFaction;
    }

    public boolean isPledgedTo(LOTRFaction fac) {
        return this.pledgeFaction == fac;
    }

    public void setPledgeFaction(LOTRFaction fac) {
        EntityPlayer entityplayer;
        this.pledgeFaction = fac;
        this.pledgeKillCooldown = 0;
        this.markDirty();
        if (fac != null) {
            this.checkAlignmentAchievements(fac, this.getAlignment(fac));
            this.addAchievement(LOTRAchievement.pledgeService);
        }
        if ((entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
            if (fac != null) {
                World world = entityplayer.worldObj;
                world.playSoundAtEntity(entityplayer, "lotr:event.pledge", 1.0f, 1.0f);
            }
            LOTRPacketPledge packet = new LOTRPacketPledge(fac);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public boolean canPledgeTo(LOTRFaction fac) {
        if (fac.isPlayableAlignmentFaction()) {
            return this.hasPledgeAlignment(fac) && this.getFactionsPreventingPledgeTo(fac).isEmpty();
        }
        return false;
    }

    public boolean hasPledgeAlignment(LOTRFaction fac) {
        float alignment = this.getAlignment(fac);
        return alignment >= fac.getPledgeAlignment();
    }

    public List<LOTRFaction> getFactionsPreventingPledgeTo(LOTRFaction fac) {
        ArrayList<LOTRFaction> enemies = new ArrayList<>();
        for (LOTRFaction otherFac : LOTRFaction.values()) {
            if (!otherFac.isPlayableAlignmentFaction() || !this.doesFactionPreventPledge(fac, otherFac) || ((this.getAlignment(otherFac)) <= 0.0f)) continue;
            enemies.add(otherFac);
        }
        return enemies;
    }

    private boolean doesFactionPreventPledge(LOTRFaction pledgeFac, LOTRFaction otherFac) {
        return pledgeFac.isMortalEnemy(otherFac);
    }

    public boolean canMakeNewPledge() {
        return this.pledgeBreakCooldown <= 0;
    }

    public void revokePledgeFaction(EntityPlayer entityplayer, boolean intentional) {
        LOTRFaction wasPledge = this.pledgeFaction;
        float pledgeLvl = wasPledge.getPledgeAlignment();
        float prevAlign = this.getAlignment(wasPledge);
        float diff = prevAlign - pledgeLvl;
        float cd = diff / 5000.0f;
        cd = MathHelper.clamp_float(cd, 0.0f, 1.0f);
        int cdTicks = 36000;
        this.setPledgeFaction(null);
        this.setBrokenPledgeFaction(wasPledge);
        this.setPledgeBreakCooldown(cdTicks += Math.round(cd * 150.0f * 60.0f * 20.0f));
        World world = entityplayer.worldObj;
        if (!world.isRemote) {
            ChatComponentTranslation msg;
            LOTRFactionRank rank = wasPledge.getRank(prevAlign);
            LOTRFactionRank rankBelow = wasPledge.getRankBelow(rank);
            LOTRFactionRank rankBelow2 = wasPledge.getRankBelow(rankBelow);
            float newAlign = rankBelow2.alignment;
            float alignPenalty = (newAlign = Math.max(newAlign, pledgeLvl / 2.0f)) - prevAlign;
            if (alignPenalty < 0.0f) {
                LOTRAlignmentValues.AlignmentBonus penalty = LOTRAlignmentValues.createPledgePenalty(alignPenalty);
                double alignX = 0.0;
                double alignY = 0.0;
                double alignZ = 0.0;
                double lookRange = 2.0;
                Vec3 posEye = Vec3.createVectorHelper(entityplayer.posX, entityplayer.boundingBox.minY + entityplayer.getEyeHeight(), entityplayer.posZ);
                Vec3 look = entityplayer.getLook(1.0f);
                Vec3 posSight = posEye.addVector(look.xCoord * lookRange, look.yCoord * lookRange, look.zCoord * lookRange);
                MovingObjectPosition mop = world.rayTraceBlocks(posEye, posSight);
                if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    alignX = mop.blockX + 0.5;
                    alignY = mop.blockY + 0.5;
                    alignZ = mop.blockZ + 0.5;
                } else {
                    alignX = posSight.xCoord;
                    alignY = posSight.yCoord;
                    alignZ = posSight.zCoord;
                }
                this.addAlignment(entityplayer, penalty, wasPledge, alignX, alignY, alignZ);
            }
            world.playSoundAtEntity(entityplayer, "lotr:event.unpledge", 1.0f, 1.0f);
            if (intentional) {
                msg = new ChatComponentTranslation("chat.lotr.unpledge", wasPledge.factionName());
                entityplayer.addChatMessage(msg);
            } else {
                msg = new ChatComponentTranslation("chat.lotr.autoUnpledge", wasPledge.factionName());
                entityplayer.addChatMessage(msg);
            }
            this.checkAlignmentAchievements(wasPledge, prevAlign);
        }
    }

    public void onPledgeKill(EntityPlayer entityplayer) {
        this.pledgeKillCooldown += 24000;
        this.markDirty();
        if (this.pledgeKillCooldown > 24000) {
            this.revokePledgeFaction(entityplayer, false);
        } else if (this.pledgeFaction != null) {
            ChatComponentTranslation msg = new ChatComponentTranslation("chat.lotr.pledgeKillWarn", this.pledgeFaction.factionName());
            entityplayer.addChatMessage(msg);
        }
    }

    public int getPledgeBreakCooldown() {
        return this.pledgeBreakCooldown;
    }

    public void setPledgeBreakCooldown(int i) {
        boolean bigChange;
        EntityPlayer entityplayer;
        int preCD = this.pledgeBreakCooldown;
        LOTRFaction preBroken = this.brokenPledgeFaction;
        this.pledgeBreakCooldown = i = Math.max(0, i);
        bigChange = (this.pledgeBreakCooldown == 0 || preCD == 0) && this.pledgeBreakCooldown != preCD;
        if (this.pledgeBreakCooldown > this.pledgeBreakCooldownStart) {
            this.setPledgeBreakCooldownStart(this.pledgeBreakCooldown);
            bigChange = true;
        }
        if (this.pledgeBreakCooldown <= 0 && preBroken != null) {
            this.setPledgeBreakCooldownStart(0);
            this.setBrokenPledgeFaction(null);
            bigChange = true;
        }
        if (bigChange || LOTRPlayerData.isTimerAutosaveTick()) {
            this.markDirty();
        }
        if ((bigChange || this.pledgeBreakCooldown % 5 == 0) && (entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketBrokenPledge packet = new LOTRPacketBrokenPledge(this.pledgeBreakCooldown, this.pledgeBreakCooldownStart, this.brokenPledgeFaction);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        if (this.pledgeBreakCooldown == 0 && preCD != this.pledgeBreakCooldown && (entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
            String brokenName = preBroken == null ? StatCollector.translateToLocal("lotr.gui.factions.pledgeUnknown") : preBroken.factionName();
            ChatComponentTranslation msg = new ChatComponentTranslation("chat.lotr.pledgeBreakCooldown", brokenName);
            entityplayer.addChatMessage(msg);
        }
    }

    public int getPledgeBreakCooldownStart() {
        return this.pledgeBreakCooldownStart;
    }

    public void setPledgeBreakCooldownStart(int i) {
        this.pledgeBreakCooldownStart = i;
        this.markDirty();
    }

    public LOTRFaction getBrokenPledgeFaction() {
        return this.brokenPledgeFaction;
    }

    public void setBrokenPledgeFaction(LOTRFaction f) {
        this.brokenPledgeFaction = f;
        this.markDirty();
    }

    public List<LOTRAchievement> getAchievements() {
        return this.achievements;
    }

    public List<LOTRAchievement> getEarnedAchievements(LOTRDimension dimension) {
        ArrayList<LOTRAchievement> earnedAchievements = new ArrayList<>();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            for (LOTRAchievement achievement : this.achievements) {
                if (achievement.getDimension() != dimension || !achievement.canPlayerEarn(entityplayer)) continue;
                earnedAchievements.add(achievement);
            }
        }
        return earnedAchievements;
    }

    public boolean hasAchievement(LOTRAchievement achievement) {
        for (LOTRAchievement a : this.achievements) {
            if (a.category != achievement.category || a.ID != achievement.ID) continue;
            return true;
        }
        return false;
    }

    public void addAchievement(LOTRAchievement achievement) {
        if (this.hasAchievement(achievement)) {
            return;
        }
        if (this.isSiegeActive()) {
            return;
        }
        this.achievements.add(achievement);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            boolean canEarn = achievement.canPlayerEarn(entityplayer);
            this.sendAchievementPacket((EntityPlayerMP)entityplayer, achievement, canEarn);
            if (canEarn) {
                achievement.broadcastEarning(entityplayer);
                List<LOTRAchievement> earnedAchievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH);
                int biomes = 0;
                for (int i = 0; i < earnedAchievements.size(); ++i) {
                    LOTRAchievement earnedAchievement = earnedAchievements.get(i);
                    if (!earnedAchievement.isBiomeAchievement) continue;
                    ++biomes;
                }
                if (biomes >= 10) {
                    this.addAchievement(LOTRAchievement.travel10);
                }
                if (biomes >= 20) {
                    this.addAchievement(LOTRAchievement.travel20);
                }
                if (biomes >= 30) {
                    this.addAchievement(LOTRAchievement.travel30);
                }
                if (biomes >= 40) {
                    this.addAchievement(LOTRAchievement.travel40);
                }
                if (biomes >= 50) {
                    this.addAchievement(LOTRAchievement.travel50);
                }
            }
        }
    }

    public void removeAchievement(LOTRAchievement achievement) {
        if (!this.hasAchievement(achievement)) {
            return;
        }
        if (this.achievements.remove(achievement)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                this.sendAchievementRemovePacket((EntityPlayerMP)entityplayer, achievement);
            }
        }
    }

    private void runAchievementChecks(EntityPlayer entityplayer, World world) {
        LOTRMaterial fullMaterial;
        int i = MathHelper.floor_double(entityplayer.posX);
        MathHelper.floor_double(entityplayer.boundingBox.minY);
        int k = MathHelper.floor_double(entityplayer.posZ);
        BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome) {
            Region biomeRegion;
            LOTRBiome lotrbiome = (LOTRBiome)biome;
            LOTRAchievement ach = lotrbiome.getBiomeAchievement();
            if (ach != null) {
                this.addAchievement(ach);
            }
            if ((biomeRegion = lotrbiome.getBiomeWaypoints()) != null) {
                this.unlockFTRegion(biomeRegion);
            }
        }
        if (entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            this.addAchievement(LOTRAchievement.enterMiddleEarth);
        }
        if (entityplayer.dimension == LOTRDimension.UTUMNO.dimensionID) {
            this.addAchievement(LOTRAchievement.enterUtumnoIce);
            int y = MathHelper.floor_double(entityplayer.boundingBox.minY);
            LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(y);
            if (level == LOTRUtumnoLevel.OBSIDIAN) {
                this.addAchievement(LOTRAchievement.enterUtumnoObsidian);
            } else if (level == LOTRUtumnoLevel.FIRE) {
                this.addAchievement(LOTRAchievement.enterUtumnoFire);
            }
        }
        if (entityplayer.inventory.hasItem(LOTRMod.pouch)) {
            this.addAchievement(LOTRAchievement.getPouch);
        }
        HashSet<Block> tables = new HashSet<>();
        int crossbowBolts = 0;
        for (ItemStack item : entityplayer.inventory.mainInventory) {
            Block block;
            if (item != null && item.getItem() instanceof ItemBlock && (block = Block.getBlockFromItem(item.getItem())) instanceof LOTRBlockCraftingTable) {
                tables.add(block);
            }
            if (item == null || !(item.getItem() instanceof LOTRItemCrossbowBolt)) continue;
            crossbowBolts += item.stackSize;
        }
        if (tables.size() >= 10) {
            this.addAchievement(LOTRAchievement.collectCraftingTables);
        }
        if (crossbowBolts >= 128) {
            this.addAchievement(LOTRAchievement.collectCrossbowBolts);
        }
        if (!this.hasAchievement(LOTRAchievement.hundreds) && this.pdTick % 20 == 0) {
            int hiredUnits = 0;
            List<LOTREntityNPC> nearbyNPCs = world.getEntitiesWithinAABB(LOTREntityNPC.class, entityplayer.boundingBox.expand(64.0, 64.0, 64.0));
            for (LOTREntityNPC npc : nearbyNPCs) {
                if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) continue;
                ++hiredUnits;
            }
            if (hiredUnits >= 100) {
                this.addAchievement(LOTRAchievement.hundreds);
            }
        }
        if (biome instanceof LOTRBiomeGenMistyMountains && entityplayer.posY > 192.0) {
            this.addAchievement(LOTRAchievement.climbMistyMountains);
        }
        if ((fullMaterial = this.isPlayerWearingFull(entityplayer)) != null) {
            if (fullMaterial == LOTRMaterial.MITHRIL) {
                this.addAchievement(LOTRAchievement.wearFullMithril);
            } else if (fullMaterial == LOTRMaterial.FUR) {
                this.addAchievement(LOTRAchievement.wearFullFur);
            } else if (fullMaterial == LOTRMaterial.BLUE_DWARVEN) {
                this.addAchievement(LOTRAchievement.wearFullBlueDwarven);
            } else if (fullMaterial == LOTRMaterial.HIGH_ELVEN) {
                this.addAchievement(LOTRAchievement.wearFullHighElven);
            } else if (fullMaterial == LOTRMaterial.GONDOLIN) {
                this.addAchievement(LOTRAchievement.wearFullGondolin);
            } else if (fullMaterial == LOTRMaterial.GALVORN) {
                this.addAchievement(LOTRAchievement.wearFullGalvorn);
            } else if (fullMaterial == LOTRMaterial.RANGER) {
                this.addAchievement(LOTRAchievement.wearFullRanger);
            } else if (fullMaterial == LOTRMaterial.GUNDABAD_URUK) {
                this.addAchievement(LOTRAchievement.wearFullGundabadUruk);
            } else if (fullMaterial == LOTRMaterial.ARNOR) {
                this.addAchievement(LOTRAchievement.wearFullArnor);
            } else if (fullMaterial == LOTRMaterial.RIVENDELL) {
                this.addAchievement(LOTRAchievement.wearFullRivendell);
            } else if (fullMaterial == LOTRMaterial.ANGMAR) {
                this.addAchievement(LOTRAchievement.wearFullAngmar);
            } else if (fullMaterial == LOTRMaterial.WOOD_ELVEN_SCOUT) {
                this.addAchievement(LOTRAchievement.wearFullWoodElvenScout);
            } else if (fullMaterial == LOTRMaterial.WOOD_ELVEN) {
                this.addAchievement(LOTRAchievement.wearFullWoodElven);
            } else if (fullMaterial == LOTRMaterial.DOL_GULDUR) {
                this.addAchievement(LOTRAchievement.wearFullDolGuldur);
            } else if (fullMaterial == LOTRMaterial.DALE) {
                this.addAchievement(LOTRAchievement.wearFullDale);
            } else if (fullMaterial == LOTRMaterial.DWARVEN) {
                this.addAchievement(LOTRAchievement.wearFullDwarven);
            } else if (fullMaterial == LOTRMaterial.GALADHRIM) {
                this.addAchievement(LOTRAchievement.wearFullElven);
            } else if (fullMaterial == LOTRMaterial.HITHLAIN) {
                this.addAchievement(LOTRAchievement.wearFullHithlain);
            } else if (fullMaterial == LOTRMaterial.URUK) {
                this.addAchievement(LOTRAchievement.wearFullUruk);
            } else if (fullMaterial == LOTRMaterial.ROHAN) {
                this.addAchievement(LOTRAchievement.wearFullRohirric);
            } else if (fullMaterial == LOTRMaterial.ROHAN_MARSHAL) {
                this.addAchievement(LOTRAchievement.wearFullRohirricMarshal);
            } else if (fullMaterial == LOTRMaterial.DUNLENDING) {
                this.addAchievement(LOTRAchievement.wearFullDunlending);
            } else if (fullMaterial == LOTRMaterial.GONDOR) {
                this.addAchievement(LOTRAchievement.wearFullGondorian);
            } else if (fullMaterial == LOTRMaterial.DOL_AMROTH) {
                this.addAchievement(LOTRAchievement.wearFullDolAmroth);
            } else if (fullMaterial == LOTRMaterial.RANGER_ITHILIEN) {
                this.addAchievement(LOTRAchievement.wearFullRangerIthilien);
            } else if (fullMaterial == LOTRMaterial.LOSSARNACH) {
                this.addAchievement(LOTRAchievement.wearFullLossarnach);
            } else if (fullMaterial == LOTRMaterial.PELARGIR) {
                this.addAchievement(LOTRAchievement.wearFullPelargir);
            } else if (fullMaterial == LOTRMaterial.PINNATH_GELIN) {
                this.addAchievement(LOTRAchievement.wearFullPinnathGelin);
            } else if (fullMaterial == LOTRMaterial.BLACKROOT) {
                this.addAchievement(LOTRAchievement.wearFullBlackroot);
            } else if (fullMaterial == LOTRMaterial.LAMEDON) {
                this.addAchievement(LOTRAchievement.wearFullLamedon);
            } else if (fullMaterial == LOTRMaterial.MORDOR) {
                this.addAchievement(LOTRAchievement.wearFullOrc);
            } else if (fullMaterial == LOTRMaterial.MORGUL) {
                this.addAchievement(LOTRAchievement.wearFullMorgul);
            } else if (fullMaterial == LOTRMaterial.BLACK_URUK) {
                this.addAchievement(LOTRAchievement.wearFullBlackUruk);
            } else if (fullMaterial == LOTRMaterial.DORWINION) {
                this.addAchievement(LOTRAchievement.wearFullDorwinion);
            } else if (fullMaterial == LOTRMaterial.DORWINION_ELF) {
                this.addAchievement(LOTRAchievement.wearFullDorwinionElf);
            } else if (fullMaterial == LOTRMaterial.RHUN) {
                this.addAchievement(LOTRAchievement.wearFullRhun);
            } else if (fullMaterial == LOTRMaterial.RHUN_GOLD) {
                this.addAchievement(LOTRAchievement.wearFullRhunGold);
            } else if (fullMaterial == LOTRMaterial.NEAR_HARAD) {
                this.addAchievement(LOTRAchievement.wearFullNearHarad);
            } else if (fullMaterial == LOTRMaterial.GULF_HARAD) {
                this.addAchievement(LOTRAchievement.wearFullGulfHarad);
            } else if (fullMaterial == LOTRMaterial.CORSAIR) {
                this.addAchievement(LOTRAchievement.wearFullCorsair);
            } else if (fullMaterial == LOTRMaterial.UMBAR) {
                this.addAchievement(LOTRAchievement.wearFullUmbar);
            } else if (fullMaterial == LOTRMaterial.HARNEDOR) {
                this.addAchievement(LOTRAchievement.wearFullHarnedor);
            } else if (fullMaterial == LOTRMaterial.HARAD_NOMAD) {
                this.addAchievement(LOTRAchievement.wearFullNomad);
            } else if (fullMaterial == LOTRMaterial.BLACK_NUMENOREAN) {
                this.addAchievement(LOTRAchievement.wearFullBlackNumenorean);
            } else if (fullMaterial == LOTRMaterial.MOREDAIN) {
                this.addAchievement(LOTRAchievement.wearFullMoredain);
            } else if (fullMaterial == LOTRMaterial.TAUREDAIN) {
                this.addAchievement(LOTRAchievement.wearFullTauredain);
            } else if (fullMaterial == LOTRMaterial.TAUREDAIN_GOLD) {
                this.addAchievement(LOTRAchievement.wearFullTaurethrimGold);
            } else if (fullMaterial == LOTRMaterial.HALF_TROLL) {
                this.addAchievement(LOTRAchievement.wearFullHalfTroll);
            } else if (fullMaterial == LOTRMaterial.UTUMNO) {
                this.addAchievement(LOTRAchievement.wearFullUtumno);
            }
        }
    }

    public LOTRMaterial isPlayerWearingFull(EntityPlayer entityplayer) {
        LOTRMaterial fullMaterial = null;
        for (ItemStack itemstack : entityplayer.inventory.armorInventory) {
            if (itemstack != null && itemstack.getItem() instanceof LOTRItemArmor) {
                LOTRItemArmor armor = (LOTRItemArmor)itemstack.getItem();
                LOTRMaterial thisMaterial = armor.getLOTRArmorMaterial();
                if (fullMaterial == null) {
                    fullMaterial = thisMaterial;
                    continue;
                }
                if (fullMaterial == thisMaterial) continue;
                return null;
            }
            return null;
        }
        return fullMaterial;
    }

    private void sendAchievementPacket(EntityPlayerMP entityplayer, LOTRAchievement achievement, boolean display) {
        LOTRPacketAchievement packet = new LOTRPacketAchievement(achievement, display);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    private void sendAchievementRemovePacket(EntityPlayerMP entityplayer, LOTRAchievement achievement) {
        LOTRPacketAchievementRemove packet = new LOTRPacketAchievementRemove(achievement);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public void setShield(LOTRShields lotrshield) {
        this.shield = lotrshield;
        this.markDirty();
    }

    public LOTRShields getShield() {
        return this.shield;
    }

    public void setStructuresBanned(boolean flag) {
        this.structuresBanned = flag;
        this.markDirty();
    }

    public boolean getStructuresBanned() {
        return this.structuresBanned;
    }

    private void sendOptionsPacket(int option, boolean flag) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketOptions packet = new LOTRPacketOptions(option, flag);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public boolean getFriendlyFire() {
        return this.friendlyFire;
    }

    public void setFriendlyFire(boolean flag) {
        this.friendlyFire = flag;
        this.markDirty();
        this.sendOptionsPacket(0, flag);
    }

    public boolean getEnableHiredDeathMessages() {
        return this.hiredDeathMessages;
    }

    public void setEnableHiredDeathMessages(boolean flag) {
        this.hiredDeathMessages = flag;
        this.markDirty();
        this.sendOptionsPacket(1, flag);
    }

    public boolean getHideAlignment() {
        return this.hideAlignment;
    }

    public void setHideAlignment(boolean flag) {
        this.hideAlignment = flag;
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
        }
    }

    private void sendFTBouncePacket(EntityPlayerMP entityplayer) {
        LOTRPacketFTBounceClient packet = new LOTRPacketFTBounceClient();
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public void receiveFTBouncePacket() {
        if (this.targetFTWaypoint != null && this.ticksUntilFT <= 0) {
            this.fastTravelTo(this.targetFTWaypoint);
            this.setTargetFTWaypoint(null);
        }
    }

    private void fastTravelTo(LOTRAbstractWaypoint waypoint) {
		EntityPlayer player = getPlayer();
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			WorldServer world = (WorldServer) entityplayer.worldObj;
			int startX = MathHelper.floor_double(entityplayer.posX);
			int startZ = MathHelper.floor_double(entityplayer.posZ);
			double range = 256.0;
			List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
			HashSet<EntityLiving> entitiesToTransport = new HashSet<>();
			for (EntityLiving entity : entities) {
				EntityTameable pet;
				if (entity instanceof LOTREntityNPC) {
					LOTREntityGollum gollum;
					LOTREntityNPC npc = (LOTREntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.shouldFollowPlayer()) {
						entitiesToTransport.add(npc);
						continue;
					}
					if (npc instanceof LOTREntityGollum && (gollum = (LOTREntityGollum) npc).getGollumOwner() == entityplayer && !gollum.isGollumSitting()) {
						entitiesToTransport.add(gollum);
						continue;
					}
				}
				if (entity instanceof EntityTameable && (pet = (EntityTameable) entity).getOwner() == entityplayer && !pet.isSitting()) {
					entitiesToTransport.add(pet);
					continue;
				}
				if (!entity.getLeashed() || entity.getLeashedToEntity() != entityplayer) {
					continue;
				}
				entitiesToTransport.add(entity);
			}
			HashSet<EntityLiving> removes = new HashSet<>();
			for (EntityLiving entity : entitiesToTransport) {
				Entity rider = entity.riddenByEntity;
				if (rider == null || !entitiesToTransport.contains(rider)) {
					continue;
				}
				removes.add(entity);
			}
			entitiesToTransport.removeAll(removes);
			int i = waypoint.getXCoord();
			int k = waypoint.getZCoord();
			world.theChunkProviderServer.provideChunk(i >> 4, k >> 4);
			int j = waypoint.getYCoord(world, i, k);
			Entity playerMount = entityplayer.ridingEntity;
			entityplayer.mountEntity(null);
			entityplayer.setPositionAndUpdate(i + 0.5, j, k + 0.5);
			entityplayer.fallDistance = 0.0f;
			if (playerMount instanceof EntityLiving) {
				playerMount = this.fastTravelEntity(world, (EntityLiving) playerMount, i, j, k);
			}
			if (playerMount != null) {
				setUUIDToMount(playerMount.getUniqueID());
			}
			for (EntityLiving entity : entitiesToTransport) {
				Entity mount = entity.ridingEntity;
				entity.mountEntity(null);
				entity = this.fastTravelEntity(world, entity, i, j, k);
				if (!(mount instanceof EntityLiving)) {
					continue;
				}
				mount = this.fastTravelEntity(world, (EntityLiving) mount, i, j, k);
				entity.mountEntity(mount);
			}
			sendFTPacket(entityplayer, waypoint, startX, startZ);
			setTimeSinceFT(0);
			incrementWPUseCount(waypoint);
			if (waypoint instanceof LOTRWaypoint) {
				lastWaypoint = (LOTRWaypoint) waypoint;
				markDirty();
			}
			if (waypoint instanceof LOTRCustomWaypoint) {
				LOTRCustomWaypointLogger.logTravel((EntityPlayer) entityplayer, (LOTRCustomWaypoint) waypoint);
			}
		}
	}

    private void sendFTPacket(EntityPlayerMP entityplayer, LOTRAbstractWaypoint waypoint, int startX, int startZ) {
        LOTRPacketFTScreen packet = new LOTRPacketFTScreen(waypoint, startX, startZ);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    private <T extends EntityLiving> T fastTravelEntity(WorldServer world, T entity, int i, int j, int k) {
        String entityID = EntityList.getEntityString(entity);
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        entity.setDead();
        EntityLiving newEntity = (EntityLiving)EntityList.createEntityByName(entityID, world);
        newEntity.readFromNBT(nbt);
        newEntity.setLocationAndAngles(i + 0.5, j, k + 0.5, newEntity.rotationYaw, newEntity.rotationPitch);
        newEntity.fallDistance = 0.0f;
        newEntity.getNavigator().clearPathEntity();
        newEntity.setAttackTarget(null);
        world.spawnEntityInWorld(newEntity);
        world.updateEntityWithOptionalForce(newEntity, false);
        return (T)newEntity;
    }

    public boolean canFastTravel() {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null) {
            World world = entityplayer.worldObj;
            if (!entityplayer.capabilities.isCreativeMode) {
                double range = 16.0;
                List entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
                for (int l = 0; l < entities.size(); ++l) {
                    EntityLiving entityliving = (EntityLiving)entities.get(l);
                    if (entityliving.getAttackTarget() != entityplayer) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int getTimeSinceFT() {
        return this.ftSinceTick;
    }

    public void setTimeSinceFT(int i) {
        this.setTimeSinceFT(i, false);
    }

    public void setTimeSinceFTWithUpdate(int i) {
        this.setTimeSinceFT(i, true);
    }

    private void setTimeSinceFT(int i, boolean forceUpdate) {
        boolean bigChange;
        EntityPlayer entityplayer;
        int preTick = this.ftSinceTick;
        this.ftSinceTick = i = Math.max(0, i);
        bigChange = (this.ftSinceTick == 0 || preTick == 0) && this.ftSinceTick != preTick || preTick < 0 && this.ftSinceTick >= 0;
        if (bigChange || LOTRPlayerData.isTimerAutosaveTick() || forceUpdate) {
            this.markDirty();
        }
        if ((bigChange || this.ftSinceTick % 5 == 0 || forceUpdate) && (entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketFTTimer packet = new LOTRPacketFTTimer(this.ftSinceTick);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    private void setUUIDToMount(UUID uuid) {
        this.uuidToMount = uuid;
        this.uuidToMountTime = this.uuidToMount != null ? 10 : 0;
        this.markDirty();
    }

    public LOTRAbstractWaypoint getTargetFTWaypoint() {
        return this.targetFTWaypoint;
    }

    public void setTargetFTWaypoint(LOTRAbstractWaypoint wp) {
        this.targetFTWaypoint = wp;
        this.markDirty();
        if (wp != null) {
            this.setTicksUntilFT(ticksUntilFT_max);
        } else {
            this.setTicksUntilFT(0);
        }
    }

    public int getTicksUntilFT() {
        return this.ticksUntilFT;
    }

    public void setTicksUntilFT(int i) {
        if (this.ticksUntilFT != i) {
            this.ticksUntilFT = i;
            if (this.ticksUntilFT == ticksUntilFT_max || this.ticksUntilFT == 0) {
                this.markDirty();
            }
        }
    }

    public void cancelFastTravel() {
        if (this.targetFTWaypoint != null) {
            this.setTargetFTWaypoint(null);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.motion"));
            }
        }
    }

    public boolean isFTRegionUnlocked(LOTRWaypoint.Region region) {
        return this.unlockedFTRegions.contains(region);
    }

    public void unlockFTRegion(LOTRWaypoint.Region region) {
        if (this.isSiegeActive()) {
            return;
        }
        if (this.unlockedFTRegions.add(region)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, true);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }

    public void lockFTRegion(LOTRWaypoint.Region region) {
        if (this.unlockedFTRegions.remove(region)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, false);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }

    public List<LOTRAbstractWaypoint> getAllAvailableWaypoints() {
        ArrayList<LOTRAbstractWaypoint> waypoints = new ArrayList<>(LOTRWaypoint.listAllWaypoints());
        waypoints.addAll(this.getCustomWaypoints());
        waypoints.addAll(this.customWaypointsShared);
        return waypoints;
    }

    public List<LOTRCustomWaypoint> getCustomWaypoints() {
        return this.customWaypoints;
    }

    public LOTRCustomWaypoint getCustomWaypointByID(int ID) {
        for (LOTRCustomWaypoint waypoint : this.customWaypoints) {
            if (waypoint.getID() != ID) continue;
            return waypoint;
        }
        return null;
    }

    public void addCustomWaypoint(LOTRCustomWaypoint waypoint) {
        this.customWaypoints.add(waypoint);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketCreateCWPClient packet = waypoint.getClientPacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
            List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
            for (UUID player : sharedPlayers) {
                LOTRLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
            }
            LOTRCustomWaypointLogger.logCreate(entityplayer, waypoint);
        }
    }

    public void removeCustomWaypoint(LOTRCustomWaypoint waypoint) {
        if (this.customWaypoints.remove(waypoint)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
                LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
                List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
                for (UUID player : sharedPlayers) {
                    LOTRLevelData.getData(player).removeSharedCustomWaypoint(shareCopy);
                }
                LOTRCustomWaypointLogger.logDelete(entityplayer, waypoint);
            }
        }
    }

    public void renameCustomWaypoint(LOTRCustomWaypoint waypoint, String newName) {
        waypoint.rename(newName);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
            List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
            for (UUID player : sharedPlayers) {
                LOTRLevelData.getData(player).renameSharedCustomWaypoint(shareCopy, newName);
            }
            LOTRCustomWaypointLogger.logRename(entityplayer, waypoint);
        }
    }

    public void customWaypointAddSharedFellowship(LOTRCustomWaypoint waypoint, LOTRFellowship fs) {
        UUID fsID = fs.getFellowshipID();
        waypoint.addSharedFellowship(fsID);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketShareCWPClient packet = waypoint.getClientAddFellowshipPacket(fsID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
        for (UUID player : fs.getAllPlayerUUIDs()) {
            if (player.equals(this.playerUUID)) continue;
            LOTRLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
        }
    }

    public void customWaypointAddSharedFellowshipClient(LOTRCustomWaypoint waypoint, LOTRFellowshipClient fs) {
        waypoint.addSharedFellowship(fs.getFellowshipID());
    }

    public void customWaypointRemoveSharedFellowship(LOTRCustomWaypoint waypoint, LOTRFellowship fs) {
        UUID fsID = fs.getFellowshipID();
        waypoint.removeSharedFellowship(fsID);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketShareCWPClient packet = waypoint.getClientRemoveFellowshipPacket(fsID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
        for (UUID player : fs.getAllPlayerUUIDs()) {
            if (player.equals(this.playerUUID)) continue;
            LOTRPlayerData pd = LOTRLevelData.getData(player);
            pd.addOrUpdateSharedCustomWaypoint(shareCopy);
            pd.checkCustomWaypointsSharedBy(ImmutableList.of(this.playerUUID));
        }
    }

    public void customWaypointRemoveSharedFellowshipClient(LOTRCustomWaypoint waypoint, UUID fsID) {
        waypoint.removeSharedFellowship(fsID);
    }

    public int getMaxCustomWaypoints() {
        int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
        return 5 + achievements / 5;
    }

    public LOTRCustomWaypoint getSharedCustomWaypointByID(UUID owner, int id) {
        for (LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
            if (!waypoint.getSharingPlayerID().equals(owner) || waypoint.getID() != id) continue;
            return waypoint;
        }
        return null;
    }

    public void addOrUpdateSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to cache a shared custom waypoint with no owner!");
            return;
        }
        if (waypoint.getSharingPlayerID().equals(this.playerUUID)) {
            FMLLog.warning("LOTR: Warning! Tried to share a custom waypoint to its own player (%s)!", this.playerUUID.toString());
            return;
        }
        CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        if (this.cwpSharedUnlocked.contains(key)) {
            waypoint.setSharedUnlocked();
        }
        waypoint.setSharedHidden(this.cwpSharedHidden.contains(key));
        LOTRCustomWaypoint existing = this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
        if (existing == null) {
            this.customWaypointsShared.add(waypoint);
        } else {
            if (existing.isSharedUnlocked()) {
                waypoint.setSharedUnlocked();
            }
            waypoint.setSharedHidden(existing.isSharedHidden());
            int i = this.customWaypointsShared.indexOf(existing);
            this.customWaypointsShared.set(i, waypoint);
        }
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketCreateCWPClient packet = waypoint.getClientPacketShared();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void removeSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint with no owner!");
            return;
        }
        LOTRCustomWaypoint existing;
        existing = this.customWaypointsShared.contains(waypoint) ? waypoint : this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
        if (existing != null) {
            this.customWaypointsShared.remove(existing);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacketShared();
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        } else {
            FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint that does not exist!");
        }
    }

    public void renameSharedCustomWaypoint(LOTRCustomWaypoint waypoint, String newName) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to rename a shared custom waypoint with no owner!");
            return;
        }
        waypoint.rename(newName);
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacketShared();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void unlockSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!");
            return;
        }
        waypoint.setSharedUnlocked();
        CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        this.cwpSharedUnlocked.add(key);
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketCWPSharedUnlockClient packet = waypoint.getClientSharedUnlockPacket();
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void hideOrUnhideSharedCustomWaypoint(LOTRCustomWaypoint waypoint, boolean hide) {
        if (!waypoint.isShared()) {
            FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!");
            return;
        }
        waypoint.setSharedHidden(hide);
        CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
        if (hide) {
            this.cwpSharedHidden.add(key);
        } else {
            this.cwpSharedHidden.remove(key);
        }
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketCWPSharedHideClient packet = waypoint.getClientSharedHidePacket(hide);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public int getWaypointFTTime(LOTRAbstractWaypoint wp, EntityPlayer entityplayer) {
        int baseMin = LOTRLevelData.getWaypointCooldownMin();
        int baseMax = LOTRLevelData.getWaypointCooldownMax();
        int useCount = this.getWPUseCount(wp);
        double dist = entityplayer.getDistance(wp.getXCoord() + 0.5, wp.getYCoordSaved(), wp.getZCoord() + 0.5);
        double time = baseMin;
        double added = (baseMax - baseMin) * Math.pow(0.9, useCount);
        time += added;
        int seconds = (int)Math.round(time *= Math.max(1.0, dist * 1.2E-5));
        seconds = Math.max(seconds, 0);
        return seconds * 20;
    }

    public int getWPUseCount(LOTRAbstractWaypoint wp) {
        if (wp instanceof LOTRCustomWaypoint) {
            LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
            int ID = cwp.getID();
            if (cwp.isShared()) {
                UUID sharingPlayer = cwp.getSharingPlayerID();
                CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
                if (this.cwpSharedUseCounts.containsKey(key)) {
                    return this.cwpSharedUseCounts.get(key);
                }
            } else if (this.cwpUseCounts.containsKey(ID)) {
                return this.cwpUseCounts.get(ID);
            }
        } else if (this.wpUseCounts.containsKey(wp)) {
            return this.wpUseCounts.get(wp);
        }
        return 0;
    }

    public void setWPUseCount(LOTRAbstractWaypoint wp, int count) {
        if (wp instanceof LOTRCustomWaypoint) {
            LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
            int ID = cwp.getID();
            if (cwp.isShared()) {
                UUID sharingPlayer = cwp.getSharingPlayerID();
                CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
                this.cwpSharedUseCounts.put(key, count);
            } else {
                this.cwpUseCounts.put(ID, count);
            }
        } else {
            this.wpUseCounts.put((LOTRWaypoint)wp, count);
        }
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketWaypointUseCount packet = new LOTRPacketWaypointUseCount(wp, count);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void incrementWPUseCount(LOTRAbstractWaypoint wp) {
        this.setWPUseCount(wp, this.getWPUseCount(wp) + 1);
    }

    public int getNextCwpID() {
        return this.nextCwpID;
    }

    public void incrementNextCwpID() {
        ++this.nextCwpID;
        this.markDirty();
    }

    private void addSharedCustomWaypointsFromAllFellowships() {
        this.addSharedCustomWaypointsFrom(null, null);
    }

    private void addSharedCustomWaypointsFromAllIn(UUID onlyOneFellowshipID) {
        this.addSharedCustomWaypointsFrom(onlyOneFellowshipID, null);
    }

    private void addSharedCustomWaypointsFrom(UUID onlyOneFellowshipID, List<UUID> checkSpecificPlayers) {
        List<UUID> checkFellowshipIDs;
        if (onlyOneFellowshipID != null) {
            checkFellowshipIDs = new ArrayList<>();
            checkFellowshipIDs.add(onlyOneFellowshipID);
        } else {
            checkFellowshipIDs = this.fellowshipIDs;
        }
        ArrayList<UUID> checkFellowPlayerIDs = new ArrayList<>();
        if (checkSpecificPlayers != null) {
            for (UUID player : checkSpecificPlayers) {
                if (player.equals(this.playerUUID)) continue;
                checkFellowPlayerIDs.add(player);
            }
        } else {
            for (UUID fsID : checkFellowshipIDs) {
                LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
                if (fs == null) continue;
                List<UUID> playerIDs = fs.getAllPlayerUUIDs();
                for (UUID player : playerIDs) {
                    if (player.equals(this.playerUUID) || checkFellowPlayerIDs.contains(player)) continue;
                    checkFellowPlayerIDs.add(player);
                }
            }
        }
        for (UUID player : checkFellowPlayerIDs) {
            LOTRPlayerData pd = LOTRLevelData.getData(player);
            List<LOTRCustomWaypoint> cwps = pd.getCustomWaypoints();
            for (LOTRCustomWaypoint waypoint : cwps) {
                boolean inSharedFellowship = false;
                for (UUID fsID : checkFellowshipIDs) {
                    if (!waypoint.hasSharedFellowship(fsID)) continue;
                    inSharedFellowship = true;
                    break;
                }
                if (!inSharedFellowship) continue;
                this.addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player));
            }
        }
    }

    private void checkCustomWaypointsSharedFromFellowships() {
        this.checkCustomWaypointsSharedBy(null);
    }

    private void checkCustomWaypointsSharedBy(List<UUID> checkSpecificPlayers) {
        ArrayList<LOTRCustomWaypoint> removes = new ArrayList<>();
        for (LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
            LOTRCustomWaypoint wpOriginal;
            UUID waypointSharingPlayer = waypoint.getSharingPlayerID();
            if (checkSpecificPlayers != null && !checkSpecificPlayers.contains(waypointSharingPlayer) || (wpOriginal = LOTRLevelData.getData(waypointSharingPlayer).getCustomWaypointByID(waypoint.getID())) == null || (wpOriginal.getPlayersInAllSharedFellowships()).contains(this.playerUUID)) continue;
            removes.add(waypoint);
        }
        for (LOTRCustomWaypoint waypoint : removes) {
            this.removeSharedCustomWaypoint(waypoint);
        }
    }

    private void unshareFellowshipFromOwnCustomWaypoints(LOTRFellowship fs) {
        for (LOTRCustomWaypoint waypoint : this.customWaypoints) {
            if (!waypoint.hasSharedFellowship(fs)) continue;
            this.customWaypointRemoveSharedFellowship(waypoint, fs);
        }
    }

    private void unlockSharedCustomWaypoints(EntityPlayer entityplayer) {
        if (this.pdTick % 20 == 0 && entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            ArrayList<LOTRCustomWaypoint> unlockWaypoints = new ArrayList<>();
            for (LOTRCustomWaypoint waypoint : this.customWaypointsShared) {
                if (!waypoint.isShared() || waypoint.isSharedUnlocked() || !waypoint.canUnlockShared(entityplayer)) continue;
                unlockWaypoints.add(waypoint);
            }
            for (LOTRCustomWaypoint waypoint : unlockWaypoints) {
                this.unlockSharedCustomWaypoint(waypoint);
            }
        }
    }

    public List<UUID> getFellowshipIDs() {
        return this.fellowshipIDs;
    }

    public List<LOTRFellowship> getFellowships() {
        ArrayList<LOTRFellowship> fellowships = new ArrayList<>();
        for (UUID fsID : this.fellowshipIDs) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded()) continue;
            fellowships.add(fs);
        }
        return fellowships;
    }

    public LOTRFellowship getFellowshipByName(String fsName) {
        for (UUID fsID : this.fellowshipIDs) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded() || !fs.getName().equalsIgnoreCase(fsName)) continue;
            return fs;
        }
        return null;
    }

    public List<String> listAllFellowshipNames() {
        ArrayList<String> list = new ArrayList<>();
        for (UUID fsID : this.fellowshipIDs) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded() || !fs.containsPlayer(this.playerUUID)) continue;
            list.add(fs.getName());
        }
        return list;
    }

    public List<String> listAllLeadingFellowshipNames() {
        ArrayList<String> list = new ArrayList<>();
        for (UUID fsID : this.fellowshipIDs) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded() || !fs.isOwner(this.playerUUID)) continue;
            list.add(fs.getName());
        }
        return list;
    }

    public void addFellowship(LOTRFellowship fs) {
        UUID fsID;
        if (fs.containsPlayer(this.playerUUID) && !this.fellowshipIDs.contains(fsID = fs.getFellowshipID())) {
            this.fellowshipIDs.add(fsID);
            this.markDirty();
            this.sendFellowshipPacket(fs);
            this.addSharedCustomWaypointsFromAllIn(fs.getFellowshipID());
        }
    }

    public void removeFellowship(LOTRFellowship fs) {
        UUID fsID;
        if ((fs.isDisbanded() || !fs.containsPlayer(this.playerUUID)) && this.fellowshipIDs.contains(fsID = fs.getFellowshipID())) {
            this.fellowshipIDs.remove(fsID);
            this.markDirty();
            this.sendFellowshipRemovePacket(fs);
            this.unshareFellowshipFromOwnCustomWaypoints(fs);
            this.checkCustomWaypointsSharedFromFellowships();
        }
    }

    public void updateFellowship(LOTRFellowship fs, FellowshipUpdateType updateType) {
        List<UUID> playersToCheckSharedWaypointsFrom;
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            IMessage updatePacket = updateType.createUpdatePacket(this, fs);
            if (updatePacket != null) {
                LOTRPacketHandler.networkWrapper.sendTo(updatePacket, (EntityPlayerMP)entityplayer);
            } else {
                LOTRLog.logger.error("No associated packet for fellowship update type " + updateType.getClass().getName());
            }
        }
        if ((playersToCheckSharedWaypointsFrom = updateType.getPlayersToCheckSharedWaypointsFrom(fs)) != null && !playersToCheckSharedWaypointsFrom.isEmpty()) {
            this.addSharedCustomWaypointsFrom(fs.getFellowshipID(), playersToCheckSharedWaypointsFrom);
            this.checkCustomWaypointsSharedBy(playersToCheckSharedWaypointsFrom);
        }
    }

    public void createFellowship(String name, boolean normalCreation) {
        if ((normalCreation && (!LOTRConfig.enableFellowshipCreation || !this.canCreateFellowships(false)))) {
            return;
        }
        if (!this.anyMatchingFellowshipNames(name, false)) {
            LOTRFellowship fellowship = new LOTRFellowship(this.playerUUID, name);
            fellowship.createAndRegister();
        }
    }

    public boolean canCreateFellowships(boolean client) {
        int max = this.getMaxLeadingFellowships();
        int leading = 0;
        if (client) {
            for (LOTRFellowshipClient fs : this.fellowshipsClient) {
                if (!fs.isOwned() || ++leading < max) continue;
                return false;
            }
        } else {
            for (UUID fsID : this.fellowshipIDs) {
                LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
                if (fs == null || fs.isDisbanded() || !fs.isOwner(this.playerUUID) || ++leading < max) continue;
                return false;
            }
        }
        return leading < max;
    }

    private int getMaxLeadingFellowships() {
        int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
        return 1 + achievements / 20;
    }

    public void disbandFellowship(LOTRFellowship fs) {
        if (fs.isOwner(this.playerUUID)) {
            ArrayList<UUID> memberUUIDs = new ArrayList<>(fs.getMemberUUIDs());
            fs.disband();
            this.removeFellowship(fs);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                for (UUID memberID : memberUUIDs) {
                    EntityPlayer member = this.getOtherPlayer(memberID);
                    if (member == null) continue;
                    fs.sendNotification(member, "lotr.gui.fellowships.notifyDisband", entityplayer.getCommandSenderName());
                }
            }
        }
    }

    public void invitePlayerToFellowship(LOTRFellowship fs, UUID player) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            LOTRLevelData.getData(player).addFellowshipInvite(fs, this.playerUUID);
        }
    }

    public void removePlayerFromFellowship(LOTRFellowship fs, UUID player) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            EntityPlayer removed;
            fs.removeMember(player);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote && (removed = this.getOtherPlayer(player)) != null) {
                fs.sendNotification(removed, "lotr.gui.fellowships.notifyRemove", entityplayer.getCommandSenderName());
            }
        }
    }

    public void transferFellowship(LOTRFellowship fs, UUID player) {
        if (fs.isOwner(this.playerUUID)) {
            EntityPlayer newOwner;
            fs.setOwner(player);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote && (newOwner = this.getOtherPlayer(player)) != null) {
                fs.sendNotification(newOwner, "lotr.gui.fellowships.notifyTransfer", entityplayer.getCommandSenderName());
            }
        }
    }

    public void setFellowshipAdmin(LOTRFellowship fs, UUID player, boolean flag) {
        if (fs.isOwner(this.playerUUID)) {
            EntityPlayer otherPlayer;
            fs.setAdmin(player, flag);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote && (otherPlayer = this.getOtherPlayer(player)) != null) {
                if (flag) {
                    fs.sendNotification(otherPlayer, "lotr.gui.fellowships.notifyOp", entityplayer.getCommandSenderName());
                } else {
                    fs.sendNotification(otherPlayer, "lotr.gui.fellowships.notifyDeop", entityplayer.getCommandSenderName());
                }
            }
        }
    }

    public void renameFellowship(LOTRFellowship fs, String name) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setName(name);
        }
    }

    public void setFellowshipIcon(LOTRFellowship fs, ItemStack itemstack) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setIcon(itemstack);
        }
    }

    public void setFellowshipPreventPVP(LOTRFellowship fs, boolean prevent) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setPreventPVP(prevent);
        }
    }

    public void setFellowshipPreventHiredFF(LOTRFellowship fs, boolean prevent) {
        if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
            fs.setPreventHiredFriendlyFire(prevent);
        }
    }

    public void setFellowshipShowMapLocations(LOTRFellowship fs, boolean show) {
        if (fs.isOwner(this.playerUUID)) {
            fs.setShowMapLocations(show);
        }
    }

    public void leaveFellowship(LOTRFellowship fs) {
        if (!fs.isOwner(this.playerUUID)) {
            EntityPlayer entityplayer;
            EntityPlayer owner;
            fs.removeMember(this.playerUUID);
            if (this.fellowshipIDs.contains(fs.getFellowshipID())) {
                this.removeFellowship(fs);
            }
            if ((entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote && (owner = this.getOtherPlayer(fs.getOwner())) != null) {
                fs.sendNotification(owner, "lotr.gui.fellowships.notifyLeave", entityplayer.getCommandSenderName());
            }
        }
    }

    private void sendFellowshipPacket(LOTRFellowship fs) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, false);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    private void sendFellowshipRemovePacket(LOTRFellowship fs) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, false);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public List<LOTRFellowshipClient> getClientFellowships() {
        return this.fellowshipsClient;
    }

    public boolean anyMatchingFellowshipNames(String name, boolean client) {
        name = StringUtils.strip(name).toLowerCase();
        if (client) {
            for (LOTRFellowshipClient fs : this.fellowshipsClient) {
                String otherName = fs.getName();
                if (!name.equals(otherName = StringUtils.strip(otherName).toLowerCase())) continue;
                return true;
            }
        } else {
            for (UUID fsID : this.fellowshipIDs) {
                LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
                if (fs == null || fs.isDisbanded()) continue;
                String otherName = fs.getName();
                if (!name.equals(otherName = StringUtils.strip(otherName).toLowerCase())) continue;
                return true;
            }
        }
        return false;
    }

    public void addOrUpdateClientFellowship(LOTRFellowshipClient fs) {
        UUID fsID = fs.getFellowshipID();
        LOTRFellowshipClient inList = null;
        for (LOTRFellowshipClient fsInList : this.fellowshipsClient) {
            if (!fsInList.getFellowshipID().equals(fsID)) continue;
            inList = fsInList;
            break;
        }
        if (inList != null) {
            inList.updateDataFrom(fs);
        } else {
            this.fellowshipsClient.add(fs);
        }
    }

    public void removeClientFellowship(UUID fsID) {
        LOTRFellowshipClient inList = null;
        for (LOTRFellowshipClient fsInList : this.fellowshipsClient) {
            if (!fsInList.getFellowshipID().equals(fsID)) continue;
            inList = fsInList;
            break;
        }
        if (inList != null) {
            this.fellowshipsClient.remove(inList);
        }
    }

    public LOTRFellowshipClient getClientFellowshipByName(String fsName) {
        for (LOTRFellowshipClient fs : this.fellowshipsClient) {
            if (!fs.getName().equalsIgnoreCase(fsName)) continue;
            return fs;
        }
        return null;
    }

    public LOTRFellowshipClient getClientFellowshipByID(UUID fsID) {
        for (LOTRFellowshipClient fs : this.fellowshipsClient) {
            if (!fs.getFellowshipID().equals(fsID)) continue;
            return fs;
        }
        return null;
    }

    public void addFellowshipInvite(LOTRFellowship fs, UUID inviterUUID) {
        UUID fsID = fs.getFellowshipID();
        boolean contains = false;
        for (LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (!invite.fellowshipID.equals(fsID)) continue;
            contains = true;
            break;
        }
        if (!contains) {
            EntityPlayer inviter;
            this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID, inviterUUID));
            this.markDirty();
            this.sendFellowshipInvitePacket(fs);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote && (inviter = this.getOtherPlayer(inviterUUID)) != null) {
                fs.sendNotification(entityplayer, "lotr.gui.fellowships.notifyInvite", inviter.getCommandSenderName());
            }
        }
    }

    public void acceptFellowshipInvite(LOTRFellowship fs) {
        UUID fsID = fs.getFellowshipID();
        LOTRFellowshipInvite existingInvite = null;
        for (LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (!invite.fellowshipID.equals(fsID)) continue;
            existingInvite = invite;
            break;
        }
        if (existingInvite != null) {
            EntityPlayer entityplayer;
            if (!fs.isDisbanded()) {
                fs.addMember(this.playerUUID);
            }
            this.fellowshipInvites.remove(existingInvite);
            this.markDirty();
            this.sendFellowshipInviteRemovePacket(fs);
            if (!fs.isDisbanded() && (entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
                EntityPlayer inviter;
                UUID inviterID = existingInvite.inviterID;
                if (inviterID == null) {
                    inviterID = fs.getOwner();
                }
                if ((inviter = this.getOtherPlayer(inviterID)) != null) {
                    fs.sendNotification(inviter, "lotr.gui.fellowships.notifyAccept", entityplayer.getCommandSenderName());
                }
            }
        }
    }

    public void rejectFellowshipInvite(LOTRFellowship fs) {
        UUID fsID = fs.getFellowshipID();
        LOTRFellowshipInvite existingInvite = null;
        for (LOTRFellowshipInvite invite : this.fellowshipInvites) {
            if (!invite.fellowshipID.equals(fsID)) continue;
            existingInvite = invite;
            break;
        }
        if (existingInvite != null) {
            this.fellowshipInvites.remove(existingInvite);
            this.markDirty();
            this.sendFellowshipInviteRemovePacket(fs);
        }
    }

    private void sendFellowshipInvitePacket(LOTRFellowship fs) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, true);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    private void sendFellowshipInviteRemovePacket(LOTRFellowship fs) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, true);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public List<LOTRFellowshipClient> getClientFellowshipInvites() {
        return this.fellowshipInvitesClient;
    }

    public void addOrUpdateClientFellowshipInvite(LOTRFellowshipClient fs) {
        UUID fsID = fs.getFellowshipID();
        LOTRFellowshipClient inList = null;
        for (LOTRFellowshipClient fsInList : this.fellowshipInvitesClient) {
            if (!fsInList.getFellowshipID().equals(fsID)) continue;
            inList = fsInList;
            break;
        }
        if (inList != null) {
            inList.updateDataFrom(fs);
        } else {
            this.fellowshipInvitesClient.add(fs);
        }
    }

    public void removeClientFellowshipInvite(UUID fsID) {
        LOTRFellowshipClient inList = null;
        for (LOTRFellowshipClient fsInList : this.fellowshipInvitesClient) {
            if (!fsInList.getFellowshipID().equals(fsID)) continue;
            inList = fsInList;
            break;
        }
        if (inList != null) {
            this.fellowshipInvitesClient.remove(inList);
        }
    }

    public UUID getChatBoundFellowshipID() {
        return this.chatBoundFellowshipID;
    }

    public LOTRFellowship getChatBoundFellowship() {
        LOTRFellowship fs;
        if (this.chatBoundFellowshipID != null && (fs = LOTRFellowshipData.getFellowship(this.chatBoundFellowshipID)) != null) {
            return fs;
        }
        return null;
    }

    public void setChatBoundFellowshipID(UUID fsID) {
        this.chatBoundFellowshipID = fsID;
        this.markDirty();
    }

    public void setChatBoundFellowship(LOTRFellowship fs) {
        this.setChatBoundFellowshipID(fs.getFellowshipID());
    }

    public void setSiegeActive(int duration) {
        this.siegeActiveTime = Math.max(this.siegeActiveTime, duration);
    }

    public boolean isSiegeActive() {
        return this.siegeActiveTime > 0;
    }

    public LOTRFaction getViewingFaction() {
        return this.viewingFaction;
    }

    public void setViewingFaction(LOTRFaction faction) {
        if (faction != null) {
            this.viewingFaction = faction;
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketUpdateViewingFaction packet = new LOTRPacketUpdateViewingFaction(this.viewingFaction);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }

    public LOTRFaction getRegionLastViewedFaction(LOTRDimension.DimensionRegion region) {
        LOTRFaction fac = this.prevRegionFactions.get(region);
        if (fac == null) {
            fac = region.factionList.get(0);
            this.prevRegionFactions.put(region, fac);
        }
        return fac;
    }

    public void setRegionLastViewedFaction(LOTRDimension.DimensionRegion region, LOTRFaction fac) {
        if (region.factionList.contains(fac)) {
            this.prevRegionFactions.put(region, fac);
            this.markDirty();
        }
    }

    public boolean showWaypoints() {
        return this.showWaypoints;
    }

    public void setShowWaypoints(boolean flag) {
        this.showWaypoints = flag;
        this.markDirty();
    }

    public boolean showCustomWaypoints() {
        return this.showCustomWaypoints;
    }

    public void setShowCustomWaypoints(boolean flag) {
        this.showCustomWaypoints = flag;
        this.markDirty();
    }

    public boolean showHiddenSharedWaypoints() {
        return this.showHiddenSharedWaypoints;
    }

    public void setShowHiddenSharedWaypoints(boolean flag) {
        this.showHiddenSharedWaypoints = flag;
        this.markDirty();
    }

    public boolean getHideMapLocation() {
        return this.hideOnMap;
    }

    public void setHideMapLocation(boolean flag) {
        this.hideOnMap = flag;
        this.markDirty();
        this.sendOptionsPacket(3, flag);
    }

    public boolean getAdminHideMap() {
        return this.adminHideMap;
    }

    public void setAdminHideMap(boolean flag) {
        this.adminHideMap = flag;
        this.markDirty();
    }

    public boolean getEnableConquestKills() {
        return this.conquestKills;
    }

    public void setEnableConquestKills(boolean flag) {
        this.conquestKills = flag;
        this.markDirty();
        this.sendOptionsPacket(5, flag);
    }

    public boolean getTeleportedME() {
        return this.teleportedME;
    }

    public void setTeleportedME(boolean flag) {
        this.teleportedME = flag;
        this.markDirty();
    }

    public ChunkCoordinates getDeathPoint() {
        return this.deathPoint;
    }

    public void setDeathPoint(int i, int j, int k) {
        this.deathPoint = new ChunkCoordinates(i, j, k);
        this.markDirty();
    }

    public int getDeathDimension() {
        return this.deathDim;
    }

    public void setDeathDimension(int dim) {
        this.deathDim = dim;
        this.markDirty();
    }

    public int getAlcoholTolerance() {
        return this.alcoholTolerance;
    }

    public void setAlcoholTolerance(int i) {
        EntityPlayer entityplayer;
        this.alcoholTolerance = i;
        this.markDirty();
        if (this.alcoholTolerance >= 250 && (entityplayer = this.getPlayer()) != null && !entityplayer.worldObj.isRemote) {
            this.addAchievement(LOTRAchievement.gainHighAlcoholTolerance);
        }
    }

    public List<LOTRMiniQuest> getMiniQuests() {
        return this.miniQuests;
    }

    public List<LOTRMiniQuest> getMiniQuestsCompleted() {
        return this.miniQuestsCompleted;
    }

    public void addMiniQuest(LOTRMiniQuest quest) {
        this.miniQuests.add(quest);
        this.updateMiniQuest(quest);
    }

    public void addMiniQuestCompleted(LOTRMiniQuest quest) {
        this.miniQuestsCompleted.add(quest);
        this.markDirty();
    }

    public void removeMiniQuest(LOTRMiniQuest quest, boolean completed) {
        List<LOTRMiniQuest> removeList = completed ? this.miniQuestsCompleted : this.miniQuests;
        if (removeList.remove(quest)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, quest.isCompleted(), false);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        } else {
            FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
        }
    }

    public void updateMiniQuest(LOTRMiniQuest quest) {
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            try {
                this.sendMiniQuestPacket((EntityPlayerMP)entityplayer, quest, false);
            }
            catch (IOException e) {
                FMLLog.severe("Error sending miniquest packet to player " + entityplayer.getCommandSenderName());
                e.printStackTrace();
            }
        }
    }

    public void completeMiniQuest(LOTRMiniQuest quest) {
        if (this.miniQuests.remove(quest)) {
            this.addMiniQuestCompleted(quest);
            ++this.completedMiniquestCount;
            this.getFactionData(quest.entityFaction).completeMiniQuest();
            this.markDirty();
            LOTRMod.proxy.setTrackedQuest(quest);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.worldObj.isRemote) {
                LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, false, true);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        } else {
            FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
        }
    }

    private void sendMiniQuestPacket(EntityPlayerMP entityplayer, LOTRMiniQuest quest, boolean completed) throws IOException {
        NBTTagCompound nbt = new NBTTagCompound();
        quest.writeToNBT(nbt);
        LOTRPacketMiniquest packet = new LOTRPacketMiniquest(nbt, completed);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public LOTRMiniQuest getMiniQuestForID(UUID id, boolean completed) {
        ArrayList<LOTRMiniQuest> threadSafe = completed ? new ArrayList<>(this.miniQuestsCompleted) : new ArrayList<>(this.miniQuests);
        for (LOTRMiniQuest quest : threadSafe) {
            if (!quest.questUUID.equals(id)) continue;
            return quest;
        }
        return null;
    }

    public List<LOTRMiniQuest> getActiveMiniQuests() {
        return this.selectMiniQuests(new MiniQuestSelector.OptionalActive().setActiveOnly());
    }

    public List<LOTRMiniQuest> getMiniQuestsForEntity(LOTREntityNPC npc, boolean activeOnly) {
        return this.getMiniQuestsForEntityID(npc.getUniqueID(), activeOnly);
    }

    public List<LOTRMiniQuest> getMiniQuestsForEntityID(UUID npcID, boolean activeOnly) {
        MiniQuestSelector.EntityId sel = new MiniQuestSelector.EntityId(npcID);
        if (activeOnly) {
            sel.setActiveOnly();
        }
        return this.selectMiniQuests(sel);
    }

    public List<LOTRMiniQuest> getMiniQuestsForFaction(final LOTRFaction f, boolean activeOnly) {
        MiniQuestSelector.Faction sel = new MiniQuestSelector.Faction(new Supplier<LOTRFaction>(){

            public LOTRFaction get() {
                return f;
            }
        });
        if (activeOnly) {
            sel.setActiveOnly();
        }
        return this.selectMiniQuests(sel);
    }

    public List<LOTRMiniQuest> selectMiniQuests(MiniQuestSelector selector) {
        ArrayList<LOTRMiniQuest> ret = new ArrayList<>();
        ArrayList<LOTRMiniQuest> threadSafe = new ArrayList<>(this.miniQuests);
        for (LOTRMiniQuest quest : threadSafe) {
            if (!selector.include(quest)) continue;
            ret.add(quest);
        }
        return ret;
    }

    public int getCompletedMiniQuestsTotal() {
        return this.completedMiniquestCount;
    }

    public int getCompletedBountyQuests() {
        return this.completedBountyQuests;
    }

    public void addCompletedBountyQuest() {
        ++this.completedBountyQuests;
        this.markDirty();
    }

    public boolean hasActiveOrCompleteMQType(Class<? extends LOTRMiniQuest> type) {
        List<LOTRMiniQuest> quests = this.getMiniQuests();
        List<LOTRMiniQuest> questsComplete = this.getMiniQuestsCompleted();
        ArrayList<LOTRMiniQuest> allQuests = new ArrayList<>();
        for (LOTRMiniQuest q : quests) {
            if (!q.isActive()) continue;
            allQuests.add(q);
        }
        allQuests.addAll(questsComplete);
        for (LOTRMiniQuest q : allQuests) {
            if (!type.isAssignableFrom(q.getClass())) continue;
            return true;
        }
        return false;
    }

    public boolean hasAnyGWQuest() {
        return this.hasActiveOrCompleteMQType(LOTRMiniQuestWelcome.class);
    }

    public void distributeMQEvent(LOTRMiniQuestEvent event) {
        for (LOTRMiniQuest quest : this.miniQuests) {
            if (!quest.isActive()) continue;
            quest.handleEvent(event);
        }
    }

    public LOTRMiniQuest getTrackingMiniQuest() {
        if (this.trackingMiniQuestID == null) {
            return null;
        }
        return this.getMiniQuestForID(this.trackingMiniQuestID, false);
    }

    public void setTrackingMiniQuest(LOTRMiniQuest quest) {
        this.setTrackingMiniQuestID(quest == null ? null : quest.questUUID);
    }

    public void setTrackingMiniQuestID(UUID npcID) {
        this.trackingMiniQuestID = npcID;
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketMiniquestTrackClient packet = new LOTRPacketMiniquestTrackClient(this.trackingMiniQuestID);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public void placeBountyFor(LOTRFaction f) {
        this.bountiesPlaced.add(f);
        this.markDirty();
    }

    public LOTRWaypoint getLastKnownWaypoint() {
        return this.lastWaypoint;
    }

    public LOTRBiome getLastKnownBiome() {
        return this.lastBiome;
    }

    public void sendMessageIfNotReceived(LOTRGuiMessageTypes message) {
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            Boolean sent = this.sentMessageTypes.get(message);
            if (sent == null) {
                sent = false;
                this.sentMessageTypes.put(message, sent);
            }
            if (!sent.booleanValue()) {
                this.sentMessageTypes.put(message, true);
                this.markDirty();
                LOTRPacketMessage packet = new LOTRPacketMessage(message);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
        }
    }

    public LOTRTitle.PlayerTitle getPlayerTitle() {
        return this.playerTitle;
    }

    public void setPlayerTitle(LOTRTitle.PlayerTitle title) {
        this.playerTitle = title;
        this.markDirty();
        EntityPlayer entityplayer = this.getPlayer();
        if (entityplayer != null && !entityplayer.worldObj.isRemote) {
            LOTRPacketTitle packet = new LOTRPacketTitle(this.playerTitle);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
        for (UUID fsID : this.fellowshipIDs) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null) continue;
            fs.updateForAllMembers(new FellowshipUpdateType.UpdatePlayerTitle(this.playerUUID, this.playerTitle));
        }
    }

    public boolean getFemRankOverride() {
        return this.femRankOverride;
    }

    public void setFemRankOverride(boolean flag) {
        this.femRankOverride = flag;
        this.markDirty();
        this.sendOptionsPacket(4, flag);
    }

    public boolean useFeminineRanks() {
        if (this.femRankOverride) {
            return true;
        }
        if (this.playerTitle != null) {
            LOTRTitle title = this.playerTitle.getTitle();
            return title.isFeminineRank();
        }
        return false;
    }

    public LOTRPlayerQuestData getQuestData() {
        return this.questData;
    }

    private static class CWPSharedKey
    extends Pair<UUID, Integer> {
        public final UUID sharingPlayer;
        public final int waypointID;

        private CWPSharedKey(UUID player, int id) {
            this.sharingPlayer = player;
            this.waypointID = id;
        }

        public static CWPSharedKey keyFor(UUID player, int id) {
            return new CWPSharedKey(player, id);
        }

        public Integer setValue(Integer value) {
            throw new UnsupportedOperationException();
        }

        public UUID getLeft() {
            return this.sharingPlayer;
        }

        public Integer getRight() {
            return this.waypointID;
        }
    }

}

