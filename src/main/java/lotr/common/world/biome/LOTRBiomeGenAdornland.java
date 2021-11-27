package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenAdornland
extends LOTRBiome {
    private WorldGenerator boulderGenStone = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
    private WorldGenerator boulderGenRohan = new LOTRWorldGenBoulder(LOTRMod.rock, 2, 1, 4);

    public LOTRBiomeGenAdornland(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 10, 2, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCrebain.class, 10, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 40);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
        this.npcSpawnList.newFactionList(50).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RUFFIANS, 10);
        this.npcSpawnList.newFactionList(2).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 3);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2);
        this.npcSpawnList.newFactionList(5).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.BOULDERS_ROHAN);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5f);
        this.decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 2, 60, Blocks.stone), 2.0f, 0, 64);
        this.decorator.setTreeCluster(12, 30);
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 2;
        this.decorator.grassPerChunk = 12;
        this.decorator.doubleGrassPerChunk = 3;
        this.decorator.addTree(LOTRTreeType.OAK, 300);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 300);
        this.decorator.addTree(LOTRTreeType.BIRCH, 20);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.BEECH, 20);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.FIR, 300);
        this.decorator.addTree(LOTRTreeType.PINE, 300);
        this.decorator.addTree(LOTRTreeType.APPLE, 2);
        this.decorator.addTree(LOTRTreeType.PEAR, 2);
        this.registerPlainsFlowers();
        this.addFlower(LOTRMod.simbelmyne, 0, 1);
        this.decorator.addRandomStructure(new LOTRWorldGenRohanBarrow(false), 4000);
        this.decorator.addRandomStructure(new LOTRWorldGenDunlendingHouse(false), 150);
        this.decorator.addRandomStructure(new LOTRWorldGenDunlendingTavern(false), 250);
        this.decorator.addRandomStructure(new LOTRWorldGenDunlendingCampfire(false), 200);
        this.decorator.addRandomStructure(new LOTRWorldGenDunlandHillFort(false), 700);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 600);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
        this.decorator.addRandomStructure(new LOTRWorldGenUrukCamp(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenUrukWargPit(false), 3000);
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedRohanWatchtower(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenBlastedLand(true), 400);
        this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.DUNLAND, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.URUK_HAI, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.ROHAN, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterAdornland;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ROHAN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DUNLAND.getSubregion("adorn");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.ROHAN_MIX.setRepair(0.8f);
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int i1;
        int l;
        super.decorate(world, random, i, k);
        if (random.nextInt(60) == 0) {
            for (l = 0; l < 3; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderGenStone.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
        if (random.nextInt(60) == 0) {
            for (l = 0; l < 3; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderGenRohan.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.75f;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.15f;
    }

    @Override
    public boolean canSpawnHostilesInDay() {
        return false;
    }

    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}

