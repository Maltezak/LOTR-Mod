package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenMistyMountains extends LOTRBiome {
    public LOTRBiomeGenMistyMountains(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
        arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 20);
        arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 1).setSpawnChance(5000);
        arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30).setConquestOnly();
        arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 20).setConquestOnly();
        arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 6).setConquestOnly();
        this.npcSpawnList.newFactionList(20).add(arrspawnListContainer2);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 15);
        arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 30);
        arrspawnListContainer5[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 20);
        this.npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
        this.variantChance = 0.1f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.3f);
        this.decorator.biomeGemFactor = 1.0f;
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMithril, 6), 0.25f, 0, 16);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0f, 0, 48);
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 3;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.generateWater = false;
        this.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
        this.decorator.addTree(LOTRTreeType.LARCH, 300);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
        this.decorator.addTree(LOTRTreeType.FIR, 500);
        this.decorator.addTree(LOTRTreeType.PINE, 500);
        this.registerMountainsFlowers();
        this.biomeColors.setSky(12241873);
        this.decorator.generateOrcDungeon = true;
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DWARVEN(1, 4), 1500);
        this.decorator.addRandomStructure(new LOTRWorldGenRuinedDwarvenTower(false), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMistyMountains;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MISTY_MOUNTAINS;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MISTY_MOUNTAINS.getSubregion("mistyMountains");
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
        int snowHeight = 120 - rockDepth;
        int stoneHeight = snowHeight - 30;
        for(int j = ySize - 1; j >= stoneHeight; --j) {
            int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if(j >= snowHeight && block == this.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
                continue;
            }
            if(block != this.topBlock && block != this.fillerBlock) continue;
            blocks[index] = Blocks.stone;
            meta[index] = 0;
        }
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        for(int count = 0; count < 2; ++count) {
            int k1;
            int i1 = i + random.nextInt(16) + 8;
            int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
            if(j1 >= 100) continue;
            this.decorator.genTree(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
}
