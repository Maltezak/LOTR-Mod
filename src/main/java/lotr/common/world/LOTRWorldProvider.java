package lotr.common.world;

import com.google.common.math.IntMath;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.*;
import lotr.common.*;
import lotr.common.world.biome.*;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.ForgeModContainer;

public abstract class LOTRWorldProvider
extends WorldProvider {
    public static int MOON_PHASES = 8;
    @SideOnly(value=Side.CLIENT)
    private IRenderHandler lotrSkyRenderer;
    @SideOnly(value=Side.CLIENT)
    private IRenderHandler lotrCloudRenderer;
    @SideOnly(value=Side.CLIENT)
    private IRenderHandler lotrWeatherRenderer;
    private double cloudsR;
    private double cloudsG;
    private double cloudsB;
    private double fogR;
    private double fogG;
    private double fogB;

    public abstract LOTRDimension getLOTRDimension();

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new LOTRWorldChunkManager(this.worldObj, this.getLOTRDimension());
        this.dimensionId = this.getLOTRDimension().dimensionID;
    }

    public String getWelcomeMessage() {
        return StatCollector.translateToLocalFormatted("lotr.dimension.enter", this.getLOTRDimension().getDimensionName());
    }

    public String getDepartMessage() {
        return StatCollector.translateToLocalFormatted("lotr.dimension.exit", this.getLOTRDimension().getDimensionName());
    }

    public String getSaveFolder() {
        return this.getLOTRDimension().dimensionName;
    }

    public String getDimensionName() {
        return this.getLOTRDimension().dimensionName;
    }

    public boolean canRespawnHere() {
        return true;
    }

    public BiomeGenBase getBiomeGenForCoords(int i, int k) {
        Chunk chunk;
        if (this.worldObj.blockExists(i, 0, k) && (chunk = this.worldObj.getChunkFromBlockCoords(i, k)) != null) {
            int chunkX = i & 0xF;
            int chunkZ = k & 0xF;
            int biomeID = chunk.getBiomeArray()[chunkZ << 4 | chunkX] & 0xFF;
            if (biomeID == 255) {
                BiomeGenBase biomegenbase = this.worldChunkMgr.getBiomeGenAt((chunk.xPosition << 4) + chunkX, (chunk.zPosition << 4) + chunkZ);
                biomeID = biomegenbase.biomeID;
                chunk.getBiomeArray()[chunkZ << 4 | chunkX] = (byte)(biomeID & 0xFF);
            }
            LOTRDimension dim = this.getLOTRDimension();
            return dim.biomeList[biomeID] == null ? dim.biomeList[0] : dim.biomeList[biomeID];
        }
        return this.worldChunkMgr.getBiomeGenAt(i, k);
    }

    public boolean canBlockFreeze(int i, int j, int k, boolean isBlockUpdate) {
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenOcean) {
            return LOTRBiomeGenOcean.isFrozen(i, k) && this.canFreeze_ignoreTemp(i, j, k, isBlockUpdate);
        }
        if (biome instanceof LOTRBiome) {
            return this.worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
        }
        return this.worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
    }

    public boolean canSnowAt(int i, int j, int k, boolean checkLight) {
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenOcean) {
            return LOTRBiomeGenOcean.isFrozen(i, k) && this.canSnow_ignoreTemp(i, j, k, checkLight);
        }
        if (biome instanceof LOTRBiomeGenTundra) {
            boolean flag = this.worldObj.canSnowAtBody(i, j, k, checkLight);
            return flag && LOTRBiomeGenTundra.isTundraSnowy(i, k);
        }
        if (biome instanceof LOTRBiome) {
            return j >= ((LOTRBiome)biome).getSnowHeight() && this.worldObj.canSnowAtBody(i, j, k, checkLight);
        }
        return this.worldObj.canSnowAtBody(i, j, k, checkLight);
    }

    private boolean canFreeze_ignoreTemp(int i, int j, int k, boolean isBlockUpdate) {
        Block block;
        if (j >= 0 && j < this.worldObj.getHeight() && this.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && ((block = this.worldObj.getBlock(i, j, k)) == Blocks.water || block == Blocks.flowing_water) && this.worldObj.getBlockMetadata(i, j, k) == 0) {
            if (!isBlockUpdate) {
                return true;
            }
            boolean surroundWater = true;
            if (surroundWater && this.worldObj.getBlock(i - 1, j, k).getMaterial() != Material.water) {
                surroundWater = false;
            }
            if (surroundWater && this.worldObj.getBlock(i + 1, j, k).getMaterial() != Material.water) {
                surroundWater = false;
            }
            if (surroundWater && this.worldObj.getBlock(i, j, k - 1).getMaterial() != Material.water) {
                surroundWater = false;
            }
            if (surroundWater && this.worldObj.getBlock(i, j, k + 1).getMaterial() != Material.water) {
                surroundWater = false;
            }
            if (!surroundWater) {
                return true;
            }
        }
        return false;
    }

    private boolean canSnow_ignoreTemp(int i, int j, int k, boolean checkLight) {
        if (!checkLight) {
            return true;
        }
        return j >= 0 && j < this.worldObj.getHeight() && this.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && (this.worldObj.getBlock(i, j, k)).getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, i, j, k);
    }

    public boolean shouldMapSpin(String entity, double x, double y, double z) {
        return false;
    }

    public void resetRainAndThunder() {
        super.resetRainAndThunder();
        if (LOTRMod.doDayCycle(this.worldObj)) {
            LOTRTime.advanceToMorning();
        }
    }

    public float calculateCelestialAngle(long time, float partialTick) {
        float daytime = (((int)(time % LOTRTime.DAY_LENGTH)) + partialTick) / LOTRTime.DAY_LENGTH - 0.25f;
        if (daytime < 0.0f) {
            daytime += 1.0f;
        }
        if (daytime > 1.0f) {
            daytime -= 1.0f;
        }
        float angle = 1.0f - (float)((Math.cos(daytime * 3.141592653589793) + 1.0) / 2.0);
        angle = daytime + (angle - daytime) / 3.0f;
        return angle;
    }

    public int getMoonPhase(long time) {
        return LOTRWorldProvider.getLOTRMoonPhase();
    }

    public static int getLOTRMoonPhase() {
        int day = LOTRDate.ShireReckoning.currentDay;
        return IntMath.mod(day, MOON_PHASES);
    }

    public static boolean isLunarEclipse() {
        int day = LOTRDate.ShireReckoning.currentDay;
        return LOTRWorldProvider.getLOTRMoonPhase() == 0 && IntMath.mod(day / MOON_PHASES, 4) == 3;
    }

    @SideOnly(value=Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        if (!LOTRModChecker.hasShaders() && LOTRConfig.enableLOTRSky) {
            if (this.lotrSkyRenderer == null) {
                this.lotrSkyRenderer = new LOTRSkyRenderer(this);
            }
            return this.lotrSkyRenderer;
        }
        return super.getSkyRenderer();
    }

    @SideOnly(value=Side.CLIENT)
    public IRenderHandler getWeatherRenderer() {
        if (this.lotrWeatherRenderer == null) {
            this.lotrWeatherRenderer = new LOTRWeatherRenderer();
        }
        return this.lotrWeatherRenderer;
    }

    @SideOnly(value=Side.CLIENT)
    public IRenderHandler getCloudRenderer() {
        if (!LOTRModChecker.hasShaders() && LOTRConfig.cloudRange > 0) {
            if (this.lotrCloudRenderer == null) {
                this.lotrCloudRenderer = new LOTRCloudRenderer();
            }
            return this.lotrCloudRenderer;
        }
        return super.getCloudRenderer();
    }

    @SideOnly(value=Side.CLIENT)
    public float getCloudHeight() {
        return 192.0f;
    }

    @SideOnly(value=Side.CLIENT)
    public Vec3 drawClouds(float f) {
        Minecraft mc = Minecraft.getMinecraft();
        int i = (int)mc.renderViewEntity.posX;
        int k = (int)mc.renderViewEntity.posZ;
        Vec3 clouds = super.drawClouds(f);
        this.cloudsB = 0.0;
        this.cloudsG = 0.0;
        this.cloudsR = 0.0;
        GameSettings settings = mc.gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i1 = -distance; i1 <= distance; ++i1) {
            for (int k1 = -distance; k1 <= distance; ++k1) {
                Vec3 tempClouds = Vec3.createVectorHelper(clouds.xCoord, clouds.yCoord, clouds.zCoord);
                BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i + i1, k + k1);
                if (biome instanceof LOTRBiome) {
                    ((LOTRBiome)biome).getCloudColor(tempClouds);
                }
                this.cloudsR += tempClouds.xCoord;
                this.cloudsG += tempClouds.yCoord;
                this.cloudsB += tempClouds.zCoord;
                ++l;
            }
        }
        this.cloudsR /= l;
        this.cloudsG /= l;
        this.cloudsB /= l;
        return Vec3.createVectorHelper(this.cloudsR, this.cloudsG, this.cloudsB);
    }

    @SideOnly(value=Side.CLIENT)
    public Vec3 getFogColor(float f, float f1) {
        Minecraft mc = Minecraft.getMinecraft();
        int i = (int)mc.renderViewEntity.posX;
        int k = (int)mc.renderViewEntity.posZ;
        Vec3 fog = super.getFogColor(f, f1);
        this.fogB = 0.0;
        this.fogG = 0.0;
        this.fogR = 0.0;
        GameSettings settings = mc.gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i1 = -distance; i1 <= distance; ++i1) {
            for (int k1 = -distance; k1 <= distance; ++k1) {
                Vec3 tempFog = Vec3.createVectorHelper(fog.xCoord, fog.yCoord, fog.zCoord);
                BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i + i1, k + k1);
                if (biome instanceof LOTRBiome) {
                    ((LOTRBiome)biome).getFogColor(tempFog);
                }
                this.fogR += tempFog.xCoord;
                this.fogG += tempFog.yCoord;
                this.fogB += tempFog.zCoord;
                ++l;
            }
        }
        this.fogR /= l;
        this.fogG /= l;
        this.fogB /= l;
        return Vec3.createVectorHelper(this.fogR, this.fogG, this.fogB);
    }

    public float[] modifyFogIntensity(float farPlane, int fogMode) {
        Minecraft mc = Minecraft.getMinecraft();
        int i = (int)mc.renderViewEntity.posX;
        int k = (int)mc.renderViewEntity.posZ;
        float fogStart = 0.0f;
        float fogEnd = 0.0f;
        GameSettings settings = mc.gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        int l = 0;
        for (int i1 = -distance; i1 <= distance; ++i1) {
            for (int k1 = -distance; k1 <= distance; ++k1) {
                float thisFogStart = 0.0f;
                float thisFogEnd = 0.0f;
                boolean foggy = this.doesXZShowFog(i + i1, k + k1);
                if (foggy) {
                    thisFogStart = farPlane * 0.05f;
                    thisFogEnd = Math.min(farPlane, 192.0f) * 0.5f;
                } else if (fogMode < 0) {
                    thisFogStart = 0.0f;
                    thisFogEnd = farPlane;
                } else {
                    thisFogStart = farPlane * 0.75f;
                    thisFogEnd = farPlane;
                }
                fogStart += thisFogStart;
                fogEnd += thisFogEnd;
                ++l;
            }
        }
        return new float[]{fogStart /= l, fogEnd /= l};
    }

    public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
        return rgb;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean doesXZShowFog(int i, int k) {
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiome) {
            return ((LOTRBiome)biome).hasFog();
        }
        return super.doesXZShowFog(i, k);
    }
}

