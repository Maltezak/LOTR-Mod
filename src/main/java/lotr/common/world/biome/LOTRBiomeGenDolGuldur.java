package lotr.common.world.biome;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenDolGuldur extends LOTRBiomeGenMirkwoodCorrupted {
    public LOTRBiomeGenDolGuldur(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 8, 4, 4));
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 20);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 30);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 5);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(50.0f);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(200.0f);
        arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(400.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 3);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        this.npcSpawnList.conquestGainRate = 0.2f;
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0f, 0, 64);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0f, 0, 32);
        this.decorator.treesPerChunk = 1;
        this.decorator.vinesPerChunk = 2;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.MIRK_OAK, 200);
        this.decorator.addTree(LOTRTreeType.MIRK_OAK_DEAD, 1000);
        this.biomeColors.setGrass(3032113);
        this.biomeColors.setFoliage(3032113);
        this.biomeColors.setSky(4343633);
        this.biomeColors.setClouds(2632757);
        this.biomeColors.setFoggy(true);
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DOL_GULDUR(1, 4), 5);
        this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurAltar(false), 160);
        this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurTower(false), 80);
        this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurCamp(false), 50);
        this.decorator.addRandomStructure(new LOTRWorldGenDolGuldurSpiderPit(false), 50);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDolGuldur;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("dolGuldur");
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }

    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
}
