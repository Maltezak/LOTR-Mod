package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenFarHaradJungle extends LOTRBiomeGenFarHarad {
    private WorldGenerator obsidianGen = new LOTRWorldGenObsidianGravel();
    protected int obsidianGravelRarity = 20;

    public LOTRBiomeGenFarHaradJungle(int i, boolean major) {
        super(i, major);
        if(this.isMuddy()) {
            this.topBlock = LOTRMod.mudGrass;
            this.fillerBlock = LOTRMod.mud;
        }
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 15, 4, 4));
        if(this.isMuddy()) {
            this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityMidges.class, 10, 4, 4));
        }
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 10).setSpawnChance(5000);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 30).setSpawnChance(5000);
        this.npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 4);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
        this.addBiomeVariant(LOTRBiomeVariant.JUNGLE_DENSE);
        if(this.isMuddy()) {
            this.decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 256);
            this.decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 64);
        }
        this.decorator.addOre(new WorldGenMinable(Blocks.gold_ore, 4), 3.0f, 0, 48);
        this.decorator.addGem(new WorldGenMinable(LOTRMod.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
        this.decorator.treesPerChunk = 40;
        this.decorator.vinesPerChunk = 50;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 4;
        this.decorator.grassPerChunk = 15;
        this.decorator.doubleGrassPerChunk = 10;
        this.decorator.enableFern = true;
        this.decorator.canePerChunk = 5;
        this.decorator.cornPerChunk = 10;
        this.decorator.melonPerChunk = 0.2f;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.JUNGLE, 1000);
        this.decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 500);
        this.decorator.addTree(LOTRTreeType.MAHOGANY, 500);
        this.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
        this.decorator.addTree(LOTRTreeType.MANGO, 20);
        this.decorator.addTree(LOTRTreeType.BANANA, 50);
        this.registerJungleFlowers();
        this.biomeColors.setGrass(10607421);
        this.biomeColors.setFoliage(8376636);
        this.biomeColors.setSky(11977908);
        this.biomeColors.setFog(11254938);
        this.biomeColors.setWater(4104311);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 4), 100);
        this.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFarHaradJungle;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FAR_HARAD_JUNGLE;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("jungle");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.TAUREDAIN.setRepair(0.8f);
    }

    public boolean hasJungleLakes() {
        return true;
    }

    public boolean isMuddy() {
        return true;
    }

    @Override
    protected double modifyStoneNoiseForFiller(double stoneNoise) {
        if(this.isMuddy()) {
            stoneNoise += 40.0;
        }
        return stoneNoise;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int j1;
        super.decorate(world, random, i, k);
        WorldGenVines vines = new WorldGenVines();
        for(int l = 0; l < 10; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            j1 = 24;
            int k1 = k + random.nextInt(16) + 8;
            vines.generate(world, random, i1, j1, k1);
        }
        if(this.obsidianGravelRarity > 0 && random.nextInt(this.obsidianGravelRarity) == 0) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            j1 = world.getTopSolidOrLiquidBlock(i1, k1);
            this.obsidianGen.generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        if(random.nextInt(4) == 0) {
            return new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 5);
        }
        return super.getRandomGrass(random);
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
