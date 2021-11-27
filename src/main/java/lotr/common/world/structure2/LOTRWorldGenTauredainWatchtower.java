package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainWatchtower extends LOTRWorldGenTauredainHouse {
    public LOTRWorldGenTauredainWatchtower(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 2;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                for(int j1 = 7; j1 <= 13; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.placeWoodBase(world, -1, -1, false);
        this.placeWoodBase(world, 1, -1, true);
        this.placeWoodBase(world, -1, 1, false);
        this.placeWoodBase(world, 1, 1, false);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.woodBlock, this.woodMeta);
                    this.setBlockAndMetadata(world, i1, 9, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i1, 10, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i1, 11, k1, this.woodBlock, this.woodMeta);
                    this.setBlockAndMetadata(world, i1, 12, k1, this.woodBlock, this.woodMeta);
                }
                if(i2 == 1 && k2 % 2 == 0) {
                    if(i1 != 1 || k1 != -2) {
                        this.setBlockAndMetadata(world, i1, 7, k1, this.woodBlock, this.woodMeta | 8);
                    }
                    this.setBlockAndMetadata(world, i1, 11, k1, this.woodBlock, this.woodMeta | 8);
                }
                if(k2 == 1 && i2 % 2 == 0) {
                    this.setBlockAndMetadata(world, i1, 7, k1, this.woodBlock, this.woodMeta | 4);
                    this.setBlockAndMetadata(world, i1, 11, k1, this.woodBlock, this.woodMeta | 4);
                }
                if(i2 == 1 && k2 == 2 || k2 == 1 && i2 == 2) {
                    this.setBlockAndMetadata(world, i1, 12, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 == 0 && k2 == 2 || k2 == 0 && i2 == 2) {
                    this.setBlockAndMetadata(world, i1, 12, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 == 0 && k2 == 1 || k2 == 0 && i2 == 1) {
                    this.setBlockAndMetadata(world, i1, 12, k1, this.thatchBlock, this.thatchMeta);
                    this.setBlockAndMetadata(world, i1, 13, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 != 0 || k2 != 0) continue;
                this.setBlockAndMetadata(world, i1, 12, k1, this.thatchBlock, this.thatchMeta);
                this.setBlockAndMetadata(world, i1, 13, k1, this.thatchBlock, this.thatchMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 7, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 0, 7, -2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 0, 11, 0, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 8, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 8, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 8, 1, this.fenceBlock, this.fenceMeta);
        return true;
    }

    private void placeWoodBase(World world, int i, int k, boolean ladder) {
        for(int j = 7; (((j == 7) || !this.isOpaque(world, i, j, k)) && (this.getY(j) >= 0)); --j) {
            if(ladder) {
                this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta);
                if(!this.isOpaque(world, i, j - 1, k)) {
                    this.setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
                }
            }
            else if(j >= 6) {
                this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta);
            }
            else if(this.isOpaque(world, i, j - 1, k)) {
                this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta);
            }
            else {
                this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
            }
            this.setGrassToDirt(world, i, j - 1, k);
        }
    }
}
