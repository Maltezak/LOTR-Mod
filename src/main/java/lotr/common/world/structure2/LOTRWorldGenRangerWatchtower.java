package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerWatchtower extends LOTRWorldGenStructureBase2 {
    private Block woodBlock;
    private int woodMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block fenceBlock;
    private int fenceMeta;
    private Block stairBlock;

    public LOTRWorldGenRangerWatchtower(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int k1;
        int randomWood;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if(this.restrictions) {
            for(int i13 = -4; i13 <= 4; ++i13) {
                for(int k12 = -4; k12 <= 4; ++k12) {
                    int j12 = this.getTopBlock(world, i13, k12);
                    Block block = this.getBlock(world, i13, j12 - 1, k12);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        if((randomWood = random.nextInt(4)) == 0) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.stairBlock = Blocks.oak_stairs;
        }
        else if(randomWood == 1) {
            this.woodBlock = Blocks.log;
            this.woodMeta = 1;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
            this.stairBlock = Blocks.spruce_stairs;
        }
        else if(randomWood == 2) {
            this.woodBlock = LOTRMod.wood2;
            this.woodMeta = 1;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 9;
            this.stairBlock = LOTRMod.stairsBeech;
        }
        else if(randomWood == 3) {
            this.woodBlock = LOTRMod.wood3;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 12;
            this.fenceBlock = LOTRMod.fence;
            this.fenceMeta = 12;
            this.stairBlock = LOTRMod.stairsMaple;
        }
        this.generateSupportPillar(world, -3, 4, -3);
        this.generateSupportPillar(world, -3, 4, 3);
        this.generateSupportPillar(world, 3, 4, -3);
        this.generateSupportPillar(world, 3, 4, 3);
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k1 = -2; k1 <= 2; ++k1) {
                for(int j13 = 5; j13 <= 19; ++j13) {
                    this.setAir(world, i12, j13, k1);
                }
            }
        }
        for(j1 = 6; j1 <= 19; ++j1) {
            this.setBlockAndMetadata(world, -2, j1, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -2, j1, 2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 2, j1, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 2, j1, 2, this.woodBlock, this.woodMeta);
        }
        for(j1 = 5; j1 <= 10; j1 += 5) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(int k13 = -3; k13 <= 3; ++k13) {
                    this.setBlockAndMetadata(world, i1, j1, k13, this.plankBlock, this.plankMeta);
                }
            }
            for(i1 = -4; i1 <= 4; ++i1) {
                this.setBlockAndMetadata(world, i1, j1, -4, this.stairBlock, 2);
                this.setBlockAndMetadata(world, i1, j1, 4, this.stairBlock, 3);
            }
            for(k1 = -3; k1 <= 3; ++k1) {
                this.setBlockAndMetadata(world, -4, j1, k1, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 4, j1, k1, this.stairBlock, 0);
            }
            for(i1 = -2; i1 <= 2; ++i1) {
                this.setBlockAndMetadata(world, i1, j1 + 1, -3, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, i1, j1 + 1, 3, this.fenceBlock, this.fenceMeta);
            }
            for(k1 = -2; k1 <= 2; ++k1) {
                this.setBlockAndMetadata(world, -3, j1 + 1, k1, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, 3, j1 + 1, k1, this.fenceBlock, this.fenceMeta);
            }
            this.setBlockAndMetadata(world, 0, j1 + 2, -3, Blocks.torch, 5);
            this.setBlockAndMetadata(world, 0, j1 + 2, 3, Blocks.torch, 5);
            this.setBlockAndMetadata(world, -3, j1 + 2, 0, Blocks.torch, 5);
            this.setBlockAndMetadata(world, 3, j1 + 2, 0, Blocks.torch, 5);
            this.spawnNPCAndSetHome(new LOTREntityRangerNorth(world), world, -1, j1 + 1, 0, 8);
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k1);
                if(i2 >= 2 && k2 >= 2) continue;
                this.setBlockAndMetadata(world, i12, 15, k1, this.plankBlock, this.plankMeta);
                if((i2 >= 2 || k2 != 2) && (i2 != 2 || k2 >= 2)) continue;
                this.setBlockAndMetadata(world, i12, 16, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        this.setGrassToDirt(world, 0, 0, 0);
        for(j1 = 1; j1 <= 25; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, 0, this.woodBlock, this.woodMeta);
            if(j1 > 15) continue;
            this.setBlockAndMetadata(world, 0, j1, -1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 6, -1, Blocks.trapdoor, 0);
        this.setBlockAndMetadata(world, 0, 11, -1, Blocks.trapdoor, 0);
        this.setBlockAndMetadata(world, 0, 17, -2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 17, 2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -2, 17, 0, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 2, 17, 0, Blocks.torch, 5);
        this.placeChest(world, random, 0, 16, 1, 0, LOTRChestContents.RANGER_TENT);
        this.setBlockAndMetadata(world, 0, 11, 1, LOTRMod.rangerTable, 0);
        for(j1 = 17; j1 <= 18; ++j1) {
            this.setBlockAndMetadata(world, -2, j1, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -2, j1, 2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2, j1, -2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2, j1, 2, this.fenceBlock, this.fenceMeta);
        }
        for(int step = 0; step <= 1; ++step) {
            for(i1 = -2 + step; i1 <= 2 - step; ++i1) {
                this.setBlockAndMetadata(world, i1, 20 + step, -2 + step, this.stairBlock, 2);
                this.setBlockAndMetadata(world, i1, 20 + step, 2 - step, this.stairBlock, 3);
            }
            for(k1 = -1 + step; k1 <= 1 - step; ++k1) {
                this.setBlockAndMetadata(world, -2 + step, 20 + step, k1, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 2 - step, 20 + step, k1, this.stairBlock, 0);
            }
        }
        this.placeWallBanner(world, -2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 3);
        this.placeWallBanner(world, 2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 1);
        this.placeWallBanner(world, 0, 15, -2, LOTRItemBanner.BannerType.RANGER_NORTH, 2);
        this.placeWallBanner(world, 0, 15, 2, LOTRItemBanner.BannerType.RANGER_NORTH, 0);
        for(j1 = 24; j1 <= 25; ++j1) {
            this.setBlockAndMetadata(world, 1, j1, 0, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 2, j1, 1, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 2, j1, 2, Blocks.wool, 13);
            this.setBlockAndMetadata(world, 3, j1, 3, Blocks.wool, 13);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityRangerNorth.class);
        respawner.setCheckRanges(24, -12, 20, 8);
        respawner.setSpawnRanges(4, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }

    private void generateSupportPillar(World world, int i, int j, int k) {
        int j1 = j;
        while(!this.isOpaque(world, i, j1, k) && this.getY(j1) >= 0) {
            this.setBlockAndMetadata(world, i, j1, k, this.woodBlock, this.woodMeta);
            this.setGrassToDirt(world, i, j1 - 1, i);
            --j1;
        }
    }
}
