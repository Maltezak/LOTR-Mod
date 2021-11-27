package lotr.common.world.biome.variant;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRDimension;
import lotr.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;

public class LOTRBiomeVariantStorage {
    private static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMap = new HashMap<>();
    private static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMapClient = new HashMap<>();

    private static Map<ChunkCoordIntPair, byte[]> getDimensionChunkMap(World world) {
        LOTRDimension dim;
        Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> sourcemap = !world.isRemote ? chunkVariantMap : chunkVariantMapClient;
        Map<ChunkCoordIntPair, byte[]> map = sourcemap.get(dim = LOTRDimension.getCurrentDimensionWithFallback(world));
        if (map == null) {
            map = new HashMap<>();
            sourcemap.put(dim, map);
        }
        return map;
    }

    private static ChunkCoordIntPair getChunkKey(Chunk chunk) {
        return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
    }

    public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
        return LOTRBiomeVariantStorage.getChunkBiomeVariants(world, LOTRBiomeVariantStorage.getChunkKey(chunk));
    }

    public static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        return LOTRBiomeVariantStorage.getDimensionChunkMap(world).get(chunk);
    }

    public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
        LOTRBiomeVariantStorage.setChunkBiomeVariants(world, LOTRBiomeVariantStorage.getChunkKey(chunk), variants);
    }

    public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
        LOTRBiomeVariantStorage.getDimensionChunkMap(world).put(chunk, variants);
    }

    public static void clearChunkBiomeVariants(World world, Chunk chunk) {
        LOTRBiomeVariantStorage.clearChunkBiomeVariants(world, LOTRBiomeVariantStorage.getChunkKey(chunk));
    }

    public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        LOTRBiomeVariantStorage.getDimensionChunkMap(world).remove(chunk);
    }

    public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        if (LOTRBiomeVariantStorage.getChunkBiomeVariants(world, chunk) == null) {
            byte[] variants = data.hasKey("LOTRBiomeVariants") ? data.getByteArray("LOTRBiomeVariants") : new byte[256];
            LOTRBiomeVariantStorage.setChunkBiomeVariants(world, chunk, variants);
        }
    }

    public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        byte[] variants = LOTRBiomeVariantStorage.getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            data.setByteArray("LOTRBiomeVariants", variants);
        }
    }

    public static void clearAllVariants(World world) {
        LOTRBiomeVariantStorage.getDimensionChunkMap(world).clear();
        FMLLog.info("Unloading LOTR biome variants in %s", LOTRDimension.getCurrentDimensionWithFallback(world).dimensionName);
    }

    public static void performCleanup(WorldServer world) {
        Map<ChunkCoordIntPair, byte[]> dimensionMap = LOTRBiomeVariantStorage.getDimensionChunkMap(world);
        dimensionMap.size();
        System.nanoTime();
        ArrayList<ChunkCoordIntPair> removalChunks = new ArrayList<>();
        for (ChunkCoordIntPair chunk : dimensionMap.keySet()) {
            if (world.theChunkProviderServer.chunkExists(chunk.chunkXPos, chunk.chunkZPos)) continue;
            removalChunks.add(chunk);
        }
        for (ChunkCoordIntPair chunk : removalChunks) {
            dimensionMap.remove(chunk);
        }
    }

    public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
        byte[] variants = LOTRBiomeVariantStorage.getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            LOTRPacketBiomeVariantsWatch packet = new LOTRPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition, variants);
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
        } else {
            String dimName = world.provider.getDimensionName();
            int posX = chunk.xPosition << 4;
            int posZ = chunk.zPosition << 4;
            String playerName = entityplayer.getCommandSenderName();
            FMLLog.severe("Could not find LOTR biome variants for %s chunk at %d, %d; requested by %s", dimName, posX, posZ, playerName);
        }
    }

    public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
        LOTRPacketBiomeVariantsUnwatch packet = new LOTRPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public static int getSize(World world) {
        Map<ChunkCoordIntPair, byte[]> map = LOTRBiomeVariantStorage.getDimensionChunkMap(world);
        return map.size();
    }
}

