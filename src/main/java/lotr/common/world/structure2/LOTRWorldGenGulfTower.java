package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTower extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfTower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
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
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                for(j1 = 1; j1 <= 16; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("gulf_tower");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
        this.associateBlockMetaAlias("WOOD|8", this.woodBlock, this.woodMeta | 8);
        this.associateBlockMetaAlias("WOOD|12", this.woodBlock, this.woodMeta | 0xC);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeChest(world, random, -2, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.GULF_HOUSE);
        this.placeSkull(world, random, 2, 2, 1);
        this.placeBarrel(world, random, -2, 2, -1, 4, LOTRFoods.GULF_HARAD_DRINK);
        this.placeMug(world, random, 2, 2, -1, 2, LOTRFoods.GULF_HARAD_DRINK);
        this.placePlate(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placePlate(world, random, -2, 2, 1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placeWallBanner(world, 0, 8, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 0, 8, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        int warriors = 1 + random.nextInt(2);
        for(int l = 0; l < warriors; ++l) {
            LOTREntityGulfHaradWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 14, 0, 8);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
        respawner.setCheckRanges(6, -20, 4, 4);
        respawner.setSpawnRanges(1, -2, 1, 8);
        this.placeNPCRespawner(respawner, world, 0, 14, 0);
        return true;
    }
}
