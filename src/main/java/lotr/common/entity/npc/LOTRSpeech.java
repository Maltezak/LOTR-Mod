package lotr.common.entity.npc;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRSpeech {
    private static Map<String, SpeechBank> allSpeechBanks = new HashMap<>();
    private static Random rand = new Random();

    public static void loadAllSpeechBanks() {
        HashMap<String, BufferedReader> speechBankNamesAndReaders = new HashMap<>();
        ZipFile zip = null;
        try {
            ModContainer mc = LOTRMod.getModContainer();
            if(mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                Enumeration<? extends ZipEntry> entries = zip.entries();
                while(entries.hasMoreElements()) {
                    String path;
                    ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    if(!s.startsWith(path = "assets/lotr/speech/") || !s.endsWith(".txt")) continue;
                    s = s.substring(path.length());
                    int i = s.indexOf(".txt");
                    try {
                        s = s.substring(0, i);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                        speechBankNamesAndReaders.put(s, reader);
                    }
                    catch(Exception e) {
                        FMLLog.severe("Failed to load LOTR speech bank " + s + "from zip file");
                        e.printStackTrace();
                    }
                }
            }
            else {
                File speechBankDir = new File(LOTRMod.class.getResource("/assets/lotr/speech").toURI());
                Collection<File> subfiles = FileUtils.listFiles(speechBankDir, null, true);
                for(File subfile : subfiles) {
                    String s = subfile.getPath();
                    s = s.substring(speechBankDir.getPath().length() + 1);
                    int i = (s = s.replace(File.separator, "/")).indexOf(".txt");
                    if(i < 0) {
                        FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder; speech bank files must be in .txt format");
                        continue;
                    }
                    try {
                        s = s.substring(0, i);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(subfile)), Charsets.UTF_8.name()));
                        speechBankNamesAndReaders.put(s, reader);
                    }
                    catch(Exception e) {
                        FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch(Exception e) {
            FMLLog.severe("Failed to load LOTR speech banks");
            e.printStackTrace();
        }
        for(String speechBankName : speechBankNamesAndReaders.keySet()) {
            BufferedReader reader = speechBankNamesAndReaders.get(speechBankName);
            try {
                String line;
                ArrayList<String> speeches = new ArrayList<>();
                ArrayList<String> allLines = new ArrayList<>();
                boolean random = true;
                while((line = reader.readLine()) != null) {
                    if(line.equals("!RANDOM")) {
                        random = false;
                    }
                    else {
                        speeches.add(line);
                    }
                    allLines.add(line);
                }
                reader.close();
                if(speeches.isEmpty()) {
                    FMLLog.severe("LOTR speech bank " + speechBankName + " is empty!");
                    continue;
                }
                SpeechBank bank = random ? new SpeechBank(speechBankName, random, speeches) : new SpeechBank(speechBankName, random, allLines);
                allSpeechBanks.put(speechBankName, bank);
            }
            catch(Exception e) {
                FMLLog.severe("Failed to load LOTR speech bank " + speechBankName);
                e.printStackTrace();
            }
        }
        if(zip != null) {
            try {
                zip.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static SpeechBank getSpeechBank(String name) {
        SpeechBank bank = allSpeechBanks.get(name);
        if(bank != null) {
            return bank;
        }
        return new SpeechBank("dummy_" + name, true, Arrays.asList("Speech bank " + name + " could not be found!"));
    }

    public static String getRandomSpeech(String bankName) {
        return LOTRSpeech.getSpeechBank(bankName).getRandomSpeech(rand);
    }

    public static String getSpeechAtLine(String bankName, int i) {
        return LOTRSpeech.getSpeechBank(bankName).getSpeechAtLine(i);
    }

    public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, EntityPlayer entityplayer) {
        return LOTRSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
    }

    public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, EntityPlayer entityplayer, String location, String objective) {
        String s = LOTRSpeech.getRandomSpeech(speechBankName);
        s = LOTRSpeech.formatSpeech(s, entityplayer, location, objective);
        if(entity.isDrunkard()) {
            float f = entity.getDrunkenSpeechFactor();
            s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
        }
        return s;
    }

    public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer) {
        return LOTRSpeech.getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, null, null);
    }

    public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer, String location, String objective) {
        String s = LOTRSpeech.getSpeechAtLine(speechBankName, i);
        s = LOTRSpeech.formatSpeech(s, entityplayer, location, objective);
        if(entity.isDrunkard()) {
            float f = entity.getDrunkenSpeechFactor();
            s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
        }
        return s;
    }

    public static String formatSpeech(String speech, EntityPlayer entityplayer, String location, String objective) {
        if(entityplayer != null) {
            speech = speech.replace("#", entityplayer.getCommandSenderName());
        }
        if(location != null) {
            speech = speech.replace("@", location);
        }
        if(objective != null) {
            speech = speech.replace("$", objective);
        }
        return speech;
    }

    public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech) {
        LOTRSpeech.sendSpeech(entityplayer, entity, speech, false);
    }

    public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech, boolean forceChatMsg) {
        LOTRPacketNPCSpeech packet = new LOTRPacketNPCSpeech(entity.getEntityId(), speech, forceChatMsg);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }

    public static void sendSpeechBankWithChatMsg(EntityPlayer entityplayer, LOTREntityNPC entity, String speechBankName) {
        String speech = LOTRSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
        LOTRSpeech.sendSpeech(entityplayer, entity, speech, true);
    }

    public static void sendSpeechAndChatMessage(EntityPlayer entityplayer, LOTREntityNPC entity, String speechBankName) {
        String name = entity.getCommandSenderName();
        String speech = LOTRSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
        String message = (EnumChatFormatting.YELLOW) + "<" + name + ">" + (EnumChatFormatting.WHITE) + " " + speech;
        ChatComponentText component = new ChatComponentText(message);
        entityplayer.addChatMessage(component);
        LOTRSpeech.sendSpeech(entityplayer, entity, speech);
    }

    public static void messageAllPlayers(IChatComponent message) {
        if(MinecraftServer.getServer() == null) {
            return;
        }
        for(Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            ((EntityPlayer) player).addChatMessage(message);
        }
    }

    public static void messageAllPlayersInWorld(World world, IChatComponent message) {
        for(Object player : world.playerEntities) {
            ((EntityPlayer) player).addChatMessage(message);
        }
    }

    private static class SpeechBank {
        public final String name;
        public final boolean isRandom;
        public final List<String> speeches;

        public SpeechBank(String s, boolean r, List<String> spc) {
            this.name = s;
            this.isRandom = r;
            this.speeches = spc;
        }

        public String getRandomSpeech(Random random) {
            if(!this.isRandom) {
                return "ERROR: Tried to retrieve random speech from non-random speech bank " + this.name;
            }
            String s = this.speeches.get(rand.nextInt(this.speeches.size()));
            s = this.internalFormatSpeech(s);
            return s;
        }

        public String getSpeechAtLine(int line) {
            if(this.isRandom) {
                return "ERROR: Tried to retrieve indexed speech from random speech bank " + this.name;
            }
            int index = line - 1;
            if(index >= 0 && index < this.speeches.size()) {
                String s = this.speeches.get(index);
                s = this.internalFormatSpeech(s);
                return s;
            }
            return "ERROR: Speech line " + line + " is out of range!";
        }

        private String internalFormatSpeech(String s) {
            if(LOTRMod.isAprilFools() || rand.nextInt(2000) == 0) {
                s = "Tbh, " + s.substring(0, 1).toLowerCase() + s.substring(1, s.length() - 1) + ", tbh.";
            }
            return s;
        }
    }

}
