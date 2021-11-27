package lotr.common.world.biome;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenRivendellHall;
import lotr.common.world.structure2.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenRivendell extends LOTRBiome {
    public LOTRBiomeGenRivendell(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_ELVES, 10);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 2);
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
        this.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0f, 0, 48);
        this.decorator.treesPerChunk = 0;
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 5;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.OAK, 500);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        this.decorator.addTree(LOTRTreeType.BEECH, 500);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 200);
        this.decorator.addTree(LOTRTreeType.BIRCH, 200);
        this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 100);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.ASPEN, 50);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
        this.decorator.addTree(LOTRTreeType.APPLE, 2);
        this.decorator.addTree(LOTRTreeType.PEAR, 2);
        this.registerPlainsFlowers();
        this.decorator.addRandomStructure(new LOTRWorldGenRivendellHouse(false), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenRivendellHall(false), 200);
        this.decorator.addRandomStructure(new LOTRWorldGenRivendellForge(false), 200);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRivendell;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RIVENDELL_VALE;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RIVENDELL.getSubregion("rivendell");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HIGH_ELVEN;
    }

    @Override
    public boolean hasSeasonalGrass() {
        return false;
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
        return 0.25f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
