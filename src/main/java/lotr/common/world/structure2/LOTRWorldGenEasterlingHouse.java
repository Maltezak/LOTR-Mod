package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingHouse extends LOTRWorldGenEasterlingStructure {
    public LOTRWorldGenEasterlingHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k15;
        int k12;
        int i1;
        int k13;
        int i12;
        int j1;
        int i2;
        int k14;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -7; i13 <= 7; ++i13) {
                for(k15 = -5; k15 <= 5; ++k15) {
                    j1 = this.getTopBlock(world, i13, k15) - 1;
                    if(!this.isSurface(world, i13, j1, k15)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(i12 = -6; i12 <= 6; ++i12) {
            for(k12 = -4; k12 <= 4; ++k12) {
                i2 = Math.abs(i12);
                int k2 = Math.abs(k12);
                if((i2 == 2 || i2 == 6) && k2 == 4 || (k2 == 0 || k2 == 4) && i2 == 6) {
                    for(j1 = 5; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k12);
                    }
                    continue;
                }
                if(i2 == 6 || k2 == 4) {
                    for(j1 = 1; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k12);
                    }
                    for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.plankBlock, this.plankMeta);
                    }
                    if(k2 == 4) {
                        this.setBlockAndMetadata(world, i12, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                        this.setBlockAndMetadata(world, i12, 5, k12, this.woodBeamBlock, this.woodBeamMeta | 4);
                        continue;
                    }
                    if(i2 != 6) continue;
                    this.setBlockAndMetadata(world, i12, 4, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
                    this.setBlockAndMetadata(world, i12, 5, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
                    continue;
                }
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k12);
                }
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i12, j1, k12);
                }
                if(random.nextInt(3) != 0) continue;
                this.setBlockAndMetadata(world, i12, 1, k12, LOTRMod.thatchFloor, 0);
            }
        }
        for(i12 = -7; i12 <= 7; ++i12) {
            this.setBlockAndMetadata(world, i12, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        this.setBlockAndMetadata(world, -7, 3, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 7, 3, 0, this.fenceBlock, this.fenceMeta);
        for(int i14 : new int[] {-2, 2}) {
            for(k13 = -5; k13 <= 5; ++k13) {
                this.setBlockAndMetadata(world, i14, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
            this.setBlockAndMetadata(world, i14, 3, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i14, 3, 5, this.fenceBlock, this.fenceMeta);
        }
        for(int i14 : new int[] {-6, 6}) {
            this.setBlockAndMetadata(world, i14, 4, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i14, 4, 5, this.fenceBlock, this.fenceMeta);
        }
        for(int k151 : new int[] {-4, 4}) {
            this.setBlockAndMetadata(world, -7, 4, k151, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 7, 4, k151, this.fenceBlock, this.fenceMeta);
        }
        for(int i14 : new int[] {-4, 4}) {
            this.setBlockAndMetadata(world, i14, 2, -4, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i14, 3, -4, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i14, 2, 4, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i14, 3, 4, this.plankStairBlock, 7);
        }
        int[] i15 = new int[] {-2, 2};
        k12 = i15.length;
        for(i2 = 0; i2 < k12; ++i2) {
            k15 = i15[i2];
            this.setBlockAndMetadata(world, -6, 2, k15, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, -6, 3, k15, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 2, k15, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 6, 3, k15, this.plankStairBlock, 4);
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -5, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 5, 5, this.roofStairBlock, 3);
        }
        for(k14 = -3; k14 <= 3; ++k14) {
            this.setBlockAndMetadata(world, -7, 5, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 7, 5, k14, this.roofStairBlock, 0);
        }
        for(i1 = -6; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, -4, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 6, 4, this.roofStairBlock, 3);
        }
        for(k14 = -3; k14 <= 3; ++k14) {
            this.setBlockAndMetadata(world, -6, 6, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6, 6, k14, this.roofStairBlock, 0);
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k12 = -3; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, i1, 6, k12, this.roofBlock, this.roofMeta);
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 7, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 7, 2, this.roofStairBlock, 3);
        }
        for(k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, -5, 7, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 5, 7, k14, this.roofStairBlock, 0);
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k12 = -1; k12 <= 1; ++k12) {
                this.setBlockAndMetadata(world, i1, 7, k12, this.roofBlock, this.roofMeta);
            }
        }
        this.setBlockAndMetadata(world, -6, 5, -5, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, -5, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -7, 5, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 6, 5, -5, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, -5, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 5, -4, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -6, 5, 5, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, 5, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -7, 5, 4, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 6, 5, 5, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, 5, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 7, 5, 4, this.roofStairBlock, 7);
        for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 5, 3, this.roofStairBlock, 6);
        }
        for(k14 = -2; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, -5, 5, k14, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 5, k14, this.roofStairBlock, 5);
        }
        this.setBlockAndMetadata(world, -1, 1, -4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 1, -4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 0, -4, this.woodBeamBlock, this.woodBeamMeta | 4);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = 1; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, i1, 0, k12, this.brickBlock, this.brickMeta);
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k12 = 2; k12 <= 4; ++k12) {
                for(int j12 = 1; j12 <= 6; ++j12) {
                    this.setBlockAndMetadata(world, i1, j12, k12, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, 4, this.brickStairBlock, 3);
        }
        for(int j13 = 7; j13 <= 8; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, 3, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, 3, Blocks.flower_pot, 0);
        this.setBlockAndMetadata(world, 0, 0, 3, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, Blocks.fire, 0);
        this.setAir(world, 0, 2, 3);
        this.setBlockAndMetadata(world, 0, 1, 2, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 2, 2, Blocks.furnace, 2);
        this.spawnItemFrame(world, 0, 3, 2, 2, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 3, 0, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 2, 3, 0, LOTRMod.chandelier, 0);
        for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(int i14 : new int[] {-2, 2}) {
            for(k13 = -3; k13 <= -1; ++k13) {
                this.setBlockAndMetadata(world, i14, 0, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
        }
        for(int i16 = -5; i16 <= -3; ++i16) {
            this.setBlockAndMetadata(world, i16, 1, -3, this.plankStairBlock, 7);
            if(random.nextBoolean()) {
                this.placePlate(world, random, i16, 2, -3, this.plateBlock, LOTRFoods.RHUN);
                continue;
            }
            this.placeMug(world, random, i16, 2, -3, 2, LOTRFoods.RHUN_DRINK);
        }
        this.setBlockAndMetadata(world, -4, 1, -1, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 1, 2, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 3, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -5, 1, 3, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, -5, 2, 3, this.plateBlock, LOTRFoods.RHUN);
        this.setBlockAndMetadata(world, 5, 1, -3, this.tableBlock, 0);
        this.placeChest(world, random, 5, 1, -2, 5, this.chestContents);
        this.setBlockAndMetadata(world, 5, 1, -1, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 5, 1, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 5, 2, 0, LOTRMod.ceramicPlateBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 1, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 5, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.placeMug(world, random, 5, 2, 2, 1, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, 5, 1, 3, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, 5, 2, 3, 5, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 4);
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
        }
        return true;
    }
}
