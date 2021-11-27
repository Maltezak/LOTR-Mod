package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class LOTRBiomeGenWhiteMountainsFoothills extends LOTRBiomeGenWhiteMountains {
    public LOTRBiomeGenWhiteMountainsFoothills(int i, boolean major) {
        super(i, major);
        this.decorator.biomeGemFactor = 0.75f;
    }

    @Override
    protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
    }
}
