package lotr.common.world.village;

import java.util.*;

import net.minecraft.world.ChunkCoordIntPair;

public class LOTRVillagePositionCache {
    private Map<ChunkCoordIntPair, LocationInfo> cacheMap = new HashMap<>();
    public LocationInfo markResult(int chunkX, int chunkZ, LocationInfo result) {
        if (this.cacheMap.size() >= 20000) {
            this.clearCache();
        }
        this.cacheMap.put(this.getChunkKey(chunkX, chunkZ), result);
        return result;
    }

    public LocationInfo getLocationAt(int chunkX, int chunkZ) {
        LocationInfo loc = this.cacheMap.get(this.getChunkKey(chunkX, chunkZ));
        return loc;
    }

    private ChunkCoordIntPair getChunkKey(int chunkX, int chunkZ) {
        return new ChunkCoordIntPair(chunkX, chunkZ);
    }

    public void clearCache() {
        this.cacheMap.clear();
    }
}

