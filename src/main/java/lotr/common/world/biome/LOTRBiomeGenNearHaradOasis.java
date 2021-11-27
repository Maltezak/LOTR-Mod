package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenNearHaradOasis extends LOTRBiomeGenNearHaradRiverbank {
    public LOTRBiomeGenNearHaradOasis(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.decorator.treesPerChunk = 3;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 4;
        this.decorator.flowersPerChunk = 5;
        this.decorator.doubleFlowersPerChunk = 2;
        this.decorator.addTree(LOTRTreeType.DATE_PALM, 2000);
        this.decorator.addTree(LOTRTreeType.OLIVE, 500);
        this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 200);
        this.decorator.addTree(LOTRTreeType.OAK_SHRUB, 3000);
        this.decorator.clearRandomStructures();
        this.decorator.clearVillages();
    }

    @Override
    protected boolean hasMixedHaradSoils() {
        return false;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNearHaradOasis;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("oasis");
    }
}
