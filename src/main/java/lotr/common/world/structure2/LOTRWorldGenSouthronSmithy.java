package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronSmithy extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5, -1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -4; i1 <= 4; ++i1) {
                for(int k1 = -5; k1 <= 13; ++k1) {
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
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -4; i1 <= 4; ++i1) {
            for(int k1 = -5; k1 <= 13; ++k1) {
                int i2 = Math.abs(i1);
                if((i2 > 3 || k1 < -4 || k1 > 6) && (i2 > 4 || k1 < 7 || k1 > 12)) continue;
                int j1 = 0;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_smithy");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, -1, 5, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -2, 5, -2, this.bedBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -3, this.bedBlock, 10);
        this.setBlockAndMetadata(world, -2, 5, -3, this.bedBlock, 10);
        this.placeChest(world, random, 3, 1, 6, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeChest(world, random, 2, 5, -3, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placePlateWithCertainty(world, random, -1, 6, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
        this.placeMug(world, random, -2, 6, 3, 0, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, -3, 1, 8, 1, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 3, 1, 8, 3, this.getRandomHaradWeapon(random));
        LOTREntityNearHaradrimBase smith = this.createSmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, 1, 6, 8);
        return true;
    }

    protected LOTREntityNearHaradrimBase createSmith(World world) {
        if(this.isUmbar()) {
            return new LOTREntityUmbarBlacksmith(world);
        }
        return new LOTREntityNearHaradBlacksmith(world);
    }
}
