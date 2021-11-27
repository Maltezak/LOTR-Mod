package lotr.common.world.biome;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;

public class LOTRBiomeGenRivendellHills extends LOTRBiomeGenRivendell {
    public LOTRBiomeGenRivendellHills(int i, boolean major) {
        super(i, major);
        this.fillerBlock = LOTRMod.rock;
        this.fillerBlockMeta = 5;
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10).setSpawnChance(500);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(100.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        this.npcSpawnList.conquestGainRate = 0.2f;
        this.clearBiomeVariants();
        this.variantChance = 0.4f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.decorator.treesPerChunk = 3;
        this.decorator.flowersPerChunk = 2;
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.PINE, 1000);
        this.decorator.addTree(LOTRTreeType.PINE_SHRUB, 200);
        this.decorator.addTree(LOTRTreeType.FIR, 100);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        this.decorator.addTree(LOTRTreeType.ASPEN, 100);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.OAK, 100);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        this.biomeColors.resetGrass();
        this.decorator.clearRandomStructures();
        this.invasionSpawns.clearInvasions();
        this.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRBiome.loneLands.getBiomeAchievement();
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRBiome.loneLands.getBiomeWaypoints();
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRBiome.loneLands.getBiomeMusic();
    }

    @Override
    public boolean hasSeasonalGrass() {
        return true;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 1.0f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 1;
    }
}
