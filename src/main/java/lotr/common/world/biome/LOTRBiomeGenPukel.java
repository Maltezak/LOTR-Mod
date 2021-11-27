package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenPukel extends LOTRBiome {
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
    protected static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);

    public LOTRBiomeGenPukel(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 6, 1, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
        this.npcSpawnList.clear();
        this.clearBiomeVariants();
        this.variantChance = 0.6f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_OAK, 6.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_DARK_OAK, 6.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
        this.decorator.setTreeCluster(10, 24);
        this.decorator.treesPerChunk = 1;
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 3;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 14;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.addTree(LOTRTreeType.OAK, 400);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        this.decorator.addTree(LOTRTreeType.OAK_PARTY, 50);
        this.decorator.addTree(LOTRTreeType.BIRCH, 50);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
        this.decorator.addTree(LOTRTreeType.BEECH, 50);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
        this.decorator.addTree(LOTRTreeType.CHESTNUT, 200);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.DARK_OAK, 500);
        this.decorator.addTree(LOTRTreeType.DARK_OAK_PARTY, 100);
        this.decorator.addTree(LOTRTreeType.FIR, 200);
        this.decorator.addTree(LOTRTreeType.PINE, 200);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        this.decorator.addTree(LOTRTreeType.LARCH, 200);
        this.decorator.addTree(LOTRTreeType.APPLE, 5);
        this.decorator.addTree(LOTRTreeType.PEAR, 5);
        this.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.decorator.addTree(LOTRTreeType.OAK_SHRUB, 300);
        this.registerPlainsFlowers();
        this.biomeColors.setGrass(6715192);
        this.biomeColors.setSky(10927288);
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.invasionSpawns.clearInvasions();
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterPukel;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.PUKEL;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.PUKEL.getSubregion("pukel");
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
        double d3 = noiseStone.func_151601_a(i * 0.06, k * 0.06);
        if(d3 + (noiseStone.func_151601_a(i * 0.4, k * 0.4)) > 1.3) {
            this.topBlock = Blocks.stone;
            this.topBlockMeta = 0;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d1 + d2 > 0.7) {
            this.topBlock = Blocks.dirt;
            this.topBlockMeta = 1;
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
        if(random.nextInt(24) == 0) {
            for(int l = 0; l < 4; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
