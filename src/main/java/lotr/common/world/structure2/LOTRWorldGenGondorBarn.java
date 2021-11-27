package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBarn extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorBarn(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int k1;
        int i12;
        int k122;
        int j1;
        int k13;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -12; i13 <= 5; ++i13) {
                for(k122 = -2; k122 <= 15; ++k122) {
                    int j12 = this.getTopBlock(world, i13, k122) - 1;
                    if(!this.isSurface(world, i13, j12, k122)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 5) continue;
                    return false;
                }
            }
        }
        for(int i14 = -12; i14 <= 4; ++i14) {
            for(k1 = -2; k1 <= 15; ++k1) {
                this.setBlockAndMetadata(world, i14, 0, k1, Blocks.grass, 0);
                j1 = -1;
                while(!this.isOpaque(world, i14, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i14, j1, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i14, j1 - 1, k1);
                    --j1;
                }
                for(j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i14, j1, k1);
                }
            }
        }
        for(int i15 : new int[] {-4, 4}) {
            for(int k14 = 0; k14 <= 13; ++k14) {
                int j13;
                this.setBlockAndMetadata(world, i15, 1, k14, this.rockBlock, this.rockMeta);
                if(k14 == 0 || k14 == 4 || k14 == 9 || k14 == 13) {
                    for(j13 = 2; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i15, j13, k14, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    continue;
                }
                for(j13 = 2; j13 <= 5; ++j13) {
                    this.setBlockAndMetadata(world, i15, j13, k14, this.plankBlock, this.plankMeta);
                }
            }
            this.setBlockAndMetadata(world, i15, 4, 1, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 4, 2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 4, 3, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i15, 4, 10, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i15, 4, 11, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15, 4, 12, this.plankStairBlock, 6);
        }
        for(int k1221 : new int[] {0, 13}) {
            for(i1 = -3; i1 <= 3; ++i1) {
                int j14;
                int i2 = Math.abs(i1);
                if(i2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 1, k1221, this.fenceGateBlock, 0);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 1, k1221, this.rockBlock, this.rockMeta);
                }
                if(i2 == 2) {
                    for(j14 = 2; j14 <= 7; ++j14) {
                        this.setBlockAndMetadata(world, i1, j14, k1221, this.woodBeamBlock, this.woodBeamMeta);
                    }
                }
                if(i2 != 3) continue;
                for(j14 = 2; j14 <= 5; ++j14) {
                    this.setBlockAndMetadata(world, i1, j14, k1221, this.plankBlock, this.plankMeta);
                }
                for(j14 = 6; j14 <= 8; ++j14) {
                    this.setBlockAndMetadata(world, i1, j14, k1221, this.wallBlock, this.wallMeta);
                }
            }
            this.setBlockAndMetadata(world, -1, 4, k1221, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 4, k1221, this.plankStairBlock, 5);
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 5, k1221, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i1, 6, k1221, this.wallBlock, this.wallMeta);
                this.setBlockAndMetadata(world, i1, 8, k1221, this.wallBlock, this.wallMeta);
                this.setBlockAndMetadata(world, i1, 9, k1221, this.wallBlock, this.wallMeta);
            }
            this.setBlockAndMetadata(world, -1, 7, k1221, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 0, 7, k1221, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 1, 7, k1221, this.wallBlock, this.wallMeta);
        }
        int[] i14 = new int[] {-1, 14};
        k1 = i14.length;
        for(j1 = 0; j1 < k1; ++j1) {
            k122 = i14[j1];
            for(i1 = -3; i1 <= 3; ++i1) {
                this.setBlockAndMetadata(world, i1, 6, k122, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -1, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 0, 5, 14, this.plankStairBlock, 7);
        for(k13 = -1; k13 <= 14; ++k13) {
            this.setBlockAndMetadata(world, -2, 8, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 2, 8, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 0, 10, k13, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, -5, 5, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 6, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -4, 7, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -3, 8, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 9, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 10, k13, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 11, k13, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 10, k13, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 9, k13, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 8, k13, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 7, k13, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 6, k13, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 5, 5, k13, this.roofStairBlock, 0);
            if(k13 != -1 && k13 != 14) continue;
            this.setBlockAndMetadata(world, -4, 5, k13, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 7, k13, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 9, k13, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 9, k13, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 7, k13, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 5, k13, this.roofStairBlock, 5);
        }
        for(k13 = 1; k13 <= 3; ++k13) {
            this.setBlockAndMetadata(world, -3, 1, k13, Blocks.hay_block, 0);
        }
        for(k13 = 1; k13 <= 2; ++k13) {
            this.setBlockAndMetadata(world, -3, 2, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -2, 1, k13, Blocks.hay_block, 0);
        }
        for(k13 = 10; k13 <= 12; ++k13) {
            this.setBlockAndMetadata(world, -3, 1, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, k13, Blocks.hay_block, 0);
        }
        for(k13 = 11; k13 <= 12; ++k13) {
            this.setBlockAndMetadata(world, -3, 2, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -2, 1, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 2, 1, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 2, k13, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -3, 1, 4, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -3, 1, 9, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 1, 9, Blocks.crafting_table, 0);
        for(int j15 = 2; j15 <= 4; ++j15) {
            this.setBlockAndMetadata(world, -3, j15, 4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -3, j15, 9, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 3, j15, 9, this.fenceBlock, this.fenceMeta);
        }
        for(k13 = 1; k13 <= 12; ++k13) {
            for(int i16 = -3; i16 <= 3; ++i16) {
                this.setBlockAndMetadata(world, i16, 5, k13, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, -3, 7, k13, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, -1, 9, k13, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, 1, 9, k13, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, 3, 7, k13, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        this.setBlockAndMetadata(world, 0, 4, 4, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 0, 4, 9, LOTRMod.chandelier, 1);
        for(int step = 0; step <= 3; ++step) {
            this.setBlockAndMetadata(world, 3, 1 + step, 2 + step, this.plankStairBlock, 2);
            this.setBlockAndMetadata(world, 3, 1 + step, 3 + step, this.plankStairBlock, 7);
        }
        this.setBlockAndMetadata(world, 2, 4, 6, this.plankStairBlock, 5);
        for(k13 = 3; k13 <= 5; ++k13) {
            this.setAir(world, 3, 5, k13);
        }
        this.setAir(world, 3, 5, 6);
        this.setAir(world, 3, 7, 6);
        this.setBlockAndMetadata(world, 2, 5, 6, this.plankStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 6, 2, this.fenceBlock, this.fenceMeta);
        for(k13 = 2; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, 2, 6, k13, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, 1, 6, 5, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 6, 6, this.fenceGateBlock, 1);
        this.setBlockAndMetadata(world, 1, 6, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 6, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 3, 6, 7, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 3, 6, 9, this.bedBlock, 2);
        this.setBlockAndMetadata(world, 3, 6, 8, this.bedBlock, 10);
        this.setBlockAndMetadata(world, 2, 6, 12, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 3, 6, 12, this.tableBlock, 0);
        this.placeChest(world, random, 3, 6, 11, 5, LOTRChestContents.GONDOR_HOUSE);
        for(i12 = -3; i12 <= -2; ++i12) {
            for(k1 = 7; k1 <= 8; ++k1) {
                this.setBlockAndMetadata(world, i12, 6, k1, this.plankBlock, this.plankMeta);
            }
        }
        this.placeBarrel(world, random, -3, 6, 6, 4, LOTRFoods.GONDOR_DRINK);
        this.placeMug(world, random, -2, 7, 7, 3, LOTRFoods.GONDOR_DRINK);
        this.placePlateWithCertainty(world, random, -2, 7, 8, this.plateBlock, LOTRFoods.GONDOR);
        this.setBlockAndMetadata(world, 0, 9, 4, LOTRMod.chandelier, 1);
        this.setBlockAndMetadata(world, 0, 9, 9, LOTRMod.chandelier, 1);
        for(k13 = 1; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, -3, 6, k13, Blocks.hay_block, 0);
        }
        for(k13 = 1; k13 <= 4; ++k13) {
            this.setBlockAndMetadata(world, -2, 6, k13, Blocks.hay_block, 0);
        }
        for(k13 = 2; k13 <= 3; ++k13) {
            this.setBlockAndMetadata(world, -2, 7, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 6, k13, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -3, 6, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 6, 12, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 7, 12, Blocks.hay_block, 0);
        for(k13 = 10; k13 <= 12; ++k13) {
            this.setBlockAndMetadata(world, -2, 6, k13, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 6, k13, Blocks.hay_block, 0);
        }
        this.setBlockAndMetadata(world, -2, 7, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -1, 7, 11, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 0, 6, 11, Blocks.hay_block, 0);
        if(random.nextInt(3) == 0) {
            if(random.nextBoolean()) {
                this.placeChest(world, random, -2, 6, 3, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
            }
            else {
                this.placeChest(world, random, -1, 6, 11, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
            }
        }
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k1 = 0; k1 <= 13; ++k1) {
                if(!this.isOpaque(world, i12, 1, k1)) continue;
                this.setGrassToDirt(world, i12, 0, k1);
            }
        }
        int animals = 3 + random.nextInt(6);
        for(int l = 0; l < animals; ++l) {
            EntityAnimal animal = LOTRWorldGenGondorBarn.getRandomAnimal(world, random);
            this.spawnNPCAndSetHome(animal, world, 0, 1, 6, 0);
            animal.detachHome();
        }
        for(k1 = 1; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, -10, 1, k1, this.rockWallBlock, this.rockWallMeta);
        }
        for(int k14 : new int[] {0, 13}) {
            this.setBlockAndMetadata(world, -10, 1, k14, this.rockWallBlock, this.rockWallMeta);
            this.setBlockAndMetadata(world, -10, 2, k14, Blocks.torch, 5);
            this.setBlockAndMetadata(world, -9, 1, k14, this.fenceGateBlock, 0);
            for(int i17 = -8; i17 <= -5; ++i17) {
                this.setBlockAndMetadata(world, i17, 1, k14, this.rockWallBlock, this.rockWallMeta);
            }
            this.setBlockAndMetadata(world, -5, 2, k14, Blocks.torch, 5);
        }
        for(int i18 = -9; i18 <= -5; ++i18) {
            for(int k15 = 1; k15 <= 12; ++k15) {
                if(i18 == -5 && k15 >= 2 && k15 <= 11) {
                    this.setBlockAndMetadata(world, i18, -1, k15, Blocks.dirt, 0);
                    this.setBlockAndMetadata(world, i18, 0, k15, Blocks.water, 0);
                    this.setBlockAndMetadata(world, i18, 1, k15, this.rockSlabBlock, this.rockSlabMeta);
                    continue;
                }
                if(i18 >= -8 && k15 >= 2 && k15 <= 11) {
                    this.setBlockAndMetadata(world, i18, 0, k15, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i18, 1, k15, this.cropBlock, this.cropMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i18, 0, k15, LOTRMod.dirtPath, 0);
            }
        }
        this.setBlockAndMetadata(world, -10, 2, 6, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -10, 3, 6, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -10, 4, 6, Blocks.pumpkin, 3);
        LOTREntityGondorFarmer farmer = new LOTREntityGondorFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 6, 8, 16);
        int farmhands = 1 + random.nextInt(3);
        for(int l = 0; l < farmhands; ++l) {
            LOTREntityGondorFarmhand farmhand = new LOTREntityGondorFarmhand(world);
            this.spawnNPCAndSetHome(farmhand, world, -7, 1, 6, 12);
            farmhand.seedsItem = this.seedItem;
        }
        return true;
    }

    public static EntityAnimal getRandomAnimal(World world, Random random) {
        int animal = random.nextInt(4);
        if(animal == 0) {
            return new EntityCow(world);
        }
        if(animal == 1) {
            return new EntityPig(world);
        }
        if(animal == 2) {
            return new EntitySheep(world);
        }
        if(animal == 3) {
            return new EntityChicken(world);
        }
        return null;
    }
}
