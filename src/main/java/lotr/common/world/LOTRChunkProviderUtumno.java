package lotr.common.world;

import java.util.*;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.*;
import lotr.common.world.mapgen.*;
import net.minecraft.block.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class LOTRChunkProviderUtumno implements IChunkProvider {
    private World worldObj;
    private Random rand;
    private BiomeGenBase[] biomesForGeneration;
    private LOTRBiomeVariant[] variantsForGeneration;
    private LOTRMapGenCaves caveGenerator = new LOTRMapGenCavesUtumno();

    public LOTRChunkProviderUtumno(World world, long l) {
        this.worldObj = world;
        this.rand = new Random(l);
        LOTRUtumnoLevel.setupLevels();
    }

    private void generateTerrain(int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
        Arrays.fill(blocks, Blocks.air);
        LOTRUtumnoLevel.generateTerrain(this.worldObj, this.rand, chunkX, chunkZ, blocks, metadata);
    }

    @Override
    public Chunk loadChunk(int i, int j) {
        return this.provideChunk(i, j);
    }

    @Override
    public Chunk provideChunk(int i, int k) {
        this.rand.setSeed(i * 341873128712L + k * 132897987541L);
        LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager) this.worldObj.getWorldChunkManager();
        Block[] blocks = new Block[65536];
        byte[] metadata = new byte[65536];
        this.generateTerrain(i, k, blocks, metadata);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, i * 16, k * 16, 16, 16);
        this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
        this.caveGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
        Chunk chunk = new Chunk(this.worldObj, i, k);
        ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
        for(int i1 = 0; i1 < 16; ++i1) {
            for(int k1 = 0; k1 < 16; ++k1) {
                for(int j1 = 0; j1 < 256; ++j1) {
                    int blockIndex = i1 << 12 | k1 << 8 | j1;
                    Block block = blocks[blockIndex];
                    if(block == null || block == Blocks.air) continue;
                    byte meta = metadata[blockIndex];
                    int j2 = j1 >> 4;
                    if(blockStorage[j2] == null) {
                        blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                    }
                    blockStorage[j2].func_150818_a(i1, j1 & 0xF, k1, block);
                    blockStorage[j2].setExtBlockMetadata(i1, j1 & 0xF, k1, meta & 0xF);
                }
            }
        }
        byte[] biomes = chunk.getBiomeArray();
        for(int l = 0; l < biomes.length; ++l) {
            biomes[l] = (byte) this.biomesForGeneration[l].biomeID;
        }
        byte[] variants = new byte[256];
        for(int l = 0; l < variants.length; ++l) {
            variants[l] = (byte) this.variantsForGeneration[l].variantID;
        }
        LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
        chunk.resetRelightChecks();
        return chunk;
    }

    @Override
    public boolean chunkExists(int i, int j) {
        return true;
    }

    @Override
    public void populate(IChunkProvider ichunkprovider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        int i = chunkX * 16;
        int k = chunkZ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(i + 16, k + 16);
        if(!(biomegenbase instanceof LOTRBiome)) {
            return;
        }
        LOTRBiome biome = (LOTRBiome) biomegenbase;
        this.rand.setSeed(this.worldObj.getSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * l1 + chunkZ * l2 ^ this.worldObj.getSeed());
        biome.decorate(this.worldObj, this.rand, i, k);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate update) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "UtumnoLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, int i, int j, int k) {
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        return biome == null ? null : biome.getSpawnableList(creatureType);
    }

    @Override
    public ChunkPosition func_147416_a(World world, String type, int i, int j, int k) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int i, int j) {
    }
}
