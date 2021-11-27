package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenRhunForest extends LOTRBiomeGenRhun {
    public LOTRBiomeGenRhunForest(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 16, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 20, 4, 6));
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 8;
        this.decorator.logsPerChunk = 1;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 2000);
        this.decorator.addTree(LOTRTreeType.OAK_PARTY, 100);
        this.registerRhunForestFlowers();
        this.biomeColors.resetGrass();
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
