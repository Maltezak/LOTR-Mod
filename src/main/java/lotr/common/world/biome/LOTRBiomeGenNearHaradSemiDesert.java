package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenNearHaradSemiDesert extends LOTRBiomeGenNearHarad {
    public LOTRBiomeGenNearHaradSemiDesert(int i, boolean major) {
        super(i, major);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(500);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(500);
        this.npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        this.decorator.grassPerChunk = 5;
        this.decorator.doubleGrassPerChunk = 0;
        this.decorator.cactiPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 4000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
        this.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
        this.decorator.clearVillages();
        this.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.5f));
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
        if(d1 + (biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3)) > 0.3) {
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
        int j1;
        int i1;
        int k1;
        super.decorate(world, random, i, k);
        if(random.nextInt(20) == 0 && world.getBlock(i1 = i + random.nextInt(16) + 8, j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8) - 1, k1) == Blocks.sand) {
            world.setBlock(i1, j1, k1, Blocks.dirt);
            LOTRTreeType treeType = LOTRTreeType.OAK_DESERT;
            WorldGenAbstractTree tree = treeType.create(false, random);
            if(!tree.generate(world, random, i1, j1 + 1, k1)) {
                world.setBlock(i1, j1, k1, Blocks.sand);
            }
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }

    @Override
    public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
        return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
    }
}
