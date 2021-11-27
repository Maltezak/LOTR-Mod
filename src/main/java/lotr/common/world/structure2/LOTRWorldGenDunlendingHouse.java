package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingHouse extends LOTRWorldGenDunlandStructure {
    public LOTRWorldGenDunlendingHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -6; i1 <= 6; ++i1) {
                for(int k1 = -7; k1 <= 7; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
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
        for(int i1 = -4; i1 <= 4; ++i1) {
            for(int k1 = -6; k1 <= 5; ++k1) {
                Math.abs(i1);
                Math.abs(k1);
                if(k1 >= -5) {
                    j1 = -1;
                    while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.floorBlock, this.floorMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                        --j1;
                    }
                }
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("dunland_house");
        this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|8", this.woodBlock, this.woodMeta | 8);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("BARS", this.barsBlock, this.barsMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, this.bedBlock, 8);
        this.placeChest(world, random, -2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeBarrel(world, random, -3, 2, -3, 4, LOTRFoods.DUNLENDING_DRINK);
        this.placePlate(world, random, -3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placePlate(world, random, -3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placeMug(world, random, 3, 2, -3, 1, LOTRFoods.DUNLENDING_DRINK);
        this.placePlate(world, random, 3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placePlate(world, random, 3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
        this.placeFlowerPot(world, -3, 2, 1, this.getRandomFlower(world, random));
        this.placeWeaponRack(world, 0, 3, -4, 4, this.getRandomDunlandWeapon(random));
        this.placeDunlandItemFrame(world, random, -2, 2, -5, 0);
        this.placeDunlandItemFrame(world, random, 2, 2, -5, 0);
        this.placeDunlandItemFrame(world, random, -2, 2, 5, 2);
        this.placeDunlandItemFrame(world, random, 2, 2, 5, 2);
        this.placeWallBanner(world, -2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, 2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
        LOTREntityDunlending dunlending = new LOTREntityDunlending(world);
        this.spawnNPCAndSetHome(dunlending, world, 0, 1, 0, 16);
        return true;
    }
}
