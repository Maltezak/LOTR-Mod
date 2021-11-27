package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.*;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenUmbar;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenUmbar extends LOTRBiome {
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(7849067306796L), 1);
    protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(628602597026L), 1);

    public LOTRBiomeGenUmbar(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 4, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 5, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBARIANS, 30).setSpawnChance(100);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10).setSpawnChance(100);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(100);
        this.npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 2);
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 2);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PELARGIR_SOLDIERS, 5).setConquestThreshold(100.0f);
        arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 5).setConquestThreshold(100.0f);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        this.npcSpawnList.conquestGainRate = 0.2f;
        this.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1f);
        this.decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        this.decorator.grassPerChunk = 6;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.flowersPerChunk = 3;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
        this.decorator.addTree(LOTRTreeType.CEDAR, 300);
        this.decorator.addTree(LOTRTreeType.CYPRESS, 500);
        this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.PALM, 100);
        this.decorator.addTree(LOTRTreeType.DATE_PALM, 5);
        this.decorator.addTree(LOTRTreeType.LEMON, 2);
        this.decorator.addTree(LOTRTreeType.ORANGE, 2);
        this.decorator.addTree(LOTRTreeType.LIME, 2);
        this.decorator.addTree(LOTRTreeType.OLIVE, 5);
        this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
        this.decorator.addTree(LOTRTreeType.PLUM, 2);
        this.registerHaradFlowers();
        this.biomeColors.setGrass(11914805);
        this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1500);
        this.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 800);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.UMBAR(1, 3), 800);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 800);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NUMENOR(1, 3), 2000);
        this.decorator.addVillage(new LOTRVillageGenUmbar(this, 0.9f));
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterUmbar;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.UMBAR;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("umbar");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.UMBAR;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = noiseDirt.func_151601_a(i * 0.002, k * 0.002);
        double d2 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
        double d3 = noiseDirt.func_151601_a(i * 0.25, k * 0.25);
        double d4 = noiseSand.func_151601_a(i * 0.002, k * 0.002);
        if(d4 + (noiseSand.func_151601_a(i * 0.07, k * 0.07)) + (noiseSand.func_151601_a(i * 0.25, k * 0.25)) > 1.1) {
            this.topBlock = Blocks.sand;
            this.topBlockMeta = 0;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d1 + d2 + d3 > 0.6) {
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
        return 0.15f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.05f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
}
