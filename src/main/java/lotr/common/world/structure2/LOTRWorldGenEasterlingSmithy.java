package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingSmithy extends LOTRWorldGenEasterlingStructureTown {
    public LOTRWorldGenEasterlingSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int j1;
        int i2;
        int i1;
        int i12;
        int i13;
        int i14;
        int j12;
        int i22;
        int k2;
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i13 = -7; i13 <= 7; ++i13) {
                for(int k12 = -7; k12 <= 7; ++k12) {
                    j12 = this.getTopBlock(world, i13, k12) - 1;
                    if(!this.isSurface(world, i13, j12, k12)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(i14 = -6; i14 <= 6; ++i14) {
            for(k1 = -6; k1 <= 6; ++k1) {
                i22 = Math.abs(i14);
                k2 = Math.abs(k1);
                if(i22 == 6 && k2 % 4 == 2 || k2 == 6 && i22 % 4 == 2) {
                    for(j12 = 4; (((j12 >= 0) || !this.isOpaque(world, i14, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i14, j12, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i14, j12 - 1, k1);
                    }
                    continue;
                }
                if(i22 == 6 || k2 == 6) {
                    for(j12 = 3; (((j12 >= 0) || !this.isOpaque(world, i14, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i14, j12, k1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i14, j12 - 1, k1);
                    }
                    if(k2 == 6) {
                        this.setBlockAndMetadata(world, i14, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                        continue;
                    }
                    if(i22 != 6) continue;
                    this.setBlockAndMetadata(world, i14, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                    continue;
                }
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i14, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i14, j12, k1, this.brickRedBlock, this.brickRedMeta);
                    this.setGrassToDirt(world, i14, j12 - 1, k1);
                }
                for(j12 = 1; j12 <= 9; ++j12) {
                    this.setAir(world, i14, j12, k1);
                }
                if(IntMath.mod(i14, 2) != 1 || IntMath.mod(k1, 2) != 1) continue;
                this.setBlockAndMetadata(world, i14, 0, k1, this.pillarRedBlock, this.pillarRedMeta);
            }
        }
        for(i14 = -5; i14 <= 5; ++i14) {
            this.setBlockAndMetadata(world, i14, 0, -2, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i14, 0, 2, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(int k13 = -5; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, -2, 0, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 2, 0, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        this.setBlockAndMetadata(world, -4, 2, -6, LOTRMod.reedBars, 0);
        this.setBlockAndMetadata(world, 4, 2, -6, LOTRMod.reedBars, 0);
        for(int k12 : new int[] {-4, 0}) {
            this.setBlockAndMetadata(world, -6, 2, k12 - 1, this.brickStairBlock, 7);
            this.setAir(world, -6, 2, k12);
            this.setBlockAndMetadata(world, -6, 2, k12 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -6, 3, k12, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 2, k12 - 1, this.brickStairBlock, 7);
            this.setAir(world, 6, 2, k12);
            this.setBlockAndMetadata(world, 6, 2, k12 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 6, 3, k12, this.brickStairBlock, 4);
        }
        for(int k12 : new int[] {-7, 7}) {
            this.setBlockAndMetadata(world, -6, 3, k12, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 6, 3, k12, this.fenceBlock, this.fenceMeta);
        }
        int[] k13 = new int[] {-7, 7};
        k1 = k13.length;
        for(i22 = 0; i22 < k1; ++i22) {
            int i15 = k13[i22];
            this.setBlockAndMetadata(world, i15, 3, -6, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 3, 6, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, -7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, 7, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -7, 3, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -7, 3, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 7, 3, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 7, 3, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 0, -6, this.woodBeamBlock, this.woodBeamMeta | 4);
        this.setBlockAndMetadata(world, 0, 1, -6, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -6, this.doorBlock, 8);
        for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = -7; k1 <= 7; ++k1) {
                i22 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i22 == 7 && k2 == 7 || i22 == 7 && (k2 == 0 || k2 == 4) || k2 == 7 && (i22 == 0 || i22 == 4)) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.roofSlabBlock, this.roofSlabMeta);
                }
                if(k2 == 7) {
                    if(i1 == -6 || i1 == -3 || i1 == 1 || i1 == 5) {
                        this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 5);
                    }
                    if(i1 == -5 || i1 == -1 || i1 == 3 || i1 == 6) {
                        this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 4);
                    }
                    if(i22 == 2) {
                        this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, k1 < 0 ? 2 : 3);
                    }
                }
                if(i22 != 7) continue;
                if(k1 == -6 || k1 == -3 || k1 == 1 || k1 == 5) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 6);
                }
                if(k1 == -5 || k1 == -1 || k1 == 3 || k1 == 6) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 7);
                }
                if(k2 != 2) continue;
                this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, i1 < 0 ? 1 : 0);
            }
        }
        for(int step = 0; step <= 1; ++step) {
            int k14;
            j1 = 5 + step;
            for(i13 = -6 + step; i13 <= 6 - step; ++i13) {
                this.setBlockAndMetadata(world, i13, j1, -6 + step, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i13, j1, 6 - step, this.roofStairBlock, 3);
            }
            for(k14 = -6 + step; k14 <= 6 - step; ++k14) {
                this.setBlockAndMetadata(world, -6 + step, j1, k14, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 6 - step, j1, k14, this.roofStairBlock, 0);
            }
            for(i13 = -5 + step; i13 <= 5 - step; ++i13) {
                this.setBlockAndMetadata(world, i13, j1, -5 + step, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i13, j1, 5 - step, this.roofStairBlock, 6);
            }
            for(k14 = -5 + step; k14 <= 5 - step; ++k14) {
                this.setBlockAndMetadata(world, -5 + step, j1, k14, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 5 - step, j1, k14, this.roofStairBlock, 5);
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                i22 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i22 <= 1 && k2 >= 3 || k2 <= 1 && i22 >= 3 || i22 >= 2 && k2 >= 2) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
                }
                if(i22 != 2 || k2 != 2) continue;
                this.setBlockAndMetadata(world, i1, 6, k1, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i1, 5, k1, LOTRMod.chandelier, 0);
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            i2 = Math.abs(i1);
            if(i2 >= 2) {
                this.setBlockAndMetadata(world, i1, 7, -2, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i1, 7, 2, this.roofStairBlock, 3);
            }
            if(i2 >= 1) {
                this.setBlockAndMetadata(world, i1, 8, -1, this.roofStairBlock, 7);
                this.setBlockAndMetadata(world, i1, 8, 1, this.roofStairBlock, 6);
            }
            if(i2 < 0) continue;
            this.setBlockAndMetadata(world, i1, 9, 0, this.roofSlabBlock, this.roofSlabMeta);
        }
        for(int k15 = -4; k15 <= 4; ++k15) {
            int k22 = Math.abs(k15);
            if(k22 >= 2) {
                this.setBlockAndMetadata(world, -2, 7, k15, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 2, 7, k15, this.roofStairBlock, 0);
            }
            if(k22 >= 1) {
                this.setBlockAndMetadata(world, -1, 8, k15, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 1, 8, k15, this.roofStairBlock, 5);
            }
            if(k22 < 0) continue;
            this.setBlockAndMetadata(world, 0, 9, k15, this.roofSlabBlock, this.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, -4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 9, 4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 9, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 9, 0, this.roofStairBlock, 1);
        for(int k12 : new int[] {-3, 3}) {
            this.setBlockAndMetadata(world, -1, 7, k12, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 0, 7, k12, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 1, 7, k12, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 8, k12, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 8, k12, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 8, k12, this.roofBlock, this.roofMeta);
        }
        for(int i15 : new int[] {-3, 3}) {
            this.setBlockAndMetadata(world, i15, 7, -1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i15, 7, 0, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i15, 7, 1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i15, 8, -1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i15, 8, 0, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i15, 8, 1, this.roofBlock, this.roofMeta);
        }
        this.setBlockAndMetadata(world, 0, 4, -7, this.plankBlock, this.plankMeta);
        this.spawnItemFrame(world, 0, 4, -7, 2, new ItemStack(LOTRMod.blacksmithHammer));
        this.setBlockAndMetadata(world, -2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 1);
        for(i12 = -5; i12 <= 5; ++i12) {
            i2 = Math.abs(i12);
            if(i2 == 2) {
                this.setBlockAndMetadata(world, i12, 1, -2, this.woodBeamBlock, this.woodBeamMeta);
                this.setBlockAndMetadata(world, i12, 2, -2, this.plankSlabBlock, this.plankSlabMeta);
                continue;
            }
            if(i12 == 4) {
                this.setBlockAndMetadata(world, i12, 1, -2, this.fenceGateBlock, 0);
                continue;
            }
            this.setBlockAndMetadata(world, i12, 1, -2, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, 3, 1, 5, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, 5, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 5, this.woodBeamBlock, this.woodBeamMeta);
        for(int k16 = 3; k16 <= 4; ++k16) {
            this.placeChest(world, random, 5, 1, k16, 5, LOTRChestContents.EASTERLING_SMITHY);
        }
        this.placeWeaponRack(world, 4, 3, 5, 6, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, 5, 3, 4, 7, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
        this.placeArmorStand(world, 5, 1, 0, 1, null);
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k1 = 4; k1 <= 6; ++k1) {
                for(int j13 = 1; j13 <= 6; ++j13) {
                    this.setBlockAndMetadata(world, i12, j13, k1, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(int j14 = 1; j14 <= 5; ++j14) {
            this.setAir(world, 0, j14, 5);
        }
        this.setBlockAndMetadata(world, 0, 0, 5, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 5, Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 0, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 1, 4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 3, 4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 5, 4, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -1, 6, 6, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 6, 6, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 7, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, 6, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 9, 6, Blocks.flower_pot, 0);
        for(i12 = -3; i12 <= -2; ++i12) {
            for(j1 = 1; j1 <= 2; ++j1) {
                this.setBlockAndMetadata(world, i12, j1, 5, this.brickBlock, this.brickMeta);
            }
        }
        this.setBlockAndMetadata(world, -3, 1, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 1, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -3, 2, 3, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -5, 1, 3, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -4, 1, 3, LOTRMod.alloyForge, 2);
        this.setBlockAndMetadata(world, -5, 2, 3, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -4, 2, 3, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 4, this.barsBlock, 0);
        for(i12 = -5; i12 <= -4; ++i12) {
            for(k1 = 4; k1 <= 5; ++k1) {
                this.setBlockAndMetadata(world, i12, 3, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i12, 1, k1, Blocks.lava, 0);
            }
        }
        this.setBlockAndMetadata(world, -5, 3, 3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -4, 3, 3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 3, 3, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, -3, 3, 4, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 3, 5, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 0, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -5, 1, 0, Blocks.cauldron, 3);
        LOTREntityEasterlingBlacksmith blacksmith = new LOTREntityEasterlingBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 16);
        return true;
    }
}
