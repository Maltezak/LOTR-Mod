package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerNarrowRivers extends LOTRGenLayer {
    private final int maxRange;

    public LOTRGenLayerNarrowRivers(long l, LOTRGenLayer layer, int r) {
        super(l);
        this.lotrParent = layer;
        this.maxRange = r;
    }

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] rivers = this.lotrParent.getInts(world, i - this.maxRange, k - this.maxRange, xSize + this.maxRange * 2, zSize + this.maxRange * 2);
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for(int k1 = 0; k1 < zSize; ++k1) {
            for(int i1 = 0; i1 < xSize; ++i1) {
                this.initChunkSeed(i + i1, k + k1);
                int isRiver = rivers[i1 + this.maxRange + (k1 + this.maxRange) * (xSize + this.maxRange * 2)];
                if(isRiver > 0) {
                    block2: for(int range = 1; range <= this.maxRange; ++range) {
                        for(int k2 = k1 - range; k2 <= k1 + range; ++k2) {
                            for(int i2 = i1 - range; i2 <= i1 + range; ++i2) {
                                if(Math.abs(i2 - i1) != range && Math.abs(k2 - k1) != range || (rivers[i2 + this.maxRange + (k2 + this.maxRange) * (xSize + this.maxRange * 2)]) != 0) continue;
                                isRiver = 0;
                                break block2;
                            }
                        }
                    }
                }
                ints[i1 + k1 * xSize] = isRiver;
            }
        }
        return ints;
    }
}
