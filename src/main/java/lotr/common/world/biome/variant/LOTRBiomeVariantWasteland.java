package lotr.common.world.biome.variant;

import java.util.Random;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeVariantWasteland extends LOTRBiomeVariant {
    private Block stoneBlock;

    public LOTRBiomeVariantWasteland(int i, String s, Block block) {
        super(i, s, LOTRBiomeVariant.VariantScale.LARGE);
        this.setTemperatureRainfall(0.0f, -0.3f);
        this.setTrees(0.1f);
        this.setGrass(0.3f);
        this.setFlowers(0.3f);
        this.stoneBlock = block;
        this.disableVillages();
    }

    @Override
    public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
        int chunkX = i & 0xF;
        int chunkZ = k & 0xF;
        int xzIndex = chunkX * 16 + chunkZ;
        int ySize = blocks.length / 256;
        double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.04, k * 0.04);
        double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3);
        d2 *= 0.3;
        double d3 = podzolNoise.func_151601_a(i * 0.04, k * 0.04);
        double d4 = podzolNoise.func_151601_a(i * 0.3, k * 0.3);
        if(d3 + (d4 *= 0.3) > 0.5) {
            int j = height;
            int index = xzIndex * ySize + j;
            blocks[index] = Blocks.dirt;
            meta[index] = 1;
        }
        else if(d1 + d2 > -0.3) {
            int j = height;
            int index = xzIndex * ySize + j;
            if(random.nextInt(5) == 0) {
                blocks[index] = Blocks.gravel;
                meta[index] = 0;
            }
            else {
                blocks[index] = this.stoneBlock;
                meta[index] = 0;
            }
            if(random.nextInt(50) == 0) {
                if(random.nextInt(3) == 0) {
                    blocks[index + 1] = this.stoneBlock;
                    meta[index + 1] = 0;
                }
                else {
                    blocks[index + 1] = Blocks.gravel;
                    meta[index + 1] = 0;
                }
            }
        }
    }
}
