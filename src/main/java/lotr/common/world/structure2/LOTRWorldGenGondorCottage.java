package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorCottage extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorCottage(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.wallBlock = LOTRMod.daub;
        this.wallMeta = 0;
        if(random.nextInt(3) == 0) {
            this.roofBlock = this.brick2Block;
            this.roofMeta = this.brick2Meta;
            this.roofSlabBlock = this.brick2SlabBlock;
            this.roofSlabMeta = this.brick2SlabMeta;
            this.roofStairBlock = this.brick2StairBlock;
            this.bedBlock = Blocks.bed;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k14;
        int k12;
        int j1;
        int i1;
        int j12;
        int i12;
        int l;
        int j13;
        int i13;
        int k13;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i14 = -6; i14 <= 6; ++i14) {
                for(k14 = -7; k14 <= 10; ++k14) {
                    j1 = this.getTopBlock(world, i14, k14) - 1;
                    if(!this.isSurface(world, i14, j1, k14)) {
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
        for(i12 = -5; i12 <= 5; ++i12) {
            for(k12 = -5; k12 <= 5; ++k12) {
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k12);
                if(i2 == 5 && k2 == 5) {
                    for(j1 = 3; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k12);
                    }
                    continue;
                }
                if(i2 == 5 || k2 == 5) {
                    for(j1 = 1; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k12);
                    }
                    this.setBlockAndMetadata(world, i12, 2, k12, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i12, 3, k12, this.wallBlock, this.wallMeta);
                    continue;
                }
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.rockBlock, this.rockMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k12);
                }
                for(j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i12, j1, k12);
                }
                if(random.nextInt(3) != 0) {
                    this.setBlockAndMetadata(world, i12, 1, k12, LOTRMod.thatchFloor, 0);
                }
                if(i2 != 4 || k2 != 4) continue;
                for(j1 = 1; j1 <= 4; ++j1) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.woodBeamBlock, this.woodBeamMeta);
                }
            }
        }
        for(i12 = -5; i12 <= 5; ++i12) {
            for(k12 = -7; k12 <= -6; ++k12) {
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k12)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i12, j12, k12, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i12, j12 - 1, k12);
                }
                for(j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k12 = 6; k12 <= 10; ++k12) {
                if(k12 == 10 && Math.abs(i12) >= 3) continue;
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i12, j12, k12)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i12, j12, k12, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i12, j12 - 1, k12);
                }
                for(j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(int i15 : new int[] {-5, 5}) {
            this.setBlockAndMetadata(world, i15, 2, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 2, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 2, 0, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 2, 2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 2, 3, this.fenceBlock, this.fenceMeta);
        }
        for(int k141 : new int[] {-5, 5}) {
            int i16;
            for(i16 = -1; i16 <= 1; ++i16) {
                for(int j14 = 2; j14 <= 3; ++j14) {
                    this.setBlockAndMetadata(world, i16, j14, k141, this.brickBlock, this.brickMeta);
                }
            }
            for(i16 = -4; i16 <= 4; ++i16) {
                this.setBlockAndMetadata(world, i16, 4, k141, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        for(int i17 = -3; i17 <= 3; ++i17) {
            if(Math.abs(i17) <= 1) continue;
            this.setBlockAndMetadata(world, i17, 2, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i17, 3, -5, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, i17, 2, 5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i17, 3, 5, this.wallBlock, this.wallMeta);
        }
        this.setBlockAndMetadata(world, 0, 0, -5, this.rockBlock, this.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, -5, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -5, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 0, 5, this.rockBlock, this.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, 5, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 5, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, 6, Blocks.torch, 3);
        int[] i17 = new int[] {-5, 5};
        k12 = i17.length;
        for(j12 = 0; j12 < k12; ++j12) {
            k14 = i17[j12];
            for(int l2 = 0; l2 <= 2; ++l2) {
                for(int i18 = -3 + l2; i18 <= 3 - l2; ++i18) {
                    this.setBlockAndMetadata(world, i18, 5 + l2, k14, this.wallBlock, this.wallMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -5, this.wallBlock, this.wallMeta);
        this.setBlockAndMetadata(world, 0, 6, -5, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 7, -5, this.wallBlock, this.wallMeta);
        this.setBlockAndMetadata(world, 0, 5, 5, this.wallBlock, this.wallMeta);
        this.setBlockAndMetadata(world, 0, 6, 5, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 7, 5, this.wallBlock, this.wallMeta);
        for(k13 = -6; k13 <= 6; ++k13) {
            for(l = 0; l <= 5; ++l) {
                this.setBlockAndMetadata(world, -6 + l, 3 + l, k13, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 6 - l, 3 + l, k13, this.roofStairBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 8, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k13, this.roofSlabBlock, this.roofSlabMeta);
            if(Math.abs(k13) != 6) continue;
            for(l = 0; l <= 4; ++l) {
                this.setBlockAndMetadata(world, -5 + l, 3 + l, k13, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 5 - l, 3 + l, k13, this.roofStairBlock, 5);
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(k13 = -4; k13 <= 4; ++k13) {
            this.setBlockAndMetadata(world, 0, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(j13 = 1; j13 <= 7; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, 0, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 3, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, -1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -4, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 3, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 0, 3, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, -4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 5, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 6, 4, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -2, 1, -4, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, -4, this.bedBlock, 11);
        this.setBlockAndMetadata(world, 2, 1, -4, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, -4, this.bedBlock, 9);
        this.setBlockAndMetadata(world, -4, 1, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 1, -3, this.bedBlock, 10);
        this.setBlockAndMetadata(world, 4, 1, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, 4, 1, -3, this.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, -4, 1, 1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
        this.placePlateWithCertainty(world, random, -4, 2, 1, this.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, -4, 1, 2, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -4, 1, 3, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
        this.placeMug(world, random, -4, 2, 3, 3, LOTRFoods.GONDOR_DRINK);
        this.setBlockAndMetadata(world, -3, 1, 4, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
        this.placeFlowerPot(world, -3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, 0, this.tableBlock, 0);
        this.placeChest(world, random, 4, 1, 1, 5, this.chestContents);
        this.placeChest(world, random, 4, 1, 2, 5, this.chestContents);
        this.setBlockAndMetadata(world, 4, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, 4, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
        this.placeFlowerPot(world, 3, 2, 4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -5, 1, -6, LOTRMod.reedBars, 0);
        for(i1 = -5; i1 <= -3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, -7, LOTRMod.reedBars, 0);
        }
        this.placeFlowerPot(world, -4, 1, -6, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -3, 1, -6, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 2, 1, -6, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, 3, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 5, 1, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 2, -6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 4, 1, -7, Blocks.hay_block, 0);
        for(j13 = 1; j13 <= 2; ++j13) {
            for(k12 = 6; k12 <= 9; ++k12) {
                this.setBlockAndMetadata(world, -4, j13, k12, LOTRMod.reedBars, 0);
                this.setBlockAndMetadata(world, 4, j13, k12, LOTRMod.reedBars, 0);
            }
            this.setBlockAndMetadata(world, -3, j13, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, -2, j13, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 2, j13, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 3, j13, 9, LOTRMod.reedBars, 0);
            for(i13 = -2; i13 <= 2; ++i13) {
                this.setBlockAndMetadata(world, i13, j13, 10, LOTRMod.reedBars, 0);
            }
        }
        int[] j15 = new int[] {-2, 1};
        i13 = j15.length;
        for(j12 = 0; j12 < i13; ++j12) {
            int i15;
            for(int i2 = i15 = j15[j12]; i2 <= i15 + 1; ++i2) {
                for(int k15 = 7; k15 <= 8; ++k15) {
                    this.setBlockAndMetadata(world, i2, 0, k15, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i2, 1, k15, this.cropBlock, this.cropMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, -1, 9, Blocks.dirt, 0);
        this.setGrassToDirt(world, 0, -2, 9);
        this.setBlockAndMetadata(world, 0, 0, 9, Blocks.water, 0);
        this.setBlockAndMetadata(world, 0, 1, 9, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 2, 9, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 9, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 0, 4, 9, Blocks.pumpkin, 0);
        int men = 1 + random.nextInt(2);
        for(l = 0; l < men; ++l) {
            LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, -1, 16);
        }
        return true;
    }
}
