package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenUmbarForest extends LOTRBiomeGenUmbar {
    public LOTRBiomeGenUmbarForest(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 7;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 12;
        this.decorator.doubleGrassPerChunk = 4;
        this.registerForestFlowers();
        this.decorator.clearVillages();
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
