package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.map.LOTRWaypoint;

public class LOTRBiomeGenRhunIslandForest extends LOTRBiomeGenRhunRedForest {
    public LOTRBiomeGenRhunIslandForest(int i, boolean major) {
        super(i, major);
        this.npcSpawnList.clear();
        this.decorator.treesPerChunk = 10;
        this.biomeColors.setFog(6132078);
        this.decorator.clearRandomStructures();
        this.decorator.clearVillages();
        this.clearTravellingTraders();
        this.invasionSpawns.clearInvasions();
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhunIsland;
    }

    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.TOL_RHUNAER;
    }
}
