package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenNearHaradRiverbank extends LOTRBiomeGenNearHaradFertile {
    public LOTRBiomeGenNearHaradRiverbank(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 20, 4, 4));
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 30).setSpawnChance(1000);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10).setSpawnChance(1000);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.variantChance = 0.3f;
        this.decorator.treesPerChunk = 0;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 3;
        this.decorator.flowersPerChunk = 1;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
        this.decorator.clearVillages();
        this.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.25f));
        this.clearTravellingTraders();
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.HARAD_DESERT;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNearHarad;
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
