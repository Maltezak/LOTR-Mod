package lotr.common.world.mapgen;

import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LOTRMapGenCavesUtumno extends LOTRMapGenCaves {
    @Override
    protected int caveRarity() {
        return 3;
    }

    @Override
    protected int getCaveGenerationHeight() {
        return this.rand.nextInt(this.rand.nextInt(240) + 8);
    }

    @Override
    protected void digBlock(Block[] blockArray, int index, int xzIndex, int i, int j, int k, int chunkX, int chunkZ, LOTRBiome biome, boolean foundTop) {
        if(j < LOTRUtumnoLevel.forY(0).getLowestCorridorFloor() || j > LOTRUtumnoLevel.forY(255).getHighestCorridorRoof()) {
            return;
        }
        for(int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
            LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
            LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
            if(j <= levelLower.getHighestCorridorRoof() || j >= levelUpper.getLowestCorridorFloor()) continue;
            return;
        }
        blockArray[index] = Blocks.air;
    }
}
