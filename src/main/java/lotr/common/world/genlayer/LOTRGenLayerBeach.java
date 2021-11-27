package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRGenLayerBeach extends LOTRGenLayer {
    private LOTRDimension dimension;
    private BiomeGenBase targetBiome;

    public LOTRGenLayerBeach(long l, LOTRGenLayer layer, LOTRDimension dim, BiomeGenBase target) {
        super(l);
        this.lotrParent = layer;
        this.dimension = dim;
        this.targetBiome = target;
    }

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] biomes = this.lotrParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for(int k1 = 0; k1 < zSize; ++k1) {
            for(int i1 = 0; i1 < xSize; ++i1) {
                this.initChunkSeed(i + i1, k + k1);
                int biomeID = biomes[i1 + 1 + (k1 + 1) * (xSize + 2)];
                LOTRBiome biome = this.dimension.biomeList[biomeID];
                int newBiomeID = biomeID;
                if(biomeID != this.targetBiome.biomeID && !biome.isWateryBiome()) {
                    int biome1 = biomes[i1 + 1 + (k1 + 1 - 1) * (xSize + 2)];
                    int biome2 = biomes[i1 + 1 + 1 + (k1 + 1) * (xSize + 2)];
                    int biome3 = biomes[i1 + 1 - 1 + (k1 + 1) * (xSize + 2)];
                    int biome4 = biomes[i1 + 1 + (k1 + 1 + 1) * (xSize + 2)];
                    if(biome1 == this.targetBiome.biomeID || biome2 == this.targetBiome.biomeID || biome3 == this.targetBiome.biomeID || biome4 == this.targetBiome.biomeID) {
                        if(biome instanceof LOTRBiomeGenLindon) {
                            newBiomeID = this.nextInt(3) == 0 ? LOTRBiome.lindonCoast.biomeID : LOTRBiome.beachWhite.biomeID;
                        }
                        else if(biome instanceof LOTRBiomeGenForodwaith) {
                            newBiomeID = LOTRBiome.forodwaithCoast.biomeID;
                        }
                        else if(biome instanceof LOTRBiomeGenFarHaradCoast) {
                            newBiomeID = biomeID;
                        }
                        else if(biome instanceof LOTRBiomeGenFarHaradVolcano) {
                            newBiomeID = biomeID;
                        }
                        else if(!(biome instanceof LOTRBiomeGenBeach)) {
                            newBiomeID = biome.decorator.whiteSand ? LOTRBiome.beachWhite.biomeID : (this.nextInt(20) == 0 ? LOTRBiome.beachGravel.biomeID : LOTRBiome.beach.biomeID);
                        }
                    }
                }
                ints[i1 + k1 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
