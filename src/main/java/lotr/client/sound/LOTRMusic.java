package lotr.client.sound;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.*;
import lotr.common.util.LOTRLog;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class LOTRMusic implements IResourceManagerReloadListener {
    private static File musicDir;
    private static final String jsonFilename = "music.json";
    public static final String musicResourcePath = "lotrmusic";
    public static final LOTRMusicResourceManager trackResourceManager;
    private static List<LOTRMusicTrack> allTracks;
    private static Map<LOTRMusicRegion.Sub, LOTRRegionTrackPool> regionTracks;
    private static boolean initSubregions;
    private static Random musicRand;

    public LOTRMusic() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourcemanager) {
        LOTRMusic.loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager) resourcemanager);
    }

    public void update() {
        LOTRMusicTicker.update(musicRand);
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent17 event) {
        Minecraft.getMinecraft();
        if(!allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof LOTRMusicTrack)) {
            if(LOTRMusic.isLOTRDimension()) {
                event.result = null;
                return;
            }
            if(LOTRMusic.isMenuMusic() && !LOTRMusic.getTracksForRegion(LOTRMusicRegion.MENU, null).isEmpty()) {
                event.result = null;
            }
        }
    }

    public static boolean isLOTRDimension() {
        Minecraft mc = Minecraft.getMinecraft();
        WorldClient world = mc.theWorld;
        EntityClientPlayerMP entityplayer = mc.thePlayer;
        return entityplayer != null && world != null && world.provider instanceof LOTRWorldProvider;
    }

    public static boolean isMenuMusic() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.func_147109_W() == MusicTicker.MusicType.MENU;
    }

    public static LOTRRegionTrackPool getTracksForRegion(LOTRMusicRegion region, String sub) {
        if(region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
            LOTRMusicRegion.Sub key = region.getSubregion(sub);
            LOTRRegionTrackPool regionPool = regionTracks.get(key);
            if(regionPool == null) {
                regionPool = new LOTRRegionTrackPool(region, sub);
                regionTracks.put(key, regionPool);
            }
            return regionPool;
        }
        LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
        return null;
    }

    public static void addTrackToRegions(LOTRMusicTrack track) {
        allTracks.add(track);
        for(LOTRMusicRegion region : track.getAllRegions()) {
            if(region.hasNoSubregions()) {
                LOTRMusic.getTracksForRegion(region, null).addTrack(track);
                continue;
            }
            for(String sub : track.getRegionInfo(region).getSubregions()) {
                LOTRMusic.getTracksForRegion(region, sub).addTrack(track);
            }
        }
    }

    private static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
        musicDir = new File(mcDir, musicResourcePath);
        if(!musicDir.exists()) {
            musicDir.mkdirs();
        }
        allTracks.clear();
        regionTracks.clear();
        if(!initSubregions) {
            for(LOTRDimension dim : LOTRDimension.values()) {
                for(LOTRBiome biome : dim.biomeList) {
                    if(biome == null) continue;
                    biome.getBiomeMusic();
                }
            }
            initSubregions = true;
        }
        for(File file : musicDir.listFiles()) {
            if(!file.isFile() || !file.getName().endsWith(".zip")) continue;
            try {
                FileResourcePack resourcePack = new FileResourcePack(file);
                resourceMgr.reloadResourcePack(resourcePack);
                ZipFile zipFile = new ZipFile(file);
                LOTRMusic.loadMusicPack(zipFile, resourceMgr);
            }
            catch(Exception e) {
                LOTRLog.logger.warn("LOTRMusic: Failed to load music pack " + file.getName() + "!");
                e.printStackTrace();
            }
        }
        try {
            LOTRMusic.generateReadme();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadMusicPack(ZipFile zip, SimpleReloadableResourceManager resourceMgr) throws IOException {
        ZipEntry entry = zip.getEntry(jsonFilename);
        if(entry != null) {
            InputStream stream = zip.getInputStream(entry);
            JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8.name()));
            JsonParser parser = new JsonParser();
            ArrayList<LOTRMusicTrack> packTracks = new ArrayList<>();
            JsonObject root = parser.parse(reader).getAsJsonObject();
            JsonArray rootArray = root.get("tracks").getAsJsonArray();
            for(JsonElement e : rootArray) {
                JsonObject trackData = e.getAsJsonObject();
                String filename = trackData.get("file").getAsString();
                ZipEntry trackEntry = zip.getEntry("assets/lotrmusic/" + filename);
                if(trackEntry == null) {
                    LOTRLog.logger.warn("LOTRMusic: Track " + filename + " in pack " + zip.getName() + " does not exist!");
                    continue;
                }
                InputStream trackStream = zip.getInputStream(trackEntry);
                LOTRMusicTrack track = new LOTRMusicTrack(filename);
                if(trackData.has("title")) {
                    String title = trackData.get("title").getAsString();
                    track.setTitle(title);
                }
                JsonArray regions = trackData.get("regions").getAsJsonArray();
                for(Object r : regions) {
                    LOTRMusicRegion region;
                    JsonObject regionData = ((JsonElement) r).getAsJsonObject();
                    String regionName = regionData.get("name").getAsString();
                    boolean allRegions = false;
                    if(regionName.equalsIgnoreCase("all")) {
                        region = null;
                        allRegions = true;
                    }
                    else {
                        region = LOTRMusicRegion.forName(regionName);
                        if(region == null) {
                            LOTRLog.logger.warn("LOTRMusic: No region named " + regionName + "!");
                            continue;
                        }
                    }
                    ArrayList<String> subregionNames = new ArrayList<>();
                    if(region != null && regionData.has("sub")) {
                        JsonArray subList = regionData.get("sub").getAsJsonArray();
                        for(Object s : subList) {
                            String sub = ((JsonElement) s).getAsString();
                            if(region.hasSubregion(sub)) {
                                subregionNames.add(sub);
                                continue;
                            }
                            LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
                        }
                    }
                    ArrayList<LOTRMusicCategory> regionCategories = new ArrayList<>();
                    if(region != null && regionData.has("categories")) {
                        JsonArray catList = regionData.get("categories").getAsJsonArray();
                        Iterator<JsonElement> s = catList.iterator();
                        while(s.hasNext()) {
                            JsonElement cat = s.next();
                            String categoryName = cat.getAsString();
                            LOTRMusicCategory category = LOTRMusicCategory.forName(categoryName);
                            if(category != null) {
                                regionCategories.add(category);
                                continue;
                            }
                            LOTRLog.logger.warn("LOTRMusic: No category named " + categoryName + "!");
                        }
                    }
                    double weight = -1.0;
                    if(regionData.has("weight")) {
                        weight = regionData.get("weight").getAsDouble();
                    }
                    ArrayList<LOTRMusicRegion> regionsAdd = new ArrayList<>();
                    if(allRegions) {
                        regionsAdd.addAll(Arrays.asList(LOTRMusicRegion.values()));
                    }
                    else {
                        regionsAdd.add(region);
                    }
                    for(LOTRMusicRegion reg : regionsAdd) {
                        LOTRTrackRegionInfo regInfo = track.createRegionInfo(reg);
                        if(weight >= 0.0) {
                            regInfo.setWeight(weight);
                        }
                        if(subregionNames.isEmpty()) {
                            regInfo.addAllSubregions();
                        }
                        else {
                            for(String sub : subregionNames) {
                                regInfo.addSubregion(sub);
                            }
                        }
                        if(regionCategories.isEmpty()) {
                            regInfo.addAllCategories();
                            continue;
                        }
                        for(LOTRMusicCategory cat : regionCategories) {
                            regInfo.addCategory(cat);
                        }
                    }
                }
                if(trackData.has("authors")) {
                    JsonArray authorList = trackData.get("authors").getAsJsonArray();
                    Iterator<JsonElement> r = authorList.iterator();
                    while(r.hasNext()) {
                        JsonElement a = r.next();
                        String author = a.getAsString();
                        track.addAuthor(author);
                    }
                }
                track.loadTrack(trackStream);
                packTracks.add(track);
            }
            reader.close();
            LOTRLog.logger.info("LOTRMusic: Successfully loaded music pack " + zip.getName() + " with " + packTracks.size() + " tracks");
        }
    }

    private static void generateReadme() throws IOException {
        File readme = new File(musicDir, "readme.txt");
        readme.createNewFile();
        PrintStream writer = new PrintStream(new FileOutputStream(readme));
        ResourceLocation template = new ResourceLocation("lotr:music/readme.txt");
        InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8.name()));
        String line = "";
        while((line = reader.readLine()) != null) {
            if(line.equals("#REGIONS#")) {
                writer.println("all");
                for(Enum region : LOTRMusicRegion.values()) {
                    String regionString = "";
                    regionString = regionString + ((LOTRMusicRegion) region).regionName;
                    List<String> subregions = ((LOTRMusicRegion) region).getAllSubregions();
                    if(!subregions.isEmpty()) {
                        String subs = "";
                        for(String s : subregions) {
                            if(subs.length() > 0) {
                                subs = subs + ", ";
                            }
                            subs = subs + s;
                        }
                        regionString = regionString + ": {" + subs + "}";
                    }
                    writer.println(regionString);
                }
                continue;
            }
            if(line.equals("#CATEGORIES#")) {
                for(Enum category : LOTRMusicCategory.values()) {
                    String catString = ((LOTRMusicCategory) category).categoryName;
                    writer.println(catString);
                }
                continue;
            }
            writer.println(line);
        }
        writer.close();
        reader.close();
    }

    static {
        trackResourceManager = new LOTRMusicResourceManager();
        allTracks = new ArrayList<>();
        regionTracks = new HashMap<>();
        initSubregions = false;
        musicRand = new Random();
    }

    public static class Reflect {
        public static void putDomainResourceManager(String domain, IResourceManager manager) {
            SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
            try {
                Map map = (Map) ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, masterManager, "domainResourceManagers", "field_110548_a");
                map.put(domain, manager);
            }
            catch(Exception e) {
                LOTRReflection.logFailure(e);
            }
        }

        public static SoundRegistry getSoundRegistry() {
            SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
            try {
                return (SoundRegistry) ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, "sndRegistry", "field_147697_e");
            }
            catch(Exception e) {
                LOTRReflection.logFailure(e);
                return null;
            }
        }
    }

}
