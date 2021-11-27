package lotr.common.world.biome;

import lotr.common.world.structure2.*;

public class LOTRBiomeGenUdun extends LOTRBiomeGenMordor {
    public LOTRBiomeGenUdun(int i, boolean major) {
        super(i, major);
        this.biomeColors.setSky(6837327);
        this.biomeColors.setClouds(4797229);
        this.biomeColors.setFog(4996410);
        this.decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 20);
        this.decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 100);
    }
}
