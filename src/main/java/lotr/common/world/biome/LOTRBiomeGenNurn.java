package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNurn extends LOTRBiomeGenMordor {
    protected WorldGenerator nurnBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 1, 3);

    public LOTRBiomeGenNurn(int i, boolean major) {
        super(i, major);
        this.topBlock = Blocks.grass;
        this.fillerBlock = Blocks.dirt;
        this.enableRain = true;
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[6];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 5).setConquestThreshold(50.0f);
        arrspawnListContainer[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        arrspawnListContainer[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        arrspawnListContainer[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0f);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.decorator.setTreeCluster(6, 30);
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 0;
        this.decorator.doubleFlowersPerChunk = 0;
        this.decorator.grassPerChunk = 8;
        this.decorator.dryReedChance = 0.6f;
        this.decorator.generateWater = true;
        this.decorator.addTree(LOTRTreeType.OAK, 500);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        this.decorator.addTree(LOTRTreeType.CEDAR, 100);
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
        this.decorator.addTree(LOTRTreeType.CHARRED, 200);
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenNurnWheatFarm(false), 40);
        this.decorator.addRandomStructure(new LOTRWorldGenOrcSlaverTower(false), 200);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.biomeColors.setGrass(10066237);
        this.biomeColors.setFoliage(7042874);
        this.biomeColors.setSky(10526098);
        this.biomeColors.resetClouds();
        this.biomeColors.resetFog();
        this.biomeColors.setWater(8877157);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNurn;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.NURN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("nurn");
    }

    @Override
    public boolean isGorgoroth() {
        return false;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        if(random.nextInt(40) == 0) {
            for(int l = 0; l < 4; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                this.nurnBoulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
