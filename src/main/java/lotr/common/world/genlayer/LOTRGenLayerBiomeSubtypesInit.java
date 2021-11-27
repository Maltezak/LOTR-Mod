package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerBiomeSubtypesInit extends LOTRGenLayer {
    public LOTRGenLayerBiomeSubtypesInit(long l) {
        super(l);
    }

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for(int k1 = 0; k1 < zSize; ++k1) {
            for(int i1 = 0; i1 < xSize; ++i1) {
                this.initChunkSeed(i + i1, k + k1);
                ints[i1 + k1 * xSize] = this.nextInt(100);
            }
        }
        return ints;
    }
}
