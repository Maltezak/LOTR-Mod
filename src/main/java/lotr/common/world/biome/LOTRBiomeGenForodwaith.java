package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenForodwaith
extends LOTRBiome {
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
    private LOTRWorldGenStalactites stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);

    public LOTRBiomeGenForodwaith(int i, boolean major) {
        super(i, major);
        this.setEnableSnow();
        this.topBlock = Blocks.snow;
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 10).setSpawnChance(100000);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        this.decorator.addSoil(new WorldGenMinable(Blocks.packed_ice, 16), 40.0f, 32, 256);
        this.decorator.treesPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.generateWater = false;
        this.biomeColors.setSky(10069160);
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 4000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 4000);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterForodwaith;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FORODWAITH;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FORODWAITH.getSubregion("forodwaith");
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int i1;
        int k1;
        super.decorate(world, random, i, k);
        if (LOTRFixedStructures.UTUMNO_ENTRANCE.isAt(world, i, k)) {
            new LOTRWorldGenUtumnoEntrance().generate(world, random, i, world.getHeightValue(i, k), k);
        }
        if (random.nextInt(32) == 0) {
            int boulders = 1 + random.nextInt(5);
            for (int l = 0; l < boulders; ++l) {
                int i12 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i12, world.getHeightValue(i12, k1), k1);
            }
        }
        for (int l = 0; l < 2; ++l) {
            i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(60);
            k1 = k + random.nextInt(16) + 8;
            this.stalactiteIceGen.generate(world, random, i1, j1, k1);
        }
        if (random.nextInt(20000) == 0) {
            LOTRWorldGenMirkOak tree = ((LOTRWorldGenMirkOak)LOTRTreeType.RED_OAK_WEIRWOOD.create(false, random)).disableRestrictions();
            i1 = i + random.nextInt(16) + 8;
            int k12 = k + random.nextInt(16) + 8;
            int j1 = world.getHeightValue(i1, k12);
            tree.generate(world, random, i1, j1, k12);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }
}

