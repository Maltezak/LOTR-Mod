package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.*;
import net.minecraft.world.World;

public class LOTRGenLayerNearHaradRiverbanks extends LOTRGenLayer {
    private LOTRGenLayer biomeLayer;
    private LOTRGenLayer mapRiverLayer;
    private LOTRDimension dimension;

    public LOTRGenLayerNearHaradRiverbanks(long l, LOTRGenLayer biomes, LOTRGenLayer rivers, LOTRDimension dim) {
        super(l);
        this.biomeLayer = biomes;
        this.mapRiverLayer = rivers;
        this.dimension = dim;
    }

    @Override
    public void initWorldGenSeed(long l) {
        super.initWorldGenSeed(l);
        this.biomeLayer.initWorldGenSeed(l);
        this.mapRiverLayer.initWorldGenSeed(l);
    }

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] biomes = this.biomeLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
        int[] mapRivers = this.mapRiverLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for(int k1 = 0; k1 < zSize; ++k1) {
            for(int i1 = 0; i1 < xSize; ++i1) {
                this.initChunkSeed(i + i1, k + k1);
                int biomeID = biomes[i1 + 2 + (k1 + 2) * (xSize + 3)];
                LOTRBiome biome = this.dimension.biomeList[biomeID];
                int newBiomeID = biomeID;
                if(biome instanceof LOTRBiomeGenNearHarad) {
                    boolean adjRiver = false;
                    for(int i2 = -2; i2 <= 1; ++i2) {
                        for(int k2 = -2; k2 <= 1; ++k2) {
                            if((mapRivers[i1 + 2 + i2 + (k1 + 2 + k2) * (xSize + 3)]) != 2) continue;
                            adjRiver = true;
                        }
                    }
                    if(adjRiver) {
                        newBiomeID = LOTRBiome.nearHaradRiverbank.biomeID;
                    }
                }
                ints[i1 + k1 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
