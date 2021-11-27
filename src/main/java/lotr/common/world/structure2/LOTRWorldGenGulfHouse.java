package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenGulfHouse extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -8; i1 <= 8; ++i1) {
                for(int k1 = -8; k1 <= 8; ++k1) {
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
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(int i1 = -8; i1 <= 8; ++i1) {
            for(int k1 = -8; k1 <= 8; ++k1) {
                int k2;
                int i2 = Math.abs(i1);
                if(i2 * i2 + (k2 = Math.abs(k1)) * k2 >= 64) continue;
                for(j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("gulf_house");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockMetaAlias("PLANK2_SLAB", this.plank2SlabBlock, this.plank2SlabMeta);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 1, 5, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 6, this.bedBlock, 8);
        this.placeChest(world, random, 6, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.GULF_HOUSE);
        for(int k1 : new int[] {-2, 0, 2}) {
            int i1 = -6;
            int j12 = 2;
            if(random.nextBoolean()) {
                this.placePlate(world, random, i1, j12, k1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
                continue;
            }
            this.placeMug(world, random, i1, j12, k1, 3, LOTRFoods.GULF_HARAD_DRINK);
        }
        int men = 1 + random.nextInt(2);
        for(int l = 0; l < men; ++l) {
            LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
            this.spawnNPCAndSetHome(gulfman, world, 0, 0, 0, 16);
        }
        return true;
    }
}
