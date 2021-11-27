package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGondorStoneHouse extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorStoneHouse(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        if(random.nextBoolean()) {
            this.doorBlock = LOTRMod.doorLebethron;
        }
        this.bedBlock = Blocks.bed;
        this.plateBlock = random.nextBoolean() ? LOTRMod.plateBlock : LOTRMod.ceramicPlateBlock;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -5; i12 <= 5; ++i12) {
                for(int k122 = -7; k122 <= 6; ++k122) {
                    j1 = this.getTopBlock(world, i12, k122) - 1;
                    if(!this.isSurface(world, i12, j1, k122)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 5) continue;
                    return false;
                }
            }
        }
        for(i1 = -5; i1 <= 4; ++i1) {
            for(k1 = -7; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                Math.abs(k1);
                if(i1 == -5) {
                    for(int j12 = 1; j12 <= 8; ++j12) {
                        this.setAir(world, i1, j12, k1);
                    }
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
                    j1 = -1;
                    while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                        --j1;
                    }
                    continue;
                }
                for(j1 = 0; (((j1 == 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                if(k1 >= -4) {
                    if((k1 == -4 || k1 == 5) && i2 == 4) {
                        for(j1 = 1; j1 <= 7; ++j1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                        }
                    }
                    else if(k1 == -4 || k1 == 5 || i2 == 4) {
                        for(j1 = 1; j1 <= 7; ++j1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                        }
                    }
                    else if(k1 >= -3 && k1 <= 4 && i2 <= 3) {
                        for(j1 = 1; j1 <= 3; ++j1) {
                            this.setAir(world, i1, j1, k1);
                        }
                        this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
                        for(j1 = 5; j1 <= 9; ++j1) {
                            this.setAir(world, i1, j1, k1);
                        }
                    }
                }
                if(k1 > -5) continue;
                if(k1 == -7) {
                    if(i2 == 4 || i2 == 2) {
                        for(j1 = 1; j1 <= 3; ++j1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                        }
                        continue;
                    }
                    for(j1 = 1; j1 <= 3; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                    continue;
                }
                if(i2 == 4) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                    this.placeFlowerPot(world, i1, 2, k1, this.getRandomFlower(world, random));
                    this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            int j2;
            int i2 = Math.abs(i1);
            for(int step = 0; step <= 2; ++step) {
                int j14 = 8 + step;
                this.setBlockAndMetadata(world, i1, j14, -5 + step, this.brick2StairBlock, 2);
                this.setBlockAndMetadata(world, i1, j14, -4 + step, this.brick2Block, this.brick2Meta);
                this.setBlockAndMetadata(world, i1, j14, -3 + step, this.brick2StairBlock, 7);
                this.setBlockAndMetadata(world, i1, j14, 6 - step, this.brick2StairBlock, 3);
                this.setBlockAndMetadata(world, i1, j14, 5 - step, this.brick2Block, this.brick2Meta);
                this.setBlockAndMetadata(world, i1, j14, 4 - step, this.brick2StairBlock, 6);
                if(i2 != 4) continue;
                for(j2 = 8; j2 <= j14 - 1; ++j2) {
                    this.setBlockAndMetadata(world, i1, j2, -5 + step, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, j2, 6 - step, this.brickBlock, this.brickMeta);
                }
            }
            for(int k15 = -2; k15 <= 3; ++k15) {
                int j12 = 10;
                this.setBlockAndMetadata(world, i1, j12, k15, this.brick2Block, this.brick2Meta);
                if(i2 != 4) continue;
                for(j2 = 8; j2 <= j12 - 1; ++j2) {
                    this.setBlockAndMetadata(world, i1, j2, k15, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(i1 = -5; i1 <= -3; ++i1) {
            for(k1 = -1; k1 <= 2; ++k1) {
                for(int j13 = 1; j13 <= 11; ++j13) {
                    this.setBlockAndMetadata(world, i1, j13, k1, this.brickBlock, this.brickMeta);
                }
                this.setGrassToDirt(world, i1, 0, k1);
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -7, this.brick2SlabBlock, this.brick2SlabMeta);
            this.setBlockAndMetadata(world, i1, 4, -6, this.brick2Block, this.brick2Meta);
            this.setBlockAndMetadata(world, i1, 4, -5, this.brick2Block, this.brick2Meta);
            this.setBlockAndMetadata(world, i1, 5, -5, this.brick2SlabBlock, this.brick2SlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 3);
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 2; ++k1) {
                this.setBlockAndMetadata(world, i1, 1, k1, Blocks.carpet, 15);
            }
        }
        if(random.nextInt(4) == 0) {
            this.placeChest(world, random, 0, 0, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_HOUSE_TREASURE);
        }
        this.setBlockAndMetadata(world, 3, 2, 4, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 3, 1, LOTRMod.chandelier, 2);
        for(int k13 = 0; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, -3, 1, k13, Blocks.iron_bars, 0);
            this.setBlockAndMetadata(world, -3, 2, k13, Blocks.furnace, 4);
            this.setBlockAndMetadata(world, -4, 0, k13, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, -4, 1, k13, Blocks.fire, 0);
            for(int j13 = 2; j13 <= 10; ++j13) {
                this.setAir(world, -4, j13, k13);
            }
        }
        for(k12 = -3; k12 <= -2; ++k12) {
            this.setBlockAndMetadata(world, -3, 1, k12, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, -3, 2, k12, 3, LOTRFoods.GONDOR_DRINK);
            this.setBlockAndMetadata(world, -3, 3, k12, this.plankStairBlock, 4);
        }
        for(k12 = 3; k12 <= 4; ++k12) {
            this.setBlockAndMetadata(world, -3, 3, k12, this.plankStairBlock, 4);
        }
        this.setBlockAndMetadata(world, -3, 1, 3, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -3, 1, 4, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, -3, 2, 4, this.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
        for(k12 = 0; k12 <= 3; ++k12) {
            this.setAir(world, 3, 4, k12);
        }
        for(int step = 0; step <= 3; ++step) {
            this.setBlockAndMetadata(world, 3, 1 + step, 2 - step, this.plankStairBlock, 3);
        }
        this.setBlockAndMetadata(world, 3, 1, 1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 1, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 2, 0, this.plankStairBlock, 6);
        this.placeChest(world, random, 3, 1, -1, 5, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, 3, 1, -2, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 3, 1, -3, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 2, -3, this.fenceBlock, this.fenceMeta);
        for(k12 = -3; k12 <= -1; ++k12) {
            this.setBlockAndMetadata(world, 3, 3, k12, this.plankBlock, this.plankMeta);
        }
        this.spawnItemFrame(world, 3, 3, -1, 3, new ItemStack(Items.clock));
        for(int j15 = 1; j15 <= 3; ++j15) {
            this.setBlockAndMetadata(world, 0, j15, 5, this.pillarBlock, this.pillarMeta);
        }
        this.placeWallBanner(world, 0, 3, 5, this.bannerType, 2);
        for(int i13 : new int[] {-3, 1}) {
            this.setBlockAndMetadata(world, i13, 2, 5, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i13, 3, 5, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i13 + 1, 2, 5, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i13 + 1, 3, 5, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i13 + 2, 2, 5, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i13 + 2, 3, 5, this.brickStairBlock, 5);
        }
        for(int k13 : new int[] {-4, 5}) {
            for(int i14 : new int[] {-3, 1}) {
                this.setBlockAndMetadata(world, i14, 6, k13, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i14, 7, k13, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i14 + 1, 6, k13, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i14 + 1, 7, k13, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i14 + 1, 8, k13, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i14 + 2, 6, k13, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i14 + 2, 7, k13, this.brickStairBlock, 5);
            }
            this.setBlockAndMetadata(world, 0, 6, k13, LOTRMod.brick, 5);
        }
        this.setBlockAndMetadata(world, -2, 5, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 6, 0, LOTRMod.plateBlock, 0);
        this.setBlockAndMetadata(world, -2, 5, 1, this.plankBlock, this.plankMeta);
        this.placeMug(world, random, -2, 6, 1, 3, LOTRFoods.GONDOR_DRINK);
        for(int k13 : new int[] {-1, 2}) {
            this.setBlockAndMetadata(world, -2, 5, k13, this.bedBlock, 11);
            this.setBlockAndMetadata(world, -1, 5, k13, this.bedBlock, 3);
            this.spawnItemFrame(world, -3, 7, k13, 1, this.getGondorFramedItem(random));
        }
        for(int k14 = 0; k14 <= 1; ++k14) {
            for(int j14 = 7; j14 <= 8; ++j14) {
                this.setBlockAndMetadata(world, -3, j14, k14, this.pillarBlock, this.pillarMeta);
            }
        }
        this.placeChest(world, random, -3, 5, -3, 4, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, -3, 5, -2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -3, 5, 3, this.plankBlock, this.plankMeta);
        this.placeChest(world, random, -3, 5, 4, 4, LOTRChestContents.GONDOR_HOUSE);
        this.setBlockAndMetadata(world, 0, 9, -2, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 0, 8, -2, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, 0, 9, 3, this.brick2Block, this.brick2Meta);
        this.setBlockAndMetadata(world, 0, 8, 3, LOTRMod.chandelier, 2);
        this.setBlockAndMetadata(world, -3, 7, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 7, 3, Blocks.torch, 1);
        for(int k14 = -1; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, -5, 12, k14, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 12, k14, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -4, 12, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 12, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 12, 1, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -4, 12, 2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, -4, 13, 0, this.brickWallBlock, this.brickWallMeta);
        this.setBlockAndMetadata(world, -4, 13, 1, this.brickWallBlock, this.brickWallMeta);
        int men = 2;
        for(int l = 0; l < men; ++l) {
            LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
        }
        return true;
    }
}
