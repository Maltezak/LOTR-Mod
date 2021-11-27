package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMoredainChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutChieftain extends LOTRWorldGenMoredainHut {
    public LOTRWorldGenMoredainHutChieftain(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 5;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int k2;
        int i1;
        int i2;
        int i12;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for(i12 = -5; i12 <= 5; ++i12) {
            for(k1 = -5; k1 <= 5; ++k1) {
                int j12;
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                if(i2 <= 4 && k2 <= 4) {
                    this.layFoundation(world, i12, k1);
                    for(j12 = 1; j12 <= 9; ++j12) {
                        this.setAir(world, i12, j12, k1);
                    }
                }
                if(i2 == 4 && k2 <= 4 || k2 == 4 && i2 <= 4) {
                    this.setBlockAndMetadata(world, i12, 1, k1, this.clayBlock, this.clayMeta);
                }
                if(i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
                    for(j12 = 2; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if(i2 == 4 && k2 == 4) {
                    for(j12 = 2; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(k1 == 5 && i2 <= 1) {
                    this.layFoundation(world, i12, k1);
                    this.setBlockAndMetadata(world, i12, 1, k1, this.clayBlock, this.clayMeta);
                    for(j12 = 2; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.brickBlock, this.brickMeta);
                    }
                    if(i2 == 0) {
                        this.setBlockAndMetadata(world, i12, 5, k1, this.plankBlock, this.plankMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta);
                    }
                }
                if(i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 5, k1, this.stainedClayBlock, this.stainedClayMeta);
                    if(i2 == 0 || k2 == 0) {
                        this.setBlockAndMetadata(world, i12, 6, k1, this.plankSlabBlock, this.plankSlabMeta);
                    }
                }
                if(i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta);
                }
                if(i2 == 3 && k2 == 3) {
                    this.setBlockAndMetadata(world, i12, 1, k1, this.plankBlock, this.plankMeta);
                    for(j12 = 2; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k1, this.fenceBlock, this.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankBlock, this.plankMeta);
                    this.setBlockAndMetadata(world, i12, 6, k1, this.fenceBlock, this.fenceMeta);
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    if(i2 == 0 || k2 == 0) {
                        this.setBlockAndMetadata(world, i12, 6, k1, this.plankSlabBlock, this.plankSlabMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i12, 6, k1, this.plankBlock, this.plankMeta);
                    }
                    this.setBlockAndMetadata(world, i12, 7, k1, this.plankBlock, this.plankMeta);
                }
                if(i2 == 3 && k2 == 2 || k2 == 3 && i2 == 2) {
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(i2 == 2 && k2 == 2) {
                    this.setBlockAndMetadata(world, i12, 6, k1, this.fenceBlock, this.fenceMeta);
                }
                if(k2 == 0 && i2 == 4) {
                    this.setBlockAndMetadata(world, i12, 2, k1, this.plankSlabBlock, this.plankSlabMeta);
                    this.setBlockAndMetadata(world, i12, 3, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(k2 > 1 || i2 != 5) continue;
                if(k2 == 0) {
                    this.setBlockAndMetadata(world, i12, 4, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                    this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 3, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                this.setBlockAndMetadata(world, i12, 5, k1, this.plankSlabBlock, this.plankSlabMeta);
                this.dropFence(world, i12, 2, k1);
            }
        }
        for(i12 = -4; i12 <= 4; ++i12) {
            for(k1 = -4; k1 <= 4; ++k1) {
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                if(i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 3 && k2 == 3) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 4 && k2 == 0 || k2 == 4 && i2 == 0) {
                    this.setBlockAndMetadata(world, i12, 8, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i12, 9, k1, this.fenceBlock, this.fenceMeta);
                }
                if(i2 == 2 && k2 == 2) {
                    this.setBlockAndMetadata(world, i12, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 == 3 && k2 == 0 || k2 == 3 && i2 == 0) {
                    this.setBlockAndMetadata(world, i12, 8, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i12, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 + k2 == 2) {
                    this.setBlockAndMetadata(world, i12, 9, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 + k2 == 1) {
                    this.setBlockAndMetadata(world, i12, 9, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 <= 2 && k2 <= 2 && i2 + k2 >= 3) {
                    this.setBlockAndMetadata(world, i12, 8, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 != 1 || k2 != 1) continue;
                this.setBlockAndMetadata(world, i12, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
            }
        }
        int[] i13 = new int[] {-1, 1};
        k1 = i13.length;
        for(i2 = 0; i2 < k1; ++i2) {
            int f = i13[i2];
            this.layFoundation(world, 2 * f, -5);
            this.setBlockAndMetadata(world, 2 * f, 1, -5, this.clayBlock, this.clayMeta);
            this.setBlockAndMetadata(world, 2 * f, 2, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 2, -4, this.stainedClayBlock, this.stainedClayMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -4, this.stainedClayBlock, this.stainedClayMeta);
            this.setAir(world, 1 * f, 1, -4);
            this.setAir(world, 1 * f, 2, -4);
            this.setBlockAndMetadata(world, 1 * f, 3, -4, this.stainedClayBlock, this.stainedClayMeta);
            this.setBlockAndMetadata(world, 1 * f, 4, -4, this.stainedClayBlock, this.stainedClayMeta);
            this.setAir(world, 0 * f, 1, -4);
            this.setAir(world, 0 * f, 2, -4);
            this.setAir(world, 0 * f, 3, -4);
            this.setBlockAndMetadata(world, 0 * f, 4, -4, this.stainedClayBlock, this.stainedClayMeta);
            this.setBlockAndMetadata(world, 2 * f, 4, -5, this.thatchSlabBlock, this.thatchSlabMeta);
            this.setBlockAndMetadata(world, 1 * f, 4, -5, this.thatchSlabBlock, this.thatchSlabMeta | 8);
            this.setBlockAndMetadata(world, 0 * f, 5, -5, this.thatchSlabBlock, this.thatchSlabMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, -3, this.thatchSlabBlock, this.thatchSlabMeta | 8);
            this.setBlockAndMetadata(world, 1 * f, 4, -3, this.thatchSlabBlock, this.thatchSlabMeta);
            this.setBlockAndMetadata(world, 0 * f, 4, -3, this.thatchSlabBlock, this.thatchSlabMeta | 8);
        }
        this.placeWallBanner(world, 0, 4, -4, LOTRItemBanner.BannerType.MOREDAIN, 2);
        this.placeWallBanner(world, -4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
        this.placeWallBanner(world, 4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
        this.placeWallBanner(world, -2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
        this.placeWallBanner(world, 0, 8, -2, LOTRItemBanner.BannerType.MOREDAIN, 0);
        this.placeWallBanner(world, 2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
        this.placeWallBanner(world, 0, 8, 2, LOTRItemBanner.BannerType.MOREDAIN, 2);
        this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.lionBed, 3);
        this.setBlockAndMetadata(world, -3, 1, -2, LOTRMod.lionBed, 11);
        this.setBlockAndMetadata(world, -2, 1, 0, LOTRMod.lionBed, 3);
        this.setBlockAndMetadata(world, -3, 1, 0, LOTRMod.lionBed, 11);
        this.setBlockAndMetadata(world, -3, 1, 1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 3, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 3, 1, 1, Blocks.crafting_table, 0);
        this.placeChest(world, random, 3, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 3, 1, -1, LOTRMod.moredainTable, 0);
        this.setBlockAndMetadata(world, 3, 1, -2, this.plankSlabBlock, this.plankSlabMeta | 8);
        for(i1 = -1; i1 <= 1; ++i1) {
            for(j1 = 1; j1 <= 4; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, 4, this.stainedClayBlock, this.stainedClayMeta);
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(j1 = 1; j1 <= 2; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, 3, this.plankBlock, this.plankMeta);
            }
        }
        this.setBlockAndMetadata(world, -1, 3, 3, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 1, 3, 3, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 1, 2, this.thatchSlabBlock, this.thatchSlabMeta);
        this.setBlockAndMetadata(world, 1, 1, 2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 2, 3, LOTRMod.goldBars, 0);
        for(int f : new int[] {-1, 1}) {
            for(int k12 = 2; k12 <= 3; ++k12) {
                this.setBlockAndMetadata(world, 2 * f, 3, k12, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                this.setBlockAndMetadata(world, 1 * f, 4, k12, this.thatchSlabBlock, this.thatchSlabMeta);
                this.setBlockAndMetadata(world, 0 * f, 4, k12, this.thatchSlabBlock, this.thatchSlabMeta | 8);
            }
        }
        this.setBlockAndMetadata(world, -3, 3, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 3, -5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 3, -1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -3, 3, 1, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 3, -1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 3, 1, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 0, -1, LOTRMod.commandTable, 0);
        LOTREntityMoredainChieftain chieftain = new LOTREntityMoredainChieftain(world);
        chieftain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
        return true;
    }
}
