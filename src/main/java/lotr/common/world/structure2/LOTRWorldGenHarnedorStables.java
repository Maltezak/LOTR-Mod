package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorStables extends LOTRWorldGenHarnedorStructure {
    public LOTRWorldGenHarnedorStables(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -7; i1 <= 7; ++i1) {
                for(int k1 = -10; k1 <= 10; ++k1) {
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
        for(int i1 = -7; i1 <= 7; ++i1) {
            for(int k1 = -10; k1 <= 10; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if((((i2 > 5) || (k2 > 4)) && ((i2 > 4) || (k2 > 6)) && ((i2 > 3) || (k2 > 7)) && ((i2 > 2) || (k2 > 8))) && (i2 > 1 || k2 > 9)) continue;
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("harnedor_stables");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.addBlockMetaAliasOption("GROUND", 5, Blocks.grass, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.sand, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, -2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, 2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.spawnItemFrame(world, -2, 2, 0, 1, new ItemStack(Items.saddle));
        this.spawnItemFrame(world, 2, 2, 0, 3, new ItemStack(Items.lead));
        this.setBlockAndMetadata(world, -3, 1, 6, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 7, this.bedBlock, 8);
        this.placeChest(world, random, -4, 1, 6, LOTRMod.chestBasket, 4, LOTRChestContents.HARNENNOR_HOUSE);
        this.placePlateWithCertainty(world, random, 4, 2, 6, LOTRMod.woodPlateBlock, LOTRFoods.HARNEDOR);
        this.placeMug(world, random, 4, 2, 5, 1, LOTRFoods.HARNEDOR_DRINK);
        LOTREntityHarnedhrim harnedhrim = new LOTREntityHarnedhrim(world);
        this.spawnNPCAndSetHome(harnedhrim, world, 0, 1, 0, 12);
        for(int k1 : new int[] {-2, 2}) {
            for(int i1 : new int[] {-4, 4}) {
                int j12 = 1;
                LOTREntityHorse horse = new LOTREntityHorse(world);
                this.spawnNPCAndSetHome(horse, world, i1, j12, k1, 0);
                horse.setHorseType(0);
                horse.saddleMountForWorldGen();
                horse.detachHome();
            }
        }
        return true;
    }
}
