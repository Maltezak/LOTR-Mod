package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.*;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenBarrowDowns extends LOTRBiome {
    public static final int WIGHT_SKY = 9674385;
    public static final int WIGHT_CLOUDS = 11842740;
    public static final int WIGHT_FOG = 10197915;

    public LOTRBiomeGenBarrowDowns(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 8, 2, 6));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BARROW_WIGHTS, 10);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNEDAIN_NORTH, 2);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        arrspawnListContainer5[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10);
        arrspawnListContainer6[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        arrspawnListContainer6[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
        this.npcSpawnList.conquestGainRate = 0.2f;
        this.variantChance = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.decorator.willowPerChunk = 1;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.generateAthelas = true;
        this.decorator.addTree(LOTRTreeType.OAK, 1000);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1500);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        this.decorator.addTree(LOTRTreeType.BIRCH, 150);
        this.registerPlainsFlowers();
        this.decorator.generateOrcDungeon = true;
        this.decorator.addRandomStructure(new LOTRWorldGenBDBarrow(false), 10);
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(2, 7), 30);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(2, 7), 30);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 200);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterBarrowDowns;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ERIADOR;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.BARROW_DOWNS.getSubregion("barrowDowns");
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 6;
    }
}
