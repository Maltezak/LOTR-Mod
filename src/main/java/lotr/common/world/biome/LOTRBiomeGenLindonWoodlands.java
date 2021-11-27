package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenLindonWoodlands extends LOTRBiomeGenLindon {
    public LOTRBiomeGenLindonWoodlands(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 6;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 3;
        this.registerForestFlowers();
    }
}
