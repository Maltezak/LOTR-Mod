package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeGarden
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeGarden(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i1 = -6; i1 <= 6; ++i1) {
                for (k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if (this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
            for (i1 = -3; i1 <= 3; ++i1) {
                for (k1 = -8; k1 <= 4; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if (this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for (i1 = -6; i1 <= 6; ++i1) {
            for (k1 = -3; k1 <= 3; ++k1) {
                for (j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for (i1 = -3; i1 <= 3; ++i1) {
            k1 = -4;
            for (j1 = 1; j1 <= 5; ++j1) {
                this.setAir(world, i1, j1, k1);
            }
        }
        for (i1 = -2; i1 <= 2; ++i1) {
            for (k1 = -8; k1 <= -5; ++k1) {
                for (j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("bree_garden");
        this.addBlockMetaAliasOption("COBBLE", 3, Blocks.cobblestone, 0);
        this.addBlockMetaAliasOption("COBBLE", 1, Blocks.mossy_cobblestone, 0);
        this.addBlockAliasOption("COBBLE_STAIR", 3, Blocks.stone_stairs);
        this.addBlockAliasOption("COBBLE_STAIR", 1, LOTRMod.stairsCobblestoneMossy);
        this.addBlockMetaAliasOption("COBBLE_WALL", 3, Blocks.cobblestone_wall, 0);
        this.addBlockMetaAliasOption("COBBLE_WALL", 1, Blocks.cobblestone_wall, 1);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.addBlockMetaAliasOption("LEAF", 1, Blocks.leaves, 4);
        this.addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves4, 4);
        this.addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves2, 5);
        this.addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves7, 4);
        this.addBlockMetaAliasOption("PATH", 14, Blocks.grass, 0);
        this.addBlockMetaAliasOption("PATH", 3, Blocks.cobblestone, 0);
        this.addBlockMetaAliasOption("PATH", 3, Blocks.cobblestone, 1);
        this.generateStrScan(world, random, 0, 0, 0);
        for (i1 = -5; i1 <= 5; ++i1) {
            for (k1 = -3; k1 <= 2; ++k1) {
                j1 = 1;
                Block below = this.getBlock(world, i1, j1 - 1, k1);
                if (below != Blocks.grass || !this.isAir(world, i1, j1, k1) || random.nextInt(5) != 0) continue;
                this.plantFlower(world, random, i1, j1, k1);
            }
        }
        return true;
    }
}

