package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenNearHaradFertileForest extends LOTRBiomeGenNearHaradFertile {
    public LOTRBiomeGenNearHaradFertileForest(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 6;
        this.decorator.addTree(LOTRTreeType.CEDAR, 6000);
        this.decorator.addTree(LOTRTreeType.CEDAR_LARGE, 1500);
        this.decorator.clearRandomStructures();
        this.decorator.clearVillages();
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 1.0f;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
