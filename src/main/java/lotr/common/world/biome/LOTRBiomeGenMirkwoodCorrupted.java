package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.spawning.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenMirkwoodCorrupted extends LOTRBiomeGenMirkwood {
    public LOTRBiomeGenMirkwoodCorrupted(int i, boolean major) {
        super(i, major);
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 6, 4, 4));
        this.variantChance = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.decorator.treesPerChunk = 8;
        this.decorator.willowPerChunk = 1;
        this.decorator.vinesPerChunk = 20;
        this.decorator.logsPerChunk = 3;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 12;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.enableFern = true;
        this.decorator.mushroomsPerChunk = 4;
        this.decorator.generateCobwebs = false;
        this.decorator.addTree(LOTRTreeType.MIRK_OAK_LARGE, 1000);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        this.decorator.addTree(LOTRTreeType.FIR, 200);
        this.decorator.addTree(LOTRTreeType.PINE, 400);
        this.biomeColors.setGrass(2841381);
        this.biomeColors.setFoliage(2503461);
        this.biomeColors.setFog(3302525);
        this.biomeColors.setFoggy(true);
        this.biomeColors.setWater(1708838);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        this.invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("mirkwood");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int i1;
        int l;
        super.decorate(world, random, i, k);
        if(this.decorator.treesPerChunk > 2) {
            for(l = 0; l < this.decorator.treesPerChunk / 2; ++l) {
                i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
                LOTRTreeType.MIRK_OAK.create(false, random).generate(world, random, i1, j1, k1);
            }
        }
        for(l = 0; l < 6; ++l) {
            i1 = i + random.nextInt(16) + 8;
            int j1 = random.nextInt(128);
            int k1 = k + random.nextInt(16) + 8;
            new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
