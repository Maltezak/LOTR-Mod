package lotr.common.world.village;

import java.util.Random;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenRohan
extends LOTRVillageGen {
    public LOTRVillageGenRohan(LOTRBiome biome, float f) {
        super(biome);
        this.gridScale = 14;
        this.gridRandomDisplace = 1;
        this.spawnChance = f;
        this.villageChunkRadius = 5;
    }

    @Override
    protected LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
        return new Instance(this, world, i, k, random, loc);
    }

    public static class Instance
    extends LOTRVillageGen.AbstractInstance<LOTRVillageGenRohan> {
        public VillageType villageType;
        private String[] villageName;
        private boolean palisade;

        public Instance(LOTRVillageGenRohan village, World world, int i, int k, Random random, LocationInfo loc) {
            super(village, world, i, k, random, loc);
        }

        @Override
        protected void setupVillageProperties(Random random) {
            this.villageName = LOTRNames.getRohanVillageName(random);
            this.villageType = random.nextInt(3) == 0 ? VillageType.FORT : VillageType.VILLAGE;
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
            } else if (this.villageType == VillageType.FORT) {
                this.setupFort(random);
            }
        }

        private void setupVillage(Random random) {
            this.addStructure(new LOTRWorldGenMeadHall(false), 0, 2, 0, true);
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRohanMan.class);
                    spawner.setCheckRanges(40, -12, 12, 40);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityRohirrimWarrior.class);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(20, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            int houses = 20;
            float frac = 1.0f / houses;
            float turn = 0.0f;
            while (turn < 1.0f) {
                int k;
                int l;
                int i;
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
                if (this.palisade && sin < 0.0f && Math.abs(cos) <= 0.25f) continue;
                if (random.nextBoolean()) {
                    l = 57;
                    i = Math.round(l * cos);
                    k = Math.round(l * sin);
                    this.addStructure(this.getRandomHouse(random), i, k, r);
                    continue;
                }
                if (random.nextInt(3) == 0) continue;
                l = 61;
                i = Math.round(l * cos);
                k = Math.round(l * sin);
                this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
            }
            int farmX = 25;
            for (int k = -1; k <= 1; ++k) {
                int farmZ = k * 14;
                this.addStructure(this.getRandomFarm(random), -farmX, farmZ, 1);
                this.addStructure(this.getRandomFarm(random), farmX, farmZ, 3);
            }
            int gardenX = 14;
            for (int k = 0; k <= 2; ++k) {
                int gardenZ = 24 + k * 8;
                this.addStructure(new LOTRWorldGenRohanVillageGarden(false), -gardenX, gardenZ, 3);
                this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 1);
            }
            int gardenZ = 41;
            for (int i = -1; i <= 1; ++i) {
                gardenX = i * 6;
                if (i == 0) continue;
                this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 0);
            }
            this.addStructure(new LOTRWorldGenRohanWell(false), 0, -23, 2, true);
            this.addStructure(new LOTRWorldGenRohanVillageSign(false).setSignText(this.villageName), 0, -11, 0, true);
            if (random.nextBoolean()) {
                int marketX = 8;
                for (int k = 0; k <= 1; ++k) {
                    int marketZ = 25 + k * 10;
                    if (random.nextBoolean()) {
                        this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), -marketX, -marketZ, 1);
                    }
                    if (!random.nextBoolean()) continue;
                    this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), marketX, -marketZ, 3);
                }
            }
            if (this.palisade) {
                int rPalisade = 81;
                int rSq = rPalisade * rPalisade;
                int rMax = rPalisade + 1;
                int rSqMax = rMax * rMax;
                for (int i = -rPalisade; i <= rPalisade; ++i) {
                    for (int k = -rPalisade; k <= rPalisade; ++k) {
                        int dSq;
                        if (Math.abs(i) <= 9 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) continue;
                        this.addStructure(new LOTRWorldGenRohanVillagePalisade(false), i, k, 0);
                    }
                }
                this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -rPalisade - 2, 0);
            }
        }

        private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
            if (random.nextInt(4) == 0) {
                int i = random.nextInt(3);
                if (i == 0) {
                    return new LOTRWorldGenRohanSmithy(false);
                }
                if (i == 1) {
                    return new LOTRWorldGenRohanStables(false);
                }
                if (i == 2) {
                    return new LOTRWorldGenRohanBarn(false);
                }
            }
            return new LOTRWorldGenRohanHouse(false);
        }

        private LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
            if (random.nextInt(3) == 0) {
                return new LOTRWorldGenRohanVillagePasture(false);
            }
            return new LOTRWorldGenRohanVillageFarm(false);
        }

        private void setupFort(Random random) {
            int farmX;
            int wallX;
            int l;
            int l2;
            int wallZ;
            this.addStructure(new LOTRWorldGenRohanFortress(false), 0, -13, 0, true);
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClasses(LOTREntityRohirrimWarrior.class, LOTREntityRohirrimArcher.class);
                    spawner.setCheckRanges(40, -12, 12, 30);
                    spawner.setSpawnRanges(32, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -53, 0, true);
            int towerX = 46;
            for (int i1 : new int[]{-towerX, towerX}) {
                this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, -towerX, 0, true);
                this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, towerX, 2, true);
            }
            for (int i1 : new int[]{-35, 35}) {
                this.addStructure(new LOTRWorldGenRohanStables(false), i1, -14, 0, true);
            }
            int farmZ = -20;
            for (l = 0; l <= 1; ++l) {
                farmX = 30 - l * 12;
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -farmX, farmZ, 2);
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), farmX, farmZ, 2);
            }
            farmZ = 26;
            for (l = -2; l <= 2; ++l) {
                farmX = l * 12;
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -farmX, farmZ, 0);
                this.addStructure(new LOTRWorldGenRohanVillageFarm(false), farmX, farmZ, 0);
            }
            for (int i1 : new int[]{-51, 51}) {
                for (int k1 : new int[]{-51, 51}) {
                    this.addStructure(new LOTRWorldGenRohanFortCorner(false), i1, k1, 0, true);
                }
            }
            for (l2 = 0; l2 <= 4; ++l2) {
                wallX = 13 + l2 * 8;
                wallZ = -51;
                this.addStructure(new LOTRWorldGenRohanFortWall(false, -3, 4), -wallX, wallZ, 0, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false, -4, 3), wallX, wallZ, 0, true);
            }
            for (l2 = -5; l2 <= 5; ++l2) {
                wallX = l2 * 9;
                wallZ = 51;
                this.addStructure(new LOTRWorldGenRohanFortWall(false), wallX, wallZ, 2, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false), -wallZ, wallX, 3, true);
                this.addStructure(new LOTRWorldGenRohanFortWall(false), wallZ, wallX, 1, true);
            }
        }

        @Override
        protected LOTRRoadType getPath(Random random, int i, int k) {
            int i1 = Math.abs(i);
            int k1 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                int dSq = i * i + k * k;
                int imn = 20 + random.nextInt(4);
                if (dSq < imn * imn) {
                    return LOTRRoadType.PATH;
                }
                int omn = 50 - random.nextInt(4);
                int omx = 56 + random.nextInt(4);
                if (dSq > omn * omn && dSq < omx * omx) {
                    return LOTRRoadType.PATH;
                }
                if (dSq < 2500 && (Math.abs(i1 - k1)) <= 2 + random.nextInt(4)) {
                    return LOTRRoadType.PATH;
                }
                if (this.palisade && k < -56 && k > -81 && i1 <= 2 + random.nextInt(4)) {
                    return LOTRRoadType.PATH;
                }
            }
            if (this.villageType == VillageType.FORT) {
                if (k <= -14 && k >= -49 && i1 <= 2) {
                    return LOTRRoadType.ROHAN;
                }
                if (k <= -14 && k >= -17 && i1 <= 37) {
                    return LOTRRoadType.PATH;
                }
                if (k >= -14 && k <= 20 && i1 >= 19 && i1 <= 22) {
                    return LOTRRoadType.PATH;
                }
                if (k >= 20 && k <= 23 && i1 <= 37) {
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
        VILLAGE,
        FORT;

    }

}

