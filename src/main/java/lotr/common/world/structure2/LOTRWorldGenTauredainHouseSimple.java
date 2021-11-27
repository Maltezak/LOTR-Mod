package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseSimple extends LOTRWorldGenTauredainHouse {
    public LOTRWorldGenTauredainHouseSimple(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 4;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            int range = 4;
            for(int i1 = -range; i1 <= range; ++i1) {
                for(int k1 = -range; k1 <= range; ++k1) {
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
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(int i1 = -3; i1 <= 3; ++i1) {
            for(int k1 = -3; k1 <= 3; ++k1) {
                for(int j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("taurethrim_house");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.thatchBlock, this.thatchMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.thatchSlabBlock, this.thatchSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.thatchSlabBlock, this.thatchSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.thatchStairBlock);
        this.associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
        this.addBlockMetaAliasOption("GROUND", 10, this.floorBlock, this.floorMeta);
        this.addBlockMetaAliasOption("GROUND", 10, LOTRMod.mud, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -2, 1, 2, this.bedBlock, 8);
        this.setBlockAndMetadata(world, 2, 1, 1, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 2, 1, 2, this.bedBlock, 8);
        this.placeChest(world, random, 0, 1, 2, LOTRMod.chestBasket, 2, LOTRChestContents.TAUREDAIN_HOUSE);
        this.placeTauredainFlowerPot(world, -2, 2, 0, random);
        this.placeTauredainFlowerPot(world, -1, 2, 2, random);
        this.placeTauredainFlowerPot(world, 0, 4, -2, random);
        this.placeTauredainFlowerPot(world, 0, 4, 2, random);
        this.placePlateWithCertainty(world, random, 1, 2, 2, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
        this.placeMug(world, random, 2, 2, 0, 3, LOTRFoods.TAUREDAIN_DRINK);
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityTauredain tauredain = new LOTREntityTauredain(world);
            this.spawnNPCAndSetHome(tauredain, world, 0, 1, 0, 8);
        }
        return true;
    }
}
