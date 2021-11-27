package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHaradPyramid extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenHaradPyramid(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int pyramidRadius = 27;
        this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? pyramidRadius : 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i1 = -pyramidRadius; i1 <= pyramidRadius; ++i1) {
                for(int k1 = -pyramidRadius; k1 <= pyramidRadius; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    Block block = this.getBlock(world, i1, j1, k1);
                    if(this.isSurface(world, i1, j1, k1) || block == Blocks.stone || block == Blocks.sandstone || block == LOTRMod.redSandstone) continue;
                    return false;
                }
            }
        }
        this.originY += MathHelper.getRandomIntegerInRange(random, -2, 4);
        this.loadStrScan("harad_pyramid");
        this.addBlockMetaAliasOption("BRICK", 3, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK", 1, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 4, Blocks.air, 0);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 3, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 1, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK_SLAB", 3, LOTRMod.slabSingle4, 0);
        this.addBlockMetaAliasOption("BRICK_SLAB", 1, LOTRMod.slabSingle7, 1);
        this.addBlockAliasOption("BRICK_STAIR", 3, LOTRMod.stairsNearHaradBrick);
        this.addBlockAliasOption("BRICK_STAIR", 1, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockMetaAliasOption("BRICK_WALL", 3, LOTRMod.wall, 15);
        this.addBlockMetaAliasOption("BRICK_WALL", 1, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("PILLAR", 4, LOTRMod.pillar, 5);
        this.addBlockMetaAliasOption("PILLAR_SLAB", 4, LOTRMod.slabSingle4, 7);
        this.addBlockMetaAliasOption("BRICK2", 3, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("BRICK2", 1, LOTRMod.brick3, 14);
        this.addBlockMetaAliasOption("BRICK2_SLAB", 3, LOTRMod.slabSingle7, 2);
        this.addBlockMetaAliasOption("BRICK2_SLAB", 1, LOTRMod.slabSingle7, 3);
        this.addBlockAliasOption("BRICK2_STAIR", 3, LOTRMod.stairsNearHaradBrickRed);
        this.addBlockAliasOption("BRICK2_STAIR", 1, LOTRMod.stairsNearHaradBrickRedCracked);
        this.addBlockMetaAliasOption("TUNNEL", 5, Blocks.sand, 0);
        this.addBlockMetaAliasOption("TUNNEL", 5, Blocks.air, 0);
        this.addBlockMetaAliasOption("ROOF", 4, Blocks.sand, 1);
        this.addBlockMetaAliasOption("ROOF", 4, LOTRMod.redSandstone, 0);
        this.addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 14);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placePyramidChest(world, random, -4, -6, 3, 2);
        this.placePyramidChest(world, random, 0, -6, 3, 2);
        this.placePyramidChest(world, random, 4, -6, 3, 2);
        this.placePyramidChest(world, random, -5, -5, -7, 4);
        this.placePyramidChest(world, random, -3, -5, -7, 5);
        this.placePyramidChest(world, random, 3, -5, -7, 4);
        this.placePyramidChest(world, random, 5, -5, -7, 5);
        this.placePyramidChest(world, random, -4, -5, -5, 2);
        this.placePyramidChest(world, random, 4, -5, -5, 2);
        this.placeSpawnerChest(world, random, 0, -6, 15, LOTRMod.spawnerChestAncientHarad, 2, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, 12);
        this.placeMobSpawner(world, 0, -2, 15, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, -12, -2, -12, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, 12, -2, -12, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, 0, 8, 0, LOTREntityDesertScorpion.class);
        this.placePyramidChest(world, random, -12, -1, -12, 2, true);
        this.placePyramidChest(world, random, 12, -1, -12, 2, true);
        this.placePyramidChest(world, random, 0, 9, 0, 2, true);
        return true;
    }

    private void placePyramidChest(World world, Random random, int i, int j, int k, int meta) {
        this.placePyramidChest(world, random, i, j, k, meta, random.nextBoolean());
    }

    private void placePyramidChest(World world, Random random, int i, int j, int k, int meta, boolean trap) {
        int amount = MathHelper.getRandomIntegerInRange(random, 3, 5);
        if(trap) {
            this.placeSpawnerChest(world, random, i, j, k, LOTRMod.spawnerChestStone, meta, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
        }
        else {
            this.placeChest(world, random, i, j, k, LOTRMod.chestStone, meta, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
        }
    }
}
