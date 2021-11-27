package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenCorsairCove extends LOTRWorldGenCorsairStructure {
    public LOTRWorldGenCorsairCove(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -15; i12 <= 9; ++i12) {
                for(int k12 = -1; k12 <= 12; ++k12) {
                    int j12 = this.getTopBlock(world, i12, k12) - 1;
                    Block block = this.getBlock(world, i12, j12, k12);
                    if(!this.isSurface(world, i12, j12, k12) && block != Blocks.stone && block != Blocks.sandstone) {
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
        for(i1 = -14; i1 <= 4; ++i1) {
            for(k1 = 0; k1 <= 7; ++k1) {
                for(j1 = 1; j1 <= 9; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("corsair_cove");
        this.addBlockMetaAliasOption("STONE", 10, Blocks.stone, 0);
        this.addBlockMetaAliasOption("STONE", 3, Blocks.sandstone, 0);
        this.addBlockMetaAliasOption("STONE", 3, Blocks.dirt, 1);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PILLAR_SLAB", this.pillarSlabBlock, this.pillarSlabMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, random, -3, 7, 3);
        this.placeBanner(world, 1, 5, 3, LOTRItemBanner.BannerType.UMBAR, 2);
        this.placeChest(world, random, -14, 4, 4, Blocks.chest, 4, LOTRChestContents.CORSAIR, MathHelper.getRandomIntegerInRange(random, 6, 12));
        this.placeBarrel(world, random, -14, 5, 6, 4, LOTRFoods.CORSAIR_DRINK);
        this.placeWallBanner(world, -12, 8, 3, LOTRItemBanner.BannerType.UMBAR, 2);
        this.placeWallBanner(world, -12, 8, 7, LOTRItemBanner.BannerType.UMBAR, 0);
        this.placeWallBanner(world, -14, 8, 5, LOTRItemBanner.BannerType.UMBAR, 3);
        this.placeWallBanner(world, -10, 8, 5, LOTRItemBanner.BannerType.UMBAR, 1);
        this.placeWeaponRack(world, -7, 5, 8, 6, this.getRandomCorsairWeapon(random));
        this.placeWeaponRack(world, -6, 5, 8, 6, this.getRandomCorsairWeapon(random));
        this.placeWeaponRack(world, -5, 5, 8, 6, this.getRandomCorsairWeapon(random));
        if(random.nextInt(3) == 0) {
            this.placeTreasure(world, random, -14, 4, 2);
            this.placeTreasure(world, random, -14, 4, 1);
            this.placeTreasure(world, random, -13, 4, 1);
            this.placeTreasure(world, random, -12, 4, 1);
            this.placeTreasure(world, random, -12, 4, 0);
            this.placeTreasure(world, random, -11, 4, 0);
        }
        if(random.nextInt(3) == 0) {
            this.placeTreasure(world, random, -4, 4, 0);
            this.placeTreasure(world, random, -3, 5, 0);
            this.placeTreasure(world, random, -3, 4, 1);
            this.placeTreasure(world, random, -3, 4, 2);
            this.placeTreasure(world, random, -2, 4, 1);
        }
        for(i1 = -14; i1 <= -5; ++i1) {
            for(k1 = 0; k1 <= 8; ++k1) {
                j1 = 4;
                if(!this.isAir(world, i1, j1, k1) || !this.isOpaque(world, i1, j1 - 1, k1) || random.nextInt(20) != 0) continue;
                this.placeFoodOrDrink(world, random, i1, j1, k1);
            }
        }
        int corsairs = 2 + random.nextInt(2);
        for(int l = 0; l < corsairs; ++l) {
            LOTREntityCorsair corsair = new LOTREntityCorsair(world);
            this.spawnNPCAndSetHome(corsair, world, -9, 4, 4, 16);
        }
        LOTREntityCorsair captain = random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world);
        this.spawnNPCAndSetHome(captain, world, -9, 4, 4, 4);
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityCorsair.class);
        respawner.setCheckRanges(24, -16, 12, 8);
        respawner.setSpawnRanges(3, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, -9, 4, 4);
        LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
        this.spawnNPCAndSetHome(slave, world, -7, 7, 6, 8);
        for(int l = 0; l < 16; ++l) {
            LOTRTreeType tree = LOTRTreeType.PALM;
            WorldGenAbstractTree treeGen = tree.create(this.notifyChanges, random);
            if(treeGen == null) continue;
            int i13 = 2;
            int j13 = 6;
            int k13 = 7;
            if(treeGen.generate(world, random, this.getX(i13, k13), this.getY(j13), this.getZ(i13, k13))) break;
        }
        return true;
    }

    private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
        if(random.nextInt(3) != 0) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.CORSAIR_DRINK);
        }
        else {
            Block plateBlock = LOTRMod.woodPlateBlock;
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
            }
            else {
                this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.CORSAIR);
            }
        }
    }

    private void placeTreasure(World world, Random random, int i, int j, int k) {
        Block block = random.nextBoolean() ? LOTRMod.treasureGold : LOTRMod.treasureSilver;
        this.setBlockAndMetadata(world, i, j, k, block, random.nextInt(7));
    }
}
