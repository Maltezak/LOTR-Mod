package lotr.common.fellowship;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class LOTRFellowshipData {
    private static Map<UUID, LOTRFellowship> fellowshipMap = new HashMap<>();
    public static boolean needsLoad = true;
    private static boolean doFullClearing = false;

    public static boolean anyDataNeedsSave() {
        for (LOTRFellowship fs : fellowshipMap.values()) {
            if (!fs.needsSave()) continue;
            return true;
        }
        return false;
    }

    private static File getFellowshipDir() {
        File fsDir = new File(LOTRLevelData.getOrCreateLOTRDir(), "fellowships");
        if (!fsDir.exists()) {
            fsDir.mkdirs();
        }
        return fsDir;
    }

    private static File getFellowshipDat(UUID fsID) {
        return new File(LOTRFellowshipData.getFellowshipDir(), fsID.toString() + ".dat");
    }

    public static void saveAll() {
        try {
            for (LOTRFellowship fs : fellowshipMap.values()) {
                if (!fs.needsSave()) continue;
                LOTRFellowshipData.saveFellowship(fs);
            }
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR fellowship data");
            e.printStackTrace();
        }
    }

    public static void loadAll() {
        try {
            LOTRFellowshipData.destroyAllFellowshipData();
            needsLoad = false;
            LOTRFellowshipData.saveAll();
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR fellowship data");
            e.printStackTrace();
        }
    }

    public static void addFellowship(LOTRFellowship fs) {
        if (!fellowshipMap.containsKey(fs.getFellowshipID())) {
            fellowshipMap.put(fs.getFellowshipID(), fs);
        }
    }

    public static LOTRFellowship getFellowship(UUID fsID) {
        LOTRFellowship fs = fellowshipMap.get(fsID);
        if (fs == null && (fs = LOTRFellowshipData.loadFellowship(fsID)) != null) {
            fellowshipMap.put(fsID, fs);
        }
        return fs;
    }

    private static LOTRFellowship loadFellowship(UUID fsID) {
        File fsDat = LOTRFellowshipData.getFellowshipDat(fsID);
        try {
            NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(fsDat);
            if (nbt.hasNoTags()) {
                return null;
            }
            LOTRFellowship fs = new LOTRFellowship(fsID);
            fs.load(nbt);
            if (fs.isDisbanded()) {
                return null;
            }
            return fs;
        }
        catch (Exception e) {
            FMLLog.severe("Error loading LOTR fellowship data for %s", fsDat.getName());
            e.printStackTrace();
            return null;
        }
    }

    public static void saveFellowship(LOTRFellowship fs) {
        try {
            NBTTagCompound nbt = new NBTTagCompound();
            fs.save(nbt);
            LOTRLevelData.saveNBTToFile(LOTRFellowshipData.getFellowshipDat(fs.getFellowshipID()), nbt);
        }
        catch (Exception e) {
            FMLLog.severe("Error saving LOTR fellowship data for %s", fs.getFellowshipID());
            e.printStackTrace();
        }
    }

    private static void saveAndClearFellowship(LOTRFellowship fs) {
        if (fellowshipMap.containsValue(fs)) {
            LOTRFellowshipData.saveFellowship(fs);
            fellowshipMap.remove(fs.getFellowshipID());
        } else {
            FMLLog.severe("Attempted to clear LOTR fellowship data for %s; no data found", fs.getFellowshipID());
        }
    }

    public static void saveAndClearUnusedFellowships() {
        if (doFullClearing) {
            ArrayList<LOTRFellowship> clearing = new ArrayList<>();
            for (LOTRFellowship fs : fellowshipMap.values()) {
                boolean foundMember = false;
                for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                    EntityPlayer entityplayer = (EntityPlayer)player;
                    if (!fs.containsPlayer(entityplayer.getUniqueID())) continue;
                    foundMember = true;
                    break;
                }
                if (foundMember) continue;
                clearing.add(fs);
            }
            for (LOTRFellowship fs : clearing) {
                LOTRFellowshipData.saveAndClearFellowship(fs);
            }
        }
    }

    public static void destroyAllFellowshipData() {
        fellowshipMap.clear();
    }
}

