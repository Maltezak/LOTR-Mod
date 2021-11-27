package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfWarCamp extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfWarCamp(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 15);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -16; i12 <= 16; ++i12) {
                for(int k1 = -16; k1 <= 16; ++k1) {
                    j1 = this.getTopBlock(world, i12, k1) - 1;
                    if(!this.isSurface(world, i12, j1, k1)) {
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
        for(i1 = -15; i1 <= 15; ++i1) {
            for(int k1 = -15; k1 <= 15; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= -1) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    if(j1 == 0) {
                        if(i2 <= 14 && k2 <= 14) {
                            if(random.nextBoolean()) {
                                this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                            }
                            else {
                                int randomGround = random.nextInt(3);
                                if(randomGround == 0) {
                                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
                                }
                                else if(randomGround == 1) {
                                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 1);
                                }
                                else if(randomGround == 2) {
                                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.sand, 1);
                                }
                            }
                        }
                        else {
                            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
                    }
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                int airHeight = 6;
                if(i2 <= 4 && k2 <= 4) {
                    airHeight = 15;
                }
                for(int j12 = 1; j12 <= airHeight; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
                if(i2 > 12 || k2 > 12 || random.nextInt(5) != 0) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        this.loadStrScan("gulf_war_camp");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        for(i1 = -13; i1 <= -9; i1 += 2) {
            this.setBlockAndMetadata(world, i1, 1, 12, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i1, 1, 13, this.bedBlock, 8);
        }
        for(i1 = 9; i1 <= 13; i1 += 2) {
            this.setBlockAndMetadata(world, i1, 1, 12, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i1, 1, 13, this.bedBlock, 8);
        }
        this.placeChest(world, random, -12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, -10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, 10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, 12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeChest(world, random, -1, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
        this.placeGulfArmor(world, random, -11, 1, -13, 2);
        this.placeGulfArmor(world, random, -9, 1, -13, 2);
        this.placeGulfArmor(world, random, -13, 1, -11, 3);
        this.placeGulfArmor(world, random, -13, 1, -9, 3);
        this.placeGulfArmor(world, random, 9, 1, -13, 2);
        this.placeGulfArmor(world, random, 11, 1, -13, 2);
        this.placeGulfArmor(world, random, 13, 1, -11, 1);
        this.placeGulfArmor(world, random, 13, 1, -9, 1);
        this.placeWeaponRack(world, -8, 2, -9, 6, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -9, 2, -8, 7, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -7, 2, -8, 5, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, -8, 2, -7, 4, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 8, 2, -9, 6, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 7, 2, -8, 7, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 9, 2, -8, 5, this.getRandomGulfWeapon(random));
        this.placeWeaponRack(world, 8, 2, -7, 4, this.getRandomGulfWeapon(random));
        this.placeSkull(world, random, -12, 3, -2);
        this.placeSkull(world, random, -12, 3, 2);
        this.placeWeaponRack(world, 11, 2, -4, 7, new ItemStack(LOTRMod.nearHaradBow));
        this.placeWeaponRack(world, 11, 2, 4, 7, new ItemStack(LOTRMod.nearHaradBow));
        this.placeBarrel(world, random, -13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
        this.placeBarrel(world, random, 13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
        this.placeWallBanner(world, 0, 6, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        this.placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        for(int i13 : new int[] {-2, 2}) {
            j1 = 1;
            int k1 = 12;
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, i13, j1, k1, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
            this.leashEntityTo(horse, world, i13, j1, k1);
        }
        LOTREntityGulfHaradWarlord warlord = new LOTREntityGulfHaradWarlord(world);
        warlord.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(warlord, world, 0, 9, -3, 6);
        this.setBlockAndMetadata(world, 0, 9, 3, LOTRMod.commandTable, 0);
        int warriors = 6;
        for(int l = 0; l < warriors; ++l) {
            LOTREntityGulfHaradWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, -1, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
        respawner.setCheckRanges(32, -8, 12, 24);
        respawner.setSpawnRanges(24, -4, 6, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    private void placeGulfArmor(World world, Random random, int i, int j, int k, int meta) {
        ItemStack[] armor = random.nextInt(3) != 0 ? new ItemStack[] {null, null, null, null} : new ItemStack[] {new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad)};
        this.placeArmorStand(world, i, j, k, meta, armor);
    }
}
