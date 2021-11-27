package lotr.common.world.biome;

public class LOTRBiomeGenFarHaradJungleLake extends LOTRBiomeGenFarHaradJungle {
    public LOTRBiomeGenFarHaradJungleLake(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.decorator.sandPerChunk = 0;
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }
}
