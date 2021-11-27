package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTower extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronTower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                for(j1 = 1; j1 <= 15; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_tower");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeChest(world, random, -1, 1, -1, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
        this.placeMug(world, random, -1, 2, 1, 0, LOTRFoods.SOUTHRON_DRINK);
        this.placeBarrel(world, random, 1, 2, 1, 2, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, -1, 8, 0, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 1, 8, 0, 7, this.getRandomHaradWeapon(random));
        this.placeWallBanner(world, 0, 14, -3, this.bannerType, 2);
        this.placeWallBanner(world, -3, 14, 0, this.bannerType, 3);
        this.placeWallBanner(world, 0, 14, 3, this.bannerType, 0);
        this.placeWallBanner(world, 3, 14, 0, this.bannerType, 1);
        int warriors = 2;
        for(int l = 0; l < warriors; ++l) {
            LOTREntityNearHaradrimBase warrior = this.createWarrior(world, random);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 15, 0, 8);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        this.setSpawnClasses(respawner);
        respawner.setCheckRanges(8, -4, 20, 8);
        respawner.setSpawnRanges(2, -1, 16, 8);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    protected LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
        return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }

    protected void setSpawnClasses(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
    }
}
