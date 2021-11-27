package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.village.LOTRVillageGenRohan;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenWold
extends LOTRBiomeGenRohan {
    private WorldGenerator woldBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 2, 2, 4);

    public LOTRBiomeGenWold(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.decorator.treesPerChunk = 0;
        this.decorator.setTreeCluster(8, 100);
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 400);
        this.decorator.addTree(LOTRTreeType.BEECH_DEAD, 400);
        this.registerPlainsFlowers();
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenRohanWatchtower(false), 1000);
        this.decorator.clearVillages();
        this.decorator.addVillage(new LOTRVillageGenRohan(this, 0.25f));
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ROHAN.getSubregion("wold");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = biomeTerrainNoise.func_151601_a(i * 0.005, k * 0.005);
        if (d1 + (biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4)) > 1.0) {
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
        super.decorate(world, random, i, k);
        if (random.nextInt(16) == 0) {
            for (int l = 0; l < 4; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                this.woldBoulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
        if (random.nextInt(30) == 0) {
            int rocks = 10 + random.nextInt(20);
            for (int l = 0; l < rocks; ++l) {
                int j1;
                int rockMeta;
                Block rockBlock;
                int k1;
                int i1 = i + random.nextInt(16) + 8;
                Block block = world.getBlock(i1, (j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8)) - 1, k1);
                if (block != this.topBlock && block != this.fillerBlock) continue;
                if (random.nextBoolean()) {
                    rockBlock = LOTRMod.rock;
                    rockMeta = 2;
                } else if (random.nextInt(5) == 0) {
                    rockBlock = Blocks.gravel;
                    rockMeta = 0;
                } else {
                    rockBlock = Blocks.stone;
                    rockMeta = 0;
                }
                if (random.nextInt(3) == 0) {
                    world.setBlock(i1, j1 - 1, k1, rockBlock, rockMeta, 2);
                    continue;
                }
                world.setBlock(i1, j1, k1, rockBlock, rockMeta, 2);
                block.onPlantGrow(world, i1, j1 - 1, k1, i1, j1, k1);
            }
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.005f;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}

