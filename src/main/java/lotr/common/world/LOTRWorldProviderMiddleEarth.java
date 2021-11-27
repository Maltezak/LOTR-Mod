package lotr.common.world;

import lotr.common.*;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRWorldProviderMiddleEarth extends LOTRWorldProvider {
    @Override
    public LOTRDimension getLOTRDimension() {
        return LOTRDimension.MIDDLE_EARTH;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new LOTRChunkProvider(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(LOTRLevelData.middleEarthPortalX, LOTRLevelData.middleEarthPortalY, LOTRLevelData.middleEarthPortalZ);
    }

    @Override
    public void setSpawnPoint(int i, int j, int k) {
    }

    public void setRingPortalLocation(int i, int j, int k) {
        LOTRLevelData.markMiddleEarthPortalLocation(i, j, k);
    }
}
