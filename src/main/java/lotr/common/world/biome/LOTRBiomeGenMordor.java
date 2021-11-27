package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenMordorTower;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenMordor
extends LOTRBiome {
    protected WorldGenerator boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 2, 8);
    protected boolean enableMordorBoulders = true;
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
    protected static NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(1379468206L), 1);

    public LOTRBiomeGenMordor(int i, boolean major) {
        super(i, major);
        this.topBlock = LOTRMod.rock;
        this.topBlockMeta = 0;
        this.fillerBlock = LOTRMod.rock;
        this.fillerBlockMeta = 0;
        if (this.isGorgoroth()) {
            this.setDisableRain();
        }
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[5];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 30);
        arrspawnListContainer[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 10);
        arrspawnListContainer[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 7);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
        this.npcSpawnList.newFactionList(1).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        this.npcSpawnList.conquestGainRate = 0.5f;
        this.decorator.clearOres();
        this.decorator.addSoil(new WorldGenMinable(LOTRMod.mordorDirt, 0, 60, LOTRMod.rock), 10.0f, 0, 60);
        this.decorator.addSoil(new WorldGenMinable(LOTRMod.mordorGravel, 0, 32, LOTRMod.rock), 10.0f, 0, 60);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreNaurite, 12, LOTRMod.rock), 20.0f, 0, 64);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 1, 8, LOTRMod.rock), 20.0f, 0, 64);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 6.0f, 0, 32);
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 1;
        this.decorator.generateWater = false;
        if (this.isGorgoroth()) {
            this.decorator.sandPerChunk = 0;
            this.decorator.clayPerChunk = 0;
            this.decorator.dryReedChance = 1.0f;
            this.enableRocky = false;
        }
        this.decorator.addTree(LOTRTreeType.CHARRED, 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 100);
        this.decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 300);
        this.decorator.addRandomStructure(new LOTRWorldGenMordorTower(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenBlackUrukFort(false), 2000);
        this.biomeColors.setGrass(5980459);
        this.biomeColors.setFoliage(6508333);
        this.biomeColors.setSky(6700595);
        this.biomeColors.setClouds(4924185);
        this.biomeColors.setFog(3154711);
        this.biomeColors.setWater(2498845);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public boolean hasSky() {
        return !this.isGorgoroth();
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMordor;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MORDOR;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("mordor");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.MORDOR;
    }

    @Override
    public LOTRRoadType.BridgeType getBridgeBlock() {
        return LOTRRoadType.BridgeType.CHARRED;
    }

    public boolean isGorgoroth() {
        return true;
    }

    protected boolean hasMordorSoils() {
        return true;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        if (this.isGorgoroth() && this.hasMordorSoils()) {
            double d1 = noiseDirt.func_151601_a(i * 0.08, k * 0.08);
            double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
            double d3 = noiseGravel.func_151601_a(i * 0.08, k * 0.08);
            if (d3 + (noiseGravel.func_151601_a(i * 0.4, k * 0.4)) > 0.8) {
                this.topBlock = LOTRMod.mordorGravel;
                this.topBlockMeta = 0;
                this.fillerBlock = this.topBlock;
                this.fillerBlockMeta = this.topBlockMeta;
            } else if (d1 + d2 > 0.5) {
                this.topBlock = LOTRMod.mordorDirt;
                this.topBlockMeta = 0;
                this.fillerBlock = this.topBlock;
                this.fillerBlockMeta = this.topBlockMeta;
            }
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        this.topBlock = topBlock_pre;
        this.topBlockMeta = topBlockMeta_pre;
        this.fillerBlock = fillerBlock_pre;
        this.fillerBlockMeta = fillerBlockMeta_pre;
    }

    @Override
    protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
        for (int j = ySize - 1; j >= 0; --j) {
            int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if (block != Blocks.stone) continue;
            blocks[index] = LOTRMod.rock;
            meta[index] = 0;
        }
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int j1;
        int i1;
        super.decorate(world, random, i, k);
        if (this.isGorgoroth()) {
            int k12;
            int i12;
            int j12;
            int l;
            if (this.enableMordorBoulders && random.nextInt(24) == 0) {
                for (l = 0; l < 6; ++l) {
                    i12 = i + random.nextInt(16) + 8;
                    k12 = k + random.nextInt(16) + 8;
                    this.boulderGen.generate(world, random, i12, world.getHeightValue(i12, k12), k12);
                }
            }
            if (random.nextInt(60) == 0) {
                for (l = 0; l < 8; ++l) {
                    i12 = i + random.nextInt(16) + 8;
                    k12 = k + random.nextInt(16) + 8;
                    j12 = world.getHeightValue(i12, k12);
                    this.decorator.genTree(world, random, i12, j12, k12);
                }
            }
            if (this.decorator.grassPerChunk > 0) {
                if (random.nextInt(20) == 0) {
                    for (l = 0; l < 6; ++l) {
                        i12 = i + random.nextInt(6) + 8;
                        if (!world.isAirBlock(i12, j12 = world.getHeightValue(i12, k12 = k + random.nextInt(6) + 8), k12) || !LOTRMod.mordorThorn.canBlockStay(world, i12, j12, k12)) continue;
                        world.setBlock(i12, j12, k12, LOTRMod.mordorThorn, 0, 2);
                    }
                }
                if (random.nextInt(20) == 0 && world.isAirBlock(i1 = i + random.nextInt(16) + 8, j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8), k1) && LOTRMod.mordorMoss.canBlockStay(world, i1, j1, k1)) {
                    new LOTRWorldGenMordorMoss().generate(world, random, i1, j1, k1);
                }
            }
        }
        if (LOTRFixedStructures.MORDOR_CHERRY_TREE.isAt(world, i, k)) {
            i1 = i + 8;
            k1 = k + 8;
            j1 = world.getHeightValue(i1, k1);
            LOTRTreeType.CHERRY_MORDOR.create(false, random).generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        if (this.isGorgoroth()) {
            return new LOTRBiome.GrassBlockAndMeta(LOTRMod.mordorGrass, 0);
        }
        return super.getRandomGrass(random);
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }

    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }

    public static boolean isSurfaceMordorBlock(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        int meta = world.getBlockMetadata(i, j, k);
        return block == LOTRMod.rock && meta == 0 || block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
    }
}

