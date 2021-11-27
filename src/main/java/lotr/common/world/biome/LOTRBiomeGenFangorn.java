package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenFangorn extends LOTRBiome {
    public LOTRBiomeGenFangorn(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCrebain.class, 6, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2);
        arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
        arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2);
        arrspawnListContainer5[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        arrspawnListContainer6[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        arrspawnListContainer6[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        arrspawnListContainer6[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
        this.npcSpawnList.conquestGainRate = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.decorator.treesPerChunk = 12;
        this.decorator.willowPerChunk = 3;
        this.decorator.logsPerChunk = 5;
        this.decorator.flowersPerChunk = 6;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 12;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.enableFern = true;
        this.decorator.addTree(LOTRTreeType.DARK_OAK, 400);
        this.decorator.addTree(LOTRTreeType.OAK, 100);
        this.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
        this.decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        this.decorator.addTree(LOTRTreeType.BIRCH, 20);
        this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 20);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.BEECH, 20);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.OAK_FANGORN, 50);
        this.decorator.addTree(LOTRTreeType.BEECH_FANGORN, 20);
        this.decorator.addTree(LOTRTreeType.ASPEN, 50);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        this.registerForestFlowers();
        this.addFlower(LOTRMod.fangornPlant, 0, 1);
        this.addFlower(LOTRMod.fangornPlant, 1, 1);
        this.addFlower(LOTRMod.fangornPlant, 2, 1);
        this.addFlower(LOTRMod.fangornPlant, 3, 1);
        this.addFlower(LOTRMod.fangornPlant, 4, 1);
        this.addFlower(LOTRMod.fangornPlant, 5, 1);
        this.biomeColors.setSky(7774322);
        this.biomeColors.setFog(3308875);
        this.biomeColors.setFoggy(true);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    public LOTRBiomeGenFangorn setBirchFangorn() {
        this.decorator.addTree(LOTRTreeType.BIRCH, 2000);
        this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 2000);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 2000);
        this.decorator.addTree(LOTRTreeType.BIRCH_FANGORN, 1000);
        return this;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFangorn;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FANGORN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FANGORN.getSubregion("fangorn");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int j1;
        int i1;
        int k1;
        super.decorate(world, random, i, k);
        if(random.nextInt(2) == 0) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            for(j1 = 64 + random.nextInt(64); j1 > 0 && world.getBlock(i1, j1 - 1, k1) == Blocks.air; --j1) {
            }
            new LOTRWorldGenWaterPlant(LOTRMod.fangornRiverweed).generate(world, random, i1, j1, k1);
        }
        if(random.nextInt(10) == 0) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getHeightValue(i1, k1);
            new LOTRWorldGenEntJars().generate(world, random, i1, j1, k1);
        }
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
