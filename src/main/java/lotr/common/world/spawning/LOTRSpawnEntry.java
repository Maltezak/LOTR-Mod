package lotr.common.world.spawning;

import net.minecraft.world.biome.BiomeGenBase;

public class LOTRSpawnEntry extends BiomeGenBase.SpawnListEntry {
    public LOTRSpawnEntry(Class c, int weight, int min, int max) {
        super(c, weight, min, max);
    }

    public static class Instance {
        public final LOTRSpawnEntry spawnEntry;
        public final int spawnChance;
        public final boolean isConquestSpawn;

        public Instance(LOTRSpawnEntry entry, int chance, boolean conquest) {
            this.spawnEntry = entry;
            this.spawnChance = chance;
            this.isConquestSpawn = conquest;
        }
    }

}
