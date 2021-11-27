package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenIthilienHideout extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenIthilienHideout(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int ladderY;
        int k1;
        int i1;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        int width = 5;
        int height = 4;
        int baseY = -(height + 2 + random.nextInt(4));
        if(this.restrictions) {
            if(!this.isSurface(world, 0, -1, 0)) {
                return false;
            }
            for(i12 = -width; i12 <= width; ++i12) {
                for(k1 = -width; k1 <= width; ++k1) {
                    for(int j1 = baseY; j1 <= baseY + height + 2; ++j1) {
                        if(this.isOpaque(world, i12, j1, k1)) continue;
                        return false;
                    }
                }
            }
        }
        for(i12 = -width - 1; i12 <= width + 1; ++i12) {
            for(k1 = -width - 1; k1 <= width + 1; ++k1) {
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k1);
                boolean withinWalls = i2 <= width && k2 <= width;
                this.setBlockAndMetadata(world, i12, baseY, k1, Blocks.stone, 0);
                this.setBlockAndMetadata(world, i12, baseY + height + 1, k1, Blocks.stone, 0);
                for(int j1 = baseY + 1; j1 <= baseY + height; ++j1) {
                    if(withinWalls) {
                        this.setAir(world, i12, j1, k1);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i12, j1, k1, Blocks.stone, 0);
                }
                if(!withinWalls) continue;
                if(i2 <= 2 && k2 <= 2 || random.nextInt(3) == 0) {
                    this.setBlockAndMetadata(world, i12, baseY + 1, k1, LOTRMod.thatchFloor, 0);
                }
                if(i2 != width && k2 != width) continue;
                this.setBlockAndMetadata(world, i12, baseY + 1, k1, LOTRMod.planks, 8);
            }
        }
        for(ladderY = baseY + 1; ladderY <= baseY + height || this.isOpaque(world, 0, ladderY, 0) || this.isOpaque(world, -1, ladderY, 0) && this.isOpaque(world, 1, ladderY, 0) && this.isOpaque(world, 0, ladderY, -1) && this.isOpaque(world, 0, ladderY, 1); ++ladderY) {
            if(!this.isOpaque(world, 0, ladderY, -1)) {
                this.setBlockAndMetadata(world, 0, ladderY, -1, Blocks.stone, 0);
            }
            this.setBlockAndMetadata(world, 0, ladderY, 0, Blocks.ladder, 3);
        }
        for(int pass = 0; pass <= 1; ++pass) {
            for(i1 = -1; i1 <= 1; ++i1) {
                block9: for(int k12 = -1; k12 <= 1; ++k12) {
                    int i2 = Math.abs(i1);
                    int k2 = Math.abs(k12);
                    if(i1 == 0 && k12 == 0) continue;
                    if(pass == 0 && i1 == 0 && k12 == 1) {
                        for(int j1 = 0; j1 <= 3; ++j1) {
                            int j2 = ladderY + j1;
                            if(LOTRTreeType.OAK_ITHILIEN_HIDEOUT.create(this.notifyChanges, random).generate(world, random, this.getX(i1, k12), this.getY(j2), this.getZ(i1, k12))) break;
                        }
                    }
                    if(pass != 1) continue;
                    boolean doublegrass = i2 != k2;
                    for(int j1 = -3; j1 <= 3; ++j1) {
                        int j2 = ladderY + j1;
                        Block below = this.getBlock(world, i1, j2 - 1, k12);
                        if(below != Blocks.grass && below != Blocks.dirt || this.isOpaque(world, i1, j2, k12) || this.isOpaque(world, i1, j2 + 1, k12)) continue;
                        if(doublegrass) {
                            this.setBlockAndMetadata(world, i1, j2, k12, Blocks.double_plant, 2);
                            this.setBlockAndMetadata(world, i1, j2 + 1, k12, Blocks.double_plant, 8);
                            continue block9;
                        }
                        this.setBlockAndMetadata(world, i1, j2, k12, Blocks.tallgrass, 1);
                        continue block9;
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, -width, baseY + 3, -width, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -width, baseY + 3, width, Blocks.torch, 2);
        this.setBlockAndMetadata(world, width, baseY + 3, -width, Blocks.torch, 1);
        this.setBlockAndMetadata(world, width, baseY + 3, width, Blocks.torch, 1);
        this.placeWallBanner(world, -width - 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 1);
        this.placeWallBanner(world, 0, baseY + 4, -width - 1, LOTRItemBanner.BannerType.ITHILIEN, 0);
        this.placeWallBanner(world, width + 1, baseY + 4, 0, LOTRItemBanner.BannerType.ITHILIEN, 3);
        this.placeWallBanner(world, -2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
        this.placeWallBanner(world, 0, baseY + 4, width + 1, LOTRItemBanner.BannerType.GONDOR, 2);
        this.placeWallBanner(world, 2, baseY + 4, width + 1, LOTRItemBanner.BannerType.ITHILIEN, 2);
        this.setBlockAndMetadata(world, -2, baseY + 1, width, LOTRMod.gondorianTable, 0);
        this.setBlockAndMetadata(world, 0, baseY + 1, width, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, baseY + 1, width, Blocks.furnace, 2);
        this.placeChest(world, random, width, baseY + 1, 0, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
        ItemStack drink = LOTRFoods.GONDOR_DRINK.getRandomBrewableDrink(random);
        this.placeBarrel(world, random, width, baseY + 2, -3, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, -2, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, 2, 5, drink);
        this.placeBarrel(world, random, width, baseY + 2, 3, 5, drink);
        for(i1 = -3; i1 <= 3; i1 += 2) {
            this.setBlockAndMetadata(world, i1, baseY + 1, -width + 1, LOTRMod.strawBed, 2);
            this.setBlockAndMetadata(world, i1, baseY + 1, -width, LOTRMod.strawBed, 10);
        }
        this.placeChest(world, random, -width, baseY + 1, 0, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
        ItemStack[] rangerArmor = new ItemStack[] {new ItemStack(LOTRMod.helmetRangerIthilien), new ItemStack(LOTRMod.bodyRangerIthilien), new ItemStack(LOTRMod.legsRangerIthilien), new ItemStack(LOTRMod.bootsRangerIthilien)};
        this.placeArmorStand(world, -width, baseY + 2, -2, 3, rangerArmor);
        this.placeArmorStand(world, -width, baseY + 2, 2, 3, rangerArmor);
        int rangers = 2 + random.nextInt(3);
        for(int l = 0; l < rangers; ++l) {
            LOTREntityRangerIthilien ranger = new LOTREntityRangerIthilien(world);
            this.spawnNPCAndSetHome(ranger, world, -2, baseY + 1, -2, 16);
        }
        this.spawnNPCAndSetHome(new LOTREntityRangerIthilienCaptain(world), world, -2, baseY + 1, -2, 16);
        return true;
    }
}
