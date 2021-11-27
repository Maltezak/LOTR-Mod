package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTavern extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfTavern(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int i12;
        int step;
        int j2;
        int j12;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i1 = -10; i1 <= 10; ++i1) {
                for(int k12 = -10; k12 <= 10; ++k12) {
                    j12 = this.getTopBlock(world, i1, k12) - 1;
                    if(!this.isSurface(world, i1, j12, k12)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i13 = -10; i13 <= 10; ++i13) {
            for(int k13 = -10; k13 <= 10; ++k13) {
                int k2;
                int i2 = Math.abs(i13);
                if(i2 * i2 + (k2 = Math.abs(k13)) * k2 >= 100) continue;
                for(j12 = 1; j12 <= 6; ++j12) {
                    this.setAir(world, i13, j12, k13);
                }
            }
        }
        this.loadStrScan("gulf_tavern");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockAlias("PLANK2_STAIR", this.plank2StairBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        String[] tavernName = LOTRNames.getHaradTavernName(random);
        String tavernNameNPC = tavernName[0] + " " + tavernName[1];
        this.placeSign(world, 0, 3, -10, Blocks.wall_sign, 2, new String[] {"", tavernName[0], tavernName[1], ""});
        this.placeSign(world, 0, 3, 10, Blocks.wall_sign, 3, new String[] {"", tavernName[0], tavernName[1], ""});
        this.placeBarrel(world, random, -3, 2, -2, 4, LOTRFoods.GULF_HARAD_DRINK);
        this.placeBarrel(world, random, 3, 2, 1, 5, LOTRFoods.GULF_HARAD_DRINK);
        this.placeFlowerPot(world, 3, 2, -2, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -3, 2, 1, this.getRandomFlower(world, random));
        this.placeKebabStand(world, random, -2, 2, 2, LOTRMod.kebabStand, 2);
        this.placeKebabStand(world, random, 2, 2, 2, LOTRMod.kebabStand, 2);
        this.placeWallBanner(world, -2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeFoodOrDrink(world, random, -5, 2, -7);
        this.placeFoodOrDrink(world, random, -5, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -6);
        this.placeFoodOrDrink(world, random, -6, 2, -5);
        this.placeFoodOrDrink(world, random, -7, 2, -5);
        this.placeFoodOrDrink(world, random, -6, 2, -1);
        this.placeFoodOrDrink(world, random, -6, 2, 0);
        this.placeFoodOrDrink(world, random, -6, 2, 1);
        this.placeFoodOrDrink(world, random, -5, 2, 7);
        this.placeFoodOrDrink(world, random, -5, 2, 6);
        this.placeFoodOrDrink(world, random, -6, 2, 6);
        this.placeFoodOrDrink(world, random, -6, 2, 5);
        this.placeFoodOrDrink(world, random, -7, 2, 5);
        this.placeFoodOrDrink(world, random, 5, 2, -7);
        this.placeFoodOrDrink(world, random, 5, 2, -6);
        this.placeFoodOrDrink(world, random, 6, 2, -6);
        this.placeFoodOrDrink(world, random, 6, 2, -5);
        this.placeFoodOrDrink(world, random, 7, 2, -5);
        this.placeFoodOrDrink(world, random, 6, 2, -1);
        this.placeFoodOrDrink(world, random, 6, 2, 0);
        this.placeFoodOrDrink(world, random, 6, 2, 1);
        this.placeFoodOrDrink(world, random, 5, 2, 7);
        this.placeFoodOrDrink(world, random, 5, 2, 6);
        this.placeFoodOrDrink(world, random, 6, 2, 6);
        this.placeFoodOrDrink(world, random, 6, 2, 5);
        this.placeFoodOrDrink(world, random, 7, 2, 5);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.placeFoodOrDrink(world, random, i1, 2, -3);
            this.placeFoodOrDrink(world, random, i1, 2, 3);
        }
        LOTREntityGulfBartender bartender = new LOTREntityGulfBartender(world);
        bartender.setSpecificLocationName(tavernNameNPC);
        this.spawnNPCAndSetHome(bartender, world, 0, 1, 0, 4);
        int numHaradrim = MathHelper.getRandomIntegerInRange(random, 3, 8);
        for(int l = 0; l < numHaradrim; ++l) {
            LOTREntityGulfHaradrim haradrim = new LOTREntityGulfHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -5 : 5, 1, 0, 16);
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(step = 0; step < 12 && !this.isOpaque(world, i12, j1 = 0 - step, k1 = -10 - step); ++step) {
                this.setBlockAndMetadata(world, i12, j1, k1, LOTRMod.stairsRedSandstone, 2);
                this.setGrassToDirt(world, i12, j1 - 1, k1);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k1, LOTRMod.redSandstone, 0);
                    this.setGrassToDirt(world, i12, j2 - 1, k1);
                    --j2;
                }
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(step = 0; step < 12 && !this.isOpaque(world, i12, j1 = 0 - step, k1 = 10 + step); ++step) {
                this.setBlockAndMetadata(world, i12, j1, k1, LOTRMod.stairsRedSandstone, 3);
                this.setGrassToDirt(world, i12, j1 - 1, k1);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k1, LOTRMod.redSandstone, 0);
                    this.setGrassToDirt(world, i12, j2 - 1, k1);
                    --j2;
                }
            }
        }
        return true;
    }

    private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
        if(random.nextBoolean()) {
            if(random.nextBoolean()) {
                this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GULF_HARAD_DRINK);
            }
            else {
                Block plateBlock;
                plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
                if(random.nextBoolean()) {
                    this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
                }
                else {
                    this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.GULF_HARAD);
                }
            }
        }
    }
}
