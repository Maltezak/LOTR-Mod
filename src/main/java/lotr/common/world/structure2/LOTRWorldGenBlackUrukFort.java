package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBlackUrukFort extends LOTRWorldGenMordorStructure {
    public LOTRWorldGenBlackUrukFort(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 19);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -18; i1 <= 26; ++i1) {
                for(int k1 = -18; k1 <= 20; ++k1) {
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
                    if(maxHeight - minHeight <= 16) continue;
                    return false;
                }
            }
        }
        for(int i1 = -17; i1 <= 25; ++i1) {
            for(int k1 = -18; k1 <= 19; ++k1) {
                for(int j1 = 1; j1 <= 16; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("black_uruk_fort");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("BRICK_CARVED", this.brickCarvedBlock, this.brickCarvedMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("SMOOTH", this.smoothBlock, this.smoothMeta);
        this.associateBlockMetaAlias("SMOOTH_SLAB", this.smoothSlabBlock, this.smoothSlabMeta);
        this.associateBlockMetaAlias("TILE", this.tileBlock, this.tileMeta);
        this.associateBlockMetaAlias("TILE_SLAB", this.tileSlabBlock, this.tileSlabMeta);
        this.associateBlockMetaAlias("TILE_SLAB_INV", this.tileSlabBlock, this.tileSlabMeta | 8);
        this.associateBlockAlias("TILE_STAIR", this.tileStairBlock);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
        this.addBlockMetaAliasOption("GROUND", 6, LOTRMod.rock, 0);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.mordorDirt, 0);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.mordorGravel, 0);
        this.associateBlockAlias("GATE_IRON", this.gateIronBlock);
        this.associateBlockAlias("GATE_ORC", this.gateOrcBlock);
        this.associateBlockMetaAlias("BARS", this.barsBlock, 0);
        this.associateBlockMetaAlias("CHANDELIER", this.chandelierBlock, this.chandelierMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -2, 5, -17, LOTRItemBanner.BannerType.BLACK_URUK, 2);
        this.placeWallBanner(world, 2, 5, -17, LOTRItemBanner.BannerType.BLACK_URUK, 2);
        this.placeWallBanner(world, 0, 14, -20, LOTRItemBanner.BannerType.MORDOR, 2);
        this.setBlockAndMetadata(world, -2, 11, -15, LOTRMod.commandTable, 0);
        this.placeOrcTorch(world, -3, 2, -18);
        this.placeOrcTorch(world, 3, 2, -18);
        this.placeOrcTorch(world, 19, 2, -11);
        this.placeOrcTorch(world, -3, 2, -10);
        this.placeOrcTorch(world, 3, 2, -10);
        this.placeOrcTorch(world, 2, 2, 4);
        this.placeOrcTorch(world, 5, 2, 4);
        this.placeOrcTorch(world, -11, 2, 12);
        this.placeOrcTorch(world, 18, 4, -13);
        this.placeOrcTorch(world, 10, 4, -13);
        this.placeOrcTorch(world, 21, 4, -10);
        this.placeOrcTorch(world, 21, 4, -2);
        this.placeOrcTorch(world, 24, 4, 1);
        this.placeOrcTorch(world, 20, 4, 1);
        this.placeOrcTorch(world, -13, 4, 3);
        this.placeOrcTorch(world, 5, 4, 9);
        this.placeOrcTorch(world, 2, 4, 9);
        this.placeOrcTorch(world, -13, 4, 11);
        this.placeOrcTorch(world, 11, 4, 14);
        this.placeOrcTorch(world, -4, 4, 14);
        this.placeOrcTorch(world, -10, 4, 14);
        this.placeOrcTorch(world, 20, 4, 17);
        this.placeOrcTorch(world, 22, 10, -14);
        this.placeOrcTorch(world, 22, 15, -14);
        this.placeWeaponRack(world, 9, 2, 2, 6, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 2, 3, 5, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 8, 2, 3, 7, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 9, 2, 4, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 15, 2, 7, 6, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 16, 2, 8, 5, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 14, 2, 8, 7, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 15, 2, 9, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 6, 5, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 1, 5, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 5, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, -3, 5, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 6, 6, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 1, 6, 10, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, 10, 6, 12, 4, this.getRandomUrukWeapon(random));
        this.placeWeaponRack(world, -3, 6, 12, 4, this.getRandomUrukWeapon(random));
        this.placeArmorStand(world, 15, 1, -9, 2, new ItemStack[] {new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondorGambeson), null, null});
        this.placeUrukArmor(world, random, 8, 4, 10, 2);
        this.placeUrukArmor(world, random, -1, 4, 10, 2);
        this.placeUrukArmor(world, random, 7, 4, 12, 3);
        this.placeUrukArmor(world, random, 0, 4, 12, 1);
        this.placeUrukArmor(world, random, 6, 4, 13, 2);
        this.placeUrukArmor(world, random, 1, 4, 13, 2);
        this.placeChest(world, random, 15, 1, 7, 2, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 9, 4, 11, 3, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -2, 4, 11, 3, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 12, 4, 13, 5, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -5, 4, 13, 4, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, 12, 4, 17, 5, LOTRChestContents.BLACK_URUK_FORT);
        this.placeChest(world, random, -5, 4, 17, 4, LOTRChestContents.BLACK_URUK_FORT);
        for(int j1 = 4; j1 <= 5; ++j1) {
            for(int i1 : new int[] {-3, -1, 1}) {
                this.setBlockAndMetadata(world, i1, j1, 17, this.bedBlock, 0);
                this.setBlockAndMetadata(world, i1, j1, 18, this.bedBlock, 8);
            }
            for(int i1 : new int[] {6, 8, 10}) {
                this.setBlockAndMetadata(world, i1, j1, 17, this.bedBlock, 0);
                this.setBlockAndMetadata(world, i1, j1, 18, this.bedBlock, 8);
            }
        }
        this.placeBarrel(world, random, -11, 5, -3, 3, LOTRFoods.ORC_DRINK);
        this.placeBarrel(world, random, -13, 5, -3, 3, LOTRFoods.ORC_DRINK);
        for(int k1 = -7; k1 <= -4; ++k1) {
            for(int i1 : new int[] {-13, -11}) {
                if(random.nextBoolean()) {
                    this.placePlate(world, random, i1, 5, k1, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
                    continue;
                }
                this.placeMug(world, random, i1, 5, k1, random.nextInt(4), LOTRFoods.ORC_DRINK);
            }
        }
        LOTREntityBlackUrukCaptain captain = new LOTREntityBlackUrukCaptain(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 8);
        int uruks = 12;
        for(int l = 0; l < uruks; ++l) {
            LOTREntityBlackUruk uruk = random.nextInt(3) == 0 ? new LOTREntityBlackUrukArcher(world) : new LOTREntityBlackUruk(world);
            uruk.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(uruk, world, 0, 1, 0, 32);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityBlackUruk.class, LOTREntityBlackUrukArcher.class);
        respawner.setCheckRanges(32, -16, 20, 24);
        respawner.setSpawnRanges(24, -4, 8, 24);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    protected ItemStack getRandomUrukWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.scimitarBlackUruk), new ItemStack(LOTRMod.daggerBlackUruk), new ItemStack(LOTRMod.daggerBlackUrukPoisoned), new ItemStack(LOTRMod.spearBlackUruk), new ItemStack(LOTRMod.battleaxeBlackUruk), new ItemStack(LOTRMod.hammerBlackUruk), new ItemStack(LOTRMod.blackUrukBow)};
        return items[random.nextInt(items.length)].copy();
    }

    private void placeUrukArmor(World world, Random random, int i, int j, int k, int meta) {
        ItemStack[] armor = random.nextInt(4) != 0 ? new ItemStack[] {null, null, null, null} : new ItemStack[] {new ItemStack(LOTRMod.helmetBlackUruk), new ItemStack(LOTRMod.bodyBlackUruk), new ItemStack(LOTRMod.legsBlackUruk), new ItemStack(LOTRMod.bootsBlackUruk)};
        this.placeArmorStand(world, i, j, k, meta, armor);
    }
}
