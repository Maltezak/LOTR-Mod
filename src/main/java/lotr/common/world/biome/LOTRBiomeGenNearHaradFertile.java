package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenSouthron;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenNearHaradFertile extends LOTRBiome {
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(12960262626062L), 1);
    protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(17860128964L), 1);
    protected static NoiseGeneratorPerlin noiseRedSand = new NoiseGeneratorPerlin(new Random(358960629620L), 1);

    public LOTRBiomeGenNearHaradFertile(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 6, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 15, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 20).setSpawnChance(100);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 15).setSpawnChance(100);
        this.npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 2);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 2);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5);
        arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(50.0f);
        arrspawnListContainer3[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5).setConquestThreshold(100.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 3).setConquestThreshold(50.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer7 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer7[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer7);
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND_SAND);
        this.decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        this.decorator.grassPerChunk = 10;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.flowersPerChunk = 3;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.cactiPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.CEDAR, 800);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        this.decorator.addTree(LOTRTreeType.DATE_PALM, 50);
        this.decorator.addTree(LOTRTreeType.CYPRESS, 400);
        this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.PALM, 100);
        this.decorator.addTree(LOTRTreeType.LEMON, 5);
        this.decorator.addTree(LOTRTreeType.ORANGE, 5);
        this.decorator.addTree(LOTRTreeType.LIME, 5);
        this.decorator.addTree(LOTRTreeType.OLIVE, 5);
        this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.ALMOND, 5);
        this.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerHaradFlowers();
        this.biomeColors.setGrass(11914805);
        this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 300);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 4000);
        this.decorator.addVillage(new LOTRVillageGenSouthron(this, 0.6f));
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterSouthronCoasts;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.SOUTHRON_COASTS;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("fertile");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HARAD_PATH;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        if(this.hasMixedHaradSoils()) {
            double d1 = noiseDirt.func_151601_a(i * 0.002, k * 0.002);
            double d2 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
            double d3 = noiseDirt.func_151601_a(i * 0.25, k * 0.25);
            double d4 = noiseSand.func_151601_a(i * 0.002, k * 0.002);
            double d5 = noiseSand.func_151601_a(i * 0.07, k * 0.07);
            double d6 = noiseSand.func_151601_a(i * 0.25, k * 0.25);
            double d7 = noiseRedSand.func_151601_a(i * 0.002, k * 0.002);
            if(d7 + (noiseRedSand.func_151601_a(i * 0.07, k * 0.07)) + (noiseRedSand.func_151601_a(i * 0.25, k * 0.25)) > 1.6) {
                this.topBlock = Blocks.sand;
                this.topBlockMeta = 1;
                this.fillerBlock = this.topBlock;
                this.fillerBlockMeta = this.topBlockMeta;
            }
            else if(d4 + d5 + d6 > 0.9) {
                this.topBlock = Blocks.sand;
                this.topBlockMeta = 0;
                this.fillerBlock = this.topBlock;
                this.fillerBlockMeta = this.topBlockMeta;
            }
            else if(d1 + d2 + d3 > 0.4) {
                this.topBlock = Blocks.dirt;
                this.topBlockMeta = 1;
            }
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        this.topBlock = topBlock_pre;
        this.topBlockMeta = topBlockMeta_pre;
        this.fillerBlock = fillerBlock_pre;
        this.fillerBlockMeta = fillerBlockMeta_pre;
    }

    protected boolean hasMixedHaradSoils() {
        return true;
    }

    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
        LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
        if(random.nextInt(5) == 0) {
            doubleFlowerGen.setFlowerType(3);
        }
        else {
            doubleFlowerGen.setFlowerType(2);
        }
        return doubleFlowerGen;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.3f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
