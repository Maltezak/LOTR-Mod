package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortress extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronFortress(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 15);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -21; i1 <= 21; ++i1) {
                for(int k1 = -15; k1 <= 15; ++k1) {
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
        for(int i1 = -21; i1 <= 21; ++i1) {
            for(int k1 = -15; k1 <= 15; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 <= 17 && k2 <= 10) {
                    for(j1 = 1; j1 <= 8; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                    continue;
                }
                if(i2 >= 15 && i2 <= 21 && k2 >= 9 && k2 <= 15) {
                    for(j1 = 1; j1 <= 9; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                    continue;
                }
                if(i2 > 2 || k1 > -10 || k1 < -15) continue;
                for(j1 = 1; j1 <= 12; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_fort");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockAlias("STONE_STAIR", this.stoneStairBlock);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
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
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.addBlockMetaAliasOption("GROUND", 5, Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.sand, 1);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -5, 8, -13, this.bannerType, 2);
        this.placeWallBanner(world, 5, 8, -13, this.bannerType, 2);
        for(int k1 : new int[] {4, 6, 8}) {
            this.setBlockAndMetadata(world, -6, 1, k1, this.bedBlock, 1);
            this.setBlockAndMetadata(world, -5, 1, k1, this.bedBlock, 9);
            this.setBlockAndMetadata(world, -12, 1, k1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, -13, 1, k1, this.bedBlock, 11);
            this.setBlockAndMetadata(world, 6, 1, k1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, 5, 1, k1, this.bedBlock, 11);
            this.setBlockAndMetadata(world, 12, 1, k1, this.bedBlock, 1);
            this.setBlockAndMetadata(world, 13, 1, k1, this.bedBlock, 9);
        }
        this.setBlockAndMetadata(world, 0, 1, 9, Blocks.bed, 3);
        this.setBlockAndMetadata(world, -1, 1, 9, Blocks.bed, 11);
        this.setBlockAndMetadata(world, 0, 1, 10, Blocks.bed, 3);
        this.setBlockAndMetadata(world, -1, 1, 10, Blocks.bed, 11);
        this.placeWeaponRack(world, -14, 2, -6, 6, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -13, 2, -5, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -15, 2, -5, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, -4, 4, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, -2, 6, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -13, 2, -1, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -15, 2, -1, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, 0, 4, this.getRandomHaradWeapon(random));
        this.placeBarrel(world, random, 3, 2, 4, 5, LOTRFoods.SOUTHRON_DRINK);
        this.placeMug(world, random, 3, 2, 5, 1, LOTRFoods.SOUTHRON_DRINK);
        this.placeChest(world, random, -1, 1, 8, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
        this.setBlockAndMetadata(world, -5, 1, 1, LOTRMod.commandTable, 0);
        int warriors = 5 + random.nextInt(5);
        for(int l = 0; l < warriors; ++l) {
            LOTREntityNearHaradrimBase warrior = this.createWarrior(world, random);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, 0, 24);
        }
        LOTREntityNearHaradrimBase captain = this.createCaptain(world, random);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 4, 8);
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        this.setSpawnClasses(respawner);
        respawner.setCheckRanges(24, -8, 20, 16);
        respawner.setSpawnRanges(12, -4, 6, 24);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    protected LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
        return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }

    protected LOTREntityNearHaradrimBase createCaptain(World world, Random random) {
        return new LOTREntityNearHaradrimWarlord(world);
    }

    protected void setSpawnClasses(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
    }
}
