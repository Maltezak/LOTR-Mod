package lotr.common.world.biome;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;

public class LOTRBiomeGenDorwinionHills extends LOTRBiomeGenDorwinion {
    public LOTRBiomeGenDorwinionHills(int i, boolean major) {
        super(i, major);
        this.fillerBlock = LOTRMod.rock;
        this.fillerBlockMeta = 5;
        this.biomeTerrain.setXZScale(50.0);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.decorator.flowersPerChunk = 3;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 5;
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 800);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDorwinionHills;
    }

    @Override
    public boolean hasDomesticAnimals() {
        return false;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
