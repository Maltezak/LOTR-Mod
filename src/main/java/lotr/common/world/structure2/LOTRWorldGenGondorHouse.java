package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorHouse extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int i2;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -4; i12 <= 4; ++i12) {
                for(int k12 = -5; k12 <= 5; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12) - 1;
                    if(!this.isSurface(world, i12, j1, k12)) {
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
        for(int i13 = -4; i13 <= 4; ++i13) {
            for(k1 = -4; k1 <= 4; ++k1) {
                i2 = Math.abs(i13);
                int k2 = Math.abs(k1);
                if(i2 == 4 && k2 == 4) continue;
                if(i2 == 3 && k2 == 4 || k2 == 3 && i2 == 4) {
                    for(j1 = 4; (((j1 >= 0) || !this.isOpaque(world, i13, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i13, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i13, j1 - 1, k1);
                    }
                    continue;
                }
                if(i2 == 4 || k2 == 4) {
                    for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i13, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i13, j1, k1, this.rockBlock, this.rockMeta);
                        this.setGrassToDirt(world, i13, j1 - 1, k1);
                    }
                    for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i13, j1, k1, this.wallBlock, this.wallMeta);
                    }
                    if(k2 != 4) continue;
                    this.setBlockAndMetadata(world, i13, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                    continue;
                }
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i13, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i13, j1, k1, this.rockBlock, this.rockMeta);
                    this.setGrassToDirt(world, i13, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i13, j1, k1);
                }
            }
        }
        int[] i13 = new int[] {-4, 4};
        k1 = i13.length;
        for(i2 = 0; i2 < k1; ++i2) {
            int i14 = i13[i2];
            for(j1 = 1; j1 <= 4; ++j1) {
                this.setBlockAndMetadata(world, i14, j1, 0, this.woodBeamBlock, this.woodBeamMeta);
            }
            this.setBlockAndMetadata(world, i14, 2, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i14, 2, 2, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 2, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 0, -4, this.rockBlock, this.rockMeta);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        for(int k13 = -5; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, -4, 4, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k13, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, -2, 5, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -1, 6, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 6, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 0, 7, k13, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 6, k13, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, 5, k13, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 4, 4, k13, this.roofStairBlock, 0);
            int k2 = Math.abs(k13);
            if(k2 == 5) {
                this.setBlockAndMetadata(world, -3, 4, k13, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, -1, 5, k13, this.roofStairBlock, 4);
                this.setBlockAndMetadata(world, 1, 5, k13, this.roofStairBlock, 5);
                this.setBlockAndMetadata(world, 3, 4, k13, this.roofStairBlock, 5);
                continue;
            }
            if(k2 != 4) continue;
            this.setBlockAndMetadata(world, -1, 5, k13, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 0, 5, k13, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 1, 5, k13, this.wallBlock, this.wallMeta);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = 3; k1 <= 5; ++k1) {
                for(int j12 = 7; (((j12 >= 0) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i1, j12, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j12 - 1, k1);
                }
            }
            this.setBlockAndMetadata(world, i1, 8, 3, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 8, 5, this.brickStairBlock, 3);
        }
        this.setBlockAndMetadata(world, -1, 8, 4, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 8, 4, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 1, 8, 4, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 9, 4, this.brickWallBlock, this.brickWallMeta);
        for(i1 = -3; i1 <= -2; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, -3, this.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i1, 1, -2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 1, -1, this.plankStairBlock, 2);
            if(random.nextBoolean()) {
                this.placePlateWithCertainty(world, random, i1, 2, -2, this.plateBlock, LOTRFoods.GONDOR);
                continue;
            }
            int drinkMeta = random.nextInt(4);
            this.placeMug(world, random, i1, 2, -2, drinkMeta, LOTRFoods.GONDOR_DRINK);
        }
        this.setBlockAndMetadata(world, 2, 1, -3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, -3, this.plankBlock, this.plankMeta);
        this.placePlateWithCertainty(world, random, 3, 2, -3, this.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, 3, 1, -2, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 3, 1, -1, this.tableBlock, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, 1, this.bedBlock, 11);
        this.setBlockAndMetadata(world, 2, 1, 1, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 3, 1, 1, this.bedBlock, 9);
        this.setBlockAndMetadata(world, -3, 1, 3, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 1, 3, this.plankBlock, this.plankMeta);
        this.placeChest(world, random, -2, 1, 3, 2, this.chestContents);
        this.placeChest(world, random, 2, 1, 3, 2, this.chestContents);
        this.setBlockAndMetadata(world, 0, 1, 3, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 0, 2, 3, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 0, 4, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, Blocks.fire, 0);
        for(int j13 = 2; j13 <= 7; ++j13) {
            this.setAir(world, 0, j13, 4);
        }
        this.spawnItemFrame(world, 0, 3, 3, 2, this.getGondorFramedItem(random));
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
            this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
        }
        return true;
    }
}
