package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTownHouse extends LOTRWorldGenEasterlingStructureTown {
    public LOTRWorldGenEasterlingTownHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int k13;
        int k12;
        int l;
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -5; i12 <= 5; ++i12) {
                for(int k14 = -8; k14 <= 8; ++k14) {
                    int j14 = this.getTopBlock(world, i12, k14) - 1;
                    if(!this.isSurface(world, i12, j14, k14)) {
                        return false;
                    }
                    if(j14 < minHeight) {
                        minHeight = j14;
                    }
                    if(j14 > maxHeight) {
                        maxHeight = j14;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i13 = -4; i13 <= 4; ++i13) {
            for(int k122 = -6; k122 <= 6; ++k122) {
                int j12;
                int i2 = Math.abs(i13);
                int k2 = Math.abs(k122);
                if(i2 == 4 && (k2 == 2 || k2 == 6) || i2 == 0 && k122 == 6) {
                    for(j12 = 4; (((j12 >= 0) || !this.isOpaque(world, i13, j12, k122)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i13, j12, k122, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i13, j12 - 1, k122);
                    }
                    continue;
                }
                if(i2 == 4 || k2 == 6) {
                    for(j12 = 3; (((j12 >= 0) || !this.isOpaque(world, i13, j12, k122)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i13, j12, k122, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i13, j12 - 1, k122);
                    }
                    if(k2 == 6) {
                        this.setBlockAndMetadata(world, i13, 4, k122, this.woodBeamBlock, this.woodBeamMeta | 4);
                        continue;
                    }
                    if(i2 != 4) continue;
                    this.setBlockAndMetadata(world, i13, 4, k122, this.woodBeamBlock, this.woodBeamMeta | 8);
                    continue;
                }
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i13, j12, k122)) && (this.getY(j12) >= 0)); --j12) {
                    if(IntMath.mod(i13, 2) == 1 && IntMath.mod(k122, 2) == 1) {
                        this.setBlockAndMetadata(world, i13, j12, k122, this.pillarRedBlock, this.pillarRedMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i13, j12, k122, this.brickRedBlock, this.brickRedMeta);
                    }
                    this.setGrassToDirt(world, i13, j12 - 1, k122);
                }
                for(j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i13, j12, k122);
                }
            }
        }
        for(int k131 : new int[] {-4, 4}) {
            for(int i14 : new int[] {-4, 4}) {
                this.setBlockAndMetadata(world, i14, 2, k131 - 1, this.brickStairBlock, 7);
                this.setAir(world, i14, 2, k131);
                this.setBlockAndMetadata(world, i14, 2, k131 + 1, this.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, -4, 3, k131, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 3, k131, this.brickStairBlock, 4);
        }
        for(int i15 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i15, 2, -6, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i15, 3, -6, this.brickStairBlock, 6);
        }
        for(int i12 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i12 - 1, 2, 6, this.brickStairBlock, 4);
            this.setAir(world, i12, 2, 6);
            this.setBlockAndMetadata(world, i12 + 1, 2, 6, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i12, 3, 6, this.brickStairBlock, 7);
        }
        for(int k131 : new int[] {-7, 7}) {
            this.setBlockAndMetadata(world, -4, 3, k131, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 4, 3, k131, this.fenceBlock, this.fenceMeta);
        }
        for(int i12 : new int[] {-5, 5}) {
            this.setBlockAndMetadata(world, i12, 3, -6, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i12, 3, 6, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 3, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -5, 3, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 5, 3, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 0, -6, this.woodBeamBlock, this.woodBeamMeta | 4);
        this.setBlockAndMetadata(world, 0, 1, -6, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -6, this.doorBlock, 8);
        for(int k15 = -5; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, 0, 0, k15, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(int k131 : new int[] {-2, 2}) {
            for(int i16 = -3; i16 <= 3; ++i16) {
                this.setBlockAndMetadata(world, i16, 0, k131, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        for(int k16 = -6; k16 <= 6; ++k16) {
            for(l = 0; l <= 3; ++l) {
                j1 = 5 + l;
                this.setBlockAndMetadata(world, -4 + l, j1, k16, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - l, j1, k16, this.roofStairBlock, 0);
                if(l <= 0) continue;
                this.setBlockAndMetadata(world, -4 + l, j1 - 1, k16, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 4 - l, j1 - 1, k16, this.roofStairBlock, 5);
            }
            this.setBlockAndMetadata(world, 0, 8, k16, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k16, this.roofSlabBlock, this.roofSlabMeta);
        }
        for(int k131 : new int[] {-7, 7}) {
            for(int l2 = 0; l2 <= 2; ++l2) {
                int j12 = 5 + l2;
                this.setBlockAndMetadata(world, -3 + l2, j12, k131, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 3 - l2, j12, k131, this.roofStairBlock, 0);
                if(l2 <= 0) continue;
                this.setBlockAndMetadata(world, -3 + l2, j12 - 1, k131, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 3 - l2, j12 - 1, k131, this.roofStairBlock, 5);
            }
            this.setBlockAndMetadata(world, 0, 7, k131, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 8, k131, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 9, k131, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, -4, 4, k131, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, -3, 4, k131, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -2, 4, k131, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, -1, 4, k131, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 4, k131, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, 1, 4, k131, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, 4, k131, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, 3, 4, k131, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 4, k131, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 5, k131, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 0, 5, k131, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 1, 5, k131, this.roofSlabBlock, this.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, -8, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 9, -8, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 8, 8, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 0, 9, 8, this.roofStairBlock, 2);
        for(int k131 : new int[] {-6, 6}) {
            for(int l3 = 0; l3 <= 2; ++l3) {
                int j13 = 5 + l3;
                this.setBlockAndMetadata(world, -3 + l3, j13, k131, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, 3 - l3, j13, k131, this.roofBlock, this.roofMeta);
                for(int i17 = -2 + l3; i17 <= 2 - l3; ++i17) {
                    this.setBlockAndMetadata(world, i17, j13, k131, this.plankBlock, this.plankMeta);
                }
            }
        }
        for(int i12 : new int[] {-5, 5}) {
            this.setBlockAndMetadata(world, i12, 5, -7, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i12, 4, -6, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 4, -5, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i12, 5, -4, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i12, 4, -3, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 4, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i12, 4, 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 4, 3, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i12, 5, 4, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i12, 4, 5, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 4, 6, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i12, 5, 7, this.roofSlabBlock, this.roofSlabMeta);
        }
        int[] k16 = new int[] {-2, 2};
        l = k16.length;
        for(j1 = 0; j1 < l; ++j1) {
            k13 = k16[j1];
            this.setBlockAndMetadata(world, -5, 4, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 5, 4, k13, this.roofStairBlock, 0);
        }
        for(int i18 = -3; i18 <= 3; ++i18) {
            for(k12 = -2; k12 <= 5; ++k12) {
                this.setBlockAndMetadata(world, i18, 4, k12, this.plankSlabBlock, this.plankSlabMeta | 8);
                if(Math.abs(i18) > 2 || random.nextInt(3) != 0) continue;
                this.setBlockAndMetadata(world, i18, 5, k12, LOTRMod.thatchFloor, 0);
            }
        }
        int[] i18 = new int[] {-2, 2};
        k12 = i18.length;
        for(j1 = 0; j1 < k12; ++j1) {
            k13 = i18[j1];
            for(int i19 = -3; i19 <= 3; ++i19) {
                this.setBlockAndMetadata(world, i19, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        for(int k131 = -5; k131 <= 5; ++k131) {
            this.setBlockAndMetadata(world, 0, 4, k131, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(int j13 = 1; j13 <= 5; ++j13) {
            this.setBlockAndMetadata(world, -3, j13, 0, this.woodBeamBlock, this.woodBeamMeta);
        }
        for(int j14 = 1; j14 <= 6; ++j14) {
            this.setBlockAndMetadata(world, -2, j14, 0, this.woodBeamBlock, this.woodBeamMeta);
            this.setBlockAndMetadata(world, -1, j14, 0, Blocks.ladder, 4);
        }
        for(int i110 = 2; i110 <= 3; ++i110) {
            this.setBlockAndMetadata(world, i110, 1, -1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i110, 2, -1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i110, 3, -1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i110, 1, 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i110, 2, 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i110, 3, 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i110, 3, 0, this.brickBlock, this.brickMeta);
            for(k12 = -1; k12 <= 1; ++k12) {
                this.setBlockAndMetadata(world, i110, 4, k12, this.brickBlock, this.brickMeta);
            }
        }
        for(int k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, 2, 5, k14, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 6, k14, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k14, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 3, 6, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 3, 7, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 3, 8, 0, Blocks.flower_pot, 0);
        this.setBlockAndMetadata(world, 2, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 2, 1, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 2, 2, 0, Blocks.furnace, 5);
        this.setBlockAndMetadata(world, 3, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 3, 1, 0, Blocks.fire, 0);
        this.spawnItemFrame(world, 2, 3, 0, 3, this.getEasterlingFramedItem(random));
        this.setBlockAndMetadata(world, -2, 1, -5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 1, -5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 1, -4, this.plankStairBlock, 4);
        this.placePlate(world, random, -2, 2, -5, this.plateBlock, LOTRFoods.RHUN);
        this.placePlate(world, random, -3, 2, -5, this.plateBlock, LOTRFoods.RHUN);
        this.placeMug(world, random, -3, 2, -4, 3, LOTRFoods.RHUN_DRINK);
        this.setBlockAndMetadata(world, 3, 1, -4, this.tableBlock, 0);
        for(int k131 : new int[] {-1, 1}) {
            this.setBlockAndMetadata(world, -3, 1, k131, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.placeBarrel(world, random, -3, 2, k131, 4, LOTRFoods.RHUN_DRINK);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 5, this.plankStairBlock, 6);
            if(Math.abs(i1) < 2) continue;
            if(random.nextBoolean()) {
                this.placePlate(world, random, i1, 2, 5, this.plateBlock, LOTRFoods.RHUN);
                continue;
            }
            this.placeMug(world, random, i1, 2, 5, 0, LOTRFoods.RHUN_DRINK);
        }
        this.setBlockAndMetadata(world, -1, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 1, 1, 5, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -2, 1, 3, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 2, 1, 3, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 3, -2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, 0, 3, 2, LOTRMod.chandelier, 0);
        this.setBlockAndMetadata(world, -3, 5, -2, this.woodBeamBlock, this.woodBeamMeta);
        this.setBlockAndMetadata(world, 3, 5, -2, this.woodBeamBlock, this.woodBeamMeta);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -2, this.fenceBlock, this.fenceMeta);
        }
        for(int i12 : new int[] {-1, 1}) {
            this.setBlockAndMetadata(world, i12, 5, 4, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i12, 5, 5, this.bedBlock, 8);
        }
        this.placeChest(world, random, 0, 5, 5, 2, this.chestContents);
        this.setBlockAndMetadata(world, 0, 7, 5, LOTRMod.chandelier, 3);
        int men = 1 + random.nextInt(2);
        for(l = 0; l < men; ++l) {
            LOTREntityEasterling easterling = new LOTREntityEasterling(world);
            this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
        }
        return true;
    }
}
