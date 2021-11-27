package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenLongMarshes extends LOTRBiomeGenWilderlandNorth {
    public LOTRBiomeGenLongMarshes(int i, boolean major) {
        super(i, major);
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 8, 4, 4));
        this.clearBiomeVariants();
        this.variantChance = 1.0f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
        this.decorator.sandPerChunk = 0;
        this.decorator.quagmirePerChunk = 2;
        this.decorator.treesPerChunk = 0;
        this.decorator.willowPerChunk = 1;
        this.decorator.logsPerChunk = 1;
        this.decorator.flowersPerChunk = 2;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 10;
        this.decorator.enableFern = true;
        this.decorator.waterlilyPerChunk = 4;
        this.decorator.canePerChunk = 10;
        this.decorator.reedPerChunk = 6;
        this.decorator.addTree(LOTRTreeType.OAK_SWAMP, 1000);
        this.decorator.addTree(LOTRTreeType.GREEN_OAK, 200);
        this.registerSwampFlowers();
        this.biomeColors.setSky(13230818);
        this.biomeColors.setFog(12112325);
        this.biomeColors.setFoggy(true);
        this.biomeColors.setWater(8167049);
        this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.invasionSpawns.clearInvasions();
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLongMarshes;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHOVANION.getSubregion("longMarshes");
    }

    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
