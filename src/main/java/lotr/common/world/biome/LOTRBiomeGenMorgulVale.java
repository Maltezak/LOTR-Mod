package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenMorgulVale
extends LOTRBiomeGenMordor {
    private NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(1860286702860L), 1);
    private NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(8903486028509023054L), 1);
    private NoiseGeneratorPerlin noiseRock = new NoiseGeneratorPerlin(new Random(769385178389572607L), 1);

    public LOTRBiomeGenMorgulVale(int i, boolean major) {
        super(i, major);
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[5];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 15).setSpawnChance(30);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2).setSpawnChance(30);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        arrspawnListContainer[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        arrspawnListContainer[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0f);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        this.npcSpawnList.conquestGainRate = 0.5f;
        this.topBlock = Blocks.grass;
        this.fillerBlock = Blocks.dirt;
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 10.0f, 0, 60);
        this.decorator.treesPerChunk = 0;
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 3;
        this.decorator.dryReedChance = 1.0f;
        this.decorator.addTree(LOTRTreeType.OAK, 200);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
        this.decorator.addTree(LOTRTreeType.CHARRED, 500);
        this.flowers.clear();
        this.addFlower(LOTRMod.morgulFlower, 0, 20);
        this.biomeColors.setGrass(6054733);
        this.biomeColors.setFoliage(4475954);
        this.biomeColors.setSky(7835270);
        this.biomeColors.setClouds(5860197);
        this.biomeColors.setFog(6318950);
        this.biomeColors.setWater(3563598);
    }

    @Override
    public boolean isGorgoroth() {
        return false;
    }

    @Override
    protected boolean hasMordorSoils() {
        return false;
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMorgulVale;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("morgulVale");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = this.noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        double d2 = this.noiseDirt.func_151601_a(i * 0.3, k * 0.3);
        double d3 = this.noiseGravel.func_151601_a(i * 0.06, k * 0.06);
        double d4 = this.noiseGravel.func_151601_a(i * 0.3, k * 0.3);
        double d5 = this.noiseRock.func_151601_a(i * 0.06, k * 0.06);
        if (d5 + (this.noiseRock.func_151601_a(i * 0.3, k * 0.3)) > 1.1) {
            this.topBlock = LOTRMod.rock;
            this.topBlockMeta = 0;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        } else if (d3 + d4 > 0.7) {
            this.topBlock = LOTRMod.mordorGravel;
            this.topBlockMeta = 0;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        } else if (d1 + d2 > 0.7) {
            this.topBlock = LOTRMod.mordorDirt;
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
        for (int l = 0; l < 4; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.getHeightValue(i1, k1);
            boolean foundWater = false;
            for (int a = 0; a < 20; ++a) {
                int range = 8;
                int i2 = i1 + MathHelper.getRandomIntegerInRange(random, (-range), range);
                Block block = world.getBlock(i2, j1 + MathHelper.getRandomIntegerInRange(random, (-range), range), k1 + MathHelper.getRandomIntegerInRange(random, (-range), range));
                if (block.getMaterial() != Material.water) continue;
                foundWater = true;
                break;
            }
            if (!foundWater) continue;
            WorldGenFlowers flowerGen = new WorldGenFlowers(LOTRMod.morgulFlower);
            flowerGen.generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}

