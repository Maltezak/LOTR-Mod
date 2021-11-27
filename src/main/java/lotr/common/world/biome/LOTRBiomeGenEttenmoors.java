package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
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

public class LOTRBiomeGenEttenmoors extends LOTRBiome {
    private WorldGenerator boulderGenLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 5);
    private WorldGenerator boulderGenSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);

    public LOTRBiomeGenEttenmoors(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 10, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityElk.class, 6, 4, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 6, 1, 4));
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 10);
        this.npcSpawnList.newFactionList(35).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 40);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 20);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 20).setSpawnChance(500);
        arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 15);
        arrspawnListContainer2[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 5);
        this.npcSpawnList.newFactionList(70).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        this.npcSpawnList.conquestGainRate = 0.75f;
        this.biomeTerrain.setXZScale(100.0);
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 1.0f);
        this.decorator.biomeGemFactor = 0.75f;
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 4;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.generateAthelas = true;
        this.decorator.addTree(LOTRTreeType.FIR, 400);
        this.decorator.addTree(LOTRTreeType.PINE, 800);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
        this.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 200);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
        this.registerTaigaFlowers();
        this.decorator.generateOrcDungeon = true;
        this.decorator.generateTrollHoard = true;
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 3000);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.COMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.COMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.COMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.COMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.COMMON);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterEttenmoors;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ETTENMOORS;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ANGMAR.getSubregion("ettenmoors");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int i1;
        int l;
        super.decorate(world, random, i, k);
        for(l = 0; l < 3; ++l) {
            i1 = i + random.nextInt(16) + 8;
            int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
            if(j1 <= 84) continue;
            this.decorator.genTree(world, random, i1, j1, k1);
        }
        if(random.nextInt(4) == 0) {
            for(l = 0; l < 3; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderGenLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
        for(l = 0; l < 2; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGenSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
