package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanWatchtower extends LOTRWorldGenRohanStructure {
    public LOTRWorldGenRohanWatchtower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j12;
        int k1;
        int k12;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int height = 7 + random.nextInt(3);
        this.originY += height;
        if(this.restrictions) {
            for(int i12 = -4; i12 <= 4; ++i12) {
                for(k12 = -4; k12 <= 4; ++k12) {
                    j12 = this.getTopBlock(world, i12, k12) - 1;
                    if(this.isSurface(world, i12, j12, k12)) continue;
                    return false;
                }
            }
        }
        int[] i12 = new int[] {-3, 3};
        k12 = i12.length;
        for(j12 = 0; j12 < k12; ++j12) {
            int i13 = i12[j12];
            for(int k13 : new int[] {-3, 3}) {
                int j13 = 3;
                while(!this.isOpaque(world, i13, j13, k13) && this.getY(j13) >= 0) {
                    this.setBlockAndMetadata(world, i13, j13, k13, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i13, j13 - 1, k13);
                    --j13;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = -2; k12 <= 2; ++k12) {
                this.setBlockAndMetadata(world, i1, 0, k12, this.plankBlock, this.plankMeta);
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k12 = -3; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, i1, 4, k12, this.plankBlock, this.plankMeta);
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, -3, this.logBlock, this.logMeta | 4);
            this.setBlockAndMetadata(world, i1, 0, 3, this.logBlock, this.logMeta | 4);
            this.setBlockAndMetadata(world, i1, 4, -3, this.logBlock, this.logMeta | 4);
            this.setBlockAndMetadata(world, i1, 4, 3, this.logBlock, this.logMeta | 4);
            this.setBlockAndMetadata(world, i1, 0, -4, this.plankStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 0, 4, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 1, -4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 1, 4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 3, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 3, 3, this.fenceBlock, this.fenceMeta);
        }
        for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, -3, 0, k1, this.logBlock, this.logMeta | 8);
            this.setBlockAndMetadata(world, 3, 0, k1, this.logBlock, this.logMeta | 8);
            this.setBlockAndMetadata(world, -3, 4, k1, this.logBlock, this.logMeta | 8);
            this.setBlockAndMetadata(world, 3, 4, k1, this.logBlock, this.logMeta | 8);
            this.setBlockAndMetadata(world, -4, 0, k1, this.plankStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 0, k1, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, -4, 1, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 4, 1, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -3, 3, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 3, 3, k1, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -3, 1, -2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -3, 1, 2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 3, 1, -2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 3, 1, 2, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, -3, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, -3, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, 3, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, 3, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -3, 2, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 2, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 2, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 2, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 4, 0, this.logBlock, this.logMeta | 8);
        this.setBlockAndMetadata(world, 1, 4, 0, this.logBlock, this.logMeta | 8);
        this.setBlockAndMetadata(world, 0, 4, -1, this.logBlock, this.logMeta | 4);
        this.setBlockAndMetadata(world, 0, 4, 1, this.logBlock, this.logMeta | 4);
        for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -4, this.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 4, 4, this.plankStairBlock, 3);
        }
        for(k1 = -4; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, -4, 4, k1, this.plankStairBlock, 1);
            this.setBlockAndMetadata(world, 4, 4, k1, this.plankStairBlock, 0);
        }
        for(j1 = 0; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, 3, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 0, j1, 2, Blocks.ladder, 2);
        }
        j1 = -1;
        while(!this.isOpaque(world, 0, j1, 3) && this.getY(j1) >= 0) {
            this.setBlockAndMetadata(world, 0, j1, 3, this.plank2Block, this.plank2Meta);
            this.setGrassToDirt(world, 0, j1 - 1, 3);
            if(!this.isOpaque(world, 0, j1, 2)) {
                this.setBlockAndMetadata(world, 0, j1, 2, Blocks.ladder, 2);
            }
            --j1;
        }
        this.placeChest(world, random, -2, 1, 2, 2, LOTRChestContents.ROHAN_WATCHTOWER);
        this.setBlockAndMetadata(world, 2, 1, 2, this.tableBlock, 0);
        for(k1 = -2; k1 <= 2; ++k1) {
            int k2 = Math.abs(k1);
            for(int i14 : new int[] {-3, 3}) {
                int j14 = -1;
                while(!this.isOpaque(world, i14, j14, k1) && this.getY(j14) >= 0) {
                    if(k2 == 2 && IntMath.mod(j14, 4) == 1 || k2 == 1 && IntMath.mod(j14, 2) == 0 || k2 == 0 && IntMath.mod(j14, 4) == 3) {
                        this.setBlockAndMetadata(world, i14, j14, k1, this.logBlock, this.logMeta);
                        if(k2 == 0) {
                            this.setBlockAndMetadata(world, i14 - 1 * Integer.signum(i14), j14, k1, Blocks.torch, i14 > 0 ? 1 : 2);
                        }
                    }
                    --j14;
                }
            }
        }
        int belowTop = this.getBelowTop(world, 2, -1, 2);
        this.placeChest(world, random, 2, belowTop, 2, 5, LOTRChestContents.ROHAN_WATCHTOWER);
        belowTop = this.getBelowTop(world, 2, -1, 0);
        this.setBlockAndMetadata(world, 2, belowTop, 0, this.plankBlock, this.plankMeta);
        this.setGrassToDirt(world, 2, belowTop - 1, 0);
        this.placeBarrel(world, random, 2, belowTop + 1, 0, 5, LOTRFoods.ROHAN_DRINK);
        belowTop = this.getBelowTop(world, -2, -1, 1);
        this.setBlockAndMetadata(world, -2, belowTop, 1, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -2, belowTop - 1, 1);
        belowTop = this.getBelowTop(world, -2, -1, 2);
        this.setBlockAndMetadata(world, -2, belowTop, 2, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -2, belowTop + 1, 2, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -2, belowTop - 1, 2);
        belowTop = this.getBelowTop(world, -1, -1, 2);
        this.setBlockAndMetadata(world, -1, belowTop, 2, Blocks.hay_block, 0);
        this.setGrassToDirt(world, -1, belowTop - 1, 2);
        int soldiers = 1 + random.nextInt(3);
        for(int l = 0; l < soldiers; ++l) {
            LOTREntityRohirrimWarrior rohirrim = random.nextInt(3) == 0 ? new LOTREntityRohirrimArcher(world) : new LOTREntityRohirrimWarrior(world);
            rohirrim.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 4);
        }
        return true;
    }

    private int getBelowTop(World world, int i, int j, int k) {
        while(!this.isOpaque(world, i, j, k) && this.getY(j) >= 0) {
            --j;
        }
        return j + 1;
    }
}
