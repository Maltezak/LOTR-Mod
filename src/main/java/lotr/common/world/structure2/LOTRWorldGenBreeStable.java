package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeStable
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeStable(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int j1;
        int i1;
        int j12;
        int i12;
        int j2;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i12 = -9; i12 <= 9; ++i12) {
                for (k12 = -5; k12 <= 9; ++k12) {
                    j12 = this.getTopBlock(world, i12, k12) - 1;
                    if (this.isSurface(world, i12, j12, k12)) continue;
                    return false;
                }
            }
        }
        for (i12 = -8; i12 <= 8; ++i12) {
            for (k12 = -5; k12 <= 5; ++k12) {
                for (j12 = 1; j12 <= 8; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for (i12 = -5; i12 <= 5; ++i12) {
            for (k12 = 6; k12 <= 8; ++k12) {
                for (j12 = 1; j12 <= 4; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for (i12 = -2; i12 <= 2; ++i12) {
            if (i12 == 0) continue;
            k12 = -5;
            for (j12 = 1; j12 <= 3; ++j12) {
                this.setAir(world, i12, j12, k12);
            }
        }
        for (int i13 = -2; i13 <= 2; ++i13) {
            if (i13 == 0) continue;
            for (int step = 0; step < 12 && !this.isOpaque(world, i13, j1 = 0 - step, k1 = -5 - step); ++step) {
                this.setBlockAndMetadata(world, i13, j1, k1, Blocks.grass, 0);
                this.setGrassToDirt(world, i13, j1 - 1, k1);
                j2 = j1 - 1;
                while (!this.isOpaque(world, i13, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i13, j2 - 1, k1);
                    --j2;
                }
            }
        }
        for (int j13 = 1; j13 <= 2; ++j13) {
            this.setAir(world, 5, j13, 6);
        }
        for (int step = 0; step < 12 && !this.isOpaque(world, i1 = 5 + step, j1 = 0 - step, k1 = 6); ++step) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.stone_stairs, 0);
            this.setGrassToDirt(world, i1, j1 - 1, k1);
            j2 = j1 - 1;
            while (!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                this.setBlockAndMetadata(world, i1, j2, k1, Blocks.cobblestone, 0);
                this.setGrassToDirt(world, i1, j2 - 1, k1);
                --j2;
            }
        }
        this.loadStrScan("bree_stable");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("COBBLE", Blocks.cobblestone, 0);
        this.associateBlockMetaAlias("COBBLE_WALL", Blocks.cobblestone_wall, 0);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.gravel, 0);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.grass, 0);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, LOTRMod.dirtPath, 0);
        this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
        this.setBlockAliasChance("THATCH_FLOOR", 0.15f);
        this.associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, -3, 1, 6, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -3, 1, 5, this.bedBlock, 10);
        this.placeRandomFlowerPot(world, random, 3, 2, 5);
        this.placePlateWithCertainty(world, random, 1, 2, 7, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
        this.placeMug(world, random, 0, 2, 7, 3, LOTRFoods.BREE_DRINK);
        this.placeBarrel(world, random, -1, 2, 7, 2, LOTRFoods.BREE_DRINK);
        this.placeChest(world, random, -3, 1, 7, 4, LOTRChestContents.BREE_HOUSE);
        this.placeWeaponRack(world, 0, 2, 3, 6, this.getRandomBreeWeapon(random));
        LOTREntityBreeMan stabler = new LOTREntityBreeMan(world);
        this.spawnNPCAndSetHome(stabler, world, 0, 1, -1, 16);
        this.spawnHorse(world, random, -6, 1, -2);
        this.spawnHorse(world, random, 6, 1, -2);
        this.spawnHorse(world, random, -6, 1, 2);
        this.spawnHorse(world, random, 6, 1, 2);
        return true;
    }

    private void spawnHorse(World world, Random random, int i, int j, int k) {
        int horses = 1 + random.nextInt(2);
        for (int l = 0; l < horses; ++l) {
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, i, j, k, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
        }
    }
}

