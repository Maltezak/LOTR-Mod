package lotr.common.world.map;

import java.io.*;
import java.nio.charset.Charset;
import java.text.*;
import java.util.*;

import com.google.common.io.Files;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRConfig;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

public class LOTRCustomWaypointLogger {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM");
    private static final DateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MM-dd");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static void logCreate(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
        LOTRCustomWaypointLogger.log("CREATE", entityplayer, cwp);
    }

    public static void logTravel(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
        LOTRCustomWaypointLogger.log("TRAVEL", entityplayer, cwp);
    }

    public static void logRename(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
        LOTRCustomWaypointLogger.log("RENAME", entityplayer, cwp);
    }

    public static void logDelete(EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
        LOTRCustomWaypointLogger.log("DELETE", entityplayer, cwp);
    }

    private static void log(String function, EntityPlayer entityplayer, LOTRCustomWaypoint cwp) {
        if (!LOTRConfig.cwpLog) {
            return;
        }
        try {
            File logFile;
            File dupeLogDir;
            Date date = Calendar.getInstance().getTime();
            String logLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", MONTH_DATE_FORMAT.format(date), TIME_FORMAT.format(date), function, entityplayer.getCommandSenderName(), entityplayer.getPersistentID(), cwp.getCodeName(), cwp.getXCoord(), cwp.getYCoordSaved(), cwp.getZCoord(), cwp.isShared(), cwp.isShared() ? cwp.getSharingPlayerName() : "N/A", cwp.isShared() ? cwp.getSharingPlayerID() : "N/A");
            if (cwp.isShared()) {
                List<UUID> fsIDs = cwp.getSharedFellowshipIDs();
                for (UUID id : fsIDs) {
                    LOTRFellowship fellowship = LOTRFellowshipData.getFellowship(id);
                    if (fellowship == null || fellowship.isDisbanded() || !fellowship.containsPlayer(entityplayer.getUniqueID())) continue;
                    logLine = logLine + ",";
                    logLine = logLine + fellowship.getName();
                }
            }
            if (!(dupeLogDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "lotr_cwp_logs")).exists()) {
                dupeLogDir.mkdirs();
            }
            if (!(logFile = new File(dupeLogDir, DATE_FORMAT.format(date) + ".csv")).exists()) {
                Files.append("date,time,function,username,UUID,wp_name,x,y,z,shared,sharer_name,sharer_UUID,common_fellowships" + System.getProperty("line.separator"), logFile, CHARSET);
            }
            Files.append(logLine + System.getProperty("line.separator"), logFile, CHARSET);
        }
        catch (IOException e) {
            FMLLog.warning("Error logging custom waypoint activities");
            e.printStackTrace();
        }
    }
}

