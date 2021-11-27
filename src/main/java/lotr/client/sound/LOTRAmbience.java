package lotr.client.sound;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.LOTRWeatherRenderer;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class LOTRAmbience {
    private int ticksSinceWight;
    private List<ISound> playingWindSounds = new ArrayList<>();
    private List<ISound> playingSeaSounds = new ArrayList<>();
    private ISound playingJazzMusic;
    private int jazzPlayerID;
    private static final ResourceLocation jazzMusicPath = new ResourceLocation("lotr:music.jazzelf");
    private static final String jazzMusicTitle = "The Galadhon Groovers - Funky Villagers";

    public LOTRAmbience() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent17 event) {
        String name = event.name;
        ISound sound = event.sound;
        if (LOTRConfig.newWeather && sound instanceof PositionedSound) {
            PositionedSound ps = (PositionedSound)sound;
            WorldClient world = Minecraft.getMinecraft().theWorld;
            if (world != null && world.provider instanceof LOTRWorldProvider) {
                if (name.equals("ambient.weather.rain")) {
                    event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.rain"), ps.getVolume(), ps.getPitch(), ps.getXPosF(), ps.getYPosF(), ps.getZPosF());
                } else if (name.equals("ambient.weather.thunder")) {
                    event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.thunder"), ps.getVolume(), ps.getPitch(), ps.getXPosF(), ps.getYPosF(), ps.getZPosF());
                }
            }
        }
        if (this.playingJazzMusic != null && event.category == SoundCategory.MUSIC) {
            event.result = null;
        }
    }

    public void updateAmbience(World world, EntityPlayer entityplayer) {
        Minecraft mc;
        block42: {
            int xzRange;
            world.theProfiler.startSection("lotrAmbience");
            mc = Minecraft.getMinecraft();
            boolean enableAmbience = LOTRConfig.enableAmbience;
            boolean enableWeather = LOTRConfig.newWeather;
            double x = entityplayer.posX;
            double y = entityplayer.boundingBox.minY;
            double z = entityplayer.posZ;
            int i = MathHelper.floor_double(x);
            int j = MathHelper.floor_double(y);
            int k = MathHelper.floor_double(z);
            BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
            Random rand = world.rand;
            if (enableAmbience) {
                if (this.ticksSinceWight > 0) {
                    --this.ticksSinceWight;
                } else {
                    boolean wights;
                    wights = LOTRTickHandlerClient.anyWightsViewed && rand.nextInt(20) == 0 || biome instanceof LOTRBiomeGenBarrowDowns && rand.nextInt(3000) == 0;
                    if (wights) {
                        world.playSound(x, y, z, "lotr:wight.ambience", 1.0f, 0.8f + rand.nextFloat() * 0.4f, false);
                        this.ticksSinceWight = 300;
                    }
                }
                boolean spookyBiomeNoise = false;
                float spookyPitch = 1.0f;
                if (biome instanceof LOTRBiomeGenDolGuldur) {
                    spookyBiomeNoise = rand.nextInt(1000) == 0;
                    spookyPitch = 0.85f;
                } else if (biome instanceof LOTRBiomeGenDeadMarshes) {
                    spookyBiomeNoise = rand.nextInt(2400) == 0;
                } else if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
                    spookyBiomeNoise = rand.nextInt(3000) == 0;
                } else if (biome instanceof LOTRBiomeGenOldForest) {
                    spookyBiomeNoise = rand.nextInt(6000) == 0;
                } else if (biome instanceof LOTRBiomeGenUtumno) {
                    spookyBiomeNoise = rand.nextInt(1000) == 0;
                    spookyPitch = 0.75f;
                }
                if (spookyBiomeNoise) {
                    world.playSound(x, y, z, "lotr:wight.ambience", 1.0f, (0.8f + rand.nextFloat() * 0.4f) * spookyPitch, false);
                }
                if (biome instanceof LOTRBiomeGenUtumno && world.rand.nextInt(500) == 0) {
                    world.playSound(x, y, z, "ambient.cave.cave", 1.0f, 0.8f + rand.nextFloat() * 0.2f, false);
                }
            }
            if (enableWeather && world.provider instanceof LOTRWorldProvider) {
                if (this.playingWindSounds.size() < 4) {
                    xzRange = 16;
                    int minWindHeight = 100;
                    int fullWindHeight = 180;
                    if (rand.nextInt(20) == 0) {
                        minWindHeight -= 10;
                        if (rand.nextInt(10) == 0) {
                            minWindHeight -= 10;
                        }
                    }
                    if (world.isRaining()) {
                        minWindHeight = 80;
                        fullWindHeight = 120;
                        if (rand.nextInt(20) == 0) {
                            minWindHeight -= 20;
                        }
                        if (LOTRWeatherRenderer.isSandstormBiome(biome)) {
                            minWindHeight = 60;
                            fullWindHeight = 80;
                        }
                    }
                    for (int l = 0; l < 2; ++l) {
                        int i1 = i + MathHelper.getRandomIntegerInRange(rand, (-xzRange), xzRange);
                        int k1 = k + MathHelper.getRandomIntegerInRange(rand, (-xzRange), xzRange);
                        int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 16);
                        if (j1 < minWindHeight || !world.canBlockSeeTheSky(i1, j1, k1)) continue;
                        float windiness = (float)(j1 - minWindHeight) / (float)(fullWindHeight - minWindHeight);
                        if ((windiness = MathHelper.clamp_float(windiness, 0.0f, 1.0f)) < rand.nextFloat()) continue;
                        float x1 = i1 + 0.5f;
                        float y1 = j1 + 0.5f;
                        float z1 = k1 + 0.5f;
                        float vol = 1.0f * Math.max(0.25f, windiness);
                        float pitch = 0.8f + rand.nextFloat() * 0.4f;
                        AmbientSoundNoAttentuation wind = new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.weather.wind"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
                        mc.getSoundHandler().playSound(wind);
                        this.playingWindSounds.add(wind);
                        break;
                    }
                } else {
                    HashSet<ISound> removes = new HashSet<>();
                    for (ISound wind : this.playingWindSounds) {
                        if (mc.getSoundHandler().isSoundPlaying(wind)) continue;
                        removes.add(wind);
                    }
                    this.playingWindSounds.removeAll(removes);
                }
            }
            if (enableAmbience) {
                if (this.playingSeaSounds.size() < 3) {
                    if (biome instanceof LOTRBiomeGenOcean || biome instanceof LOTRBiomeGenBeach || biome instanceof LOTRBiomeGenLindonCoast || biome instanceof LOTRBiomeGenFarHaradCoast) {
                        xzRange = 64;
                        for (float fr : new float[]{0.25f, 0.5f, 0.75f, 1.0f}) {
                            int range = (int)(xzRange * fr);
                            for (int l = 0; l < 8; ++l) {
                                int i1 = i + MathHelper.getRandomIntegerInRange(rand, (-range), range);
                                int k1 = k + MathHelper.getRandomIntegerInRange(rand, (-range), range);
                                int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 8);
                                Block block = world.getBlock(i1, j1, k1);
                                if (block.getMaterial() != Material.water || j1 < world.getTopSolidOrLiquidBlock(i1, k1)) continue;
                                float x1 = i1 + 0.5f;
                                float y1 = j1 + 0.5f;
                                float z1 = k1 + 0.5f;
                                float vol = 1.0f;
                                float pitch = 0.8f + rand.nextFloat() * 0.4f;
                                AmbientSoundNoAttentuation sea = new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.terrain.sea"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
                                mc.getSoundHandler().playSound(sea);
                                this.playingSeaSounds.add(sea);
                                int j2 = world.getHeightValue(i1, k1) - 1;
                                if (world.getBlock(i1, j2, k1).getMaterial() == Material.water) {
                                    double dx = i1 + 0.5 - entityplayer.posX;
                                    double dz = k1 + 0.5 - entityplayer.posZ;
                                    float angle = (float)Math.atan2(dz, dx);
                                    float cos = MathHelper.cos(angle);
                                    float sin = MathHelper.sin(angle);
                                    float angle90 = angle + (float)Math.toRadians(-90.0);
                                    float cos90 = MathHelper.cos(angle90);
                                    float sin90 = MathHelper.sin(angle90);
                                    float waveSpeed = MathHelper.randomFloatClamp(rand, 0.3f, 0.5f);
                                    int waveR = 40 + rand.nextInt(100);
                                    for (int w = -waveR; w <= waveR; ++w) {
                                        float f = w / 8.0f;
                                        double d0 = i1 + 0.5;
                                        double d1 = j2 + 1.0 + MathHelper.randomFloatClamp(rand, 0.02f, 0.1f);
                                        double d2 = k1 + 0.5;
                                        if (world.getBlock(MathHelper.floor_double(d0 += f * cos90), MathHelper.floor_double(d1) - 1, MathHelper.floor_double(d2 += f * sin90)).getMaterial() != Material.water) continue;
                                        double d3 = waveSpeed * -cos;
                                        double d4 = 0.0;
                                        double d5 = waveSpeed * -sin;
                                        LOTRMod.proxy.spawnParticle("wave", d0, d1, d2, d3, d4, d5);
                                    }
                                }
                                break block42;
                            }
                        }
                    }
                } else {
                    HashSet<ISound> removes = new HashSet<>();
                    for (ISound sea : this.playingSeaSounds) {
                        if (mc.getSoundHandler().isSoundPlaying(sea)) continue;
                        removes.add(sea);
                    }
                    this.playingSeaSounds.removeAll(removes);
                }
            }
        }
        if (this.playingJazzMusic == null) {
            if (entityplayer.ticksExisted % 20 == 0) {
                double range = 16.0;
                List elves = world.getEntitiesWithinAABB(LOTREntityElf.class, entityplayer.boundingBox.expand(range, range, range));
                LOTREntityElf playingElf = null;
                for (Object obj : elves) {
                    LOTREntityElf elf = (LOTREntityElf)obj;
                    if (!elf.isEntityAlive() || !elf.isJazz() || !elf.isSolo()) continue;
                    playingElf = elf;
                    break;
                }
                if (playingElf != null) {
                    mc.getSoundHandler().stopSounds();
                    this.jazzPlayerID = playingElf.getEntityId();
                    ISound music = this.getJazzMusic(playingElf);
                    mc.getSoundHandler().playSound(music);
                    this.playingJazzMusic = music;
                    mc.ingameGUI.setRecordPlayingMessage(jazzMusicTitle);
                }
            }
        } else {
            Entity player = world.getEntityByID(this.jazzPlayerID);
            if (player == null || !player.isEntityAlive()) {
                mc.getSoundHandler().stopSound(this.playingJazzMusic);
                this.playingJazzMusic = null;
            }
            if (!mc.getSoundHandler().isSoundPlaying(this.playingJazzMusic)) {
                this.playingJazzMusic = null;
            }
        }
        world.theProfiler.endSection();
    }

    private ISound getJazzMusic(Entity entity) {
        return PositionedSoundRecord.func_147675_a(jazzMusicPath, ((float)entity.posX), ((float)entity.posY), ((float)entity.posZ));
    }

    private static class AmbientSoundNoAttentuation
    extends PositionedSoundRecord {
        public AmbientSoundNoAttentuation(ResourceLocation sound, float vol, float pitch, float x, float y, float z) {
            super(sound, vol, pitch, x, y, z);
            this.field_147666_i = ISound.AttenuationType.NONE;
        }

        public AmbientSoundNoAttentuation calcAmbientVolume(EntityPlayer entityplayer, int maxRange) {
            float distFr = (float)entityplayer.getDistance(this.xPosF, this.yPosF, this.zPosF);
            distFr /= maxRange;
            distFr = Math.min(distFr, 1.0f);
            distFr = 1.0f - distFr;
            distFr *= 1.5f;
            distFr = MathHelper.clamp_float(distFr, 0.1f, 1.0f);
            this.volume *= distFr;
            return this;
        }
    }

}

