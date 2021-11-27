package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenShireWoodlands extends LOTRBiomeGenShire {
    public LOTRBiomeGenShireWoodlands(int i, boolean major) {
        super(i, major);
        this.variantChance = 0.2f;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.decorator.treesPerChunk = 9;
        this.decorator.flowersPerChunk = 6;
        this.decorator.doubleFlowersPerChunk = 2;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.enableFern = true;
        this.decorator.addTree(LOTRTreeType.BIRCH, 250);
        this.decorator.addTree(LOTRTreeType.SHIRE_PINE, 2500);
        this.decorator.addTree(LOTRTreeType.ASPEN, 300);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 100);
        this.addFlower(LOTRMod.shireHeather, 0, 20);
        this.biomeColors.resetGrass();
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("woodland");
    }

    @Override
    public boolean hasDomesticAnimals() {
        return false;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
