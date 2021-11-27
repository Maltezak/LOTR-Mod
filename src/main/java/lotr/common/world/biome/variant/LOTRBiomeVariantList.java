package lotr.common.world.biome.variant;

import java.util.*;

public class LOTRBiomeVariantList {
    private float totalWeight;
    private List<VariantBucket> variantList = new ArrayList<>();

    public void add(LOTRBiomeVariant v, float f) {
        this.variantList.add(new VariantBucket(v, this.totalWeight, this.totalWeight + f));
        this.totalWeight += f;
    }

    public void clear() {
        this.totalWeight = 0.0f;
        this.variantList.clear();
    }

    public LOTRBiomeVariant get(float index) {
        if(index < 0.0f) {
            index = 0.0f;
        }
        if(index >= 1.0f) {
            index = 0.9999f;
        }
        float f = index * this.totalWeight;
        for(VariantBucket bucket : this.variantList) {
            if((f < bucket.min) || (f >= bucket.max)) continue;
            return bucket.variant;
        }
        return null;
    }

    public boolean isEmpty() {
        return this.totalWeight == 0.0f;
    }

    private static class VariantBucket {
        public final LOTRBiomeVariant variant;
        public final float min;
        public final float max;

        private VariantBucket(LOTRBiomeVariant v, float f0, float f1) {
            this.variant = v;
            this.min = f0;
            this.max = f1;
        }
    }

}
