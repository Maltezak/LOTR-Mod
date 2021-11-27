package lotr.common.world.biome;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBiomeGenWindMountains extends LOTRBiome {
    public LOTRBiomeGenWindMountains(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.npcSpawnList.clear();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.3f);
        this.decorator.biomeGemFactor = 1.0f;
        this.decorator.flowersPerChunk = 1;
        this.decorator.doubleFlowersPerChunk = 0;
        this.decorator.grassPerChunk = 4;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 10);
        this.decorator.addTree(LOTRTreeType.LARCH, 500);
        this.decorator.addTree(LOTRTreeType.FIR, 500);
        this.decorator.addTree(LOTRTreeType.PINE, 500);
        this.decorator.addTree(LOTRTreeType.MAPLE, 300);
        this.registerMountainsFlowers();
        this.biomeColors.setSky(11653858);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMountainsWind;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("windMountains");
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
        int snowHeight = 150 - rockDepth;
        int stoneHeight = snowHeight - 40;
        for(int j = ySize - 1; j >= stoneHeight; --j) {
            int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if(j >= snowHeight && block == this.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
            }
            else if(block == this.topBlock || block == this.fillerBlock) {
                blocks[index] = Blocks.stone;
                meta[index] = 0;
            }
            block = blocks[index];
            if(block != Blocks.stone) continue;
            if(random.nextInt(6) == 0) {
                int h = 1 + random.nextInt(6);
                for(int j1 = j; j1 > j - h && j1 > 0; --j1) {
                    int indexH = xzIndex * ySize + j1;
                    if(blocks[indexH] != Blocks.stone) continue;
                    blocks[indexH] = Blocks.stained_hardened_clay;
                    meta[indexH] = 9;
                }
                continue;
            }
            if(random.nextInt(16) != 0) continue;
            blocks[index] = Blocks.clay;
            meta[index] = 0;
        }
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int j1 = MathHelper.getRandomIntegerInRange(random, 70, 160);
            int k1 = k + random.nextInt(16) + 8;
            new LOTRWorldGenMountainsideBush(LOTRMod.leaves5, 0).generate(world, random, i1, j1, k1);
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
