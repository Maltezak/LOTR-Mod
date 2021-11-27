package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHalfTrollHouse extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenHalfTrollHouse(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int k1;
        int radius = 5;
        int height = 6 + random.nextInt(4);
        this.setOriginAndRotation(world, i, j, k, rotation, radius + 1);
        if(this.restrictions) {
            for(i1 = -radius; i1 <= radius; ++i1) {
                for(k1 = -radius; k1 <= radius; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1);
                    Block block = this.getBlock(world, i1, j1 - 1, k1);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        for(i1 = -radius; i1 <= radius; ++i1) {
            for(k1 = -radius; k1 <= radius; ++k1) {
                for(j1 = 0; j1 <= height; ++j1) {
                    double f = (i1 * i1 + k1 * k1) / 2.0 - (8 - j1);
                    if((f >= 8.0)) continue;
                    if(j1 == 0) {
                        for(int j2 = 0; (((j2 == 0) || !this.isOpaque(world, i1, j2, k1)) && (this.getY(j2) >= 0)); --j2) {
                            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.hardened_clay, 0);
                            this.setGrassToDirt(world, i1, j2 - 1, k1);
                        }
                    }
                    if(f > 0.0) {
                        if(j1 <= 1 || j1 == height - 1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.stained_hardened_clay, 12);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.hardened_clay, 0);
                        continue;
                    }
                    if(j1 == 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
                        continue;
                    }
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -radius; k1 <= -radius + 1; ++k1) {
                this.setBlockAndMetadata(world, i1, 0, k1, Blocks.cobblestone, 0);
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
            this.setBlockAndMetadata(world, i1, 4, -radius, LOTRMod.woodSlabSingle, 3);
        }
        this.setBlockAndMetadata(world, -2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, -2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        this.setBlockAndMetadata(world, 2, 2, -radius, LOTRMod.fence, 3);
        this.setBlockAndMetadata(world, 2, 3, -radius, LOTRMod.woodSlabSingle, 3);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == 2 || k2 == 2 || i2 == 0 && k2 == 0) {
                    for(int j12 = -4; j12 <= 0; ++j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, Blocks.stained_hardened_clay, 12);
                    }
                    continue;
                }
                if(i2 != 1 && k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, -4, k1, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i1, -3, k1, Blocks.fire, 0);
                this.setBlockAndMetadata(world, i1, -2, k1, Blocks.air, 0);
                this.setBlockAndMetadata(world, i1, -1, k1, Blocks.air, 0);
                this.setBlockAndMetadata(world, i1, 0, k1, Blocks.iron_bars, 0);
            }
        }
        this.setBlockAndMetadata(world, 0, 0, 0, Blocks.cobblestone, 0);
        for(int l = 0; l < 8; ++l) {
            int i12 = (2 + (l + 1) / 2 % 2) * IntMath.pow(-1, l / 4);
            int k12 = (2 + (l + 3) / 2 % 2) * IntMath.pow(-1, (l + 2) / 4);
            this.setBlockAndMetadata(world, i12, 1, k12, Blocks.cobblestone, 0);
            this.setBlockAndMetadata(world, i12, 2, k12, LOTRMod.fence, 3);
            this.setBlockAndMetadata(world, i12, 3, k12, LOTRMod.fence, 3);
        }
        this.setBlockAndMetadata(world, -4, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, -5, 3, 0);
        this.setBlockAndMetadata(world, 4, 3, 0, LOTRMod.fence, 3);
        this.setAir(world, 5, 3, 0);
        this.setBlockAndMetadata(world, 0, 3, 4, LOTRMod.fence, 3);
        this.setAir(world, 0, 3, 5);
        for(i1 = -3; i1 <= 3; i1 += 6) {
            this.setBlockAndMetadata(world, i1, 1, -1, Blocks.stone_slab, 11);
            this.setBlockAndMetadata(world, i1, 1, 1, Blocks.stone_slab, 11);
            this.placeChest(world, random, i1, 1, 0, LOTRMod.chestBasket, 0, LOTRChestContents.HALF_TROLL_HOUSE);
        }
        this.setBlockAndMetadata(world, -1, 1, 3, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, Blocks.stone_slab, 11);
        this.setBlockAndMetadata(world, 1, 1, 3, LOTRMod.halfTrollTable, 0);
        LOTREntityHalfTroll halfTroll = new LOTREntityHalfTroll(world);
        this.spawnNPCAndSetHome(halfTroll, world, 0, 1, 0, 16);
        return true;
    }
}
