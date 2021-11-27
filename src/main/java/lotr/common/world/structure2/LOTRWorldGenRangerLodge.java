package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenRangerLodge extends LOTRWorldGenRangerStructure {
    public LOTRWorldGenRangerLodge(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k13;
        int k12;
        int j1;
        int j12;
        int i2;
        int j13;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -5; i1 <= 6; ++i1) {
                for(k13 = -4; k13 <= 4; ++k13) {
                    j1 = this.getTopBlock(world, i1, k13) - 1;
                    if(!this.isSurface(world, i1, j1, k13)) {
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
        for(int i1 = -5; i1 <= 5; ++i1) {
            for(k12 = -4; k12 <= 4; ++k12) {
                i2 = Math.abs(i1);
                int k2 = Math.abs(k12);
                if(i2 > 4 && k2 > 3) continue;
                for(j1 = 0; (((j1 >= -3) || !this.isOpaque(world, i1, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k12);
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k12);
                }
                if(k2 == 4 || i2 == 5) {
                    int j14;
                    boolean beam = false;
                    if(k12 == -4 && (i2 == 1 || i2 == 4)) {
                        beam = true;
                    }
                    if(k12 == 4 && (i2 == 0 || i2 == 4)) {
                        beam = true;
                    }
                    if(i2 == 5 && (k2 == 0 || k2 == 3)) {
                        beam = true;
                    }
                    if(beam) {
                        for(j14 = 1; j14 <= 3; ++j14) {
                            this.setBlockAndMetadata(world, i1, j14, k12, this.woodBeamBlock, this.woodBeamMeta);
                        }
                    }
                    else {
                        for(j14 = 1; j14 <= 3; ++j14) {
                            this.setBlockAndMetadata(world, i1, j14, k12, this.wallBlock, this.wallMeta);
                        }
                    }
                }
                if(k2 > 3 || i2 > 4) continue;
                this.setBlockAndMetadata(world, i1, 0, k12, this.plankSlabBlock, this.plankSlabMeta | 8);
                if(random.nextInt(3) == 0) {
                    this.setBlockAndMetadata(world, i1, 1, k12, LOTRMod.thatchFloor, 0);
                }
                for(j1 = -2; j1 <= -1; ++j1) {
                    this.setAir(world, i1, j1, k12);
                }
            }
        }
        for(int k131 : new int[] {-4, 4}) {
            for(int i1 = -4; i1 <= 4; ++i1) {
                this.setBlockAndMetadata(world, i1, 4, k131, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        for(int i1 : new int[] {-5, 5}) {
            for(int k14 = -3; k14 <= 3; ++k14) {
                int k2 = Math.abs(k14);
                if(k2 == 0) {
                    for(int j15 = 4; j15 <= 6; ++j15) {
                        this.setBlockAndMetadata(world, i1, j15, k14, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 4, k14, this.woodBeamBlock, this.woodBeamMeta | 8);
                if(k2 > 2) continue;
                this.setBlockAndMetadata(world, i1, 5, k14, this.wallBlock, this.wallMeta);
            }
        }
        for(int i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 6, -1, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 7, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 6, 1, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 5, 3, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 5, -4, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 6, -3, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 6, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 7, -1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 7, 1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 6, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 6, 3, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 5, 4, this.roofStairBlock, 3);
        }
        for(int k15 = -4; k15 <= 4; ++k15) {
            this.setBlockAndMetadata(world, 0, 4, k15, this.woodBeamBlock, this.woodBeamMeta | 8);
        }
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 4, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 2, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 3, 2, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 2, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, 4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -5, 2, -1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -5, 2, 1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -4, 4, 0, Blocks.torch, 2);
        for(int i1 : new int[] {-4, 4}) {
            for(int k16 : new int[] {-3, 3}) {
                this.setBlockAndMetadata(world, i1, 1, k16, this.plankBlock, this.plankMeta);
                for(j13 = 2; j13 <= 4; ++j13) {
                    this.setBlockAndMetadata(world, i1, j13, k16, this.fenceBlock, this.fenceMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 1, -3, this.plankBlock, this.plankMeta);
        this.placePlate(world, random, -2, 2, -3, this.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, -3, 1, -3, this.plankBlock, this.plankMeta);
        this.placePlate(world, random, -3, 2, -3, this.plateBlock, LOTRFoods.RANGER);
        this.setBlockAndMetadata(world, -4, 1, -2, this.plankBlock, this.plankMeta);
        this.placeMug(world, random, -4, 2, -2, 3, LOTRFoods.RANGER_DRINK);
        this.placeChest(world, random, -4, 1, -1, 4, this.chestContentsHouse);
        this.setBlockAndMetadata(world, -4, 1, 0, Blocks.crafting_table, 0);
        this.placeChest(world, random, -4, 1, 1, 4, this.chestContentsHouse);
        this.setBlockAndMetadata(world, -4, 1, 2, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, -4, 2, 2, 4, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, -3, 1, 3, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, -3, 2, 3, 2, LOTRFoods.RANGER_DRINK);
        this.setBlockAndMetadata(world, -2, 1, 3, this.plankBlock, this.plankMeta);
        this.placeMug(world, random, -2, 2, 3, 0, LOTRFoods.RANGER_DRINK);
        int[] k15 = new int[] {-3, 3};
        k12 = k15.length;
        for(i2 = 0; i2 < k12; ++i2) {
            k13 = k15[i2];
            this.setBlockAndMetadata(world, 2, 1, k13, this.bedBlock, 1);
            this.setBlockAndMetadata(world, 3, 1, k13, this.bedBlock, 9);
        }
        this.setBlockAndMetadata(world, 4, 1, -2, this.plankBlock, this.plankMeta);
        for(int i1 = 4; i1 <= 6; ++i1) {
            for(k12 = -1; k12 <= 1; ++k12) {
                for(int j16 = 5; (((j16 >= 0) || !this.isOpaque(world, i1, j16, k12)) && (this.getY(j16) >= 0)); --j16) {
                    this.setBlockAndMetadata(world, i1, j16, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j16 - 1, k12);
                }
            }
        }
        this.setBlockAndMetadata(world, 4, 6, 0, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 6, 5, -1, this.brickStairBlock, 2);
        this.setBlockAndMetadata(world, 6, 5, 1, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 6, 6, 0, this.brickStairBlock, 0);
        for(j12 = 6; j12 <= 8; ++j12) {
            this.setBlockAndMetadata(world, 5, j12, 0, this.brickBlock, this.brickMeta);
        }
        for(j12 = 9; j12 <= 10; ++j12) {
            this.setBlockAndMetadata(world, 5, j12, 0, this.brickWallBlock, this.brickWallMeta);
        }
        this.setBlockAndMetadata(world, 5, 0, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 5, 1, 0, Blocks.fire, 0);
        for(j12 = 2; j12 <= 3; ++j12) {
            this.setAir(world, 5, j12, 0);
        }
        this.setBlockAndMetadata(world, 4, 1, 0, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 4, 2, 0, Blocks.furnace, 5);
        this.spawnItemFrame(world, 4, 4, 0, 3, this.getRangerFramedItem(random));
        this.setBlockAndMetadata(world, 4, 1, 2, Blocks.trapdoor, 3);
        for(j12 = -2; j12 <= 0; ++j12) {
            this.setBlockAndMetadata(world, 4, j12, 2, Blocks.ladder, 3);
        }
        for(int i1 : new int[] {-4, 4}) {
            for(int k16 : new int[] {-3, 3}) {
                this.setBlockAndMetadata(world, i1, 0, k16, this.plankBlock, this.plankMeta);
                for(j13 = -2; j13 <= -1; ++j13) {
                    this.setBlockAndMetadata(world, i1, j13, k16, this.woodBeamBlock, this.woodBeamMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, -3, -1, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, -1, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, -1, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, -1, 3, Blocks.torch, 1);
        for(int i1 : new int[] {-2, 0, 2}) {
            this.setBlockAndMetadata(world, i1, -2, -2, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i1, -2, -3, this.bedBlock, 10);
        }
        for(int k131 : new int[] {-2, 2}) {
            ItemStack[] armor = null;
            if(random.nextInt(3) == 0) {
                armor = new ItemStack[] {new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger)};
            }
            this.placeArmorStand(world, -4, -2, k131, 3, armor);
        }
        for(int k131 : new int[] {-1, 1}) {
            this.spawnItemFrame(world, -5, -1, k131, 1, this.getRangerFramedItem(random));
        }
        this.setBlockAndMetadata(world, 0, -2, 3, this.tableBlock, 0);
        for(int i1 : new int[] {-1, 1}) {
            int amount = 2 + random.nextInt(5);
            this.placeChest(world, random, i1, -2, 3, 2, this.chestContentsRanger, amount);
        }
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
            this.spawnNPCAndSetHome(dunedain, world, 0, 1, 0, 16);
        }
        return true;
    }
}
