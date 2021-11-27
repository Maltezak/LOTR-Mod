package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronMansion extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronMansion(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = Blocks.bed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -5; i1 <= 11; ++i1) {
                for(int k1 = -9; k1 <= 5; ++k1) {
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
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -5; i1 <= 11; ++i1) {
            for(int k1 = -9; k1 <= 5; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                if(i1 >= -4 && i1 <= 10 && k1 >= -4 && k1 <= 4) {
                    j1 = 0;
                    while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                        --j1;
                    }
                    for(j1 = 1; j1 <= 8; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if((i2 > 2 || k1 != -9) && (i2 > 4 || k1 < -8 || k1 > -5)) continue;
                j1 = -1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_mansion");
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
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.associateBlockAlias("CROP", this.cropBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.plantFlower(world, random, -2, 1, -5);
        this.plantFlower(world, random, -1, 1, -5);
        this.plantFlower(world, random, 1, 1, -5);
        this.plantFlower(world, random, 2, 1, -5);
        this.placeWallBanner(world, 3, 3, -4, this.bannerType, 0);
        this.placeChest(world, random, -3, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeBarrel(world, random, 6, 2, 2, 4, LOTRFoods.SOUTHRON_DRINK);
        this.placePlateWithCertainty(world, random, 6, 2, 1, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
        this.placeMug(world, random, 6, 2, 3, 3, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, 10, 2, -2, 7, this.getRandomHaradWeapon(random));
        this.setBlockAndMetadata(world, 8, 5, -1, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 9, 5, -1, this.bedBlock, 9);
        this.setBlockAndMetadata(world, 8, 5, 1, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 9, 5, 1, this.bedBlock, 9);
        this.placeFlowerPot(world, 4, 2, 1, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 9, 6, -3, new ItemStack(LOTRMod.sapling3, 1, 2));
        this.placeFlowerPot(world, 9, 6, 3, new ItemStack(LOTRMod.sapling3, 1, 2));
        int numHaradrim = 2;
        for(int l = 0; l < numHaradrim; ++l) {
            LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
        }
        return true;
    }
}
