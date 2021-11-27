package lotr.common.world.village;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenGulfHarad
extends LOTRVillageGen {
    public LOTRVillageGenGulfHarad(LOTRBiome biome, float f) {
        super(biome);
        this.gridScale = 14;
        this.gridRandomDisplace = 1;
        this.spawnChance = f;
        this.villageChunkRadius = 6;
    }

    @Override
    protected LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
        return new Instance(this, world, i, k, random, loc);
    }

    public static class Instance
    extends LOTRVillageGen.AbstractInstance<LOTRVillageGenGulfHarad> {
        public VillageType villageType;
        public String[] villageName;
        private int numOuterHouses;
        private boolean townWall = true;
        int rTownTower = 90;

        public Instance(LOTRVillageGenGulfHarad village, World world, int i, int k, Random random, LocationInfo loc) {
            super(village, world, i, k, random, loc);
        }

        @Override
        protected void setupVillageProperties(Random random) {
            this.villageType = random.nextInt(4) == 0 ? VillageType.FORT : (random.nextInt(3) == 0 ? VillageType.TOWN : VillageType.VILLAGE);
            this.villageName = LOTRNames.getHaradVillageName(random);
            this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
        }

        @Override
        public boolean isFlat() {
            return false;
        }

        @Override
        protected void addVillageStructures(Random random) {
            if (this.villageType == VillageType.VILLAGE) {
                this.setupVillage(random);
            } else if (this.villageType == VillageType.TOWN) {
                this.setupTown(random);
            } else if (this.villageType == VillageType.FORT) {
                this.setupFort(random);
            }
        }

        private void setupVillage(Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(64, -12, 12, 24);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
                    spawner.setCheckRanges(64, -12, 12, 12);
                    spawner.setSpawnRanges(32, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(64);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenGulfTotem(false), 0, -2, 0, true);
            this.addStructure(new LOTRWorldGenGulfTavern(false), 0, 24, 0, true);
            int rSignsInner = 11;
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), -rSignsInner, 0, 1, true);
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), rSignsInner, 0, 3, true);
            for (int h = 0; h < this.numOuterHouses; ++h) {
                float turn = (float)h / (float)(this.numOuterHouses - 1);
                float turnMin = 0.15f;
                float turnMax = 1.0f - turnMin;
                float turnInRange = turnMin + (turnMax - turnMin) * turn;
                float turnCorrected = (turnInRange + 0.25f) % 1.0f;
                float turnR = (float)Math.toRadians(turnCorrected * 360.0f);
                float sin = MathHelper.sin(turnR);
                float cos = MathHelper.cos(turnR);
                int r = 0;
                float turn8 = turnCorrected * 8.0f;
                if (turn8 >= 1.0f && turn8 < 3.0f) {
                    r = 0;
                } else if (turn8 >= 3.0f && turn8 < 5.0f) {
                    r = 1;
                } else if (turn8 >= 5.0f && turn8 < 7.0f) {
                    r = 2;
                } else if (turn8 >= 7.0f || turn8 < 1.0f) {
                    r = 3;
                }
                int l = 24;
                int i = Math.round(l * cos);
                int k = Math.round(l * sin);
                this.addStructure(this.getRandomHouse(random), i, k, r);
            }
            int numFarms = this.numOuterHouses * 2;
            float frac = 1.0f / numFarms;
            float turn = 0.0f;
            while (turn < 1.0f) {
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
                int l = 52;
                int i = Math.round(l * cos);
                int k = Math.round(l * sin);
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                    continue;
                }
                this.addStructure(this.getRandomFarm(random), i, k, r);
            }
        }

        private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
            if (random.nextInt(5) == 0) {
                return new LOTRWorldGenGulfSmithy(false);
            }
            return new LOTRWorldGenGulfHouse(false);
        }

        private LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
            if (random.nextBoolean()) {
                return new LOTRWorldGenGulfFarm(false);
            }
            return new LOTRWorldGenGulfPasture(false);
        }

        private void setupTown(Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(80, -12, 12, 100);
                    spawner.setSpawnRanges(40, -6, 6, 64);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            for (int i1 : new int[]{-40, 40}) {
                for (int k1 : new int[]{-40, 40}) {
                    this.addStructure(new LOTRWorldGenNPCRespawner(false){

                        @Override
                        public void setupRespawner(LOTREntityNPCRespawner spawner) {
                            spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
                            spawner.setCheckRanges(64, -12, 12, 20);
                            spawner.setSpawnRanges(20, -6, 6, 64);
                            spawner.setBlockEnemySpawnRange(64);
                        }
                    }, i1, k1, 0);
                }
            }
            this.addStructure(new LOTRWorldGenGulfPyramid(false), 0, -11, 0, true);
            int lightR = 15;
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, -lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, -lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, lightR, 0, true);
            this.addStructure(new LOTRWorldGenGulfBazaar(false), -74, 0, 1, true);
            this.addStructure(new LOTRWorldGenGulfAltar(false), 74, 0, 3, true);
            this.addStructure(new LOTRWorldGenGulfTotem(false), 0, 79, 0, true);
            for (int l = 0; l <= 2; ++l) {
                int i = 5;
                int k = 32 + l * 20;
                this.addStructure(new LOTRWorldGenGulfHouse(false), -i, -k, 1, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), i, -k, 3, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), -i, k, 1, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), i, k, 3, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), k, -i, 2, true);
                this.addStructure(new LOTRWorldGenGulfHouse(false), k, i, 0, true);
                if (l != 0) continue;
                this.addStructure(new LOTRWorldGenGulfSmithy(false), -k - 6, -i, 2, true);
                this.addStructure(new LOTRWorldGenGulfTavern(false), -k - 6, i, 0, true);
            }
            int xzTownTower = (int)(this.rTownTower / Math.sqrt(2.0));
            this.addStructure(new LOTRWorldGenGulfTower(false), -xzTownTower, -xzTownTower + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), xzTownTower, -xzTownTower + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), -xzTownTower, xzTownTower - 4, 0, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), xzTownTower, xzTownTower - 4, 0, true);
            int turn = 0;
            int numTurns = 24;
            while (turn <= numTurns) {
                if (++turn % 3 == 0) continue;
                float turnF = (float)turn / (float)numTurns;
                float turnR = (float)Math.toRadians(turnF * 360.0f);
                float sin = MathHelper.sin(turnR);
                float cos = MathHelper.cos(turnR);
                int r = 0;
                float turn8 = turnF * 8.0f;
                if (turn8 >= 1.0f && turn8 < 3.0f) {
                    r = 0;
                } else if (turn8 >= 3.0f && turn8 < 5.0f) {
                    r = 1;
                } else if (turn8 >= 5.0f && turn8 < 7.0f) {
                    r = 2;
                } else if (turn8 >= 7.0f || turn8 < 1.0f) {
                    r = 3;
                }
                int l = this.rTownTower - 6;
                int i = Math.round(l * cos);
                int k = Math.round(l * sin);
                if (random.nextInt(3) == 0) {
                    this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                    continue;
                }
                this.addStructure(this.getRandomFarm(random), i, k, r);
            }
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), -5, -96, 0, true);
            this.addStructure(new LOTRWorldGenGulfVillageSign(false).setSignText(this.villageName), 5, -96, 0, true);
            if (this.townWall) {
                int rSq = 9604;
                int rMax = 99;
                int rSqMax = rMax * rMax;
                for (int i = -98; i <= 98; ++i) {
                    for (int k = -98; k <= 98; ++k) {
                        int dSq;
                        int i1 = Math.abs(i);
                        if (i1 <= 6 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) continue;
                        LOTRWorldGenGulfTownWall wall = new LOTRWorldGenGulfTownWall(false);
                        if (i1 == 7 && k < 0) {
                            wall.setTall();
                        }
                        this.addStructure(wall, i, k, 0);
                    }
                }
            }
        }

        private void setupFort(Random random) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false){

                @Override
                public void setupRespawner(LOTREntityNPCRespawner spawner) {
                    spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
                    spawner.setCheckRanges(40, -12, 12, 16);
                    spawner.setSpawnRanges(24, -6, 6, 32);
                    spawner.setBlockEnemySpawnRange(60);
                }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenGulfWarCamp(false), 0, -15, 0, true);
            int towerX = 36;
            this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, -towerX + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), towerX, -towerX + 4, 2, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, towerX - 4, 0, true);
            this.addStructure(new LOTRWorldGenGulfTower(false), towerX, towerX - 4, 0, true);
            for (int l = -1; l <= 1; ++l) {
                int i = l * 16;
                int k = 28;
                this.addStructure(this.getRandomFarm(random), i, k, 0);
                this.addStructure(this.getRandomFarm(random), -k, i, 1);
                this.addStructure(this.getRandomFarm(random), k, i, 3);
            }
        }

        @Override
        protected LOTRRoadType getPath(Random random, int i, int k) {
            int dSq;
            int i1 = Math.abs(i);
            int k1 = Math.abs(k);
            if (this.villageType == VillageType.VILLAGE) {
                dSq = i * i + k * k;
                int imn = 16 - random.nextInt(3);
                int imx = 21 + random.nextInt(3);
                if (dSq > imn * imn && dSq < imx * imx) {
                    return LOTRRoadType.PATH;
                }
            }
            if (this.villageType == VillageType.TOWN) {
                dSq = i * i + k * k;
                if (dSq < 576) {
                    return LOTRRoadType.GULF_HARAD;
                }
                if (k1 <= 3 && i1 <= 74 || i1 <= 3 && k <= 74) {
                    return LOTRRoadType.GULF_HARAD;
                }
            }
            return null;
        }

        @Override
        public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
            if (this.villageType == VillageType.TOWN) {
                Block block = world.getBlock(i, j, k);
                int meta = world.getBlockMetadata(i, j, k);
                if (block == LOTRMod.brick3 && meta == 13 || block == LOTRMod.brick3 && meta == 14) {
                    return true;
                }
            }
            return false;
        }

    }

    public enum VillageType {
        VILLAGE,
        TOWN,
        FORT;

    }

}

