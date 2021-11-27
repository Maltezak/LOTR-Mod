package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;

public class LOTRBiomeGenShireMarshes extends LOTRBiomeGenShire {
    public LOTRBiomeGenShireMarshes(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        this.decorator.sandPerChunk = 0;
        this.decorator.quagmirePerChunk = 1;
        this.decorator.treesPerChunk = 0;
        this.decorator.willowPerChunk = 1;
        this.decorator.logsPerChunk = 2;
        this.decorator.flowersPerChunk = 4;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.enableFern = true;
        this.decorator.waterlilyPerChunk = 4;
        this.decorator.canePerChunk = 10;
        this.decorator.reedPerChunk = 4;
        this.decorator.addTree(LOTRTreeType.OAK_SWAMP, 2000);
        this.registerSwampFlowers();
        this.biomeColors.resetGrass();
        this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.75f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
