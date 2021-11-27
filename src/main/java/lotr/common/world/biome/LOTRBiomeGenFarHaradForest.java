package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenFarHaradForest extends LOTRBiomeGenFarHaradSavannah {
    public LOTRBiomeGenFarHaradForest(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.variantChance = 0.4f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 7;
        this.decorator.vinesPerChunk = 10;
        this.decorator.logsPerChunk = 3;
        this.decorator.grassPerChunk = 8;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 3;
        this.decorator.melonPerChunk = 0.08f;
        this.biomeColors.setGrass(11659848);
        this.biomeColors.setFoliage(8376636);
    }
}
