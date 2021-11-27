package lotr.common;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.fellowship.*;
import lotr.common.network.*;
import lotr.common.world.spawning.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.StatCollector;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public class LOTRLevelData {
    public static int madePortal;
    public static int madeMiddleEarthPortal;
    public static int overworldPortalX;
    public static int overworldPortalY;
    public static int overworldPortalZ;
    public static int middleEarthPortalX;
    public static int middleEarthPortalY;
    public static int middleEarthPortalZ;
    private static int structuresBanned;
    private static int waypointCooldownMax;
    private static int waypointCooldownMin;
    private static boolean gollumSpawned;
    private static boolean enableAlignmentZones;
    private static float conquestRate;
    public static boolean clientside_thisServer_feastMode;
    public static boolean clientside_thisServer_fellowshipCreation;
    public static boolean clientside_thisServer_enchanting;
    public static boolean clientside_thisServer_enchantingLOTR;
    public static boolean clientside_thisServer_strictFactionTitleRequirements;
    private static EnumDifficulty difficulty;
    private static boolean difficultyLock;
    private static Map<UUID, LOTRPlayerData> playerDataMap;
    public static boolean needsLoad;
    private static boolean needsSave;
    private static Random rand;

    public static void markDirty() {
        needsSave = true;
    }

    public static boolean anyDataNeedsSave() {
        if (needsSave) {
            return true;
        }
        if (LOTRSpawnDamping.needsSave) {
            return true;
        }
        for (LOTRPlayerData pd : playerDataMap.values()) {
            if (!pd.needsSave()) continue;
            return true;
        }
        return false;
    }

    public static File getOrCreateLOTRDir() {
        File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File getLOTRDat() {
        return new File(LOTRLevelData.getOrCreateLOTRDir(), "LOTR.dat");
    }

    private static File getLOTRPlayerDat(UUID player) {
        File playerDir = new File(LOTRLevelData.getOrCreateLOTRDir(), "players");
        if (!playerDir.exists()) {
            playerDir.mkdirs();
        }
        return new File(playerDir, player.toString() + ".dat");
    }

    public static NBTTagCompound loadNBTFromFile(File file) throws FileNotFoundException, IOException {
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(fis);
            fis.close();
            return nbt;
        }
        return new NBTTagCompound();
    }

    public static void saveNBTToFile(File file, NBTTagCompound nbt) throws FileNotFoundException, IOException {
        CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
    }

    public static void save() {
        try {
            if (needsSave) {
                File LOTR_dat = LOTRLevelData.getLOTRDat();
                if (!LOTR_dat.exists()) {
                    LOTRLevelData.saveNBTToFile(LOTR_dat, new NBTTagCompound());
                }
                NBTTagCompound levelData = new NBTTagCompound();
                levelData.setInteger("MadePortal", madePortal);
                levelData.setInteger("MadeMiddlePortal", madeMiddleEarthPortal);
                levelData.setInteger("OverworldX", overworldPortalX);
                levelData.setInteger("OverworldY", overworldPortalY);
                levelData.setInteger("OverworldZ", overworldPortalZ);
                levelData.setInteger("MiddleEarthX", middleEarthPortalX);
                levelData.setInteger("MiddleEarthY", middleEarthPortalY);
                levelData.setInteger("MiddleEarthZ", middleEarthPortalZ);
                levelData.setInteger("StructuresBanned", structuresBanned);
                levelData.setInteger("WpCdMax", waypointCooldownMax);
                levelData.setInteger("WpCdMin", waypointCooldownMin);
                levelData.setBoolean("GollumSpawned", gollumSpawned);
                levelData.setBoolean("AlignmentZones", enableAlignmentZones);
                levelData.setFloat("ConqRate", conquestRate);
                if (difficulty != null) {
                    levelData.setInteger("SavedDifficulty", difficulty.getDifficultyId());
                }
                levelData.setBoolean("DifficultyLock", difficultyLock);
                NBTTagCompound travellingTraderData = new NBTTagCompound();
                for (LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    trader.writeToNBT(nbt);
                    travellingTraderData.setTag(trader.entityClassName, nbt);
                }
                levelData.setTag("TravellingTraders", travellingTraderData);
                LOTRGreyWandererTracker.save(levelData);
                LOTRDate.saveDates(levelData);
                LOTRLevelData.saveNBTToFile(LOTR_dat, levelData);
                needsSave = false;
            }
            for (Map.Entry<UUID, LOTRPlayerData> e : playerDataMap.entrySet()) {
                UUID player = e.getKey();
                LOTRPlayerData pd = e.getValue();
                if (pd.needsSave()) {
                    LOTRLevelData.saveData(player);
                }
            }
            if (LOTRSpawnDamping.needsSave) {
                LOTRSpawnDamping.saveAll();
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR data");
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            NBTTagCompound levelData = LOTRLevelData.loadNBTFromFile(LOTRLevelData.getLOTRDat());
            File oldLOTRDat = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR.dat");
            if (oldLOTRDat.exists()) {
                levelData = LOTRLevelData.loadNBTFromFile(oldLOTRDat);
                oldLOTRDat.delete();
                if (levelData.hasKey("PlayerData")) {
                    NBTTagList playerDataTags = levelData.getTagList("PlayerData", 10);
                    for (int i = 0; i < playerDataTags.tagCount(); ++i) {
                        NBTTagCompound nbt = playerDataTags.getCompoundTagAt(i);
                        UUID player = UUID.fromString(nbt.getString("PlayerUUID"));
                        LOTRLevelData.saveNBTToFile(LOTRLevelData.getLOTRPlayerDat(player), nbt);
                    }
                }
            }
            madePortal = levelData.getInteger("MadePortal");
            madeMiddleEarthPortal = levelData.getInteger("MadeMiddlePortal");
            overworldPortalX = levelData.getInteger("OverworldX");
            overworldPortalY = levelData.getInteger("OverworldY");
            overworldPortalZ = levelData.getInteger("OverworldZ");
            middleEarthPortalX = levelData.getInteger("MiddleEarthX");
            middleEarthPortalY = levelData.getInteger("MiddleEarthY");
            middleEarthPortalZ = levelData.getInteger("MiddleEarthZ");
            structuresBanned = levelData.getInteger("StructuresBanned");
            waypointCooldownMax = levelData.hasKey("FastTravel") ? levelData.getInteger("FastTravel") / 20 : (levelData.hasKey("WpCdMax") ? levelData.getInteger("WpCdMax") : 1800);
            waypointCooldownMin = levelData.hasKey("FastTravelMin") ? levelData.getInteger("FastTravelMin") / 20 : (levelData.hasKey("WpCdMin") ? levelData.getInteger("WpCdMin") : 180);
            gollumSpawned = levelData.getBoolean("GollumSpawned");
            enableAlignmentZones = levelData.hasKey("AlignmentZones") ? levelData.getBoolean("AlignmentZones") : true;
            conquestRate = levelData.hasKey("ConqRate") ? levelData.getFloat("ConqRate") : 1.0f;
            if (levelData.hasKey("SavedDifficulty")) {
                int id = levelData.getInteger("SavedDifficulty");
                difficulty = EnumDifficulty.getDifficultyEnum(id);
                LOTRMod.proxy.setClientDifficulty(difficulty);
            } else {
                difficulty = null;
            }
            difficultyLock = levelData.getBoolean("DifficultyLock");
            NBTTagCompound travellingTraderData = levelData.getCompoundTag("TravellingTraders");
            for (LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
                NBTTagCompound nbt = travellingTraderData.getCompoundTag(trader.entityClassName);
                trader.readFromNBT(nbt);
            }
            LOTRGreyWandererTracker.load(levelData);
            LOTRLevelData.destroyAllPlayerData();
            LOTRDate.loadDates(levelData);
            LOTRSpawnDamping.loadAll();
            needsLoad = false;
            needsSave = true;
            LOTRLevelData.save();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR data");
            e.printStackTrace();
        }
    }

    public static void setMadePortal(int i) {
        madePortal = i;
        LOTRLevelData.markDirty();
    }

    public static void setMadeMiddleEarthPortal(int i) {
        madeMiddleEarthPortal = i;
        LOTRLevelData.markDirty();
    }

    public static void markOverworldPortalLocation(int i, int j, int k) {
        overworldPortalX = i;
        overworldPortalY = j;
        overworldPortalZ = k;
        LOTRLevelData.markDirty();
    }

    public static void markMiddleEarthPortalLocation(int i, int j, int k) {
        LOTRPacketPortalPos packet = new LOTRPacketPortalPos(i, j, k);
        LOTRPacketHandler.networkWrapper.sendToAll(packet);
        LOTRLevelData.markDirty();
    }

    public static void sendLoginPacket(EntityPlayerMP entityplayer) {
        LOTRPacketLogin packet = new LOTRPacketLogin();
        packet.ringPortalX = middleEarthPortalX;
        packet.ringPortalY = middleEarthPortalY;
        packet.ringPortalZ = middleEarthPortalZ;
        packet.ftCooldownMax = waypointCooldownMax;
        packet.ftCooldownMin = waypointCooldownMin;
        packet.difficulty = difficulty;
        packet.difficultyLocked = difficultyLock;
        packet.alignmentZones = enableAlignmentZones;
        packet.feastMode = LOTRConfig.canAlwaysEat;
        packet.fellowshipCreation = LOTRConfig.enableFellowshipCreation;
        packet.enchanting = LOTRConfig.enchantingVanilla;
        packet.enchantingLOTR = LOTRConfig.enchantingLOTR;
        packet.strictFactionTitleRequirements = LOTRConfig.strictFactionTitleRequirements;
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public static int getWaypointCooldownMax() {
        return waypointCooldownMax;
    }

    public static int getWaypointCooldownMin() {
        return waypointCooldownMin;
    }

    public static void setWaypointCooldown(int max, int min) {
        max = Math.max(0, max);
        if ((min = Math.max(0, min)) > max) {
            min = max;
        }
        waypointCooldownMax = max;
        waypointCooldownMin = min;
        LOTRLevelData.markDirty();
        if (!LOTRMod.proxy.isClient()) {
            List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for (int i = 0; i < players.size(); ++i) {
                EntityPlayerMP entityplayer = (EntityPlayerMP)players.get(i);
                LOTRPacketFTCooldown packet = new LOTRPacketFTCooldown(waypointCooldownMax, waypointCooldownMin);
                LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
            }
        }
    }

    public static boolean enableAlignmentZones() {
        return enableAlignmentZones;
    }

    public static void setEnableAlignmentZones(boolean flag) {
        enableAlignmentZones = flag;
        LOTRLevelData.markDirty();
        if (!LOTRMod.proxy.isClient()) {
            List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for (int i = 0; i < players.size(); ++i) {
                EntityPlayerMP entityplayer = (EntityPlayerMP)players.get(i);
                LOTRPacketEnableAlignmentZones packet = new LOTRPacketEnableAlignmentZones(enableAlignmentZones);
                LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
            }
        }
    }

    public static float getConquestRate() {
        return conquestRate;
    }

    public static void setConquestRate(float f) {
        conquestRate = f;
        LOTRLevelData.markDirty();
    }

    public static void sendPlayerData(EntityPlayerMP entityplayer) {
        try {
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            pd.sendPlayerData(entityplayer);
        }
        catch (Exception e) {
            FMLLog.severe("Failed to send player data to player " + entityplayer.getCommandSenderName());
            e.printStackTrace();
        }
    }

    public static LOTRPlayerData getData(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer.getUniqueID());
    }

    public static LOTRPlayerData getData(UUID player) {
        LOTRPlayerData pd = playerDataMap.get(player);
        if (pd == null) {
            pd = LOTRLevelData.loadData(player);
            if (pd == null) {
                pd = new LOTRPlayerData(player);
            }
            playerDataMap.put(player, pd);
        }
        return pd;
    }

    private static LOTRPlayerData loadData(UUID player) {
        try {
            NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(LOTRLevelData.getLOTRPlayerDat(player));
            LOTRPlayerData pd = new LOTRPlayerData(player);
            pd.load(nbt);
            return pd;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR player data for %s", player);
            e.printStackTrace();
            return null;
        }
    }

    public static void saveData(UUID player) {
        try {
            NBTTagCompound nbt = new NBTTagCompound();
            LOTRPlayerData pd = playerDataMap.get(player);
            pd.save(nbt);
            LOTRLevelData.saveNBTToFile(LOTRLevelData.getLOTRPlayerDat(player), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR player data for %s", player);
            e.printStackTrace();
        }
    }

    private static boolean saveAndClearData(UUID player) {
        LOTRPlayerData pd = playerDataMap.get(player);
        if (pd != null) {
            boolean saved = false;
            if (pd.needsSave()) {
                LOTRLevelData.saveData(player);
                saved = true;
            }
            playerDataMap.remove(player);
            return saved;
        }
        FMLLog.severe("Attempted to clear LOTR player data for %s; no data found", player);
        return false;
    }

    public static void saveAndClearUnusedPlayerData() {
        ArrayList<UUID> clearing = new ArrayList<>();
        for (UUID player : playerDataMap.keySet()) {
            boolean foundPlayer = false;
            for (WorldServer world : MinecraftServer.getServer().worldServers) {
                if (world.func_152378_a(player) == null) continue;
                foundPlayer = true;
                break;
            }
            if (foundPlayer) continue;
            clearing.add(player);
        }
        clearing.size();
        playerDataMap.size();
        for (UUID player : clearing) {
            boolean saved = LOTRLevelData.saveAndClearData(player);
            if (!saved) continue;
        }
        playerDataMap.size();
    }

    public static void destroyAllPlayerData() {
        playerDataMap.clear();
    }

    public static boolean structuresBanned() {
        return structuresBanned == 1;
    }

    public static void setStructuresBanned(boolean banned) {
        structuresBanned = banned ? 1 : 0;
        LOTRLevelData.markDirty();
    }

    public static void setPlayerBannedForStructures(String username, boolean flag) {
        UUID uuid = UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
        if (uuid != null) {
            LOTRLevelData.getData(uuid).setStructuresBanned(flag);
        }
    }

    public static boolean isPlayerBannedForStructures(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getStructuresBanned();
    }

    public static Set<String> getBannedStructurePlayersUsernames() {
        HashSet<String> players = new HashSet<>();
        for (UUID uuid : playerDataMap.keySet()) {
            String username;
            if (!LOTRLevelData.getData(uuid).getStructuresBanned()) continue;
            GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
            if (StringUtils.isBlank(profile.getName())) {
                MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
            }
            if (StringUtils.isBlank(username = profile.getName())) continue;
            players.add(username);
        }
        return players;
    }

    public static void sendAlignmentToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer worldPlayer = (EntityPlayer)world.playerEntities.get(i);
            LOTRPacketAlignment packet = new LOTRPacketAlignment(entityplayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)worldPlayer);
        }
    }

    public static void sendAllAlignmentsInWorldToPlayer(EntityPlayer entityplayer, World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer worldPlayer = (EntityPlayer)world.playerEntities.get(i);
            LOTRPacketAlignment packet = new LOTRPacketAlignment(worldPlayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public static void selectDefaultShieldForPlayer(EntityPlayer entityplayer) {
        if (LOTRLevelData.getData(entityplayer).getShield() == null) {
            for (LOTRShields shield : LOTRShields.values()) {
                if (!shield.canPlayerWear(entityplayer)) continue;
                LOTRLevelData.getData(entityplayer).setShield(shield);
                return;
            }
        }
    }

    public static void sendShieldToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer worldPlayer = (EntityPlayer)world.playerEntities.get(i);
            LOTRPacketShield packet = new LOTRPacketShield(entityplayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)worldPlayer);
        }
    }

    public static void sendAllShieldsInWorldToPlayer(EntityPlayer entityplayer, World world) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer worldPlayer = (EntityPlayer)world.playerEntities.get(i);
            LOTRPacketShield packet = new LOTRPacketShield(worldPlayer.getUniqueID());
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
        }
    }

    public static void sendPlayerLocationsToPlayer(EntityPlayer sendPlayer, World world) {
        LOTRPacketUpdatePlayerLocations packetLocations = new LOTRPacketUpdatePlayerLocations();
        boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(sendPlayer.getGameProfile());
        boolean creative = sendPlayer.capabilities.isCreativeMode;
        LOTRPlayerData playerData = LOTRLevelData.getData(sendPlayer);
        ArrayList<LOTRFellowship> fellowshipsMapShow = new ArrayList<>();
        for (UUID fsID : playerData.getFellowshipIDs()) {
            LOTRFellowship fs = LOTRFellowshipData.getFellowship(fsID);
            if (fs == null || fs.isDisbanded() || !fs.getShowMapLocations()) continue;
            fellowshipsMapShow.add(fs);
        }
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            boolean show;
            EntityPlayer otherPlayer = (EntityPlayer)world.playerEntities.get(i);
            if (otherPlayer == sendPlayer) continue;
            show = !LOTRLevelData.getData(otherPlayer).getHideMapLocation();
            if (!isOp && LOTRLevelData.getData(otherPlayer).getAdminHideMap()) {
                show = false;
            } else if (LOTRConfig.forceMapLocations == 1) {
                show = false;
            } else if (LOTRConfig.forceMapLocations == 2) {
                show = true;
            } else if (!show) {
                if (isOp && creative) {
                    show = true;
                } else if (!playerData.isSiegeActive()) {
                    for (LOTRFellowship fs : fellowshipsMapShow) {
                        if (!fs.containsPlayer(otherPlayer.getUniqueID())) continue;
                        show = true;
                        break;
                    }
                }
            }
            if (!show) continue;
            packetLocations.addPlayerLocation(otherPlayer.getGameProfile(), otherPlayer.posX, otherPlayer.posZ);
        }
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packetLocations, (EntityPlayerMP)sendPlayer);
    }

    public static boolean gollumSpawned() {
        return gollumSpawned;
    }

    public static void setGollumSpawned(boolean flag) {
        gollumSpawned = flag;
        LOTRLevelData.markDirty();
    }

    public static EnumDifficulty getSavedDifficulty() {
        return difficulty;
    }

    public static void setSavedDifficulty(EnumDifficulty d) {
        difficulty = d;
        LOTRLevelData.markDirty();
    }

    public static boolean isDifficultyLocked() {
        return difficultyLock;
    }

    public static void setDifficultyLocked(boolean flag) {
        difficultyLock = flag;
        LOTRLevelData.markDirty();
    }

    public static String getHMSTime_Seconds(int secs) {
        return LOTRLevelData.getHMSTime_Ticks(secs * 20);
    }

    public static String getHMSTime_Ticks(int ticks) {
        int hours = ticks / 72000;
        int minutes = ticks % 72000 / 1200;
        int seconds = ticks % 72000 % 1200 / 20;
        String sHours = StatCollector.translateToLocalFormatted("lotr.gui.time.hours", hours);
        String sMinutes = StatCollector.translateToLocalFormatted("lotr.gui.time.minutes", minutes);
        String sSeconds = StatCollector.translateToLocalFormatted("lotr.gui.time.seconds", seconds);
        if (hours > 0) {
            return StatCollector.translateToLocalFormatted("lotr.gui.time.format.hms", sHours, sMinutes, sSeconds);
        }
        if (minutes > 0) {
            return StatCollector.translateToLocalFormatted("lotr.gui.time.format.ms", sMinutes, sSeconds);
        }
        return StatCollector.translateToLocalFormatted("lotr.gui.time.format.s", sSeconds);
    }

    static {
        conquestRate = 1.0f;
        difficultyLock = false;
        playerDataMap = new HashMap<>();
        needsLoad = true;
        needsSave = false;
        rand = new Random();
    }
}

