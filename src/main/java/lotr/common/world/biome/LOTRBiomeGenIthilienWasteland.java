package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenIthilienWasteland extends LOTRBiomeGenIthilien {
    public LOTRBiomeGenIthilienWasteland(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.clearBiomeVariants();
        this.variantChance = 0.7f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.decorator.logsPerChunk = 2;
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        this.decorator.addTree(LOTRTreeType.LEBETHRON_DEAD, 200);
        this.decorator.addTree(LOTRTreeType.BIRCH_DEAD, 50);
    }
}
