package lotr.common.world.village;

import java.util.Random;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenDunedain
extends LOTRVillageGen {
    public LOTRVillageGenDunedain(LOTRBiome biome, float f) {
        super(biome);
        this.gridScale = 12;
        this.gridRandomDisplace = 1;
        this.spawnChance = f;
        this.villageChunkRadius = 4;
    }

    @Override
    protected LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
        return new Instance(this, world, i, k, random, loc);
    }

    public static class Instance
    extends LOTRVillageGen.AbstractInstance<LOTRVillageGenDunedain> {
        public VillageType villageType;
        private int innerSize;
        private boolean palisade;
        public Instance(LOTRVillageGenDunedain village, World world, int i, int k, Random random, LocationInfo loc) {
            super(village, world, i, k, random, loc);
        }

        @Override
        protected void setupVillageProperties(Random random) {
            this.villageType = VillageType.VILLAGE;
            this.innerSize = MathHelper.getRandomIntegerInRange(random, 12, 20);
            this.palisade = random.nextBoolean();
        }

        @Override
        public boolean isFlat() {
            return false;
        }

        @Override
        protected void addVillageStructures(Random random) {
            if (this.villageType == VillageType.VILLAGE) {
                this.setupVillage(random);
            }
        }

        private void setupVillage(Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityDunedain.class);
                    spawner.setCheckRanges(40, -12, 12, 30);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRangerNorth.class);
                    spawner.setCheckRanges(40, -12, 12, 12);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenRangerWell(false), 0, -2, 0, true);
            int lampX = 8;
            for (int i : new int[]{-lampX, lampX}) {
                for (int k : new int[]{-lampX, lampX}) {
                    this.addStructure(new LOTRWorldGenRangerVillageLight(false), i, k, 0);
                }
            }
            int houses = 20;
            float frac = 1.0f / houses;
            float turn = 0.0f;
            while (turn < 1.0f) {
                int l;
                int i;
                int k;
                float turnR = (float)Math.toRadians((turn += frac) * 360.0f);
                float sin = MathHelper.sin(turnR);
                float cos = MathHelper.cos(turnR);
                int r = 0;
                float turn8 = turn * 8.0f;
                if (turn8 >= 1.0f && turn8 < 3.0f) {
                    r = 0;
                } else if (turn8 >= 3.0f && turn8 < 5.0f) {
                    r = 1;
                } else if (turn8 >= 5.0f && turn8 < 7.0f) {
                    r = 2;
                } else if (turn8 >= 7.0f || turn8 < 1.0f) {
                    r = 3;
                }
                if (this.palisade && sin < 0.0f && Math.abs(cos) <= 0.5f) continue;
                if (random.nextInt(3) != 0) {
                    l = this.innerSize + 3;
                    if (random.nextInt(3) == 0) {
                        l += 12;
                    }
                    i = Math.round(l * cos);
                    k = Math.round(l * sin);
                    this.addStructure(this.getRandomHouse(random), i, k, r);
                    continue;
                }
                if (random.nextInt(4) != 0) continue;
                l = this.innerSize + 5;
                if (random.nextInt(3) == 0) {
                    l += 12;
                }
                i = Math.round(l * cos);
                k = Math.round(l * sin);
                this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
            }
            if (this.palisade) {
                int rPalisade = this.innerSize + 12 + 16;
                int rSq = rPalisade * rPalisade;
                int rMax = rPalisade + 1;
                int rSqMax = rMax * rMax;
                for (int i = -rPalisade; i <= rPalisade; ++i) {
                    for (int k = -rPalisade; k <= rPalisade; ++k) {
                        int dSq;
                        if (Math.abs(i) <= 5 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) continue;
                        this.addStructure(new LOTRWorldGenRangerVillagePalisade(false), i, k, 0);
                    }
                }
            }
        }

        private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
            if (random.nextInt(3) == 0) {
                int i = random.nextInt(3);
                if (i == 0) {
                    return new LOTRWorldGenRangerSmithy(false);
                }
                if (i == 1) {
                    return new LOTRWorldGenRangerStables(false);
                }
                if (i == 2) {
                    return new LOTRWorldGenRangerLodge(false);
                }
            }
            return new LOTRWorldGenRangerHouse(false);
        }

        @Override
        protected LOTRRoadType getPath(Random random, int i, int k) {
            int i1 = Math.abs(i);
            int k1 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                int dSq = i * i + k * k;
                if (i1 <= 2 && k1 <= 2) {
                    return null;
                }
                int imn = this.innerSize + random.nextInt(3);
                if (dSq < imn * imn) {
                    return LOTRRoadType.PATH;
                }
                if (this.palisade && k < 0 && k > -(this.innerSize + 12 + 16) && i1 <= 2 + random.nextInt(3)) {
                    return LOTRRoadType.PATH;
                }
            }
            return null;
        }

        @Override
        public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
            return false;
        }

    }

    public enum VillageType {
        VILLAGE;

    }

}

