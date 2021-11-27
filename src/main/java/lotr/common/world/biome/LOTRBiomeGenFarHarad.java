package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.animal.*;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public abstract class LOTRBiomeGenFarHarad extends LOTRBiome {
    public LOTRBiomeGenFarHarad(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLion.class, 4, 2, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLioness.class, 4, 2, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGiraffe.class, 4, 4, 6));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityZebra.class, 8, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGemsbok.class, 8, 4, 8));
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 5, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 8, 4, 4));
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDikDik.class, 8, 4, 6));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCrocodile.class, 10, 4, 4));
        this.npcSpawnList.clear();
        this.decorator.biomeGemFactor = 0.75f;
        this.decorator.treesPerChunk = 0;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 12;
        this.decorator.flowersPerChunk = 3;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.ACACIA, 1000);
        this.decorator.addTree(LOTRTreeType.OAK_DESERT, 300);
        this.decorator.addTree(LOTRTreeType.BAOBAB, 20);
        this.decorator.addTree(LOTRTreeType.MANGO, 1);
        this.registerHaradFlowers();
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FAR_HARAD;
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        LOTRBiomeVariant variant = ((LOTRWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
        if(variant == LOTRBiomeVariant.RIVER && random.nextInt(3) == 0) {
            WorldGenAbstractTree bananaTree = LOTRTreeType.BANANA.create(false, random);
            int bananas = 3 + random.nextInt(8);
            for(int l = 0; l < bananas; ++l) {
                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
                bananaTree.generate(world, random, i1, j1, k1);
            }
        }
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
}
