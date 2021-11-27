package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenGulfHaradForest extends LOTRBiomeGenGulfHarad {
    public LOTRBiomeGenGulfHaradForest(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 5;
        this.decorator.addTree(LOTRTreeType.DRAGONBLOOD, 1000);
        this.decorator.addTree(LOTRTreeType.DRAGONBLOOD_LARGE, 400);
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
