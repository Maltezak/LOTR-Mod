package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeInn
extends LOTRWorldGenBreeStructure {
    private boolean hasPresets = false;
    private String[] presetInnName;
    private String presetInnkeeperName;
    private boolean presetIsMaleKeeper;
    private boolean presetIsHobbitKeeper;

    public LOTRWorldGenBreeInn(boolean flag) {
        super(flag);
    }

    public void setPresets(String[] innName, String innkeeperName, boolean innkeeperMale, boolean hobbit) {
        this.hasPresets = true;
        this.presetInnName = innName;
        this.presetInnkeeperName = innkeeperName;
        this.presetIsMaleKeeper = innkeeperMale;
        this.presetIsHobbitKeeper = hobbit;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        LOTREntityMan innkeeper;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 5, -2);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i1 = -9; i1 <= 9; ++i1) {
                for (k1 = -7; k1 <= 7; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if (this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for (i1 = -8; i1 <= 8; ++i1) {
            for (k1 = -5; k1 <= 6; ++k1) {
                for (j1 = 1; j1 <= 12; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("bree_inn");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockAlias("BRICK2_STAIR", this.brick2StairBlock);
        this.associateBlockMetaAlias("BRICK2_WALL", this.brick2WallBlock, this.brick2WallMeta);
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
        this.associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
        this.setBlockAliasChance("LEAF", 0.6f);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeRandomFlowerPot(world, random, -4, 2, -3);
        this.placeRandomFlowerPot(world, random, -3, 6, 0);
        this.plantFlower(world, random, -8, 6, 0);
        this.plantFlower(world, random, 8, 6, 0);
        this.plantFlower(world, random, -8, 6, 1);
        this.plantFlower(world, random, 8, 6, 1);
        this.placeChest(world, random, -5, 1, -3, 3, LOTRChestContents.BREE_HOUSE);
        this.setBlockAndMetadata(world, -6, 2, -3, LOTRWorldGenBreeInn.getRandomPieBlock(random), 0);
        this.placeBarrel(world, random, -6, 2, 1, 4, LOTRFoods.BREE_DRINK);
        this.placeBarrel(world, random, -4, 2, 4, 2, LOTRFoods.BREE_DRINK);
        this.placeFoodOrDrink(world, random, 6, 2, -3);
        this.placeFoodOrDrink(world, random, 5, 2, -3);
        this.placeFoodOrDrink(world, random, 1, 2, -3);
        this.placeFoodOrDrink(world, random, 6, 2, -2);
        this.placeFoodOrDrink(world, random, 5, 2, -2);
        this.placeFoodOrDrink(world, random, 0, 2, 0);
        this.placeFoodOrDrink(world, random, -6, 2, 0);
        this.placeFoodOrDrink(world, random, -4, 2, 0);
        this.placeFoodOrDrink(world, random, -4, 2, 1);
        this.placeFoodOrDrink(world, random, 0, 2, 1);
        this.placeFoodOrDrink(world, random, -4, 2, 3);
        this.placeFoodOrDrink(world, random, 6, 2, 4);
        this.placeFoodOrDrink(world, random, 2, 2, 4);
        this.placeFoodOrDrink(world, random, 6, 6, -3);
        this.placeFoodOrDrink(world, random, 5, 6, -3);
        this.placeFoodOrDrink(world, random, 0, 6, -3);
        this.placeFoodOrDrink(world, random, -5, 6, -3);
        this.placeFoodOrDrink(world, random, -6, 6, -3);
        this.placeFoodOrDrink(world, random, 5, 6, 4);
        this.placeFoodOrDrink(world, random, -5, 6, 4);
        this.placeWeaponRack(world, -3, 7, -1, 5, this.getRandomBreeWeapon(random));
        this.placeWeaponRack(world, 3, 7, -1, 7, this.getRandomBreeWeapon(random));
        this.placeWeaponRack(world, -3, 7, 2, 5, this.getRandomBreeWeapon(random));
        this.placeWeaponRack(world, 3, 7, 2, 7, this.getRandomBreeWeapon(random));
        this.setBlockAndMetadata(world, 5, 5, 0, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 6, 5, 0, this.bedBlock, 9);
        this.setBlockAndMetadata(world, -5, 5, 0, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -6, 5, 0, this.bedBlock, 11);
        this.setBlockAndMetadata(world, 5, 5, 2, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 6, 5, 2, this.bedBlock, 9);
        this.setBlockAndMetadata(world, -5, 5, 2, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -6, 5, 2, this.bedBlock, 11);
        this.setBlockAndMetadata(world, 5, 8, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, 5, 8, -3, this.bedBlock, 10);
        this.setBlockAndMetadata(world, -5, 8, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -5, 8, -3, this.bedBlock, 10);
        String[] innName = LOTRNames.getBreeInnName(random);
        if (this.hasPresets) {
            innName = this.presetInnName;
        }
        String innNameNPC = innName[0] + " " + innName[1];
        this.placeSign(world, -2, 4, -7, Blocks.wall_sign, 2, new String[]{"", innName[0], innName[1], ""});
        this.placeSign(world, -1, 4, -6, Blocks.wall_sign, 4, new String[]{"", innName[0], innName[1], ""});
        this.placeSign(world, -3, 4, -6, Blocks.wall_sign, 5, new String[]{"", innName[0], innName[1], ""});
        if (this.hasPresets) {
            innkeeper = this.presetIsHobbitKeeper ? new LOTREntityBreeHobbitInnkeeper(world) : new LOTREntityBreeInnkeeper(world);
        } else {
            innkeeper = random.nextInt(3) == 0 ? new LOTREntityBreeHobbitInnkeeper(world) : new LOTREntityBreeInnkeeper(world);
        }
        if (this.hasPresets) {
            innkeeper.familyInfo.setMale(this.presetIsMaleKeeper);
            innkeeper.familyInfo.setName(this.presetInnkeeperName);
        }
        innkeeper.setSpecificLocationName(innNameNPC);
        this.spawnNPCAndSetHome(innkeeper, world, -5, 1, 0, 4);
        String[] innkeeperNameParts = innkeeper.getNPCName().split(" ");
        if (innkeeperNameParts.length < 2) {
            innkeeperNameParts = new String[]{innkeeperNameParts[0], ""};
        }
        this.placeSign(world, -2, 3, -5, Blocks.wall_sign, 2, new String[]{"", "by " + innkeeperNameParts[0], innkeeperNameParts[1], ""});
        int men = 8 + random.nextInt(6);
        for (int l = 0; l < men; ++l) {
            LOTREntityMan breelander;
            breelander = random.nextInt(3) == 0 ? new LOTREntityBreeHobbit(world) : new LOTREntityBreeMan(world);
            if (random.nextInt(10) == 0) {
                breelander = new LOTREntityRuffianSpy(world);
            }
            this.spawnNPCAndSetHome(breelander, world, -2, 1, 0, 16);
        }
        return true;
    }

    private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.BREE_DRINK);
            } else {
                Block[] plates = new Block[]{LOTRMod.plateBlock, LOTRMod.ceramicPlateBlock, LOTRMod.woodPlateBlock};
                Block plateBlock = plates[random.nextInt(plates.length)];
                if (random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                } else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.BREE);
                }
            }
        }
    }
}

