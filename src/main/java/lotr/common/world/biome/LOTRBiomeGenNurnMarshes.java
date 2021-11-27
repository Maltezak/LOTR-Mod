package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenNurnMarshes extends LOTRBiomeGenNurn {
    public LOTRBiomeGenNurnMarshes(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        this.decorator.sandPerChunk = 0;
        this.decorator.quagmirePerChunk = 1;
        this.decorator.treesPerChunk = 0;
        this.decorator.willowPerChunk = 1;
        this.decorator.logsPerChunk = 2;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 10;
        this.decorator.enableFern = true;
        this.decorator.canePerChunk = 10;
        this.decorator.reedPerChunk = 4;
        this.decorator.clearRandomStructures();
    }
}
