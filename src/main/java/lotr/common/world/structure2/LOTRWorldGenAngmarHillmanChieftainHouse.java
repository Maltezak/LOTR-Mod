package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillmanChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanChieftainHouse extends LOTRWorldGenStructureBase2 {
    private Block woodBlock;
    private int woodMeta;
    private Block plankBlock;
    private int plankMeta;
    private Block slabBlock;
    private int slabMeta;
    private Block stairBlock;
    private Block fenceBlock;
    private int fenceMeta;
    private Block doorBlock;
    private Block floorBlock;
    private int floorMeta;

    public LOTRWorldGenAngmarHillmanChieftainHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j12;
        int j13;
        int i1;
        int k1;
        int i12;
        int k12;
        int j14;
        int j15;
        int j16;
        int k13;
        int k14;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -5; i13 <= 5; ++i13) {
                for(int k15 = -6; k15 <= 6; ++k15) {
                    j13 = this.getTopBlock(world, i13, k15);
                    Block block = this.getBlock(world, i13, j13 - 1, k15);
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
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.doorBlock = LOTRMod.doorSpruce;
        this.floorBlock = Blocks.stained_hardened_clay;
        this.floorMeta = 15;
        for(i12 = -5; i12 <= 5; ++i12) {
            for(k1 = -6; k1 <= 6; ++k1) {
                for(j12 = 1; j12 <= 10; ++j12) {
                    this.setAir(world, i12, j12, k1);
                }
                for(j12 = 0; (((j12 == 0) || !this.isOpaque(world, i12, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    if(this.getBlock(world, i12, j12 + 1, k1).isOpaqueCube()) {
                        this.setBlockAndMetadata(world, i12, j12, k1, Blocks.dirt, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, j12, k1, Blocks.grass, 0);
                    }
                    this.setGrassToDirt(world, i12, j12 - 1, k1);
                }
            }
        }
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k1 = -5; k1 <= 5; ++k1) {
                this.setBlockAndMetadata(world, i12, 0, k1, this.floorBlock, this.floorMeta);
                if(random.nextInt(2) != 0) continue;
                this.setBlockAndMetadata(world, i12, 1, k1, LOTRMod.thatchFloor, 0);
            }
        }
        for(int i14 : new int[] {-4, 4}) {
            for(k12 = -4; k12 <= 4; ++k12) {
                this.setBlockAndMetadata(world, i14, 1, k12, this.woodBlock, this.woodMeta | 8);
                this.setBlockAndMetadata(world, i14, 4, k12, this.woodBlock, this.woodMeta | 8);
            }
            for(j13 = 1; j13 <= 4; ++j13) {
                this.setBlockAndMetadata(world, i14, j13, -5, this.woodBlock, this.woodMeta);
                this.setBlockAndMetadata(world, i14, j13, 0, this.woodBlock, this.woodMeta);
                this.setBlockAndMetadata(world, i14, j13, 5, this.woodBlock, this.woodMeta);
            }
        }
        for(int i14 : new int[] {-3, 3}) {
            for(k12 = -4; k12 <= 4; ++k12) {
                this.setBlockAndMetadata(world, i14, 1, k12, this.plankBlock, this.plankMeta);
            }
            for(j13 = 2; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i14, j13, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i14, j13, -1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i14, j13, 1, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i14, j13, 4, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, i14, 3, -3, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i14, 3, -2, this.stairBlock, 6);
            this.setBlockAndMetadata(world, i14, 3, 2, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i14, 3, 3, this.stairBlock, 6);
            for(j13 = 1; j13 <= 5; ++j13) {
                this.setBlockAndMetadata(world, i14, j13, 0, this.woodBlock, this.woodMeta);
            }
            this.setBlockAndMetadata(world, i14, 1, -5, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, i14, 2, -5, this.stairBlock, 2);
            this.setBlockAndMetadata(world, i14, 3, -5, this.stairBlock, 6);
            this.setBlockAndMetadata(world, i14, 4, -5, this.slabBlock, this.slabMeta);
        }
        int[] i15 = new int[] {-2, 2};
        k1 = i15.length;
        for(j12 = 0; j12 < k1; ++j12) {
            int i14;
            i14 = i15[j12];
            for(j13 = 1; j13 <= 3; ++j13) {
                this.setBlockAndMetadata(world, i14, j13, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, i14, j13, -5, this.woodBlock, this.woodMeta);
            }
            this.setBlockAndMetadata(world, i14, 4, -5, this.slabBlock, this.slabMeta);
            this.setBlockAndMetadata(world, i14, 2, -6, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i14, 3, -6, Blocks.skull, 2);
        }
        for(j15 = 1; j15 <= 3; ++j15) {
            this.setBlockAndMetadata(world, -1, j15, -4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 1, j15, -4, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, -1, 2, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 2, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 3, -5, this.stairBlock, 4);
        this.setBlockAndMetadata(world, -1, 4, -5, this.stairBlock, 1);
        this.setBlockAndMetadata(world, 1, 3, -5, this.stairBlock, 5);
        this.setBlockAndMetadata(world, 1, 4, -5, this.stairBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 3, -4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, -5, this.slabBlock, this.slabMeta | 8);
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -4, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, i1, 5, -5, this.stairBlock, 6);
        }
        this.setBlockAndMetadata(world, -2, 5, -4, Blocks.skull, 3);
        this.setBlockAndMetadata(world, 2, 5, -4, Blocks.skull, 3);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, -5, this.woodBlock, this.woodMeta | 4);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 7, -5, this.woodBlock, this.woodMeta | 4);
        }
        for(j15 = 4; j15 <= 9; ++j15) {
            this.setBlockAndMetadata(world, 0, j15, -5, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, -4, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 6, -6, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, -4, Blocks.torch, 3);
        this.placeWallBanner(world, 0, 5, -5, LOTRItemBanner.BannerType.ANGMAR, 2);
        this.placeWallBanner(world, -2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
        this.placeWallBanner(world, 2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 5, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, i1, 2, 5, this.stairBlock, 3);
            this.setBlockAndMetadata(world, i1, 3, 5, this.stairBlock, 7);
            this.setBlockAndMetadata(world, i1, 4, 5, this.woodBlock, this.woodMeta | 4);
        }
        this.setBlockAndMetadata(world, -3, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 5, 5, this.slabBlock, this.slabMeta | 8);
        this.setBlockAndMetadata(world, 0, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 5, 5, this.slabBlock, this.slabMeta | 8);
        this.setBlockAndMetadata(world, 2, 5, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 3, 5, 5, this.plankBlock, this.plankMeta);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(j1 = 6; j1 <= 7; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, 5, this.plankBlock, this.plankMeta);
            }
        }
        for(int i14 : new int[] {-2, 2}) {
            for(j13 = 1; j13 <= 4; ++j13) {
                this.setBlockAndMetadata(world, i14, j13, 4, this.plankBlock, this.plankMeta);
            }
            this.setBlockAndMetadata(world, i14, 5, 4, this.fenceBlock, this.fenceMeta);
        }
        for(j14 = 4; j14 <= 5; ++j14) {
            this.setBlockAndMetadata(world, -3, j14, 4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 3, j14, 4, this.plankBlock, this.plankMeta);
        }
        for(j14 = 7; j14 <= 9; ++j14) {
            this.setBlockAndMetadata(world, 0, j14, 5, this.woodBlock, this.woodMeta);
        }
        this.setBlockAndMetadata(world, 0, 9, 4, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 5, 4, Blocks.torch, 4);
        this.placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.ANGMAR, 2);
        this.setBlockAndMetadata(world, -1, 4, 4, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 1, 4, 4, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 0, 3, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 3, 6, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 4, 6, this.woodBlock, this.woodMeta);
        this.setBlockAndMetadata(world, 0, 5, 6, this.woodBlock, this.woodMeta);
        this.setBlockAndMetadata(world, 0, 6, 6, this.stairBlock, 3);
        this.setBlockAndMetadata(world, -2, 5, 0, Blocks.torch, 2);
        this.placeWallBanner(world, -3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 1);
        this.setBlockAndMetadata(world, 2, 5, 0, Blocks.torch, 1);
        this.placeWallBanner(world, 3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 3);
        for(k13 = -3; k13 <= -1; ++k13) {
            this.setBlockAndMetadata(world, -3, 4, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 4, k13, this.stairBlock, 1);
        }
        for(k13 = -4; k13 <= -1; ++k13) {
            this.setBlockAndMetadata(world, -3, 5, k13, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 3, 5, k13, this.stairBlock, 5);
        }
        for(k13 = 1; k13 <= 3; ++k13) {
            this.setBlockAndMetadata(world, -3, 4, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 3, 4, k13, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 5, k13, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 3, 5, k13, this.stairBlock, 5);
        }
        for(k13 = -6; k13 <= 6; ++k13) {
            this.setBlockAndMetadata(world, -5, 4, k13, this.slabBlock, this.slabMeta | 8);
            this.setBlockAndMetadata(world, -4, 5, k13, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -3, 6, k13, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -2, 7, k13, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -2, 8, k13, this.stairBlock, 1);
            this.setBlockAndMetadata(world, -1, 9, k13, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 10, k13, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 0, 10, k13, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 1, 10, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 1, 9, k13, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 2, 8, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 2, 7, k13, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 3, 6, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, k13, this.stairBlock, 0);
            this.setBlockAndMetadata(world, 5, 4, k13, this.slabBlock, this.slabMeta | 8);
        }
        for(int k15 : new int[] {-6, 6}) {
            this.setBlockAndMetadata(world, -4, 4, k15, this.slabBlock, this.slabMeta | 8);
            this.setBlockAndMetadata(world, -3, 5, k15, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -2, 6, k15, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -1, 7, k15, this.stairBlock, 4);
            this.setBlockAndMetadata(world, -1, 8, k15, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 8, k15, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 7, k15, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 2, 6, k15, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 3, 5, k15, this.stairBlock, 5);
            this.setBlockAndMetadata(world, 4, 4, k15, this.slabBlock, this.slabMeta | 8);
        }
        this.setBlockAndMetadata(world, 0, 11, -6, this.stairBlock, 3);
        this.setBlockAndMetadata(world, 0, 11, -7, this.stairBlock, 6);
        this.setBlockAndMetadata(world, 0, 12, -7, this.stairBlock, 3);
        this.setBlockAndMetadata(world, 0, 11, 6, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 7, this.stairBlock, 7);
        this.setBlockAndMetadata(world, 0, 12, 7, this.stairBlock, 2);
        for(k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, -1, 10, k14, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1, 10, k14, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, -1, 11, k14, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 1, 11, k14, this.stairBlock, 0);
        }
        this.setBlockAndMetadata(world, 0, 11, -1, this.stairBlock, 2);
        this.setBlockAndMetadata(world, 0, 11, 1, this.stairBlock, 3);
        this.setAir(world, 0, 10, 0);
        for(int l = 0; l <= 2; ++l) {
            j1 = 4 + l * 2;
            this.setBlockAndMetadata(world, -4 + l, j1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -4 + l, j1 + 1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -4 + l, j1 + 2, 0, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 4 - l, j1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 4 - l, j1 + 1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 4 - l, j1 + 2, 0, this.stairBlock, 0);
        }
        for(k14 = -4; k14 <= 4; ++k14) {
            this.setBlockAndMetadata(world, -2, 6, k14, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k14, this.stairBlock, 5);
        }
        for(k14 = -3; k14 <= 3; ++k14) {
            this.setBlockAndMetadata(world, -1, 8, k14, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 1, 8, k14, this.stairBlock, 5);
        }
        for(int i14 : new int[] {-1, 1}) {
            this.setBlockAndMetadata(world, i14, 8, -5, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i14, 8, -4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i14, 8, 4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i14, 8, 5, this.plankBlock, this.plankMeta);
        }
        this.setBlockAndMetadata(world, -1, 7, -4, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 1, 7, -4, this.stairBlock, 5);
        this.setBlockAndMetadata(world, -1, 7, 4, this.stairBlock, 4);
        this.setBlockAndMetadata(world, 1, 7, 4, this.stairBlock, 5);
        for(j16 = 0; j16 >= -5; --j16) {
            for(int i16 = -1; i16 <= 1; ++i16) {
                for(int k16 = -1; k16 <= 1; ++k16) {
                    if(i16 == 0 && k16 == 0) {
                        this.setAir(world, i16, j16, k16);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i16, j16, k16, Blocks.cobblestone, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, -6, 0, LOTRMod.hearth, 0);
        this.setBlockAndMetadata(world, 0, -5, 0, Blocks.fire, 0);
        this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.bronzeBars, 0);
        this.setAir(world, 0, 1, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
        for(j16 = 1; j16 <= 2; ++j16) {
            this.setBlockAndMetadata(world, -1, j16, 4, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 1, j16, 4, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.angmarTable, 0);
        this.setBlockAndMetadata(world, -2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 3, Blocks.furnace, 5);
        this.placeChest(world, random, 2, 1, 2, 5, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
        LOTREntityAngmarHillmanChieftain chieftain = new LOTREntityAngmarHillmanChieftain(world);
        this.spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
        return true;
    }
}
