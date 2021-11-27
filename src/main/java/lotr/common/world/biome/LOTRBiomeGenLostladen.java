package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenLostladen extends LOTRBiome {
    private static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(486938207230702L), 1);
    private static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(28507830789060732L), 1);
    private static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(275928960292060726L), 1);
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
    private WorldGenerator boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.sandstone, 0, 1, 3);

    public LOTRBiomeGenLostladen(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.npcSpawnList.clear();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK, 3.0f);
        this.decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        this.decorator.treesPerChunk = 0;
        this.decorator.grassPerChunk = 3;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.flowersPerChunk = 1;
        this.decorator.cactiPerChunk = 1;
        this.decorator.deadBushPerChunk = 2;
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
        this.registerHaradFlowers();
        this.biomeColors.setSky(15592678);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLostladen;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.LOSTLADEN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("lostladen");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HARAD.setRepair(0.3f);
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        double d3 = noiseSand.func_151601_a(i * 0.09, k * 0.09);
        double d4 = noiseSand.func_151601_a(i * 0.4, k * 0.4);
        double d5 = noiseStone.func_151601_a(i * 0.09, k * 0.09);
        if(d5 + (noiseStone.func_151601_a(i * 0.4, k * 0.4)) > 0.3) {
            if(random.nextInt(5) == 0) {
                this.topBlock = Blocks.gravel;
                this.topBlockMeta = 0;
            }
            else {
                this.topBlock = Blocks.stone;
                this.topBlockMeta = 0;
            }
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d3 + d4 > 0.1) {
            if(random.nextInt(5) == 0) {
                this.topBlock = Blocks.sandstone;
                this.topBlockMeta = 0;
            }
            else {
                this.topBlock = Blocks.sand;
                this.topBlockMeta = 0;
            }
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d1 + d2 > -0.2) {
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
        if(random.nextInt(20) == 0) {
            int boulders = 1 + random.nextInt(4);
            for(int l = 0; l < boulders; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                int j1 = world.getHeightValue(i1, k1);
                if(random.nextBoolean()) {
                    this.boulderGen.generate(world, random, i1, j1, k1);
                    continue;
                }
                this.boulderGenSandstone.generate(world, random, i1, j1, k1);
            }
        }
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        if(random.nextBoolean()) {
            return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
        }
        return super.getRandomGrass(random);
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.01f;
    }
}
