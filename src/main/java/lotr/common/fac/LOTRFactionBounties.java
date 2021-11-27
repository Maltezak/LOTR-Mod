package lotr.common.fac;

import java.io.File;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;

public class LOTRFactionBounties {
    public static Map<LOTRFaction, LOTRFactionBounties> factionBountyMap = new HashMap<>();
    public static boolean needsLoad = true;
    public static final int KILL_RECORD_TIME = 3456000;
    public static final int BOUNTY_KILLED_TIME = 864000;
    public final LOTRFaction theFaction;
    private Map<UUID, PlayerData> playerList = new HashMap<>();
    private boolean needsSave = false;

    public static LOTRFactionBounties forFaction(LOTRFaction fac) {
        LOTRFactionBounties bounties = factionBountyMap.get(fac);
        if (bounties == null) {
            bounties = LOTRFactionBounties.loadFaction(fac);
            if (bounties == null) {
                bounties = new LOTRFactionBounties(fac);
            }
            factionBountyMap.put(fac, bounties);
        }
        return bounties;
    }

    public static void updateAll() {
        for (LOTRFactionBounties fb : factionBountyMap.values()) {
            fb.update();
        }
    }

    public LOTRFactionBounties(LOTRFaction f) {
        this.theFaction = f;
    }

    public PlayerData forPlayer(EntityPlayer entityplayer) {
        return this.forPlayer(entityplayer.getUniqueID());
    }

    public PlayerData forPlayer(UUID id) {
        PlayerData pd = this.playerList.get(id);
        if (pd == null) {
            pd = new PlayerData(this, id);
            this.playerList.put(id, pd);
        }
        return pd;
    }

    public void update() {
        for (PlayerData pd : this.playerList.values()) {
            pd.update();
        }
    }

    public List<PlayerData> findBountyTargets(int killAmount) {
        ArrayList<PlayerData> players = new ArrayList<>();
        for (PlayerData pd : this.playerList.values()) {
            if (pd.recentlyBountyKilled() || pd.getNumKills() < killAmount) continue;
            players.add(pd);
        }
        return players;
    }

    public void markDirty() {
        this.needsSave = true;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList playerTags = new NBTTagList();
        for (Map.Entry<UUID, PlayerData> e : this.playerList.entrySet()) {
            UUID id = e.getKey();
            PlayerData pd = e.getValue();
            if (!pd.shouldSave()) continue;
            NBTTagCompound playerData = new NBTTagCompound();
            playerData.setString("UUID", id.toString());
            pd.writeToNBT(playerData);
            playerTags.appendTag(playerData);
        }
        nbt.setTag("PlayerList", playerTags);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.playerList.clear();
        if (nbt.hasKey("PlayerList")) {
            NBTTagList playerTags = nbt.getTagList("PlayerList", 10);
            for (int i = 0; i < playerTags.tagCount(); ++i) {
                NBTTagCompound playerData = playerTags.getCompoundTagAt(i);
                UUID id = UUID.fromString(playerData.getString("UUID"));
                if (id == null) continue;
                PlayerData pd = new PlayerData(this, id);
                pd.readFromNBT(playerData);
                this.playerList.put(id, pd);
            }
        }
    }

    public static boolean anyDataNeedsSave() {
        for (LOTRFactionBounties fb : factionBountyMap.values()) {
            if (!fb.needsSave) continue;
            return true;
        }
        return false;
    }

    private static File getBountiesDir() {
        File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "factionbounties");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getFactionFile(LOTRFaction f, boolean findLegacy) {
        File defaultFile = new File(LOTRFactionBounties.getBountiesDir(), f.codeName() + ".dat");
        if (!findLegacy) {
            return defaultFile;
        }
        if (defaultFile.exists()) {
            return defaultFile;
        }
        for (String alias : f.listAliases()) {
            File aliasFile = new File(LOTRFactionBounties.getBountiesDir(), alias + ".dat");
            if (!aliasFile.exists()) continue;
            return aliasFile;
        }
        return defaultFile;
    }

