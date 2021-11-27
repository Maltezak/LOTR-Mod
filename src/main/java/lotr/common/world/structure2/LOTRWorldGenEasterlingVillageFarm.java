package lotr.common.world.structure2;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public abstract class LOTRWorldGenEasterlingVillageFarm extends LOTRWorldGenEasterlingStructure {
    public LOTRWorldGenEasterlingVillageFarm(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -5; i1 <= 5; ++i1) {
                for(int k1 = -5; k1 <= 5; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 4) continue;
                    return false;
                }
            }
        }
        if(this.restrictions) {
            int highestHeight = 0;
            for(int i1 = -6; i1 <= 6; ++i1) {
                for(int k1 = -6; k1 <= 6; ++k1) {
                    int j12;
                    int i2 = Math.abs(i1);
                    int k2 = Math.abs(k1);
                    if((i2 != 6 || k2 != 0) && (k2 != 6 || i2 != 0) || !this.isSurface(world, i1, j12 = this.getTopBlock(world, i1, k1) - 1, k1) || j12 <= highestHeight) continue;
                    highestHeight = j12;
                }
            }
            this.originY = this.getY(highestHeight);
        }
        for(int i1 = -5; i1 <= 5; ++i1) {
            for(int k1 = -5; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 == 5 && k2 == 5) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                if(i2 == 5 || k2 == 5) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
                    if(i2 == 2 || k2 == 2) {
                        this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                        this.setBlockAndMetadata(world, i1, 2, k1, this.brickBlock, this.brickMeta);
                        this.setBlockAndMetadata(world, i1, 3, k1, this.brickWallBlock, this.brickWallMeta);
                        this.setBlockAndMetadata(world, i1, 4, k1, Blocks.torch, 5);
                    }
                    if(i2 != 0 && k2 != 0) continue;
                    this.setAir(world, i1, 1, k1);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
            }
        }
        return true;
    }

    public static class Tree extends LOTRWorldGenEasterlingVillageFarm {
        public Tree(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            int i1;
            if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for(i1 = -5; i1 <= 5; ++i1) {
                for(int k1 = -5; k1 <= 5; ++k1) {
                    int i2 = Math.abs(i1);
                    int k2 = Math.abs(k1);
                    if(i2 != 5 || k2 != 5) continue;
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickWallBlock, this.brickWallMeta);
                    this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.leaves6, 6);
                }
            }
            for(int l = 0; l < 16; ++l) {
                LOTRTreeType tree = Tree.getRandomTree(random);
                WorldGenAbstractTree treeGen = tree.create(this.notifyChanges, random);
                if(treeGen == null) continue;
                int i12 = 0;
                int j1 = 1;
                int k1 = 0;
                if(treeGen.generate(world, random, this.getX(i12, k1), this.getY(j1), this.getZ(i12, k1))) break;
            }
            for(i1 = -4; i1 <= 4; ++i1) {
                for(int k1 = -4; k1 <= 4; ++k1) {
                    int j1 = 1;
                    if(this.isOpaque(world, i1, j1, k1) || random.nextInt(8) != 0) continue;
                    this.plantFlower(world, random, i1, j1, k1);
                }
            }
            return true;
        }

        public static LOTRTreeType getRandomTree(Random random) {
            ArrayList<LOTRTreeType> treeList = new ArrayList<>();
            treeList.add(LOTRTreeType.BEECH);
            treeList.add(LOTRTreeType.BEECH_LARGE);
            treeList.add(LOTRTreeType.MAPLE);
            treeList.add(LOTRTreeType.MAPLE_LARGE);
            treeList.add(LOTRTreeType.CYPRESS);
            treeList.add(LOTRTreeType.ALMOND);
            treeList.add(LOTRTreeType.OLIVE);
            treeList.add(LOTRTreeType.DATE_PALM);
            treeList.add(LOTRTreeType.POMEGRANATE);
            return(treeList.get(random.nextInt(treeList.size())));
        }
    }

    public static class Animals extends LOTRWorldGenEasterlingVillageFarm {
        public Animals(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            int i1;
            if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 1, -5, this.fenceGateBlock, 0);
                this.setBlockAndMetadata(world, i1, 1, 5, this.fenceGateBlock, 2);
            }
            for(int k1 = -1; k1 <= 1; ++k1) {
                this.setBlockAndMetadata(world, -5, 1, k1, this.fenceGateBlock, 1);
                this.setBlockAndMetadata(world, 5, 1, k1, this.fenceGateBlock, 3);
            }
            for(i1 = -1; i1 <= 1; ++i1) {
                for(int k1 = -1; k1 <= 1; ++k1) {
                    if(random.nextInt(3) != 0) continue;
                    int j1 = 1;
                    int j2 = 1;
                    if(i1 == 0 && k1 == 0 && random.nextBoolean()) {
                        ++j2;
                    }
                    for(int j3 = j1; j3 <= j2; ++j3) {
                        this.setBlockAndMetadata(world, i1, j3, k1, Blocks.hay_block, 0);
                    }
                }
            }
            int animals = 4 + random.nextInt(5);
            for(int l = 0; l < animals; ++l) {
                EntityAnimal animal = Animals.getRandomAnimal(world, random);
                int i12 = 3 * (random.nextBoolean() ? 1 : -1);
                int k1 = 3 * (random.nextBoolean() ? 1 : -1);
                this.spawnNPCAndSetHome(animal, world, i12, 1, k1, 0);
                animal.detachHome();
            }
            return true;
        }

        private static EntityAnimal getRandomAnimal(World world, Random random) {
            int animal = random.nextInt(4);
            if(animal == 0) {
                return new EntityCow(world);
            }
            if(animal == 1) {
                return new EntityPig(world);
            }
            if(animal == 2) {
                return new EntitySheep(world);
            }
            if(animal == 3) {
                return new EntityChicken(world);
            }
            return null;
        }
    }

    public static class Crops extends LOTRWorldGenEasterlingVillageFarm {
        public Crops(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
                return false;
            }
            for(int i1 = -4; i1 <= 4; ++i1) {
                for(int k1 = -4; k1 <= 4; ++k1) {
                    int i2 = Math.abs(i1);
                    int k2 = Math.abs(k1);
                    if(i2 <= 2 && k2 <= 2) {
                        if(i2 == 0 && k2 == 0) {
                            this.setBlockAndMetadata(world, i1, 0, k1, Blocks.water, 0);
                            this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                            this.setBlockAndMetadata(world, i1, 2, k1, Blocks.hay_block, 0);
                            this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                            this.setBlockAndMetadata(world, i1, 4, k1, Blocks.hay_block, 0);
                            this.setBlockAndMetadata(world, i1, 5, k1, Blocks.pumpkin, 2);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.farmland, 7);
                        this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
                }
            }
            this.setBlockAndMetadata(world, 0, 1, -5, this.fenceGateBlock, 0);
            this.setBlockAndMetadata(world, 0, 1, 5, this.fenceGateBlock, 2);
            this.setBlockAndMetadata(world, -5, 1, 0, this.fenceGateBlock, 1);
            this.setBlockAndMetadata(world, 5, 1, 0, this.fenceGateBlock, 3);
            int farmhands = 1 + random.nextInt(2);
            for(int l = 0; l < farmhands; ++l) {
                LOTREntityEasterlingFarmhand farmhand = new LOTREntityEasterlingFarmhand(world);
                this.spawnNPCAndSetHome(farmhand, world, 0, 1, -1, 8);
                farmhand.seedsItem = this.seedItem;
            }
            if(random.nextInt(3) == 0) {
                LOTREntityEasterlingFarmer farmer = new LOTREntityEasterlingFarmer(world);
                this.spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
            }
            return true;
        }
    }

}
