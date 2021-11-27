package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.spawning.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradVolcano
extends LOTRBiomeGenFarHarad {
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
    private WorldGenerator obsidianGen = new LOTRWorldGenObsidianGravel();
    private static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(5286926989260620260L), 1);

    public LOTRBiomeGenFarHaradVolcano(int i, boolean major) {
        super(i, major);
        this.setDisableRain();
        this.topBlock = Blocks.stone;
        this.fillerBlock = Blocks.stone;
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableLOTRAmbientList.clear();
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10).setSpawnChance(200);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        this.decorator.treesPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.doubleGrassPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
        this.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
        this.decorator.addTree(LOTRTreeType.CHARRED, 500);
        this.biomeColors.setSky(5986904);
        this.biomeColors.setClouds(3355443);
        this.biomeColors.setFog(6710886);
        this.biomeColors.setWater(4009759);
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFarHaradVolcano;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("volcano");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        if (d1 + (noiseDirt.func_151601_a(i * 0.4, k * 0.4)) > 0.2) {
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
        int i1;
        int k1;
        int i12;
        int j1;
        int l;
        super.decorate(world, random, i, k);
        if (random.nextInt(32) == 0) {
            int boulders = 1 + random.nextInt(4);
            for (l = 0; l < boulders; ++l) {
                i12 = i + random.nextInt(16) + 8;
                int k12 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i12, world.getHeightValue(i12, k12), k12);
            }
        }
        LOTRWorldGenStreams lavaGen = new LOTRWorldGenStreams(Blocks.flowing_lava);
        for (l = 0; l < 50; ++l) {
            i12 = i + random.nextInt(16) + 8;
            j1 = 40 + random.nextInt(120);
            int k13 = k + random.nextInt(16) + 8;
            lavaGen.generate(world, random, i12, j1, k13);
        }
        if (random.nextInt(1) == 0) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getHeightValue(i1, k1);
            new LOTRWorldGenVolcanoCrater().generate(world, random, i1, j1, k1);
        }
        if (random.nextInt(50) == 0) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getTopSolidOrLiquidBlock(i1, k1);
            this.obsidianGen.generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }

    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }

    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
}

