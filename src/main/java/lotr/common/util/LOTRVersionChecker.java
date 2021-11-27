package lotr.common.util;

import java.io.*;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.*;

public class LOTRVersionChecker {
    private static String versionURL = "https://dl.dropboxusercontent.com/s/sidxw1dicl2nsev/version.txt";
    private static boolean checkedUpdate = false;

    public static void checkForUpdates() {
        if (!checkedUpdate) {
            Thread checkThread = new Thread("LOTR Update Checker"){

                @Override
                public void run() {
                    try {
                        String line;
                        URL url = new URL(versionURL);
                        BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String updateVersion = "";
                        while ((line = updateReader.readLine()) != null) {
                            updateVersion = updateVersion.concat(line);
                        }
                        updateReader.close();
                        updateVersion = updateVersion.trim();
                        String currentVersion = "Update v36.10 for Minecraft 1.7.10";
                        if (!updateVersion.equals(currentVersion)) {
                            ChatComponentText component = new ChatComponentText("The Lord of the Rings Mod:");
                            component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                            EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
                            if (entityplayer != null) {
                                entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.update", component, updateVersion));
                            }
                        }
                    }
                    catch (Exception e) {
                        LOTRLog.logger.warn("LOTR: Version check failed");
                        e.printStackTrace();
                    }
                }
            };
            checkedUpdate = true;
            checkThread.setDaemon(true);
            checkThread.start();
        }
    }

}

