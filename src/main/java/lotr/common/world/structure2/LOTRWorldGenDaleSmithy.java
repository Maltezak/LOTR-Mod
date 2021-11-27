package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDaleBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleSmithy extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int j12;
        int i1;
        int j13;
        Block block;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -5; i12 <= 5; ++i12) {
                for(int k13 = -2; k13 <= 12; ++k13) {
                    j13 = this.getTopBlock(world, i12, k13) - 1;
                    block = this.getBlock(world, i12, j13, k13);
                    if(block != Blocks.grass) {
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
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = 0; k1 <= 10; ++k1) {
                j12 = 0;
                while(!this.isOpaque(world, i1, j12, k1) && this.getY(j12) >= 0) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                    --j12;
                }
                for(j12 = 1; j12 <= 10; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -1; k1 <= 11; ++k1) {
                int j14;
                int i2 = Math.abs(i1);
                if(i2 == 4 && IntMath.mod(k1, 4) == 3 || i1 == 0 && k1 == 11) {
                    for(j14 = 3; (((j14 >= 1) || !this.isOpaque(world, i1, j14, k1)) && (this.getY(j14) >= 0)); --j14) {
                        this.setBlockAndMetadata(world, i1, j14, k1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i1, j14 - 1, k1);
                    }
                }
                if(i2 == 4 && IntMath.mod(k1, 4) == 0) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 7);
                }
                if(i2 == 4 && IntMath.mod(k1, 4) == 2) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 6);
                }
                if(k1 == 11 && IntMath.mod(i1, 4) == 1) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 4);
                }
                if(k1 == 11 && IntMath.mod(i1, 4) == 3) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 5);
                }
                if(k1 == -1 && i1 == -3) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 4);
                }
                if(k1 == -1 && i1 == 3) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 5);
                }
                if(i2 == 1 && k1 == -1) {
                    for(j14 = 3; (((j14 >= 1) || !this.isOpaque(world, i1, j14, k1)) && (this.getY(j14) >= 0)); --j14) {
                        this.setBlockAndMetadata(world, i1, j14, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i1, j14 - 1, k1);
                    }
                }
                if(i2 == 0 && k1 == -1) {
                    for(j14 = 0; (((j14 == 0) || !this.isOpaque(world, i1, j14, k1)) && (this.getY(j14) >= 0)); --j14) {
                        this.setBlockAndMetadata(world, i1, j14, k1, this.floorBlock, this.floorMeta);
                        this.setGrassToDirt(world, i1, j14 - 1, k1);
                    }
                    for(j14 = 1; j14 <= 3; ++j14) {
                        this.setAir(world, i1, j14, k1);
                    }
                }
                if(i2 <= 2 && k1 >= 1 && k1 <= 9) {
                    this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);
                }
                if(i2 <= 3 && k1 >= 0 && k1 <= 10) {
                    if(i2 == 3 || k1 == 0 || k1 == 10) {
                        for(j14 = 1; j14 <= 4; ++j14) {
                            this.setBlockAndMetadata(world, i1, j14, k1, this.brickBlock, this.brickMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
                }
                if(i2 != 4 && k1 != -1 && k1 != 11) continue;
                this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
                for(j14 = 5; j14 <= 6; ++j14) {
                    this.setBlockAndMetadata(world, i1, j14, k1, this.plankBlock, this.plankMeta);
                }
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -2, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 4, 12, this.brickStairBlock, 7);
            if(IntMath.mod(i1, 2) != 1) continue;
            this.setBlockAndMetadata(world, i1, 5, -2, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 5, 12, this.brickWallBlock, this.brickWallMeta);
        }
        for(k12 = -1; k12 <= 11; ++k12) {
            this.setBlockAndMetadata(world, -5, 4, k12, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 5, 4, k12, this.brickStairBlock, 4);
            if(IntMath.mod(k12, 2) != 0) continue;
            this.setBlockAndMetadata(world, -5, 5, k12, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 5, 5, k12, this.brickWallBlock, this.brickWallMeta);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 7, -1, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 7, 11, this.woodBeamBlock, this.woodBeamMeta | 4);
        }
        for(k12 = 0; k12 <= 10; ++k12) {
            this.setBlockAndMetadata(world, -4, 7, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 4, 7, k12, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            if(IntMath.mod(i1, 4) == 0) {
                for(j1 = 5; j1 <= 7; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, -1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, j1, 11, this.brickBlock, this.brickMeta);
                }
                this.setBlockAndMetadata(world, i1, 7, -2, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i1, 7, 12, this.brickStairBlock, 7);
                continue;
            }
            if(IntMath.mod(i1, 4) != 2) continue;
            this.setBlockAndMetadata(world, i1, 6, -1, this.barsBlock, 0);
            this.setBlockAndMetadata(world, i1, 6, 11, this.barsBlock, 0);
        }
        for(k12 = -1; k12 <= 11; ++k12) {
            if(IntMath.mod(k12, 4) == 3) {
                for(j1 = 5; j1 <= 7; ++j1) {
                    this.setBlockAndMetadata(world, -4, j1, k12, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, 4, j1, k12, this.brickBlock, this.brickMeta);
                }
                this.setBlockAndMetadata(world, -5, 7, k12, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, 5, 7, k12, this.brickStairBlock, 4);
                continue;
            }
            if(IntMath.mod(k12, 4) != 1) continue;
            this.setBlockAndMetadata(world, -4, 6, k12, this.barsBlock, 0);
            this.setBlockAndMetadata(world, 4, 6, k12, this.barsBlock, 0);
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -2; k1 <= 12; ++k1) {
                if(i1 > -4 && i1 < 4 && k1 > -1 && k1 < 11) continue;
                this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -1; k1 <= 11; ++k1) {
                if(i1 != -4 && i1 != 4 && k1 != -1 && k1 != 11) continue;
                this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = 0; k1 <= 10; ++k1) {
                if(i1 == -3 || i1 == 3 || k1 == 0 || k1 == 10) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.plankBlock, this.plankMeta);
                }
                if(k1 == 3 || k1 == 7) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                }
                else if(i1 == 0) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                }
                this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, i1, 10, -1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 10, 11, this.plankBlock, this.plankMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, -2, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 10, -2, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 9, 12, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 10, 12, this.brickStairBlock, 7);
        for(k12 = -2; k12 <= 12; ++k12) {
            this.setBlockAndMetadata(world, -5, 9, k12, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 10, k12, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 11, k12, this.roofStairBlock, 1);
            for(int i13 = -2; i13 <= 2; ++i13) {
                this.setBlockAndMetadata(world, i13, 11, k12, this.roofBlock, this.roofMeta);
            }
            this.setBlockAndMetadata(world, 3, 11, k12, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 10, k12, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 9, k12, this.roofStairBlock, 0);
            if(k12 != -2 && k12 != 12) continue;
            this.setBlockAndMetadata(world, -4, 9, k12, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 10, k12, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 3, 10, k12, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 9, k12, this.roofStairBlock, 5);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = 7; k1 <= 9; ++k1) {
                for(j12 = 1; j12 <= 12; ++j12) {
                    if(i1 == 0 && k1 == 8) {
                        this.setAir(world, i1, j12, k1);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i1, j12, k1, this.brickBlock, this.brickMeta);
                }
                if(Math.abs(i1) == 1 && (k1 == 7 || k1 == 9)) {
                    this.setBlockAndMetadata(world, i1, 13, k1, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                if(Math.abs(i1) == 1 || k1 == 7 || k1 == 9) {
                    this.setBlockAndMetadata(world, i1, 13, k1, this.brickBlock, this.brickMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 13, k1, this.barsBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, 0, 1, 0, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, 0, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, 0, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
        int[] i14 = new int[] {-2, 2};
        k1 = i14.length;
        for(j12 = 0; j12 < k1; ++j12) {
            int i15 = i14[j12];
            this.setBlockAndMetadata(world, i15, 1, 1, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i15, 2, 1, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i15, 3, 1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i15, 3, 2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 3, 3, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i15, 1, 4, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i15, 2, 4, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i15, 3, 4, this.pillarBlock, this.pillarMeta);
        }
        this.setBlockAndMetadata(world, -1, 3, 1, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 1, 2, LOTRMod.unsmeltery, 4);
        this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.alloyForge, 4);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, 2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.daleTable, 0);
        for(int l = 0; l <= 3; ++l) {
            k1 = 6 + l;
            j12 = 1 + l;
            for(int i16 : new int[] {-2, 2}) {
                this.setAir(world, i16, 4, k1);
                this.setBlockAndMetadata(world, i16, j12, k1, this.brickStairBlock, 2);
                for(int j2 = j12 - 1; j2 >= 1; --j2) {
                    this.setBlockAndMetadata(world, i16, j2, k1, this.brickBlock, this.brickMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 6, Blocks.anvil, 3);
        this.setBlockAndMetadata(world, -1, 1, 7, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 7, Blocks.stone_brick_stairs, 2);
        this.setBlockAndMetadata(world, 1, 1, 7, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -1, 2, 7, LOTRMod.wallStoneV, 1);
        this.setAir(world, 0, 2, 7);
        this.setBlockAndMetadata(world, 1, 2, 7, LOTRMod.wallStoneV, 1);
        this.setBlockAndMetadata(world, 0, 3, 7, this.brickStairBlock, 6);
        this.setBlockAndMetadata(world, -1, 1, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 8, Blocks.lava, 0);
        this.setBlockAndMetadata(world, 1, 1, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -2, 2, 8, Blocks.stonebrick, 0);
        this.setAir(world, -1, 2, 8);
        this.setAir(world, 1, 2, 8);
        this.setBlockAndMetadata(world, 2, 2, 8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 1, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -1, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 1, 2, 9, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, 0, 7, 0, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 7, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, 7, 7, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 7, 7, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 7, 10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, 6, Blocks.crafting_table, 0);
        this.placeChest(world, random, -1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);
        this.placeChest(world, random, 1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);
        for(int i17 = -2; i17 <= 0; ++i17) {
            for(k1 = 1; k1 <= 3; ++k1) {
                if((((i17 == -2) || (i17 == 0)) && ((k1 == 1) || (k1 == 3)))) {
                    this.setBlockAndMetadata(world, i17, 5, k1, this.plankBlock, this.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i17, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(random.nextBoolean()) {
                    this.placePlate(world, random, i17, 6, k1, this.plateBlock, LOTRFoods.DALE);
                    continue;
                }
                int mugMeta = random.nextInt(4);
                this.placeMug(world, random, i17, 6, k1, mugMeta, LOTRFoods.DALE_DRINK);
            }
        }
        for(int i18 : new int[] {2, 3}) {
            this.setBlockAndMetadata(world, i18, 5, 1, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i18, 5, 0, LOTRMod.strawBed, 10);
        }
        LOTREntityDaleBlacksmith blacksmith = new LOTREntityDaleBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 5, 16);
        return true;
    }
}
