package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenRhunRedForest
extends LOTRBiomeGenRhunLand {
    public LOTRBiomeGenRhunRedForest(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 16, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 20, 4, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 10, 1, 4));
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setSpawnChance(1000);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 8).setConquestOnly();
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1).setConquestOnly();
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELVES, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELF_WARRIORS, 3);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        this.npcSpawnList.conquestGainRate = 0.5f;
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 6;
        this.decorator.logsPerChunk = 1;
        this.decorator.flowersPerChunk = 4;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.enableFern = true;
        this.decorator.addTree(LOTRTreeType.REDWOOD, 10000);
        this.decorator.addTree(LOTRTreeType.REDWOOD_2, 10000);
        this.decorator.addTree(LOTRTreeType.REDWOOD_3, 5000);
        this.decorator.addTree(LOTRTreeType.REDWOOD_4, 5000);
        this.decorator.addTree(LOTRTreeType.REDWOOD_5, 2000);
        this.registerForestFlowers();
        this.decorator.clearRandomStructures();
        this.decorator.clearVillages();
        this.biomeColors.resetGrass();
        this.biomeColors.setGrass(8951356);
        this.biomeColors.setFog(11259063);
        this.biomeColors.setFoggy(true);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhunRedwood;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        int chunkX = i & 0xF;
        int chunkZ = k & 0xF;
        int xzIndex = chunkX * 16 + chunkZ;
        int ySize = blocks.length / 256;
        if (variant.treeFactor >= 1.0f && (biomeTerrainNoise.func_151601_a(i * 0.05, k * 0.05)) + (biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4)) > -0.8) {
            int index = xzIndex * ySize + height;
            if (random.nextFloat() < 0.75f) {
                blocks[index] = Blocks.dirt;
                meta[index] = 2;
            }
        }
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        if (random.nextFloat() < 0.7f) {
            return new LOTRBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
        }
        return super.getRandomGrass(random);
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}

