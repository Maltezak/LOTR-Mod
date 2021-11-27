package lotr.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.fac.LOTRFaction;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class LOTRBiomeSpawnList {
    private final String biomeIdentifier;
    private List<FactionContainer> factionContainers = new ArrayList<>();
    private List<LOTRFaction> presentFactions = new ArrayList<>();
    public float conquestGainRate = 1.0f;

    public LOTRBiomeSpawnList(LOTRBiome biome) {
        this((biome).getClass().getName());
    }

    public LOTRBiomeSpawnList(String s) {
        this.biomeIdentifier = s;
    }

    public FactionContainer newFactionList(int w) {
        return this.newFactionList(w, 1.0f);
    }

    public FactionContainer newFactionList(int w, float conq) {
        FactionContainer cont = new FactionContainer(this, w);
        cont.conquestSensitivity = conq;
        this.factionContainers.add(cont);
        return cont;
    }

    public static SpawnListContainer entry(LOTRSpawnList list) {
        return LOTRBiomeSpawnList.entry(list, 1);
    }

    public static SpawnListContainer entry(LOTRSpawnList list, int weight) {
        SpawnListContainer container = new SpawnListContainer(list, weight);
        return container;
    }

    public void clear() {
        this.factionContainers.clear();
        this.presentFactions.clear();
        this.conquestGainRate = 1.0f;
    }

    private void determineFactions(World world) {
        if (this.presentFactions.isEmpty() && !this.factionContainers.isEmpty()) {
            for (FactionContainer facContainer : this.factionContainers) {
                facContainer.determineFaction(world);
                LOTRFaction fac = facContainer.theFaction;
                if (this.presentFactions.contains(fac)) continue;
                this.presentFactions.add(fac);
            }
        }
    }

    public boolean isFactionPresent(World world, LOTRFaction fac) {
        this.determineFactions(world);
        return this.presentFactions.contains(fac);
    }

    public LOTRSpawnEntry.Instance getRandomSpawnEntry(Random rand, World world, int i, int j, int k) {
        this.determineFactions(world);
        LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(i, k);
        int totalWeight = 0;
        HashMap<FactionContainer, Integer> cachedFacWeights = new HashMap<>();
        HashMap<FactionContainer, Float> cachedConqStrengths = new HashMap<>();
        for (FactionContainer cont : this.factionContainers) {
            int weight;
            float conq;
            if (cont.isEmpty() || (weight = cont.getFactionWeight(conq = cont.getEffectiveConquestStrength(world, zone))) <= 0) continue;
            totalWeight += weight;
            cachedFacWeights.put(cont, weight);
            cachedConqStrengths.put(cont, conq);
        }
        if (totalWeight > 0) {
            FactionContainer chosenFacContainer = null;
            boolean isConquestSpawn = false;
            int w = rand.nextInt(totalWeight);
            for (FactionContainer cont : this.factionContainers) {
                int facWeight;
                if (cont.isEmpty() || !cachedFacWeights.containsKey(cont) || (w -= (facWeight = cachedFacWeights.get(cont))) >= 0) continue;
                chosenFacContainer = cont;
                if (facWeight <= cont.baseWeight) break;
                isConquestSpawn = rand.nextFloat() < (float)(facWeight - cont.baseWeight) / (float)facWeight;
                break;
            }
            if (chosenFacContainer != null) {
                float conq = cachedConqStrengths.get(chosenFacContainer);
                SpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq);
                if (spawnList == null || spawnList.spawnList == null) {
                    System.out.println("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
                    FMLLog.severe("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
                    LOTRLog.logger.warn("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
                }
                LOTRSpawnEntry entry = spawnList.spawnList.getRandomSpawnEntry(rand);
                int chance = spawnList.spawnChance;
                return new LOTRSpawnEntry.Instance(entry, chance, isConquestSpawn);
            }
        }
        return null;
    }

    public List<LOTRSpawnEntry> getAllSpawnEntries(World world) {
        this.determineFactions(world);
        ArrayList<LOTRSpawnEntry> spawns = new ArrayList<>();
        for (FactionContainer facCont : this.factionContainers) {
            if (facCont.isEmpty()) continue;
            for (SpawnListContainer listCont : facCont.spawnLists) {
                LOTRSpawnList list = listCont.spawnList;
                spawns.addAll(list.getReadOnlyList());
            }
        }
        return spawns;
    }

    public boolean containsEntityClassByDefault(Class<? extends EntityLivingBase> desiredClass, World world) {
        this.determineFactions(world);
        for (FactionContainer facCont : this.factionContainers) {
            if (facCont.isEmpty() || facCont.isConquestFaction()) continue;
            for (SpawnListContainer listCont : facCont.spawnLists) {
                LOTRSpawnList list = listCont.spawnList;
                for (LOTRSpawnEntry e : list.getReadOnlyList()) {
                    Class spawnClass = e.entityClass;
                    if (!desiredClass.isAssignableFrom(spawnClass)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public static class SpawnListContainer {
        private final LOTRSpawnList spawnList;
        private final int weight;
        private int spawnChance = 0;
        private float conquestThreshold = -1.0f;

        public SpawnListContainer(LOTRSpawnList list, int w) {
            this.spawnList = list;
            this.weight = w;
        }

        public SpawnListContainer setSpawnChance(int i) {
            this.spawnChance = i;
            return this;
        }

        public SpawnListContainer setConquestOnly() {
            return this.setConquestThreshold(0.0f);
        }

        public SpawnListContainer setConquestThreshold(float f) {
            this.conquestThreshold = f;
            return this;
        }

        public boolean canSpawnAtConquestLevel(float conq) {
            return conq > this.conquestThreshold;
        }

        public boolean isConquestOnly() {
            return this.conquestThreshold >= 0.0f;
        }
    }

    public static class FactionContainer {
        private final LOTRBiomeSpawnList parent;
        private LOTRFaction theFaction;
        private final List<SpawnListContainer> spawnLists = new ArrayList<>();
        private final int baseWeight;
        private float conquestSensitivity = 1.0f;

        public FactionContainer(LOTRBiomeSpawnList biomeList, int w) {
            this.parent = biomeList;
            this.baseWeight = w;
        }

        public void add(SpawnListContainer ... lists) {
            for (SpawnListContainer cont : lists) {
                this.spawnLists.add(cont);
            }
        }

        public boolean isEmpty() {
            return this.spawnLists.isEmpty();
        }

        public boolean isConquestFaction() {
            return this.baseWeight <= 0;
        }

        public void determineFaction(World world) {
            if (this.theFaction == null) {
                for (SpawnListContainer cont : this.spawnLists) {
                    LOTRSpawnList list = cont.spawnList;
                    LOTRFaction fac = list.getListCommonFaction(world);
                    if (this.theFaction == null) {
                        this.theFaction = fac;
                        continue;
                    }
                    if (fac == this.theFaction) continue;
                    throw new IllegalArgumentException("Faction containers must include spawn lists of only one faction! Mismatched faction " + fac.codeName() + " in biome " + this.parent.biomeIdentifier);
                }
            }
        }

        public float getEffectiveConquestStrength(World world, LOTRConquestZone zone) {
            if (LOTRConquestGrid.conquestEnabled(world) && !zone.isEmpty()) {
                float conqStr = zone.getConquestStrength(this.theFaction, world);
                for (LOTRFaction allyFac : this.theFaction.getConquestBoostRelations()) {
                    if (this.parent.isFactionPresent(world, allyFac)) continue;
                    conqStr += zone.getConquestStrength(allyFac, world) * 0.333f;
                }
                return conqStr;
            }
            return 0.0f;
        }

        public int getFactionWeight(float conq) {
            if (conq > 0.0f) {
                float conqFactor = conq * 0.2f * this.conquestSensitivity;
                return this.baseWeight + Math.round(conqFactor);
            }
            return this.baseWeight;
        }

        public SpawnListContainer getRandomSpawnList(Random rand, float conq) {
            int totalWeight = 0;
            for (SpawnListContainer cont : this.spawnLists) {
                if (!cont.canSpawnAtConquestLevel(conq)) continue;
                totalWeight += cont.weight;
            }
            if (totalWeight > 0) {
                SpawnListContainer chosenList = null;
                int w = rand.nextInt(totalWeight);
                for (SpawnListContainer cont : this.spawnLists) {
                    if (!cont.canSpawnAtConquestLevel(conq) || (w -= cont.weight) >= 0) continue;
                    chosenList = cont;
                    break;
                }
                return chosenList;
            }
            return null;
        }
    }

}

