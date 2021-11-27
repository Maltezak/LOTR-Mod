package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingTavern extends LOTRWorldGenDunlandStructure {
    public LOTRWorldGenDunlendingTavern(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -11; i1 <= 11; ++i1) {
                for(int k1 = -8; k1 <= 8; ++k1) {
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
                    if(maxHeight - minHeight <= 12) continue;
                    return false;
                }
            }
        }
        for(int i1 = -9; i1 <= 9; ++i1) {
            for(int k1 = -7; k1 <= 7; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                j1 = -1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.floorBlock, this.floorMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                if(random.nextInt(4) != 0 || i2 <= 3 && k2 <= 2) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        this.loadStrScan("dunland_tavern");
        this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
        this.associateBlockMetaAlias("WOOD|8", this.woodBlock, this.woodMeta | 8);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("BARS", this.barsBlock, this.barsMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeFlowerPot(world, 8, 2, -5, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 8, 2, 5, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -8, 2, -4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -8, 2, 4, this.getRandomFlower(world, random));
        this.placeChest(world, random, 7, 1, -5, LOTRMod.chestBasket, 5, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeBarrel(world, random, 7, 2, 6, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeBarrel(world, random, 4, 2, 6, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeFoodOrDrink(world, random, -6, 2, -6);
        this.placeFoodOrDrink(world, random, -5, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -1);
        this.placeFoodOrDrink(world, random, -5, 2, -1);
        this.placeFoodOrDrink(world, random, -6, 2, 0);
        this.placeFoodOrDrink(world, random, -5, 2, 0);
        this.placeFoodOrDrink(world, random, -6, 2, 1);
        this.placeFoodOrDrink(world, random, -5, 2, 1);
        this.placeFoodOrDrink(world, random, -6, 2, 6);
        this.placeFoodOrDrink(world, random, -5, 2, 6);
        this.placeFoodOrDrink(world, random, -1, 2, 6);
        this.placeFoodOrDrink(world, random, 0, 2, 6);
        this.placeFoodOrDrink(world, random, 1, 2, 6);
        this.placeFoodOrDrink(world, random, 5, 2, 3);
        this.placeFoodOrDrink(world, random, 6, 2, 3);
        this.placeFoodOrDrink(world, random, 8, 2, 4);
        this.placeFoodOrDrink(world, random, 5, 2, -3);
        this.placeFoodOrDrink(world, random, 6, 2, -3);
        this.placeFoodOrDrink(world, random, 8, 2, -4);
        this.placeFoodOrDrink(world, random, 4, 2, -6);
        this.placeFoodOrDrink(world, random, 7, 2, -6);
        String[] tavernName = LOTRNames.getDunlendingTavernName(random);
        String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeSign(world, 0, 3, -8, Blocks.wall_sign, 2, new String[] {"", tavernName[0], tavernName[1], ""});
        this.placeWallBanner(world, -8, 6, 0, LOTRItemBanner.BannerType.DUNLAND, 1);
        this.placeWallBanner(world, 8, 6, 0, LOTRItemBanner.BannerType.DUNLAND, 3);
        for(int k1 : new int[] {-3, 3}) {
            this.placeDunlandItemFrame(world, random, -3, 2, k1, 1);
            this.placeDunlandItemFrame(world, random, 3, 2, k1, 3);
        }
        LOTREntityDunlendingBartender bartender = new LOTREntityDunlendingBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        if(random.nextBoolean()) {
            this.spawnNPCAndSetHome(bartender, world, 5, 1, -4, 2);
        }
        else {
            this.spawnNPCAndSetHome(bartender, world, 5, 1, 4, 2);
        }
        int dunlendings = MathHelper.getRandomIntegerInRange(random, 3, 8);
        for(int l = 0; l < dunlendings; ++l) {
            LOTREntityDunlending dunlending = new LOTREntityDunlending(world);
            this.spawnNPCAndSetHome(dunlending, world, 0, 1, 0, 16);
        }
        return true;
    }

    private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
        if(random.nextBoolean()) {
            if(random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.DUNLENDING_DRINK);
            }
            else {
                Block plateBlock;
                plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if(random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.DUNLENDING);
                }
            }
        }
    }
}
