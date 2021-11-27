package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRhun extends LOTRBiome {
    private WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);

    public LOTRBiomeGenRhun(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 20, 2, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 5, 1, 4));
        this.npcSpawnList.clear();
        this.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_SPRUCE, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
        this.decorator.resetTreeCluster();
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 1;
        this.decorator.grassPerChunk = 12;
        this.decorator.doubleGrassPerChunk = 8;
        this.decorator.addTree(LOTRTreeType.OAK, 100);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        this.decorator.addTree(LOTRTreeType.PINE, 500);
        this.decorator.addTree(LOTRTreeType.PINE_SHRUB, 4000);
        this.decorator.addTree(LOTRTreeType.CHESTNUT, 500);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 20);
        this.decorator.addTree(LOTRTreeType.ASPEN, 100);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
        this.decorator.addTree(LOTRTreeType.MAPLE, 50);
        this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
        this.registerRhunPlainsFlowers();
        this.biomeColors.setGrass(12504664);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
        this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.RARE);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhun;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("rhun");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        if(random.nextInt(200) == 0) {
            for(int l = 0; l < 3; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
        if(random.nextInt(4) == 0) {
            LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
            doubleFlowerGen.setFlowerType(0);
            return doubleFlowerGen;
        }
        return super.getRandomWorldGenForDoubleFlower(random);
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.03f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
