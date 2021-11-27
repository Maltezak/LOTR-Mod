package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerHouse extends LOTRWorldGenRangerStructure {
    public LOTRWorldGenRangerHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int gateMeta;
        int gateZ;
        int k15;
        int gateX;
        int k12;
        int i122;
        block54: {
            int k13;
            int i13;
            int k2;
            int j1;
            this.setOriginAndRotation(world, i, j, k, rotation, 4);
            this.setupRandomBlocks(random);
            if(this.restrictions) {
                int minHeight = 0;
                int maxHeight = 0;
                for(int i14 = -6; i14 <= 7; ++i14) {
                    for(k15 = -4; k15 <= 4; ++k15) {
                        j1 = this.getTopBlock(world, i14, k15) - 1;
                        if(!this.isSurface(world, i14, j1, k15)) {
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
            for(int i15 = -2; i15 <= 7; ++i15) {
                for(k13 = -3; k13 <= 3; ++k13) {
                    int j12;
                    int j13;
                    k2 = Math.abs(k13);
                    for(j12 = 1; j12 <= 8; ++j12) {
                        this.setAir(world, i15, j12, k13);
                    }
                    if(i15 >= 5 && k2 <= 1) {
                        for(j12 = 5; (((j12 >= 0) || !this.isOpaque(world, i15, j12, k13)) && (this.getY(j12) >= 0)); --j12) {
                            this.setBlockAndMetadata(world, i15, j12, k13, this.brickBlock, this.brickMeta);
                            this.setGrassToDirt(world, i15, j12 - 1, k13);
                        }
                        continue;
                    }
                    if(i15 == 6 && k2 == 2) {
                        for(j12 = 4; (((j12 >= 0) || !this.isOpaque(world, i15, j12, k13)) && (this.getY(j12) >= 0)); --j12) {
                            this.setBlockAndMetadata(world, i15, j12, k13, this.woodBeamBlock, this.woodBeamMeta);
                            this.setGrassToDirt(world, i15, j12 - 1, k13);
                        }
                        continue;
                    }
                    if(i15 > 5) continue;
                    boolean beam = false;
                    boolean wall = false;
                    if(i15 == -2 && IntMath.mod(k13, 3) == 0) {
                        beam = true;
                    }
                    if(k2 == 3 && (i15 == 2 || i15 == 5)) {
                        beam = true;
                    }
                    if(i15 == -2 || k2 == 3) {
                        wall = true;
                    }
                    if(beam) {
                        for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i15, j13, k13)) && (this.getY(j13) >= 0)); --j13) {
                            this.setBlockAndMetadata(world, i15, j13, k13, this.woodBeamBlock, this.woodBeamMeta);
                            this.setGrassToDirt(world, i15, j13 - 1, k13);
                        }
                        continue;
                    }
                    if(wall) {
                        for(j13 = 1; j13 <= 4; ++j13) {
                            this.setBlockAndMetadata(world, i15, j13, k13, this.wallBlock, this.wallMeta);
                        }
                        for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i15, j13, k13)) && (this.getY(j13) >= 0)); --j13) {
                            this.setBlockAndMetadata(world, i15, j13, k13, this.plankBlock, this.plankMeta);
                            this.setGrassToDirt(world, i15, j13 - 1, k13);
                        }
                        continue;
                    }
                    for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i15, j13, k13)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i15, j13, k13, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, i15, j13 - 1, k13);
                    }
                    if(random.nextInt(3) != 0) continue;
                    this.setBlockAndMetadata(world, i15, 1, k13, LOTRMod.thatchFloor, 0);
                }
            }
            this.setBlockAndMetadata(world, 0, 0, -3, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 1, -3, this.doorBlock, 1);
            this.setBlockAndMetadata(world, 0, 2, -3, this.doorBlock, 8);
            this.setBlockAndMetadata(world, -2, 2, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -2, 2, 1, this.fenceBlock, this.fenceMeta);
            int[] i15 = new int[] {-3, 3};
            k13 = i15.length;
            for(k2 = 0; k2 < k13; ++k2) {
                k15 = i15[k2];
                if(k15 >= 0) {
                    this.setBlockAndMetadata(world, 0, 2, k15, this.fenceBlock, this.fenceMeta);
                }
                this.setBlockAndMetadata(world, 3, 2, k15, this.fenceBlock, this.fenceMeta);
            }
            for(i13 = -2; i13 <= 5; ++i13) {
                this.setBlockAndMetadata(world, i13, 4, -3, this.woodBeamBlock, this.woodBeamMeta | 4);
                this.setBlockAndMetadata(world, i13, 4, 3, this.woodBeamBlock, this.woodBeamMeta | 4);
                if(i13 > 4) continue;
                this.setBlockAndMetadata(world, i13, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
            for(i13 = -2; i13 <= 5; ++i13) {
                this.setBlockAndMetadata(world, i13, 4, -4, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i13, 4, 4, this.roofStairBlock, 3);
                this.setBlockAndMetadata(world, i13, 5, -3, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i13, 5, 3, this.roofStairBlock, 3);
            }
            for(int k14 = -3; k14 <= 3; ++k14) {
                int k22 = Math.abs(k14);
                this.setBlockAndMetadata(world, -3, 4, k14, this.roofStairBlock, 1);
                if(k22 <= 2) {
                    this.setBlockAndMetadata(world, -2, 5, k14, this.roofStairBlock, 1);
                }
                if(k22 >= 2) {
                    this.setBlockAndMetadata(world, 6, 4, k14, this.roofStairBlock, 0);
                }
                if(k22 != 2) continue;
                this.setBlockAndMetadata(world, 5, 5, k14, this.roofStairBlock, 0);
            }
            for(i13 = -1; i13 <= 4; ++i13) {
                for(k13 = -2; k13 <= 2; ++k13) {
                    k2 = Math.abs(k13);
                    if(k2 <= 1 && i13 >= 0 && i13 <= 3) {
                        this.setBlockAndMetadata(world, i13, 6, k13, this.roofBlock, this.roofMeta);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i13, 6, k13, this.roofSlabBlock, this.roofSlabMeta);
                }
            }
            for(int k151 : new int[] {-2, 2}) {
                for(i1 = -1; i1 <= 4; ++i1) {
                    this.setBlockAndMetadata(world, i1, 5, k151, this.roofBlock, this.roofMeta);
                }
            }
            for(int i1221 : new int[] {-1, 4}) {
                for(k12 = -1; k12 <= 1; ++k12) {
                    this.setBlockAndMetadata(world, i1221, 5, k12, this.fenceBlock, this.fenceMeta);
                }
            }
            this.setBlockAndMetadata(world, 6, 5, -1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, 7, 5, -1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, 7, 5, 0, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 7, 5, 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, 6, 5, 1, this.brickStairBlock, 3);
            for(int j14 = 6; j14 <= 7; ++j14) {
                this.setBlockAndMetadata(world, 6, j14, 0, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, 6, 8, 0, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
            this.setBlockAndMetadata(world, 2, 2, 4, Blocks.torch, 3);
            this.setBlockAndMetadata(world, 0, 3, -2, Blocks.torch, 3);
            this.setBlockAndMetadata(world, -1, 1, -1, this.tableBlock, 0);
            this.setBlockAndMetadata(world, -1, 1, 0, this.plankBlock, this.plankMeta);
            this.placePlateWithCertainty(world, random, -1, 2, 0, this.plateBlock, LOTRFoods.RANGER);
            this.setBlockAndMetadata(world, -1, 1, 1, Blocks.crafting_table, 0);
            this.setBlockAndMetadata(world, -1, 1, 2, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, -1, 2, 2, random.nextInt(3), LOTRFoods.RANGER_DRINK);
            this.placeChest(world, random, 0, 1, 2, 2, this.chestContentsHouse);
            this.setBlockAndMetadata(world, 1, 1, 2, this.plankBlock, this.plankMeta);
            this.placeBarrel(world, random, 1, 2, 2, 2, LOTRFoods.RANGER_DRINK);
            for(int k151 : new int[] {-2, 2}) {
                this.setBlockAndMetadata(world, 3, 1, k151, this.bedBlock, 1);
                this.setBlockAndMetadata(world, 4, 1, k151, this.bedBlock, 9);
                this.setBlockAndMetadata(world, 5, 1, k151, this.plankBlock, this.plankMeta);
                for(j1 = 2; j1 <= 4; ++j1) {
                    this.setBlockAndMetadata(world, 5, j1, k151, this.fenceBlock, this.fenceMeta);
                }
            }
            this.setBlockAndMetadata(world, 6, 0, 0, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, 6, 1, 0, Blocks.fire, 0);
            for(int j15 = 2; j15 <= 3; ++j15) {
                this.setAir(world, 6, j15, 0);
            }
            this.setBlockAndMetadata(world, 5, 1, 0, this.barsBlock, 0);
            this.setBlockAndMetadata(world, 5, 2, 0, Blocks.furnace, 5);
            this.spawnItemFrame(world, 5, 3, 0, 3, this.getRangerFramedItem(random));
            gateX = 0;
            gateZ = 0;
            gateMeta = -1;
            for(i122 = -5; i122 <= -5; ++i122) {
                k12 = -4;
                if(this.isValidGatePos(world, i122, 1, k12)) {
                    gateX = i122;
                    gateZ = k12 + 1;
                    gateMeta = 0;
                }
                else {
                    k12 = 4;
                    if(!this.isValidGatePos(world, i122, 1, k12)) continue;
                    gateX = i122;
                    gateZ = k12 - 1;
                    gateMeta = 2;
                }
                break block54;
            }
            for(k15 = -2; k15 <= 2; ++k15) {
                i1 = -7;
                if(!this.isValidGatePos(world, i1, 1, k15)) continue;
                gateX = i1 + 1;
                gateZ = k15;
                gateMeta = 3;
                break;
            }
        }
        if(gateMeta != -1) {
            for(i122 = -6; i122 <= -3; ++i122) {
                for(k12 = -3; k12 <= 3; ++k12) {
                    int j1;
                    int k2 = Math.abs(k12);
                    for(j1 = 1; j1 <= 3; ++j1) {
                        this.setAir(world, i122, j1, k12);
                    }
                    for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i122, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                        if(j1 == 0) {
                            this.setBlockAndMetadata(world, i122, j1, k12, Blocks.grass, 0);
                        }
                        else {
                            this.setBlockAndMetadata(world, i122, j1, k12, Blocks.dirt, 0);
                        }
                        this.setGrassToDirt(world, i122, j1 - 1, k12);
                    }
                    if(i122 != -6 && k2 != 3) continue;
                    this.setBlockAndMetadata(world, i122, 1, k12, this.fenceBlock, this.fenceMeta);
                }
            }
            this.setBlockAndMetadata(world, gateX, 1, gateZ, this.fenceGateBlock, gateMeta);
            this.setBlockAndMetadata(world, gateX, 0, gateZ, LOTRMod.dirtPath, 0);
            for(k15 = -2; k15 <= 2; ++k15) {
                this.setBlockAndMetadata(world, -5, 0, k15, LOTRMod.dirtPath, 0);
                for(i1 = -4; i1 <= -3; ++i1) {
                    if(k15 == 0 && i1 == -3) continue;
                    this.setBlockAndMetadata(world, i1, 0, k15, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i1, 1, k15, this.cropBlock, this.cropMeta);
                }
            }
            this.setBlockAndMetadata(world, -3, -1, 0, Blocks.dirt, 0);
            this.setGrassToDirt(world, -3, -2, 0);
            this.setBlockAndMetadata(world, -3, 0, 0, Blocks.water, 0);
            this.setBlockAndMetadata(world, -3, 1, 0, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -3, 2, 0, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -6, 2, 0, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -6, 3, 0, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -6, 4, 0, Blocks.pumpkin, 3);
        }
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
            this.spawnNPCAndSetHome(dunedain, world, 2, 1, 0, 16);
        }
        return true;
    }

    private boolean isValidGatePos(World world, int i, int j, int k) {
        return this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k) && this.isAir(world, i, j + 1, k);
    }
}
