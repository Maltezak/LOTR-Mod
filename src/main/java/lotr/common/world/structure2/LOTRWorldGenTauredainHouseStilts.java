package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseStilts extends LOTRWorldGenTauredainHouse {
    public LOTRWorldGenTauredainHouseStilts(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 5;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int k2;
        int i1;
        int i2;
        int i12;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        this.bedBlock = LOTRMod.strawBed;
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -3; k1 <= 3; ++k1) {
                for(int j1 = 3; j1 <= 7; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
            }
        }
        this.placeStilt(world, -3, -3, false);
        this.placeStilt(world, 3, -3, false);
        this.placeStilt(world, -3, 3, false);
        this.placeStilt(world, 3, 3, false);
        this.placeStilt(world, 0, -4, true);
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -3; k1 <= 3; ++k1) {
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                if(i2 == 3 && k2 == 3) {
                    for(int j1 = 3; j1 <= 7; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.woodBlock, this.woodMeta);
                    }
                }
                if(i2 == 3 && k2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 3, k1, this.woodBlock, this.woodMeta | 8);
                    this.setBlockAndMetadata(world, i12, 4, k1, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i12, 6, k1, this.woodBlock, this.woodMeta | 8);
                }
                if(k2 == 3 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 3, k1, this.woodBlock, this.woodMeta | 4);
                    this.setBlockAndMetadata(world, i12, 4, k1, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i12, 6, k1, this.woodBlock, this.woodMeta | 4);
                }
                if(i2 <= 2 && k2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 3, k1, this.plankBlock, this.plankMeta);
                    if(random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i12, 4, k1, LOTRMod.thatchFloor, 0);
                    }
                }
                if(i12 == -3 && k2 == 1) {
                    this.setBlockAndMetadata(world, i12, 4, k1, this.plankStairBlock, 1);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.fenceBlock, this.fenceMeta);
                }
                if(i12 == 3 && k2 == 1) {
                    this.setBlockAndMetadata(world, i12, 4, k1, this.plankStairBlock, 0);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.fenceBlock, this.fenceMeta);
                }
                if(k1 != 3 || i2 != 1) continue;
                this.setBlockAndMetadata(world, i12, 4, k1, this.plankStairBlock, 3);
                this.setBlockAndMetadata(world, i12, 5, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        int[] i13 = new int[] {3, 6};
        k1 = i13.length;
        for(i2 = 0; i2 < k1; ++i2) {
            int j1 = i13[i2];
            this.setBlockAndMetadata(world, -4, j1, -3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, 4, j1, -3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, -4, j1, 3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, 4, j1, 3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, -3, j1, -4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, -3, j1, 4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 3, j1, -4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 3, j1, 4, this.woodBlock, this.woodMeta | 8);
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 4 && k2 == 2 || k2 == 4 && i2 == 2) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                }
                if(i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
                    this.setBlockAndMetadata(world, i1, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 7, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                    if(i2 == 2 && k2 == 2) {
                        this.setBlockAndMetadata(world, i1, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, 7, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i2 == 1 && k2 <= 1 || k2 == 1 && i2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 != 0 || k2 != 0) continue;
                this.setBlockAndMetadata(world, i1, 9, k1, this.thatchSlabBlock, this.thatchSlabMeta);
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, -4, this.plankBlock, this.plankMeta);
            if(i1 == 0) continue;
            this.setBlockAndMetadata(world, i1, 3, -5, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 4, -5, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 4, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 4, -4, this.fenceBlock, this.fenceMeta);
        this.setAir(world, 0, 4, -3);
        this.setAir(world, 0, 5, -3);
        this.setBlockAndMetadata(world, 0, 6, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 6, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 6, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 6, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 4, -2, this.woodBlock, this.woodMeta);
        this.placeChest(world, random, -2, 5, -2, 3, LOTRChestContents.TAUREDAIN_HOUSE);
        this.setBlockAndMetadata(world, 2, 4, -2, this.woodBlock, this.woodMeta);
        this.placeBarrel(world, random, 2, 5, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
        for(int i14 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i14, 4, 1, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i14, 4, 2, this.bedBlock, 8);
        }
        this.setBlockAndMetadata(world, -1, 4, 2, LOTRMod.tauredainTable, 0);
        this.setBlockAndMetadata(world, 0, 4, 2, this.woodBlock, this.woodMeta);
        this.placeMug(world, random, 0, 5, 2, 0, LOTRFoods.TAUREDAIN_DRINK);
        this.setBlockAndMetadata(world, 1, 4, 2, Blocks.crafting_table, 0);
        this.placeTauredainTorch(world, -1, 5, -5);
        this.placeTauredainTorch(world, 1, 5, -5);
        LOTREntityTauredainShaman shaman = new LOTREntityTauredainShaman(world);
        this.spawnNPCAndSetHome(shaman, world, 0, 4, 0, 2);
        return true;
    }

    private void placeStilt(World world, int i, int k, boolean ladder) {
        for(int j = 3; (((j == 3) || !this.isOpaque(world, i, j, k)) && (this.getY(j) >= 0)); --j) {
            this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta);
            this.setGrassToDirt(world, i, j - 1, k);
            if(!ladder) continue;
            this.setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
        }
    }
}
