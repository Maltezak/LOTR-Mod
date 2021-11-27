package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTavern extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronTavern(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 16);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i1 = -6; i1 <= 6; ++i1) {
                for(k1 = -16; k1 <= 16; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 10) continue;
                    return false;
                }
            }
        }
        for(int i12 = -5; i12 <= 5; ++i12) {
            for(int k12 = -15; k12 <= 15; ++k12) {
                int j1 = 0;
                while(!this.isOpaque(world, i12, j1, k12) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.stoneBlock, this.stoneMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k12);
                    --j1;
                }
                for(j1 = 1; j1 <= 9; ++j1) {
                    this.setAir(world, i12, j1, k12);
                }
            }
        }
        this.loadStrScan("southron_tavern");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        String[] tavernName = LOTRNames.getHaradTavernName(random);
        String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeWeaponRack(world, 4, 3, -4, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -4, 3, 4, 5, this.getRandomHaradWeapon(random));
        this.spawnItemFrame(world, 5, 3, -8, 3, this.getRandomHaradItem(random));
        this.spawnItemFrame(world, -5, 3, -4, 1, this.getRandomHaradItem(random));
        this.spawnItemFrame(world, 5, 3, 4, 3, this.getRandomHaradItem(random));
        this.placeFoodOrDrink(world, random, -2, 2, -12);
        this.placeFoodOrDrink(world, random, -2, 2, -11);
        for(i1 = 0; i1 <= 2; ++i1) {
            for(k1 = -9; k1 <= -7; ++k1) {
                this.placeFoodOrDrink(world, random, i1, 2, k1);
            }
        }
        this.placeFoodOrDrink(world, random, -2, 2, -5);
        this.placeFoodOrDrink(world, random, -2, 2, -4);
        this.placeFoodOrDrink(world, random, 1, 2, -4);
        this.placeFoodOrDrink(world, random, 2, 2, -4);
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = 1; k1 <= 3; ++k1) {
                if(i1 == 0 && k1 == 2) {
                    this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.lemonCake, 0);
                    continue;
                }
                this.placeFoodOrDrink(world, random, i1, 2, k1);
            }
        }
        this.placeFoodOrDrink(world, random, -3, 2, 7);
        this.placeFoodOrDrink(world, random, -2, 2, 7);
        this.placeFoodOrDrink(world, random, -1, 2, 7);
        this.placeKebabStand(world, random, -4, 2, 9, LOTRMod.kebabStand, 4);
        this.placeChest(world, random, 3, 1, 14, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeBarrel(world, random, 4, 2, 11, 5, LOTRFoods.SOUTHRON_DRINK);
        this.placeBarrel(world, random, 4, 2, 12, 5, LOTRFoods.SOUTHRON_DRINK);
        this.setBlockAndMetadata(world, -3, 8, -13, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -3, 8, -14, this.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 8, -13, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, -14, this.bedBlock, 10);
        this.placeFlowerPot(world, -1, 9, -14, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, -5, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, -4, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 8, -5, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 8, -4, this.bedBlock, 8);
        this.placeFlowerPot(world, -1, 9, -4, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, -1, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -3, 8, -2, this.bedBlock, 10);
        this.setBlockAndMetadata(world, -4, 8, -1, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -4, 8, -2, this.bedBlock, 10);
        this.placeFlowerPot(world, -1, 9, -2, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -3, 8, 7, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 8, 8, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 8, 7, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 8, 8, this.bedBlock, 8);
        this.placeFlowerPot(world, -1, 9, 8, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 1, 9, -3, this.getRandomFlower(world, random));
        this.placeWallBanner(world, -2, 5, -15, this.bannerType, 0);
        this.placeWallBanner(world, 2, 5, -15, this.bannerType, 0);
        LOTREntityNearHaradrimBase bartender = this.createBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, -2, 1, 8, 4);
        int haradrim = 4 + random.nextInt(10);
        for(int l = 0; l < haradrim; ++l) {
            LOTREntityNearHaradrimBase southron = this.createHaradrim(world);
            this.spawnNPCAndSetHome(southron, world, 0, 1, 0, 16);
        }
        block11: for(int i13 = -1; i13 <= 1; ++i13) {
            int j1 = 0;
            for(int step = 0; step < 12; ++step) {
                int j2;
                int k13 = -17 - step;
                if(this.isOpaque(world, i13, j1 + 1, k13)) {
                    this.setAir(world, i13, ++j1 + 1, k13);
                    this.setAir(world, i13, j1 + 2, k13);
                    this.setAir(world, i13, j1 + 3, k13);
                    this.setBlockAndMetadata(world, i13, j1, k13, this.stoneStairBlock, 3);
                    this.setGrassToDirt(world, i13, j1 - 1, k13);
                    j2 = j1 - 1;
                    while(!this.isOpaque(world, i13, j2, k13) && this.getY(j2) >= 0) {
                        this.setBlockAndMetadata(world, i13, j2, k13, this.stoneBlock, this.stoneMeta);
                        this.setGrassToDirt(world, i13, j2 - 1, k13);
                        --j2;
                    }
                    continue;
                }
                if(this.isOpaque(world, i13, j1, k13)) continue block11;
                this.setAir(world, i13, j1 + 1, k13);
                this.setAir(world, i13, j1 + 2, k13);
                this.setAir(world, i13, j1 + 3, k13);
                this.setBlockAndMetadata(world, i13, j1, k13, this.stoneStairBlock, 2);
                this.setGrassToDirt(world, i13, j1 - 1, k13);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i13, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k13, this.stoneBlock, this.stoneMeta);
                    this.setGrassToDirt(world, i13, j2 - 1, k13);
                    --j2;
                }
                --j1;
            }
        }
        this.setBlockAndMetadata(world, 0, 5, -16, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 5, -17, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -17, this.plankBlock, this.plankMeta);
        this.placeSign(world, -1, 4, -17, Blocks.wall_sign, 5, new String[] {"", tavernName[0], tavernName[1], ""});
        this.placeSign(world, 0, 4, -18, Blocks.wall_sign, 2, new String[] {"", tavernName[0], tavernName[1], ""});
        this.placeSign(world, 1, 4, -17, Blocks.wall_sign, 4, new String[] {"", tavernName[0], tavernName[1], ""});
        return true;
    }

    protected LOTREntityNearHaradrimBase createBartender(World world) {
        if(this.isUmbar()) {
            return new LOTREntityUmbarBartender(world);
        }
        return new LOTREntitySouthronBartender(world);
    }

    private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
        if(random.nextBoolean()) {
            if(random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.SOUTHRON_DRINK);
            }
            else {
                Block plateBlock;
                plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if(random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.SOUTHRON);
                }
            }
        }
    }
}
