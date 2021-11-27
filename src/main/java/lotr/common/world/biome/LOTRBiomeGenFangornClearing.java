package lotr.common.world.biome;

import lotr.common.world.spawning.*;

public class LOTRBiomeGenFangornClearing extends LOTRBiomeGenFangorn {
    public LOTRBiomeGenFangornClearing(int i, boolean major) {
        super(i, major);
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        this.clearBiomeVariants();
        this.decorator.treesPerChunk = 0;
        this.decorator.flowersPerChunk = 4;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 8;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
}
