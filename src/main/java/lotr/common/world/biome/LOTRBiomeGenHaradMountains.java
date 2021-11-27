package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeGenHaradMountains extends LOTRBiomeGenFarHarad {
    public LOTRBiomeGenHaradMountains(int i, boolean major) {
        super(i, major);
        this.spawnableMonsterList.clear();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.decorator.biomeGemFactor = 1.0f;
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.HARAD_MOUNTAINS;
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("mountains");
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }

    @Override
    protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
        int stoneHeight = 100 - rockDepth;
        for(int j = ySize - 1; j >= stoneHeight; --j) {
            int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if(block != this.topBlock && block != this.fillerBlock) continue;
            blocks[index] = Blocks.stone;
            meta[index] = 0;
        }
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }
}
