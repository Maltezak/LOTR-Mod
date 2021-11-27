package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeSmithy
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 11);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i1 = -5; i1 <= 5; ++i1) {
                for (k1 = -11; k1 <= 5; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if (this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for (i1 = -5; i1 <= 5; ++i1) {
            for (k1 = -5; k1 <= 5; ++k1) {
                for (j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for (i1 = -4; i1 <= 4; ++i1) {
            for (k1 = -10; k1 <= -6; ++k1) {
                for (j1 = 1; j1 <= 4; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("bree_smithy");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
        this.associateBlockAlias("BRICK2_STAIR", this.brick2StairBlock);
        this.associateBlockMetaAlias("BRICK2_WALL", this.brick2WallBlock, this.brick2WallMeta);
        this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
        this.associateBlockMetaAlias("STONE_WALL", this.stoneWallBlock, this.stoneWallMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
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
        this.addBlockMetaAliasOption("PATH", 5, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
        this.addBlockMetaAliasOption("PATH", 5, Blocks.cobblestone, 0);
        this.associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, -2, 1, 3, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 1, 3, this.bedBlock, 11);
        this.placeChest(world, random, 3, 1, 0, 5, LOTRChestContents.BREE_HOUSE);
        this.placeArmorStand(world, 3, 1, -8, 1, this.getDisplayArmorOrNull(world, random));
        this.placeArmorStand(world, 3, 1, -6, 1, this.getDisplayArmorOrNull(world, random));
        this.placeArmorStand(world, 1, 1, 1, 3, this.getDisplayArmorOrNull(world, random));
        this.placeWeaponRack(world, 1, 2, 3, 2, this.getDisplayWeaponOrNull(random));
        this.placeWeaponRack(world, 3, 2, 3, 2, this.getDisplayWeaponOrNull(random));
        this.placeWeaponRack(world, 0, 3, -5, 6, this.getRandomBreeWeapon(random));
        this.placeWeaponRack(world, -2, 3, -2, 4, this.getDisplayWeaponOrNull(random));
        this.placeMug(world, random, -2, 2, 1, 2, LOTRFoods.BREE_DRINK);
        this.placeBarrel(world, random, -3, 2, 1, 3, LOTRFoods.BREE_DRINK);
        this.placePlateWithCertainty(world, random, 0, 2, -3, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
        LOTREntityBreeBlacksmith blacksmith = new LOTREntityBreeBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, 0, 1, -1, 8);
        return true;
    }

    private ItemStack getDisplayWeaponOrNull(Random random) {
        return random.nextBoolean() ? this.getRandomBreeWeapon(random) : null;
    }

    private ItemStack[] getDisplayArmorOrNull(World world, Random random) {
        if (random.nextBoolean()) {
            LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
            armorGuard.onSpawnWithEgg(null);
            return new ItemStack[]{armorGuard.getEquipmentInSlot(4), armorGuard.getEquipmentInSlot(3), null, null};
        }
        return null;
    }
}

