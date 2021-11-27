package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.spawning.*;

public class LOTRBiomeGenFarHaradJungleEdge extends LOTRBiomeGenFarHaradJungle {
    public LOTRBiomeGenFarHaradJungleEdge(int i, boolean major) {
        super(i, major);
        this.obsidianGravelRarity = 200;
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.decorator.treesPerChunk = 4;
        this.decorator.vinesPerChunk = 10;
        this.decorator.melonPerChunk = 0.03f;
        this.decorator.clearTrees();
        this.decorator.addTree(LOTRTreeType.JUNGLE, 200);
        this.decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.MAHOGANY, 50);
        this.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
        this.decorator.addTree(LOTRTreeType.MANGO, 5);
        this.biomeColors.resetSky();
        this.biomeColors.resetFog();
        this.invasionSpawns.clearInvasions();
        this.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
    }

    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.TAUREDAIN.setRepair(0.5f);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("edge");
    }

    @Override
    public boolean hasJungleLakes() {
        return false;
    }

    @Override
    public boolean isMuddy() {
        return false;
    }
}
