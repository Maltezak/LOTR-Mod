package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntitySeagull;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenUnderwaterElvenRuin;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenOcean extends LOTRBiome {
    private static Random iceRand = new Random();
    private WorldGenerator spongeGen = new LOTRWorldGenSeaBlock(Blocks.sponge, 0, 24);
    private WorldGenerator coralGen = new LOTRWorldGenSeaBlock(LOTRMod.coralReef, 0, 64);

    public LOTRBiomeGenOcean(int i, boolean major) {
        super(i, major);
        this.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 4, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntitySeagull.class, 20, 4, 4));
        this.npcSpawnList.clear();
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8), 4.0f, 0, 64);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, Blocks.sand), 0.5f, 56, 80);
        this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, LOTRMod.whiteSand), 0.5f, 56, 80);
        this.decorator.treesPerChunk = 1;
        this.decorator.willowPerChunk = 1;
        this.decorator.flowersPerChunk = 2;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.OAK, 1000);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        this.decorator.addTree(LOTRTreeType.BIRCH, 100);
        this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.BEECH, 50);
        this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 5);
        this.decorator.addTree(LOTRTreeType.APPLE, 3);
        this.decorator.addTree(LOTRTreeType.PEAR, 3);
        this.decorator.addRandomStructure(new LOTRWorldGenNumenorRuin(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.OCEAN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SEA.getSubregion("sea");
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterOcean;
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int j1;
        int i1;
        int k1;
        super.decorate(world, random, i, k);
        if(i < LOTRWaypoint.MITHLOND_SOUTH.getXCoord() && k > LOTRWaypoint.SOUTH_FOROCHEL.getZCoord() && k < LOTRWaypoint.ERYN_VORN.getZCoord() && random.nextInt(200) == 0) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.getTopSolidOrLiquidBlock(i1, k1);
            new LOTRWorldGenUnderwaterElvenRuin(false).generate(world, random, i1, j1, k1);
        }
        if(k > -30000) {
            if(random.nextInt(12) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
                this.spongeGen.generate(world, random, i1, j1, k1);
            }
            if(random.nextInt(4) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
                this.coralGen.generate(world, random, i1, j1, k1);
            }
        }
        if(k >= 64000) {
            float chance;
            chance = k >= 130000 ? 1.0f : (k - 64000) / 66000.0f;
            if(random.nextFloat() < chance && random.nextInt(6) == 0) {
                int palms = 1 + random.nextInt(2);
                if(random.nextInt(3) == 0) {
                    ++palms;
                }
                for(int l = 0; l < palms; ++l) {
                    int j12;
                    int k12;
                    int i12 = i + random.nextInt(16) + 8;
                    if(!world.getBlock(i12, j12 = world.getTopSolidOrLiquidBlock(i12, k12 = k + random.nextInt(16) + 8) - 1, k12).isNormalCube() || !LOTRWorldGenStructureBase2.isSurfaceStatic(world, i12, j12, k12)) continue;
                    Block prevBlock = world.getBlock(i12, j12, k12);
                    int prevMeta = world.getBlockMetadata(i12, j12, k12);
                    world.setBlock(i12, j12, k12, Blocks.dirt, 0, 2);
                    WorldGenAbstractTree palmGen = LOTRTreeType.PALM.create(false, random);
                    if(palmGen.generate(world, random, i12, j12 + 1, k12)) continue;
                    world.setBlock(i12, j12, k12, prevBlock, prevMeta, 2);
                }
            }
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }

    public static boolean isFrozen(int i, int k) {
        if(k > -30000) {
            return false;
        }
        int l = -60000 - k;
        if((l *= -1) < 1) {
            return true;
        }
        iceRand.setSeed(i * 341873128712L + k * 132897987541L);
        if((l -= Math.abs(-30000) / 2) < 0) {
            l *= -1;
            if((l = (int) Math.sqrt(l)) < 2) {
                l = 2;
            }
            return iceRand.nextInt(l) != 0;
        }
        if((l = (int) Math.sqrt(l)) < 2) {
            l = 2;
        }
        return iceRand.nextInt(l) == 0;
    }
}
