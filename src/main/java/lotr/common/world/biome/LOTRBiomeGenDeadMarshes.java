package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenDeadMarshes
extends LOTRBiome {
    public LOTRBiomeGenDeadMarshes(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        this.npcSpawnList.clear();
        this.decorator.addOre(new WorldGenMinable(LOTRMod.remains, 6, Blocks.dirt), 5.0f, 55, 65);
        this.clearBiomeVariants();
        this.variantChance = 1.0f;
        this.addBiomeVariant(LOTRBiomeVariant.SWAMP_LOWLAND);
        this.decorator.sandPerChunk = 0;
        this.decorator.clayPerChunk = 0;
        this.decorator.quagmirePerChunk = 1;
        this.decorator.treesPerChunk = 0;
        this.decorator.logsPerChunk = 2;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 8;
        this.decorator.flowersPerChunk = 0;
        this.decorator.enableFern = true;
        this.decorator.enableSpecialGrasses = false;
        this.decorator.canePerChunk = 10;
        this.decorator.reedPerChunk = 2;
        this.decorator.dryReedChance = 1.0f;
        this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        this.flowers.clear();
        this.addFlower(LOTRMod.deadPlant, 0, 10);
        this.biomeColors.setGrass(8348751);
        this.biomeColors.setSky(5657394);
        this.biomeColors.setClouds(10525542);
        this.biomeColors.setFog(4210724);
        this.biomeColors.setWater(1316367);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDeadMarshes;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.NINDALF;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DEAD_MARSHES.getSubregion("deadMarshes");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int i1;
        int j1;
        int l;
        super.decorate(world, random, i, k);
        for (l = 0; l < 6; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = random.nextInt(128);
            new WorldGenFlowers(LOTRMod.deadPlant).generate(world, random, i1, j1, k1);
        }
        for (l = 0; l < 4; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            for (j1 = 128; j1 > 0 && world.isAirBlock(i1, j1 - 1, k1); --j1) {
            }
            new LOTRWorldGenMarshLights().generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
}

