package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDunedainBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerSmithy extends LOTRWorldGenRangerStructure {
    public LOTRWorldGenRangerSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int l;
        int i12;
        int j12;
        int k1;
        int j13;
        int k12;
        int k13;
        int k14;
        int i13;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i1 = -7; i1 <= 9; ++i1) {
                for(int k15 = -5; k15 <= 5; ++k15) {
                    j13 = this.getTopBlock(world, i1, k15) - 1;
                    if(!this.isSurface(world, i1, j13, k15)) {
                        return false;
                    }
                    if(j13 < minHeight) {
                        minHeight = j13;
                    }
                    if(j13 > maxHeight) {
                        maxHeight = j13;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(i12 = -7; i12 <= 8; ++i12) {
            for(k1 = -4; k1 <= 4; ++k1) {
                int j14;
                int k2 = Math.abs(k1);
                for(j14 = 1; j14 <= 8; ++j14) {
                    this.setAir(world, i12, j14, k1);
                }
                if(i12 <= 1 || i12 == 2 && k2 == 4) {
                    for(j14 = 0; (((j14 >= 0) || !this.isOpaque(world, i12, j14, k1)) && (this.getY(j14) >= 0)); --j14) {
                        this.setBlockAndMetadata(world, i12, j14, k1, this.cobbleBlock, this.cobbleMeta);
                        this.setGrassToDirt(world, i12, j14 - 1, k1);
                    }
                    continue;
                }
                if((i12 != 2 && i12 != 8 || k2 > 3) && (i12 < 3 || i12 > 7 || k2 > 4)) continue;
                if((i12 == 2 || i12 == 8) && k2 <= 3 || i12 >= 3 && i12 <= 7 && k2 == 4) {
                    boolean beam = false;
                    if((i12 == 2 || i12 == 8) && k2 == 3) {
                        beam = true;
                    }
                    if((i12 == 3 || i12 == 7) && k2 == 4) {
                        beam = true;
                    }
                    if(beam) {
                        for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k1)) && (this.getY(j13) >= 0)); --j13) {
                            this.setBlockAndMetadata(world, i12, j13, k1, this.woodBeamBlock, this.woodBeamMeta);
                            this.setGrassToDirt(world, i12, j13 - 1, k1);
                        }
                        continue;
                    }
                    for(j13 = 1; j13 <= 3; ++j13) {
                        this.setBlockAndMetadata(world, i12, j13, k1, this.wallBlock, this.wallMeta);
                    }
                    for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k1)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i12, j13, k1, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, i12, j13 - 1, k1);
                    }
                    continue;
                }
                for(j14 = 0; (((j14 >= 0) || !this.isOpaque(world, i12, j14, k1)) && (this.getY(j14) >= 0)); --j14) {
                    this.setBlockAndMetadata(world, i12, j14, k1, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i12, j14 - 1, k1);
                }
                if(random.nextInt(3) != 0) continue;
                this.setBlockAndMetadata(world, i12, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        for(i12 = 4; i12 <= 6; ++i12) {
            this.setBlockAndMetadata(world, i12, 4, -4, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i12, 4, 4, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(k13 = -2; k13 <= 2; ++k13) {
            this.setBlockAndMetadata(world, 2, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 8, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        this.setBlockAndMetadata(world, 5, 2, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 5, 2, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, -2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, 2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, this.doorBlock, 2);
        this.setBlockAndMetadata(world, 2, 2, 0, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 3, 3, 0, Blocks.torch, 2);
        for(l = 0; l <= 2; ++l) {
            j1 = 4 + l;
            for(i1 = 2 + l; i1 <= 8 - l; ++i1) {
                this.setBlockAndMetadata(world, i1, j1, -5 + l, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i1, j1, 5 - l, this.roofStairBlock, 3);
            }
            for(int i14 : new int[] {1 + l, 9 - l}) {
                this.setBlockAndMetadata(world, i14, j1, -4 + l, this.roofStairBlock, 2);
                this.setBlockAndMetadata(world, i14, j1, 4 - l, this.roofStairBlock, 3);
            }
            for(int k16 = -3 + l; k16 <= 3 - l; ++k16) {
                this.setBlockAndMetadata(world, 1 + l, j1, k16, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 9 - l, j1, k16, this.roofStairBlock, 0);
            }
            for(int k17 : new int[] {-4 + l, 4 - l}) {
                this.setBlockAndMetadata(world, 2 + l, j1, k17, this.roofStairBlock, 1);
                this.setBlockAndMetadata(world, 8 - l, j1, k17, this.roofStairBlock, 0);
            }
        }
        for(k13 = -1; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, 5, 7, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, 7, k13, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 6, 7, k13, this.roofSlabBlock, this.roofSlabMeta);
        }
        this.setBlockAndMetadata(world, 5, 7, -2, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 5, 7, 2, this.roofSlabBlock, this.roofSlabMeta);
        for(l = 0; l <= 1; ++l) {
            j1 = 5 + l;
            for(i1 = 4 + l; i1 <= 6 - l; ++i1) {
                this.setBlockAndMetadata(world, i1, j1, -3 + l, this.roofSlabBlock, this.roofSlabMeta | 8);
                this.setBlockAndMetadata(world, i1, j1, 3 - l, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
            for(k12 = -2 + l; k12 <= 2 - l; ++k12) {
                this.setBlockAndMetadata(world, 3 + l, j1, k12, this.roofSlabBlock, this.roofSlabMeta | 8);
                this.setBlockAndMetadata(world, 7 - l, j1, k12, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
        }
        for(i12 = 7; i12 <= 9; ++i12) {
            for(k1 = -1; k1 <= 1; ++k1) {
                for(int j15 = 5; (((j15 >= 0) || !this.isOpaque(world, i12, j15, k1)) && (this.getY(j15) >= 0)); --j15) {
                    this.setBlockAndMetadata(world, i12, j15, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j15 - 1, k1);
                }
            }
        }
        for(k13 = -1; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, 9, 5, k13, this.brickStairBlock, 0);
        }
        this.setBlockAndMetadata(world, 8, 5, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 8, 5, 1, this.brickStairBlock, 3);
        for(int j16 = 6; j16 <= 7; ++j16) {
            this.setBlockAndMetadata(world, 8, j16, 0, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 8, 8, 0, this.brickWallBlock, this.brickWallMeta);
        for(k13 = -3; k13 <= 3; ++k13) {
            this.setBlockAndMetadata(world, 5, 4, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(int i15 : new int[] {3, 7}) {
            for(int k18 : new int[] {-3, 3}) {
                this.setBlockAndMetadata(world, i15, 1, k18, this.plankBlock, this.plankMeta);
                for(int j17 = 2; j17 <= 4; ++j17) {
                    this.setBlockAndMetadata(world, i15, j17, k18, this.fenceBlock, this.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 3, 1, -2, this.plankBlock, this.plankMeta);
        this.placePlate(world, random, 3, 2, -2, this.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, 4, 1, -3, this.plankBlock, this.plankMeta);
        this.placePlate(world, random, 4, 2, -3, this.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, 5, 1, -3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 6, 1, -3, this.plankBlock, this.plankMeta);
        this.placeMug(world, random, 6, 2, -3, 2, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 7, 1, -2, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, 7, 2, -2, 5, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 3, 1, 2, this.plankBlock, this.plankMeta);
        this.placeMug(world, random, 3, 2, 2, 3, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, 5, 1, 3, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 6, 1, 3, this.bedBlock, 9);
        this.placeChest(world, random, 7, 1, 2, 5, this.chestContentsHouse);
        this.setBlockAndMetadata(world, 8, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 8, 1, 0, Blocks.fire, 0);
        for(j12 = 2; j12 <= 3; ++j12) {
            this.setAir(world, 8, j12, 0);
        }
        this.setBlockAndMetadata(world, 7, 1, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 7, 2, 0, Blocks.furnace, 5);
        this.spawnItemFrame(world, 7, 3, 0, 3, this.getRangerFramedItem(random));
        this.placeChest(world, random, 1, 1, 2, 5, LOTRChestContents.RANGER_SMITHY);
        this.setBlockAndMetadata(world, 1, 1, -2, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 1, 1, -3, Blocks.crafting_table, 0);
        for(j12 = 1; j12 <= 3; ++j12) {
            for(i13 = -6; i13 <= -3; ++i13) {
                for(k12 = 0; k12 <= 3; ++k12) {
                    this.setBlockAndMetadata(world, i13, j12, k12, this.brickBlock, this.brickMeta);
                }
            }
            this.setBlockAndMetadata(world, -2, j12, 3, this.brickBlock, this.brickMeta);
        }
        for(j12 = 1; j12 <= 3; ++j12) {
            this.setBlockAndMetadata(world, -6, j12, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -2, j12, -3, this.fenceBlock, this.fenceMeta);
        }
        for(int l2 = 0; l2 <= 1; ++l2) {
            j1 = 4 + l2;
            for(i1 = -6 + l2; i1 <= -2 - l2; ++i1) {
                this.setBlockAndMetadata(world, i1, j1, -3 + l2, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i1, j1, 3 - l2, this.brickStairBlock, 3);
            }
            for(k12 = -2 + l2; k12 <= 2 - l2; ++k12) {
                this.setBlockAndMetadata(world, -6 + l2, j1, k12, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, -2 - l2, j1, k12, this.brickStairBlock, 0);
            }
        }
        for(k14 = -2; k14 <= 2; ++k14) {
            for(i13 = -5; i13 <= -3; ++i13) {
                this.setBlockAndMetadata(world, i13, 4, k14, this.brickBlock, this.brickMeta);
            }
        }
        for(k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, -4, 5, k14, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -3, 1, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 1, LOTRMod.alloyForge, 4);
        this.setBlockAndMetadata(world, -4, 2, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -3, 2, 1, this.barsBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 1, Blocks.lava, 0);
        for(j12 = 2; j12 <= 5; ++j12) {
            this.setAir(world, -4, j12, 1);
        }
        this.setBlockAndMetadata(world, -2, 1, 2, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -5, 1, -1, LOTRMod.unsmeltery, 4);
        this.setBlockAndMetadata(world, -5, 1, -3, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -3, 1, -3, Blocks.anvil, 1);
        LOTREntityDunedainBlacksmith blacksmith = new LOTREntityDunedainBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 8);
        return true;
    }
}
