package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHalfTrollWarlord;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHalfTrollWarlordHouse extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenHalfTrollWarlordHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int k1;
        int j1;
        int i12;
        int k12;
        int radius = 7;
        int height = 10 + random.nextInt(4);
        this.setOriginAndRotation(world, i, j, k, rotation, radius + 1);
        if(this.restrictions) {
            for(i12 = -radius; i12 <= radius; ++i12) {
                for(k12 = -radius; k12 <= radius; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12);
                    Block block = this.getBlock(world, i12, j1 - 1, k12);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        for(i12 = -radius; i12 <= radius; ++i12) {
            for(k12 = -radius; k12 <= radius; ++k12) {
                for(j1 = 0; j1 <= height; ++j1) {
                    double f = (i12 * i12 + k12 * k12) / 4.0 - (8 - j1);
                    if((f >= 8.0)) continue;
                    if(j1 == 0) {
                        for(int j2 = 0; (((j2 == 0) || !this.isOpaque(world, i12, j2, k12)) && (this.getY(j2) >= 0)); --j2) {
                            this.setBlockAndMetadata(world, i12, j2, k12, Blocks.hardened_clay, 0);
                            this.setGrassToDirt(world, i12, j2 - 1, k12);
                        }
                    }
                    if(f > 0.0) {
                        if(j1 <= 1 || j1 == height - 1) {
                            this.setBlockAndMetadata(world, i12, j1, k12, Blocks.stained_hardened_clay, 12);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i12, j1, k12, Blocks.hardened_clay, 0);
                        continue;
                    }
                    if(j1 == 0) {
                        this.setBlockAndMetadata(world, i12, j1, k12, Blocks.cobblestone, 0);
                        continue;
                    }
                    this.setAir(world, i12, j1, k12);
                }
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k12 = -radius; k12 <= -radius + 2; ++k12) {
                this.setBlockAndMetadata(world, i12, 0, k12, Blocks.cobblestone, 0);
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i12, j1, k12);
                }
            }
            this.setBlockAndMetadata(world, i12, 4, -radius, LOTRMod.woodSlabSingle, 3);
        }
        this.setBlockAndMetadata(world, -2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, -2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        this.setBlockAndMetadata(world, 2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, 2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k12 = -2; k12 <= 2; ++k12) {
                int i2 = Math.abs(i12);
                int k2 = Math.abs(k12);
                if(i2 == 2 || k2 == 2 || i2 == 0 && k2 == 0) {
                    for(int j12 = -4; j12 <= 0; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k12, Blocks.stained_hardened_clay, 12);
                    }
                    continue;
                }
                if(i2 != 1 && k2 != 1) continue;
                this.setBlockAndMetadata(world, i12, -4, k12, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i12, -3, k12, Blocks.fire, 0);
                this.setBlockAndMetadata(world, i12, -2, k12, Blocks.air, 0);
                this.setBlockAndMetadata(world, i12, -1, k12, Blocks.air, 0);
                this.setBlockAndMetadata(world, i12, 0, k12, Blocks.iron_bars, 0);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, Blocks.cobblestone, 0);
        for(int l = 0; l < 8; ++l) {
            i1 = (3 + (l + 1) / 2 % 2) * IntMath.pow(-1, l / 4);
            k1 = (3 + (l + 3) / 2 % 2) * IntMath.pow(-1, (l + 2) / 4);
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.cobblestone, 0);
            this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.fence, 3);
            this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.fence, 3);
        }
        this.setBlockAndMetadata(world, -5, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, -6, 3, 0);
        this.setAir(world, -7, 3, 0);
        this.setBlockAndMetadata(world, 5, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, 6, 3, 0);
        this.setAir(world, 7, 3, 0);
        this.setBlockAndMetadata(world, 0, 3, 5, LOTRMod.fence, 3);
        this.setAir(world, 0, 3, 6);
        this.setAir(world, 0, 3, 7);
        for(i12 = -4; i12 <= 4; i12 += 8) {
            this.setBlockAndMetadata(world, i12, 1, -2, Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i12, 1, -1, Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i12, 1, 1, Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i12, 1, 2, Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i12 + Integer.signum(i12), 1, 0, Blocks.stained_hardened_clay, 12);
            this.placeChest(world, random, i12, 1, 0, LOTRMod.chestBasket, 0, LOTRChestContents.HALF_TROLL_HOUSE);
        }
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, -1, 1, 4, Blocks.stone_slab, 11);
        this.setBlockAndMetadata(world, 0, 1, 4, Blocks.cobblestone, 0);
        this.setBlockAndMetadata(world, 1, 1, 4, Blocks.stone_slab, 11);
        this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.halfTrollTable, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.commandTable, 0);
        this.placeWallBanner(world, 0, 6, -radius + 1, LOTRItemBanner.BannerType.HALF_TROLL, 2);
        int b = 8;
        for(int l = 0; l < 2; ++l) {
            this.setBlockAndMetadata(world, 0, b, 0, LOTRMod.fence, 3);
            --b;
        }
        this.setBlockAndMetadata(world, 0, b, 0, LOTRMod.woodSlabSingle, 11);
        this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 0);
        this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 1);
        this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 2);
        this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 3);
        for(i1 = -radius; i1 <= radius; ++i1) {
            block16: for(k1 = -radius; k1 <= radius; ++k1) {
                if(random.nextInt(10) != 0) continue;
                for(int j13 = height; j13 > 0; --j13) {
                    if(!this.isAir(world, i1, j13, k1) || !this.isOpaque(world, i1, j13 - 1, k1)) continue;
                    this.placeSkull(world, random, i1, j13, k1);
                    continue block16;
                }
            }
        }
        LOTREntityHalfTrollWarlord halfTroll = new LOTREntityHalfTrollWarlord(world);
        halfTroll.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(halfTroll, world, 0, 1, 0, 16);
        return true;
    }
}
