package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHouse
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int k1;
        int j2;
        int j1;
        int i12;
        int randPath;
        int j12;
        int i13;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i13 = -7; i13 <= 8; ++i13) {
                for (k12 = -8; k12 <= 5; ++k12) {
                    j12 = this.getTopBlock(world, i13, k12) - 1;
                    if (this.isSurface(world, i13, j12, k12)) continue;
                    return false;
                }
            }
        }
        for (i13 = -3; i13 <= 8; ++i13) {
            for (k12 = -5; k12 <= 3; ++k12) {
                for (j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i13, j12, k12);
                }
            }
        }
        for (i13 = -2; i13 <= 2; ++i13) {
            for (k12 = -8; k12 <= -6; ++k12) {
                for (j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i13, j12, k12);
                }
            }
        }
        for (i13 = 2; i13 <= 7; ++i13) {
            for (k12 = 3; k12 <= 5; ++k12) {
                for (j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i13, j12, k12);
                }
            }
        }
        for (i13 = -7; i13 <= -3; ++i13) {
            for (k12 = -4; k12 <= 2; ++k12) {
                for (j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i13, j12, k12);
                }
            }
        }
        this.loadStrScan("bree_house");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
        this.associateBlockMetaAlias("STONE_WALL", this.stoneWallBlock, this.stoneWallMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
        this.addBlockMetaAliasOption("PATH", 5, Blocks.grass, 0);
        this.addBlockMetaAliasOption("PATH", 5, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
        this.addBlockMetaAliasOption("PATH", 5, Blocks.cobblestone, 0);
        this.associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
        this.generateStrScan(world, random, 0, 0, 0);
        for (i1 = 3; i1 <= 6; ++i1) {
            for (int step = 0; step < 12 && !this.isOpaque(world, i1, j1 = -1 - step, k1 = 6 + step); ++step) {
                randPath = random.nextInt(4);
                if (randPath == 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
                } else if (randPath == 1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 1);
                } else if (randPath == 2) {
                    this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                } else if (randPath == 3) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
                }
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                j2 = j1 - 1;
                while (!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j2 - 1, k1);
                    --j2;
                }
            }
        }
        for (int step = 0; step < 12 && !this.isOpaque(world, i12 = -5, j1 = 0 - step, k1 = -5 - step); ++step) {
            randPath = random.nextInt(4);
            if (randPath == 0) {
                this.setBlockAndMetadata(world, i12, j1, k1, Blocks.grass, 0);
            } else if (randPath == 1) {
                this.setBlockAndMetadata(world, i12, j1, k1, Blocks.dirt, 1);
            } else if (randPath == 2) {
                this.setBlockAndMetadata(world, i12, j1, k1, LOTRMod.dirtPath, 0);
            } else if (randPath == 3) {
                this.setBlockAndMetadata(world, i12, j1, k1, Blocks.cobblestone, 0);
            }
            this.setGrassToDirt(world, i12, j1 - 1, k1);
            j2 = j1 - 1;
            while (!this.isOpaque(world, i12, j2, k1) && this.getY(j2) >= 0) {
                this.setBlockAndMetadata(world, i12, j2, k1, Blocks.dirt, 0);
                this.setGrassToDirt(world, i12, j2 - 1, k1);
                --j2;
            }
        }
        for (i1 = -6; i1 <= -3; ++i1) {
            for (int k13 = -3; k13 <= 1; ++k13) {
                j1 = 1;
                if (this.getBlock(world, i1, j1 - 1, k13) != Blocks.grass || random.nextInt(4) != 0) continue;
                this.plantFlower(world, random, i1, j1, k13);
            }
        }
        this.placeRandomFlowerPot(world, random, 6, 1, 3);
        this.placeRandomFlowerPot(world, random, 3, 1, 3);
        this.placeRandomFlowerPot(world, random, -1, 5, -1);
        this.placeRandomFlowerPot(world, random, 2, 5, 1);
        this.plantFlower(world, random, 0, 2, 3);
        this.plantFlower(world, random, 8, 6, -1);
        this.placeChest(world, random, -1, 1, 1, 4, LOTRChestContents.BREE_HOUSE);
        this.placeChest(world, random, 1, 5, 1, 2, LOTRChestContents.BREE_HOUSE);
        this.placeMug(world, random, 3, 2, -2, 3, LOTRFoods.BREE_DRINK);
        this.placePlateWithCertainty(world, random, 3, 2, -3, LOTRMod.plateBlock, LOTRFoods.BREE);
        this.setBlockAndMetadata(world, 0, 5, 0, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -1, 5, 0, this.bedBlock, 11);
        if (random.nextBoolean()) {
            this.spawnItemFrame(world, 2, 3, 0, 3, new ItemStack(Items.clock));
        }
        String[] breeNames = LOTRNames.getBreeCoupleAndHomeNames(random);
        LOTREntityBreeMan man = new LOTREntityBreeMan(world);
        man.familyInfo.setMale(true);
        man.familyInfo.setName(breeNames[0]);
        this.spawnNPCAndSetHome(man, world, 0, 1, 0, 16);
        LOTREntityBreeMan woman = new LOTREntityBreeMan(world);
        woman.familyInfo.setMale(false);
        woman.familyInfo.setName(breeNames[1]);
        this.spawnNPCAndSetHome(woman, world, 0, 1, 0, 16);
        this.placeSign(world, 2, 2, -8, Blocks.standing_sign, 9, new String[]{"", breeNames[2], breeNames[3], ""});
        return true;
    }
}

