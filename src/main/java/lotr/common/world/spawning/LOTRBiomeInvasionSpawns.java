package lotr.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.world.biome.LOTRBiome;

public class LOTRBiomeInvasionSpawns {
    private LOTRBiome theBiome;
    private Map<LOTREventSpawner.EventChance, List<LOTRInvasions>> invasionsByChance = new HashMap<>();
    private List<LOTRInvasions> registeredInvasions = new ArrayList<>();

    public LOTRBiomeInvasionSpawns(LOTRBiome biome) {
        this.theBiome = biome;
    }

    public void addInvasion(LOTRInvasions invasion, LOTREventSpawner.EventChance chance) {
        List<LOTRInvasions> chanceList = this.getInvasionsForChance(chance);
        if(chanceList.contains(invasion) || this.registeredInvasions.contains(invasion)) {
            FMLLog.warning("LOTR biome %s already has invasion %s registered", this.theBiome.biomeName, invasion.codeName());
        }
        else {
            chanceList.add(invasion);
            this.registeredInvasions.add(invasion);
        }
    }

    public void clearInvasions() {
        this.invasionsByChance.clear();
        this.registeredInvasions.clear();
    }

    public List<LOTRInvasions> getInvasionsForChance(LOTREventSpawner.EventChance chance) {
        List<LOTRInvasions> chanceList = this.invasionsByChance.get(chance);
        if(chanceList == null) {
            chanceList = new ArrayList<>();
        }
        this.invasionsByChance.put(chance, chanceList);
        return chanceList;
    }
}