    public static void saveAll() {
        try {
            for (LOTRFactionBounties fb : factionBountyMap.values()) {
                if (!fb.needsSave) continue;
                LOTRFactionBounties.saveFaction(fb);
                fb.needsSave = false;
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR faction bounty data");
            e.printStackTrace();
        }
    }

    public static void loadAll() {
        try {
            factionBountyMap.clear();
            needsLoad = false;
            LOTRFactionBounties.saveAll();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR faction bounty data");
            e.printStackTrace();
        }
    }

    private static LOTRFactionBounties loadFaction(LOTRFaction fac) {
        File file = LOTRFactionBounties.getFactionFile(fac, true);
        try {
            NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(file);
            if (nbt.hasNoTags()) {
                return null;
            }
            LOTRFactionBounties fb = new LOTRFactionBounties(fac);
            fb.readFromNBT(nbt);
            return fb;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR faction bounty data for %s", fac.codeName());
            e.printStackTrace();
            return null;
        }
    }

    private static void saveFaction(LOTRFactionBounties fb) {
        try {
            NBTTagCompound nbt = new NBTTagCompound();
            fb.writeToNBT(nbt);
            LOTRLevelData.saveNBTToFile(LOTRFactionBounties.getFactionFile(fb.theFaction, false), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR faction bounty data for %s", fb.theFaction.codeName());
            e.printStackTrace();
        }
    }

    public static class PlayerData {
        public final LOTRFactionBounties bountyList;
        public final UUID playerID;
        private String username;
        private List<KillRecord> killRecords = new ArrayList<>();
        private int recentBountyKilled;

        public PlayerData(LOTRFactionBounties b, UUID id) {
            this.bountyList = b;
            this.playerID = id;
        }

        public void recordNewKill() {
            this.killRecords.add(new KillRecord());
            this.markDirty();
        }

        public int getNumKills() {
            return this.killRecords.size();
        }

        public void recordBountyKilled() {
            this.recentBountyKilled = 864000;
            this.markDirty();
        }

        public boolean recentlyBountyKilled() {
            return this.recentBountyKilled > 0;
        }

        public void update() {
            boolean minorChanges = false;
            if (this.recentBountyKilled > 0) {
                --this.recentBountyKilled;
                minorChanges = true;
            }
            ArrayList<KillRecord> toRemove = new ArrayList<>();
            for (KillRecord kr : this.killRecords) {
                kr.timeElapsed--;
                if (kr.timeElapsed <= 0) {
                    toRemove.add(kr);
                }
                minorChanges = true;
            }
            if (!toRemove.isEmpty()) {
                this.killRecords.removeAll(toRemove);
                minorChanges = true;
            }
            if (minorChanges && MinecraftServer.getServer().getTickCounter() % 600 == 0) {
                this.markDirty();
            }
        }

        private void markDirty() {
            this.bountyList.markDirty();
        }

        public boolean shouldSave() {
            return !this.killRecords.isEmpty() || this.recentBountyKilled > 0;
        }

        public void writeToNBT(NBTTagCompound nbt) {
            NBTTagList recordTags = new NBTTagList();
            for (KillRecord kr : this.killRecords) {
                NBTTagCompound killData = new NBTTagCompound();
                kr.writeToNBT(killData);
                recordTags.appendTag(killData);
            }
            nbt.setTag("KillRecords", recordTags);
            nbt.setInteger("RecentBountyKilled", this.recentBountyKilled);
        }

        public void readFromNBT(NBTTagCompound nbt) {
            this.killRecords.clear();
            if (nbt.hasKey("KillRecords")) {
                NBTTagList recordTags = nbt.getTagList("KillRecords", 10);
                for (int i = 0; i < recordTags.tagCount(); ++i) {
                    NBTTagCompound killData = recordTags.getCompoundTagAt(i);
                    KillRecord kr = new KillRecord();
                    kr.readFromNBT(killData);
                    this.killRecords.add(kr);
                }
            }
            this.recentBountyKilled = nbt.getInteger("RecentBountyKilled");
        }

        public String findUsername() {
            if (this.username == null) {
                GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(this.playerID);
                if (profile == null || StringUtils.isBlank(profile.getName())) {
                    String name = UsernameCache.getLastKnownUsername(this.playerID);
                    if (name != null) {
                        profile = new GameProfile(this.playerID, name);
                    } else {
                        profile = new GameProfile(this.playerID, "");
                        MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
                    }
                }
                this.username = profile.getName();
            }
            return this.username;
        }

        private static class KillRecord {
            private int timeElapsed = 3456000;

            public void writeToNBT(NBTTagCompound nbt) {
                nbt.setInteger("Time", this.timeElapsed);
            }

            public void readFromNBT(NBTTagCompound nbt) {
                this.timeElapsed = nbt.getInteger("Time");
            }
        }

    }

}

