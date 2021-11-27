package lotr.common.world.biome;

public class LOTRBiomeGenGorgoroth extends LOTRBiomeGenMordor {
    public LOTRBiomeGenGorgoroth(int i, boolean major) {
        super(i, major);
        this.enableMordorBoulders = false;
        this.decorator.grassPerChunk = 0;
        this.biomeColors.setSky(5843484);
    }
}
