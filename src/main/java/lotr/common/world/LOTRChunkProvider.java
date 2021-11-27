package lotr.common.world;

import java.util.*;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.*;
import lotr.common.world.map.*;
import lotr.common.world.mapgen.*;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import lotr.common.world.mapgen.tpyr.LOTRMapGenTauredainPyramid;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.block.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenStructure;

public class LOTRChunkProvider
implements IChunkProvider {
    private World worldObj;
    private Random rand;
    private BiomeGenBase[] biomesForGeneration;
    private LOTRBiomeVariant[] variantsForGeneration;
    private int biomeSampleRadius;
    private int biomeSampleWidth;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen5;
    private NoiseGeneratorOctaves noiseGen6;
    private NoiseGeneratorOctaves stoneNoiseGen;
    private double[] noise1;
    private double[] noise2;
    private double[] noise3;
    private double[] noise5;
    private double[] noise6;
    private double[] stoneNoise = new double[256];
    private double[] heightNoise;
    private float[] biomeHeightNoise;
    private double[] blockHeightNoiseArray;
    private LOTRMapGenCaves caveGenerator = new LOTRMapGenCaves();
    private MapGenBase ravineGenerator = new LOTRMapGenRavine();
    private MapGenStructure dwarvenMineGenerator = new LOTRMapGenDwarvenMine();
    private MapGenStructure tauredainPyramid = new LOTRMapGenTauredainPyramid();
    public static final int seaLevel = 62;

    public LOTRChunkProvider(World world, long seed) {
        this.worldObj = world;
        this.rand = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.stoneNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.biomeSampleRadius = 6;
        this.biomeSampleWidth = 2 * this.biomeSampleRadius + 1;
        this.biomeHeightNoise = new float[this.biomeSampleWidth * this.biomeSampleWidth];
        for (int i = -this.biomeSampleRadius; i <= this.biomeSampleRadius; ++i) {
            for (int k = -this.biomeSampleRadius; k <= this.biomeSampleRadius; ++k) {
                this.biomeHeightNoise[i + this.biomeSampleRadius + (k + this.biomeSampleRadius) * this.biomeSampleWidth] = 10.0f / MathHelper.sqrt_float(i * i + k * k + 0.2f);
            }
        }
    }

    private void generateTerrain(int i, int j, Block[] blocks, ChunkFlags chunkFlags) {
        LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        int byte0 = 4;
        int byte1 = 32;
        int k = byte0 + 1;
        int byte3 = 33;
        int l = byte0 + 1;
        this.biomesForGeneration = chunkManager.getBiomesForGeneration(this.biomesForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth);
        this.variantsForGeneration = chunkManager.getVariantsChunkGen(this.variantsForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth, this.biomesForGeneration);
        this.heightNoise = this.initializeHeightNoise(this.heightNoise, i * byte0, 0, j * byte0, k, byte3, l, chunkFlags);
        this.blockHeightNoiseArray = new double[blocks.length];
        for (int i1 = 0; i1 < byte0; ++i1) {
            for (int j1 = 0; j1 < byte0; ++j1) {
                for (int k1 = 0; k1 < byte1; ++k1) {
                    double d = 0.125;
                    double d1 = this.heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 0];
                    double d2 = this.heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 0];
                    double d3 = this.heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 0];
                    double d4 = this.heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 0];
                    double d5 = (this.heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 1] - d1) * d;
                    double d6 = (this.heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 1] - d2) * d;
                    double d7 = (this.heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 1] - d3) * d;
                    double d8 = (this.heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 1] - d4) * d;
                    for (int l1 = 0; l1 < 8; ++l1) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i2 = 0; i2 < 4; ++i2) {
                            int j2 = i2 + i1 * 4 << 12 | 0 + j1 * 4 << 8 | k1 * 8 + l1;
                            double d14 = 0.25;
                            double d15 = (d11 - d10) * d14;
                            for (int k2 = 0; k2 < 4; ++k2) {
                                double blockHeightNoise;
                                int blockIndex = j2 + k2 * 256;
                                this.blockHeightNoiseArray[blockIndex] = blockHeightNoise = d10 + d15 * k2;
                                blocks[blockIndex] = blockHeightNoise > 0.0 ? Blocks.stone : (k1 * 8 + l1 <= 62 ? Blocks.water : Blocks.air);
                            }
                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    private void replaceBlocksForBiome(int i, int k, Block[] blocks, byte[] metadata, BiomeGenBase[] biomeArray, LOTRBiomeVariant[] variantArray, ChunkFlags chunkFlags) {
        double d = 0.03125;
        this.stoneNoise = this.stoneNoiseGen.generateNoiseOctaves(this.stoneNoise, i * 16, k * 16, 0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        int ySize = blocks.length / 256;
        for (int i1 = 0; i1 < 16; ++i1) {
            for (int k1 = 0; k1 < 16; ++k1) {
                int index;
                int x = i * 16 + i1;
                int z = k * 16 + k1;
                int xzIndex = i1 * 16 + k1;
                int xzIndexBiome = i1 + k1 * 16;
                LOTRBiome biome = (LOTRBiome)biomeArray[xzIndexBiome];
                LOTRBiomeVariant variant = variantArray[xzIndexBiome];
                int height = 0;
                for (int j = ySize - 1; j >= 0; --j) {
                    int index2 = xzIndex * ySize + j;
                    Block block2 = blocks[index2];
                    if (!block2.isOpaqueCube()) continue;
                    height = j;
                    break;
                }
                biome.generateBiomeTerrain(this.worldObj, this.rand, blocks, metadata, x, z, this.stoneNoise[xzIndex], height, variant);
                if (!LOTRFixedStructures.hasMapFeatures(this.worldObj)) continue;
                chunkFlags.roadFlags[xzIndex] = LOTRRoadGenerator.generateRoad(this.worldObj, this.rand, x, z, biome, blocks, metadata, this.blockHeightNoiseArray);
                int lavaHeight = LOTRMountains.getLavaHeight(x, z);
                if (lavaHeight <= 0) continue;
                for (int j = lavaHeight; j >= 0 && !(blocks[index = xzIndex * ySize + j]).isOpaqueCube(); --j) {
                    blocks[index] = Blocks.lava;
                    metadata[index] = 0;
                }
            }
        }
    }

    public Chunk loadChunk(int i, int k) {
        return this.provideChunk(i, k);
    }

    public Chunk provideChunk(int i, int k) {
        this.rand.setSeed(i * 341873128712L + k * 132897987541L);
        LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        Block[] blocks = new Block[65536];
        byte[] metadata = new byte[65536];
        ChunkFlags chunkFlags = new ChunkFlags();
        this.generateTerrain(i, k, blocks, chunkFlags);
        this.biomesForGeneration = chunkManager.loadBlockGeneratorData(this.biomesForGeneration, i * 16, k * 16, 16, 16);
        this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
        this.replaceBlocksForBiome(i, k, blocks, metadata, this.biomesForGeneration, this.variantsForGeneration, chunkFlags);
        this.caveGenerator.chunkFlags = chunkFlags;
        this.caveGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
        this.ravineGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
        this.dwarvenMineGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
        this.tauredainPyramid.func_151539_a(this, this.worldObj, i, k, blocks);
        Chunk chunk = new Chunk(this.worldObj, i, k);
        ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
        for (int i1 = 0; i1 < 16; ++i1) {
            for (int k1 = 0; k1 < 16; ++k1) {
                for (int j1 = 0; j1 < 256; ++j1) {
                    int blockIndex = i1 << 12 | k1 << 8 | j1;
                    Block block = blocks[blockIndex];
                    if (block == null || block == Blocks.air) continue;
                    byte meta = metadata[blockIndex];
                    int j2 = j1 >> 4;
                    if (blockStorage[j2] == null) {
                        blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                    }
                    blockStorage[j2].func_150818_a(i1, j1 & 0xF, k1, block);
                    blockStorage[j2].setExtBlockMetadata(i1, j1 & 0xF, k1, meta & 0xF);
                }
            }
        }
        byte[] biomes = chunk.getBiomeArray();
        for (int l = 0; l < biomes.length; ++l) {
            biomes[l] = (byte)this.biomesForGeneration[l].biomeID;
        }
        byte[] variants = new byte[256];
        for (int l = 0; l < variants.length; ++l) {
            variants[l] = (byte)this.variantsForGeneration[l].variantID;
        }
        LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
        chunk.generateSkylightMap();
        LOTRFixedStructures.nanoTimeElapsed = 0L;
        return chunk;
    }

    private double[] initializeHeightNoise(double[] noise, int i, int j, int k, int xSize, int ySize, int zSize, ChunkFlags chunkFlags) {
        if (noise == null) {
            noise = new double[xSize * ySize * zSize];
        }
        double xzNoiseScale = 400.0;
        double heightStretch = 6.0;
        int noiseCentralIndex = (xSize - 1) / 2 + this.biomeSampleRadius + ((zSize - 1) / 2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
        LOTRBiome noiseCentralBiome = (LOTRBiome)this.biomesForGeneration[noiseCentralIndex];
        if (noiseCentralBiome.biomeTerrain.hasXZScale()) {
            xzNoiseScale = noiseCentralBiome.biomeTerrain.getXZScale();
        }
        if (noiseCentralBiome.biomeTerrain.hasHeightStretchFactor()) {
            heightStretch *= noiseCentralBiome.biomeTerrain.getHeightStretchFactor();
        }
        this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, i, k, xSize, zSize, 1.121, 1.121, 0.5);
        this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, i, k, xSize, zSize, 200.0, 200.0, 0.5);
        this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, i, j, k, xSize, ySize, zSize, 684.412 / xzNoiseScale, 2.0E-4, 684.412 / xzNoiseScale);
        this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, i, j, k, xSize, ySize, zSize, 684.412, 1.0, 684.412);
        this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, i, j, k, xSize, ySize, zSize, 684.412, 1.0, 684.412);
        int noiseIndexXZ = 0;
        int noiseIndex = 0;
        for (int i1 = 0; i1 < xSize; ++i1) {
            for (int k1 = 0; k1 < zSize; ++k1) {
                double heightNoise;
                int xPos = i + i1 << 2;
                int zPos = k + k1 << 2;
                float totalBaseHeight = 0.0f;
                float totalHeightVariation = 0.0f;
                float totalHeightNoise = 0.0f;
                float totalVariantHillFactor = 0.0f;
                float totalFlatBiomeHeight = 0.0f;
                int biomeCount = 0;
                int centreBiomeIndex = i1 + this.biomeSampleRadius + (k1 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
                BiomeGenBase centreBiome = this.biomesForGeneration[centreBiomeIndex];
                LOTRBiomeVariant centreVariant = this.variantsForGeneration[centreBiomeIndex];
                float centreHeight = centreBiome.rootHeight + centreVariant.getHeightBoostAt(xPos += 2, zPos += 2);
                if (centreVariant.absoluteHeight) {
                    centreHeight = centreVariant.getHeightBoostAt(xPos, zPos);
                }
                for (int i2 = -this.biomeSampleRadius; i2 <= this.biomeSampleRadius; ++i2) {
                    for (int k2 = -this.biomeSampleRadius; k2 <= this.biomeSampleRadius; ++k2) {
                        int biomeIndex = i1 + i2 + this.biomeSampleRadius + (k1 + k2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
                        BiomeGenBase biome = this.biomesForGeneration[biomeIndex];
                        LOTRBiomeVariant variant = this.variantsForGeneration[biomeIndex];
                        int xPosHere = xPos + (i2 << 2);
                        int zPosHere = zPos + (k2 << 2);
                        float baseHeight = biome.rootHeight + variant.getHeightBoostAt(xPosHere, zPosHere);
                        float heightVariation = biome.heightVariation * variant.hillFactor;
                        if (variant.absoluteHeight) {
                            baseHeight = variant.getHeightBoostAt(xPosHere, zPosHere);
                            heightVariation = variant.hillFactor;
                        }
                        float hillFactor = variant.hillFactor;
                        float baseHeightPlus = baseHeight + 2.0f;
                        if (baseHeightPlus == 0.0f) {
                            baseHeightPlus = 0.001f;
                        }
                        float heightNoise2 = this.biomeHeightNoise[i2 + this.biomeSampleRadius + (k2 + this.biomeSampleRadius) * this.biomeSampleWidth] / baseHeightPlus / 2.0f;
                        heightNoise2 = Math.abs(heightNoise2);
                        if (baseHeight > centreHeight) {
                            heightNoise2 /= 2.0f;
                        }
                        totalBaseHeight += baseHeight * heightNoise2;
                        totalHeightVariation += heightVariation * heightNoise2;
                        totalHeightNoise += heightNoise2;
                        totalVariantHillFactor += hillFactor;
                        float flatBiomeHeight = biome.rootHeight;
                        boolean isWater = ((LOTRBiome)biome).isWateryBiome();
                        if (variant.absoluteHeight && variant.absoluteHeightLevel < 0.0f) {
                            isWater = true;
                        }
                        if (isWater) {
                            flatBiomeHeight = baseHeight;
                        }
                        totalFlatBiomeHeight += flatBiomeHeight;
                        ++biomeCount;
                    }
                }
                float avgBaseHeight = totalBaseHeight / totalHeightNoise;
                float avgHeightVariation = totalHeightVariation / totalHeightNoise;
                float avgFlatBiomeHeight = totalFlatBiomeHeight / biomeCount;
                float avgVariantHillFactor = totalVariantHillFactor / biomeCount;
                if (LOTRFixedStructures.hasMapFeatures(this.worldObj)) {
                    float mountain;
                    float roadNear = LOTRRoads.isRoadNear(xPos, zPos, 32);
                    if (roadNear >= 0.0f) {
                        float interpFactor = roadNear;
                        avgBaseHeight = avgFlatBiomeHeight + (avgBaseHeight - avgFlatBiomeHeight) * interpFactor;
                        avgHeightVariation *= interpFactor;
                    }
                    if ((mountain = LOTRMountains.getTotalHeightBoost(xPos, zPos)) > 0.005f) {
                        avgBaseHeight += mountain;
                        float mtnV = 0.2f;
                        float dv = avgHeightVariation - mtnV;
                        avgHeightVariation = mtnV + dv / (1.0f + mountain);
                    }
                }
                if (centreBiome instanceof LOTRBiome) {
                    LOTRBiome lb = (LOTRBiome)centreBiome;
                    lb.decorator.checkForVillages(this.worldObj, xPos, zPos, chunkFlags);
                    if (chunkFlags.isFlatVillage) {
                        avgBaseHeight = avgFlatBiomeHeight;
                        avgHeightVariation = 0.0f;
                    }
                }
                avgBaseHeight = (avgBaseHeight * 4.0f - 1.0f) / 8.0f;
                if (avgHeightVariation == 0.0f) {
                    avgHeightVariation = 0.001f;
                }
                if ((heightNoise = this.noise6[noiseIndexXZ] / 8000.0) < 0.0) {
                    heightNoise = -heightNoise * 0.3;
                }
                if ((heightNoise = heightNoise * 3.0 - 2.0) < 0.0) {
                    if ((heightNoise /= 2.0) < -1.0) {
                        heightNoise = -1.0;
                    }
                    heightNoise /= 1.4;
                    heightNoise /= 2.0;
                } else {
                    if (heightNoise > 1.0) {
                        heightNoise = 1.0;
                    }
                    heightNoise /= 8.0;
                }
                ++noiseIndexXZ;
                for (int j1 = 0; j1 < ySize; ++j1) {
                    double baseHeight = avgBaseHeight;
                    double heightVariation = avgHeightVariation;
                    baseHeight += heightNoise * 0.2 * avgVariantHillFactor;
                    baseHeight = baseHeight * ySize / 16.0;
                    double var28 = ySize / 2.0 + baseHeight * 4.0;
                    double totalNoise;
                    double var32 = (j1 - var28) * heightStretch * 128.0 / 256.0 / heightVariation;
                    if (var32 < 0.0) {
                        var32 *= 4.0;
                    }
                    double var34 = this.noise1[noiseIndex] / 512.0;
                    double var36 = this.noise2[noiseIndex] / 512.0;
                    double var38 = (this.noise3[noiseIndex] / 10.0 + 1.0) / 2.0 * avgVariantHillFactor;
                    totalNoise = var38 < 0.0 ? var34 : (var38 > 1.0 ? var36 : var34 + (var36 - var34) * var38);
                    totalNoise -= var32;
                    if (j1 > ySize - 4) {
                        double var40 = (j1 - (ySize - 4)) / 3.0f;
                        totalNoise = totalNoise * (1.0 - var40) + -10.0 * var40;
                    }
                    noise[noiseIndex] = totalNoise;
                    ++noiseIndex;
                }
            }
        }
        return noise;
    }

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        int i1;
        int j1;
        int k1;
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        LOTRBiome biome = (LOTRBiome)this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        LOTRBiomeVariant variant = ((LOTRWorldChunkManager)this.worldObj.getWorldChunkManager()).getBiomeVariantAt(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(i * l1 + j * l2 ^ this.worldObj.getSeed());
        this.dwarvenMineGenerator.generateStructuresInChunk(this.worldObj, this.rand, i, j);
        this.tauredainPyramid.generateStructuresInChunk(this.worldObj, this.rand, i, j);
        if (this.rand.nextInt(4) == 0) {
            i1 = k + this.rand.nextInt(16) + 8;
            j1 = this.rand.nextInt(128);
            k1 = l + this.rand.nextInt(16) + 8;
            if (j1 < 60) {
                new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, i1, j1, k1);
            }
        }
        if (this.rand.nextInt(8) == 0) {
            i1 = k + this.rand.nextInt(16) + 8;
            j1 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            k1 = l + this.rand.nextInt(16) + 8;
            if (j1 < 60) {
                new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, i1, j1, k1);
            }
        }
        biome.decorate(this.worldObj, this.rand, k, l);
        if (biome.getChanceToSpawnAnimals() <= 1.0f) {
            if (this.rand.nextFloat() < biome.getChanceToSpawnAnimals()) {
                LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
            }
        } else {
            int spawns = MathHelper.floor_double(biome.getChanceToSpawnAnimals());
            for (int i12 = 0; i12 < spawns; ++i12) {
                LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
            }
        }
        k += 8;
        l += 8;
        for (i1 = 0; i1 < 16; ++i1) {
            for (int k12 = 0; k12 < 16; ++k12) {
                int j12 = this.worldObj.getPrecipitationHeight(k + i1, l + k12);
                if (this.worldObj.isBlockFreezable(i1 + k, j12 - 1, k12 + l)) {
                    this.worldObj.setBlock(i1 + k, j12 - 1, k12 + l, Blocks.ice, 0, 2);
                }
                if (!this.worldObj.func_147478_e(i1 + k, j12, k12 + l, true)) continue;
                this.worldObj.setBlock(i1 + k, j12, k12 + l, Blocks.snow_layer, 0, 2);
            }
        }
        BlockSand.fallInstantly = false;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate update) {
        return true;
    }

    public void saveExtraData() {
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "MiddleEarthLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, int i, int j, int k) {
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        return biome == null ? null : biome.getSpawnableList(creatureType);
    }

    public ChunkPosition func_147416_a(World world, String type, int i, int j, int k) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int i, int k) {
        this.dwarvenMineGenerator.func_151539_a(this, this.worldObj, i, k, null);
        this.tauredainPyramid.func_151539_a(this, this.worldObj, i, k, null);
    }

    public static class ChunkFlags {
        public boolean isVillage = false;
        public boolean isFlatVillage = false;
        public boolean[] roadFlags = new boolean[256];

        private ChunkFlags() {
        }
    }

}

