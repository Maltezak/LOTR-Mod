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
import lotr.common.world.village.LOTRVillageGenGulfHarad;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenGulfHarad extends LOTRBiome {
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
    protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
    protected static NoiseGeneratorPerlin noiseRedSand = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);

    public LOTRBiomeGenGulfHarad(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityWildBoar.class, 10, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 8, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityAurochs.class, 6, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityWhiteOryx.class, 12, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 2, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 10, 4, 4));
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_HARADRIM, 20).setSpawnChance(100);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 5).setSpawnChance(100);
        this.npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
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
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 2;
        this.decorator.flowersPerChunk = 1;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.deadBushPerChunk = 1;
        this.decorator.cactiPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.PALM, 500);
        this.decorator.addTree(LOTRTreeType.ACACIA, 300);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 400);
        this.decorator.addTree(LOTRTreeType.DRAGONBLOOD, 200);
        this.decorator.addTree(LOTRTreeType.DRAGONBLOOD_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.DATE_PALM, 50);
        this.decorator.addTree(LOTRTreeType.LEMON, 5);
        this.decorator.addTree(LOTRTreeType.ORANGE, 5);
        this.decorator.addTree(LOTRTreeType.LIME, 5);
        this.decorator.addTree(LOTRTreeType.OLIVE, 5);
        this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.ALMOND, 5);
        this.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerHaradFlowers();
        this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 3000);
        this.decorator.addVillage(new LOTRVillageGenGulfHarad(this, 0.75f));
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterGulfHarad;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.GULF_HARAD;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("gulf");
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.GULF_HARAD;
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
            if(d7 + (noiseRedSand.func_151601_a(i * 0.07, k * 0.07)) + (noiseRedSand.func_151601_a(i * 0.25, k * 0.25)) > 0.9) {
                this.topBlock = Blocks.sand;
                this.topBlockMeta = 1;
                this.fillerBlock = this.topBlock;
                this.fillerBlockMeta = this.topBlockMeta;
            }
            else if(d4 + d5 + d6 > 1.2) {
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
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        if(random.nextInt(20) == 0) {
            int boulders = 1 + random.nextInt(3);
            for(int l = 0; l < boulders; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                int j1 = world.getHeightValue(i1, k1);
                this.boulderGen.generate(world, random, i1, j1, k1);
            }
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
        LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
        if(random.nextInt(3) != 0) {
            doubleFlowerGen.setFlowerType(3);
        }
        else {
            doubleFlowerGen.setFlowerType(2);
        }
        return doubleFlowerGen;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
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
