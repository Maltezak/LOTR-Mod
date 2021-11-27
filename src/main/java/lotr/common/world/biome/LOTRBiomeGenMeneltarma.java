package lotr.common.world.biome;

import java.util.*;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenMeneltarma extends LOTRBiomeGenOcean {
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);

    public LOTRBiomeGenMeneltarma(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        this.decorator.setTreeCluster(8, 20);
        this.decorator.treesPerChunk = 0;
        this.decorator.flowersPerChunk = 5;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.generateAthelas = true;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.CEDAR, 1000);
        this.decorator.addTree(LOTRTreeType.CEDAR_LARGE, 500);
        this.decorator.addTree(LOTRTreeType.OAK, 200);
        this.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
        this.decorator.addTree(LOTRTreeType.BIRCH, 200);
        this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 400);
        this.decorator.addTree(LOTRTreeType.BEECH, 200);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 400);
        ArrayList flowerDupes = new ArrayList();
        for(int l = 0; l < 10; ++l) {
            this.flowers.clear();
            this.registerPlainsFlowers();
            flowerDupes.addAll(this.flowers);
        }
        this.flowers.clear();
        this.flowers.addAll(flowerDupes);
        this.addFlower(LOTRMod.athelas, 0, 10);
        this.decorator.clearRandomStructures();
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MENELTARMA;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMeneltarma;
    }

    @Override
    public boolean isHiddenBiome() {
        return true;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SEA.getSubregion("meneltarma");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = biomeTerrainNoise.func_151601_a(i * 0.1, k * 0.1);
        if(d1 > -0.1) {
            this.topBlock = Blocks.stone;
            this.topBlockMeta = 0;
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
        super.decorate(world, random, i, k);
        if(random.nextInt(2) == 0) {
            for(int l = 0; l < 3; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
        if(random.nextInt(3) == 0) {
            int i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            k1 = k + random.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.athelas).generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
