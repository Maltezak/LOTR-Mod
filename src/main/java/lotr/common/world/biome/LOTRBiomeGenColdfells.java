package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenColdfells extends LOTRBiome {
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 4);

    public LOTRBiomeGenColdfells(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 10, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityElk.class, 4, 4, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 6, 1, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 5);
        this.npcSpawnList.newFactionList(50).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 15);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 5).setSpawnChance(2000);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 5).setConquestOnly();
        arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 5).setConquestOnly();
        arrspawnListContainer2[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 5).setConquestThreshold(50.0f);
        this.npcSpawnList.newFactionList(50).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        this.decorator.biomeGemFactor = 0.75f;
        this.decorator.treesPerChunk = 2;
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 2;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.generateAthelas = true;
        this.decorator.addTree(LOTRTreeType.FIR, 500);
        this.decorator.addTree(LOTRTreeType.PINE, 500);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 200);
        this.decorator.addTree(LOTRTreeType.OAK, 200);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 30);
        this.decorator.addTree(LOTRTreeType.LARCH, 300);
        this.decorator.addTree(LOTRTreeType.MAPLE, 100);
        this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 10);
        this.registerForestFlowers();
        this.decorator.generateOrcDungeon = true;
        this.decorator.generateTrollHoard = true;
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 800);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 3000);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterColdfells;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.COLDFELLS;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ANGMAR.getSubregion("coldfells");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        if(random.nextInt(3) == 0) {
            for(int l = 0; l < 3; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
