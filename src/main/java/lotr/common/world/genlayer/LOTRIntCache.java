package lotr.common.world.genlayer;

import java.util.*;

import net.minecraft.world.World;

public class LOTRIntCache {
    private static LOTRIntCache SERVER = new LOTRIntCache();
    private static LOTRIntCache CLIENT = new LOTRIntCache();
    private int intCacheSize = 256;
    private List<int[]> freeSmallArrays = new ArrayList<>();
    private List<int[]> inUseSmallArrays = new ArrayList<>();
    private List<int[]> freeLargeArrays = new ArrayList<>();
    private List<int[]> inUseLargeArrays = new ArrayList<>();

    public static LOTRIntCache get(World world) {
        if(!world.isRemote) {
            return SERVER;
        }
        return CLIENT;
    }

    public int[] getIntArray(int size) {
        if(size <= 256) {
            if(this.freeSmallArrays.isEmpty()) {
                int[] ints = new int[256];
                this.inUseSmallArrays.add(ints);
                return ints;
            }
            int[] ints = this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
            this.inUseSmallArrays.add(ints);
            return ints;
        }
        if(size > this.intCacheSize) {
            this.intCacheSize = size;
            this.freeLargeArrays.clear();
            this.inUseLargeArrays.clear();
            int[] ints = new int[this.intCacheSize];
            this.inUseLargeArrays.add(ints);
            return ints;
        }
        if(this.freeLargeArrays.isEmpty()) {
            int[] ints = new int[this.intCacheSize];
            this.inUseLargeArrays.add(ints);
            return ints;
        }
        int[] ints = this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
        this.inUseLargeArrays.add(ints);
        return ints;
    }

    public void resetIntCache() {
        if(!this.freeLargeArrays.isEmpty()) {
            this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
        }
        if(!this.freeSmallArrays.isEmpty()) {
            this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
        }
        this.freeLargeArrays.addAll(this.inUseLargeArrays);
        this.freeSmallArrays.addAll(this.inUseSmallArrays);
        this.inUseLargeArrays.clear();
        this.inUseSmallArrays.clear();
    }

    public String getCacheSizes() {
        return "cache: " + this.freeLargeArrays.size() + ", tcache: " + this.freeSmallArrays.size() + ", allocated: " + this.inUseLargeArrays.size() + ", tallocated: " + this.inUseSmallArrays.size();
    }
}
