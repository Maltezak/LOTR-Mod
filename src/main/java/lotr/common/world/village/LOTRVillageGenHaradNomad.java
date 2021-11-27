package lotr.common.world.village;

import java.util.Random;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenHaradNomad
extends LOTRVillageGen {
    public LOTRVillageGenHaradNomad(LOTRBiome biome, float f) {
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
    extends LOTRVillageGen.AbstractInstance<LOTRVillageGenHaradNomad> {
        public VillageType villageType;
        private int numOuterHouses;

        public Instance(LOTRVillageGenHaradNomad village, World world, int i, int k, Random random, LocationInfo loc) {
            super(village, world, i, k, random, loc);
        }

        @Override
        protected void setupVillageProperties(Random random) {
            if (random.nextInt(3) == 0) {
                this.villageType = VillageType.BIG;
                this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 8, 14);
            } else {
                this.villageType = VillageType.SMALL;
                this.numOuterHouses = MathHelper.getRandomIntegerInRange(random, 4, 7);
            }
        }

        @Override
        public boolean isFlat() {
            return false;
        }

        @Override
        protected void addVillageStructures(Random random) {
            this.setupVillage(random);
        }

        private void setupVillage(Random random) {
            if (this.villageType == VillageType.SMALL) {
                this.addStructure(new LOTRWorldGenNPCRespawner(false){

                    @Override
                    public void setupRespawner(LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClass(LOTREntityNomad.class);
                        spawner.setCheckRanges(64, -12, 12, 24);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(40);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNPCRespawner(false){

                    @Override
                    public void setupRespawner(LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClasses(LOTREntityNomadWarrior.class, LOTREntityNomadArcher.class);
                        spawner.setCheckRanges(64, -12, 12, 12);
                        spawner.setSpawnRanges(32, -6, 6, 32);
                        spawner.setBlockEnemySpawnRange(40);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), 0, -8, 0, true);
            } else if (this.villageType == VillageType.BIG) {
                this.addStructure(new LOTRWorldGenNPCRespawner(false){

                    @Override
                    public void setupRespawner(LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClass(LOTREntityNomad.class);
                        spawner.setCheckRanges(80, -12, 12, 50);
                        spawner.setSpawnRanges(40, -8, 8, 40);
                        spawner.setBlockEnemySpawnRange(60);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNPCRespawner(false){

                    @Override
                    public void setupRespawner(LOTREntityNPCRespawner spawner) {
                        spawner.setSpawnClasses(LOTREntityNomadWarrior.class, LOTREntityNomadArcher.class);
                        spawner.setCheckRanges(80, -12, 12, 24);
                        spawner.setSpawnRanges(40, -8, 8, 40);
                        spawner.setBlockEnemySpawnRange(60);
                    }
                }, 0, 0, 0);
                this.addStructure(new LOTRWorldGenNomadWell(false), 0, 0, 0, true);
                this.addStructure(new LOTRWorldGenNomadChieftainTent(false), 0, 14, 0, true);
                this.addStructure(new LOTRWorldGenNomadBazaarTent(false), 0, -14, 2, true);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), -14, 0, 1, true);
                this.addStructure(new LOTRWorldGenNomadTentLarge(false), 14, 0, 3, true);
            }
            int minOuterSize = 0;
            if (this.villageType == VillageType.SMALL) {
                minOuterSize = MathHelper.getRandomIntegerInRange(random, 15, 25);
            } else if (this.villageType == VillageType.BIG) {
                minOuterSize = MathHelper.getRandomIntegerInRange(random, 35, 45);
            }
            float frac = 1.0f / this.numOuterHouses;
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
                int l = minOuterSize + random.nextInt(5);
                int i = Math.round(l * cos);
                int k = Math.round(l * sin);
                this.addStructure(new LOTRWorldGenNomadTent(false), i, k, r);
            }
        }

        @Override
        protected LOTRRoadType getPath(Random random, int i, int k) {
            return null;
        }

        @Override
        public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
            return false;
        }

    }

    public enum VillageType {
        SMALL,
        BIG;

    }

}

