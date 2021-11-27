package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenKanuka extends LOTRBiomeGenFarHarad {
    private static NoiseGeneratorPerlin noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
    private static NoiseGeneratorPerlin noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);
    public LOTRBiomeGenKanuka(int i, boolean major) {
        super(i, major);
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.enablePodzol = false;
        this.decorator.treesPerChunk = 0;
        this.decorator.setTreeCluster(8, 3);
        this.decorator.vinesPerChunk = 0;
        this.decorator.flowersPerChunk = 3;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 4;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.enableFern = true;
        this.decorator.melonPerChunk = 0.0f;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.KANUKA, 100);
        this.biomeColors.setGrass(11915563);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("kanuka");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        double d1 = noisePaths1.func_151601_a(i * 0.008, k * 0.008);
        double d2 = noisePaths2.func_151601_a(i * 0.008, k * 0.008);
        if(d1 > 0.0 && d1 < 0.1 || d2 > 0.0 && d2 < 0.1) {
            this.topBlock = LOTRMod.dirtPath;
            this.topBlockMeta = 1;
        }
        this.enablePodzol = height > 75;
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        this.enablePodzol = false;
        this.topBlock = topBlock_pre;
        this.topBlockMeta = topBlockMeta_pre;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        for(int count = 0; count < 4; ++count) {
            int k1;
            int i1 = i + random.nextInt(16) + 8;
            int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
            if(j1 <= 75) continue;
            this.decorator.genTree(world, random, i1, j1, k1);
        }
        LOTRBiomeVariant variant = ((LOTRWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
        int grasses = 12;
        grasses = Math.round(grasses * variant.grassFactor);
        for(int l = 0; l < grasses; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
            if(world.getHeightValue(i1, k1) <= 75) continue;
            WorldGenerator grassGen = this.getRandomWorldGenForGrass(random);
            grassGen.generate(world, random, i1, j1, k1);
        }
        int doubleGrasses = 4;
        doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
        for(int l = 0; l < doubleGrasses; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
            if(world.getHeightValue(i1, k1) <= 75) continue;
            WorldGenerator grassGen = this.getRandomWorldGenForDoubleGrass(random);
            grassGen.generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        if(random.nextInt(5) != 0) {
            return new LOTRBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
        }
        return super.getRandomGrass(random);
    }

    @Override
    public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
        WorldGenDoublePlant generator = new WorldGenDoublePlant();
        generator.func_150548_a(3);
        return generator;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return super.getChanceToSpawnAnimals() * 0.25f;
    }
}
