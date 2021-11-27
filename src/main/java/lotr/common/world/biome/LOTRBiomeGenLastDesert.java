package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenLastDesert
extends LOTRBiome {
    protected WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);

    public LOTRBiomeGenLastDesert(int i, boolean major) {
        super(i, major);
        this.setDisableRain();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 10, 2, 6));
        this.spawnableLOTRAmbientList.clear();
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDesertScorpion.class, 10, 4, 4));
        this.npcSpawnList.clear();
        this.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.decorator.grassPerChunk = 0;
        this.decorator.doubleGrassPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
        this.decorator.doubleFlowersPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        this.registerRhunPlainsFlowers();
        this.biomeColors.setGrass(16767886);
        this.biomeColors.setSky(14736588);
        this.biomeColors.setClouds(10853237);
        this.biomeColors.setFog(14406319);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLastDesert;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("lastDesert");
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
        double d2 = biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + (d2 *= 0.6) > 0.7) {
            this.topBlock = Blocks.grass;
            this.topBlockMeta = 0;
            this.fillerBlock = Blocks.dirt;
            this.fillerBlockMeta = 0;
        } else if (d1 + d2 > 0.2) {
            this.topBlock = Blocks.dirt;
            this.topBlockMeta = 1;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        this.topBlock = topBlock_pre;
        this.topBlockMeta = topBlockMeta_pre;
        this.fillerBlock = fillerBlock_pre;
        this.fillerBlockMeta = fillerBlockMeta_pre;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int i1;
        int j1;
        int k12;
        int l;
        int i12;
        super.decorate(world, random, i, k);
        if (random.nextInt(8) == 0) {
            i12 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getHeightValue(i12, k1);
            this.getRandomWorldGenForGrass(random).generate(world, random, i12, j1, k1);
        }
        if (random.nextInt(100) == 0) {
            i12 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getHeightValue(i12, k1);
            new WorldGenCactus().generate(world, random, i12, j1, k1);
        }
        if (random.nextInt(20) == 0) {
            i12 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getHeightValue(i12, k1);
            new WorldGenDeadBush(Blocks.deadbush).generate(world, random, i12, j1, k1);
        }
        if (random.nextInt(32) == 0) {
            int boulders = 1 + random.nextInt(4);
            for (l = 0; l < boulders; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k12 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i1, world.getHeightValue(i1, k12), k12);
            }
        }
        if (random.nextInt(500) == 0) {
            int trees = 1 + random.nextInt(4);
            for (l = 0; l < trees; ++l) {
                i1 = i + random.nextInt(8) + 8;
                k12 = k + random.nextInt(8) + 8;
                int j12 = world.getHeightValue(i1, k12);
                this.decorator.genTree(world, random, i1, j12, k12);
            }
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.02f;
    }
}

