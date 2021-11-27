package lotr.common.world.genlayer;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public class LOTRGenLayerBiomeVariantsLake extends LOTRGenLayer {
    public static final int FLAG_LAKE = 1;
    public static final int FLAG_JUNGLE = 2;
    public static final int FLAG_MANGROVE = 4;
    private int zoomScale;
    private int lakeFlags = 0;

    public LOTRGenLayerBiomeVariantsLake(long l, LOTRGenLayer layer, int i) {
        super(l);
        this.lotrParent = layer;
        this.zoomScale = IntMath.pow(2, i);
    }

    public LOTRGenLayerBiomeVariantsLake setLakeFlags(int... flags) {
        for(int f : flags) {
            this.lakeFlags = LOTRGenLayerBiomeVariantsLake.setFlag(this.lakeFlags, f);
        }
        return this;
    }

    public static int setFlag(int param, int flag) {
        return param |= flag;
    }

    public static boolean getFlag(int param, int flag) {
        return (param & flag) == flag;
    }

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] baseInts = this.lotrParent == null ? null : this.lotrParent.getInts(world, i, k, xSize, zSize);
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for(int k1 = 0; k1 < zSize; ++k1) {
            for(int i1 = 0; i1 < xSize; ++i1) {
                int baseInt;
                this.initChunkSeed(i + i1, k + k1);
                baseInt = baseInts == null ? 0 : baseInts[i1 + k1 * xSize];
                if(LOTRGenLayerBiomeVariantsLake.getFlag(this.lakeFlags, 1) && this.nextInt(30 * this.zoomScale * this.zoomScale * this.zoomScale) == 2) {
                    baseInt = LOTRGenLayerBiomeVariantsLake.setFlag(baseInt, 1);
                }
                if(LOTRGenLayerBiomeVariantsLake.getFlag(this.lakeFlags, 2) && this.nextInt(12) == 3) {
                    baseInt = LOTRGenLayerBiomeVariantsLake.setFlag(baseInt, 2);
                }
                if(LOTRGenLayerBiomeVariantsLake.getFlag(this.lakeFlags, 4) && this.nextInt(10) == 1) {
                    baseInt = LOTRGenLayerBiomeVariantsLake.setFlag(baseInt, 4);
                }
                ints[i1 + k1 * xSize] = baseInt;
            }
        }
        return ints;
    }
}
