package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanHouse extends LOTRWorldGenStructureBase2 {
    private Block woodBlock;
    private int woodMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block slabBlock;
    private int slabMeta;
    private Block stairBlock;
    private Block doorBlock;
    private Block floorBlock;
    private int floorMeta;

    public LOTRWorldGenAngmarHillmanHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        int i12;
        int j12;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -4; i13 <= 4; ++i13) {
                for(int k12 = -6; k12 <= 6; ++k12) {
                    int j13 = this.getTopBlock(world, i13, k12);
                    Block block = this.getBlock(world, i13, j13 - 1, k12);
                    if(block != Blocks.grass && block != Blocks.stone) {
                        return false;
                    }
                    if(j13 < minHeight) {
                        minHeight = j13;
                    }
                    if(j13 > maxHeight) {
                        maxHeight = j13;
                    }
                    if(maxHeight - minHeight <= 4) continue;
                    return false;
                }
            }
        }
        this.woodBlock = Blocks.log;
        this.woodMeta = 1;
        this.plankBlock = Blocks.planks;
        this.plankMeta = 1;
        this.slabBlock = Blocks.wooden_slab;
        this.slabMeta = 1;
        this.stairBlock = Blocks.spruce_stairs;
        this.doorBlock = LOTRMod.doorSpruce;
        this.floorBlock = Blocks.stained_hardened_clay;
        this.floorMeta = 15;
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k1 = -6; k1 <= 6; ++k1) {
                for(j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
                for(j1 = 0; (((j1 == 0) || !this.isOpaque(world, i12, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    if(this.getBlock(world, i12, j1 + 1, k1).isOpaqueCube()) {
                        this.setBlockAndMetadata(world, i12, j1, k1, Blocks.dirt, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, j1, k1, Blocks.grass, 0);
                    }
                    this.setGrassToDirt(world, i12, j1 - 1, k1);
                }
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k1 = -5; k1 <= 5; ++k1) {
                this.setBlockAndMetadata(world, i12, 0, k1, this.floorBlock, this.floorMeta);
                if(random.nextInt(2) != 0) continue;
                this.setBlockAndMetadata(world, i12, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        for(int j14 = 1; j14 <= 4; ++j14) {
            this.setBlockAndMetadata(world, -3, j14, -5, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 3, j14, -5, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -3, j14, 5, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 3, j14, 5, this.woodBlock, this.woodMeta);
        }
        for(int j15 : new int[] {1, 4}) {
            for(i1 = -2; i1 <= 2; ++i1) {
                this.setBlockAndMetadata(world, i1, j15, -5, this.woodBlock, this.woodMeta | 4);
                this.setBlockAndMetadata(world, i1, j15, 5, this.woodBlock, this.woodMeta | 4);
            }
            for(int k13 = -4; k13 <= 4; ++k13) {
                this.setBlockAndMetadata(world, -3, j15, k13, this.woodBlock, this.woodMeta | 8);
                this.setBlockAndMetadata(world, 3, j15, k13, this.woodBlock, this.woodMeta | 8);
            }
        }
        for(int k14 = -4; k14 <= 4; ++k14) {
            this.setBlockAndMetadata(world, -3, 2, k14, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 3, 2, k14, this.stairBlock, 0);
            this.setBlockAndMetadata(world, -3, 3, k14, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 3, 3, k14, this.stairBlock, 4);
        }
        int[] k14 = new int[] {-3, 3};
        k1 = k14.length;
        for(j1 = 0; j1 < k1; ++j1) {
            int i14 = k14[j1];
            this.setBlockAndMetadata(world, i14, 2, 0, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, i14, 3, 0, this.slabBlock, this.slabMeta | 8);
        }
        for(int k15 = -5; k15 <= 5; ++k15) {
            for(int l = 0; l <= 3; ++l) {
                this.setBlockAndMetadata(world, -4 + l, 4 + l, k15, this.stairBlock, 1);
                this.setBlockAndMetadata(world, 4 - l, 4 + l, k15, this.stairBlock, 0);
            }
            this.setBlockAndMetadata(world, 0, 7, k15, this.plankBlock, this.plankMeta);
        }
        for(int k12 : new int[] {-5, 5}) {
            for(i1 = -2; i1 <= 2; ++i1) {
                this.setBlockAndMetadata(world, i1, 4, k12, this.woodBlock, this.woodMeta | 4);
                this.setBlockAndMetadata(world, i1, 5, k12, this.woodBlock, this.woodMeta | 4);
            }
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 6, k12, this.woodBlock, this.woodMeta | 4);
            }
        }
        for(int i15 = -2; i15 <= 2; ++i15) {
            this.setBlockAndMetadata(world, i15, 2, 5, this.stairBlock, 3);
            this.setBlockAndMetadata(world, i15, 3, 5, this.stairBlock, 7);
        }
        this.setBlockAndMetadata(world, 0, 3, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, 6, this.stairBlock, 7);
        for(j12 = 4; j12 <= 8; ++j12) {
            this.setBlockAndMetadata(world, 0, j12, 6, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, 5, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 9, 6, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 1, -5, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -5, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 2, -5, this.stairBlock, 2);
        this.setBlockAndMetadata(world, -2, 3, -5, this.stairBlock, 6);
        this.setBlockAndMetadata(world, -1, 2, -5, this.stairBlock, 1);
        this.setBlockAndMetadata(world, -1, 3, -5, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 1, 2, -5, this.stairBlock, 0);
        this.setBlockAndMetadata(world, 1, 3, -5, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 2, 2, -5, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 2, 3, -5, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 3, -6, this.stairBlock, 6);
        for(j12 = 4; j12 <= 8; ++j12) {
            this.setBlockAndMetadata(world, 0, j12, -6, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 8, -5, this.stairBlock, 3);
        this.setBlockAndMetadata(world, 0, 9, -6, this.stairBlock, 3);
        this.setBlockAndMetadata(world, -1, 5, -6, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 1, 5, -6, Blocks.torch, 2);
        for(int k16 = -4; k16 <= 4; ++k16) {
            this.setBlockAndMetadata(world, -2, 1, k16, this.slabBlock, this.slabMeta | 8);
            this.setBlockAndMetadata(world, 2, 1, k16, this.slabBlock, this.slabMeta | 8);
        }
        this.setBlockAndMetadata(world, -2, 3, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 3, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, 4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
        this.setBlockAndMetadata(world, -1, 1, 4, Blocks.crafting_table, 0);
        this.placeChest(world, random, 1, 1, 4, 2, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
        this.placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.RHUDAUR, 2);
        this.setBlockAndMetadata(world, -1, 3, 4, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 1, 3, 4, Blocks.skull, 2);
        LOTREntityAngmarHillman hillman = new LOTREntityAngmarHillman(world);
        this.spawnNPCAndSetHome(hillman, world, 0, 1, 0, 8);
        return true;
    }
}
